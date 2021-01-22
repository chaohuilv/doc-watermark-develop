package com.chaohuilv.docwatermark.service;

import com.chaohuilv.docwatermark.model.DocWaterMark;

public interface DocWaterMarkService {

    /**
     * 添加水印
     * @param docWaterMark 水印信息
     */
    void AddDocWaterMark(DocWaterMark docWaterMark);

    /**
     * 删除水印
     * @param docWaterMark 水印信息
     */
    void RemoveWatermark(DocWaterMark docWaterMark);
}
