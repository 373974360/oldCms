package com.deya.wcm.services.material;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.deya.util.UploadManager;
import com.deya.util.io.FileOperation;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.material.MateFolderBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.material.MateFolderDao;
import com.deya.wcm.server.ServerManager;

/**
 * 素材库文件夹管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 素材库文件夹管理逻处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 *
 * @author zhangqiang
 * @version 1.0
 *          *
 */
public class MateFolderManager implements ISyncCatch {
    private static TreeMap<String, MateFolderBean> mf_map = new TreeMap<String, MateFolderBean>();
    private static String root_MateFolder_fid = "0";

    static {
        reloadCatchHandl();
    }

    public void reloadCatch() {
        reloadCatchHandl();
    }

    public static void reloadCatchHandl() {
        List<MateFolderBean> mf_list = MateFolderDao.getMateFolderBeanList();
        mf_map.clear();
        if (mf_list != null && mf_list.size() > 0) {
            for (int i = 0; i < mf_list.size(); i++) {
                mf_map.put(mf_list.get(i).getF_id() + "", mf_list.get(i));
            }
        }
    }

    /**
     * 初始加载文件夹信息
     *
     * @param
     * @return
     */
    public static void reloadMateFolder() {
        SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.material.MateFolderManager");
    }

    /**
     * 得到文件夹Map
     *
     * @param
     * @return String
     */
    public static Map<String, MateFolderBean> getMateFolderMap() {
        return mf_map;
    }

    /**
     * 得到所有文件夹,并组织为json数据
     *
     * @param
     * @return String
     */
    public static String getMateFolderTreeJsonStr(String f_id, String site_id, String user_id) {
        String json_str = "[" + getMateTypeTreeJsonStr(site_id) + ",";
        json_str += "{\"id\":\"0\",\"text\":\"我的素材\",\"attributes\":{\"ctype\":\"custom\"}";
        String child_str = getMateFolderTreeJsonStrHandl(getMateFolderList(root_MateFolder_fid, site_id, user_id));
        if (child_str != null && !"".equals(child_str))
            json_str += ",\"children\":[" + child_str + "]";
        json_str += "}]";
        return json_str;
    }

    /**
     * 得到自定义文件夹,并组织为json数据
     *
     * @param
     * @return String
     */
    public static String getMFTreeJsonStrForCustom(String site_id, String user_id) {
        String json_str = "[";
        json_str += "{\"id\":\"0\",\"text\":\"我的素材\",\"attributes\":{\"ctype\":\"custom\"}";
        String child_str = getMateFolderTreeJsonStrHandl(getMateFolderList(root_MateFolder_fid, site_id, user_id));
        if (child_str != null && !"".equals(child_str))
            json_str += ",\"children\":[" + child_str + "]";
        json_str += "}]";
        return json_str;

    }

    /**
     * 根据文件夹集合,组织json字符串
     *
     * @param List<MateFolderBean> all_matefolder_list
     * @return String
     */
    public static String getMateFolderTreeJsonStrHandl(List<MateFolderBean> all_matefolder_list) {
        String json_str = "";
        if (all_matefolder_list != null && all_matefolder_list.size() > 0) {
            for (int i = 0; i < all_matefolder_list.size(); i++) {
                json_str += "{";
                json_str += "\"id\":" + all_matefolder_list.get(i).getF_id() + ",\"text\":\"" + all_matefolder_list.get(i).getCname() + "\",\"attributes\":{\"ctype\":\"custom\"}";
                List<MateFolderBean> child_o_list = getMateFolderList(all_matefolder_list.get(i).getF_id() + "", all_matefolder_list.get(i).getSite_id(), all_matefolder_list.get(i).getUser_id() + "");
                if (child_o_list != null && child_o_list.size() > 0)
                    json_str += ",\"children\":[" + getMateFolderTreeJsonStrHandl(child_o_list) + "]";
                json_str += "}";
                if (i + 1 != all_matefolder_list.size())
                    json_str += ",";
            }
        }
        return json_str;
    }

    /**
     * 根据文件夹ID得到对象
     *
     * @param String f_id
     * @return MateFolderBean
     */
    public static MateFolderBean getMateFolderBean(String f_id) {
        if (mf_map.containsKey(f_id)) {
            return mf_map.get(f_id);
        } else {
            MateFolderBean mf = MateFolderDao.getMateFolderBean(f_id);
            mf_map.put(f_id, mf);
            return mf;
        }
    }

