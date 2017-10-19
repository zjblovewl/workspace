package net.ycii.entity;

/**
 * 调用jsapi的临时票据对象
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  jack
 * @version  [版本号, 2017-7-13]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JsTicket
{
    /**
     * 返回代码
     */
    private String errcode;
    
    /**
     * 返回提示
     */
    private String errmsg;
    
    /**
     * 临时票据(用于调用微信jsapi的签名)
     */
    private String ticket;
    
    /**
     * 有效时间，默认是7200秒
     */
    private String expires_in;

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

    public String getTicket()
    {
        return ticket;
    }

    public void setTicket( String ticket )
    {
        this.ticket = ticket;
    }

    public String getExpires_in()
    {
        return expires_in;
    }

    public void setExpires_in( String expires_in )
    {
        this.expires_in = expires_in;
    }
}
