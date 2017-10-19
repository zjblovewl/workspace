
package com.tyunsoft.base.utils;

import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * <p>
 * Package: com.common.util
 * <p>
 * File: StringUtil.java
 * </p>
 * <p>
 * Date:2008-6-1
 * </p>
 * <p>
 * Description: 关于读取操作String字符串的基础类
 * </p>
 * 
 * @author flymz
 * @version 1.0
 * @history
 */
public class StringUtil
{
    /**
     * 检查给定的string是否为空
     * 
     * @param str
     *            给定的字符串
     * @return true表示传入字符串为空，false表示传入字符串为非空
     */
    public static final boolean isBlank( String str )
    {
        if ( str == null || str.trim().equals( "" ) )
        {
            return true;
        }
        return false;
    }

    /**
     * 检查给定的string是否非空
     * 
     * @param str
     *            给定的字符串
     * @return true表示传入字符串为非空，false表示传入字符串为空
     */
    public static final boolean isNotBlank( String str )
    {
        return !isBlank( str );
    }

    /**
     * 检查给定的object是否为空
     * 
     * @param obj
     *            给定的对象
     * @return true表示传入的object对象为空，false表示传入的object对象为非空
     */
    public static final boolean isBlank( Object obj )
    {
        if ( obj == null || obj.equals( "" ) )
        {
            return true;
        }
        return false;
    }

    /**
     * 检查给定的object是否非空
     * 
     * @param obj
     *            给定的对象
     * @return true表示传入的object对象非空，false表示传入的object对象为为空
     */
    public static final boolean isNotBlank( Object obj )
    {
        return !isBlank( obj );
    }

    /**
     * 将IP地址转换成长整型
     * 
     * @param ip
     *            给定的IP地址
     * @return 转换成长整型后的IP地址
     */
    public static long ip4ToLong( String ip )
    {
        if ( ip == null )
            return -1;

        String[] elms = split( ip, "." );
        if ( elms.length != 4 )
            return -1;
        try
        {
            long ipl = 0;
            for ( int i = 0; i < elms.length; i++ )
            {
                int n = Integer.parseInt( elms[i] );
                if ( n > 255 || n < 0 )
                    return -1;
                ipl = (ipl << 8) + n;
            }
            return ipl;
        } catch ( NumberFormatException e )
        {
            return -1;
        }
    }

    /**
     * 将长整型转换成IP地址
     * 
     * @param ip
     *            长整型
     * @return 转换正常显示后的IP地址
     */
    public static String longToIp4( long ip )
    {
        if ( ip < 0 )
            return "";

        byte b1 = (byte)(ip >> 24);
        byte b2 = (byte)(ip >> 16);
        byte b3 = (byte)(ip >> 8);
        byte b4 = (byte)ip;

        StringBuffer sbuf = new StringBuffer();

        sbuf.append( b1 & 0xff );
        sbuf.append( "." );
        sbuf.append( b2 & 0xff );
        sbuf.append( "." );
        sbuf.append( b3 & 0xff );
        sbuf.append( "." );
        sbuf.append( b4 & 0xff );

        return sbuf.toString();
    }

    /**
     * 将字符串以指定字符隔离，返回字符串数组
     * 
     * @param str
     *            需要隔开的字符串
     * @param c
     *            指定使用的隔离字符
     * @return 隔离后的字符串数组
     */
    public static String[] split( String str, char c )
    {
        char[] chs;
        int count;
        String[] ret;
        ArrayList<Integer> vSep;
        int[] sep;

        if ( (str == null) || (str.length() == 0) )
        {
            return new String[0];
        }

        chs = str.toCharArray();
        count = 0;
        vSep = new ArrayList<Integer>();
        for ( int i = 0; i < chs.length; i++ )
        {
            if ( chs[i] == c )
            {
                count++;
                vSep.add( new Integer( i ) );
            }
        }

        sep = new int[count + 2];
        for ( int i = 0; i < count; i++ )
        {
            sep[i + 1] = ((Integer)vSep.get( i )).intValue();
        }
        sep[0] = -1;
        sep[count + 1] = str.length();

        ret = new String[count + 1];
        for ( int i = 0; i < ret.length; i++ )
        {
            ret[i] = str.substring( sep[i] + 1, sep[i + 1] );
        }
        return ret;
    }

