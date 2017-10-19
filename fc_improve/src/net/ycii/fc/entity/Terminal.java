package net.ycii.fc.entity;

import net.sf.json.JSONObject;

import com.tyunsoft.base.annotation.Column;
import com.tyunsoft.base.annotation.Table;
import com.tyunsoft.base.entity.IEntity;

/**
 * 用户终端信息 对应实体类
 */
@Table(name="t_fc_terminal_info")
public class Terminal implements IEntity
{
    
    /**
     * 
     */
    @Column(name="id",isKey=true)
    private String id;
    
    /**
     * 型号名称
     */
    @Column(name="phone_name")
    private String phoneName;
    
    /**
     * 手机版本
     */
    @Column(name="phone_version")
    private String phoneVersion;
    
    /**
     * 软件版本
     */
    @Column(name="soft_version")
    private String softVersion;
    
    /**
     * IMSI号
     */
    @Column(name="imsi")
    private String imsi;
    
    /**
     * IMEI号
     */
    @Column(name="imei")
    private String imei;
    
    /**
     * 登录帐号
     */
    @Column(name="login_name")
    private String loginName;
    
    /**
     * 所在地
     */
    @Column(name="ownship")
    private String ownship;
    

	public String getId()
	{  
	  return id;  
	}  
	public void setId(String id)
	{  
	  this.id = id;  
	}  
	public String getPhoneName()
	{  
	  return phoneName;  
	}  
	public void setPhoneName(String phoneName)
	{  
	  this.phoneName = phoneName;  
	}  
	public String getPhoneVersion()
	{  
	  return phoneVersion;  
	}  
	public void setPhoneVersion(String phoneVersion)
	{  
	  this.phoneVersion = phoneVersion;  
	}  
	public String getSoftVersion()
	{  
	  return softVersion;  
	}  
	public void setSoftVersion(String softVersion)
	{  
	  this.softVersion = softVersion;  
	}  
	public String getImsi()
	{  
	  return imsi;  
	}  
	public void setImsi(String imsi)
	{  
	  this.imsi = imsi;  
	}  
	public String getImei()
	{  
	  return imei;  
	}  
	public void setImei(String imei)
	{  
	  this.imei = imei;  
	}  
	public String getLoginName()
	{  
	  return loginName;  
	}  
	public void setLoginName(String loginName)
	{  
	  this.loginName = loginName;  
	}  
	public String getOwnship()
	{  
	  return ownship;  
	}  
	public void setOwnship(String ownship)
	{  
	  this.ownship = ownship;  
	}  
	  
	public String toString()
	{
	    return JSONObject.fromObject(this).toString();
	}

}
