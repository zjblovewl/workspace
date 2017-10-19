
package com.tyunsoft.base.annotation.dao;

import java.util.List;
import java.util.Map;

import com.tyunsoft.base.entity.IEntity;
import com.tyunsoft.base.utils.Pager;

/**
 * 通用的Dao接口，提供一些简易的单表的增删改查功能,复杂的查询功能需要重新写 <br/>
 * 调用方式： dao.setDataSourceConfigCode("dataSourceConfigCode").insert(entity);
 * 
 * @author Flyer.zuo
 * @version [v1.0, 2014年7月29日]
 */
public interface IAnnotaionDao
{

    /**
     * 新增数据
     * 
     * @param entity
     *            需要新增的数据对象
     * @return 新增影响的记录数
     */
    int insert( IEntity entity );

    /**
     * 修改对象，从entity中解析出key字段，然后根据key字段修改对应的数据
     * 
     * @param entity
     *            对象实体类
     * @return 修改的记录数
     */
    int updateByKey( IEntity entity );

    /**
     * 删除对象，从entity中解析出key字段，然后根据key字段删除对应的数据
     * 
     * @param entity
     *            对象实体类
     * @return 删除的记录数
     */
    int deleteByKey( IEntity entity );
    
    /**
     * 删除所有数据
     * @param entity 对象实体类
     * @return 删除的记录数
     */
    int deleteAll(IEntity entity);
    
    /**
     * 执行更新操作
     * @param sql 更新的SQL语句
     * @param params 更新参数
     * @return 更新的记录数
     */
    int executeUpdate(String sql,Object[] params);
    
    /**
     * 根据SQL查询整型返回值
     * @param sql SQL语句
     * @param params 参数
     * @return 查询返回的数量
     */
    int queryCount(String sql, Object[] params);
    
    /**
     * 进行批量更新操作
     * 
     * @param sql
     *            SQL语句
     * @param params
     *            参数对象List
     * @return 每次更新成功记录数
     */
    int[] batchUpdate( String sql, final List<Map<String, String>> params );

    /**
     * 查询单一实例结果集，并且将结果封装到entity对象中
     * 
     * @param sql
     *            SQL语句
     * @param params
     *            参数
     * @param clazz
     *            实体对象，将数据封装到该对象中
     * @return 反对封装的对象，clazz对象
     */
    Object query( String sql, Object[] params, Class<?> clazz );

    /**
     * 根据SQL语句查询结果集
     * 
     * @param sql
     *            SQL语句
     * @param params
     *            参数
     * @param clazz
     *            将数据封装到该对象实体类，然后存入List中
     * @return 查询结果集
     */
    List<Object> queryList( String sql, Object[] params, Class<?> clazz );

    /**
     * 查询所有列表数据
     * 
     * @param clazz
     *            需要封装的对象
     * @return 返回查询的结果集
     */
    List<Object> queryAllList( Class<?> clazz );

    /**
     * 查询分页数据对象，对象中只能将基本属性信息封装进去
     * 
     * @param countSql
     *            记录数查询SQL
     * @param pageSql
     *            结果集查询SQL
     * @param params
     *            参数
     * @param clazz
     *            将数据封装到该对象实体类，然后存入List中
     * @return 分页数据对象
     */
    Pager queryPage( String countSql, String pageSql, Object[] params,
            Class<?> clazz );
    
    /**
     * 根据主键值查询指定对象
     * @param clazz 指定查询的类型
     * @param entity 封装了主键的对象
     * @return 指定结果对象
     */
    Object queryByKey(Class<?> clazz,IEntity entity);
    
    /**
     * 直接查询对象
     * @param sql sql语句
     * @param params 参数
     * @return 查询的list对象
     */
    List<Map<String,Object>> queryForList(String sql, Object[] params);

}
