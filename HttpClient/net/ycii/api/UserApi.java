package net.ycii.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.ycii.context.WeiXinApplicationContext;
import net.ycii.entity.Group;
import net.ycii.entity.WXUserAuthorization;
import net.ycii.entity.WeiXinAuthorUser;
import net.ycii.entity.WeiXinUser;
import net.ycii.entity.WeiXinUsersResult;
import net.ycii.exception.WeiXinException;
import net.ycii.util.HttpClientUtils;

import org.apache.log4j.Logger;


/**
 * <一句话功能简述>用户Api
 * <p><功能详细描述>开发者可以使用接口，对公众平台的分组进行查询、创建、修改、删除等操作，也可以使用接口在需要时移动用户到某个分组 
 * @author  kaylves
 * @version  [版本号, 2015年5月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UserApi
{
    private static Logger logger = Logger.getLogger(UserApi.class);

    
    /**
     * <一句话功能简述>创建分组 
     * <功能详细描述>
     * url：https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN
     * @author kaylves
     * @time 2015年5月5日 上午11:46:06
     * @param name  
     *            分组名字（30个字符以内） {"group":{"name":"test"}}
     * @param accessToken
     *            [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static Group createGroup( String name, String accessToken ) throws WeiXinException
    {
        
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        
        JSONObject group = new JSONObject();
        group.put( "name",              name );
        
        jsonObject.put( "group",        group );
        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.GROUP_ADD_URL+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(!json.containsKey( "group" ))
        {
            throw new WeiXinException( json.getString( "errmsg" ) );
        }
        JSONObject job = json.getJSONObject( "group" );
        Group temp = new Group();
        temp.setId( job.getString( "id" ) );
        temp.setName( job.getString( "name" ) );
        return temp;
    }
    
    /**
     * <一句话功能简述>更新分组信息
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月13日 下午12:00:17
     * @param group             分组信息
     * @param accessToken       授权码
     * @throws WeiXinException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void updateGroup( Group group, String accessToken ) throws WeiXinException
    {
        //json数据包格式
        //{"group":{"id":108,"name":"test2_modify2"}}
        
        JSONObject jsonObject = new JSONObject();
        
        JSONObject groupObject = new JSONObject();
        
        groupObject.put( "id",                group.getId() );
        groupObject.put( "name",              group.getName() );
        
        jsonObject.put( "group",        groupObject );
        
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.GROUP_UPDATE_URL+"?access_token="+accessToken, jsonObject.toString() );
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals( json.getString( "errcode" ) ))
        {
            throw new WeiXinException( json.getString( "errmsg" ) );
        }
        
    }
    
    /**
     * <一句话功能简述>删除微信分组
     * <功能详细描述>
     * @param group
     * @param accessToken [参数说明]
     * 
     * @return void [返回类型说明]
     * @throws WeiXinException 
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void deleteGroup(Integer GroupId,String accessToken) throws WeiXinException{
        //json数据
        JSONObject json = new JSONObject();
        JSONObject groupJson = new JSONObject();
        groupJson.put( "id", GroupId );
        json.put( "group", groupJson );
        
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.GROUP_DELETE_URL+"?access_token="+accessToken, json.toString() );
        
        JSONObject jsonObject = JSONObject.fromObject( result );
        
        if(!"0".equals( jsonObject.getString( "errcode" ) ))
        {
            throw new WeiXinException( jsonObject.getString( "errmsg" ) );
        }
       
    }
    
    /**
     * <一句话功能简述>移动用户分组
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月13日 下午12:59:56
     * @param openId
     * @param toGroupid
     * @param accessToken
     * @throws WeiXinException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void moveGroup( String openId, String toGroupid, String accessToken ) throws WeiXinException
    {
        //json数据包格式
        //{"openid":"oDF3iYx0ro3_7jD4HFRDfrjdCM58","to_groupid":108}
        
        
        JSONObject groupObject = new JSONObject();
        
        groupObject.put( "openid",                  openId );
        groupObject.put( "to_groupid",              toGroupid );
        groupObject.put( "accessToken",             accessToken);
        
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.GROUP_UPDATE_URL+"?access_token="+accessToken, groupObject.toString() );
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals( json.getString( "errcode" ) ))
        {
            throw new WeiXinException( json.getString( "errmsg" ) );
        }
    }
    
    
    public static void moveGroup( List<String> openidList, String toGroupid, String accessToken ) throws WeiXinException
    {
        //json数据包格式
        //{"openid_list":["oDF3iYx0ro3_7jD4HFRDfrjdCM58","oDF3iY9FGSSRHom3B-0w5j4jlEyY"],"to_groupid":108}
//        {"openid_list":["oKeoWuJei6k1WD28DDdwHgMkwKtg","oKeoWuAQj44WPsIf-PeWrga9pe-o"],"to_groupid":"0","accessToken":"HEtWJbJcUc3CA_EewFauv_3NhnZdFLyQk0UIAe7F1Pm-KwNO67H1M_bzLDQXdpfwisRfy1VaZ0dkbjc3Axujed2cKmYm5WwzyGVrI8lEefI"}
        
        JSONObject groupObject = new JSONObject();
        
        groupObject.put( "openid_list",             openidList);
        groupObject.put( "to_groupid",              toGroupid );
        groupObject.put( "accessToken",             accessToken);
        
        
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.GROUP_ALL_MOVE_URL+"?access_token="+accessToken, groupObject.toString() );
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals( json.getString( "errcode" ) ))
        {
            throw new WeiXinException( json.getString( "errmsg" ) );
        }
    }
    
    /**
     * <一句话功能简述>查询用户所在分组
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月13日 上午11:54:02
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getUserGroupId(String openid,String accessToken)
    {
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put( "openid",        openid );
        
        
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.GROUP_ADD_URL+"?access_token="+accessToken, jsonObject.toString() );
        //响应结果说明：{"groupid": 102}
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(json.containsKey( "groupid" ))
        {
            return json.getString( "groupid" );
        }
        else
        {
            logger.warn( "调用微信查询所有分组失败："+result );
        }
        
        return null;
    }
    
    public static List<Group> getAllGroup(String accessToken)
    {
        Map<String,String> paraMap = new HashMap<String,String>();
        paraMap.put( "access_token", accessToken );
        String result = HttpClientUtils.get( WeiXinApplicationContext.GROUP_LIST_URL ,paraMap );
        
        
        JSONObject json = JSONObject.fromObject( result );
        if(json.containsKey( "errmsg" ))
        {
            logger.warn( "调用微信查询所有分组失败："+result );
            return null;
        }
        
        
        JSONArray groups = json.getJSONArray( "groups" );
        
        
        JSONObject object = null;
        Group group = null;
        List<Group> groupList = new ArrayList<Group>();
        for(int i=0; i<groups.size();i++)
        {
            object = groups.getJSONObject( i );
            group = new Group();
            group.setId( object.getString( "id" ) );
            group.setName( object.getString( "name" ) );
            groupList.add( group );
        }
        return groupList;
    }
    
    /**
     * <一句话功能简述>?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月14日 上午9:39:33
     * @param openId
     * @param accessToken
     * @return [参数说明]
     * 
     * @return WeiXinUser [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static WeiXinUser getWeiXinUserInfo(String openId,String accessToken)
    {
        //String result = "{\"subscribe\": 1,\"openid\": \"o6_bmjrPTlm6_2sgVt7hMZOPfL2M\",\"nickname\": \"Band\",\"sex\": 1, \"language\": \"zh_CN\",\"city\": \"广州\",\"province\": \"广东\",\"country\": \"中国\",\"headimgurl\":\"http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0\",\"subscribe_time\": 1382694957,\"unionid\": \" o6_bmasdasdsad6_2sgVt7hMZOPfL\",\"remark\": \"\",\"groupid\": 0}";
        Map<String,String> paraMap = new HashMap<String,String>();
        paraMap.put( "access_token", accessToken );
        paraMap.put( "openid",       openId );
        paraMap.put( "lang",         "zh_CN" );
        String result = HttpClientUtils.get( WeiXinApplicationContext.USER_INFO_URL ,paraMap );
        
        JSONObject json = JSONObject.fromObject( result );
        if(json.containsKey( "errmsg" ))
        {
            logger.warn( "调用微信查询用户信息失败："+result );
            return null;
        }
        return (WeiXinUser)JSONObject.toBean( json, WeiXinUser.class );
    }
    
    /**
     * <一句话功能简述>获取用户授权验证信息
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月14日 上午10:23:12
     * @param appid
     * @param secret
     * @param code
     * @return [参数说明]
     * 
     * @return WXUserAuthorization [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static WXUserAuthorization getUserAuthorizationCode(String appid,String secret,String code)
    {
        Map<String,String> paraMap = new HashMap<String,String>();
        paraMap.put( "appid",        appid );
        paraMap.put( "secret",       secret );
        paraMap.put( "code",         code );
        paraMap.put( "grant_type",   "authorization_code" );
        //String result = "{\"access_token\":\"ACCESS_TOKEN\",\"expires_in\":7200,\"refresh_token\":\"REFRESH_TOKEN\",\"openid\":\"OPENID\",\"scope\":\"SCOPE\",\"unionid\": \"o6_bmasdasdsad6_2sgVt7hMZOPfL\"}";
        String result = HttpClientUtils.post( WeiXinApplicationContext.AUTHORIZATION_CODE_URL,paraMap );
        
        
        JSONObject json = JSONObject.fromObject( result );
        if(json.containsKey( "errmsg" ))
        {
            logger.warn( "获取微信授权验证信息失败："+result );
            return null;
        }
        return (WXUserAuthorization)JSONObject.toBean( json, WXUserAuthorization.class );        
    }
    
    public static WeiXinAuthorUser getAhtorWeiXinUserInfo(String openId,String accessToken)
    {
        //String result = "{\"subscribe\": 1,\"openid\": \"o6_bmjrPTlm6_2sgVt7hMZOPfL2M\",\"nickname\": \"Band\",\"sex\": 1, \"language\": \"zh_CN\",\"city\": \"广州\",\"province\": \"广东\",\"country\": \"中国\",\"headimgurl\":\"http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0\",\"subscribe_time\": 1382694957,\"unionid\": \" o6_bmasdasdsad6_2sgVt7hMZOPfL\",\"remark\": \"\",\"groupid\": 0}";
        Map<String,String> paraMap = new HashMap<String,String>();
        paraMap.put( "access_token", accessToken );
        paraMap.put( "openid",       openId );
        paraMap.put( "lang",         "zh_CN" );
        String result = HttpClientUtils.get( WeiXinApplicationContext.WX_AUTHOR_USER_INFO ,paraMap );
        
        JSONObject json = JSONObject.fromObject( result );
        if(json.containsKey( "errmsg" ))
        {
            logger.warn( "调用微信查询用户信息失败："+result );
            return null;
        }
        return (WeiXinAuthorUser)JSONObject.toBean( json, WeiXinAuthorUser.class );
    }
    
    /**
     * <一句话功能简述>获取用户列表
     * <功能详细描述>url  https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID
     * @author  kaylves
     * @time  2015年5月14日 下午2:05:55
     * @param accessToken   调用接口凭证
     * @param next_openid   第一个拉取的OPENID，不填默认从头开始拉取
     * @return [参数说明]
     * 
     * @return WeiXinUsersResult [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static WeiXinUsersResult getWeiXinUsers(String accessToken,String next_openid){
        Map<String,String> paraMap = new HashMap<String,String>();
        paraMap.put( "access_token",        accessToken );
        if(next_openid!=null && !"".equals( next_openid))
        {
            paraMap.put( "next_openid",         next_openid );
        }
        String result = HttpClientUtils.get( WeiXinApplicationContext.GET_USERS_URL ,paraMap );
        
        JSONObject json = JSONObject.fromObject( result );
        if(json.containsKey( "errmsg" ))
        {
            logger.warn( "调用微信查询用户信息失败："+result );
            return null;
        }
        return (WeiXinUsersResult)JSONObject.toBean( json, WeiXinUsersResult.class );       
    }
}
