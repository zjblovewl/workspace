package com.tyunsoft.base.service;

import java.util.List;

import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.entity.Setter;
import com.tyunsoft.base.utils.Pager;

/**
 * 系统设置相关的业务层接口
 * 
 * @author  flymz
 * @version  [v1.0, 2014年12月26日]
 */
public interface ISetterService
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
     * 新增系统设置记录
     * @param conveneNews 系统设置对象 
     * @return 新增是否成功
     */
    boolean insert( Setter setter );

	/**
     * 根据ID主键更新信息
     * @param conveneNews 系统设置对象
     * @return 更新是否成功
     */
    boolean updateById(  Setter setter  );
    
    /**
     * 检查设置名称是否存在
     * @param id 设置编码
     * @param name 设置名称
     * @return 是否存在
     */
    boolean checkNameExist(String id,String name);

}
