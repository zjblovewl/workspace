
package net.ycii.entity;

/**
 * <一句话功能简述>微信消息类
 * <p>
 * <功能详细描述>
 * 
 * @author kaylves
 * @version [版本号, 2015年5月20日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class WeiXinMessage
{

    /**
     * 开发者微信号
     */
    private String toUserName;

    /**
     * 发送方帐号（一个OpenID）
     */
    private String fromUserName;
    /**
     * 消息创建时间 （整型）
     */
    private String createTime;

    /**
     * 消息类型 text 文本消息 image 图片消息 voice 语音消息 video 视频消息 shortvideo 小视频消息 location
     * 地理位置消息 link 连接消息
     */
    private String msgType;

    /**
     * 文本消息内容
     */
    private String content;

    /**
     * 图片链接
     */
    private String picUrl;

    /**
     * 
     */
    private String mediaId;

    /**
     * 语音消息媒体id，可以调用多媒体文件下载接口拉取数据
     */
    private String thumbMediaId;

    /**
     * 语音格式，如amr，speex等
     */
    private String format;

    /**
     * 地理位置维度
     */
    private String locationX;

    /**
     * 地理位置经度
     */
    private String locationY;

    /**
     * 地图缩放大小
     */
    private String scale;

    /**
     * 地理位置信息
     */
    private String label;

    /**
     * 消息标题
     */
    private String title;

    /**
     * Description消息描述
     */
    private String description;

    /**
     * Url 消息链接
     */
    private String url;
    /**
     * 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
     */
    private String event;
    /**
     * 事件KEY值，qrscene_为前缀，后面为二维码的参数值
     */
    private String eventKey;
    /**
     * 二维码的ticket，可用来换取二维码图片
     */
    private String ticket;
    
    private String latitude;
    private String longitude;
    private String precision;

    public String getEventKey()
    {
        return eventKey;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public void setLatitude( String latitude )
    {
        this.latitude = latitude;
    }

    public String getLongitude()
    {
        return longitude;
    }

    public void setLongitude( String longitude )
    {
        this.longitude = longitude;
    }

    public String getPrecision()
    {
        return precision;
    }

    public void setPrecision( String precision )
    {
        this.precision = precision;
    }

    public void setEventKey( String eventKey )
    {
        this.eventKey = eventKey;
    }

    public String getTicket()
    {
        return ticket;
    }

    public void setTicket( String ticket )
    {
        this.ticket = ticket;
    }

    public String getEvent()
    {
        return event;
    }

    public void setEvent( String event )
    {
        this.event = event;
    }

    /**
     * 消息id，64位整型
     */
    private String msgId;

    public String getContent()
    {
        return content;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public String getDescription()
    {
        return description;
    }

    public String getFormat()
    {
        return format;
    }

    public String getFromUserName()
    {
        return fromUserName;
    }

    public String getLabel()
    {
        return label;
    }

    public String getLocationX()
    {
        return locationX;
    }

    public String getLocationY()
    {
        return locationY;
    }

    public String getMediaId()
    {
        return mediaId;
    }

    public String getMsgId()
    {
        return msgId;
    }

    public String getMsgType()
    {
        return msgType;
    }

    public String getPicUrl()
    {
        return picUrl;
    }

    public String getScale()
    {
        return scale;
    }

    public String getThumbMediaId()
    {
        return thumbMediaId;
    }

    public String getTitle()
    {
        return title;
    }

    public String getToUserName()
    {
        return toUserName;
    }

    public String getUrl()
    {
        return url;
    }

    public void setContent( String content )
    {
        this.content = content;
    }

    public void setCreateTime( String createTime )
    {
        this.createTime = createTime;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public void setFormat( String format )
    {
        this.format = format;
    }

    public void setFromUserName( String fromUserName )
    {
        this.fromUserName = fromUserName;
    }

    public void setLabel( String label )
    {
        this.label = label;
    }

    public void setLocationX( String locationX )
    {
        this.locationX = locationX;
    }

    public void setLocationY( String locationY )
    {
        this.locationY = locationY;
    }

    public void setMediaId( String mediaId )
    {
        this.mediaId = mediaId;
    }

    public void setMsgId( String msgId )
    {
        this.msgId = msgId;
    }

    public void setMsgType( String msgType )
    {
        this.msgType = msgType;
    }

    public void setPicUrl( String picUrl )
    {
        this.picUrl = picUrl;
    }

    public void setScale( String scale )
    {
        this.scale = scale;
    }

    public void setThumbMediaId( String thumbMediaId )
    {
        this.thumbMediaId = thumbMediaId;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public void setToUserName( String toUserName )
    {
        this.toUserName = toUserName;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

}
