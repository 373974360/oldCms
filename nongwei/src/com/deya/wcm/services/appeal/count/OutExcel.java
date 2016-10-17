package com.deya.wcm.services.appeal.count;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

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
     * @param fileName		要输出的Excel文件名
     * @param head			Excel的表头，表头为一维数组
     * @param content		Excel的表内容
     */
    public void doOut(String fileName, String[] head, String[][] content)
    {
      try
      {
        File fl = new File(fileName);
        WritableWorkbook wwb = Workbook.createWorkbook(fl);
        WritableSheet ws = wwb.createSheet(this.SHEET, this.SHEETCOUNT);
        Label labelC = null;
        for (int k = 0; k < head.length; ++k)
        {
          labelC = new Label(k, 0, head[k]);

          ws.addCell(labelC);
        }

        int line = content.length;
        for (int i = 0; (i < line) && (content != null); ++i)
        {
          int row = content[i].length;
          for (int j = 0; (j < row) && (content[i] != null); ++j)
          {
            labelC = new Label(j, i + 1, content[i][j]);

            ws.addCell(labelC);
          }
        }

        wwb.write();
        wwb.close();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }

   /*
    public void doOut(String fileName, String head[], String content[][])
    {
        try
        {
        	//去最大节点深度
        	List<Integer> levels = new ArrayList<Integer>();
        	for(int i=0; i<content.length; i++)
        	{
        		if(content[i].length>8){
        			levels.add(Integer.parseInt(content[i][8].toString()));
        		}
        	}
        	Collections.sort(levels);
        	int maxLv = levels.get(levels.size()-1);
        	
            File fl = new File(fileName);
            WritableWorkbook wwb = Workbook.createWorkbook(fl);
            WritableSheet ws = wwb.createSheet(this.SHEET, this.SHEETCOUNT);
            Label labelC = null;
            for(int k = 0; k < head.length; k++)
            {
            	if(k==0)
            	{
            		labelC = new Label(k, 0, head[k]);
            		
            	}else{
            		labelC = new Label(k+maxLv, 0, head[k]);
                }
//                System.out.print(head[k] + "\t");
                ws.addCell(labelC);
            }
//            System.out.println();
            int line = content.length;
            for(int i = 0; i < line && content != null; i++)
            {
                int row = content[i].length;
                for(int j = 0; j < row-1 && content[i] != null; j++)
                {
                	if(j==0)
                	{
                		labelC = new Label(j+Integer.parseInt(content[i][8].toString()), i+1, content[i][j]);
                	}else
                	{
                		labelC = new Label(j+maxLv, i+1, content[i][j]);
                	}
//                    System.out.print(content[i][j] + "\t");
                    ws.addCell(labelC);
                }
//                System.out.println();
            }
            ws.mergeCells(0, 0, maxLv, 0);
            wwb.write();
            wwb.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
*/
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
    
    
    
    
    
    public static void main(String arr[]){
//    	String [] head=new String[]{"","业务类型1","业务类型1","业务类型1","业务类型2","业务类型2","业务类型2","业务类型3","业务类型3","业务类型3"};
//    	String[][] data = new String[6][10];
//    	data[0][0] = "";
//    	for(int i=0;i<3;i++){
//    		data[0][i*3+1] = "总数";
//    		data[0][i*3+2] = "黄牌";
//    		data[0][i*3+3] = "红牌";
//    	}
//    	for(int i=1;i<6;i++){
//    		data[i][0] = "111";
//    		data[i][1] = "222";
//    		data[i][2] = "333";
//    		data[i][3] = "444";
//    		data[i][4] = "444";
//    		data[i][5] = "444";
//    		data[i][6] = "444";
//    		data[i][7] = "444";
//    		data[i][8] = "444";
//    		data[i][9] = "444";
//    	}
//    	OutExcel oe=new OutExcel("业务统计表");
//    	oe.doOut3("C:\\aa.xls",head,data,3); 
    	
    } 
    
    
}