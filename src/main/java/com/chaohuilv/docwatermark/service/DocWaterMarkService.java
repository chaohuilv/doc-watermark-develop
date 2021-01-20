package com.chaohuilv.docwatermark.service;

import com.chaohuilv.docwatermark.model.DocWaterMark;

public interface DocWaterMarkService {

    /**
     * 添加水印
     * @param docWaterMark
     */
    void AddDocWaterMark(DocWaterMark docWaterMark);

    /**
     * 删除水印
     * @param docWaterMark
     */
    void RemoveWatermark(DocWaterMark docWaterMark);
}
