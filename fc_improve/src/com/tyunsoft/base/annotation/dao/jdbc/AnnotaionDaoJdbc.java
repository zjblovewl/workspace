package com.tyunsoft.base.annotation.dao.jdbc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.tyunsoft.base.annotation.Column;
import com.tyunsoft.base.annotation.Table;
import com.tyunsoft.base.annotation.dao.IAnnotaionDao;
import com.tyunsoft.base.entity.IEntity;
import com.tyunsoft.base.utils.Pager;

/**
 * 进行数据增删改的接口数据层实现
 * 
 * @author Flyer.zuo
 * @version [v1.0, 2014年7月28日]
 */
@Repository
public class AnnotaionDaoJdbc implements IAnnotaionDao
{

    /**
     * 日志对象
     */
    private static final Logger LOG = Logger.getLogger( AnnotaionDaoJdbc.class );

    /**
     * JDBCTEMPLATE 对象主要提供jdbcTempalte的get方法供不满足本系统功能的界面使用
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 新增数据
     * 
     * @param entity
     *            需要新增的数据对象
     * @return 新增影响的记录数
     */
    public int insert( IEntity entity )
    {
        boolean hasTableAnnotation = entity.getClass().isAnnotationPresent(
                Table.class );
        if ( !hasTableAnnotation )
        {
            throw new RuntimeException( "更新不成功，类" + entity.getClass().getName()
                    + "未使用Table注解!" );
        }
        Table tableAnnotation = entity.getClass().getAnnotation( Table.class );
        String tableName = tableAnnotation.name();

        Field[] fields = entity.getClass().getDeclaredFields();
        List<Object> params = new ArrayList<Object>();
        StringBuffer insertFields = new StringBuffer();
        StringBuffer questionMarks = new StringBuffer();
        for ( Field field : fields )
        {
            boolean hasFieldAnnotation = field
                    .isAnnotationPresent( Column.class );
            if ( hasFieldAnnotation )
            {
                Column column = field.getAnnotation( Column.class );
                try
                {
                    String fieldName = column.name();
                    Object fieldValue = PropertyUtils.getProperty( entity,
                            field.getName() );
                    if ( null != fieldValue )
                    {// 不为空值的时候保存内容
                        insertFields.append( fieldName ).append( "," );
                        questionMarks.append( "?" ).append( "," );
                        params.add( fieldValue );
                    }

                }
                catch ( IllegalAccessException e )
                {
                    LOG.error( "保存数据过程中发生错误", e );
                    e.printStackTrace();
                }
                catch ( InvocationTargetException e )
                {
                    LOG.error( "保存数据过程中发生错误", e );
                    e.printStackTrace();
                }
                catch ( NoSuchMethodException e )
                {
                    LOG.error( "保存数据过程中发生错误", e );
                    e.printStackTrace();
                }
            }
        }
        if ( null == insertFields || "".equals( insertFields.toString() ) )
        {
            throw new RuntimeException( "未找到任何有效注解字段或者注解的字段值都为null !" );
        }

        String sql = getInsertSql( tableName, insertFields.toString(),
                questionMarks.toString() );
        if ( LOG.isDebugEnabled() )
        {
            LOG.debug( "新增SQL语句：" + sql );
        }
        return jdbcTemplate.update( sql, params.toArray() );
    }

