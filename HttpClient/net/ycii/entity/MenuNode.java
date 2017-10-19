package net.ycii.entity;

import java.util.List;

/**
 * <一句话功能简述>微信菜单
 * <p><功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年5月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MenuNode
{
    /**
     * 菜单类型
     */
    private MenuType type;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单key
     */
    private String key;
    
    /**
     * 网页链接，用户点击菜单可打开链接，不超过256字节
     */
    private String url;
    /**
     * media_id类型和view_limited类型必须  
     * 调用新增永久素材接口返回的合法media_id
     */
    private String media_id;
    /**
     * 当前菜单下子菜单
     */
    private List<MenuNode> sub_button;
    public MenuType getType()
    {
        return type;
    }
    public void setType( MenuType type )
    {
        this.type = type;
    }
    public String getName()
    {
        return name;
    }
    public void setName( String name )
    {
        this.name = name;
    }
    public String getKey()
    {
        return key;
    }
    public void setKey( String key )
    {
        this.key = key;
    }
    public String getUrl()
    {
        return url;
    }
    public void setUrl( String url )
    {
        this.url = url;
    }
    public String getMedia_id()
    {
        return media_id;
    }
    public void setMedia_id( String media_id )
    {
        this.media_id = media_id;
    }
    public List<MenuNode> getSub_button()
    {
        return sub_button;
    }
    public void setSub_button( List<MenuNode> sub_button )
    {
        this.sub_button = sub_button;
    }
    
}
