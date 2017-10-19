package com.tyunsoft.base.loginlogger.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.annotation.dao.IAnnotaionDao;
import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.loginlogger.entity.LoginLogger;
import com.tyunsoft.base.loginlogger.service.ILoginLoggerService;
import com.tyunsoft.base.utils.Pager;
import com.tyunsoft.base.utils.SqlFactory;

/**
 * 登录日志相关的业务层接口实现
 * 
 * @author  flymz
 * @version  [v1.0, 2014年09月25日]
 */
@Service
public class LoginLoggerServiceImpl implements ILoginLoggerService
{
    
    @Autowired
    private IDao queryDao;
    
    @Autowired
    private IAnnotaionDao dao;

    /**
     * 查询分页记录数
     * @param pageNumber 请求页数
     * @param pageSize 每页记录数
     * @param conditions 查询条件
     * @return 查询的分页对象
     */
    public Pager list( int pageNumber, int pageSize, List<SearchCondition> conditions  )
    {
        Pager pager = new Pager();
        int count = queryDao.findForInt( getSql("sql_query_loginlogger_count") );
        pager.setTotal( count );
        SqlRowSet rs = queryDao.find( getPageSql("sql_query_loginlogger_list",pageNumber,pageSize) );
        List<LoginLogger> loggers = new ArrayList<LoginLogger>();
        LoginLogger logger = null;
        while(rs.next()){
            logger = new LoginLogger();
            logger.setId( rs.getString( "id" ) );
            logger.setLastLoginTime( rs.getDate( "last_login_time" ) );
            logger.setLoginIp( rs.getString( "login_ip" ) );
            logger.setUserId( rs.getString( "user_id" ) );
            loggers.add( logger );
        }
        pager.setRows( loggers );
        return pager;
    }
    
    /**
     * 一次性查询表中所有数据
     * @return 表中所有数据
     */
    public List<Object> listAll()
    {
        return dao.queryAllList(LoginLogger.class);
    }

    /**
     * 根据主键查询对象
     * @param id 主键编码
     * @return 查询的对象
     */
    public Object queryById( String id )
    {
        LoginLogger loginLogger = new LoginLogger();
        loginLogger.setId( id );
        return dao.queryByKey( LoginLogger.class, loginLogger );
    }

    /**
     * 根据主键删除对象
     * @param id 主键编码
     * @return 删除是否成功
     */
    public boolean deleteById( String id )
    {
        LoginLogger loginLogger = new LoginLogger();
        loginLogger.setId( id );
        int result = dao.deleteByKey( loginLogger );
        return result == 1;
    }
    
    /**
     * 删除表中所有记录
     * @return 删除是否成功
     */
    public int deleteAll()
    {
    	LoginLogger loginLogger = new LoginLogger();
    	return dao.deleteAll( loginLogger );
    }

    /**
     * 新增登录日志记录
     * @param conveneNews 登录日志对象 
     * @return 新增是否成功
     */
    public boolean insert( LoginLogger loginLogger )
    {
        int result = dao.insert( loginLogger );
        return result == 1;
    }

    /**
     * 根据ID主键更新信息
     * @param conveneNews 登录日志对象
     * @return 更新是否成功
     */
    public boolean updateById( LoginLogger loginLogger )
    {
        int result = dao.updateByKey( loginLogger );
        return result == 1;
    }
    
    /**
     * 根据用户登录信息
     * @param loginLogger 登录日志信息
     * @return 记录是否成功
     */
    public boolean updateUserLogin(LoginLogger loginLogger)
    {
        int result = dao.executeUpdate( getSql("sql_update_login_user_by_userid"), new Object[]{} );
        return result == 1;
    }
    
    private String getSql(String sqlId)
    {
        return SqlFactory.getInstance( "system_sql.xml", LoginLoggerServiceImpl.class ).getSql( sqlId );
    }
    
    private String getPageSql(String sqlId, int pageNumber ,int pageSize)
    {
        return SqlFactory.getInstance( "system_sql.xml", LoginLoggerServiceImpl.class ).getPageSql( sqlId, pageNumber, pageSize );
    }

}