    /**
     * 修改对象，从entity中解析出key字段，然后根据key字段修改对应的数据
     * 
     * @param entity
     *            对象实体类
     * @return 修改的记录数
     */
    public int updateByKey( IEntity entity )
    {
        boolean hasTableAnnotation = entity.getClass().isAnnotationPresent(
                Table.class );
        if ( !hasTableAnnotation )
        {
            throw new RuntimeException( "更新不成功，类" + entity.getClass().getName()
                    + "未使用Table注解!" );
        }
        Table tableAnnotation = entity.getClass().getAnnotation( Table.class );
        String tableName = tableAnnotation.name();

        Field[] fields = entity.getClass().getDeclaredFields();
        List<Object> updateParams = new ArrayList<Object>();
        List<Object> whereParams = new ArrayList<Object>();
        StringBuffer updateFields = new StringBuffer();
        StringBuffer whereFields = new StringBuffer();
        for ( Field field : fields )
        {
            boolean hasFieldAnnotation = field
                    .isAnnotationPresent( Column.class );
            if ( hasFieldAnnotation )
            {
                Column column = field.getAnnotation( Column.class );
                try
                {
                    String fieldName = column.name();
                    boolean isKey = column.isKey();
                    Object fieldValue = PropertyUtils.getProperty( entity,
                            field.getName() );
                    if ( null != fieldValue && !isKey )
                    {// 不为空值的时候保存内容
                        updateFields.append( fieldName ).append( " = ?," );
                        updateParams.add( fieldValue );
                    }
                    if ( isKey )
                    {
                        whereFields.append( " and " ).append( fieldName )
                                .append( " = ? " );
                        whereParams.add( fieldValue );
                    }

                }
                catch ( IllegalAccessException e )
                {
                    LOG.error( "更新数据过程中发生错误", e );
                    e.printStackTrace();
                }
                catch ( InvocationTargetException e )
                {
                    LOG.error( "更新数据过程中发生错误", e );
                    e.printStackTrace();
                }
                catch ( NoSuchMethodException e )
                {
                    LOG.error( "更新数据过程中发生错误", e );
                    e.printStackTrace();
                }
            }
        }
        if ( null == updateFields || "".equals( updateFields.toString() ) )
        {
            throw new RuntimeException( "未找到任何需要更新的注解字段或者注解的字段值都为null !" );
        }
        String sql = getUpdateSql( tableName, updateFields.toString(),
                whereFields.toString() );
        if ( LOG.isDebugEnabled() )
        {
            LOG.debug( "更新SQL语句：" + sql );
        }
        updateParams.addAll( whereParams );
        return jdbcTemplate.update( sql, updateParams.toArray() );
    }

    /**
     * 删除对象，从entity中解析出key字段，然后根据key字段删除对应的数据
     * 
     * @param entity
     *            对象实体类
     * @return 删除的记录数
     */
    public int deleteByKey( IEntity entity )
    {
        boolean hasTableAnnotation = entity.getClass().isAnnotationPresent(
                Table.class );
        if ( !hasTableAnnotation )
        {
            throw new RuntimeException( "删除不成功，类" + entity.getClass().getName()
                    + "未使用Table注解!" );
        }
        Table tableAnnotation = entity.getClass().getAnnotation( Table.class );
        String tableName = tableAnnotation.name();

        Field[] fields = entity.getClass().getDeclaredFields();
        List<Object> whereParams = new ArrayList<Object>();
        StringBuffer whereFields = new StringBuffer();
        for ( Field field : fields )
        {
            boolean hasFieldAnnotation = field
                    .isAnnotationPresent( Column.class );
            if ( hasFieldAnnotation )
            {
                Column column = field.getAnnotation( Column.class );
                try
                {
                    String fieldName = column.name();
                    boolean isKey = column.isKey();
                    Object fieldValue = PropertyUtils.getProperty( entity,
                            field.getName() );
                    if ( isKey )
                    {
                        whereFields.append( " and " ).append( fieldName )
                                .append( " = ? " );
                        whereParams.add( fieldValue );
                    }

                }
                catch ( IllegalAccessException e )
                {
                    LOG.error( "删除数据过程中发生错误", e );
                    e.printStackTrace();
                }
                catch ( InvocationTargetException e )
                {
                    LOG.error( "删除数据过程中发生错误", e );
                    e.printStackTrace();
                }
                catch ( NoSuchMethodException e )
                {
                    LOG.error( "删除数据过程中发生错误", e );
                    e.printStackTrace();
                }
            }
        }
        String sql = getDeleteSql( tableName, whereFields.toString() );
        if ( LOG.isDebugEnabled() )
        {
            LOG.debug( "删除SQL语句：" + sql );
        }
        return jdbcTemplate.update( sql, whereParams.toArray() );
    }
    
