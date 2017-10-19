package net.ycii.api;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import net.ycii.context.WeiXinApplicationContext;
import net.ycii.entity.Token;
import net.ycii.exception.WeiXinException;
import net.ycii.util.HttpClientUtils;

import org.apache.log4j.Logger;


/**
 * <一句话功能简述>微信公众平台API
 * <功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年5月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class WeiXinApi 
{
    
    private static Logger logger = Logger.getLogger( WeiXinApi.class );

    
    /**
     * <一句话功能简述>获取微信token
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月13日 上午11:26:00
     * @param appid
     * @param secret
     * @return
     * @throws WeiXinException [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getAccessToken(String appid,String secret)
    {
        Token token = token( appid, secret );
        if(token!=null)
        {
            return token.getAccess_token();
        }
        return null;
    }
    
    
    public static Token token(String appid,String secret)
    {
        Map<String,String> paraMap = new HashMap<String,String>();
        paraMap.put( "grant_type",  "client_credential" );
        paraMap.put( "appid",       appid );
        paraMap.put( "secret",      secret );
        
        String result = HttpClientUtils.get( WeiXinApplicationContext.ACCESS_TOKEN_URL,paraMap);
        
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(json.containsKey( WeiXinApplicationContext.RESULT_ACCESS_TOKEN_KEY ))
        {
            Token token = new Token();
            token.setAccess_token( json.getString( WeiXinApplicationContext.RESULT_ACCESS_TOKEN_KEY ) );
            token.setExpires_in( json.getInt( "expires_in" ) );
            return token;
        }
        
        logger.warn( json.get( WeiXinApplicationContext.RESULT_ERRMSG_KEY ) );
        return null;
    }
    
}
