
package com.tyunsoft.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.tyunsoft.base.entity.FileEntity;


/**
 * 从数据库对记录进行相关操作Dao层接口
 * 
 * @author Flymz
 */
public interface IDao
{

    /**
     * 新增记录
     * 
     * @param sql
     *            记录SQL语句
     * @param params
     *            记录参数
     * @return 新增记录数
     */
    int add( String sql, Object[] params );

    /**
     * 删除记录
     * 
     * @param sql
     *            记录SQL语句
     * @param id
     *            记录编号
     * @return 删除记录数
     */
    int delete( String sql, String id );

    /**
     * 更新记录
     * 
     * @param sql
     *            记录SQL语句
     * @param params
     *            记录参数
     * @return 更新记录数
     */
    int update( String sql, Object[] params );

    /**
     * 更新记录
     * 
     * @param sql
     *            记录SQL语句
     * @return 更新记录数
     */
    int update( String sql );

    /**
     * 查询记录单条数
     * 
     * @param sql
     *            查询记录SQL语句
     * @param params
     *            查询记录参数
     * @return 返回记录对象
     */
    SqlRowSet find( String sql, Object[] params );

    /**
     * 查询单条记录
     * 
     * @param sql
     *            查询的语句
     * @return 返回记录对象
     */
    SqlRowSet find( String sql );

    /**
     * 查询返回的int型数据
     * 
     * @param sql
     *            查询记录SQL语句
     * @param params
     *            查询记录参数
     * @return 返回的int型数据
     */
    int findForInt( String sql, Object[] params );

    /**
     * 查询返回的int型数据
     * 
     * @param sql
     *            查询记录SQL语句
     * @return 返回的int型数据
     */
    int findForInt( String sql );

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
     * 批量更新数据
     * @param sql sql语句
     * @param params 参数对象
     */
    void batchUpdateByObject(String sql,final List<Object[]> params);
    
    /**
     * 查询oracle数据库BLOB字段内容
     * 
     * @param sql
     *            SQL语句
     * @param blobField
     *            数据库中的BLOB字段
     */
    List<byte[]> findBlobs( String sql, Object[] params, final String blobField );

    /**
     * 根据编码更新BLOB字段内容
     * 
     * @param sql
     *            SQL语句
     * @param content
     *            内容
     * @param id
     *            编码
     */
    void updateBlob( String sql, final byte[] content, final String id );

    /**
     * 根据SQL查询CLOB字段内容
     * 
     * @param sql
     *            SQL语句
     * @param params
     *            参数
     * @param clobFiled
     *            CLOB字段
     * @return clob转换后的字符串List
     */
    List<String> findClob( String sql, Object[] params, final String clobFiled );
    
    
    /**
     * 根据对象，带blob内容
     * @param sql SQL语句
     * @param params 参数
     */
    void updateWithBlob(String sql,final Object[] params);
    
    /**
     * 查询blob和对应的名称内容
     * @param sql SQL语句
     * @param params 条件
     * @param nameField 名称字段
     * @param field 内容字段
     * @return 内容
     */
    FileEntity findBlobAndName(String sql,Object[] params,String nameField,String field);
}
