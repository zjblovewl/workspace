package net.ycii.fc.service;

import java.util.List;

import net.ycii.fc.entity.PeopleThing;

import com.tyunsoft.base.utils.Pager;

/**
 * 民生实事相关的业务层接口
 * 
 * @author  flymz
 * @version  [v1.0, 2015年03月10日]
 */
public interface IPeopleThingService
{
	
	/**
     * 查询分页记录数
     * @param pageNumber 请求页数
     * @param pageSize 每页记录数
     * @param conditions 查询条件
     * @return 查询的分页对象
     */
    Pager list( int pageNumber, int pageSize, String userName,String dept,String startDate, String endDate,String town,String village );
    
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
     * 新增民生实事记录
     * @param conveneNews 民生实事对象 
     * @return 新增是否成功
     */
    boolean insert( PeopleThing peopleThing );

	/**
     * 根据ID主键更新信息
     * @param conveneNews 民生实事对象
     * @return 更新是否成功
     */
    boolean updateById(  PeopleThing peopleThing  );

}
