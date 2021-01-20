package com.chaohuilv.docwatermark.service.impl;

import com.chaohuilv.docwatermark.model.DocWaterMark;
import com.chaohuilv.docwatermark.service.DocWaterMarkService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * Created by null on 2020/1/20
 */
@Slf4j
public class PdfWaterMarkServiceImpl implements DocWaterMarkService {

    /**
     * pdf 添加水印
     * @param docWatermark
     */
    public void AddDocWaterMark(DocWaterMark docWatermark) {
        ByteArrayOutputStream bosOutFile = new ByteArrayOutputStream();
        PdfStamper stamper = null;
        try {
            PdfReader reader = new PdfReader(docWatermark.getInPutFilePath());
            stamper = new PdfStamper(reader, bosOutFile);
            int total = reader.getNumberOfPages() + 1;
            PdfContentByte content;
            BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
            PdfGState gs = new PdfGState();
            for (int i = 1; i < total; i++) {
                //content = stamper.getOverContent(i);// 在内容上方加水印
                content = stamper.getUnderContent(i);//在内容下方加水印
                gs.setFillOpacity(0.2f);
                content.beginText();
                content.setColorFill(new BaseColor(192, 192, 192));
                content.setFontAndSize(base, 50);
                content.setTextMatrix(70, 200);
                //文字水印内容生成
                if (!docWatermark.getTextWaterMarkName().equals("")){
                    content.showTextAligned(Element.ALIGN_CENTER, docWatermark.getTextWaterMarkName(), 300, 350, 55);
                }
                //图片水印内容生成
                if ( docWatermark.getImgWaterMarkFilePath() != null && !docWatermark.getImgWaterMarkFilePath().equals("") ){
                    Image image = Image.getInstance(docWatermark.getImgWaterMarkFilePath());
                    /*
                      img.setAlignment(Image.LEFT | Image.TEXTWRAP);
                      img.setBorder(Image.BOX); img.setBorderWidth(10);
                      img.setBorderColor(BaseColor.WHITE); img.scaleToFit(100072);//大小
                      img.setRotationDegrees(-30);//旋转
                     */
                    image.setBorderColor(new BaseColor(192, 192, 192));
                    image.setAbsolutePosition(200, 206); // set the first background
                    // image of the absolute
                    image.scaleToFit(200, 200);
                    content.addImage(image);
                }
                content.setColorFill(new BaseColor(0, 0, 0));
                content.setFontAndSize(base, 8);

                //页脚水印生成
                if(docWatermark.getWaterMarkNameFooter() != null && !docWatermark.getWaterMarkNameFooter().equals("")){
                    content.showTextAligned(Element.ALIGN_CENTER, docWatermark.getWaterMarkNameFooter(), 300, 10, 0);
                }
                content.endText();
            }
            stamper.close();
            reader.close();
        } catch (IOException e) {
            log.error("Pdf添加水印IOException={}",e);
            e.printStackTrace();
        } catch (DocumentException e) {
            log.error("Pdf添加水印DocumentException={}",e);
            e.printStackTrace();
        }finally {
            try {
                bosOutFile.writeTo(new FileOutputStream(new File(docWatermark.getOutPutFilePath())));
            } catch (IOException e) {
                log.error("Pdf添加水印IOException={}",e);
                e.printStackTrace();
            }
        }
    }

    /**
     * 移除水印
     * @param docWaterMark
     */
    public void RemoveWatermark(DocWaterMark docWaterMark){
        ByteArrayOutputStream bosOutFile = new ByteArrayOutputStream();
        //注意，这将破坏所有层的文档中，只有当你没有额外的层使用
        try {
            PdfReader reader =new PdfReader(docWaterMark.getInPutFilePath());
            //从文档中彻底删除的OCG组。
            //占位符变量
            reader.removeUnusedObjects();
            int pageCount = reader.getNumberOfPages();
            PRStream prStream=null;
            PdfDictionary curPage;
            PdfArray contentarray;
            //循环遍历每个页面
            for(int i=1; i<=pageCount; i++){
                //获取页面
                curPage = reader.getPageN(i);
                //获取原始内容
                contentarray = curPage.getAsArray(PdfName.CONTENTS);
                if(contentarray != null){
                    //循环遍历内容
                    for(int j=0; j<contentarray.size(); j++){
                        //获取原始字节流
                        prStream =(PRStream)contentarray.getAsStream(j);
                        // 0代表水印层
                        if (j == 0){
                            //给它零长度和零数据删除它
                            prStream.put(PdfName.LENGTH, new PdfNumber(0));
                            prStream.setData(new byte[0]);
                        }
                    }
                }
            }
            //写出来的内容
            Document doc = new Document(prStream.getReader().getPageSize(1));
            PdfCopy copy = new PdfCopy(doc, bosOutFile);
            doc.open();
            for (int j = 1; j <= pageCount; j++) {
                doc.newPage();
                PdfImportedPage page = copy.getImportedPage(prStream.getReader(), j);
                copy.addPage(page);
            }
            doc.close();
            reader.close();
        } catch (BadPdfFormatException e) {
            log.error("Pdf移除水印BadPdfFormatException={}",e);
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            log.error("Pdf移除水印FileNotFoundException={}",e);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("Pdf移除水印IOException={}",e);
            e.printStackTrace();
        } catch (DocumentException e) {
            log.error("Pdf移除水印DocumentException={}",e);
            e.printStackTrace();
        }finally {
            try {
                bosOutFile.writeTo(new FileOutputStream(docWaterMark.getOutPutFilePath()));
            } catch (IOException e) {
                log.error("Pdf移除水印IOException={}",e);
                e.printStackTrace();
            }
        }
    }

}
