package net.ycii.entity;

import java.util.Map;

/**
 * <一句话功能简述>
 * <p><功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年6月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TemplateMessage
{
    /**
     * 
     */
    private String touser;
    /**
     * 
     */
    private String template_id;
    /**
     * 
     */
    private String url;
    
    private String topcolor;
    

    public String getTouser()
    {
        return touser;
    }

    public void setTouser( String touser )
    {
        this.touser = touser;
    }


    public String getTemplate_id()
    {
        return template_id;
    }

    public void setTemplate_id( String template_id )
    {
        this.template_id = template_id;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

    public String getTopcolor()
    {
        return topcolor;
    }

    public void setTopcolor( String topcolor )
    {
        this.topcolor = topcolor;
    }

    public Map<String, Object> getData()
    {
        return data;
    }

    public void setData( Map<String, Object> data )
    {
        this.data = data;
    }

    private Map<String,Object> data;

  
}
