package com.deya.project.priceMonitor;

import com.deya.util.FormatUtil;
import com.deya.util.OutExcel;
import com.deya.util.jconfig.JconfigUtil;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.services.appeal.count.CountUtil;
import com.deya.wcm.services.control.domain.SiteDomainManager;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelHandleUtil
{
    private static String rootPath = "/deya/cms/vhosts/";

    public static boolean importPfLs(String excel_path, String userId, String site_id, String scName)
    {
        try
        {
            String domain = SiteDomainManager.getDefaultSiteDomainBySiteID(site_id) + "/ROOT";
            String filePath = rootPath + domain + excel_path;
            File e = new File(filePath);
            Workbook book = Workbook.getWorkbook(e);
            if (book != null)
            {
                Sheet sheet = book.getSheet(0);
                for (int i = 1; i < sheet.getRows(); i++)
                {
                    String price = sheet.getRow(i)[3].getContents().trim();
                    if ((price != null) && (!"".equals(price)))
                    {
                        String id = sheet.getRow(i)[2].getContents().trim();
                        String addTime = sheet.getRow(i)[4].getContents().trim();
                        String unit = sheet.getRow(i)[5].getContents().trim();
                        String isSellstr = sheet.getRow(i)[6].getContents().trim();
                        ProductBean ptb = ProductManager.getProductBean(id);
                        PriceInfoBean pib = new PriceInfoBean();
                        if (ptb != null)
                        {
                            pib.setMarketId(ptb.getMarketId());
                            pib.setTypeId(ptb.getTypeId());
                            pib.setProductId(ptb.getId());
                            pib.setPrice(Float.parseFloat(price));
                            pib.setUnit(unit);
                            pib.setStatus("0");
                            if ("批发".equals(isSellstr)) {
                                pib.setIsSell(0);
                            } else {
                                pib.setIsSell(1);
                            }
                            pib.setAddTime(addTime.replace("/", "-"));
                            pib.setAddUser(Integer.parseInt(userId));
                            pib.setScName(scName);
                            PriceInfoManager.insertPriceInfo(pib, null);
                        }
                    }
                }
            }
            e.delete();
            return true;
        }
        catch (BiffException var17)
        {
            var17.printStackTrace();
            return false;
        }
        catch (IOException var18)
        {
            var18.printStackTrace();
        }
        return false;
    }

    public static boolean importCdjg(String excel_path, String userId, String site_id, String scName)
    {
        try
        {
            String domain = SiteDomainManager.getDefaultSiteDomainBySiteID(site_id) + "/ROOT";
            String filePath = rootPath + domain + excel_path;
            File e = new File(filePath);
            Workbook book = Workbook.getWorkbook(e);
            if (book != null)
            {
                Sheet sheet = book.getSheet(0);
                for (int i = 3; i < sheet.getRows(); i++)
                {
                    String price = sheet.getRow(i)[3].getContents().trim();
                    if ((price != null) && (!"".equals(price)))
                    {
                        String id = sheet.getRow(i)[2].getContents().trim();
                        String location = "";
                        String addTime = sheet.getRow(i)[4].getContents().trim();
                        String unit = sheet.getRow(i)[5].getContents().trim();
                        ProductBean ptb = ProductManager.getProductBean(id);
                        PriceInfoBean pib = new PriceInfoBean();
                        if (ptb != null)
                        {
                            pib.setMarketId(ptb.getMarketId());
                            pib.setTypeId(ptb.getTypeId());
                            pib.setProductId(ptb.getId());
                            pib.setLocation(location);
                            pib.setPrice(Float.parseFloat(price));
                            pib.setUnit(unit);
                            pib.setAddTime(addTime.replace("/", "-"));
                            pib.setAddUser(Integer.parseInt(userId));
                            pib.setScName(scName);
                            pib.setStatus("0");
                            PriceInfoManager.insertPriceInfo(pib, null);
                        }
                    }
                }
            }
            e.delete();
            return true;
        }
        catch (BiffException var17)
        {
            var17.printStackTrace();
            return false;
        }
        catch (IOException var18)
        {
            var18.printStackTrace();
        }
        return false;
    }

    public static boolean importNzjg(String excel_path, String userId, String site_id, String scName)
    {
        try
        {
            String domain = SiteDomainManager.getDefaultSiteDomainBySiteID(site_id) + "/ROOT";
            String filePath = rootPath + domain + excel_path;
            File e = new File(filePath);
            Workbook book = Workbook.getWorkbook(e);
            if (book != null)
            {
                Sheet sheet = book.getSheet(0);
                for (int i = 3; i < sheet.getRows(); i++)
                {
                    String price = sheet.getRow(i)[4].getContents().trim();
                    if ((price != null) && (!"".equals(price)))
                    {
                        String id = sheet.getRow(i)[2].getContents().trim();
                        String productLevel = sheet.getRow(i)[3].getContents().trim();
                        String addTime = sheet.getRow(i)[5].getContents().trim();
                        String unit = sheet.getRow(i)[6].getContents().trim();
                        String sourceFrom = "";
                        if (sheet.getRow(i).length >= 8) {
                            sourceFrom = sheet.getRow(i)[7].getContents().trim();
                        }
                        ProductBean ptb = ProductManager.getProductBean(id);
                        PriceInfoBean pib = new PriceInfoBean();
                        if (ptb != null)
                        {
                            pib.setMarketId(ptb.getMarketId());
                            pib.setTypeId(ptb.getTypeId());
                            pib.setProductId(ptb.getId());
                            pib.setProductLevel(productLevel);
                            pib.setPrice(Float.parseFloat(price));
                            pib.setUnit(unit);
                            pib.setAddTime(addTime.replace("/", "-"));
                            pib.setAddUser(Integer.parseInt(userId));
                            pib.setScName(scName);
                            pib.setSourceFrom(sourceFrom);
                            pib.setStatus("0");
                            PriceInfoManager.insertPriceInfo(pib, null);
                        }
                    }
                }
            }
            e.delete();
            return true;
        }
        catch (BiffException var17)
        {
            var17.printStackTrace();
            return false;
        }
        catch (IOException var18)
        {
            var18.printStackTrace();
        }
        return false;
    }

    public static String exportInfoOutExcel(Map<String, String> m)
    {
        List<PriceInfoBean> pibList = PriceInfoManager.getAllPriceInfoListByDate(m);
        if ((pibList != null) && (pibList.size() > 0)) {
            try
            {
                String[] head = { "序号", "市场名称", "分类名称", "产品名称", "价格", "单位", "产地", "产品级别", "上市量", "交易量", "来源", "备注", "导入时间", "添加用户" };
                String[] fileStr = getFileUrl();

                int size = head.length;

                String[][] data = new String[pibList.size()][14];
                for (int i = 0; i < pibList.size(); i++)
                {
                    PriceInfoBean pib = (PriceInfoBean)pibList.get(i);
                    data[i][0] = String.valueOf(i + 1);
                    data[i][1] = String.valueOf(pib.getScName());
                    data[i][2] = String.valueOf(pib.getTypeName());
                    data[i][3] = String.valueOf(pib.getProductName());
                    data[i][4] = String.valueOf(pib.getPrice());
                    data[i][5] = String.valueOf(pib.getUnit());
                    data[i][6] = String.valueOf(pib.getLocation());
                    data[i][7] = String.valueOf(pib.getProductLevel());
                    data[i][8] = String.valueOf(pib.getLandings());
                    data[i][9] = String.valueOf(pib.getTradings());
                    data[i][10] = String.valueOf(pib.getSourceFrom());
                    data[i][11] = String.valueOf(pib.getComments());
                    data[i][12] = String.valueOf(pib.getAddTime());
                    data[i][13] = String.valueOf(pib.getUsername());
                }
                OutExcel oe = new OutExcel("站点信息统计表");
                oe.doOut(fileStr[0], head, data);
                return fileStr[1];
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }

    public static String[] getFileUrl()
    {
        String root_path = JconfigUtilContainer.bashConfig().getProperty("path", "", "manager_path");
        String path = FormatUtil.formatPath(root_path + "/project/exportFile/");
        CountUtil.deleteFile(path);


        String nowDate = CountUtil.getNowDayDate();
        String fileTemp2 = FormatUtil.formatPath(path + File.separator + nowDate);
        File file2 = new File(fileTemp2);
        if (!file2.exists()) {
            file2.mkdir();
        }
        String nowTime = CountUtil.getNowDayDateTime();
        String xls = nowTime + CountUtil.getEnglish(1) + ".xls";
        String xlsFile = fileTemp2 + File.separator + xls;
        String urlFile = "/sys/project/exportFile/" + nowDate + File.separator + xls;
        String[] str = { xlsFile, urlFile };

        return str;
    }
}
