
package com.tyunsoft.base.entity;

import java.io.Serializable;
import java.util.Date;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

/**
 * 应用信息
 * 
 * @author flymz
 * @version [版本号, 2013-3-24]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
public class Application implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    // 应用编码
    private String appId;

    // 应用名称
    private String appName;

    // 应用链接
    private String appLink;

    // 应用高度 0表示自动适应
    private int appHeight;

    // 应用描述
    private String appDesc;

    // 是否可缩小 0为是，1为否
    private String appIsSmall;

    // 应用图标
    private String appIcon;

    // 应用创建者
    private String appCreator;

    // 创建日期
    private Date appCreateDate;
    
    //用户应用所在行
    private int appRow;
    
    //用户应用所在列
    private int appColumn;

    public String getAppId()
    {
        return appId;
    }

    public void setAppId( String appId )
    {
        this.appId = appId;
    }

    public String getAppName()
    {
        return appName;
    }

    public void setAppName( String appName )
    {
        this.appName = appName;
    }

    public String getAppLink()
    {
        return appLink;
    }

    public void setAppLink( String appLink )
    {
        this.appLink = appLink;
    }

    public int getAppHeight()
    {
        return appHeight;
    }

    public void setAppHeight( int appHeight )
    {
        this.appHeight = appHeight;
    }

    public String getAppDesc()
    {
        return appDesc;
    }

    public void setAppDesc( String appDesc )
    {
        this.appDesc = appDesc;
    }

    public String getAppIsSmall()
    {
        return appIsSmall;
    }
    
    public String getAppIsSmallStr()
    {
        return "0".equals( appIsSmall ) ? "是" : "否";
    }

    public void setAppIsSmall( String appIsSmall )
    {
        this.appIsSmall = appIsSmall;
    }

    public String getAppIcon()
    {
        return appIcon;
    }

    public void setAppIcon( String appIcon )
    {
        this.appIcon = appIcon;
    }

    public String getAppCreator()
    {
        return appCreator;
    }

    public void setAppCreator( String appCreator )
    {
        this.appCreator = appCreator;
    }

    public Date getAppCreateDate()
    {
        return appCreateDate;
    }

    public void setAppCreateDate( Date appCreateDate )
    {
        this.appCreateDate = appCreateDate;
    }

    public int getAppRow()
    {
        return appRow;
    }

    public void setAppRow( int appRow )
    {
        this.appRow = appRow;
    }

    public int getAppColumn()
    {
        return appColumn;
    }

    public void setAppColumn( int appColumn )
    {
        this.appColumn = appColumn;
    }

    public String toString()
    {
        return JSONObject.fromObject( this ).toString();
    }

}
