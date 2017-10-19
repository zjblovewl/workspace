package net.ycii.entity;

/**
 * 微信授权过后获取用户信息（两种方法，要么根据openid和普通的access_token获取用户，要么根据授权的access_token获取用户信息）
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  jack
 * @version  [版本号, 2017年9月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class WeiXinAuthorUser
{
    private String openid;
    
    private String nickname;
    
    private Integer sex;
    
    private String province;
    
    private String city;
    
    private String country;
    
    private String headimgurl;
    
    private String[] privilege;
    
    private String unionid;

    public String getOpenid()
    {
        return openid;
    }

    public void setOpenid( String openid )
    {
        this.openid = openid;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname( String nickname )
    {
        this.nickname = nickname;
    }

    public Integer getSex()
    {
        return sex;
    }

    public void setSex( Integer sex )
    {
        this.sex = sex;
    }

    public String getProvince()
    {
        return province;
    }

    public void setProvince( String province )
    {
        this.province = province;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity( String city )
    {
        this.city = city;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry( String country )
    {
        this.country = country;
    }

    public String getHeadimgurl()
    {
        return headimgurl;
    }

    public void setHeadimgurl( String headimgurl )
    {
        this.headimgurl = headimgurl;
    }

    public String[] getPrivilege()
    {
        return privilege;
    }

    public void setPrivilege( String[] privilege )
    {
        this.privilege = privilege;
    }

    public String getUnionid()
    {
        return unionid;
    }

    public void setUnionid( String unionid )
    {
        this.unionid = unionid;
    }
    
    
}
