package net.ycii.entity;

import java.util.List;

/**
 * <一句话功能简述>
 * <p><功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年5月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MediaResultList
{
    private Integer total_count;
    
    private Integer item_count;
    
    public Integer getTotal_count()
    {
        return total_count;
    }

    public void setTotal_count( Integer total_count )
    {
        this.total_count = total_count;
    }

    public Integer getItem_count()
    {
        return item_count;
    }

    public void setItem_count( Integer item_count )
    {
        this.item_count = item_count;
    }

    public List<MediaResult> getItem()
    {
        return item;
    }

    public void setItem( List<MediaResult> item )
    {
        this.item = item;
    }

    private List<MediaResult>item;
    
}
