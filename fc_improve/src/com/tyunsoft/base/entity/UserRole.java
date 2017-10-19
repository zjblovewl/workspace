
package com.tyunsoft.base.entity;

import net.sf.json.JSONObject;

/**
 * 用户角色关联信息Bean
 * 
 * @author flymz
 */
public class UserRole
{

    // 用户编码
    private String userId;

    // 角色编码

    private String roleId;

    // 角色对应名称
    private String roleName;

    // 在所有的角色列表中，当前用户是否有权限
    private boolean checked;

    public String getUserId()
    {
        return userId;
    }

    public boolean isChecked()
    {
        checked = null != userId;
        return checked;
    }

    public void setUserId( String userId )
    {
        this.userId = userId;
    }

    public String getRoleId()
    {
        return roleId;
    }

    public void setRoleId( String roleId )
    {
        this.roleId = roleId;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName( String roleName )
    {
        this.roleName = roleName;
    }

    public String toString()
    {
        return JSONObject.fromObject( this ).toString();
    }
}
