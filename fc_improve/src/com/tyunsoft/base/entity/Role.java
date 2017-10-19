
package com.tyunsoft.base.entity;

import net.sf.json.JSONObject;

/**
 * 角色信息对象
 * 
 * @author flymz
 */
public class Role
{

    // 角色编码
    private String roleId;

    // 角色名称
    private String roleName;

    // 角色说明
    private String roleRemark;

    // 是否被删除 0为正常，1为被删除
    private String isDeleted;

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

    public String getRoleRemark()
    {
        return roleRemark;
    }

    public void setRoleRemark( String roleRemark )
    {
        this.roleRemark = roleRemark;
    }

    public String getIsDeleted()
    {
        return isDeleted;
    }

    public void setIsDeleted( String isDeleted )
    {
        this.isDeleted = isDeleted;
    }

    public String toString()
    {
        return JSONObject.fromObject( this ).toString();
    }

}
