
package com.tyunsoft.base.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

/**
 * <p>
 * Package: com.common.util
 * <p>
 * File: DateUtil.java
 * </p>
 * <p>
 * Date:2008-6-1
 * </p>
 * <p>
 * Description: 关于日期的操作基础类
 * </p>
 * 
 * @author flymz
 * @version 1.0
 * @history
 */
public class DateUtil implements Converter
{
    private static String DEFAULT_FORMAT = "yyyy-MM-dd";

    public static final String TS_FORMAT = "yyyy-MM-dd HH:mm";
    
    public static final String FORMAT_CHINESE = "yyyy年MM月dd日";

    /**
     * 常用日期格式
     */
    private static String[] dateFormat = {"yyyy-MM-dd HH:mm:ss",
            "yyyy/MM/dd HH:mm:ss", "yyyy年MM月dd日HH时mm分ss秒", "yyyy-MM-dd",
            "yyyy/MM/dd", "yy-MM-dd", "yy/MM/dd", "yyyy年MM月dd日", "HH:mm:ss",
            "yyyyMMddHHmmss", "yyyyMMdd", "yyyy.MM.dd", "yy.MM.dd",};

    private static SimpleDateFormat simpleDateFormat;

    /**
     * 将传入的时间(java.util.Date)类型按照指定的类型进行格式化,默认格式化类型为yyyy-MM-dd
     * 
     * @param date
     *            需要格式化的时间
     * @param format
     *            格式化类型
     * @return 返回格式化以后的时间字符串
     */
    public static String getDateStr( Date date, String format )
    {
        if ( date == null )
        {
            return null;
        }
        format = format == null || format.equals( "" ) ? DEFAULT_FORMAT
                : format;
        simpleDateFormat = new SimpleDateFormat( format );
        String result = simpleDateFormat.format( date );
        return result;
    }

    /**
     * 将当前时间格式化成指定的的类型,默认格式化类型为yyyy-MM-dd
     * 
     * @param format
     *            格式化类型
     * @return 格式化后的当前时间字符串
     */
    public static String getCurrentDateStr( String format )
    {
        format = format == null || format.equals( "" ) ? DEFAULT_FORMAT
                : format;
        simpleDateFormat = new SimpleDateFormat( format );
        String result = simpleDateFormat.format( new Date() );
        return result;
    }

    /**
     * 判断参数 date 是否为合法日期,日期格式:yyyy-MM-dd
     * 
     * @param date
     *            欲判断的日期
     * @return 若参数为合法日期,返回 true; 若不合法则返回 false;
     */
    public static boolean isLegalDate( String date )
    {
        try
        {

            String[] tempDate = date.split( "-" );

            int year = Integer.parseInt( tempDate[0] );
            int month = Integer.parseInt( tempDate[1] );
            int day = Integer.parseInt( tempDate[2] );

            GregorianCalendar gregorianCalendar = new GregorianCalendar();

            gregorianCalendar.set( year, month - 1, 1 );

            int maxiumDay = gregorianCalendar
                    .getActualMaximum( GregorianCalendar.DAY_OF_MONTH );

            if ( day > maxiumDay || day <= 0 )
            {
                return false;
            }

        } catch ( NumberFormatException nfe )
        {
            return false;
        } catch ( Exception e )
        {
            return false;
        }

        return true;
    }

    /**
     * 判断传入的Date的前后，返回后来的Date 例如：2007-02-03 和 2007-05-06 返回的是 后者
     * 
     * @param date0
     *            传入的日期1
     * @param date1
     *            传入的日期2
     * @return 返回较后的日期
     */
    public static Date compareMax( Date date0, Date date1 )
    {
        if ( date0 == null || date1 == null )
        {
            return null;
        } else if ( date0.getTime() > date1.getTime() )
        {
            return date0;
        } else
        {
            return date1;
        }
    }

    /**
     * 判断传入的Date的前后，返回前面的Date 例如：2007-02-03 和 2007-05-06 返回的是 前者
     * 
     * @param date0
     *            传入的日期1
     * @param date1
     *            传入的日期2
     * @return 返回较前的日期
     */
    public static Date compareMin( Date date0, Date date1 )
    {
        if ( date0 == null || date1 == null )
        {
            return null;
        } else if ( date0.getTime() > date1.getTime() )
        {
            return date1;
        } else
        {
            return date0;
        }
    }

