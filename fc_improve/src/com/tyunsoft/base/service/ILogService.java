
package com.tyunsoft.base.service;

/**
 * 日志操作业务层接口
 * 
 * @author Flymz
 */
public interface ILogService
{

    /**
     * 保存日志记录到日志表中
     * 
     * @param info
     *            日志信息
     * @param userID
     *            操作者
     * @param level
     *            日志级别
     * @param type
     *            操作业务分类
     */
    void log( String info, String userId, String level, String type );

}
