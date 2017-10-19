
package com.tyunsoft.base.datasource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于配置服务构建的C3P0数据源连接池
 * 
 * @author Flyer.zuo
 * @version [v1.0, 2014年8月17日]
 */
public class TyunPooledDataSource extends C3P0PooledDataSource
{

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 5577607299581931464L;

    public TyunPooledDataSource()
    {
        super();
    }

    /**
     * 根据配置服务编码获取对应的数据源信息，并且设置数据源信息 <br/>
     * 设置信息如下： <li>driverClass=oracle.jdbc.driver.OracleDriver</li> <li>
     * jdbcUrl=jdbc:oracle:thin:@localhost:1521:Test</li> <li>user=Kay</li> <li>
     * password=root</li> <!--连接池中保留的最小连接数。--> <li>minPoolSize=10</li>
     * <!--连接池中保留的最大连接数。Default: 15 --> <li>maxPoolSize=100</li>
     * <!--最大空闲时间,1800秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 0 --> <li>maxIdleTime=1800</li>
     * <!--当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3 --> <li>acquireIncrement=3</li>
     * <li>maxStatements=1000</li> <li>initialPoolSize=10</li>
     * <!--每60秒检查所有连接池中的空闲连接。Default: 0 --> <li>idleConnectionTestPeriod=60</li>
     * <!--定义在从数据库获取新连接失败后重复尝试的次数。Default: 30 --> <li>acquireRetryAttempts=30</li>
     * <li>breakAfterAcquireFailure=true</li> <li>testConnectionOnCheckout=false
     * </li>
     * 
     * @param dataSourceConfigCode
     *            配置服务编码
     */
    public void setDataSourceString(String dataSourceString)
    {
        String[] dsProperties = dataSourceString.split( "[|]" );
        Map<String, String> properties = new HashMap<String, String>();
        for ( String property : dsProperties )
        {
            String[] keyAndValue = property.split( "=", 2 );
            if ( keyAndValue.length == 2 )
            {
                properties.put( keyAndValue[0].trim(), keyAndValue[1].trim() );
            }
        }
        try
        {
            super.setDriverClass( properties.get( "driverClass" ) );
            super.setJdbcUrl( properties.get( "jdbcUrl" ) );
            super.setUser( properties.get( "user" ) );
            super.setPassword( properties.get( "password" ) );
            super.setMinPoolSize( Integer.parseInt( properties
                    .get( "minPoolSize" ) ) );
            super.setMaxPoolSize( Integer.parseInt( properties
                    .get( "maxPoolSize" ) ) );
            super.setMaxIdleTime( Integer.parseInt( properties
                    .get( "maxIdleTime" ) ) );
            super.setAcquireIncrement( Integer.parseInt( properties
                    .get( "acquireIncrement" ) ) );
            super.setMaxStatements( Integer.parseInt( properties
                    .get( "maxStatements" ) ) );
            super.setInitialPoolSize( Integer.parseInt( properties
                    .get( "initialPoolSize" ) ) );
            super.setIdleConnectionTestPeriod( Integer.parseInt( properties
                    .get( "idleConnectionTestPeriod" ) ) );
            super.setAcquireRetryAttempts( Integer.parseInt( properties
                    .get( "acquireRetryAttempts" ) ) );
            super.setBreakAfterAcquireFailure( Boolean.parseBoolean( properties
                    .get( "breakAfterAcquireFailure" ) ) );
            super.setTestConnectionOnCheckout( Boolean.parseBoolean( properties
                    .get( "testConnectionOnCheckout" ) ) );

        } catch ( PropertyVetoException e )
        {
            e.printStackTrace();
        } catch ( NumberFormatException e )
        {
            e.printStackTrace();
        }

    }

    /**
     * 获取配置服务表中制定数据源的连接
     * 获取方式
     *YcPooledDataSource dataSource = new YcPooledDataSource();
     *dataSource.setsetDataSourceConfigCode(“配置项编码”);
     *dataSource.getConnection();
     * @return Connection数据库连接
     */
    public Connection getConnection()
    {
        try
        {
            return super.getConnection();
        } catch ( SQLException e )
        {
            e.printStackTrace();
        }
        return null;
    }

}
