
package com.tyunsoft.base.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

/**
 * 菜单信息Bean
 * 
 * @author flymz
 */
@Component
public class Menu implements Serializable
{

    private static final long serialVersionUID = 1L;

    // 菜单编码
    private String menuId;

    // 菜单名称
    private String menuName;

    // 菜单排序
    private int menuOrder;

    // 菜单图标
    private String menuIcon;

    // 父菜单编码
    private String pMenuId = "0";

    // 菜单链接
    private String menuLink = "";

    // 是否有菜单的访问权限
    private boolean hasViewPower;

    // 子菜单
    private List<Menu> children = new ArrayList<Menu>();

    // 菜单操作功能
    private List<Function> functions = new ArrayList<Function>();

    public String getMenuId()
    {
        return menuId;
    }

    public void setMenuId( String menuId )
    {
        this.menuId = menuId;
    }

    public String getMenuName()
    {
        return menuName;
    }

    public void setMenuName( String menuName )
    {
        this.menuName = menuName;
    }

    public int getMenuOrder()
    {
        return menuOrder;
    }

    public void setMenuOrder( int menuOrder )
    {
        this.menuOrder = menuOrder;
    }

    public String getMenuIcon()
    {
        return menuIcon;
    }

    public void setMenuIcon( String menuIcon )
    {
        this.menuIcon = menuIcon;
    }

    public String getPMenuId()
    {
        return pMenuId;
    }

    public void setPMenuId( String menuId )
    {
        pMenuId = menuId;
    }

    public String getMenuLink()
    {
        return menuLink;
    }

    public void setMenuLink( String menuLink )
    {
        this.menuLink = menuLink;
    }

    public boolean isHasViewPower()
    {
        return hasViewPower;
    }

    public void setHasViewPower( boolean hasViewPower )
    {
        this.hasViewPower = hasViewPower;
    }

    public List<Menu> getChildren()
    {
        return children;
    }

    public void setChildren( List<Menu> children )
    {
        this.children = children;
    }

    public List<Function> getFunctions()
    {
        return functions;
    }

    public void setFunctions( List<Function> functions )
    {
        this.functions = functions;
    }

    public String toString()
    {
        return JSONObject.fromObject( this ).toString();
    }

}
