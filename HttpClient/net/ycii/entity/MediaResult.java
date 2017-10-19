package net.ycii.entity;


/**
 * <素材结果>
 * <p><功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年5月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MediaResult
{
    /**
     * 
     */
    private String media_id;
    /**
     * 内容
     */
    private MediaContent content;
    /**
     * 更新时间
     */
    private Integer update_time;
    public MediaContent getContent()
    {
        return content;
    }
    public String getMedia_id()
    {
        return media_id;
    }
    public Integer getUpdate_time()
    {
        return update_time;
    }
    public void setContent( MediaContent content )
    {
        this.content = content;
    }
    public void setMedia_id( String media_id )
    {
        this.media_id = media_id;
    }
    public void setUpdate_time( Integer update_time )
    {
        this.update_time = update_time;
    }
}
