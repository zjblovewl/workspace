package net.ycii.entity;

public class MediaNews
{
   /**
    *  图文消息的标题
    */
    private String title;
    /**
     * 图文消息的封面图片素材id（必须是永久mediaID）
     */
    private String thumb_media_id;
    /**
     * 是否显示封面，0为false，即不显示，1为true，即显示
     */
    private String show_cover_pic;
   /**
    *  作者
    */
    private String author;
    /**
     * 图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空
     */
    private String digest;
    /**
     * 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS
     */
    private String content;
   /**
    *  图文页的URL
    */
    private String url;
    /**
     * 图文消息的原文地址，即点击“阅读原文”后的URL
     */
    private String content_source_url;
    /**
     * 自定义参数
     */
    private String tempImageUrl;
    /**
     * 自定义参数
     */
    private String id;
    public String getId()
    {
        return id;
    }
    public void setId( String id )
    {
        this.id = id;
    }
    public String getAuthor()
    {
        return author;
    }
    public String getContent()
    {
        return content;
    }
    public String getContent_source_url()
    {
        return content_source_url;
    }
    public String getDigest()
    {
        return digest;
    }
    public String getShow_cover_pic()
    {
        return show_cover_pic;
    }
    public String getTempImageUrl()
    {
        return tempImageUrl;
    }
    public String getThumb_media_id()
    {
        return thumb_media_id;
    }
    public String getTitle()
    {
        return title;
    }
    public String getUrl()
    {
        return url;
    }
    public void setAuthor( String author )
    {
        this.author = author;
    }
    public void setContent( String content )
    {
        this.content = content;
    }
    public void setContent_source_url( String content_source_url )
    {
        this.content_source_url = content_source_url;
    }
    public void setDigest( String digest )
    {
        this.digest = digest;
    }
    public void setShow_cover_pic( String show_cover_pic )
    {
        this.show_cover_pic = show_cover_pic;
    }
    public void setTempImageUrl( String tempImageUrl )
    {
        this.tempImageUrl = tempImageUrl;
    }
    public void setThumb_media_id( String thumb_media_id )
    {
        this.thumb_media_id = thumb_media_id;
    }
    public void setTitle( String title )
    {
        this.title = title;
    }
    public void setUrl( String url )
    {
        this.url = url;
    }
}
