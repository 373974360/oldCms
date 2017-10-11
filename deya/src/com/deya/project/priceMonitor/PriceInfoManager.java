package com.deya.project.priceMonitor;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.services.org.user.UserManager;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PriceInfoManager
{
    static Logger logger = LoggerFactory.getLogger(PriceInfoManager.class);

    public static String getPriceInfoCount(Map<String, String> m)
    {
        if ((m.containsKey("key_word")) &&
                (!FormatUtil.isValiditySQL((String)m.get("key_word")))) {
            return "0";
        }
        return PriceInfoDAO.getPriceInfoCount(m);
    }

    public static List<PriceInfoBean> getPriceInfoList(Map<String, String> m)
    {
        if ((m.containsKey("key_word")) &&
                (!FormatUtil.isValiditySQL((String)m.get("key_word")))) {
            return new ArrayList();
        }
        return setOhterName(PriceInfoDAO.getPriceInfoList(m));
    }

    public static List<PriceInfoBean> getAllPriceInfoList()
    {
        return setOhterName(PriceInfoDAO.getAllPriceInfoList());
    }

    public static List<PriceInfoBean> getAllPriceInfoListByDate(Map<String, String> m)
    {
        return setOhterName(PriceInfoDAO.getAllPriceInfoListByDate(m));
    }

    public static List<PriceInfoBean> getAllPriceInfoByProductID(Map<String, String> m)
    {
        return setOhterName(PriceInfoDAO.getAllPriceInfoByProductID(m));
    }

    public static PriceInfoBean getPriceInfoBean(String id)
    {
        return setOhterName(PriceInfoDAO.getPriceInfoBean(id));
    }

    public static boolean insertPriceInfo(PriceInfoBean hb, SettingLogsBean stl)
    {
        hb.setId(PublicTableDAO.getIDByTableName("dz_priceInfo"));
        if ((hb.getAddTime() == null) || ("".equals(hb.getAddTime()))) {
            hb.setAddTime(DateUtil.getCurrentDate());
        }
        return PriceInfoDAO.insertPriceInfo(hb, stl);
    }

    public static boolean updatePriceInfo(PriceInfoBean hb, SettingLogsBean stl)
    {
        return PriceInfoDAO.updatePriceInfo(hb, stl);
    }

    public static boolean deletePriceInfo(Map<String, String> m, SettingLogsBean stl)
    {
        return PriceInfoDAO.deletePriceInfo(m, stl);
    }

    private static PriceInfoBean setOhterName(PriceInfoBean pb)
    {
        int marketId = pb.getMarketId();
        MarketTypeBean mtb = MarketTypeManager.getMarketTypeBean(marketId + "");
        pb.setMarketName(mtb.getMarketName());
        int typeId = pb.getTypeId();
        ProductTypeBean ptb = ProductTypeManager.getProductTypeBean(typeId + "");
        pb.setTypeName(ptb.getTypeName());
        int productId = pb.getProductId();
        ProductBean pdb = ProductManager.getProductBean(productId + "");
        pb.setProductName(pdb.getProductName());
        int userId = pb.getAddUser();
        String username = UserManager.getUserRealName(userId + "");
        pb.setUsername(username);
        return pb;
    }

    private static List<PriceInfoBean> setOhterName(List<PriceInfoBean> pbList)
    {
        List<PriceInfoBean> result = new ArrayList();
        if ((pbList != null) && (pbList.size() > 0)) {
            for (int i = 0; i < pbList.size(); i++)
            {
                PriceInfoBean pb = (PriceInfoBean)pbList.get(i);
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
                int productId = pb.getProductId();
                ProductBean pdb = ProductManager.getProductBean(productId + "");
                pb.setProductName(pdb.getProductName());
                int userId = pb.getAddUser();
                String username = UserManager.getUserRealName(userId + "");
                pb.setUsername(username);
                result.add(pb);
            }
        }
        return result;
    }

    public static void main(String[] args)
    {
        System.out.println(DateUtil.getDateBefore("2015-12-10", 10));
    }
}
