
package net.ycii.entity;

/**
 * <获取素材总数>
 * <p><功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年5月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MediaUsedInfo
{
    /**
     * 语音总数量
     */
    private String voice_count;
    /**
     * 视频总数量
     */
    private String video_count;
    /**
     * 图片总数量
     */
    private String image_count;
    /**
     * 图文总数量
     */
    private String news_count;

    public String getVoice_count()
    {
        return voice_count;
    }

    public void setVoice_count( String voice_count )
    {
        this.voice_count = voice_count;
    }

    public String getVideo_count()
    {
        return video_count;
    }

    public void setVideo_count( String video_count )
    {
        this.video_count = video_count;
    }

    public String getImage_count()
    {
        return image_count;
    }

    public void setImage_count( String image_count )
    {
        this.image_count = image_count;
    }

    public String getNews_count()
    {
        return news_count;
    }

    public void setNews_count( String news_count )
    {
        this.news_count = news_count;
    }
}
