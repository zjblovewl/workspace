
package com.tyunsoft.base.utils;

import java.util.Date;

/**
 * <p>
 * Package: com.common.util
 * <p>
 * File: IDUtil.java
 * </p>
 * <p>
 * Date:2008-6-1
 * </p>
 * <p>
 * Description: 关于生成数据库主键ID的操作基础类
 * </p>
 * 
 * @author flymz
 * @version 1.0
 * @history
 */
public class IDUtil
{
    /**
     * 返回长整型的ID
     * 
     * @return 返回长整型的ID
     */
    public static final long getNextLongId()
    {
        Date date = new Date();
        long result = date.getTime();
        return result;
    }

    /**
     * 返回指定长度的UUID
     * 
     * @param len
     *            返回的UUID长度
     * @return 返回指定长度的UUID,最大长度为32位
     */
    public static final String getUUIDStr( int len )
    {
        long varl = 0;
        if ( len > 32 )
        {
            len = 32;
        }
        String UUID = Long.toHexString( new java.util.Date().getTime() )
                .toUpperCase();
        UUID += Long.toHexString( varl );
        varl++;

        while ( UUID.length() < len )
        {
            String random = String.valueOf( Math.random() );
            try
            {
                UUID += Long.toHexString(
                        Long.parseLong( random.substring( 2 ) ) ).toUpperCase();
            } catch ( NumberFormatException e )
            {
            }
        }
        UUID = UUID.substring( 0, len );
        return UUID;
    }

    /**
     * 返回默认位32位长度的UUID
     * 
     * @return 返回默认位32位长度的UUID
     */
    public static final String getUUIDStr()
    {
        return getUUIDStr( 32 );
    }
}
