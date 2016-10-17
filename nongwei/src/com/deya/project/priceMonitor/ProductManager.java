package com.deya.project.priceMonitor;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.*;

public class ProductManager implements ISyncCatch {

    public static TreeMap<Integer,ProductBean> ptMap = new TreeMap<Integer,ProductBean>();

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
            List<ProductBean> pdbList = ProductDAO.getAllProduct();
            if(pdbList != null && pdbList.size() > 0)
            {
                for(int i=0;i<pdbList.size();i++)
                {
                    ptMap.put(pdbList.get(i).getId(), pdbList.get(i));
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String getProductCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return ProductDAO.getProductCount(m);
    }

    public static List<ProductBean> getProductList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return setOtherName(ProductDAO.getProductList(m));
    }

    public static List<ProductBean> getAllProductListByTypeId(String typeId) {
        List<ProductBean> result =  new ArrayList<ProductBean>();
        Set<Integer> set = ptMap.keySet();
        for(int i : set){
            if(typeId.equals(ptMap.get(i).getTypeId() + ""))
            {
                result.add(ptMap.get(i));
            }
        }
        return setOtherName(result);
    }

    public static List<ProductBean> getAllProductListByMarketId(String marketId) {
        List<ProductBean> result =  new ArrayList<ProductBean>();
        Set<Integer> set = ptMap.keySet();
        for(int i : set){
            if(marketId.equals(ptMap.get(i).getMarketId() + ""))
            {
                result.add(ptMap.get(i));
            }
        }
        return setOtherName(result);
    }

    public static ProductBean getProductBean(String id) {

        return setOtherName(ptMap.get(Integer.parseInt(id)));
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if(ProductDAO.changeStatus(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean insertProduct(ProductBean hb, SettingLogsBean stl) {
        hb.setId(PublicTableDAO.getIDByTableName("dz_product"));
        hb.setAddTime(DateUtil.getCurrentDateTime());
        if(ProductDAO.insertProduct(hb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }


    public static boolean updateProduct(ProductBean hb, SettingLogsBean stl) {
        if(ProductDAO.updateProduct(hb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean deleteProduct(Map<String, String> m, SettingLogsBean stl) {
        if(ProductDAO.deleteProduct(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    private static ProductBean setOtherName(ProductBean pb)
    {
        if(pb != null) {
            int marketId = pb.getMarketId();
            MarketTypeBean mtb = MarketTypeManager.getMarketTypeBean(marketId + "");
            pb.setMarketName(mtb.getMarketName());
            int typeId = pb.getTypeId();
            ProductTypeBean ptb = ProductTypeManager.getProductTypeBean(typeId + "");
            pb.setTypeName(ptb.getTypeName());
        }
        return pb;
    }

    private static List<ProductBean> setOtherName(List<ProductBean> pbList)
    {
        List<ProductBean> result = new ArrayList<ProductBean>();
        if(pbList != null && pbList.size() > 0)
        {
            for(int i = 0; i < pbList.size(); i++){
                ProductBean pb = pbList.get(i);
                int marketId = pb.getMarketId();
                MarketTypeBean mtb = MarketTypeManager.getMarketTypeBean(marketId + "");
                pb.setMarketName(mtb.getMarketName());
                int typeId = pb.getTypeId();
                ProductTypeBean ptb = ProductTypeManager.getProductTypeBean(typeId + "");
                pb.setTypeName(ptb.getTypeName());
                result.add(pb);
            }
        }
        return result;
    }
}