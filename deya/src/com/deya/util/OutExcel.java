package com.deya.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class OutExcel
{

    private String SHEET;		//工作表名
    private int SHEETCOUNT;		//第N个工作表
     
    //使用默认值（常用）
    public OutExcel() 
    {
        this.SHEET="Sheet";
        this.SHEETCOUNT=0;
    }

    //指定工作表名（常用）
    public OutExcel(String sheet)
    {
        this.SHEET=sheet;
        this.SHEETCOUNT=0;
    }

    //指定工作表名和工作表的位置
    public OutExcel(String sheet, int count)
    {
    	this.SHEET=sheet;
        this.SHEETCOUNT=count;
    }

    /**
     * @param fileName	Excel文件名
     * @param excel		表头和表内容都包含在其中
     */
    public void doOut(String fileName, String excel[][])
    {
        try
        {
            File fl = new File(fileName);
            WritableWorkbook wwb = Workbook.createWorkbook(fl);
            WritableSheet ws = wwb.createSheet(this.SHEET, this.SHEETCOUNT);           
            Label labelC = null;
            int line = excel.length;
            for(int i = 0; i < line && excel != null; i++)
            {
                int row = excel[i].length;
                for(int j = 0; j < row && excel[i] != null; j++)
                {
                    labelC = new Label(j, i, excel[i][j]);
//                    System.out.print(excel[i][j] + "\t");
                    ws.addCell(labelC);
                }
//                System.out.println();
            }
            wwb.write();
            wwb.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * @param fileName	Excel文件名
     * @param excel		表头和表内容都包含在其中
     * @param cell_start		第一列合并开始数
     * @param cell_end		第一列合并结束数
     */
    public void doOut(String fileName, String excel[][],int cell_start,int cell_end)
    {
        try
        {
            File fl = new File(fileName);
            WritableWorkbook wwb = Workbook.createWorkbook(fl);
            WritableSheet ws = wwb.createSheet(this.SHEET, this.SHEETCOUNT);
            ws.mergeCells(0,cell_start,0,cell_end);
            Label labelC = null;
            int line = excel.length;
            for(int i = 0; i < line && excel != null; i++)
            {
                int row = excel[i].length;
                for(int j = 0; j < row && excel[i] != null; j++)
                {
                    labelC = new Label(j, i, excel[i][j]);
//                    System.out.print(excel[i][j] + "\t");
                    ws.addCell(labelC);
                }
//                System.out.println();
            }
            wwb.write();
            wwb.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @param fileName		要输出的Excel文件名
     * @param head			Excel的表头，表头为一维数组
     * @param content		Excel的表内容
     */
    public void doOut(String fileName, String head[], String content[][])
    {
        try
        {
            File fl = new File(fileName);
            WritableWorkbook wwb = Workbook.createWorkbook(fl);
            WritableSheet ws = wwb.createSheet(this.SHEET, this.SHEETCOUNT);
            Label labelC = null;
            for(int k = 0; k < head.length; k++)
            {
                labelC = new Label(k, 0, head[k]);
//                System.out.print(head[k] + "\t");
                ws.addCell(labelC);
            }
//            System.out.println();
            int line = content.length;
            for(int i = 0; i < line && content != null; i++)
            {
                int row = content[i].length;
                for(int j = 0; j < row && content[i] != null; j++)
                {
                    labelC = new Label(j, i+1, content[i][j]);
//                    System.out.print(content[i][j] + "\t");
                    ws.addCell(labelC);
                }
//                System.out.println();
            }
            wwb.write();
            wwb.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    /**
     * @param fileName		要输出的Excel文件名
     * @param head			Excel的表头，表头为一维数组
     * @param Map map       头的描述
     * @param content		Excel的表内容
     */
    public void doOut(String fileName, String head[], String content[][],Map map)
    {
        try
        {
            File fl = new File(fileName);
            WritableWorkbook wwb = Workbook.createWorkbook(fl);
            WritableSheet ws = wwb.createSheet(this.SHEET, this.SHEETCOUNT);
            Label labelC = null;
            
            ws.mergeCells(1, 0, head.length-1, 0);
            int mun = 0;
            Iterator it = map.keySet().iterator();
            while(it.hasNext()){
            	String key = (String)it.next();
            	String value = (String)map.get(key);
            	labelC = new Label(0, mun, key);
            	ws.addCell(labelC);
            	labelC = new Label(1, mun, value);
            	ws.addCell(labelC);
            	mun++;
            }
            
            
            for(int k = 0; k < head.length; k++)
            {
                labelC = new Label(k, mun, head[k]);
//                System.out.print(head[k] + "\t");
                ws.addCell(labelC);
            }
//            System.out.println();
            int line = content.length;
            for(int i = 0; i < line && content != null; i++)
            {
                int row = content[i].length;
                for(int j = 0; j < row && content[i] != null; j++)
                {
                    labelC = new Label(j, i+mun+1, content[i][j]);
//                    System.out.print(content[i][j] + "\t");
                    ws.addCell(labelC);
                }
//                System.out.println();
            }
            wwb.write();
            wwb.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @param fileName		要输出的Excel文件名
     * @param head			Excel的表头，表头为二维数组
     * @param content		Excel的内容
     */
    public void doOut(String fileName, String head[][], String content[][])
    {
        try
        {
            File fl = new File(fileName);
            WritableWorkbook wwb = Workbook.createWorkbook(fl);
            WritableSheet ws = wwb.createSheet(this.SHEET, this.SHEETCOUNT);
            Label labelC = null;
            for(int k = 0; k < head[0].length; k++)
            {
                labelC = new Label(k, 0, head[0][k]);
//                System.out.print(head[0][k] + "\t");
                ws.addCell(labelC);
            }
//            System.out.println();
            int line = content.length;
            for(int i = 0; i < line && content != null; i++)
            {
                int row = content[i].length;
                for(int j = 0; j < row && content[i] != null; j++)
                {
                    labelC = new Label(j, i+1, content[i][j]);
//                    System.out.print(content[i][j] + "\t");
                    ws.addCell(labelC);
                }
//                System.out.println();
            }
            wwb.write();
            wwb.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    
    /**
     * @param fileName		要输出的Excel文件名
     * @param head			Excel的表头，表头为一维数组
     * @param int row       要合并的单元格行数
     * @param content		Excel的表内容
     */
    public void doOut2(String fileName, String head[], String content[][],int row2)
    {
        try
        {
            File fl = new File(fileName);
            WritableWorkbook wwb = Workbook.createWorkbook(fl);
            WritableSheet ws = wwb.createSheet(this.SHEET, this.SHEETCOUNT);
            //sheetWrite.mergeCells(column, row, column1, row1);
            

            int totalRows = content.length;//数据总行数
            for(int i=0;i<totalRows/row2;i++){
            	if(i==0){
            		ws.mergeCells(0, 1, 0, row2);
            	}else{
                    ws.mergeCells(0,i*row2+1, 0, i*row2+row2);
            	}
            }
            
            Label labelC = null;
            for(int k = 0; k < head.length; k++)
            {
                labelC = new Label(k, 0, head[k]);
//                System.out.print(head[k] + "\t");
                ws.addCell(labelC);
            }
//            System.out.println();
            int line = content.length;
            for(int i = 0; i < line && content != null; i++)
            {
                int row = content[i].length;
                for(int j = 0; j < row && content[i] != null; j++)
                {
                    labelC = new Label(j, i+1, content[i][j]);
//                    System.out.print(content[i][j] + "\t");
                    ws.addCell(labelC);
                }
//                System.out.println();
            }
            wwb.write();
            wwb.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    /**
     * @param fileName		要输出的Excel文件名
     * @param head			Excel的表头，表头为一维数组
     * @param int column1       要合并的单元格行数
     * @param content		Excel的表内容
     */
    public void doOut3(String fileName, String head[], String content[][],int col2)
    {
        try
        {
            File fl = new File(fileName);
            WritableWorkbook wwb = Workbook.createWorkbook(fl);
            WritableSheet ws = wwb.createSheet(this.SHEET, this.SHEETCOUNT);
            //sheetWrite.mergeCells(column, row, column1, row1);
            ws.mergeCells(0, 0, 0, 1);
            
            int totalCols = head.length-1;//数据总列数-1
            for(int i=0;i<totalCols/col2;i++){
            	if(i==0){
            		ws.mergeCells(1, 0, col2, 0);
            	}else{
                    ws.mergeCells(i*col2+1,0, i*col2+col2, 0);
            	}
            }
            
            Label labelC = null;
            for(int k = 0; k < head.length; k++)
            {
                labelC = new Label(k, 0, head[k]);
//                System.out.print(head[k] + "\t");
                ws.addCell(labelC);
            }
//            System.out.println();
            int line = content.length;
            for(int i = 0; i < line && content != null; i++)
            {
                int row = content[i].length;
                for(int j = 0; j < row && content[i] != null; j++)
                {
                    labelC = new Label(j, i+1, content[i][j]);
//                    System.out.print(content[i][j] + "\t");
                    ws.addCell(labelC);
                }
//                System.out.println();
            }
            wwb.write();
            wwb.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    
    
     ////////   lisp  ///////////////////
    public void doOutDep(String fileName, String excel[][],int column1)
    {
        try
        {
            File fl = new File(fileName);
            WritableWorkbook wwb = Workbook.createWorkbook(fl);
            WritableSheet ws = wwb.createSheet(this.SHEET, this.SHEETCOUNT);
            //sheetWrite.mergeCells(column, row, column1, row1);
            ws.mergeCells(1, 0, column1-1, 0);
            ws.mergeCells(0,1,0,2);
            ws.mergeCells(1,1,column1-1,1);
            ws.mergeCells(1,2,column1-1,2);
            Label labelC = null;
            int line = excel.length;
            for(int i = 0; i < line && excel != null; i++)
            {
                int row = excel[i].length;
                for(int j = 0; j < row && excel[i] != null; j++)
                {
                    labelC = new Label(j, i, excel[i][j]);
//                    System.out.print(excel[i][j] + "\t");
                    ws.addCell(labelC);
                }
//                System.out.println();
            }
            wwb.write();
            wwb.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    public void doOutB(String fileName, String excel[][],int column1)
    {
        try
        {
            File fl = new File(fileName);
            WritableWorkbook wwb = Workbook.createWorkbook(fl);
            WritableSheet ws = wwb.createSheet(this.SHEET, this.SHEETCOUNT);
            //sheetWrite.mergeCells(column, row, column1, row1);
            ws.mergeCells(1, 0, column1-1, 0);
            ws.mergeCells(1,1,column1-1,1);   
            Label labelC = null;
            int line = excel.length;
            for(int i = 0; i < line && excel != null; i++)
            {
                int row = excel[i].length;
                for(int j = 0; j < row && excel[i] != null; j++)
                {
                    labelC = new Label(j, i, excel[i][j]);
//                    System.out.print(excel[i][j] + "\t");
                    ws.addCell(labelC);
                }
//                System.out.println();
            }
            wwb.write();
            wwb.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    
    public void doOutDepYear(String fileName, String excel[][],int column1,int row2)
    {
        try
        {
            File fl = new File(fileName);
            WritableWorkbook wwb = Workbook.createWorkbook(fl);
            WritableSheet ws = wwb.createSheet(this.SHEET, this.SHEETCOUNT);
            
            //sheetWrite.mergeCells(column, row, column1, row1);
            ws.mergeCells(1, 0, column1-1, 0);
            ws.mergeCells(0,1,0,2);
            ws.mergeCells(1,1,column1-1,1);
            ws.mergeCells(1,2,column1-1,2);
            
            int totalRows = excel.length;//数据总行数
            for(int i=0;i<totalRows-4/row2;i++){
            	if(i==0){
            		ws.mergeCells(0, 1+3, 0, row2+3);
            	}else{
                    ws.mergeCells(0,i*row2+1+3, 0, i*row2+row2+3);
            	}
            }
   
            Label labelC = null;
            int line = excel.length;
            for(int i = 0; i < line && excel != null; i++)
            {
                int row = excel[i].length;
                for(int j = 0; j < row && excel[i] != null; j++)
                {
                    labelC = new Label(j, i, excel[i][j]);
//                    System.out.print(excel[i][j] + "\t");
                    ws.addCell(labelC);
                }
//                System.out.println();
            }
            wwb.write();
            wwb.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    
    public void doOutDepRedYellow(String fileName, String excel[][],int column1,int col2)
    {
        try
        { 
            File fl = new File(fileName);
            WritableWorkbook wwb = Workbook.createWorkbook(fl);
            WritableSheet ws = wwb.createSheet(this.SHEET, this.SHEETCOUNT);
            
            //sheetWrite.mergeCells(column, row, column1, row1);
            ws.mergeCells(1, 0, column1-1, 0);
            ws.mergeCells(1,1,column1-1,1);
            ws.mergeCells(0, 0+2, 0, 1+2);
            
            int totalCols = excel[0].length-1;//数据总列数-1
            for(int i=0;i<totalCols/col2;i++){
            	if(i==0){
            		ws.mergeCells(1, 0+2, col2, 0+2);
            	}else{
                    ws.mergeCells(i*col2+1,0+2, i*col2+col2, 0+2);
            	}
            }
   
            Label labelC = null;
            int line = excel.length;
            for(int i = 0; i < line && excel != null; i++)
            {
                int row = excel[i].length;
                for(int j = 0; j < row && excel[i] != null; j++)
                {
                    labelC = new Label(j, i, excel[i][j]);
//                    System.out.print(excel[i][j] + "\t");
                    ws.addCell(labelC);
                }
//                System.out.println();
            }
            wwb.write();
            wwb.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    /**
     * 删除今天以前的文件夹 
     * @param String path 文件夹存放路径
     * @return void 
     */
	public static void deleteFile(String path){
		try{
			File fileRoot = new File(path);
			if(fileRoot.exists()){
				File fileAll[] = fileRoot.listFiles();
				for(File file : fileAll){
					if(file.isDirectory()){
						if(file.getName().startsWith("2")){
							String nowDate = com.deya.util.DateUtil.getCurrentDate();
							if(!file.getName().equals(nowDate)){
								com.deya.util.io.FileOperation.deleteDir(file.getPath());
							}
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	
    /**
     * @param fileName		要输出的Excel文件名
     * @param head			Excel的表头，表头为一维数组
     * @param content		Excel的表内容
     * @param int countnum	tree的深度
     */
    public void doOutTree(String fileName,String head[], String content[][],int countnum)
    {
        try
        { 
        	
        	File fl = new File(fileName);
            WritableWorkbook wwb = Workbook.createWorkbook(fl);
            WritableSheet ws = wwb.createSheet(this.SHEET, this.SHEETCOUNT);
            
            if(content==null){
            	return;
            }
            int clos = content[0].length;
            //System.out.println("clos===" + clos);
            ws.mergeCells(0, 0, countnum-1, 0);
            int num = 1;
            Label labelC = null;
            for(int k = 0; k < clos; k++)
            {
            	if(k==0){
            		labelC = new Label(0, 0, head[k]);
                    ws.addCell(labelC);
            	}else if(k>=countnum){
            		//System.out.println(k);
            		labelC = new Label(k, 0, head[num]);
            		num++;
                    ws.addCell(labelC);
            	}
                
            }
            int line = content.length;
            for(int i = 0; i < line && content != null; i++)
            {
                int row = content[i].length;
                for(int j = 0; j < row && content[i] != null; j++)
                {
                    labelC = new Label(j, i+1, content[i][j]);
                    ws.addCell(labelC);
                }
            }
            wwb.write();
            wwb.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void main(String arr[]){
//    	String [] head=new String[]{"业务类型1","业务类型2","业务类型3"};
//    	String[][] data = new String[6][3];
//    	for(int i=0;i<6;i++){
//    		data[i][0] = "111";
//    		data[i][1] = "222";
//    		data[i][2] = "333";
//    	}
//    	OutExcel oe=new OutExcel("业务统计表");
//    	
//    	Map map = new HashMap();
//    	map.put("时间范围", "2010-03-03----2012-03-02");
//    	map.put("时间范围2", "2010-03-03----2012-03-02");
//    	  
//    	oe.doOut("C:\\aa.xls",head,data,map); 
    	
    	String strTree = "[{\"b_count\": 0, \"z_count\": 28, \"site_id\": \"\", \"is_leaf\": false, \"site_name\": \"\", \"cat_name\": \"\u6982\u51b5\u4fe1\u606f\", \"cat_id\": 1834, \"update_time\": \"\", \"y_count\": 0, \"javaClass\": \"com.deya.wcm.bean.zwgk.count.GKCountBean\", \"app_id\": \"\", \"info_count\": 28, \"child_list\": [{\"b_count\": 0, \"z_count\": 13, \"site_id\": \"\", \"is_leaf\": true, \"site_name\": \"\", \"cat_name\": \"\u673a\u6784\u804c\u80fd\", \"cat_id\": 1835, \"update_time\": \"\", \"y_count\": 0, \"javaClass\": \"com.deya.wcm.bean.zwgk.count.GKCountBean\", \"app_id\": \"\", \"info_count\": 13, \"child_list\": null}, {\"b_count\": 0, \"z_count\": 6, \"site_id\": \"\", \"is_leaf\": true, \"site_name\": \"\", \"cat_name\": \"\u9886\u5bfc\u4fe1\u606f\", \"cat_id\": 1836, \"update_time\": \"\", \"y_count\": 0, \"javaClass\": \"com.deya.wcm.bean.zwgk.count.GKCountBean\", \"app_id\": \"\", \"info_count\": 6, \"child_list\": null}, {\"b_count\": 0, \"z_count\": 3, \"site_id\": \"\", \"is_leaf\": true, \"site_name\": \"\", \"cat_name\": \"\u5185\u8bbe\u79d1\u5ba4\", \"cat_id\": 1837, \"update_time\": \"\", \"y_count\": 0, \"javaClass\": \"com.deya.wcm.bean.zwgk.count.GKCountBean\", \"app_id\": \"\", \"info_count\": 3, \"child_list\": null}, {\"b_count\": 0, \"z_count\": 6, \"site_id\": \"\", \"is_leaf\": false, \"site_name\": \"\", \"cat_name\": \"\u5730\u533a\u6982\u51b5\", \"cat_id\": 1838, \"update_time\": \"\", \"y_count\": 0, \"javaClass\": \"com.deya.wcm.bean.zwgk.count.GKCountBean\", \"app_id\": \"\", \"info_count\": 6, \"child_list\": [{\"b_count\": 0, \"z_count\": 13, \"site_id\": \"\", \"is_leaf\": true, \"site_name\": \"\", \"cat_name\": \"\u673a\u6784\u804c\u80fd1\", \"cat_id\": 1835, \"update_time\": \"\", \"y_count\": 0, \"javaClass\": \"com.deya.wcm.bean.zwgk.count.GKCountBean\", \"app_id\": \"\", \"info_count\": 13, \"child_list\": null}, {\"b_count\": 0, \"z_count\": 6, \"site_id\": \"\", \"is_leaf\": true, \"site_name\": \"\", \"cat_name\": \"\u9886\u5bfc\u4fe1\u606f1\", \"cat_id\": 1836, \"update_time\": \"\", \"y_count\": 0, \"javaClass\": \"com.deya.wcm.bean.zwgk.count.GKCountBean\", \"app_id\": \"\", \"info_count\": 6, \"child_list\": null}, {\"b_count\": 0, \"z_count\": 3, \"site_id\": \"\", \"is_leaf\": true, \"site_name\": \"\", \"cat_name\": \"\u5185\u8bbe\u79d1\u5ba4\", \"cat_id\": 1837, \"update_time\": \"\", \"y_count\": 0, \"javaClass\": \"com.deya.wcm.bean.zwgk.count.GKCountBean\", \"app_id\": \"\", \"info_count\": 3, \"child_list\": null}, {\"b_count\": 0, \"z_count\": 6, \"site_id\": \"\", \"is_leaf\": true, \"site_name\": \"\", \"cat_name\": \"\u5730\u533a\u6982\u51b51\", \"cat_id\": 1838, \"update_time\": \"\", \"y_count\": 0, \"javaClass\": \"com.deya.wcm.bean.zwgk.count.GKCountBean\", \"app_id\": \"\", \"info_count\": 6, \"child_list\": null}]}]},{\"b_count\": 0, \"z_count\": 10, \"site_id\": \"\", \"is_leaf\": false, \"site_name\": \"\", \"cat_name\": \"\u6cd5\u89c4\u516c\u6587\", \"cat_id\": 1839, \"update_time\": \"\", \"y_count\": 0, \"javaClass\": \"com.deya.wcm.bean.zwgk.count.GKCountBean\", \"app_id\": \"\", \"info_count\": 10, \"child_list\": [{\"b_count\": 0, \"z_count\": 10, \"site_id\": \"\", \"is_leaf\": true, \"site_name\": \"\", \"cat_name\": \"\u6cd5\u89c4\", \"cat_id\": 1840, \"update_time\": \"\", \"y_count\": 0, \"javaClass\": \"com.deya.wcm.bean.zwgk.count.GKCountBean\", \"app_id\": \"\", \"info_count\": 10, \"child_list\": null}, {\"b_count\": 0, \"z_count\": 0, \"site_id\": \"\", \"is_leaf\": true, \"site_name\": \"\", \"cat_name\": \"\u6cd5\u89c4\", \"cat_id\": 1841, \"update_time\": \"\", \"y_count\": 0, \"javaClass\": \"com.deya.wcm.bean.zwgk.count.GKCountBean\", \"app_id\": \"\", \"info_count\": 0, \"child_list\": null}, {\"b_count\": 0, \"z_count\": 0, \"site_id\": \"\", \"is_leaf\": true, \"site_name\": \"\", \"cat_name\": \"\u516c\u6587\", \"cat_id\": 1842, \"update_time\": \"\", \"y_count\": 0, \"javaClass\": \"com.deya.wcm.bean.zwgk.count.GKCountBean\", \"app_id\": \"\", \"info_count\": 0, \"child_list\": null}]},{\"b_count\": 0, \"z_count\": 3, \"site_id\": \"\", \"is_leaf\": true, \"site_name\": \"\", \"cat_name\": \"\u63a8\u9001\u6d4b\u8bd5\", \"cat_id\": 2039, \"update_time\": \"\", \"y_count\": 0, \"javaClass\": \"com.deya.wcm.bean.zwgk.count.GKCountBean\", \"app_id\": \"\", \"info_count\": 3, \"child_list\": null}]";
    	JSONArray jsonArray = JSONArray.fromObject(strTree);  
        Iterator it = jsonArray.iterator();
        Map map = new HashMap();
        List list = new ArrayList();
        map.put("numcount", 1);
        while(it.hasNext()){
        	JSONObject jsonObject = (JSONObject)it.next();
        	int mun = 1;
        	doOutTreeBean(jsonObject,mun,map,list);
        }
        System.out.println("numcount = " + map.get("numcount").toString());
    	System.out.println("list.size()==" + list.size());
    	
    	List listResult = new ArrayList();
    	int numcount = Integer.valueOf(map.get("numcount").toString());
    	for(int i=0;i<list.size();i++){
    		Map mapO = (Map)list.get(i);
    		int numO = Integer.valueOf(mapO.get("num").toString());
            String cat_name = mapO.get("cat_name").toString();
    		List listCur = new ArrayList();
    		for(int j=1;j<=numcount;j++){
    			if(j==numO){
    				listCur.add(cat_name);
    			}else{
    				listCur.add("");
    			}
    		}
    		listCur.add("10");
    		listCur.add("10");
    		listCur.add("10");
    		listCur.add("10");
    		listResult.add(listCur);
    	}
    	String[][] data = new String[listResult.size()][numcount+4];
    	for(int i=0;i<listResult.size();i++){
    		List listCur = (List)listResult.get(i);
    		for(int j=0;j<numcount+4;j++){
    			data[i][j] = (String)listCur.get(j);
    		}
    		
    	}
    	
    	String [] head=new String[]{"栏目名称","信息总数","主动公开数目","依申请公开数目","不公开数目"};
    	OutExcel oe=new OutExcel("业务统计表");
    	oe.doOutTree("C:\\aa.xls",head,data,numcount); 
    } 
    
    
    public static void setNumCount(Map map,int num){
    	if(num>Integer.valueOf(map.get("numcount").toString())){
    		map.put("numcount", num);
    	}
    }
    
    public static void doOutTreeBean(JSONObject jsonObject,int num,Map map,List list){
    	String is_leaf = String.valueOf(jsonObject.get("is_leaf"));
    	if(is_leaf.equals("true")){
    		for(int i=1;i<num;i++){
    			System.out.print("  ");
    		}
    		System.out.print(String.valueOf(num)+jsonObject.get("cat_name"));
    		Map map1 = new HashMap();
    		map1.put("num", num);
    		map1.put("cat_name", jsonObject.get("cat_name"));
    		list.add(map1);
    		//System.out.println();
    	}else{
    		for(int i=1;i<num;i++){
    			System.out.print("  ");
    		}
    		System.out.print(String.valueOf(num)+jsonObject.get("cat_name"));
    		Map map1 = new HashMap();
    		map1.put("num", num);
    		map1.put("cat_name", jsonObject.get("cat_name"));
    		list.add(map1);
    		//System.out.println();
    		JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("child_list"));
    		if(jsonArray!=null && !"".equals(jsonArray) && jsonArray.size()>0){
    			num++;
    			setNumCount(map,num);
    			Iterator it = jsonArray.iterator();
    	        while(it.hasNext()){  
    	        	JSONObject jsonObject2 = (JSONObject)it.next();
    	        	//System.out.println(jsonObject2);
    	        	if(jsonObject2!=null){
    	        		doOutTreeBean(jsonObject2,num,map,list);
    	        	}
    	        }
    		}
    	}
    }
    
    
}