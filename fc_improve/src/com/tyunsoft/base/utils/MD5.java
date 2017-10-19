
package com.tyunsoft.base.utils;

import java.security.MessageDigest;

/**
 * <p>
 * Package: com.studio.common
 * <p>
 * File: MD5.java
 * </p>
 * <p>
 * Date:2008-6-1
 * </p>
 * <p>
 * Description: 关于密码加密的操作基础类
 * </p>
 * 
 * @author flymz
 * @version 1.0
 * @history
 */
public class MD5
{
    /**
     * 组成加密字符串的字符数组
     */
    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n','o','p','q','r','s','t','u','v','w','x','y','z'};

    /**
     * 对传入的字符串(password)进行加密,返回后长度为32位
     * 
     * @param password
     *            原可见密码
     * @return 加密以后的密码信息
     */
    public static String password( String password )
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance( "MD5" );
            md.update( password.getBytes() );
            byte[] md1 = md.digest();
            int j = md1.length;
            char str[] = new char[j * 2];
            int k = 0;
            for ( int i = 0; i < j; i++ )
            {
                byte byte0 = md1[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String( str );
        } catch ( Exception e )
        {
            return null;
        }
    }

    public static void main( String[] args )
    {
        System.out.println( password( "123456" ) );
    }
}
