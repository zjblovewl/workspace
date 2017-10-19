package net.ycii.entity;

/**
 * <一句话功能简述>微信用户分组
 * <功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年5月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Group
{
    /**
     * 组ID
     */
    private String id;
    /**
     * 组名称
     */
    private String name;
    public String getId()
    {
        return id;
    }
    public void setId( String id )
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName( String name )
    {
        this.name = name;
    }
    
}
