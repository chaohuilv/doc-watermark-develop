package com.chaohuilv.docwatermark.service.impl;

import com.chaohuilv.docwatermark.model.DocWaterMark;
import junit.framework.TestCase;

public class PdfWaterMarkServiceImplTest extends TestCase {

    public void testAddDocWaterMark() {
        DocWaterMark docWaterMark = new DocWaterMark();
        docWaterMark.setOutPutFilePath("D:\\桌面\\Pdf\\ces12.pdf");
        docWaterMark.setInPutFilePath("D:\\桌面\\Pdf\\ces12.pdf");
        docWaterMark.setImgWaterMarkFilePath("D:\\桌面\\Pdf\\timg.jfif");
        docWaterMark.setTextWaterMarkName("公司机密文件，请保密。");
        docWaterMark.setWaterMarkNameFooter("2022-01-20 吕朝晖");
        PdfWaterMarkServiceImpl docWaterMarkService = new PdfWaterMarkServiceImpl();
        docWaterMarkService.AddDocWaterMark(docWaterMark);
    }

    public void testRemoveWatermark(){
        DocWaterMark docWaterMark = new DocWaterMark();
        docWaterMark.setOutPutFilePath("D:\\桌面\\Pdf\\ces12.pdf");
        docWaterMark.setInPutFilePath("D:\\桌面\\Pdf\\ces12.pdf");
        PdfWaterMarkServiceImpl docWaterMarkService = new PdfWaterMarkServiceImpl();
        docWaterMarkService.RemoveWatermark(docWaterMark);
    }

}