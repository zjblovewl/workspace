
package com.tyunsoft.base.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.service.ILogService;
import com.tyunsoft.base.utils.IDUtil;
import com.tyunsoft.base.utils.SqlFactory;

/**
 * 日志管理业务层实现
 * 
 * @author Flymz
 */
@Service
public class LogServiceImpl implements ILogService
{

    @Autowired
    private IDao dao;

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
    public void log( String info, String userId, String level, String type )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_insert_log" );
        dao.add( sql, new Object[] {IDUtil.getUUIDStr(), level, type, info,
                new Date(), userId} );

    }

}
