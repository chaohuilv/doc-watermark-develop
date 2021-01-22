package com.chaohuilv.docwatermark.service.impl;

import com.chaohuilv.docwatermark.model.DocWaterMark;
import com.chaohuilv.docwatermark.model.FTPModel;
import com.chaohuilv.docwatermark.service.DocWaterMarkService;
import com.chaohuilv.docwatermark.utils.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FTPServiceImplTest {

    @Autowired
    private FTPServiceImpl ftpUtilsService;
    @Test
    void connectServer() {
        Boolean isFtp = ftpUtilsService.connectServer();
        System.out.println(isFtp);
    }


    @Test
    void testAddDocWaterMark() {
        Boolean isFtp = ftpUtilsService.connectServer();
        System.out.println(isFtp);
        if(isFtp == true){
            String remoteFile = "\\Gedi_Sup_EvaluateTmp\\e0b705c3-28c1-44ca-ac32-7e9e1f0bc4cd\\6da42a53-e2ed-8560-7d33-b0fdd07eafca.pdf";
            String localFile = "D:\\桌面\\Pdf\\";
            //String localPath = ftpUtilsService.download(remoteFile,localFile);
            FTPModel ftpModel = new FTPModel();
            ftpModel.setRemoteFile(remoteFile);
            ftpModel.setLocalPath(localFile);
            String localPath = ftpUtilsService.download(ftpModel);

            DocWaterMark docWaterMark = new DocWaterMark();
            docWaterMark.setOutPutFilePath(localPath);
            docWaterMark.setInPutFilePath(localPath);
            docWaterMark.setTextWaterMarkName("公司机密文件，请保密。");
            docWaterMark.setWaterMarkNameFooter("2022-01-22 吕朝晖");
            DocWaterMarkService docWaterMarkService = new PdfWaterMarkServiceImpl();
            DocWaterMarkServiceImpl docWaterMarkService1 = new DocWaterMarkServiceImpl(docWaterMarkService);
            docWaterMarkService1.AddDocWaterMark(docWaterMark);

            ftpModel.setLocalPath(localPath);
            ftpUtilsService.upload(ftpModel);
            ftpUtilsService.closeClient();
            FileUtils.delAllFile(localPath);
        }
    }

    @Test
    void testRemoveWatermark() {
        Boolean isFtp = ftpUtilsService.connectServer();
        System.out.println(isFtp);
        if(isFtp == true){
            //1、ftp 下载附件
            String remoteFile = "\\Gedi_Sup_EvaluateTmp\\e0b705c3-28c1-44ca-ac32-7e9e1f0bc4cd\\6da42a53-e2ed-8560-7d33-b0fdd07eafca.pdf";
            String localFile = "D:\\桌面\\Pdf\\";
            FTPModel ftpModel = new FTPModel();
            ftpModel.setRemoteFile(remoteFile);
            ftpModel.setLocalPath(localFile);
            String localPath = ftpUtilsService.download(ftpModel);
            //2、本地去除水印
            DocWaterMark docWaterMark = new DocWaterMark();
            docWaterMark.setOutPutFilePath(localPath);
            docWaterMark.setInPutFilePath(localPath);
            DocWaterMarkService docWaterMarkService = new PdfWaterMarkServiceImpl();
            DocWaterMarkServiceImpl docWaterMarkService1 = new DocWaterMarkServiceImpl(docWaterMarkService);
            docWaterMarkService1.RemoveWatermark(docWaterMark);
            ftpModel.setLocalPath(localPath);
            //3、上传附件
            ftpUtilsService.upload(ftpModel);
            ftpUtilsService.closeClient();
            //4、删除本地文件
            FileUtils.delAllFile(localPath);

        }

    }
}