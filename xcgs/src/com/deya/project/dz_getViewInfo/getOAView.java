package com.deya.project.dz_getViewInfo;

/**
 * Created with IntelliJ IDEA.
 * User: like
 * Date: 2015/12/17
 * Time: 15:35
 * Description:
 * Version: v1.0
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.jws.WebMethod;

import javax.jws.WebService;

import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

import com.deya.wcm.bean.cms.info.ArticleBean;
import com.deya.wcm.services.cms.info.InfoBaseManager;
import com.deya.wcm.services.cms.info.ModelUtil;
import lotus.domino.Database;
import lotus.domino.Document;
import lotus.domino.NotesFactory;
import lotus.domino.Session;
import lotus.domino.View;

public class getOAView {
    Database db;

    public  ArrayList returnOAView() {
        String serverName = "oa.xayc.cbpm";
        String dataBaseName = "weboa/weboardnew.nsf";
        String user = "admin544/xa544/cbpmc";
        String password = "11111111";
        String documentViewName = "vwAllBoardDocPortal";
        ArrayList result = new ArrayList();
        Map result2=new HashMap();
        Vector v =null;
        try {
            String ior = NotesFactory.getIOR(serverName);
            Session session =
                    NotesFactory.createSessionWithIOR(ior, user, password);
            System.out.println("---------------创建session成功！-----------------");
            View vw =
                    ConnectToDomino(session, serverName, dataBaseName, documentViewName);
            System.out.println("---------------获取视图成功！-----------------");
            if (vw != null) {
                Document doc = vw.getFirstDocument();
                v = doc.getColumnValues();

                result.add(addListIitem(v));
                int i=1;
                while (vw.getNextDocument(doc) != null) {
                    doc = vw.getNextDocument(doc);
                    v = doc.getColumnValues();
                    result.add(addListIitem(v));
                    i++;
                }
                for(int j = 0; i < 10; i++)
                {
                    System.out.println(result.get(j).toString());
                }
                if (doc != null)
                    doc.recycle();
                if (vw != null)
                    vw.recycle();
                if (db != null)
                    db.recycle();
                if (session != null)
                    session.recycle();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


    @WebMethod(exclude = true)
    public  View ConnectToDomino(Session session, String pServer,
                                 String pDbFileName, String pNotesView) {
        try {

            db = session.getDatabase(pServer, pDbFileName, false);
            System.out.println(db);

            if (db == null) {
                System.out.println("未找到数据库：" + pDbFileName + "\n");

                if (session != null)
                    session.recycle();
                return null;
            } else {
                System.out.println("成功打开数据库：" + pDbFileName);
                View vwResult = db.getView(pNotesView);
                System.out.println(pNotesView);
                if (vwResult == null) {
                    System.out.println("未找到视图：" + pNotesView);
                    if (db != null)
                        db.recycle();
                    if (session != null)
                        session.recycle();
                    return null;
                } else {
                    System.out.println("成功打开视图：" + pNotesView);
                    return vwResult;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @WebMethod(exclude = true)
    public  ArrayList addListIitem(Vector v) {

        int num=v.toArray().length;
        ArrayList result = new ArrayList();
        for(int i=0;i<num;i++) {
            result.add(v.toArray()[i].toString());
        }

        return result;
    }

    public boolean OAInfoToCMSInfo()
    {
        ArrayList viewInfo = returnOAView();
        if(viewInfo != null && viewInfo.size() > 0)
        {
            for(int i = 0; i < viewInfo.size(); i++)
            {
                insertData(viewInfo.get(i));
            }
            return true;
        }
        return true;
    }

    public void insertData(Object obj)
    {
        String title = "";
        String publish_time = "";
        ArticleBean article = new ArticleBean();
        article.setInput_user(1);
        article.setInfo_status(8);
        article.setModel_id(11);
        article.setApp_id("cms");
        article.setSite_id("CMSxayc");

        int id = InfoBaseManager.getNextInfoId();
        article.setId(id);
        article.setInfo_id(id);
        article.setCat_id(10030);
        article.setGk_index("33-44-55-66");
        article.setGk_duty_dept("西安印钞有限公司");
        article.setGk_input_dept("西安印钞有限公司");
        if(title != null && !"".equals(title) && !"null".equals(title))
        {
            article.setTitle(title);
        }
        else
        {
            article.setTitle("");
        }
        article.setHits(0);
        article.setSubtitle("");
        article.setTags("");
        article.setAuthor("");
        article.setSource("");
        article.setEditor("");
        article.setThumb_url("");
        article.setInfo_content("");
        article.setDescription("");
        article.setInput_dtime(publish_time);
        article.setReleased_dtime(publish_time);

        if(ModelUtil.insert(article, "article", null))
        {
            System.out.println("从视图里面添加信息成功！" );
        }else
        {
            System.out.println("从视图里面添加信息失败！" );
        }
    }
}


//3 标题 4 时间 6 url