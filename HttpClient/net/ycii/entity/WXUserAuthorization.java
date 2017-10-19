package net.ycii.entity;

/**
 * <一句话功能简述>微信用户授权后返回值
 * <功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年5月14日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class WXUserAuthorization
{
    /**
     * 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
     */
    private String access_token;
    /**
     * 用户刷新access_token
     * 
     */
    private String refresh_token;
    /**
     * expires_in   access_token接口调用凭证超时时间，单位（秒）
     */
    private Integer expires_in;
    /**
     * 用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
     */
    private String openid;
    /**
     * 用户授权的作用域，使用逗号（,）分隔
     */
    private String scope;
    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
     */
    private String unionid;
    public String getAccess_token()
    {
        return access_token;
    }
    public Integer getExpires_in()
    {
        return expires_in;
    }
    public String getOpenid()
    {
        return openid;
    }
    public String getRefresh_token()
    {
        return refresh_token;
    }
    public String getScope()
    {
        return scope;
    }
    public String getUnionid()
    {
        return unionid;
    }
    public void setAccess_token( String access_token )
    {
        this.access_token = access_token;
    }
    public void setExpires_in( Integer expires_in )
    {
        this.expires_in = expires_in;
    }
    public void setOpenid( String openid )
    {
        this.openid = openid;
    }
    public void setRefresh_token( String refresh_token )
    {
        this.refresh_token = refresh_token;
    }
    public void setScope( String scope )
    {
        this.scope = scope;
    }
    public void setUnionid( String unionid )
    {
        this.unionid = unionid;
    }
}
