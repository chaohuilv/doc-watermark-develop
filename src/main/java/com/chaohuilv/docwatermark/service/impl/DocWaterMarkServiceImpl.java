package com.chaohuilv.docwatermark.service.impl;

import com.chaohuilv.docwatermark.model.DocWaterMark;
import com.chaohuilv.docwatermark.service.DocWaterMarkService;

public class DocWaterMarkServiceImpl {

    //持有一个具体的策略对象
    private DocWaterMarkService docWaterMarkStrategy;

    /**
     * 构造函数，传入一个具体的策略对象
     * @param docWaterMarkStrategy    具体的策略对象
     */
    public DocWaterMarkServiceImpl(DocWaterMarkService docWaterMarkStrategy){
        this.docWaterMarkStrategy = docWaterMarkStrategy;
    }

    /**
     * 添加水印
     * @param docWaterMark 水印信息
     * @return
     */
    public void AddDocWaterMark(DocWaterMark docWaterMark){
        this.docWaterMarkStrategy.AddDocWaterMark(docWaterMark);
    }

    /**
     * 删除水印
     * @param docWaterMark
     */
    public void RemoveWatermark(DocWaterMark docWaterMark){
        this.docWaterMarkStrategy.RemoveWatermark(docWaterMark);
    }

}
