
package com.tyunsoft.base.dao.jdbc;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.entity.FileEntity;

/**
 * 从数据库对记录进行相关操作Dao层实现类
 * 
 * @author Flymz
 */
@Repository
public class DaoJdbc implements IDao
{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    final LobHandler lobHandler = new DefaultLobHandler();

    public void setJdbcTemplate( JdbcTemplate jdbcTemplate )
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 新增记录
     * 
     * @param sql
     *            记录SQL语句
     * @param params
     *            记录参数
     * @return 新增记录数
     */
    public int add( String sql, Object[] params )
    {
        return jdbcTemplate.update( sql, params );
    }

    /**
     * 更新记录
     * 
     * @param sql
     *            记录SQL语句
     * @param params
     *            记录参数
     * @return 更新记录数
     */
    public int update( String sql, Object[] params )
    {
        return jdbcTemplate.update( sql, params );
    }

    /**
     * 更新记录
     * 
     * @param sql
     *            记录SQL语句
     * @return 更新记录数
     */
    public int update( String sql )
    {
        return jdbcTemplate.update( sql );
    }

    /**
     * 删除记录
     * 
     * @param sql
     *            记录SQL语句
     * @param id
     *            记录编号
     * @return 删除记录数
     */
    public int delete( String sql, String id )
    {
        return jdbcTemplate.update( sql, new Object[] {id} );
    }

    /**
     * 查询记录单条数
     * 
     * @param sql
     *            查询记录SQL语句
     * @param params
     *            查询记录参数
     * @return 返回记录对象
     */
    public SqlRowSet find( String sql, Object[] params )
    {
        return jdbcTemplate.queryForRowSet( sql, params );
    }

    /**
     * 查询单条记录
     * 
     * @param sql
     *            查询的语句
     * @return 返回记录对象
     */
    public SqlRowSet find( String sql )
    {
        return jdbcTemplate.queryForRowSet( sql );
    }

    /**
     * 查询返回的int型数据
     * 
     * @param sql
     *            查询记录SQL语句
     * @param params
     *            查询记录参数
     * @return 返回的int型数据
     */
    public int findForInt( String sql, Object[] params )
    {
        return jdbcTemplate.queryForInt( sql, params );
    }

    /**
     * 查询返回的int型数据
     * 
     * @param sql
     *            查询记录SQL语句
     * @return 返回的int型数据
     */
    public int findForInt( String sql )
    {
        return jdbcTemplate.queryForInt( sql );
    }

    /**
     * 进行批量更新操作
     * 
     * @param sql
     *            SQL语句
     * @param params
     *            参数对象List
     * @return 每次更新成功记录数
     */
    public int[] batchUpdate( String sql, final List<Map<String, String>> params )
    {
        return jdbcTemplate.batchUpdate( sql,
                new BatchPreparedStatementSetter()
                {

                    @Override
                    public void setValues( PreparedStatement ps, int rowNum )
                            throws SQLException
                    {
                        Map<String, String> userMap = params.get( rowNum );
                        Iterator<String> it = userMap.keySet().iterator();
                        while ( it.hasNext() )
                        {
                            String key = it.next();
                            ps.setString( Integer.parseInt( key ), userMap
                                    .get( key ) );
                        }
                    }

                    @Override
                    public int getBatchSize()
                    {
                        return params.size();
                    }
                } );
    }
    
    /**
     * 批量更新数据
     * @param sql sql语句
     * @param params 参数对象
     */
    public void batchUpdateByObject(String sql,final List<Object[]> params)
    {
    	jdbcTemplate.batchUpdate(sql, params);
    }

    /**
     * 查询oracle数据库BLOB字段内容
     * 
     * @param sql
     *            SQL语句
     * @param blobField
     *            数据库中的BLOB字段
     */
    @SuppressWarnings("unchecked")
    public List<byte[]> findBlobs( String sql, Object[] params,
            final String blobField )
    {
        final List<byte[]> result = new ArrayList<byte[]>();
        jdbcTemplate.query( sql, params,
                new AbstractLobStreamingResultSetExtractor()
                {

                    @Override
                    protected void streamData( ResultSet rs )
                            throws SQLException, IOException,
                            DataAccessException
                    {
                        result.add( lobHandler.getBlobAsBytes( rs, blobField ) );
                    }

                } );
        return result;
    }

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
    @SuppressWarnings("unchecked")
    public List<String> findClob( String sql, Object[] params,
            final String clobFiled )
    {
        final List<String> result = new ArrayList<String>();
        jdbcTemplate.query( sql, params,
                new AbstractLobStreamingResultSetExtractor()
                {

                    @Override
                    protected void streamData( ResultSet rs )
                            throws SQLException, IOException,
                            DataAccessException
                    {
                        result
                                .add( lobHandler
                                        .getClobAsString( rs, clobFiled ) );
                    }

                } );
        return result;
    }

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
    public void updateBlob( String sql, final byte[] content, final String id )
    {
        jdbcTemplate.execute( sql,
                new AbstractLobCreatingPreparedStatementCallback( lobHandler )
                {

                    @Override
                    protected void setValues( PreparedStatement pstmt,
                            LobCreator lobCreator ) throws SQLException,
                            DataAccessException
                    {
                        lobCreator.setBlobAsBytes( pstmt, 1, content );
                        pstmt.setString( 2, id );
                    }
                } );
    }
    
    /**
     * 根据对象，带blob内容
     * @param sql SQL语句
     * @param params 参数
     */
    public void updateWithBlob(String sql,final Object[] params)
    {
        jdbcTemplate.execute( sql,
                new AbstractLobCreatingPreparedStatementCallback( lobHandler )
                {

                    @Override
                    protected void setValues( PreparedStatement pstmt,
                            LobCreator lobCreator ) throws SQLException,
                            DataAccessException
                    {
                        for(int i = 0;i<params.length;i++)
                        {
                            if(params[i] instanceof byte[])
                            {
                            	pstmt.setBytes(i+1, (byte[])params[i]);
                                //lobCreator.setBlobAsBytes( pstmt, i+1, (byte[])params[i] );
                            }else if(params[i] instanceof Integer)
                            {
                                pstmt.setInt( i+1, (Integer)params[i] );
                            }else{
                                pstmt.setString( i+1, (String)params[i]);
                            }
                        }
                        
                        
                    }
                } );
    }
    
    
    /**
     * 查询blob和对应的名称内容
     * @param sql SQL语句
     * @param params 条件
     * @param nameField 名称字段
     * @param field 内容字段
     * @return 内容
     */
    @SuppressWarnings("unchecked")
	public FileEntity findBlobAndName(String sql,Object[] params,final String nameField,final String field)
    {
    	final FileEntity file = new FileEntity();
    	jdbcTemplate.query( sql, params,
                new AbstractLobStreamingResultSetExtractor()
                {

                    @Override
                    protected void streamData( ResultSet rs )
                            throws SQLException, IOException,
                            DataAccessException
                    {
                    	file.setFileName(rs.getString(nameField));
                    	file.setFileBytes( lobHandler.getBlobAsBytes( rs, field ) );
                    }

                } );
    	return file;
    }

}