    /**
     * 删除所有数据
     * @param entity 对象实体类
     * @return 删除的记录数
     */
    public int deleteAll(IEntity entity)
    {

        boolean hasTableAnnotation = entity.getClass().isAnnotationPresent(
                Table.class );
        if ( !hasTableAnnotation )
        {
            throw new RuntimeException( "删除不成功，类" + entity.getClass().getName()
                    + "未使用Table注解!" );
        }
        Table tableAnnotation = entity.getClass().getAnnotation( Table.class );
        String tableName = tableAnnotation.name();

        String sql = getDeleteSql( tableName, "" );
        if ( LOG.isDebugEnabled() )
        {
            LOG.debug( "删除SQL语句：" + sql );
        }
        return jdbcTemplate.update( sql );
    
    }
    
    /**
     * 直接查询对象
     * @param sql sql语句
     * @param params 参数
     * @return 查询的list对象
     */
    public List<Map<String,Object>> queryForList(String sql, Object[] params)
    {
    	return jdbcTemplate.queryForList(sql, params);
    }
    
    /**
     * 执行更新操作
     * @param sql 更新的SQL语句
     * @param params 更新参数
     * @return 更新的记录数
     */
    public int executeUpdate(String sql,Object[] params)
    {
        return jdbcTemplate.update(sql, params);
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
     * 查询单一实例结果集，并且将结果封装到entity对象中
     * 
     * @param sql
     *            SQL语句
     * @param params
     *            参数
     * @param clazz
     *            实体对象，将数据封装到该对象中
     * @return 查询的对象
     */
    public Object query( String sql, Object[] params, final Class<?> clazz )
    {
        try
        {
            return queryForObject( sql, params, clazz );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询单一实例结果集，并且将结果封装到entity对象中
     * 
     * @param sql
     *            SQL语句
     * @param params
     *            参数
     * @param clazz
     *            实体对象，将数据封装到该对象中
     * @return 查询的对象结果
     * @throws Exception
     *             抛出的异常
     */
    private Object queryForObject( String sql, Object[] params,
            final Class<?> clazz ) throws Exception
    {
        Field[] fields = clazz.getDeclaredFields();
        final Map<String, String> columnPropertyMap = new HashMap<String, String>();
        for ( Field field : fields )
        {
            boolean hasFieldAnnotation = field
                    .isAnnotationPresent( Column.class );
            if ( hasFieldAnnotation )
            {
                Column column = field.getAnnotation( Column.class );
                columnPropertyMap.put( column.name(), field.getName() );
            }
        }

        final Object entityInstance = clazz.newInstance();
        jdbcTemplate.query( sql, params, new RowCallbackHandler()
        {

            @Override
            public void processRow( ResultSet rs ) throws SQLException
            {
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();

                for ( int i = 0; i < columnCount; i++ )
                {
                    String columnName = rsmd.getColumnName( i + 1 );
                    try
                    {
                        PropertyUtils.setProperty( entityInstance,
                                columnPropertyMap.get( columnName ),
                                rs.getObject( columnName ) );
                    }
                    catch ( IllegalAccessException e )
                    {
                        e.printStackTrace();
                    }
                    catch ( InvocationTargetException e )
                    {
                        e.printStackTrace();
                    }
                    catch ( NoSuchMethodException e )
                    {
                        e.printStackTrace();
                    }
                }
            }
        } );
        return entityInstance;
    }

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
    public Pager queryPage( String countSql, String pageSql, Object[] params,
            final Class<?> clazz )
    {

        Field[] fields = clazz.getDeclaredFields();
        final Map<String, String> columnPropertyMap = new HashMap<String, String>();
        for ( Field field : fields )
        {
            boolean hasFieldAnnotation = field
                    .isAnnotationPresent( Column.class );
            if ( hasFieldAnnotation )
            {
                Column column = field.getAnnotation( Column.class );
                columnPropertyMap.put( column.name(), field.getName() );
            }
        }

        Pager pager = new Pager();
        int total = jdbcTemplate.queryForInt( countSql, params );
        pager.setTotal( total );
        List<Object> dataList = jdbcTemplate.query( pageSql, params,
                new RowMapper<Object>()
                {

                    public Object mapRow( ResultSet rs, int rowNum )
                            throws SQLException
                    {
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int columnCount = rsmd.getColumnCount();
                        Object entityInstance = null;
                        try
                        {
                            entityInstance = clazz.newInstance();
                        }
                        catch ( InstantiationException e1 )
                        {
                            e1.printStackTrace();
                        }
                        catch ( IllegalAccessException e1 )
                        {
                            e1.printStackTrace();
                        }
                        for ( int i = 0; i < columnCount; i++ )
                        {
                            String columnName = rsmd.getColumnName( i + 1 );
                            try
                            {
                                PropertyUtils.setProperty( entityInstance,
                                        columnPropertyMap.get( columnName ),
                                        rs.getObject( columnName ) );
                            }
                            catch ( IllegalAccessException e )
                            {
                                e.printStackTrace();
                            }
                            catch ( InvocationTargetException e )
                            {
                                e.printStackTrace();
                            }
                            catch ( NoSuchMethodException e )
                            {
                                e.printStackTrace();
                            }
                        }

                        return entityInstance;
                    }
                } );
        pager.setRows( dataList );
        return pager;
    }

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
    public List<Object> queryList( String sql, Object[] params,
            final Class<?> clazz )
    {
        Field[] fields = clazz.getDeclaredFields();
        final Map<String, String> columnPropertyMap = new HashMap<String, String>();
        for ( Field field : fields )
        {
            boolean hasFieldAnnotation = field
                    .isAnnotationPresent( Column.class );
            if ( hasFieldAnnotation )
            {
                Column column = field.getAnnotation( Column.class );
                columnPropertyMap.put( column.name(), field.getName() );
            }
        }

        return jdbcTemplate.query( sql, params, new RowMapper<Object>()
        {

            public Object mapRow( ResultSet rs, int rowNum )
                    throws SQLException
            {
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                Object entityInstance = null;
                try
                {
                    entityInstance = clazz.newInstance();
                }
                catch ( InstantiationException e1 )
                {
                    e1.printStackTrace();
                }
                catch ( IllegalAccessException e1 )
                {
                    e1.printStackTrace();
                }
                for ( int i = 0; i < columnCount; i++ )
                {
                    String columnName = rsmd.getColumnName( i + 1 );
                    try
                    {
                        PropertyUtils.setProperty( entityInstance,
                                columnPropertyMap.get( columnName ),
                                rs.getObject( columnName ) );
                    }
                    catch ( IllegalAccessException e )
                    {
                        e.printStackTrace();
                    }
                    catch ( InvocationTargetException e )
                    {
                        e.printStackTrace();
                    }
                    catch ( NoSuchMethodException e )
                    {
                        e.printStackTrace();
                    }
                }

                return entityInstance;
            }
        } );
    }

    public Object queryByKey( final Class<?> clazz, IEntity entity )
    {
        try
        {
            return queryObjectByKey( clazz, entity );
        }
        catch ( InstantiationException e )
        {
            e.printStackTrace();
        }
        catch ( IllegalAccessException e )
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 根据SQL查询整型返回值
     * @param sql SQL语句
     * @param params 参数
     * @return 查询返回的数量
     */
    public int queryCount(String sql, Object[] params)
    {
        return jdbcTemplate.queryForInt( sql, params );
    }

    /**
     * 根据主键值查询指定对象
     * 
     * @param clazz
     *            指定查询的类型
     * @param entity
     *            封装了主键的对象
     * @return 指定结果对象
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private Object queryObjectByKey( final Class<?> clazz, IEntity entity )
            throws InstantiationException, IllegalAccessException
    {
        boolean hasTableAnnotation = entity.getClass().isAnnotationPresent(
                Table.class );
        if ( !hasTableAnnotation )
        {
            throw new RuntimeException( "删除不成功，类" + entity.getClass().getName()
                    + "未使用Table注解!" );
        }
        Table tableAnnotation = entity.getClass().getAnnotation( Table.class );
        String tableName = tableAnnotation.name();

        Field[] fields = entity.getClass().getDeclaredFields();
        StringBuffer sql = new StringBuffer( "select " );
        List<Object> whereParams = new ArrayList<Object>();
        StringBuffer whereFields = new StringBuffer();
        for ( Field field : fields )
        {
            boolean hasFieldAnnotation = field
                    .isAnnotationPresent( Column.class );
            if ( hasFieldAnnotation )
            {
                Column column = field.getAnnotation( Column.class );
                try
                {
                    String fieldName = column.name();
                    boolean isKey = column.isKey();
                    Object fieldValue = PropertyUtils.getProperty( entity,
                            field.getName() );
                    sql.append( fieldName ).append( "," );
                    if ( isKey )
                    {
                        whereFields.append( " and " ).append( fieldName )
                                .append( " = ? " );
                        whereParams.add( fieldValue );
                    }

                }
                catch ( IllegalAccessException e )
                {
                    LOG.error( "查询数据过程中发生错误", e );
                    e.printStackTrace();
                }
                catch ( InvocationTargetException e )
                {
                    LOG.error( "查看数据过程中发生错误", e );
                    e.printStackTrace();
                }
                catch ( NoSuchMethodException e )
                {
                    LOG.error( "查询数据过程中发生错误", e );
                    e.printStackTrace();
                }
            }
        }
        if ( sql.toString().endsWith( "," ) )
        {
            sql = new StringBuffer( sql.substring( 0, sql.length() - 1 ) );
        }
        sql.append( " from " ).append( tableName ).append( " where 1=1 " )
                .append( whereFields );
        final Map<String, String> columnPropertyMap = new HashMap<String, String>();
        for ( Field field : fields )
        {
            boolean hasFieldAnnotation = field
                    .isAnnotationPresent( Column.class );
            if ( hasFieldAnnotation )
            {
                Column column = field.getAnnotation( Column.class );
                columnPropertyMap.put( column.name(), field.getName() );
            }
        }
        final Object entityInstance = clazz.newInstance();
        jdbcTemplate.query( sql.toString(), whereParams.toArray(),
                new RowCallbackHandler()
                {

                    @Override
                    public void processRow( ResultSet rs ) throws SQLException
                    {
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int columnCount = rsmd.getColumnCount();

                        for ( int i = 0; i < columnCount; i++ )
                        {
                            String columnName = rsmd.getColumnName( i + 1 );
                            try
                            {
                                PropertyUtils.setProperty( entityInstance,
                                        columnPropertyMap.get( columnName ),
                                        rs.getObject( columnName ) );
                            }
                            catch ( IllegalAccessException e )
                            {
                                e.printStackTrace();
                            }
                            catch ( InvocationTargetException e )
                            {
                                e.printStackTrace();
                            }
                            catch ( NoSuchMethodException e )
                            {
                                e.printStackTrace();
                            }
                        }

                    }
                } );

        return entityInstance;
    }

    /**
     * 查询所有列表数据
     * 
     * @param clazz
     *            对象的class
     * @return 返回查询的结果集
     */
    public List<Object> queryAllList( final Class<?> clazz )
    {
        boolean hasTableAnnotation = clazz.isAnnotationPresent( Table.class );
        if ( !hasTableAnnotation )
        {
            throw new RuntimeException( "更新不成功，类" + clazz.getName()
                    + "未使用Table注解!" );
        }
        Table tableAnnotation = clazz.getAnnotation( Table.class );
        String tableName = tableAnnotation.name();

        Field[] fields = clazz.getDeclaredFields();
        final Map<String, String> columnPropertyMap = new HashMap<String, String>();
        StringBuffer sql = new StringBuffer( "select " );
        for ( Field field : fields )
        {
            boolean hasFieldAnnotation = field
                    .isAnnotationPresent( Column.class );
            if ( hasFieldAnnotation )
            {
                Column column = field.getAnnotation( Column.class );
                columnPropertyMap.put( column.name(), field.getName() );
                sql.append( "t." ).append( column.name() ).append( "," );
            }
        }
        if ( "select ".equals( sql.toString() ) )
        {
            throw new RuntimeException( "没有任何可查询的列，请在实体类中配置Column注解!" );
        }
        if ( sql.toString().endsWith( "," ) )
        {
            sql = new StringBuffer( sql.toString().substring( 0,
                    sql.length() - 1 ) );
        }
        sql.append( " from " ).append( tableName ).append( " t " );
        if ( LOG.isDebugEnabled() )
        {
            LOG.debug( "queryAllList SQL:" + sql );
        }
        return jdbcTemplate.query( sql.toString(), new Object[] {},
                new RowMapper<Object>()
                {

                    public Object mapRow( ResultSet rs, int rowNum )
                            throws SQLException
                    {
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int columnCount = rsmd.getColumnCount();
                        Object entityInstance = null;
                        try
                        {
                            entityInstance = clazz.newInstance();
                        }
                        catch ( InstantiationException e1 )
                        {
                            e1.printStackTrace();
                        }
                        catch ( IllegalAccessException e1 )
                        {
                            e1.printStackTrace();
                        }
                        for ( int i = 0; i < columnCount; i++ )
                        {
                            String columnName = rsmd.getColumnName( i + 1 );
                            try
                            {
                                PropertyUtils.setProperty( entityInstance,
                                        columnPropertyMap.get( columnName ),
                                        rs.getObject( columnName ) );
                            }
                            catch ( IllegalAccessException e )
                            {
                                e.printStackTrace();
                            }
                            catch ( InvocationTargetException e )
                            {
                                e.printStackTrace();
                            }
                            catch ( NoSuchMethodException e )
                            {
                                e.printStackTrace();
                            }
                        }

                        return entityInstance;
                    }
                } );

    }

    /**
     * 数据新增的SQL语句
     * 
     * @param tableName
     *            表名称
     * @param insertFields
     *            新增字段拼装
     * @param questionMarks
     *            根据字段数量生成对应的问号数量
     * @return 数据新增的SQL语句
     */
    private String getInsertSql( String tableName, String insertFields,
            String questionMarks )
    {
        if ( insertFields.endsWith( "," ) )
        {
            insertFields = insertFields
                    .substring( 0, insertFields.length() - 1 );
            questionMarks = questionMarks.substring( 0,
                    questionMarks.length() - 1 );
        }
        StringBuffer sql = new StringBuffer( "insert into " );
        sql.append( tableName ).append( "(" ).append( insertFields )
                .append( ") values(" ).append( questionMarks ).append( ")" );
        return sql.toString();
    }

    /**
     * 获取数据更新的SQL语句
     * 
     * @param tableName
     *            表名称
     * @param updateFields
     *            更新字段拼装
     * @param whereFields
     *            where条件字段拼装
     * @return 数据更新的SQL语句
     */
    private String getUpdateSql( String tableName, String updateFields,
            String whereFields )
    {
        if ( updateFields.endsWith( "," ) )
        {
            updateFields = updateFields
                    .substring( 0, updateFields.length() - 1 );
        }
        StringBuffer sql = new StringBuffer( "update " );
        sql.append( tableName ).append( " set " ).append( updateFields )
                .append( " where 1=1 " ).append( whereFields );
        return sql.toString();
    }

    /**
     * 获取删除的SQL语句
     * 
     * @param tableName
     *            表名称
     * @param whereFields
     *            where条件字段拼接
     * @return 删除的SQL语句
     */
    private String getDeleteSql( String tableName, String whereFields )
    {
        StringBuffer sql = new StringBuffer( "delete from " );
        sql.append( tableName ).append( " where 1 = 1 " ).append( whereFields );
        return sql.toString();
    }

}
