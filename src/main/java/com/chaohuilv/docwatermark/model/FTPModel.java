package com.chaohuilv.docwatermark.model;

import lombok.Data;

@Data
public class FTPModel {

    private String remoteFile; //远程文件

    private String localPath; //保存至本地得路径

    private String FTPPath; //Ftp目录

    private String pathName; //文件地址

    private String fileName; //文件名称

    private String path; //文件路径

    private String subfolderName;//文件夹名称

}
