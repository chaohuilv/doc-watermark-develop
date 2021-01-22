package com.chaohuilv.docwatermark.config;


import java.util.Objects;

public class FTPConfig {

    private String ftpEncode; //编码方式

    private String ftpIp; //地址

    private Integer ftpPort; //端口

    private String ftpUserName; //用户名

    private String ftpPassword; //密码

    private String localPath; //文件路径

    public String getFtpEncode() {
        return ftpEncode;
    }

    public void setFtpEncode(String ftpEncode) {
        this.ftpEncode = ftpEncode;
    }

    public String getFtpIp() {
        return ftpIp;
    }

    public void setFtpIp(String ftpIp) {
        this.ftpIp = ftpIp;
    }

    public Integer getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(Integer ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpUserName() {
        return ftpUserName;
    }

    public void setFtpUserName(String ftpUserName) {
        this.ftpUserName = ftpUserName;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public String getLocalPath() {
        return "ftp://"+ftpIp+":"+ftpPort;
    }

    public FTPConfig() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FTPConfig ftpConfig = (FTPConfig) o;
        return Objects.equals(ftpEncode, ftpConfig.ftpEncode) &&
                Objects.equals(ftpIp, ftpConfig.ftpIp) &&
                Objects.equals(ftpPort, ftpConfig.ftpPort) &&
                Objects.equals(ftpUserName, ftpConfig.ftpUserName) &&
                Objects.equals(ftpPassword, ftpConfig.ftpPassword) &&
                Objects.equals(localPath, ftpConfig.localPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ftpEncode, ftpIp, ftpPort, ftpUserName, ftpPassword, localPath);
    }

    @Override
    public String toString() {
        return "FTPConfig{" +
                "ftpEncode='" + ftpEncode + '\'' +
                ", ftpIp='" + ftpIp + '\'' +
                ", ftpPort=" + ftpPort +
                ", ftpUserName='" + ftpUserName + '\'' +
                ", ftpPassword='" + ftpPassword + '\'' +
                ", localPath='" + localPath + '\'' +
                '}';
    }
}
