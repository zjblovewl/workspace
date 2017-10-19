
package com.tyunsoft.base.entity;

import net.sf.json.JSONObject;

import com.tyunsoft.base.utils.Logger;

/**
 * 日志信息实体类
 * 
 * @author flymz
 */
public class Log
{

    /**
     * 日志编码
     */
    private String logId;

    /**
     * 日志类型
     */
    private String logType;

    /**
     * 日志描述内容
     */
    private String logDesc;

    /**
     * 日志时间
     */
    private String logDate;

    /**
     * 日志操作用户
     */
    private String logUser;

    /**
     * 日志级别
     */
    private String logLevel;

    public String getLogId()
    {
        return logId;
    }

    public void setLogId( String logId )
    {
        this.logId = logId;
    }

    public String getLogType()
    {
        return logType;
    }

    public void setLogType( String logType )
    {
        this.logType = logType;
    }

    public String getLogDesc()
    {
        return logDesc;
    }

    public void setLogDesc( String logDesc )
    {
        this.logDesc = logDesc;
    }

    public String getLogDate()
    {
        return logDate;
    }

    public void setLogDate( String logDate )
    {
        this.logDate = logDate;
    }

    public String getLogUser()
    {
        return logUser;
    }

    public void setLogUser( String logUser )
    {
        this.logUser = logUser;
    }

    public String getLogLevel()
    {
        return Logger.INFO.equals( logLevel ) ? "普通日志" : "错误日志";
    }

    public void setLogLevel( String logLevel )
    {
        this.logLevel = logLevel;
    }

    public String toString()
    {
        return JSONObject.fromObject( this ).toString();
    }

}
