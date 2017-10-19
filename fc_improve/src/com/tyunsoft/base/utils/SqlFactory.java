
package com.tyunsoft.base.utils;

import java.util.Map;

/**
 * 将sql语句进行缓存读取操作
 * 
 * @author flymz
 */

public class SqlFactory
{

    private static SqlFactory factory = null;

    /**
     * sql语句解析工具类
     */
    private static SqlXml sqlXml = null;

    private static final String DEFAULT_SQL_FILE = "system_sql.xml";

    public static synchronized SqlFactory getInstance()
    {

        if ( null == factory )
        {
            factory = new SqlFactory();
        }
        sqlXml = new SqlXml( DEFAULT_SQL_FILE );

        return factory;

    }

    public static synchronized SqlFactory getInstance( String sqlFile,
            Class<?> clazz )
    {
        if ( sqlFile.indexOf( "mysql" ) != -1
                || sqlFile.indexOf( "oracle" ) != -1
                || sqlFile.indexOf( "sqlserver" ) != -1
                || sqlFile.indexOf( "db2" ) != -1 )
        {
            throw new RuntimeException( "SQL文件中不需要带入数据库类型，类型由系统自动识别!" );
        }

        if ( null == factory )
        {
            factory = new SqlFactory();
        }
        sqlXml = new SqlXml( sqlFile, clazz );

        return factory;

    }

    /**
     * 获取sql语句
     * 
     * @param sqlId
     * @return
     */
    public String getSql( String sqlId )
    {
        return sqlXml.getSql( sqlId );
    }

    /**
     * 获取分页SQL
     * 
     * @param sqlId
     *            SQL编码
     * @param pageNumber
     *            请求页数
     * @param pageSize
     *            每页记录树
     * @return 分页SQL
     */
    public String getPageSql( String sqlId, int pageNumber, int pageSize )
    {
        String sql = getSql( sqlId );
        String sqlType = Read.getMsg( "system.database.type" );
        if ( Constants.DATABASE_MYSQL.equals( sqlType ) )
        {
            sql += " limit " + (pageNumber - 1) * pageSize + "," + pageSize;
        } else if ( Constants.DATABASE_SQLSERVER.equals( sqlType ) )
        {

        }else if(Constants.DATABASE_ORACLE.equals(sqlType))
        {
        	sql = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (" + sql +  ") A WHERE ROWNUM <= "+ pageNumber * pageSize +") WHERE RN >= " + ((pageNumber - 1) * pageSize+1);
        }
        return sql;
    }

    /**
     * 进行SQL语句数据替换
     * 
     * @param sqlId
     * @param map
     * @return
     */
    public String getSql( String sqlId, Map<String, String> map )
    {
        return sqlXml.getSql( sqlId, map );
    }

}
