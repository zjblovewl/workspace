package net.ycii.api;


import net.sf.json.JSONObject;
import net.ycii.context.WeiXinApplicationContext;
import net.ycii.entity.JsTicket;
import net.ycii.util.HttpClientUtils;

/**
 * 通过access_token获取调用js的临时票据
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  jack
 * @version  [版本号, 2017-7-13]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JsApi
{
    public static JsTicket getJSTicketByAccessToken(String token_key)
    {
        String result = HttpClientUtils.get( WeiXinApplicationContext.JS_TICKET_URL+"?access_token="+token_key + "&type=jsapi");
        JSONObject json = JSONObject.fromObject( result );
        if(!json.get( "errmsg" ).equals( "ok" ))
        {
            return null;
        }
        
        return (JsTicket)JSONObject.toBean( json,JsTicket.class );
    }
}
