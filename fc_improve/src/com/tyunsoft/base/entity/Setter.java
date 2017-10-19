package com.tyunsoft.base.entity;

import java.util.Date;

import net.sf.json.JSONObject;

import com.tyunsoft.base.annotation.Column;
import com.tyunsoft.base.annotation.Table;

/**
 * 系统设置 对应实体类
 */
@Table(name="t_fs_setter")
public class Setter implements IEntity
{
    
    /**
     * 
     */
    @Column(name="id",isKey=true)
    private String id;
    
    /**
     * 设置名称
     */
    @Column(name="setter_name")
    private String setterName;
    
    /**
     * 设置值
     */
    @Column(name="setter_value")
    private String setterValue;
    
    /**
     * 设置说明
     */
    @Column(name="setter_remark")
    private String setterRemark;
    
    /**
     * 创建人
     */
    @Column(name="create_user")
    private String createUser;
    
    /**
     * 
     */
    @Column(name="create_date")
    private Date createDate;
    
    /**
     * 
     */
    @Column(name="update_user")
    private String updateUser;
    
    /**
     * 
     */
    @Column(name="update_date")
    private Date updateDate;
    

	public String getId()
	{  
	  return id;  
	}  
	public void setId(String id)
	{  
	  this.id = id;  
	}  
	public String getSetterName()
	{  
	  return setterName;  
	}  
	public void setSetterName(String setterName)
	{  
	  this.setterName = setterName;  
	}  
	public String getSetterValue()
	{  
	  return setterValue;  
	}  
	public void setSetterValue(String setterValue)
	{  
	  this.setterValue = setterValue;  
	}  
	public String getSetterRemark()
	{  
	  return setterRemark;  
	}  
	public void setSetterRemark(String setterRemark)
	{  
	  this.setterRemark = setterRemark;  
	}  
	public String getCreateUser()
	{  
	  return createUser;  
	}  
	public void setCreateUser(String createUser)
	{  
	  this.createUser = createUser;  
	}  
	public Date getCreateDate()
	{  
	  return createDate;  
	}  
	public void setCreateDate(Date createDate)
	{  
	  this.createDate = createDate;  
	}  
	public String getUpdateUser()
	{  
	  return updateUser;  
	}  
	public void setUpdateUser(String updateUser)
	{  
	  this.updateUser = updateUser;  
	}  
	public Date getUpdateDate()
	{  
	  return updateDate;  
	}  
	public void setUpdateDate(Date updateDate)
	{  
	  this.updateDate = updateDate;  
	}  
	  
	public String toString()
	{
	    return JSONObject.fromObject(this).toString();
	}

}
