package com.deya.project.salarySearch;

import com.cicro.iresource.service.remoteClient.Data;
import com.deya.util.DateUtil;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.services.control.domain.SiteDomainManager;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.jsoup.helper.DataUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: like
 * Date: 2016/3/1
 * Time: 14:15
 * Description:
 * Version: v1.0
 */
public class ExcelHandleUtil {

    private static String rootPath = "/deya/cms/vhosts/";

    public static boolean importSalaryData(String excel_path,String site_id,String date) {
        try {
            String domain = SiteDomainManager.getDefaultSiteDomainBySiteID(site_id) + "/ROOT";
            String filePath = rootPath + domain + excel_path;
            //System.out.println(filePath + "****************************");
            File e = new File(filePath);
            Workbook book = Workbook.getWorkbook(e);
            if(book != null) {
                Sheet sheet = book.getSheet(0);    //工资报表
                for(int i = 0; i < sheet.getRows(); ++i) {
                    Cell[] cell = sheet.getRow(i);
                    String temp = cell[0].getContents().trim();
                    if(temp == null || "".equals(temp))
                    {
                        continue;
                    }else{
                        SalaryUserBean sub = SalaryUserManager.getUserByCell(cell);
                        if(sub != null)
                        {
                            Map m = new HashMap<>();
                            m.put("typeId","1");
                            List<ExcelTitleBean> etList = ExcelTitleManager.getExcelTitleList(m);
                            int start = 4;
                            for(ExcelTitleBean etb : etList)
                            {
                                if(etb.getIsShow() == 1)
                                {
                                    //System.out.println("***************excel表头**************"+etb.getCname());
                                    //System.out.println("***************excel数据**************"+cell[start].getContents().trim());
                                    SalaryBean slb = new SalaryBean();
                                    slb.setId(PublicTableDAO.getIDByTableName("dz_salary"));
                                    slb.setUserId(sub.getId());
                                    slb.setSalaryDate(date);
                                    slb.setExcelTitleId(etb.getId());
                                    slb.setExcelData(cell[start].getContents().trim());
                                    slb.setAddTime(DateUtil.getCurrentDateTime());
                                    slb.setStatus("0");
                                    if(SalaryManager.insertSalary(slb,null))
                                    {
                                        start = start + 1;
                                    }
                                }
                            }
                        }
                    }
                }

                Sheet sheet2 = book.getSheet(1);    //津贴报表
                for(int i = 1; i < sheet2.getRows(); ++i) {
                    Cell[] cell = sheet2.getRow(i);
                    SalaryUserBean sub = SalaryUserManager.getUserByCell(cell);
                    if(sub != null)
                    {
                        Map m = new HashMap<>();
                        m.put("typeId","2");
                        List<ExcelTitleBean> etList = ExcelTitleManager.getExcelTitleList(m);
                        int start = 4;
                        for(ExcelTitleBean etb : etList)
                        {
                            if(etb.getIsShow() == 1)
                            {
                                SalaryBean slb = new SalaryBean();
                                slb.setId(PublicTableDAO.getIDByTableName("dz_salary"));
                                slb.setUserId(sub.getId());
                                slb.setSalaryDate(date);
                                slb.setExcelTitleId(etb.getId());
                                slb.setExcelData(cell[start].getContents().trim());
                                slb.setAddTime(DateUtil.getCurrentDateTime());
                                slb.setStatus("0");
                                if(SalaryManager.insertSalary(slb,null))
                                {
                                    start = start + 1;
                                }
                            }
                        }
                    }
                }
            }
            e.delete();
            return true;
        } catch (BiffException var17) {
            var17.printStackTrace();
            return false;
        } catch (IOException var18) {
            var18.printStackTrace();
            return false;
        }
    }

    /*
    public static String exportPriceInfo(Map<String, String> m) {
        List<PriceInfoBean> pxxxBmList = getPriceInfoList(m);
        if (pxxxBmList != null && pxxxBmList.size() > 0) {
            String[][] data = new String[pxxxBmList.size() + 1][12];
            data[0][0] = "培训名称";
            data[0][1] = "报名ID";
            data[0][2] = "姓名";
            data[0][3] = "性别";
            data[0][4] = "身份证号";
            data[0][5] = "工作单位";
            data[0][6] = "职务";
            data[0][7] = "联系方式";
            data[0][8] = "QQ";
            data[0][9] = "报名时间";
            data[0][10] = "是否住宿";
            data[0][11] = "备注";
            for (int i = 0; i < pxxxBmList.size(); i++) {
                PriceInfoBean pxxxBmBean = pxxxBmList.get(i);
                data[i + 1][0] = pxxxBmBean.getPxmc();
                data[i + 1][1] = pxxxBmBean.getBmid();
                data[i + 1][2] = pxxxBmBean.getXm();
                data[i + 1][3] = pxxxBmBean.getXb();
                data[i + 1][4] = pxxxBmBean.getSfzh();
                data[i + 1][5] = pxxxBmBean.getGzdw();
                data[i + 1][6] = pxxxBmBean.getZw();
                data[i + 1][7] = pxxxBmBean.getLxfs();
                data[i + 1][8] = pxxxBmBean.getQq();
                data[i + 1][9] = pxxxBmBean.getBmsj();
                data[i + 1][10] = pxxxBmBean.getSfzs();
                data[i + 1][11] = "";
            }
            String file_name = DateUtil.getCurrentDateTime("yyyyMMddHHmmss");
            JconfigUtil bc = JconfigFactory.getJconfigUtilInstance("bashConfig");
            String path = bc.getProperty("path", "", "manager_path").trim() + "/pxxxBm";
            String tempPath = path + "/" + DateUtil.getCurrentDate();
            File file2 = new File(FormatUtil.formatPath(tempPath));
            if (!file2.exists()) {
                file2.mkdirs();
            }
            String xFile = FormatUtil.formatPath(tempPath + "/" + file_name + ".xls");

            //OutExcel.deleteFile(path);

            OutExcel oe = new OutExcel("培训信息报名表");

            oe.doOut(xFile, data);
            return (tempPath + "/" + file_name + ".xls").replace("/cicro/wcm/vhosts/common", "");
        }
        return "";
    }
    */
}
