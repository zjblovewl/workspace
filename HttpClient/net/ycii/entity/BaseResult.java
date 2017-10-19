package net.ycii.entity;

/**
 * <一句话功能简述>微信接口返回公共结果
 * <功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年5月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BaseResult
{
    private String errcode;
    private String errmsg;
    public String getErrcode()
    {
        return errcode;
    }
    public void setErrcode( String errcode )
    {
        this.errcode = errcode;
    }
    public String getErrmsg()
    {
        return errmsg;
    }
    public void setErrmsg( String errmsg )
    {
        this.errmsg = errmsg;
    }

}
