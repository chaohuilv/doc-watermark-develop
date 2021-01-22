package com.chaohuilv.docwatermark.service;

import com.chaohuilv.docwatermark.model.DocWaterMark;
import com.chaohuilv.docwatermark.model.FTPModel;


public interface FTPService {

    /**
     * 连接ftp服务
     * @return 是否成功
     */
    boolean connectServer();

    /**
     * 判断文件是否存在
     * @param ftpModel ftp信息
     * @return 是否存在
     */
    boolean existFile(FTPModel ftpModel);

    /**
     * 删除ftp上得文件
     * @param ftpModel ftp信息
     * @return 是否删除成功
     */
    boolean deleteFile(FTPModel ftpModel);

    /**
     * 从ftp上下载文件到本地
     * @param ftpModel ftp信息
     * @return 本地文件保存地址
     */
    String download(FTPModel ftpModel);

    /**
     * 上传文件
     * @param ftpModel ftp信息
     * @return 是否上传成功
     */
    boolean upload(FTPModel ftpModel);

    /**
     * 目录下是否存在该文件夹
     * @param ftpModel ftp信息
     * @return 是否存在
     */
    boolean checkSubfolder(FTPModel ftpModel);

    /**
     * 目录下创建文件加
     * @param ftpModel ftp信息
     * @return 是否存在
     */
    boolean createSubfolder(FTPModel ftpModel);

    /**
     * 断开与远程服务器的连接
     */
    void  closeClient();

}