    /**
     * 根据文件夹ID得到此节点下的文件夹节点个数
     *
     * @param String f_id
     * @return String
     */
    @SuppressWarnings("unchecked")
    public static String getChildMateFolderCount(String f_id, String user_id) {
        int count = 0;
        Iterator iter = mf_map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            MateFolderBean mf = getMateFolderBean(mf_map.get(key).getF_id() + "");
            if (f_id.equals(mf_map.get(key).getParent_id() + "") && user_id.equals(mf.getUser_id() + "")) {
                count += 1;
            }
        }
        return count + "";
    }

    /**
     * 根据文件夹ID得到此节点下的文件夹节点列表deep+1
     *
     * @param String f_id
     * @return List
     */
    @SuppressWarnings("unchecked")
    public static List<MateFolderBean> getMateFolderList(String f_id, String site_id, String user_id) {
        List<MateFolderBean> oL = new ArrayList<MateFolderBean>();
        Iterator iter = mf_map.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            MateFolderBean mf = getMateFolderBean(mf_map.get(key).getF_id() + "");
            if (mf != null) {
                if ("0".equals(mf.getParent_id()) || f_id.equals(mf.getParent_id() + "") && site_id.equals(mf.getSite_id()) && user_id.equals(mf.getUser_id() + "")) {
                    oL.add((MateFolderBean) entry.getValue());
                }
            }

        }
        Collections.sort(oL, new MateFolderComparator());
        return oL;
    }

    /**
     * 根据文件夹ID得到此节点下的子节点ID deep+n(可输入多个菜单ID,一般用于删除子节点),
     *
     * @param String menu_id
     * @return List
     */
    public static String getALLChildOptIDSByID(String f_ids) {
        String o_ids = "";
        if (f_ids != null && !"".equals(f_ids)) {
            String[] opt_a = f_ids.split(",");
            for (int i = 0; i < opt_a.length; i++) {
                MateFolderBean mf = getMateFolderBean(opt_a[i]);
                if (mf != null) {
                    String tree_position = mf.getF_treeposition();
                    Set<String> set = mf_map.keySet();
                    for (String j : set) {
                        mf = mf_map.get(j);
                        if (mf.getF_treeposition().startsWith(tree_position) && !tree_position.equals(mf.getF_treeposition()))
                            o_ids += "," + mf.getF_id();
                    }
                }
            }
        }
        return o_ids;
    }


    /**
     * 插入文件夹信息
     *
     * @param MateFolderBean  mf
     * @param SettingLogsBean stl
     * @return boolean
     */
    public static boolean insertMateFolder(MateFolderBean mf, SettingLogsBean stl) {
        //mf.setF_treeposition(getMateFolderBean(mf.getParent_id()+"").getF_treeposition());
        //mf.getSite_id();
        //mf.getUser_id();
        String father_treeposition = "";
        MateFolderBean f_FBean = getMateFolderBean(mf.getParent_id() + "");
        //System.out.println("insertMateFolder====="+f_FBean);
        if (f_FBean == null) {
            father_treeposition = "$0$";
        } else {
            father_treeposition = f_FBean.getF_treeposition();
        }
        mf.setF_treeposition(father_treeposition + mf.getF_id() + "$");
        String creatDtime = com.deya.util.DateUtil.getCurrentDateTime();
        mf.setCreat_dtime(creatDtime);

        //不需要应用id，将应用id写死
        mf.setApp_id("cms");
        if (MateFolderDao.insertMateFolder(mf, stl)) {
            reloadMateFolder();
            return true;
        } else {
            return false;
        }
    }

    public static int getMateFolderID() {
        return PublicTableDAO.getIDByTableName(PublicTableDAO.MateFolder_TABLE_NAME);
    }

    /**
     * 修改文件夹信息
     *
     * @param MateFolderBean  mf
     * @param SettingLogsBean stl
     * @return boolean
     */
    public static boolean updateMateFolder(MateFolderBean mf, SettingLogsBean stl) {
        if (MateFolderDao.updateMateFolder(mf, stl)) {
            reloadMateFolder();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 移动文件夹
     *
     * @param String parent_id
     * @param String f_ids
     * @return boolean
     */
    public static boolean moveMateFolder(String parent_id, String f_ids, SettingLogsBean stl, String site_id, String user_id) {
        String parent_tree_position = getMateFolderBean(parent_id).getF_treeposition();
        if (f_ids != null && !"".equals(f_ids)) {
            try {
                String[] tempA = f_ids.split(",");
                for (int i = 0; i < tempA.length; i++) {
                    moveMateFolderHandl(tempA[i], parent_id, parent_tree_position, stl, site_id, user_id);
                }
                reloadMateFolder();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static void moveMateFolderHandl(String f_id, String parent_id, String tree_position, SettingLogsBean stl, String site_id, String user_id) {
        String position = tree_position + f_id + "$";
        Map<String, String> new_m = new HashMap<String, String>();
        new_m.put("f_id", f_id);
        new_m.put("parent_id", parent_id);
        new_m.put("f_treeposition", position);
        if (MateFolderDao.moveMateFolder(new_m, stl)) {
            //该节点下的子节点一并转移
            List<MateFolderBean> o_list = getMateFolderList(f_id, site_id, user_id);
            if (o_list != null && o_list.size() > 0) {
                for (int i = 0; i < o_list.size(); i++) {
                    moveMateFolderHandl(o_list.get(i).getF_id() + "", f_id, position, stl, site_id, user_id);
                }
            }
        }
    }

    /**
     * 删除文件夹信息
     *
     * @param String          f_id
     * @param SettingLogsBean stl
     * @return boolean
     */
    public static boolean deleteMateFolder(String f_id, SettingLogsBean stl) {
        f_id = f_id + getALLChildOptIDSByID(f_id);
        if (MateFolderDao.deleteMateFolder(f_id, stl)) {
            reloadMateFolder();
            return true;
        } else {
            return false;
        }
    }

    static class MateFolderComparator implements Comparator<Object> {

        public int compare(Object o1, Object o2) {

            MateFolderBean mf1 = (MateFolderBean) o1;
            MateFolderBean mf2 = (MateFolderBean) o2;
            if (mf1.getF_id() > mf2.getF_id()) {
                return 1;
            } else {
                if (mf1.getF_id() == mf2.getF_id()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        }
    }

    public static void main(String args[]) {
    }

    public static void insertMateFolderTest() {
        MateFolderBean mf = new MateFolderBean();
        mf.setF_id(1);
        mf.setApp_id("cms");
        mf.setF_treeposition("$0");
        mf.setCname("测试信息");
        mf.setCreat_dtime("2011-04-08 19:20:00");
        mf.setParent_id(0);
        mf.setUser_id(3);
        mf.setSite_id("tt");
        insertMateFolder(mf, new SettingLogsBean());
    }


    /**
     * 拼到素材类别树,并组织为json数据
     *
     * @param
     * @return String
     */
    public static String getMateTypeTreeJsonStr() {
        String json_str = "{\"id\":-1,\"text\":\"素材类别\",\"attributes\":{\"ctype\":\"fixed\"},\"children\":[{\"id\":\"type_0\",\"text\":\"图片\",\"attributes\":{\"ctype\":\"fixed\"}}," +
                "{\"id\":\"type_1\",\"text\":\"Flash\",\"attributes\":{\"ctype\":\"fixed\"}}," +
                "{\"id\":\"type_2\",\"text\":\"视频\",\"attributes\":{\"ctype\":\"fixed\"}}," +
                "{\"id\":\"type_3\",\"text\":\"文件\",\"attributes\":{\"ctype\":\"fixed\"}}," +
                "{\"id\":\"type_4\",\"text\":\"其它\",\"attributes\":{\"ctype\":\"fixed\"}}]";
        json_str += "}";
        return json_str;
    }

    public static String getMateTypeTreeJsonStr(String site_id) {
        String json_str = "{\"id\":-1,\"text\":\"素材类别\",\"attributes\":{\"ctype\":\"fixed\",\"state\":\"open\"},\"children\":[{\"id\":\"type_0\",\"text\":\"图片\",\"attributes\":{\"ctype\":\"fixed\"},\"state\":\"closed\",\"children\":[" +
                getFolderJSONStr(site_id, "type_0") + "]}," +
                "{\"id\":\"type_1\",\"text\":\"Flash\",\"attributes\":{\"ctype\":\"fixed\"},\"state\":\"closed\",\"children\":[" + getFolderJSONStr(site_id, "type_1") + "]}," +
                "{\"id\":\"type_2\",\"text\":\"视频\",\"attributes\":{\"ctype\":\"fixed\"},\"state\":\"closed\",\"children\":[" + getFolderJSONStr(site_id, "type_2") + "]}," +
                "{\"id\":\"type_3\",\"text\":\"文件\",\"attributes\":{\"ctype\":\"fixed\"},\"state\":\"closed\",\"children\":[" + getFolderJSONStr(site_id, "type_3") + "]}," +
                "{\"id\":\"type_4\",\"text\":\"其它\",\"attributes\":{\"ctype\":\"fixed\"},\"state\":\"closed\",\"children\":[" + getFolderJSONStr(site_id, "type_4") + "]}]";
        json_str = json_str + "}";
        return json_str;
    }

    public static String getFolderJSONStr(String site_id, String imgleibie) {
        String upload_img_path = UploadManager.getUploadFilePath(site_id);
        upload_img_path = upload_img_path.substring(0, upload_img_path.length() - 7);
        String json = "";
        json = json + getFolderJSONStrForPath(upload_img_path, imgleibie);
        if (ServerManager.isWindows()) {
            json = json.replaceAll("\\\\", "\\\\\\\\");
        }
        return json;
    }

    public static String getFolderJSONStrForPath(String file_path, String imgleibie) {
        //System.out.println("***************file_path*******************"+file_path);
        String json_str = "";
        List list = FileOperation.getFolderList(file_path);
        if ((list != null) && (list.size() > 0)) {
            json_str = getFolderJSONStrHandl(list, imgleibie);
        }
        return json_str;
    }

    public static String getFolderJSONStrHandl(List<String> l, String imgleibie) {
        String json = "";
        TreeMap map = new TreeMap();
        for (int i = 0; i < l.size(); i++) {
            String upload_years = (String) l.get(i);
            String upload_month = (String) l.get(i);
            String month = "";
            upload_years = upload_years.substring(upload_years.lastIndexOf(File.separator) + 1).substring(0, 4);
            upload_month = upload_month.substring(upload_month.lastIndexOf(File.separator) + 1).substring(4, 6);
            if (map.containsKey(upload_years)) {
                month = (String) map.get(upload_years);
                map.remove(upload_years);
                month = month + "," + upload_month;
                map.put(upload_years, month);
            } else {
                map.put(upload_years, upload_month);
            }
        }
        json = getFolderJSONS(map, imgleibie);
        return json;
    }

    public static String getFolderJSONS(Map<String, String> map, String imgleibie) {
        String json = "";
        for (Map.Entry entry : map.entrySet()) {
            json = json + "{";
            String key = ((String) entry.getKey()).toString();
            String value = ((String) entry.getValue()).toString();
            json = json + "\"id\":\"" + imgleibie + "\",\"text\":\"" + key + "\",\"attributes\":{\"ctype\":\"y\",\"year\":\"" + key + "\"},\"state\":\"closed\"";
            if (value.contains(",")) {
                String[] monthArray = value.split(",");
                Arrays.sort(monthArray);
                json = json + ",\"children\":[";
                for (String monthValue : monthArray) {
                    json = json + "{\"id\":\"" + imgleibie + "\",\"text\":\"" + monthValue + "\",\"attributes\":{\"ctype\":\"m\",\"year\":\"" + key + "\"}},";
                }
                json = json.substring(0, json.length() - 1);
                json = json + "]},";
            } else {
                json = json + ",\"children\":[{\"id\":\"" + imgleibie + "\",\"text\":\"" + value + "\",\"attributes\":{\"ctype\":\"m\",\"year\":\"" + key + "\"}}";
                json = json + "]},";
            }
        }
        json = json.substring(0, json.length() - 1);
        return json;
    }


    public static void updateMateFolderTest() {
//		MateFolderBean mf = new MateFolderBean();
//		mf.setf_id(101);
//		mf.setAction("action1");
//		mf.setApp_id("org");
//		mf.setOpt_memo("app_memo1");
//		mf.setController("controller1");
//		mf.setOpt_flag("opt_flag1");
//		mf.setOpt_name("部门管理1");
//		mf.setParent_id(2);
//		mf.setTree_position("$1$2$1");
//		updateMateFolder(mf,new SettingLogsBean());
    }
}
