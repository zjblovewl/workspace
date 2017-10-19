
package com.tyunsoft.base.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tyunsoft.base.service.ILogService;

/**
 * 系统日志调用静态类
 * 
 * @author Flymz
 */
@Component
public class Logger
{

    // 信息级别
    public static final String INFO = "info";

    // 错误级别
    public static final String ERROR = "error";

    // 调试Debug级别
    public static final String DEBUG = "debug";

    private static final String DEFAULT_SYSTEM_USER = "system";
    /**
     * 日志信息存储服务类
     */
    @Autowired
    private ILogService logService;

    private static String dataBaseLevels;

    private static String consoleLevels;

    private static Logger logger = new Logger();

    private Logger()
    {
    }

    /**
     * 获取logger对象
     * 
     * @return
     */
    public static Logger getLogger()
    {
        return logger;
    }

    static
    {
        dataBaseLevels = Read.getMsg( "system.log.database.levels" )
                .toLowerCase();
        consoleLevels = Read.getMsg( "system.log.console.levels" )
                .toLowerCase();
    }

    /**
     * info级别的日志查看
     * 
     * @param info
     *            日志信息
     * @param userId
     *            用户
     * @param type
     *            日志操作业务分类
     */
    public void info( String info, String userId, String type )
    {
        if ( null == userId )
        {
            userId = DEFAULT_SYSTEM_USER;
        }
        if ( dataBaseLevels.indexOf( INFO ) != -1 )
        {
            logService.log( info, userId, INFO, type );
        }
        if ( consoleLevels.indexOf( INFO ) != -1 )
        {
            System.out.println( "INFO(" + userId + "):" + info );
        }

    }

    /**
     * info级别的日志查看
     * 
     * @param info
     *            日志信息
     * @param userId
     *            用户
     * @param type
     *            日志操作业务分类
     */
    public void info( StringBuffer info, String userId, String type )
    {
        info( info.toString(), userId, type );
    }

    /**
     * error级别的日志查看和保存
     * 
     * @param error
     *            日志信息
     * @param userId
     *            用户
     * @param type
     *            日志操作业务分类
     */
    public void error( String error, String userId, String type )
    {
        if ( null == userId )
        {
            userId = DEFAULT_SYSTEM_USER;
        }
        if ( dataBaseLevels.indexOf( ERROR ) != -1 )
        {
            logService.log( error, userId, ERROR, type );
        }
        if ( consoleLevels.indexOf( ERROR ) != -1 )
        {
            System.out.println( "ERROR(" + userId + "):" + error );
        }
    }

    /**
     * error级别的日志查看和保存
     * 
     * @param error
     *            日志信息
     * @param userId
     *            用户
     * @param type
     *            日志操作业务分类
     */
    public void error( StringBuffer error, String userId, String type )
    {
        error( error.toString(), userId, type );
    }

    public void consoleError( String error, String userId )
    {
        if ( consoleLevels.indexOf( ERROR ) != -1 )
        {
            System.out.println( "ERROR(" + userId + "):" + error );
        }
    }

    public void consoleDebug( String debug )
    {
        if ( consoleLevels.indexOf( DEBUG ) != -1 )
        {
            System.out.println( "调试日志:" + debug );
        }
    }

    public void consoleDebug( Object object )
    {
        consoleDebug( object.toString() );
    }

    /**
     * debug级别的日志查看和保存
     * 
     * @param debug
     *            日志信息
     * @param userId
     *            用户
     * @param type
     *            操作业务分类
     */
    public void debug( String debug, String userId, String type )
    {
        if ( null == userId )
        {
            userId = DEFAULT_SYSTEM_USER;
        }
        if ( dataBaseLevels.indexOf( DEBUG ) != -1 )
        {
            logService.log( debug.toString(), userId, DEBUG, type );
        }
        if ( consoleLevels.indexOf( DEBUG ) != -1 )
        {
            System.out.println( "DEBUG(" + userId + "):" + debug );
        }
    }

    /**
     * debug级别的日志查看和保存
     * 
     * @param debug
     *            日志信息
     * @param userId
     *            用户
     * @param type
     *            日志操作业务分类
     */
    public void debug( StringBuffer debug, String userId, String type )
    {
        debug( debug.toString(), userId, type );
    }

}