    /**
     * 将字符串以指定字符串隔离，返回字符串数组
     * 
     * @param str
     *            需要隔开的字符串
     * @param delimiter
     *            指定使用的隔离字符串
     * @return 隔离后的字符串数组
     */
    public static String[] split( String str, String delimiter )
    {
        ArrayList<String> array = new ArrayList<String>();
        int index = 0;
        int begin = 0, end;
        String[] result = new String[0];

        if ( isBlank( str ) )
        {
            return new String[0];
        }

        while ( true )
        {
            index = str.indexOf( delimiter, begin );

            if ( index == begin )
            {
                if ( index >= 0 )
                {
                    array.add( "" );
                }
                begin += delimiter.length();
            } else if ( index > begin )
            {
                end = index;

                array.add( str.substring( begin, end ) );

                begin = index + delimiter.length();
            } else
            {
                if ( (begin >= 0) && (begin < str.length()) )
                {
                    array.add( str.substring( begin ) );
                }

                break;
            }
        }

        if ( str.endsWith( delimiter ) )
        {
            array.add( "" );
        }
        if ( array.size() > 0 )
        {
            result = new String[array.size()];

            array.toArray( result );
        }

        return result;
    }

    /**
     * 将字符串的某个字符串替换成另一个字符串，返回替换后的字符串
     * 
     * @param src
     *            需要替换的字符串
     * @param oldStr
     *            需要被替换的字符串
     * @param newStr
     *            需要替换成的字符串
     * @return 替换后的字符串
     */
    public static String replace( String src, String oldStr, String newStr )
    {
        int index;
        StringBuffer buffer;

        if ( isBlank( src ) || isBlank( oldStr ) || (newStr == null) )
        {
            return src;
        }

        buffer = new StringBuffer( src );
        index = src.length();

        while ( (index = src.lastIndexOf( oldStr, index )) >= 0 )
        {
            buffer.replace( index, index + oldStr.length(), newStr );

            index = index - oldStr.length();
        }

        return buffer.toString();
    }

    /**
     * 将clob字段内容取出转化为String类型，备注：此方法主要用于oracle数据库
     * 
     * @param clob
     *            clob内容
     * @return 转化后的字符串
     */
    public static final String clobToString( Clob clob )
    {
        String s = "";
        String result = "";
        if ( clob != null )
        {
            java.io.Reader is = null;
            java.io.BufferedReader br = null;
            try
            {
                is = clob.getCharacterStream();
                br = new java.io.BufferedReader( is );
                while ( s != null )
                {
                    try
                    {
                        s = br.readLine();
                    } catch ( IOException e )
                    {
                        return "";
                    }
                    result = result + s;
                }
            } catch ( SQLException e )
            {
                return "";
            } finally
            {
                try
                {
                    br.close();
                    is.close();
                } catch ( IOException e )
                {
                    return "";
                }
            }
            result = result.substring( 0, result.length() - 4 );
        }
        return result;
    }

    /**
     * 将数字字符串+默认加入值（当前默认加入值为1，默认返回值为0） 后重新转换成字符串 如 0099 +1=0100
     * 
     * @param str
     *            需要转换的字符串
     * @return 转换后的字符串
     */
    public static String transformString( String str )
    {
        String defaultResult = "0";
        int defaultAddValue = 1;
        return transformString( str, defaultResult, defaultAddValue );
    }

    /**
     * 将数字字符串加上默认加入值后转换成字符串 如 0099 +1=0100
     * 
     * @param str
     *            需要转换的字符串
     * @param defaultResult
     *            字符串默认值
     * @param defaultAddValue
     *            字符串默认需要加入值
     * @return 转换后的字符串
     */
    public static final String transformString( String str,
            String defaultResult, int defaultAddValue )
    {
        int flag = 0;
        if ( isBlank( str ) )
        {
            return defaultResult;
        }
        try
        {
            Long.parseLong( str );
        } catch ( Exception e )
        {
            return defaultResult;
        }
        for ( int i = 0; i < str.length(); i++ )
        {
            String indexValue = str.substring( i, i + 1 );
            if ( indexValue.equals( "0" ) )
            {
                flag++;
            } else
            {
                break;
            }
        }
        defaultResult = str.substring( 0, flag )
                + String
                        .valueOf( (Long.parseLong( str.substring( flag ) ) + defaultAddValue) );
        return defaultResult;
    }

    /**
     * @Description 将对象去空， 如果对象为日期类型，将对象保存为当前日期， 如果为Integer类型，保存为0，
     *              如果为String类型，保存为""
     * @param obj
     *            需要转换的对象
     * @return
     * @history
     */
    public static Object toObject( Object obj )
    {
        if ( isBlank( obj ) )
        {
            if ( obj instanceof Calendar )
            {
                obj = Calendar.getInstance();
            } else if ( obj instanceof Integer )
            {
                obj = new Integer( 0 );
            } else if ( obj instanceof String )
            {
                obj = "";
            }
        }
        return obj;
    }

    /**
     * @Description 将StringBuffer 后面加上换行
     * @param buf
     *            需要加换行的StringBuffer
     * @param content
     *            添加的内容
     * @history
     */
    public static void genStringBuffer( StringBuffer buf, Object content )
    {
        buf.append( content ).append( "\r\n" );
    }

}
