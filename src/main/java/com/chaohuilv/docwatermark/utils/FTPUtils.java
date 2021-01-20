package com.chaohuilv.docwatermark.utils;

import com.chaohuilv.docwatermark.config.FTPConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;

@Slf4j
public class FTPUtils {

    private FTPClient ftpClient;

    public FTPUtils() {

    }

    public synchronized boolean connectServer(FTPConfig ftpConfig) {
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding(ftpConfig.getFtpEncode());//解决上传文件时文件名乱码
        int reply = 0 ;
        try {
            // 连接至服务器
            ftpClient.connect(ftpConfig.getFtpIp(),ftpConfig.getFtpPort());
            // 登录服务器
            ftpClient.login(ftpConfig.getFtpUserName(), ftpConfig.getFtpPassword());
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

    //判断ftp服务器文件是否存在
    public boolean existFile(String path)  {
        boolean flag = false;
        FTPFile[] ftpFileArr;
        try {
            ftpFileArr = ftpClient.listFiles(path);
            if (ftpFileArr.length > 0) {
                flag = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    //删除ftp文件
    public synchronized boolean deleteFile(String pathname, String filename){
        boolean flag = false;
        try {
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.dele(filename);
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

    /**
     * 从FTP server下载到本地文件夹
     * @param remoteFile 远程文件
     * @param localFile 本地文件
     * @return
     */
    public synchronized String download(String remoteFile,String localFile){
        String path = "";
        FTPFile[] fs=null;
        try {
            fs = ftpClient.listFiles(remoteFile);
            if(fs.length<0) {
                return path;
            }
            //1、遍历FTP路径下所有文件
            for(FTPFile file:fs){
                //File localfile = new File(localFile);
                //2、保存到本地
               /* 在lunix上要用InputStream input = ftp.retrieveFileStream(new String(fileName.getBytes("GBK"),"ISO-8859-1"));
                重要的是retrieveFileStream这个方法，而且前边要加ftp.enterLocalPassiveMode();
                在windows系统上边发的服务要用 ftpClient.retrieveFile(new String(ff.getName().getBytes("GBK"),"ISO-8859-1"), out);*/
                path = localFile + File.separatorChar + file.getName();
                File local = new File(path);
                if (!local.getParentFile().exists()) {
                    local.getParentFile().mkdirs();
                }
                if(local.isDirectory())
                {
                    continue;
                }
                OutputStream os = new FileOutputStream(local);
                boolean retrieveStatus = ftpClient.retrieveFile(remoteFile, os);
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * 上传文件
     * @param remoteFile
     * @param localFile
     * @return
     */
    public synchronized boolean upload(String remoteFile , String localFile) {
        try {

            //切换工作路径，设置上传的路径
            ftpClient.changeWorkingDirectory(localFile);
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
            File file= new File(localFile) ;    // 声明File对象
            InputStream inputStream = new FileInputStream(file);
            ftpClient.storeFile(remoteFile, inputStream);
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

    public boolean checkSubfolder(String path, String subfolderName) {
        try {
            //切换到FTP根目录
            ftpClient.changeWorkingDirectory(path);
            //查看根目录下是否存在该文件夹
            InputStream is = ftpClient.retrieveFileStream(new String(subfolderName.getBytes("UTF-8")));
            if (is == null || ftpClient.getReplyCode() == FTPReply.FILE_UNAVAILABLE) {
                //若不存在该文件夹，则创建文件夹
                return createSubfolder(path,subfolderName);
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

    public synchronized boolean createSubfolder(String path,String subfolderName){
        try {
            ftpClient.changeWorkingDirectory(path);
            ftpClient.makeDirectory(subfolderName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 断开与远程服务器的连接
     */
    public void closeClient(){
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
