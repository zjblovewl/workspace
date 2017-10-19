/*
 * 文 件 名:  TreeNode.java
 * 版    权:  Foresoft Technologies,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2015年2月13日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package net.ycii.fc.entity;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年2月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TreeNode
{
    public String nid;
    
    public String nPid;
    
    public String name;
    
    public String title;
    
    public String isParent;

    public String getNid()
    {
        return nid;
    }

    public void setNid( String nid )
    {
        this.nid = nid;
    }

    public String getnPid()
    {
        return nPid;
    }

    public void setnPid( String nPid )
    {
        this.nPid = nPid;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public String getIsParent()
    {
        return isParent;
    }

    public void setIsParent( String isParent )
    {
        this.isParent = isParent;
    }
    
}