    /**
     * 比较两个时间是否相等，相等返回true，否则返回false
     * 
     * @param date0
     *            传入的日期1
     * @param date1
     *            传入的日期2
     * @return 返回传入的两个日期是否相等的boolean值
     */
    public static boolean compareEqual( Date date0, Date date1 )
    {
        if ( date0 == null && date1 == null )
        {
            return true;
        } else if ( date0 == null || date1 == null )
        {
            return false;
        } else
        {
            return date0.getTime() == date1.getTime();
        }
    }

    /**
     * 将日期字符串(2008-03-12)格式化成java.sql.Date类型
     * 
     * @param date
     *            字符串类型的日期
     * @return 返回java.sql.Date类型的日期
     */
    public static java.sql.Date parseStrToSql( String date )
    {
        StringTokenizer st = new StringTokenizer( date, "-" );
        int year = Integer.parseInt( st.nextToken() );
        int month = Integer.parseInt( st.nextToken() );
        month--;
        int day = Integer.parseInt( st.nextToken() );
        Calendar calendar = Calendar.getInstance();
        calendar.set( year, month, day );
        java.util.Date utilDate = calendar.getTime();
        long milliSecond = utilDate.getTime();
        return new java.sql.Date( milliSecond );
    }

    /**
     * 返回当前日期和星期字符串 例如：2008年3月13日 星期四
     * 
     * @return String 返回今天的日期和星期字符串
     */
    public static String todayStringWithWeek()
    {
        Calendar now = Calendar.getInstance();
        String[] week = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        return now.get( Calendar.YEAR ) + "年" + (now.get( Calendar.MONTH ) + 1)
                + "月" + now.get( Calendar.DATE ) + "日 "
                + week[now.get( Calendar.DAY_OF_WEEK ) - 1];
    }

    /**
     * 获得指定天数的星期
     * 
     * @param date
     *            指定的天数 格式为：yyyy-MM-dd
     * @return 返回指定天数的星期数
     */
    public static String getWeekByDate( String date )
    {
        Calendar now = Calendar.getInstance();
        if ( isLegalDate( date ) )
        {
            now.setTime( getDateFromStr( date ) );
        } else
        {
            return null;
        }
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String week = weeks[now.get( Calendar.DAY_OF_WEEK ) - 1];
        return week;
    }

    /**
     * 获取当前的星期
     * 
     * @param date
     *            指定日期
     * @return 从星期日-星期六 分别是0-6
     */
    public static int getWeekByDate( Date date )
    {
        Calendar now = Calendar.getInstance();
        now.setTime( date );
        return now.get( Calendar.DAY_OF_WEEK );
    }

    /**
     * 给定一个字符串，获得指定日期。指定日期字符串格式为：yyyy－MM－dd。
     * 
     * @param date
     *            指定的日期字符串格式
     * @return 转换后的日期格式
     */
    public static Date getDateFromStr( String date )
    {
        if ( StringUtil.isBlank( date ) )
        {
            return null;
        }
        return parseDate( date, 3 );
    }

    /**
     * 给定一个字符串，获得指定日期。
     * 
     * @param date
     *            指定的日期字符串格式
     * @return 转换后的日期格式
     */
    public static Date parseDate( String dateStr, int index )
    {
        DateFormat df = null;
        try
        {
            df = new SimpleDateFormat( dateFormat[index] );

            return df.parse( dateStr );
        } catch ( ParseException pe )
        {
            return parseDate( dateStr, index + 1 );
        } catch ( ArrayIndexOutOfBoundsException aioe )
        {
            return null;
        }
    }

    /**
     * 两个日期相减，返回相差的天数
     * 
     * @param startDate
     *            开始日期
     * @param endDate
     *            结束日期
     * @return 返回两个日期相减的天数
     */
    public static long subtract( String startDate, String endDate )
    {
        simpleDateFormat = new SimpleDateFormat( DEFAULT_FORMAT );
        java.util.Date date;
        java.util.Date mydate;
        long day = 0;
        try
        {
            date = simpleDateFormat.parse( startDate );
            mydate = simpleDateFormat.parse( endDate );
            day = (mydate.getTime() - date.getTime()) / (24 * 60 * 60 * 1000);
        } catch ( ParseException e )
        {
            return 0;
        }
        return day;
    }

