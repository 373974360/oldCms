package com.deya.project.priceMonitor;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.dao.PublicTableDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductManager
        implements ISyncCatch
{
    static Logger logger = LoggerFactory.getLogger(ProductManager.class);
    public static TreeMap<Integer, ProductBean> ptMap = new TreeMap();

    static
    {
        reloadCatchHandl();
    }

    public static void reloadCatchHandl()
    {
        ptMap.clear();
        try
        {
            Map<String, String> m = new HashMap();
            List<ProductBean> pdbList = ProductDAO.getAllProduct();
            if ((pdbList != null) && (pdbList.size() > 0)) {
                for (int i = 0; i < pdbList.size(); i++) {
                    ptMap.put(Integer.valueOf(((ProductBean)pdbList.get(i)).getId()), pdbList.get(i));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String getProductCount(Map<String, String> m)
    {
        if ((m.containsKey("key_word")) &&
                (!FormatUtil.isValiditySQL((String)m.get("key_word")))) {
            return "0";
        }
        return ProductDAO.getProductCount(m);
    }

    public static List<ProductBean> getProductList(Map<String, String> m)
    {
        if ((m.containsKey("key_word")) &&
                (!FormatUtil.isValiditySQL((String)m.get("key_word")))) {
            return new ArrayList();
        }
        return setOtherName(ProductDAO.getProductList(m));
    }

    public static List<ProductBean> getAllProductListByTypeId(String typeId)
    {
        List<ProductBean> result = new ArrayList();
        Set<Integer> set = ptMap.keySet();
        for (Iterator localIterator = set.iterator(); localIterator.hasNext();)
        {
            int i = ((Integer)localIterator.next()).intValue();
            if (typeId.equals(((ProductBean)ptMap.get(Integer.valueOf(i))).getTypeId() + "")) {
                result.add(ptMap.get(Integer.valueOf(i)));
            }
        }
        return setOtherName(result);
    }

    public static List<ProductBean> getAllProductListByMarketId(String marketId)
    {
        List<ProductBean> result = new ArrayList();
        Set<Integer> set = ptMap.keySet();
        for (Iterator localIterator = set.iterator(); localIterator.hasNext();)
        {
            int i = ((Integer)localIterator.next()).intValue();
            if (marketId.equals(((ProductBean)ptMap.get(Integer.valueOf(i))).getMarketId() + "")) {
                result.add(ptMap.get(Integer.valueOf(i)));
            }
        }
        return setOtherName(result);
    }

    public static ProductBean getProductBean(String id)
    {
        return setOtherName((ProductBean)ptMap.get(Integer.valueOf(Integer.parseInt(id))));
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl)
    {
        if (ProductDAO.changeStatus(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean insertProduct(ProductBean hb, SettingLogsBean stl)
    {
        hb.setId(PublicTableDAO.getIDByTableName("dz_product"));
        hb.setAddTime(DateUtil.getCurrentDateTime());
        if (ProductDAO.insertProduct(hb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean updateProduct(ProductBean hb, SettingLogsBean stl)
    {
        if (ProductDAO.updateProduct(hb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean deleteProduct(Map<String, String> m, SettingLogsBean stl)
    {
        if (ProductDAO.deleteProduct(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    private static ProductBean setOtherName(ProductBean pb)
    {
        if (pb != null)
        {
            int marketId = pb.getMarketId();
            MarketTypeBean mtb = MarketTypeManager.getMarketTypeBean(marketId + "");
            pb.setMarketName(mtb.getMarketName());
            int typeId = pb.getTypeId();
            ProductTypeBean ptb = ProductTypeManager.getProductTypeBean(typeId + "");
            if (ptb != null) {
                pb.setTypeName(ptb.getTypeName());
            } else {
                logger.warn("产品分类(" + typeId + ")没有找到");
            }
        }
        return pb;
    }

    private static List<ProductBean> setOtherName(List<ProductBean> pbList)
    {
        List<ProductBean> result = new ArrayList();
        if ((pbList != null) && (pbList.size() > 0)) {
            for (int i = 0; i < pbList.size(); i++)
            {
                ProductBean pb = (ProductBean)pbList.get(i);
                int marketId = pb.getMarketId();
                MarketTypeBean mtb = MarketTypeManager.getMarketTypeBean(marketId + "");
                pb.setMarketName(mtb.getMarketName());
                int typeId = pb.getTypeId();
                ProductTypeBean ptb = ProductTypeManager.getProductTypeBean(typeId + "");
                if (ptb != null) {
                    pb.setTypeName(ptb.getTypeName());
                } else {
                    logger.warn("产品分类(" + typeId + ")没有找到");
                }
                result.add(pb);
            }
        }
        return result;
    }

    public void reloadCatch() {}
}
