package com.deya.project.priceMonitor;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.*;

public class ProductTypeManager implements ISyncCatch {

    public static TreeMap<Integer,ProductTypeBean> ptMap = new TreeMap<Integer,ProductTypeBean>();

    static{
        reloadCatchHandl();
    }

    public void reloadCatch()
    {
        reloadCatchHandl();
    }

    public static void reloadCatchHandl()
    {
        ptMap.clear();
        try{
            Map<String,String> m = new HashMap<String,String>();
            List<ProductTypeBean> ptb_list = ProductTypeDAO.getAllProductTypeList();
            if(ptb_list != null && ptb_list.size() > 0)
            {
                for(int i=0;i<ptb_list.size();i++)
                {
                    ptMap.put(ptb_list.get(i).getId(), ptb_list.get(i));
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String getProductTypeCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return ProductTypeDAO.getProductTypeCount(m);
    }

    public static List<ProductTypeBean> getProductTypeList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return setOtherName(ProductTypeDAO.getProductTypeList(m));
    }

    public static List<ProductTypeBean> getAllProductTypeList() {
        List<ProductTypeBean> result = new ArrayList<ProductTypeBean>();
        Set<Integer> set = ptMap.keySet();
        for(int i : set){
            result.add(ptMap.get(i));
        }
        return setOtherName(result);
    }

    public static List<ProductTypeBean> getProductTypeByMarketId(String marketId) {
        List<ProductTypeBean> result = new ArrayList<ProductTypeBean>();
        Set<Integer> set = ptMap.keySet();
        for(int i : set){
            if(ptMap.get(i).getMarketId() == Integer.parseInt(marketId))
            {
                result.add(ptMap.get(i));
            }
        }
        return setOtherName(result);
    }

    public static ProductTypeBean getProductTypeBean(String id) {
        return setOtherName(ptMap.get(Integer.parseInt(id)));
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if(ProductTypeDAO.changeStatus(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean insertProductType(ProductTypeBean ptb, SettingLogsBean stl) {
        ptb.setId(PublicTableDAO.getIDByTableName("dz_productTpye"));
        ptb.setAddTime(DateUtil.getCurrentDateTime());
        if(ProductTypeDAO.insertProductType(ptb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean updateProductType(ProductTypeBean ptb, SettingLogsBean stl) {
        ptb.setAddTime(DateUtil.getCurrentDateTime());
        if(ProductTypeDAO.updateProductType(ptb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean deleteProductType(Map<String, String> m, SettingLogsBean stl) {
        if(ProductTypeDAO.deleteProductType(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    private static ProductTypeBean setOtherName(ProductTypeBean pb)
    {
        if(pb != null){
            int marketId = pb.getMarketId();
            MarketTypeBean mtb = MarketTypeManager.getMarketTypeBean(marketId + "");
            pb.setMarketName(mtb.getMarketName());
        }
        return pb;
    }

    private static List<ProductTypeBean> setOtherName(List<ProductTypeBean> pbList)
    {
        List<ProductTypeBean> result = new ArrayList<ProductTypeBean>();
        if(pbList != null && pbList.size() > 0)
        {
            for(int i = 0; i < pbList.size(); i++){
                ProductTypeBean pb = pbList.get(i);
                int marketId = pb.getMarketId();
                MarketTypeBean mtb = MarketTypeManager.getMarketTypeBean(marketId + "");
                pb.setMarketName(mtb.getMarketName());
                result.add(pb);
            }
        }
        return result;
    }
}