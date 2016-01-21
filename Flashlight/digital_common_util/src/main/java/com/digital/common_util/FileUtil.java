package com.digital.common_util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by fangzhengyou on 15/9/16.
 */
public class FileUtil {

    /**
     * 解压zip包
     * @param file 解压包的位置
     * @param destDir 解压之后的目的地
     * @throws IOException
     */
    public static void unzipFiles(File file,String destDir)throws IOException {
        //压缩文件
        File srcZipFile=file;
        //基本目录
        if(!destDir.endsWith("/")){
            destDir+="/";
        }
        String prefixion=destDir;

        //压缩输入流
        ZipInputStream zipInput=new ZipInputStream(new FileInputStream(srcZipFile));
        //压缩文件入口
        ZipEntry currentZipEntry=null;
        //循环获取压缩文件及目录
        while((currentZipEntry=zipInput.getNextEntry())!=null){
            //获取文件名或文件夹名
            String fileName=currentZipEntry.getName();
            //Log.v("filename",fileName);
            //构成File对象
            File tempFile=new File(prefixion+fileName);
            //父目录是否存在
            if(!tempFile.getParentFile().exists()){
                //不存在就建立此目录
                tempFile.getParentFile().mkdir();
            }
            //如果是目录，文件名的末尾应该有“/"
            if(currentZipEntry.isDirectory()){
                //如果此目录不在，就建立目录。
                if(!tempFile.exists()){
                    tempFile.mkdir();
                }
                //是目录，就不需要进行后续操作，返回到下一次循环即可。
                continue;
            }
            //如果是文件
            if(!tempFile.exists()){
                //不存在就重新建立此文件。当文件不存在的时候，不建立文件就无法解压缩。
                tempFile.createNewFile();
            }
            //输出解压的文件
            FileOutputStream tempOutputStream=new FileOutputStream(tempFile);

            //获取压缩文件的数据
            byte[] buffer=new byte[10240];
            int hasRead=0;
            //循环读取文件数据
            while((hasRead=zipInput.read(buffer))>0){
                tempOutputStream.write(buffer,0,hasRead);
            }
            tempOutputStream.flush();
            tempOutputStream.close();
        }
        zipInput.close();
    }


    //删除指定文件夹下所有文件
//param path 文件夹完整绝对路径
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    //删除文件夹
//param folderPath 文件夹完整绝对路径

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
