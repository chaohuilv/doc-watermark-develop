package com.chaohuilv.docwatermark.service.impl;

import com.chaohuilv.docwatermark.model.DocWaterMark;
import com.chaohuilv.docwatermark.service.DocWaterMarkService;
import junit.framework.TestCase;

public class DocWaterMarkServiceImplTest extends TestCase {

    public void testAddDocWaterMark() {
        DocWaterMark docWaterMark = new DocWaterMark();
        docWaterMark.setOutPutFilePath("D:\\桌面\\Pdf\\ces12.pdf");
        docWaterMark.setInPutFilePath("D:\\桌面\\Pdf\\ces12.pdf");
        docWaterMark.setImgWaterMarkFilePath("D:\\桌面\\Pdf\\timg.jfif");
        docWaterMark.setTextWaterMarkName("公司机密文件，请保密。");
        docWaterMark.setWaterMarkNameFooter("2022-01-20 吕朝晖");
        DocWaterMarkService docWaterMarkService = new PdfWaterMarkServiceImpl();
        DocWaterMarkServiceImpl docWaterMarkService1 = new DocWaterMarkServiceImpl(docWaterMarkService);
        docWaterMarkService1.AddDocWaterMark(docWaterMark);
    }

    public void testRemoveWatermark() {
        DocWaterMark docWaterMark = new DocWaterMark();
        docWaterMark.setOutPutFilePath("D:\\桌面\\Pdf\\ces12.pdf");
        docWaterMark.setInPutFilePath("D:\\桌面\\Pdf\\ces12.pdf");
        DocWaterMarkService docWaterMarkService = new PdfWaterMarkServiceImpl();
        DocWaterMarkServiceImpl docWaterMarkService1 = new DocWaterMarkServiceImpl(docWaterMarkService);
        docWaterMarkService1.RemoveWatermark(docWaterMark);
    }
}