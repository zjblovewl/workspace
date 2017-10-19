
package com.tyunsoft.base.entity;

import net.sf.json.JSONObject;

/**
 * 角色菜单权限Bean
 * 
 * @author Flymz
 */
public class RoleMenu
{
    // 角色编码
    private String roleId;

    // 菜单编码
    private String menuId;

    public String getRoleId()
    {
        return roleId;
    }

    public void setRoleId( String roleId )
    {
        this.roleId = roleId;
    }

    public String getMenuId()
    {
        return menuId;
    }

    public void setMenuId( String menuId )
    {
        this.menuId = menuId;
    }

    public String toString()
    {
        return JSONObject.fromObject( this ).toString();
    }
}
