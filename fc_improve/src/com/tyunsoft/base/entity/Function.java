
package com.tyunsoft.base.entity;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * 系统功能信息
 * 
 * @author flymz
 */
public class Function implements Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    // 功能对应菜单编码
    private String menuId;

    // 功能编码
    private String funId;

    // 功能名称
    private String funName;

    // 功能排序
    private int funOrder;

    // 菜单的访问链接，用于进行权限检查
    private String funLink;

    // 对应的角色中该功能是否被选中
    private boolean isChecked = false;

    public String getMenuId()
    {
        return menuId;
    }

    public void setMenuId( String menuId )
    {
        this.menuId = menuId;
    }

    public String getFunId()
    {
        return funId;
    }

    public void setFunId( String funId )
    {
        this.funId = funId;
    }

    public String getFunName()
    {
        return funName;
    }

    public void setFunName( String funName )
    {
        this.funName = funName;
    }

    public int getFunOrder()
    {
        return funOrder;
    }

    public void setFunOrder( int funOrder )
    {
        this.funOrder = funOrder;
    }

    public String getFunLink()
    {
        return funLink;
    }

    public void setFunLink( String funLink )
    {
        this.funLink = funLink;
    }

    public boolean isChecked()
    {
        return isChecked;
    }

    public void setChecked( boolean isChecked )
    {
        this.isChecked = isChecked;
    }

    public String toString()
    {
        return JSONObject.fromObject( this ).toString();
    }

}
