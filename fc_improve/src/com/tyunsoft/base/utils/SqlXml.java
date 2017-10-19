
package com.tyunsoft.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SqlXml
{

    private NodeList nl;

    public SqlXml( String xmlName )
    {
        try
        {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document document = docBuilder.parse( getInputStream(
                    getSQLXmlName( xmlName ), this.getClass() ) );
            nl = document.getElementsByTagName( "sql" );
        } catch ( SAXException e )
        {
            e.printStackTrace();
        } catch ( IOException e )
        {
            e.printStackTrace();
        } catch ( ParserConfigurationException e )
        {
            e.printStackTrace();
        }

    }

    public SqlXml( String xmlName,Class<?> clazz )
    {
        try
        {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document document = docBuilder.parse( getInputStream(
                    getSQLXmlName( xmlName ), clazz ) );
            nl = document.getElementsByTagName( "sql" );
        } catch ( SAXException e )
        {
            e.printStackTrace();
        } catch ( IOException e )
        {
            e.printStackTrace();
        } catch ( ParserConfigurationException e )
        {
            e.printStackTrace();
        }

    }

    /**
     * 将原始的SQL文件名称根据当前数据库类型进行转换
     * 
     * @param xmlName
     *            xml文件名称
     * @return 转换后的XML文件名
     */
    private String getSQLXmlName( String xmlName )
    {
        String sqlType = Read.getMsg( "system.database.type" );
        String suffix = ".xml";
        xmlName = xmlName.replaceAll( suffix, "_" + sqlType + suffix );
        return xmlName;
    }

    private InputStream getInputStream( String xmlName, Class<?> clazz )
            throws IOException
    {
        InputStream is = null;
        File f = new File( getPath(clazz) + xmlName );
        if ( !f.exists() )
        {
        	is = this.getClass().getResourceAsStream("/sql/" + xmlName);
        } else
        {
            is = new FileInputStream( f );
        }
        return is;

    }

    private String getPath(Class<?> clazz)
    {
        
        String path = clazz.getResource( "" ).getPath();
        path = path.replace("%20", " ");
        path = path.substring( 0, path.indexOf(clazz.getPackage().getName().replaceAll( "[.]", "/" )) )
                + "sql/";
        return path;
    }

    /**
     * 获取like参数
     * 
     * @param param
     *            待封装的参数
     * @return 封装后的参数
     */
    public static String getLinkParam( String param )
    {
        return '%' + (StringUtil.isBlank( param ) ? "" : param) + '%';
    }

    /**
     * 将以,号隔开的字符串加上引号
     * 
     * @param params
     * @return
     */
    public static String getInParams( String params )
    {
        params = "'" + params + "'";
        return params.replaceAll( ",", "','" );
    }

    /**
     * 根据SQLID获取SQL语句，该方法解决了in语句
     * 
     * @param sqlId
     * @param map
     * @return
     */
    public String getSql( String sqlId, Map<String, String> map )
    {
        String sql = getSql( sqlId );
        Set<String> set = map.keySet();
        Iterator<String> it = set.iterator();
        while ( it.hasNext() )
        {
            String key = (String)it.next();
            sql = sql.replaceAll( key, String.valueOf( map.get( key ) ) );
        }
        return sql;
    }

    /**
     * 根据SQLid获取SQL语句
     * 
     * @param sqlId
     * @return
     */
    public String getSql( String sqlId )
    {
        String sql = null;
        for ( int i = 0; i < nl.getLength(); i++ )
        {
            Node node = nl.item( i );
            String nodeId = node.getAttributes().getNamedItem( "id" )
                    .getNodeValue();
            if ( nodeId.equals( sqlId ) )
            {
                sql = node.getTextContent().trim();
                break;
            }
        }
        return sql;
    }

    public static void main( String[] args )
    {
        System.out.println();
        
        SqlXml sx = new SqlXml( "system_sql.xml" );
        System.out.println( sx.getSql( "login_user_sql" ) );
    }
}
