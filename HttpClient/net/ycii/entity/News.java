package net.ycii.entity;

/**
 * <一句话功能简述>微信图文消息
 * <p><功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年5月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class News
{
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 点击后跳转的链接
     */
    private String url;
    /**
     * 图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80
     */
    private String picurl;
    /**
     * <获取描述信息>
     * <获取描述信息>
     * @author  kaylves
     * @time  2015年5月21日 下午3:35:23
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String getDescription()
    {
        return description;
    }
    /**
     * <图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80>
     * <图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80>
     * @author  kaylves
     * @time  2015年5月21日 下午3:36:18
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String getPicurl()
    {
        return picurl;
    }
    /**
     * <图文消息标题>
     * <图文消息标题>
     * @author  kaylves
     * @time  2015年5月21日 下午3:34:28
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String getTitle()
    {
        return title;
    }
    /**
     * <点击后跳转的链接>
     * <点击后跳转的链接>
     * @author  kaylves
     * @time  2015年5月21日 下午3:36:01
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String getUrl()
    {
        return url;
    }
    public void setDescription( String description )
    {
        this.description = description;
    }
    public void setPicurl( String picurl )
    {
        this.picurl = picurl;
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
