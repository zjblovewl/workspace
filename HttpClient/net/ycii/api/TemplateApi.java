package net.ycii.api;

import net.sf.json.JSONObject;
import net.ycii.context.WeiXinApplicationContext;
import net.ycii.entity.TemplateMessage;
import net.ycii.exception.WeiXinException;
import net.ycii.util.HttpClientUtils;


/**
 * <消息模板>
 * <p><功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年6月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TemplateApi
{
    /**
     * <设置消息模板所属行业>
     * <设置消息模板所属行业>
     * @author  kaylves
     * @time  2015年6月16日 下午12:10:30
     * @param industry_id1  公众号模板消息所属行业编号
     * @param industry_id2  公众号模板消息所属行业编号
     * @param accessToken
     * @throws WeiXinException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void setIndustry( String industry_id1,String industry_id2, String accessToken ) throws WeiXinException
    {
        
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put( "industry_id1",              industry_id1 );
        
        jsonObject.put( "industry_id2",              industry_id2 );
        
        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.TEMPLATE_API_SET_INDUSTRY+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        if( !json.getString( "errmsg" ).equals( "ok" ) )
        {
            
            throw new WeiXinException( result );
        }
        
    }
    
    /**
     * <获得模板ID>
     * <获得模板ID>
     * @author  kaylves
     * @time  2015年6月16日 下午12:11:51
     * @param templateIdShort
     * @param accessToken
     * @throws WeiXinException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String addTemplate( String templateIdShort, String accessToken ) throws WeiXinException
    {
        
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put( "template_id_short",              templateIdShort );
        
        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.TEMPLATE_ADD_TEMPLATE+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        System.out.println(result);
        if( !json.getString( "errmsg" ).equals( "ok" ) )
        {
            
            throw new WeiXinException( result );
        }
        
        return json.getString( "template_id" );
    }
    
    /**
     * <一句话功能简述>
     * <功能详细描述>
     *       {
           "touser":"OPENID",
           "template_id":"ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY",
           "url":"http://weixin.qq.com/download",
           "topcolor":"#FF0000",
           "data":{
                   "first": {
                       "value":"恭喜你购买成功！",
                       "color":"#173177"
                   },
                   "keynote1":{
                       "value":"巧克力",
                       "color":"#173177"
                   },
                   "keynote2": {
                       "value":"39.8元",
                       "color":"#173177"
                   },
                   "keynote3": {
                       "value":"2014年9月16日",
                       "color":"#173177"
                   },
                   "remark":{
                       "value":"欢迎再次购买！",
                       "color":"#173177"
                   }
           }
       }
     * @author  kaylves
     * @time  2015年6月16日 下午2:37:21 [参数说明]
     * 
     * @return void [返回类型说明]
     * @throws WeiXinException 
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void sendTemplateMsg(TemplateMessage message,String accessToken) throws WeiXinException
    {
        JSONObject jsonObject = JSONObject.fromObject( message );
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.TEMPLATE_SEND+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        System.out.println(result);
        if( !json.getString( "errmsg" ).equals( "ok" ) )
        {
            
            throw new WeiXinException( result );
        }
        
    }
}
