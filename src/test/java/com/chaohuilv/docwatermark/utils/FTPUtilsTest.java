package com.chaohuilv.docwatermark.utils;

import com.chaohuilv.docwatermark.config.FTPConfig;
import com.chaohuilv.docwatermark.model.DocWaterMark;
import com.chaohuilv.docwatermark.service.DocWaterMarkService;
import com.chaohuilv.docwatermark.service.impl.DocWaterMarkServiceImpl;
import com.chaohuilv.docwatermark.service.impl.PdfWaterMarkServiceImpl;
import junit.framework.TestCase;

public class FTPUtilsTest extends TestCase {

    public void testConnectServer() {
        FTPConfig ftpConfig = new FTPConfig();
        ftpConfig.setFtpIp("10.150.172.49");
        ftpConfig.setFtpPort(21);
        ftpConfig.setFtpUserName("ftp");
        ftpConfig.setFtpPassword("666666");
        ftpConfig.setFtpEncode("UTF-8");
        FTPUtils ftpUtils = new FTPUtils();
        Boolean isFtp = ftpUtils.connectServer(ftpConfig);
        if(isFtp == true){
            String remoteFile = "\\Gedi_Sup_EvaluateTmp\\e0b705c3-28c1-44ca-ac32-7e9e1f0bc4cd\\b2aa0c52-fbef-7bf4-b51b-f0620988acfb.pdf";
            String localFile = "D:\\桌面\\Pdf\\";
            String localPath = ftpUtils.download(remoteFile,localFile);
            DocWaterMark docWaterMark = new DocWaterMark();
            docWaterMark.setOutPutFilePath(localPath);
            docWaterMark.setInPutFilePath(localPath);
            docWaterMark.setTextWaterMarkName("公司机密文件，请保密。");
            docWaterMark.setWaterMarkNameFooter("2022-01-20 吕朝晖");
            DocWaterMarkService docWaterMarkService = new PdfWaterMarkServiceImpl();
            DocWaterMarkServiceImpl docWaterMarkService1 = new DocWaterMarkServiceImpl(docWaterMarkService);
            docWaterMarkService1.AddDocWaterMark(docWaterMark);
            ftpUtils.upload(remoteFile,localPath);
            FileUtils.delAllFile(localPath);
        }

    }
}