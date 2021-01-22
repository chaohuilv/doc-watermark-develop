package com.chaohuilv.docwatermark.model;

import com.itextpdf.text.pdf.BaseFont;
import lombok.Data;

/**
 * Created by null on 2020/1/20
 */
@Data
public class DocWaterMark {

    private String outPutFilePath; //输出文件地址

    private String inPutFilePath; //输入文件地址

    private String waterMarkNameFooter; //页脚添加水印的内容

    private String imgWaterMarkFilePath; //图片水印地址

    private String textWaterMarkName; //文字水印内容

}
