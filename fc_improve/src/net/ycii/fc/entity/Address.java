package net.ycii.fc.entity;


import net.sf.json.JSONObject;

import com.tyunsoft.base.annotation.Column;
import com.tyunsoft.base.annotation.Table;
import com.tyunsoft.base.entity.IEntity;

/**
 * 联络人管理 对应实体类
 */
@Table(name="t_fc_address")
public class Address implements IEntity
{
    
    /**
     * 
     */
    @Column(name="id",isKey=true)
    private String id;
    
    /**
     * 镇街社区
     */
    @Column(name="town")
    private String town;
    
    /**
     * 村小区
     */
    @Column(name="village")
    private String village;
    
    /**
     * 
     */
    @Column(name="address_type")
    private String addressType;
    

	public String getId()
	{  
	  return id;  
	}  
	public void setId(String id)
	{  
	  this.id = id;  
	}  
	public String getTown()
	{  
	  return town;  
	}  
	public void setTown(String town)
	{  
	  this.town = town;  
	}  
	public String getVillage()
	{  
	  return village;  
	}  
	public void setVillage(String village)
	{  
	  this.village = village;  
	}  
	public String getAddressType()
	{  
	  return addressType;  
	}  
	public void setAddressType(String addressType)
	{  
	  this.addressType = addressType;  
	}  
	  
	public String toString()
	{
	    return JSONObject.fromObject(this).toString();
	}

}
