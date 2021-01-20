package com.chaohuilv.docwatermark.exception;

import com.chaohuilv.docwatermark.enums.DocWaterMarkEnum;

/**
 * Created by null on 2010/1/20
 */
public class DocWaterMarkException extends RuntimeException {

    private Integer code;

    public DocWaterMarkException(DocWaterMarkEnum docWaterMarkEnum){
        super(docWaterMarkEnum.getMsg());
        code = docWaterMarkEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

}
