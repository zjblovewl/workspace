
package net.ycii.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author kaylves
 * @version [版本号, 2015年5月13日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class HttpClientUtils
{

    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger( HttpClientUtils.class );

    /**
     * 连接时间
     */
    private static final int CONNECTION_TIME_OUT = 5000;

    private static final String UTF = "UTF-8";

    private static final int SO_TIME_OUT = 100000;

    private static BasicHttpParams bp;

    private static SchemeRegistry schreg;

    private static PoolingClientConnectionManager pccm;

    static
    {
        bp = new BasicHttpParams();

        // 超时时间设置
        HttpConnectionParams.setConnectionTimeout( bp, CONNECTION_TIME_OUT );
        HttpConnectionParams.setSoTimeout( bp, SO_TIME_OUT );
        // 设置访问协议
        schreg = new SchemeRegistry();
        schreg.register( new Scheme( "http", 80, PlainSocketFactory
                .getSocketFactory() ) );
        schreg.register( new Scheme( "https", 443, SSLSocketFactory
                .getSocketFactory() ) );

        // 多连接的线程安全的管理器
        pccm = new PoolingClientConnectionManager( schreg );
        // 每个主机的最大并行链接数
        pccm.setDefaultMaxPerRoute( 200 );
        pccm.setMaxTotal( 600 );
    }

    /**
     * @author Kaylves
     * @time 2014-6-20 下午04:57:56
     * @param actionUrl
     * @param params
     * @return String
     * @description
     * @version 1.0
     */
    public static String post( String actionUrl, Map<String, String> params )
    {
        HttpClient httpclient = new DefaultHttpClient( pccm, bp );
        HttpPost httpPost = new HttpPost( actionUrl );
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for ( Map.Entry<String, String> entry : params.entrySet() )
        { // 构建表单字段内容
            String key = entry.getKey();
            String value = entry.getValue();
            list.add( new BasicNameValuePair( key, value ) );
        }

        HttpConnectionParams.setConnectionTimeout( bp, CONNECTION_TIME_OUT ); // 超时时间设置
        HttpConnectionParams.setSoTimeout( bp, SO_TIME_OUT );

        HttpResponse httpResponse;

        String responseString = "";

        logger.warn( "传入后台的URL：" + actionUrl );
        logger.warn( "传入后台的参数：" + list );

        try
        {
            httpPost.setEntity( new UrlEncodedFormEntity( list, UTF ) );
            httpResponse = httpclient.execute( httpPost );
            if ( httpResponse.getStatusLine().getStatusCode() == 200 )
            {
                responseString = EntityUtils
                        .toString( httpResponse.getEntity() );
                return responseString;
            } else if ( httpResponse.getStatusLine().getStatusCode() == 404 )
            {
                logger.warn( "actionUrl:{} not found 404!" + actionUrl );
            }
        } catch ( Exception e )
        {
            throw new RuntimeException( e );
        } finally
        {
            httpPost.releaseConnection();
        }
        return null;
    }

    public static String postJSON( String actionUrl, String data )
    {
        HttpClient httpclient = new DefaultHttpClient( pccm, bp );
        HttpPost httpPost = new HttpPost( actionUrl );

        HttpConnectionParams.setConnectionTimeout( bp, CONNECTION_TIME_OUT ); // 超时时间设置
        HttpConnectionParams.setSoTimeout( bp, SO_TIME_OUT );

        StringEntity myEntity = new StringEntity( data,
                ContentType.APPLICATION_JSON );// 构造请求数据
        httpPost.setEntity( myEntity );// 设置请求体

        HttpResponse httpResponse;

        String responseString = "";

        logger.warn( "接口URL:" + actionUrl );
        logger.warn( "接口参数:" + data );

        try
        {
            httpResponse = httpclient.execute( httpPost );
            if ( httpResponse.getStatusLine().getStatusCode() == 200 )
            {
                responseString = EntityUtils.toString(
                        httpResponse.getEntity(), UTF );
                logger.warn( "responseString：" + responseString );
                return responseString;
            } else if ( httpResponse.getStatusLine().getStatusCode() == 404 )
            {
                logger.warn( "actionUrl:{} not found 404!" + actionUrl );
            }
        } catch ( Exception e )
        {
            throw new RuntimeException( e );
        } finally
        {
            httpPost.releaseConnection();
        }
        return null;
    }
    
    /**
     * 多类型请求
     **/
    public static String multipartPost( String actionUrl, Map<String,File> files,Map<String,String> params )
    {
        HttpClient httpclient = new DefaultHttpClient( pccm, bp );
        HttpPost httpPost = new HttpPost( actionUrl );

        HttpConnectionParams.setConnectionTimeout( bp, CONNECTION_TIME_OUT ); // 超时时间设置
        HttpConnectionParams.setSoTimeout( bp, SO_TIME_OUT );

        try
        {
            
            MultipartEntity multipartEntity = new SimpleMultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE,null, Charset.forName(UTF));
            
            for ( Map.Entry<String, String> entry : params.entrySet() )
            { // 构建表单字段内容
                multipartEntity.addPart(entry.getKey(), new StringBody(entry.getValue(), Charset.forName(UTF)));
            }
            
            
            
            //文件请求
            for ( Map.Entry<String, File> entry : files.entrySet() )
            { // 构建表单字段内容
                multipartEntity.addPart(entry.getKey(), new FileBody(entry.getValue()));
            }
            
            httpPost.setEntity( multipartEntity );// 设置请求体
            
            HttpResponse httpResponse;
            
            String responseString = "";
            
            logger.warn( "接口URL:" + actionUrl );
            logger.warn( "接口参数:" + params );
            httpResponse = httpclient.execute( httpPost );
            if ( httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK )
            {
                responseString = EntityUtils.toString(httpResponse.getEntity(), UTF );
                logger.warn( "responseString：" + responseString );
                return responseString;
            } else if ( httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND )
            {
                logger.warn( "actionUrl:{} not found 404!" + actionUrl );
            }
        } catch ( Exception e )
        {
            throw new RuntimeException( e );
        } finally
        {
            httpPost.releaseConnection();
        }
        return null;
    }

    public static String postWithHeader( String data,
            Map<String, String> header, String actionUrl ) throws Exception
    {
        InputStream in = null;
        java.io.BufferedReader breader = null;
        try
        {
            URL url = new URL( actionUrl );
            HttpURLConnection connection = (HttpURLConnection)url
                    .openConnection();
            connection.setRequestProperty( "Connection", "Keep-Alive" );
            connection.setRequestProperty( "Cache-Control", "no-cache" );
            connection.setRequestProperty( "Accept", "*/*" );
            connection.setRequestProperty( "User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)" );
            connection.setRequestProperty( "Accept-Language", "zh-cn" );
            connection.setRequestProperty( "Accept-Encoding", "gzip, deflate" );
            connection.setRequestProperty( "Content-Length",
                    String.valueOf( data.length() ) );
            for ( Map.Entry<String, String> entry : header.entrySet() )
            {
                connection
                        .setRequestProperty( entry.getKey(), entry.getValue() );
            }
            connection.setConnectTimeout( 5000 );
            connection.setDoOutput( true );
            connection.connect();
            connection.getOutputStream().write( data.getBytes() );
            if ( connection.getResponseCode() == 200 )
            {
                in = connection.getInputStream();
                breader = new BufferedReader( new InputStreamReader( in,
                        "UTF-8" ) );
                String str = breader.readLine();
                StringBuffer sb = new StringBuffer();
                while ( str != null )
                {
                    sb.append( str );
                    str = breader.readLine();
                }
                return sb.toString();
            }

        } catch ( Exception e )
        {
            e.printStackTrace();
            throw e;
        } finally
        {
            if ( null != breader )
            {
                breader.close();
            }
            if ( null != in )
            {
                in.close();
            }
        }
        return "";
    }

    public static String getWithHeader( String data,
            Map<String, String> header, String actionUrl ) throws Exception
    {
        InputStream in = null;
        java.io.BufferedReader breader = null;
        try
        {
            URL url = new URL( actionUrl );
            HttpURLConnection connection = (HttpURLConnection)url
                    .openConnection();
            connection.setRequestProperty( "Connection", "Keep-Alive" );
            connection.setRequestProperty( "Cache-Control", "no-cache" );
            connection.setRequestProperty( "Accept", "*/*" );
            connection.setRequestProperty( "User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)" );
            connection.setRequestProperty( "Accept-Language", "zh-cn" );
            connection.setRequestProperty( "Accept-Encoding", "gzip, deflate" );
            connection.setRequestProperty( "Content-Length",
                    String.valueOf( data.length() ) );

            for ( Map.Entry<String, String> entry : header.entrySet() )
            {
                connection
                        .setRequestProperty( entry.getKey(), entry.getValue() );
            }
            connection.setRequestMethod( "GET" );
            connection.setConnectTimeout( 5000 );
            connection.setDoOutput( true );
            connection.connect();
            connection.getOutputStream().write( data.getBytes() );
            if ( connection.getResponseCode() == 200 )
            {
                in = connection.getInputStream();
                breader = new BufferedReader( new InputStreamReader( in,
                        "UTF-8" ) );
                String str = breader.readLine();
                StringBuffer sb = new StringBuffer();
                while ( str != null )
                {
                    sb.append( str );
                    str = breader.readLine();
                }
                return sb.toString();
            }

        } catch ( Exception e )
        {
            e.printStackTrace();
            throw e;
        } finally
        {
            if ( null != breader )
            {
                breader.close();
            }
            if ( null != in )
            {
                in.close();
            }
        }
        return "";
    }

    public static String get( String url, String model,
            Map<String, String> params )
    {
        BasicHttpParams bp = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout( bp, CONNECTION_TIME_OUT ); // 超时时间设置
        HttpConnectionParams.setSoTimeout( bp, SO_TIME_OUT );
        HttpClient httpclient = new DefaultHttpClient( bp );
        HttpGet httpGet = new HttpGet( url );
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for ( Map.Entry<String, String> entry : params.entrySet() )
        { // 构建表单字段内容
            list.add( new BasicNameValuePair( entry.getKey(), entry.getValue() ) );
        }
        logger.warn( "传入后台的参数" + list );
        httpGet.setHeader( "User-Agent", model );
        try
        {
            httpclient.getParams().setParameter( "utf-8", "UTF-8" );
            HttpResponse httpResponse = httpclient.execute( httpGet );
            if ( httpResponse.getStatusLine().getStatusCode() == 200 )
            {
                return EntityUtils.toString( httpResponse.getEntity(), UTF );
            } else if ( httpResponse.getStatusLine().getStatusCode() == 404 )
            {
                logger.warn( "actionUrl:{} not found 404!" + url );
            }
        } catch ( Exception e )
        {
            throw new RuntimeException( e );
        } finally
        {
            httpclient.getConnectionManager().shutdown();
        }
        return null;
    }

    /**
     * <一句话功能简述> <功能详细描述>
     * 
     * @author kaylves
     * @time 2015年5月13日 上午9:46:08
     * @param url
     *            url传过来时不需要指定?符号
     * @param params
     * @return [参数说明]
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String get( String url, Map<String, String> params )
    {
        BasicHttpParams bp = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout( bp, CONNECTION_TIME_OUT ); // 超时时间设置
        HttpConnectionParams.setSoTimeout( bp, SO_TIME_OUT );
        HttpClient httpclient = new DefaultHttpClient( bp );

        StringBuffer actionUrl = new StringBuffer( url );
        actionUrl.append( "?" );

        for ( Map.Entry<String, String> entry : params.entrySet() )
        { // 构建表单字段内容
            actionUrl.append( entry.getKey() ).append( "=" )
                    .append( entry.getValue() ).append( "&" );
        }

        if ( params.size() > 1 )
        {
            actionUrl = actionUrl.replace( actionUrl.length() - 1,
                    actionUrl.length(), "" );
        }

        logger.warn( "actionUrl:" + actionUrl.toString() );
        HttpGet httpGet = new HttpGet( actionUrl.toString() );

        try
        {
            httpclient.getParams().setParameter( "utf-8", "UTF-8" );
            HttpResponse httpResponse = httpclient.execute( httpGet );
            if ( httpResponse.getStatusLine().getStatusCode() == 200 )
            {
                String result = EntityUtils.toString( httpResponse.getEntity(),
                        UTF );
                logger.warn( "result:" + result );
                return result;
            } else if ( httpResponse.getStatusLine().getStatusCode() == 404 )
            {
                logger.warn( "actionUrl:{} not found 404!" + url );
            }
        } catch ( Exception e )
        {
            throw new RuntimeException( e );
        } finally
        {
            httpclient.getConnectionManager().shutdown();
        }
        return null;
    }

    public static InputStream  getInputStream( String url, Map<String, String> params )
    {
        BasicHttpParams bp = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout( bp, CONNECTION_TIME_OUT ); // 超时时间设置
        HttpConnectionParams.setSoTimeout( bp, SO_TIME_OUT );
        HttpClient httpclient = new DefaultHttpClient( bp );

        StringBuffer actionUrl = new StringBuffer( url );
        actionUrl.append( "?" );

        for ( Map.Entry<String, String> entry : params.entrySet() )
        { // 构建表单字段内容
            actionUrl.append( entry.getKey() ).append( "=" )
                    .append( entry.getValue() ).append( "&" );
        }

        if ( params.size() > 1 )
        {
            actionUrl = actionUrl.replace( actionUrl.length() - 1,
                    actionUrl.length(), "" );
        }

        logger.warn( "actionUrl:" + actionUrl.toString() );
        HttpGet httpGet = new HttpGet( actionUrl.toString() );

        try
        {
            httpclient.getParams().setParameter( "utf-8", "UTF-8" );
            HttpResponse httpResponse = httpclient.execute( httpGet );
            InputStream in = null;
            FileOutputStream out = null;
            if ( httpResponse.getStatusLine().getStatusCode() == 200 )
            {
                Header contentHead = httpResponse.getAllHeaders()[3];
                //开始解析文件头信息，这里使用的是HeaderElement对象作为文件头的基础信息
                HeaderElement[] elements = contentHead.getElements();
                String filerName = null;
                for (HeaderElement el : elements) {
                    //遍历，获取filename。filename信息对应的就是下载文件的文件名称。
                    NameValuePair pair = el.getParameterByName("filename");
                    System.out.println(pair.getName() + ":" + pair.getValue());
                    filerName = pair.getValue();
                }
                //由于这个地址是下载地址，返回的是字节流信息。所有这里直接将返回的body转换成字节流
                in = httpResponse.getEntity().getContent();

                out = new FileOutputStream(new File("D:\\speex\\" + filerName));

                byte[] b = new byte[1024];
                int len = 0;
                byte[] content = new byte[0];
                int length = 0;
                //写文件，这里将所有的内容都缓存到内存中，最后一次性写入
                while ((len = in.read(b)) > 0) {
                    // out.write(b,0,len);
                    length = content.length;
                    content = Arrays.copyOf(content, length + len);// 扩容
                    System.arraycopy(b, 0, content, length, len);// 将第二个数组与第一个数组合并
                }
                out.write(content, 0, content.length);
                in.close();
                out.close();
            } else if ( httpResponse.getStatusLine().getStatusCode() == 404 )
            {
                logger.warn( "actionUrl:{} not found 404!" + url );
            }
        } catch ( Exception e )
        {
            throw new RuntimeException( e );
        } finally
        {
            httpclient.getConnectionManager().shutdown();
        }
        return null;
    }
    
    public static String get( String url )
    {
        HttpConnectionParams.setConnectionTimeout( bp, CONNECTION_TIME_OUT ); // 超时时间设置
        HttpConnectionParams.setSoTimeout( bp, SO_TIME_OUT );
        HttpClient httpclient = new DefaultHttpClient( bp );
        HttpGet httpGet = new HttpGet( url );
        try
        {
            httpclient.getParams().setParameter( "utf-8", "UTF-8" );
            HttpResponse httpResponse = httpclient.execute( httpGet );
            if ( httpResponse.getStatusLine().getStatusCode() == 200 )
            {
                return EntityUtils.toString( httpResponse.getEntity() );
            } else if ( httpResponse.getStatusLine().getStatusCode() == 404 )
            {
                logger.warn( "actionUrl:{} not found 404!" + url );
            }
        } catch ( Exception e )
        {
            throw new RuntimeException( e );
        } finally
        {
            httpclient.getConnectionManager().shutdown();
        }
        return null;
    }
    
    public static class SimpleMultipartEntity extends MultipartEntity {

        @Override
        protected String generateContentType(String boundary, Charset charset) {
            StringBuilder buffer = new StringBuilder();
            buffer.append("multipart/form-data");
            if (charset != null) {
                buffer.append("; charset=");
                buffer.append(charset.name());
            }
            buffer.append(";boundary=");
            buffer.append(boundary);
            return buffer.toString();
        }
        
        public SimpleMultipartEntity(HttpMultipartMode mode,String boundary,Charset charset) {
            super(mode,boundary,charset);
        }

    }
    

    public static void main( String[] args )
    {
        
    }
}
