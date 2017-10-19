
package com.tyunsoft.base.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

public class ImageResizer
{
    /***
     * 功能 :调整图片大小
     * 
     * @param srcImgPath
     *            原图片路径
     * @param distImgPath
     *            转换大小后图片路径
     * @param width
     *            转换后图片宽度
     * @param height
     *            转换后图片高度
     */
    public static void resizeImage( String srcImgPath, String distImgPath,
            int width, int height ) throws IOException
    {

        File srcFile = new File( srcImgPath );
        Image srcImg = ImageIO.read( srcFile );
        BufferedImage buffImg = null;
        buffImg = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );
        buffImg.getGraphics().drawImage(
                srcImg.getScaledInstance( width, height, Image.SCALE_SMOOTH ),
                0, 0, null );
        String suffix = srcImgPath
                .substring( srcImgPath.lastIndexOf( "." ) + 1 );
        ImageIO.write( buffImg, suffix, new File( distImgPath ) );

    }
    
    /**
     * 根据宽度修改图片尺寸
     * @param srcImgPath
     * @param distImgPath
     * @param width [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void resizeImageByWidth(String srcImgPath,String distImgPath,int width)
    {
        File srcFile = new File( srcImgPath );
        try
        {
            BufferedImage sourceImg = ImageIO.read( new FileInputStream( srcFile ) );
            resizeImage( srcImgPath, distImgPath, (double)width/sourceImg.getWidth() );
        }
        catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    /**
     * 按照比例更改图片
     * 
     * @param srcImgPath
     *            原图片
     * @param distImgPath
     *            目标图片路径
     * @param scale
     *            比例
     * @throws IOException
     */
    public static void resizeImage( String srcImgPath, String distImgPath,
            double scale ) throws IOException
    {
        File srcFile = new File( srcImgPath );
        BufferedImage sourceImg = ImageIO.read( new FileInputStream( srcFile ) );
        Image srcImg = ImageIO.read( srcFile );
        BufferedImage buffImg = null;
        int width = Integer.parseInt( new DecimalFormat( "0" )
                .format( sourceImg.getWidth() * scale ) );
        int height = Integer.parseInt( new DecimalFormat( "0" )
                .format( sourceImg.getHeight() * scale ) );
        buffImg = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );
        buffImg.getGraphics().drawImage(
                srcImg.getScaledInstance( width, height, Image.SCALE_SMOOTH ),
                0, 0, null );
        String suffix = srcImgPath
                .substring( srcImgPath.lastIndexOf( "." ) + 1 );
        ImageIO.write( buffImg, suffix, new File( distImgPath ) );
    }

    public static void addFontToImage( String srcImgPath, String distImgPath, String text )
    {
        try
        {
            BufferedImage buffImage = ImageIO.read( new File( srcImgPath ) );
            Graphics g = buffImage.getGraphics();
            Font font = new Font( "黑体", Font.BOLD, 30 );
            g.setFont( font );
            g.setColor( Color.ORANGE );
            g.drawString( text, 10, buffImage.getHeight() - 30 );

            FileOutputStream outImg = new FileOutputStream( new File(
                    distImgPath ) );
            ImageIO.write( buffImage, "jpg", outImg );
            outImg.flush();
            outImg.close();
        }
        catch ( FileNotFoundException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main( String[] args )
    {
        File file = new File( "d:/img" );
        String[] files = file.list();
        for ( String f : files )
        {
            
            addFontToImage("d:/img/" + f, "d:/img/FONT_" + f, "美丽社区100分");
//            try
//            {
//                resizeImage( "d:/img/" + f, "d:/img/SMALL_" + f, 5 );
//                
//            }
//            catch ( IOException e )
//            {
//                e.printStackTrace();
//            }
        }
    }

}
