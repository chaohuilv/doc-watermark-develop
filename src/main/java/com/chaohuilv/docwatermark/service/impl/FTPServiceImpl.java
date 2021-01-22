package com.chaohuilv.docwatermark.service.impl;

import com.chaohuilv.docwatermark.config.FTPConfig;
import com.chaohuilv.docwatermark.model.FTPModel;
import com.chaohuilv.docwatermark.service.FTPService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;

@Slf4j
public class FTPServiceImpl implements FTPService {

    //ftp client
    private FTPClient ftpClient;

    //ftp地址配置信息
    private FTPConfig ftpConfig;

    public void setFtpConfig(FTPConfig ftpConfig) {
        this.ftpConfig = ftpConfig;
    }

    public FTPServiceImpl() {

    }

    @Override
    public synchronized boolean connectServer() {
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding(this.ftpConfig.getFtpEncode());//解决上传文件时文件名乱码
        int reply = 0 ;
        try {
            // 连接至服务器
            ftpClient.connect(this.ftpConfig.getFtpIp(),this.ftpConfig.getFtpPort());
            // 登录服务器
            ftpClient.login(this.ftpConfig.getFtpUserName(), this.ftpConfig.getFtpPassword());
            //登陆成功，返回码是230
            reply = ftpClient.getReplyCode();
            // 判断返回码是否合法
            if(!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return false;
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public synchronized boolean existFile(FTPModel ftpModel) {
        boolean flag = false;
        FTPFile[] ftpFileArr;
        try {
            ftpFileArr = ftpClient.listFiles(ftpModel.getPath());
            if (ftpFileArr.length > 0) {
                flag = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public synchronized boolean deleteFile(FTPModel ftpModel) {
        boolean flag = false;
        try {
            //切换FTP目录
            ftpClient.changeWorkingDirectory(ftpModel.getPathName());
            //删除文件
            ftpClient.dele(ftpModel.getFileName());
            ftpClient.logout();
            flag = true;
            log.info("删除文件成功");
            System.out.println("删除文件成功");
        } catch (Exception e) {
            log.error("删除文件失败,Exception={}",e);
            e.printStackTrace();
        } finally {
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    log.error("删除文件失败,IOException={}",e);
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    @Override
    public synchronized String download(FTPModel ftpModel) {
        String path = "";
        FTPFile[] fs=null;
        try {
            fs = ftpClient.listFiles(ftpModel.getRemoteFile());
            if(fs.length<0) {
                return path;
            }
            //1、遍历FTP路径下所有文件
            for(FTPFile file:fs){
                //2、保存到本地
                path = ftpModel.getLocalPath() + File.separatorChar + file.getName();
                File local = new File(path);
                if (!local.getParentFile().exists()) {
                    local.getParentFile().mkdirs();
                }
                if(local.isDirectory())
                {
                    continue;
                }
                OutputStream os = new FileOutputStream(local);
                ftpClient.retrieveFile(new String(ftpModel.getRemoteFile().getBytes("GBK"),"ISO-8859-1"), os);
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }


    @Override
    public synchronized boolean upload(FTPModel ftpModel) {
        try {

            //切换工作路径，设置上传的路径
            ftpClient.changeWorkingDirectory(ftpModel.getLocalPath());
            //设置1M缓冲
            ftpClient.setBufferSize(1024);
            // 设置被动模式
            ftpClient.enterLocalPassiveMode();
            // 设置以二进制方式传输
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            /*
             * 第一个参数：服务器端文档名
             * 第二个参数：上传文档的inputStream
             * 在前面设置好路径，缓冲，编码，文件类型后，开始上传
             */
            File file= new File(ftpModel.getLocalPath()) ;    // 声明File对象
            InputStream inputStream = new FileInputStream(file);
            ftpClient.storeFile(ftpModel.getRemoteFile(), inputStream);
            inputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeClient();
        }
    }

    @Override
    public synchronized boolean checkSubfolder(FTPModel ftpModel) {
        try {
            //切换到FTP根目录
            ftpClient.changeWorkingDirectory(ftpModel.getPath());
            //查看根目录下是否存在该文件夹
            InputStream is = ftpClient.retrieveFileStream(new String(ftpModel.getSubfolderName().getBytes("UTF-8")));
            if (is == null || ftpClient.getReplyCode() == FTPReply.FILE_UNAVAILABLE) {
                //若不存在该文件夹，则创建文件夹
                return createSubfolder(ftpModel);
            }
            if (is != null) {
                is.close();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public synchronized boolean createSubfolder(FTPModel ftpModel) {
        try {
            ftpClient.changeWorkingDirectory(ftpModel.getPath());
            ftpClient.makeDirectory(ftpModel.getSubfolderName());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void closeClient() {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
