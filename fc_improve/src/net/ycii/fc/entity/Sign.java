package net.ycii.fc.entity;

import java.util.Date;

import net.sf.json.JSONObject;

import com.tyunsoft.base.annotation.Column;
import com.tyunsoft.base.annotation.Table;
import com.tyunsoft.base.common.CacheFactory;
import com.tyunsoft.base.entity.IEntity;

/**
 * 签到签退信息 对应实体类
 */
@Table(name="t_fc_sign_in_out")
public class Sign implements IEntity
{
    
    /**
     * 
     */
    @Column(name="id",isKey=true)
    private String id;
    
    /**
     * 签到用户
     */
    @Column(name="user_id")
    private String userId;
    
    /**
     * 签到类型
     */
    @Column(name="type")
    private String type;
    
    /**
     * 签到地址
     */
    @Column(name="address")
    private String address;
    
    @Column(name="hand_address")
    private String handAddress;
    
    private String town;
    
    private String village;
    
    /**
     * 图片信息
     */
    @Column(name="img_path")
    private String imgPath;
    
    /**
     * 签到时间
     */
    @Column(name="sign_time")
    private Date signTime;
    
    /**
     * 签退的时候记录的签到编码
     */
    @Column(name="sign_id")
    private String signId;

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
	public String getType()
	{  
	  return type;  
	}  
   public String getTypeStr()
	{  
		return CacheFactory.getInstance().getDictionaryText("signtype", type);
	}  
	public void setType(String type)
	{  
	  this.type = type;  
	}  
	public String getAddress()
	{  
	  return address;  
	}  
	public void setAddress(String address)
	{  
	  this.address = address;  
	}  
	
	public String getHandAddress()
    {
        return handAddress;
    }
    public void setHandAddress( String handAddress )
    {
        this.handAddress = handAddress;
    }
    public String getImgPath()
	{  
	  return imgPath;  
	}  
	public void setImgPath(String imgPath)
	{  
	  this.imgPath = imgPath;  
	}  
	public Date getSignTime()
	{  
	  return signTime;  
	}  
	public void setSignTime(Date signTime)
	{  
	  this.signTime = signTime;  
	}  
	  
	public String getSignId()
    {
        return signId;
    }
    public void setSignId( String signId )
    {
        this.signId = signId;
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
	    return JSONObject.fromObject(this).toString();
	}

}
