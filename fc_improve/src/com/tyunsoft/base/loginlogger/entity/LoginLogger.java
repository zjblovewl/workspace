package com.tyunsoft.base.loginlogger.entity;

import java.util.Date;

import com.tyunsoft.base.annotation.Column;
import com.tyunsoft.base.annotation.Table;
import com.tyunsoft.base.entity.IEntity;

/**
 * 登录日志 对应实体类
 */
@Table(name="t_fs_login_logger")
public class LoginLogger implements IEntity
{
    
    /**
     * 
     */
    @Column(name="id",isKey=true)
    private String id;
    
    /**
     * 登录帐号
     */
    @Column(name="user_id")
    private String userId;
    
    /**
     * 最后登录时间
     */
    @Column(name="last_login_time")
    private Date lastLoginTime;
    
    /**
     * 登录IP
     */
    @Column(name="login_ip")
    private String loginIp;
    

	public String getId()
	{  
	  return id;  
	}  
	public void setId(String id)
	{  
	  this.id = id;  
	}  
	public String getUserId()
	{  
	  return userId;  
	}  
	public void setUserId(String userId)
	{  
	  this.userId = userId;  
	}  
	public Date getLastLoginTime()
	{  
	  return lastLoginTime;  
	}  
	public void setLastLoginTime(Date lastLoginTime)
	{  
	  this.lastLoginTime = lastLoginTime;  
	}  
	public String getLoginIp()
	{  
	  return loginIp;  
	}  
	public void setLoginIp(String loginIp)
	{  
	  this.loginIp = loginIp;  
	}  
	  
}
