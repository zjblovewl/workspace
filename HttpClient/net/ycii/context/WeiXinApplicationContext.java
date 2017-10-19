package net.ycii.context;

/**
 * <一句话功能简述>微信应用上下文
 * <功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年5月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public final class WeiXinApplicationContext
{
    /**
     * base url
     */
    public static final String BASE_URL = "https://api.weixin.qq.com";
    /**
     * 设置消息模板所属行业
     */
    public static final String TEMPLATE_API_SET_INDUSTRY = BASE_URL+"/cgi-bin/template/api_set_industry";
    /**
     * 获得模板ID
     */
    public static final String TEMPLATE_ADD_TEMPLATE = BASE_URL+"/cgi-bin/template/api_add_template";
    /**
     * 发送模板消息
     */
    public static final String TEMPLATE_SEND = BASE_URL+"/cgi-bin/message/template/send";
    /**
     * 微信用户组列表
     */
    public static final String GROUP_LIST_URL = BASE_URL+"/cgi-bin/groups/get";
    
    /**
     * 微信组添加URL
     */
    public static String GROUP_ADD_URL=BASE_URL+"/cgi-bin/groups/create";
    
    /**
     * 微信组更新URL
     */
    public static String GROUP_UPDATE_URL=BASE_URL+"/cgi-bin/groups/update";
    
    /**
     * 微信组删除URL
     */
    public static String GROUP_DELETE_URL=BASE_URL+"/cgi-bin/groups/delete";
    /**
     * 微信移动用户分组
     */
    public static String GROUP_MOVE_URL=BASE_URL+"/cgi-bin/groups/members/update";
    
    /**
     * 微信批量移动用户分组
     */
    public static String GROUP_ALL_MOVE_URL=BASE_URL+"/cgi-bin/groups/members/batchupdate";
    /**
     * 微信用户信息Url
     */
    public static String USER_INFO_URL=BASE_URL+"/cgi-bin/user/info";
    /**
     * 用户信息接口URL
     */
    public static String GET_USERS_URL=BASE_URL+"/cgi-bin/user/get";
    /**
     * 通过code换取网页授权access_token
     */
    public static String AUTHORIZATION_CODE_URL=BASE_URL+"/sns/oauth2/access_token";
    
    /**
     * 获取Token Url
     */
    public static String ACCESS_TOKEN_URL =BASE_URL+"/cgi-bin/token";
    
    /**
     * 菜单获取URL
     */
    public static String MENU_GET_ALL_URL = BASE_URL+"/cgi-bin/menu/get";
    
    /**
     * 菜单创建URL
     */
    public static String MENU_CREATE_URL  = BASE_URL+"/cgi-bin/menu/create";
    /**
     * 删除菜单URL
     */
    public static String MENU_DELETE_URL  = BASE_URL+"/cgi-bin/menu/delete";
    
    
    /**
     * 客服服务消息
     */
    public static final String CUSTOMER_SEND_URL = BASE_URL+"/cgi-bin/message/custom/send";
    
    /**
     * 群发消息地址
     */
    public static final String MESSAGE_SENDALL_URL = BASE_URL+"/cgi-bin/message/mass/sendall";
    
    /**
     * 预览接口【订阅号与服务号认证后均可用】
     * 开发者可通过该接口发送消息给指定用户，在手机端查看消息的样式和排版。
     */
    public static final String PREVIEW_SENDALL_URL = BASE_URL+"/cgi-bin/message/mass/preview";

    /**
     * open id 群发送消息
     */
    public static final String MESSAGE_SEN_URL = BASE_URL+"/cgi-bin/message/mass/send";
    
    /**
     * 查询群发消息发送状态【订阅号与服务号认证后均可用】
     */
    public static final String MESSAGE_SENDALL_GET_STATUS_URL = BASE_URL+"/cgi-bin/message/mass/get";
    
    
    /**
     * 新增临时素材url
     */
    public static final String MEDIA_UPLOAD_URL = BASE_URL+"/cgi-bin/media/upload";
    /**
     * 获取临时素材url
     */
    public static final String MEDIA_GET_URL = BASE_URL+"/cgi-bin/media/get";
    /**
     * 新增永久性图文素材
     */
    public static final String MEDIA_ADD_NEWS_URL = BASE_URL+"/cgi-bin/material/add_news";
    
    /**
     * 修改永久图文素材
     */
    public static final String MEDIA_UPDATE_NEWS_URL = BASE_URL+"/cgi-bin/material/update_news";

    /**
     * 新增永久性其它素材
     */
    public static final String MEDIA_ADD_MATERIAL_URL = BASE_URL+"/cgi-bin/material/add_material";
    
    /**
     * 删除永久素材
     */
    public static final String MEDIA_DEL_MATERIAL_URL = BASE_URL+"/cgi-bin/material/del_material";
    /**
     * 获取素材总数
     */
    public static final String MEDIA_GET_MEDIA_COUNT = BASE_URL +"/cgi-bin/material/get_materialcount";
    
    /**
     * 获取永久素材的列表
     */
    public static final String BATCHGET_MATERIAL = BASE_URL+"/cgi-bin/material/batchget_material";
    
    
    public static final String JS_TICKET_URL = BASE_URL + "/cgi-bin/ticket/getticket"; 
    
    
    /**
     * 获取Token时返回的token key
     */
    public static String RESULT_ACCESS_TOKEN_KEY="access_token";
    
    /**
     * 获取临时素材url
     */
    public static final String MEDIA_JSSDK_GET_URL = BASE_URL+"/cgi-bin/media/get";
    
    /**
     * 获取token时ermsg
     */
    public static String RESULT_ERRMSG_KEY="errmsg";
    
    /**
     * 微信授权时获取用户信息
     */
    public static String WX_AUTHOR_USER_INFO = BASE_URL + "/sns/userinfo";
    
    
    public static boolean isNull(Object o){
        return o==null||"".equals( o.toString() )||"null".equals( o.toString() );
    }
    
    public static String nullToEmpty(Object o){
        return isNull(o)?"":o.toString();
    }
}
