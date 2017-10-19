package com.tyunsoft.base.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;
//用ant.jar的zip.*可以解决中文文件名问题
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 压缩文件. 2007-10-17 下午11:19:50
 * 
 * @author chenlb
 */
public class RecursiveZip {

    public static void main(String[] args) {

        RecursiveZip recursiveZip = new RecursiveZip();
        System.out.println("====开始====");
        try {
            OutputStream os = new FileOutputStream("e:/doc-recursive.zip");
            BufferedOutputStream bs = new BufferedOutputStream(os);
            ZipOutputStream zo = new ZipOutputStream(bs);

            // recursiveZip.zip("e:/recursive-zip/中文文件名.txt", new
            // File("e:/recursive-zip"), zo, true, true);
            recursiveZip.zip("e:/recursive-zip", new File("e:/recursive-zip"),
                    zo, true, true);

            zo.closeEntry();
            zo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("====完成====");
    }
    
    /**
     * 压缩ZIP文件
     * @param zipDir 需要压缩的目录
     * @param zipFile 压缩后的zip文件，包括目录
     */
    public void zip(String zipDir, String zipFile)
    {
        try {
            OutputStream os = new FileOutputStream(zipFile);
            BufferedOutputStream bs = new BufferedOutputStream(os);
            ZipOutputStream zo = new ZipOutputStream(bs);

            zip(zipDir, new File(zipDir),
                    zo, true, true);

            zo.closeEntry();
            zo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("====完成====");
    }

    /**
     * @param path
     *            要压缩的路径, 可以是目录, 也可以是文件.
     * @param basePath
     *            如果path是目录,它一般为new File(path), 作用是:使输出的zip文件以此目录为根目录,
     *            如果为null它只压缩文件, 不解压目录.
     * @param zo
     *            压缩输出流
     * @param isRecursive
     *            是否递归
     * @param isOutBlankDir
     *            是否输出空目录, 要使输出空目录为true,同时baseFile不为null.
     * @throws IOException
     */
    public void zip(String path, File basePath, ZipOutputStream zo,
            boolean isRecursive, boolean isOutBlankDir) throws IOException {

        File inFile = new File(path);

        File[] files = new File[0];
        if (inFile.isDirectory()) { // 是目录
            files = inFile.listFiles();
        } else if (inFile.isFile()) { // 是文件
            files = new File[1];
            files[0] = inFile;
        }
        byte[] buf = new byte[1024];
        int len;
        // System.out.println("baseFile: "+baseFile.getPath());
        for (int i = 0; i < files.length; i++) {
            String pathName = "";
            if (basePath != null) {
                if (basePath.isDirectory()) {
                    pathName = files[i].getPath().substring(
                            basePath.getPath().length() + 1);
                } else {// 文件
                    pathName = files[i].getPath().substring(
                            basePath.getParent().length() + 1);
                }
            } else {
                pathName = files[i].getName();
            }
            System.out.println(pathName);
            if (files[i].isDirectory()) {
                if (isOutBlankDir && basePath != null) {
                    zo.putNextEntry(new ZipEntry(pathName + "/")); // 可以使空目录也放进去
                }
                if (isRecursive) { // 递归
                    zip(files[i].getPath(), basePath, zo, isRecursive,
                            isOutBlankDir);
                }
            } else {
                FileInputStream fin = new FileInputStream(files[i]);
                zo.putNextEntry(new ZipEntry(pathName));
                while ((len = fin.read(buf)) > 0) {
                    zo.write(buf, 0, len);
                }
                fin.close();
            }
        }
    }
}
