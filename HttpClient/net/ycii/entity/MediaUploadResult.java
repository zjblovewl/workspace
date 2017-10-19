package net.ycii.entity;

/**
 * <一句话功能简述>素材上传返回结果
 * <p><功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年5月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MediaUploadResult extends BaseResult
{
    /**
     * 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb，主要用于视频与音乐格式的缩略图）
     */
    private String type;
    /**
     * 媒体文件上传后，获取时的唯一标识
     */
    private String media_id;
    /**
     * 媒体文件上传时间戳,单位秒
     */
    private Integer created_at;
    public String getType()
    {
        return type;
    }
    public void setType( String type )
    {
        this.type = type;
    }
    public String getMedia_id()
    {
        return media_id;
    }
    public void setMedia_id( String media_id )
    {
        this.media_id = media_id;
    }
    public Integer getCreated_at()
    {
        return created_at;
    }
    public void setCreated_at( Integer created_at )
    {
        this.created_at = created_at;
    }
}
