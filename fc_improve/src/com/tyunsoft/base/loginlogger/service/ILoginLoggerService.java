package com.tyunsoft.base.loginlogger.service;

import java.util.List;

import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.loginlogger.entity.LoginLogger;
import com.tyunsoft.base.utils.Pager;

/**
 * 登录日志相关的业务层接口
 * 
 * @author  flymz
 * @version  [v1.0, 2014年09月25日]
 */
public interface ILoginLoggerService
{
	
	/**
     * 查询分页记录数
     * @param pageNumber 请求页数
     * @param pageSize 每页记录数
     * @param conditions 查询条件
     * @return 查询的分页对象
     */
    Pager list( int pageNumber, int pageSize, List<SearchCondition> conditions );
    
    /**
     * 一次性查询表中所有数据
     * @return 表中所有数据
     */
    List<Object> listAll();

	/**
     * 根据主键查询对象
     * @param id 主键编码
     * @return 查询的对象
     */
    Object queryById( String id );
	
	/**
     * 根据主键删除对象
     * @param id 主键编码
     * @return 删除是否成功
     */
    boolean deleteById( String id );
    
    /**
     * 删除表中所有记录
     * @return 删除影响的记录数
     */
    int deleteAll();

	/**
     * 新增登录日志记录
     * @param conveneNews 登录日志对象 
     * @return 新增是否成功
     */
    boolean insert( LoginLogger loginLogger );

	/**
     * 根据ID主键更新信息
     * @param conveneNews 登录日志对象
     * @return 更新是否成功
     */
    boolean updateById(  LoginLogger loginLogger  );
    
    /**
     * 根据用户登录信息
     * @param loginLogger 登录日志信息
     * @return 记录是否成功
     */
    boolean updateUserLogin(LoginLogger loginLogger);

}
