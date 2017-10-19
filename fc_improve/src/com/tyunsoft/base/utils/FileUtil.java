
package com.tyunsoft.base.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.util.Streams;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.tyunsoft.base.entity.FileEntity;

public class FileUtil
{

    /**
     * 文件转化为字节数组
     * 
     * @Author Sean.guo
     * @EditTime 2007-8-13 上午11:45:28
     */
    public static byte[] getBytesFromFile( File f )
    {
        if ( f == null )
        {
            return null;
        }
        try
        {
            FileInputStream stream = new FileInputStream( f );
            ByteArrayOutputStream out = new ByteArrayOutputStream( 1000 );
            byte[] b = new byte[1000];
            int n;
            while ( (n = stream.read( b )) != -1 )
                out.write( b, 0, n );
            stream.close();
            out.close();
            return out.toByteArray();
        }
        catch ( IOException e )
        {
        }
        return null;
    }

    /** */
    /**
     * 把字节数组保存为一个文件
     * 
     * @Author Sean.guo
     * @EditTime 2007-8-13 上午11:45:56
     */
    public static File getFileFromBytes( byte[] b, String outputFile )
    {
        BufferedOutputStream stream = null;
        File file = null;
        try
        {
            file = new File( outputFile );
            FileOutputStream fstream = new FileOutputStream( file );
            stream = new BufferedOutputStream( fstream );
            stream.write( b );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        finally
        {
            if ( stream != null )
            {
                try
                {
                    stream.close();
                }
                catch ( IOException e1 )
                {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    /** */
    /**
     * 从字节数组获取对象
     * 
     * @Author Sean.guo
     * @EditTime 2007-8-13 上午11:46:34
     */
    public static Object getObjectFromBytes( byte[] objBytes ) throws Exception
    {
        if ( objBytes == null || objBytes.length == 0 )
        {
            return null;
        }
        ByteArrayInputStream bi = new ByteArrayInputStream( objBytes );
        ObjectInputStream oi = new ObjectInputStream( bi );
        return oi.readObject();
    }

    /** */
    /**
     * 从对象获取一个字节数组
     * 
     * @Author Sean.guo
     * @EditTime 2007-8-13 上午11:46:56
     */
    public static byte[] getBytesFromObject( Serializable obj )
            throws Exception
    {
        if ( obj == null )
        {
            return null;
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream( bo );
        oo.writeObject( obj );
        return bo.toByteArray();
    }

    public static final int DEFAULT_BLOCK_SIZE = 1024 * 1024;

    /**
     * 下载文件的方法
     * 
     * @param file
     *            需要下载的文件
     * @param response
     *            获取response以用来设置头文件信息
     */
    public static void download( File file, HttpServletResponse response )
    {
        String fileName = file.getName();
        InputStream in = null;
        try
        {
            fileName = new String( fileName.getBytes( "utf-8" ), "ISO-8859-1" );
            in = new FileInputStream( file );
            response.setContentType( "application/x-msdownload" );
            response.setHeader( "Content-Disposition", "attachment; filename="
                    + fileName );
            copyStream( in, response.getOutputStream() );
        }
        catch ( UnsupportedEncodingException e )
        {
            e.printStackTrace();
        }
        catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            closeStream( in );
        }

    }

    /**
     * @Description 根据字节数组下载文件
     * @param fileName
     *            文件名称
     * @param content
     *            字节数组内容
     * @param response
     *            获取response以用来设置头文件信息
     * @history
     */
    public static void download( String fileName, byte[] content,
            HttpServletResponse response )
    {
        InputStream in = null;
        try
        {
            fileName = new String( fileName.getBytes( "gbk" ), "ISO-8859-1" );
            in = new ByteArrayInputStream( content );
            response.setContentType( "application/x-msdownload" );
            response.setHeader( "Content-Disposition", "attachment; filename="
                    + fileName );
            copyStream( in, response.getOutputStream() );
        }
        catch ( UnsupportedEncodingException e )
        {
            e.printStackTrace();
        }
        catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            closeStream( in );
        }
    }

    /**
     * 将输入流拷贝到输出流中
     * 
     * @param in
     *            输入流
     * @param out
     *            输出流
     * @return 返回文件的长度
     * @throws IOException
     */
    public static int copyStream( InputStream in, OutputStream out )
            throws IOException
    {
        byte[] buf = new byte[1024 * 1024]; // 64k bytes
        int len = 0;
        int empCount = 0;
        int totalLen = 0;

        while ( len >= 0 && empCount < 5 )
        {
            len = in.read( buf );

            if ( len < 0 )
            {
                break;
            }
            else if ( len == 0 )
            {
                ++empCount;
            }
            else
            {
                out.write( buf, 0, len );
                totalLen += len;
            }
        }
        return totalLen;
    }

    /**
     * 将字节数组输出流转换成输入流
     * 
     * @param out
     *            需要转换的输出流
     * @return
     */
    public static InputStream copyStream( ByteArrayOutputStream out )
    {
        InputStream is = new ByteArrayInputStream( out.toByteArray() );
        return is;
    }

    /**
     * 关闭输入流
     * 
     * @param in
     *            待关闭输入流
     */
    public static void closeStream( InputStream in )
    {
        if ( in != null )
        {
            try
            {
                in.close();
            }
            catch ( Exception ex )
            {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     * 
     * @param out
     *            待关闭输出流
     */
    public static void closeStream( OutputStream out )
    {
        if ( out != null )
        {
            try
            {
                out.close();
            }
            catch ( Exception ex )
            {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 将文件名读入到字节数组当中
     * 
     * @param fileName
     *            文件名
     * @return 读取后的字节数组
     * @throws IOException
     */
    public static byte[] readBytes( String fileName ) throws IOException
    {
        InputStream in = null;
        ByteArrayOutputStream bufOut = null;

        try
        {
            bufOut = new ByteArrayOutputStream(
                    (int)new File( fileName ).length() );
            in = new FileInputStream( fileName );
            copyStream( in, bufOut );
            return bufOut.toByteArray();
        }
        finally
        {
            closeStream( in );
        }
    }

    /**
     * 将输入流读入到字节数组当中
     * 
     * @param in
     *            输入流
     * @return 读取后的字节数组
     * @throws IOException
     */
    public static byte[] readBytes( InputStream in ) throws IOException
    {
        ByteArrayOutputStream bufOut = null;

        bufOut = new ByteArrayOutputStream( in.available() );
        copyStream( in, bufOut );
        return bufOut.toByteArray();
    }

    /**
     * 比较两个文件的内容
     * 
     * @param f1
     *            文件1
     * @param f2
     *            文件2
     * @return 内容一直返回true，否则返回false
     * @throws IOException
     */
    @SuppressWarnings( "resource" )
    public static boolean compareFile( File f1, File f2 ) throws IOException
    {
        InputStream in1 = null;
        InputStream in2 = null;

        try
        {
            byte buf1[] = new byte[DEFAULT_BLOCK_SIZE];
            byte buf2[] = new byte[DEFAULT_BLOCK_SIZE];
            int readSize;

            if ( f1.length() != f2.length() )
            {
                return false;
            }

            in1 = new FileInputStream( f1 );
            in2 = new FileInputStream( f2 );

            while ( true )
            {
                readSize = in1.read( buf1 );
                if ( in2.read( buf2 ) != readSize )
                {
                    throw new IOException( "Error when compare file `"
                            + f1.getAbsolutePath() + "' and '"
                            + f2.getAbsolutePath() + "'." );
                }
                if ( readSize <= 0 )
                {
                    return true;
                }
                for ( int i = 0; i < readSize; i++ )
                {
                    if ( buf1[i] != buf2[i] )
                    {
                        return false;
                    }
                }
            }
        }
        finally
        {
            FileUtil.closeStream( in1 );
            FileUtil.closeStream( in2 );
        }
    }

    /**
     * 删除文件或者清空目录下所有文件,并且删除该目录
     * 
     * @param file
     *            需要删除的文件/目录
     * @return 删除成功返回true、否则返回false
     */
    public static boolean deleteFile( File file )
    {
        File filelist[] = file.listFiles();
        int listlen = filelist.length;
        for ( int i = 0; i < listlen; i++ )
        {
            if ( filelist[i].isDirectory() )
            {
                deleteFile( filelist[i] );
            }
            else
            {
                filelist[i].delete();
            }
        }
        return file.delete();// 删除当前目录
    }

    /**
     * 根据文件的名称(带路径,如：c:\aa\aa.txt)获得文件的目录和文件的名称
     * 
     * @param fileName
     *            传入的文件名
     * @return String[0]表示文件所在目录、String[1]表示文件名称
     */
    public static String[] splitFileName( String fileName )
    {
        int index = -1;

        for ( int i = fileName.length() - 1; i >= 0; i-- )
        {
            char c = fileName.charAt( i );

            if ( c == '/' || c == '\\' )
            {
                index = i;
                break;
            }
        }
        if ( index < 0 )
        {
            return new String[] {null, fileName};
        }
        else
        {
            return new String[] {fileName.substring( 0, index ),
                    fileName.substring( index + 1, fileName.length() )};
        }
    }

    /**
     * 根据文件的名称(带路径,如：c:\aa\aa.txt)获得文件所在目录
     * 
     * @param fileName
     *            传入的文件名
     * @return 经过转换后的文件目录
     */
    public static String getFileDirectory( String fileName )
    {
        return splitFileName( fileName )[0];
    }

    /**
     * 根据文件的名称(带路径,如：c:\aa\aa.txt)获得文件的实际名称
     * 
     * @param fileName
     *            传入的文件名
     * @return 经过转换后获得的文件名
     */
    public static String getFileName( String fileName )
    {
        return splitFileName( fileName )[1];
    }

    /**
     * 从请求中获取文件对象
     * 
     * @param request
     *            请求
     * @param inputFileName
     *            页面input的name
     * @return 文件对象
     */
    public static FileEntity getRequestFileEntity( HttpServletRequest request,
            String inputFileName )
    {
        FileEntity result = null;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        CommonsMultipartFile file = (CommonsMultipartFile)multipartRequest
                .getFile( inputFileName );
        if ( null != file && file.getSize() != 0 )
        {
            result = new FileEntity();
            result.setFileName( file.getOriginalFilename() );
            result.setFileBytes( file.getBytes() );
            result.setFileSuffix( file.getOriginalFilename().substring(
                    file.getOriginalFilename().lastIndexOf( "." ) + 1 ) );
        }
        else
        {
            result = new FileEntity();
            result.setFileName( "" );
            result.setFileBytes( new byte[] {} );
        }
        return result;
    }

    /**
     * 获取文件对象列表，针对同名获取多个文件对象
     * 
     * @param request
     *            请求
     * @param inputFileName
     *            页面input的name
     * @return 文件对象列表
     */
    public static List<FileEntity> getRequestFileEntities(
            HttpServletRequest request, String inputFileName )
    {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        List<MultipartFile> files = multipartRequest.getFiles( inputFileName );
        List<FileEntity> result = new ArrayList<FileEntity>();
        FileEntity fe = null;
        for ( int i = 0; i < files.size(); i++ )
        {

            MultipartFile file = files.get( i );
            if ( file.getSize() != 0 )
            {
                fe = new FileEntity();
                fe.setFileName( file.getOriginalFilename() );
                try
                {
                    fe.setFileBytes( file.getBytes() );
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
                result.add( fe );
            }
        }
        return result;
    }
    
    /**
     * 获取请求中所有的文件对象
     * @param request 请求
     * @return 所有的文件对象
     */
    public static List<FileEntity> getRequestAllFileEntities(HttpServletRequest request)
    {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        List<MultipartFile> files = multipartRequest.getFiles( "Img" );
        List<FileEntity> result = new ArrayList<FileEntity>();
        FileEntity fe = null;
        for ( int i = 0; i < files.size(); i++ )
        {

            MultipartFile file = files.get( i );
            fe = new FileEntity();
            fe.setFileName( file.getOriginalFilename() );
            fe.setFileSuffix(  file.getOriginalFilename().substring(
                    file.getOriginalFilename().lastIndexOf( "." ) + 1 )  );
            try
            {
                fe.setFileBytes( file.getBytes() );
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
            result.add( fe );
        }
        return result;
    }

    /**
     * 获取文件对象列表，针对同名获取多个文件对象
     * 
     * @param request
     *            请求
     * @param inputFileName
     *            页面input的name
     * @return 文件对象列表
     */
    public static List<FileEntity> getRequestAllFileEntities(
            HttpServletRequest request, String inputFileName )
    {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        List<MultipartFile> files = multipartRequest.getFiles( inputFileName );
        List<FileEntity> result = new ArrayList<FileEntity>();
        FileEntity fe = null;
        for ( int i = 0; i < files.size(); i++ )
        {

            MultipartFile file = files.get( i );
            fe = new FileEntity();
            fe.setFileName( file.getOriginalFilename() );
            try
            {
                fe.setFileBytes( file.getBytes() );
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
            result.add( fe );
        }
        return result;
    }

    public static String upload( HttpServletRequest request, String savePath,
            String saveFileBeforeName, String inputName )
    {
        return upload( request, savePath, saveFileBeforeName, inputName, null );
    }
    
    
    public static String uploadAll(HttpServletRequest request, String savePath, String saveFileBeforeName)
    {
        List<FileEntity> files = getRequestAllFileEntities( request );
        String result = "";
        StringBuffer returnResult = new StringBuffer(); 
        for ( int i = 0; i < files.size(); i++ )
        {
            result = toUpload( request, savePath, saveFileBeforeName+"_"+(i+1), files.get( i ) );
            returnResult.append( result );
            if(files.size() != i + 1)
            {
                returnResult.append( "|" );
            }
        }
        return returnResult.toString();
    }

    public static String upload( HttpServletRequest request, String savePath,
            String saveFileBeforeName, String inputName, Map<String, Double> map )
    {

        FileEntity file = FileUtil.getRequestFileEntity( request, inputName );
        if ( file.getFileBytes().length == 0 )
        {
            return null;
        }

        return toUpload( request, savePath, saveFileBeforeName, file );
    }
    
    
    private static String toUpload(HttpServletRequest request, String savePath,String saveFileBeforeName,FileEntity file){
        String physicalPath = getPhysicalPath( request, savePath, file );
        String projectSavePath = savePath + "/" + saveFileBeforeName + "."
                + file.getFileSuffix();
        ByteArrayInputStream in = new ByteArrayInputStream( file.getFileBytes() );
        FileOutputStream out = null;
        BufferedOutputStream output = null;
        try
        {
            out = new FileOutputStream( new File( physicalPath + "/"
                    + saveFileBeforeName + "." + file.getFileSuffix() ) );
            output = new BufferedOutputStream( out );
            Streams.copy( in, output, true );
        }
        catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            if ( null != output )
            {
                try
                {
                    output.close();
                    out.close();
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
            }
        }
        return projectSavePath;
    }
    
    
    public static void uploadWithGenSmall( HttpServletRequest request, String savePath,
            String saveFileBeforeName, String inputName ,int smallImageWidth)
    {
        String filePath = upload( request, savePath, saveFileBeforeName, inputName );
        String fileSuffix = filePath.substring( filePath.lastIndexOf( "." ));
      //生成小图片
        String path = FileUtil.class.getClassLoader().getResource( "/" )
                .getPath();
        path = path.replaceAll( "WEB-INF/classes/", savePath );
        ImageResizer.resizeImageByWidth( path +File.separator+ saveFileBeforeName + fileSuffix, path +File.separator + "small_" +saveFileBeforeName + fileSuffix, smallImageWidth );
    }

    /**
     * 修改图片的尺寸
     * @param width 修改后的宽度
     * @param height 修改后的高度
     * @param request 请求
     * @param savePath 保存路径
     * @param saveFileBeforeName 文件除了后缀的保存名称
     * @param inputName 上传界面的表单名称
     */
    public static void resizeImage( int width, int height,
            HttpServletRequest request, String savePath,
            String saveFileBeforeName, String inputName )
    {
        FileEntity file = FileUtil.getRequestFileEntity( request, inputName );
        String physicalPath = getPhysicalPath( request, savePath, file );
        String srcPath = physicalPath + "/" + saveFileBeforeName + "."
                + file.getFileSuffix();
        String distPath = 
                physicalPath + "/" + "SMALL_" + saveFileBeforeName + "."
                        + file.getFileSuffix() ;
        try
        {
            ImageResizer.resizeImage( srcPath, distPath, width, height );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    

    /**
     * java获取文件大小的类 四舍五入保留两位小数
     * 
     * @param file
     * @return
     * @throws Exception
     */
    public static double getFileSize( InputStream is )
    {// 取得文件大小
        double size = 0;
        try
        {
            size = is.available();
        }
        catch ( IOException e )
        {
            size = 0;
        }
        size = size / 1024.00;
        size = Double.parseDouble( size + "" );
        BigDecimal b = new BigDecimal( size );
        double y1 = b.setScale( 2, BigDecimal.ROUND_HALF_UP ).doubleValue();
        java.text.DecimalFormat df = new java.text.DecimalFormat( "#.00" );
        return Double.parseDouble( df.format( y1 ) );
    }

    private static String getPhysicalPath( HttpServletRequest request,
            String savePath, FileEntity fileEntity )
    {
        String path = FileUtil.class.getClassLoader().getResource( "/" )
                .getPath();
        path = path.replaceAll( "WEB-INF/classes/", savePath );
        File f = new File( path );
        if ( !f.exists() )
        {
            f.mkdirs();
        }
        return path;
    }
    
}
