
package com.tyunsoft.base.utils;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public final class JsonUtil
{

    /**
     * list转换成json并且输出
     * 
     * @param response
     *            输出
     * @param list
     *            需要转换的list
     */
    public static final void list2Json( HttpServletResponse response, List<?> list )
    {
        JSONArray jsonArray = JSONArray.fromObject( list );
        try
        {
            response.setCharacterEncoding( "UTF-8" );
            Logger.getLogger().consoleDebug( jsonArray.toString() );
            response.getWriter().write( jsonArray.toString() );

        } catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
    
    /**
     * list转换成json并且输出
     * 
     * @param response
     *            输出
     * @param list
     *            需要转换的list
     */
    public static final void list2JsonForDate( HttpServletResponse response, List<?> list ){
        JsonConfig jsonConfig = new JsonConfig();  
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));  
        JSONArray jsonArray = JSONArray.fromObject( list ,jsonConfig);
        try
        {
            response.setCharacterEncoding( "UTF-8" );
            Logger.getLogger().consoleDebug( jsonArray.toString() );
            response.getWriter().write( jsonArray.toString() );

        } catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
    
    public static String list2JsonStr(List<?> list)
    {
        JsonConfig jsonConfig = new JsonConfig();  
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));  
        JSONArray jsonArray = JSONArray.fromObject( list ,jsonConfig);
        return  jsonArray.toString() ;
    }

    /**
     * 将list转化为json并且返回字符串
     * 
     * @param list
     *            需要转换的List
     * @return json字符串
     */
    public static final String list2Json( List<?> list )
    {
        JSONArray jsonArray = JSONArray.fromObject( list );
        return jsonArray.toString();
    }

    /**
     * 对象转换成Json
     * @param response 响应
     * @param object 需要转换的对象
     */
    public static final void bean2Json( HttpServletResponse response,
            Object object )
    {
        JSONObject jsonObject = JSONObject.fromObject( object );
        try
        {
            response.setCharacterEncoding( "UTF-8" );
            Logger.getLogger().consoleDebug( jsonObject.toString() );
            response.getWriter().write( jsonObject.toString() );

        } catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
    
    public static final String bean2JsonStr(Object object)
    {
        JsonConfig jsonConfig = new JsonConfig();  
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));  
        JSONObject jsonObject = JSONObject.fromObject( object,jsonConfig );
        return  jsonObject.toString() ;
    }
    
    /**
     * 包含日期格式的对象转换成Json
     * @param response 响应
     * @param object 需要转换的对象
     */
    public static final void bean2JsonForDate( HttpServletResponse response,
            Object object )
    {
        JsonConfig jsonConfig = new JsonConfig();  
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));  
        JSONObject jsonObject = JSONObject.fromObject( object,jsonConfig );
        try
        {
            response.setCharacterEncoding( "UTF-8" );
            Logger.getLogger().consoleDebug( jsonObject.toString() );
            response.getWriter().write( jsonObject.toString() );

        } catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    /**
     * 输出boolean型变量，实质是转换成字符串进行输入
     * 
     * @param response
     *            响应
     * @param result
     *            输出值
     */
    public static final void boolOut( HttpServletResponse response,
            boolean result )
    {
        strOut( response, String.valueOf( result ) );
    }

    /**
     * 输出字符串
     * 
     * @param response
     *            响应
     * @param str
     *            输出值
     */
    public static final void strOut( HttpServletResponse response, String str )
    {
        try
        {
            response.setCharacterEncoding( "UTF-8" );
            response.getWriter().write( str );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}
