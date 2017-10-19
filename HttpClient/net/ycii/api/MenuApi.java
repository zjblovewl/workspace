package net.ycii.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.ycii.context.WeiXinApplicationContext;
import net.ycii.entity.MenuNode;
import net.ycii.exception.WeiXinException;
import net.ycii.util.HttpClientUtils;

import org.apache.log4j.Logger;


/**
 * <一句话功能简述>菜单Api
 * <p><功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年5月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MenuApi
{
    
    private static Logger logger = Logger.getLogger(MenuApi.class);
    
    /**
     * <一句话功能简述>获取微信用户下菜单列表
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月20日 上午9:32:42
     * @param accessToken   访问标识符
     * @return [参数说明]
     * 
     * @return List<MenuNode> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings( "unchecked" )
    public static List<MenuNode> getAllMenu(String accessToken)
    {
        Map<String,String> paraMap = new HashMap<String,String>();
        paraMap.put( "access_token", accessToken );
        String result = HttpClientUtils.get( WeiXinApplicationContext.MENU_GET_ALL_URL ,paraMap );
        
        
        JSONObject json = JSONObject.fromObject( result );
        if(json.containsKey( "errmsg" ))
        {
            logger.warn( "调用微信查询所有菜单失败："+result );
            return null;
        }
        
        JSONArray groups = json.getJSONObject( "menu" ).getJSONArray( "button" );
            

        return (List<MenuNode>)JSONArray.toCollection( groups, MenuNode.class );
    }    
    
    /**
     * <创建菜单>
     * <创建菜单>
     * @author  kaylves
     * @time  2015年5月27日 下午12:06:41
     * @param menus
     * @param accessToken
     * @throws WeiXinException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void createMenu(List<MenuNode> menus,String accessToken ) throws WeiXinException
    {
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        jsonObject.put( "button", menus );
        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.MENU_CREATE_URL+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals(json.getString( "errcode" )))
        {
            throw new WeiXinException( json.getString( "errcode" )+"_"+json.getString( "errmsg" ) );
        }
    }
    
    public static void deleteMenu(String accessToken ) throws WeiXinException
    {
        Map<String,String> paraMap = new HashMap<String,String>();
        paraMap.put( "access_token", accessToken );
        String result = HttpClientUtils.get( WeiXinApplicationContext.MENU_DELETE_URL ,paraMap );
        
        JSONObject json = JSONObject.fromObject( result );
        if(!"0".equals(json.getString( "errcode" )))
        {
            throw new WeiXinException( json.getString( "errcode" )+"_"+json.getString( "errmsg" ) );
        }
    }
    
}
