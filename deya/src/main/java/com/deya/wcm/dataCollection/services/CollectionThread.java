package com.deya.wcm.dataCollection.services;

import com.deya.wcm.dataCollection.util.FormatString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class CollectionThread extends Thread {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private String id = "";

    public CollectionThread(String id) {
        this.id = id;
    }


    public void run() {
        logger.info("CollDateThreadStart.........start....");
        CollectionDataManager.CollectionData(this.id);
//        String filePath = FormatString.getManagerPath();
//        File file = new File(filePath + File.separator + this.id + ".txt");
        try {
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
//            bw.write("collOver");
//            bw.flush();
//            bw.close();
        } finally {
            CaijiBean.removeMapString(this.id);
            logger.info("-----------coll  over-------------" + CaijiBean.map.toString());
        }
    }
}
