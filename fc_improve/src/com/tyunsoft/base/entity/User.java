
package com.tyunsoft.base.entity;

import java.io.Serializable;
import java.util.Date;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

/**
 * 用户信息Bean
 * 
 * @author flymz
 */
@Component
public class User implements Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    // 用户名
    private String userId = "";

    // 用户密码
    private String userPswd;

    // 真实姓名
    private String userName;

    // 性别
    private String sex;

    // 是否在职 0为在职，1为离职
    private String isOnJob;

    // 是否被删除 0为正常，1为被删除
    private String isDeleted;

    // 所属部门编码
    private String deptId;

    // 所在部门的名称
    private String deptName;

    // 职务编码
    private String dutyId;

    // 职务名称
    private String dutyName;

    // 用户所拥有的角色，使用,号隔开
    private String roleIds;

    // 用户所拥有的角色名称，使用,号隔开
    private String roleNames;

    // 创建者
    private String creator;

    // 创建日期
    private Date createDate;
    
    private int deptLevel;
    
    private String userPhone;
    
    private String town;
    
    private String village;

    public String getUserId()
    {
        return userId;
    }

    public void setUserId( String userId )
    {
        this.userId = userId;
    }

    public String getUserPswd()
    {
        return userPswd;
    }

    public void setUserPswd( String userPswd )
    {
        this.userPswd = userPswd;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName( String userName )
    {
        this.userName = userName;
    }

    public String getSex()
    {
        return sex;
    }

    public String getSexStr()
    {
        return "0".equals( sex ) ? "男" : "女";
    }

    public void setSex( String sex )
    {
        this.sex = sex;
    }

    public String getIsOnJob()
    {
        return isOnJob;
    }

    public String getIsOnJobStr()
    {
        return "0".equals( isOnJob ) ? "在职" : "已离职";
    }

    public void setIsOnJob( String isOnJob )
    {
        this.isOnJob = isOnJob;
    }

    public String getDeptId()
    {
        return deptId;
    }

    public void setDeptId( String deptId )
    {
        this.deptId = deptId;
    }

    public String getDeptName()
    {
        return deptName == null ? "无" : deptName;
    }

    public void setDeptName( String deptName )
    {
        this.deptName = deptName;
    }

    public String getDutyId()
    {
        return dutyId;
    }

    public void setDutyId( String dutyId )
    {
        this.dutyId = dutyId;
    }

    public String getDutyName()
    {
        return dutyName;
    }

    public void setDutyName( String dutyName )
    {
        this.dutyName = dutyName;
    }

    public String getRoleIds()
    {
        return (roleIds != null &&roleIds.startsWith(","))?roleIds.substring(1):roleIds;
    }

    public void setRoleIds( String roleIds )
    {
        this.roleIds = roleIds;
    }

    public String getRoleNames()
    {
        return roleNames;
    }

    public void setRoleNames( String roleNames )
    {
        this.roleNames = roleNames;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate( Date createDate )
    {
        this.createDate = createDate;
    }

    public String getIsDeleted()
    {
        return isDeleted;
    }

    public void setIsDeleted( String isDeleted )
    {
        this.isDeleted = isDeleted;
    }

    public int getDeptLevel() {
		return deptLevel;
	}

	public void setDeptLevel(int deptLevel) {
		this.deptLevel = deptLevel;
	}

	public String getUserPhone()
    {
        return userPhone;
    }

    public void setUserPhone( String userPhone )
    {
        this.userPhone = userPhone;
    }

    public String getTown()
    {
        return town;
    }

    public void setTown( String town )
    {
        this.town = town;
    }

    public String getVillage()
    {
        return village;
    }

    public void setVillage( String village )
    {
        this.village = village;
    }

    public String toString()
    {
        return JSONObject.fromObject( this ).toString();
    }
}
