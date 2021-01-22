package com.chaohuilv.docwatermark.config;

import com.chaohuilv.docwatermark.service.impl.FTPServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FTPConfigTest {


    @Bean
    public FTPServiceImpl ftpUtilsService() {
        FTPServiceImpl ftpUtils = new FTPServiceImpl();
        ftpUtils.setFtpConfig(ftpConfig());
        return ftpUtils;
    }

    @Bean
    public FTPConfig ftpConfig(){
        FTPConfig ftpConfig = new FTPConfig();
        ftpConfig.setFtpIp("10.150.172.49");
        ftpConfig.setFtpPort(21);
        ftpConfig.setFtpUserName("ftp");
        ftpConfig.setFtpPassword("666666");
        ftpConfig.setFtpEncode("UTF-8");
        return ftpConfig;
    }

}