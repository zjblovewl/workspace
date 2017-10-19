
package com.tyunsoft.base.utils;

import java.util.ResourceBundle;

/**
 * <p>
 * Package: com.common.util
 * <p>
 * File: Read.java
 * </p>
 * <p>
 * Date:2008-6-1
 * </p>
 * <p>
 * Description: 读取文件中对应键值基础类
 * </p>
 * 
 * @author flymz
 * @version 1.0
 * @history
 */
public class Read
{
    private static String paths = "set";

    /**
     * 设置需要读取的*.properties文件路径
     * 
     * @param path
     *            需要读取的文件路径.如果读取的是*.properties文件,则可以使用getMsg()方法
     *            设置路径例如：com.flymz.common.configs.configs
     *            如果读取的是其他文件，则需要使用getMsgAllFile
     *            ()方法,设置路径例如：com/flymz/common/configs/bb.bb,其中bb.bb是文件名称
     */
    public static void setPath( String path )
    {
        Read.setPaths( path );
    }

    private static void setPaths( String path )
    {
        Read.paths = path;
    }

    ResourceBundle resources = ResourceBundle.getBundle( paths );
    
    
    /**
     * 获取properties的数据值
     * @param path properties文件名称，不包括后缀
     * @param key 键
     * @return 数据值
     */
    public static String getMsg(String propertiesName,String key)
    {
    	ResourceBundle resources = ResourceBundle.getBundle( propertiesName );
    	return resources.getString(key);
    }

    /**
     * 读取*.properties文件
     * 
     * @param key
     *            读取的properties文件字段值名字
     * @return 读取的对应名称的值
     */
    public static String getMsg( String key )
    {
        return new Read().getMsgs( key );
    }

    private String getMsgs( String key )
    {
        return resources.getString( key );
    }

}
