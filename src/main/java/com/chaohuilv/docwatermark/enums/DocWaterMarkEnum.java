package com.chaohuilv.docwatermark.enums;

/**
 * Created by null on 2021/1/20
 */
public enum DocWaterMarkEnum {

    UNKNOW_ERROR(-1, "未知异常"),
    SUCCESS(0, "成功"),
    PARAM_ERROR(1, "参数错误"),
    PARAM_ERROR_TEXT(2,"参数错误，文字水印内容不能为空"),
    PARAM_ERROR_IMG(3,"参数错误，图片水印地址不能为空"),
    CONFIG_ERROR(2, "配置错误, 请检查是否漏了配置项"),

    ;

    private Integer code;

    private String msg;

    DocWaterMarkEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