    /**
     * 一个日期减去指定天数,获得另一个日期
     * 
     * @param date
     *            传入的日期
     * @param day
     *            需要减去的天数
     * @return 传入的日期减去需要减去的日期后所获得的日期
     */
    public static Date getAnotherDateBySubtract( Date date, long day )
    {
        java.util.Date retDate;
        long time;
        time = date.getTime() - day * 24 * 60 * 60 * 1000;
        retDate = new Date( time );
        return retDate;
    }

    /**
     * 一个日期加上指定天数,获得另一个日期
     * 
     * @param date
     *            传入的日期
     * @param day
     *            需要加上的天数
     * @return 传入的日期加上需要减去的日期后所获得的日期
     */
    public static Date getAnotherDateByAdd( Date date, long day )
    {
        java.util.Date retDate;
        long time;
        time = date.getTime() + day * 24 * 60 * 60 * 1000;
        retDate = new Date( time );
        return retDate;
    }

    /**
     * @Description 将字符串转化成Calendar格式
     * @param dateStr
     *            需要转化的字符串
     * @return
     * @history
     */
    public static Calendar parseDate( String dateStr )
    {
        if ( dateStr == null || dateStr.trim().length() == 0 )
            return null;

        Date result = parseDate( dateStr, 0 );
        Calendar cal = Calendar.getInstance();
        cal.setTime( result );

        return cal;
    }

    /**
     * 数据的类型转换，包括转换成Timestamp、Date以及String类型
     * 
     * @param type
     *            转换的类型 如Date.class
     * @param value
     *            需要转换的对象
     * @return 经过转换后的对象
     */
    public Object convert( @SuppressWarnings( "rawtypes" ) Class type, Object value )
    {
        if ( value == null )
        {
            return null;
        } else if ( type == Timestamp.class )
        {
            return convertToDate( type, value, TS_FORMAT );
        } else if ( type == Date.class )
        {
            return convertToDate( type, value, "YYYY-MM-DD" );
        } else if ( type == String.class )
        {
            return convertToString( type, value );
        }

        throw new ConversionException( "不能从 " + value.getClass().getName()
                + " 类型转换到 " + type.getName() + "类型。" );
    }

    /**
     * 将日期格式从 java.util.Timestamp 转到 java.util.Calendar 格式
     * 
     * @param date
     *            java.sql.Timestamp 格式表示的日期
     * @return java.util.Calendar 格式表示的日期
     */
    public static java.util.Calendar convTimestampToCalendar(
            java.sql.Timestamp date )
    {
        if ( date == null )
            return null;
        else
        {
            java.util.GregorianCalendar gc = new java.util.GregorianCalendar();
            gc.setTimeInMillis( date.getTime() );
            return gc;
        }
    }

    @SuppressWarnings( "unchecked" )
    protected Object convertToDate( Class type, Object value, String pattern )
    {
        DateFormat df = new SimpleDateFormat( pattern );
        if ( value instanceof String )
        {
            try
            {
                if ( StringUtil.isBlank( value ) )
                {
                    return null;
                }

                Date date = df.parse( (String)value );
                if ( type.equals( Timestamp.class ) )
                {
                    return new Timestamp( date.getTime() );
                }
                return date;
            } catch ( Exception pe )
            {
                pe.printStackTrace();
                throw new ConversionException( "String转换Date过程中发生异常。" );
            }
        }

        throw new ConversionException( "不能从 " + value.getClass().getName()
                + " 类型转换到 " + type.getName() + "类型。" );
    }

    @SuppressWarnings( "unchecked" )
    protected Object convertToString( Class type, Object value )
    {

        if ( value instanceof Date )
        {
            DateFormat df = new SimpleDateFormat( "YYYY-MM-DD" );
            if ( value instanceof Timestamp )
            {
                df = new SimpleDateFormat( TS_FORMAT );
            }

            try
            {
                return df.format( value );
            } catch ( Exception e )
            {
                e.printStackTrace();
                throw new ConversionException( "Date转换String过程中发生异常。" );
            }
        } else
        {
            return value.toString();
        }
    }
}
