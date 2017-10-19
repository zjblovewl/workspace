/*
 * 文 件 名:  Base64FileUtil.java
 * 版    权:  Foresoft Technologies,  All rights reserved
 * 描    述:  <描述>
 * 修改时间:  2015年4月24日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package net.ycii.fc.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.ycii.fc.exception.BaseException;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.tyunsoft.base.utils.DateUtil;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @version  [版本号, 2015年4月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Base64FileUtil
{
    /**
     * base64操作
     */
    private static BASE64Decoder decoder = new BASE64Decoder();
    
    private static int BUFFER_SIZE = 1024 * 8;
    
    /**
     * 编码
     */
    private static BASE64Encoder encoder = new BASE64Encoder();
    
    
    /**
     * base64字符串转为文件存储，返回存储路径
     * @param fileName 文件名称
     * @param base64Str base64字符串
     * @param basePath 基础路径
     * @param dirs 文件夹路径
     * @param dateDir 是否添加日期文件夹
     * @return [参数说明] 转换结果
     * @see [类、类#方法、类#成员]
     */
    public static String convert2File( String fileName,String base64Str,String basePath,String dirs,boolean dateDir )
    {
        String result = null;
        String httpDirs = dirs;
        try
        {
            if ( dateDir )
            {
                String dirItem = DateUtil.getCurrentDateStr( "yyyyMMdd" );
                dirs = dirs + File.separator + dirItem;
                httpDirs = httpDirs + "/" + dirItem;
            }
            String dirPath = basePath + dirs;
            File dir = new File(dirPath);
            if ( !dir.exists() )
            {
                dir.mkdirs();
            }
            int index = 0;
            String[] files = dir.list();
            if ( null != files )
            {
                index = files.length + 1;
            }
            String finalFileName = index + fileName.substring( fileName.lastIndexOf( "." ) );
            result = httpDirs + "/" + finalFileName;
            FileOutputStream fos = new FileOutputStream( new File(dir,finalFileName) );
            fos.write( decoder.decodeBuffer( base64Str ) );
            fos.flush();
            fos.close();
        } catch ( Exception e )
        {
            result = null;
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 将多个base64字符串转为文件存储，返回以|分隔的存储路径
     * JSONArray对象至少有{"fileName":"s.doc","fileContent":""}格式
     * @param arr jsonArray对象
     * @param basePath 基础路径
     * @param dirs 文件夹路径
     * @param dateDir 是否添加日期文件夹
     * @return [参数说明] 转换结果
     * @see [类、类#方法、类#成员]
     */
    public static String convertFile(JSONArray arr,String basePath,String dirs,boolean dateDir)
    {
        int index = arr.size();
        JSONObject json = null;
        StringBuilder result = new StringBuilder();
        String path = null;
        String fileName = null;
        String fileContent = null;
        for ( int i = 0; i < index; i++ )
        {
            json = arr.getJSONObject( i );
            fileName = json.getString( "fileName" );
            fileContent = json.getString( "fileContent" );
            path = convert2File(fileName,fileContent,basePath,dirs,dateDir);
            if ( StringUtils.isNotEmpty( path ) )
            {
                result.append( path ).append( "|" );
            }
        }
        if ( result.length() > 0 )
        {
            result.deleteCharAt( result.length() - 1 );
        }
        return result.toString();
    }
    
    public static String getRealContextPath( HttpServletRequest request )
    {
        String realPath = request.getSession().getServletContext().getRealPath( "" ) + File.separator;
        return realPath;
    }
    
    public static String file2Base64Str( File file ) throws Exception
    {
        FileInputStream fis = new FileInputStream(file);
        byte[] buf = new byte[(int)file.length()];
        fis.read( buf );
        String result = encoder.encode( buf );
        
        fis.close();
        return result;
    }
    
    public static void main( String[] args ) throws Exception
    {
        File file = new File("D:\\yuechuang_dev\\mm\\050.code\\mimi\\WebRoot\\images\\add.png");
        String basePath = "D:\\test\\";
        String str = file2Base64Str(file);
        String path = convert2File("add.png",str,basePath,"upload",true);
        System.out.println(path);
    }
    
    public static boolean copyToPath( InputStream inputStream, String path,String newFileName ) throws BaseException
    {
        File pathFile = new File(path);
        //判断文件目录是否存在,如果不存在创建目录
        if(!pathFile.exists())
        {
            pathFile.mkdirs();
        }
        
        File newFile = new File( path + newFileName );

        OutputStream os = null;
        InputStream in = null;
        try
        {
            in = new BufferedInputStream(  inputStream );
            os = new BufferedOutputStream( new FileOutputStream( newFile ) );
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            int total = in.available();
            while ( (len = in.read( buffer )) != -1 )
            {
                if ( BUFFER_SIZE > total )
                {
                    os.write( buffer, 0, len );
                } else
                {
                    os.write( buffer );
                }
            }
            os.flush();
            os.close();
        } catch ( Exception e )
        {
            return false;
        }
        return true;
    }
    
    public static String getFileType(String fileName){
        return fileName.substring( fileName.lastIndexOf( "." )+1,fileName.length() );
    }
}
