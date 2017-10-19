package net.ycii.fc.entity;

import java.util.Date;

import net.sf.json.JSONObject;

import com.tyunsoft.base.annotation.Column;
import com.tyunsoft.base.annotation.Table;
import com.tyunsoft.base.entity.IEntity;

/**
 * 事件上报 对应实体类
 */
@Table(name="t_fc_thing_report")
public class ThingReport implements IEntity
{
    
    /**
     * 
     */
    @Column(name="id",isKey=true)
    private String id;
    
    /**
     * 诉求人姓名
     */
    @Column(name="appealer_name")
    private String appealerName;
    
    /**
     * 诉求人电话
     */
    @Column(name="appealer_phone")
    private String appealerPhone;
    
    /**
     * 诉求人地址
     */
    @Column(name="appealer_address")
    private String appealerAddress;
    
    /**
     * 相关图片
     */
    @Column(name="appeal_images")
    private String appealImages = "";
    
    /**
     * 上报人
     */
    @Column(name="reporter")
    private String reporter;
    
    
    private String reporterName;
    
    /**
     * 上报时间
     */
    @Column(name="report_time")
    private Date reportTime;
    
    @Column(name="description")
    private String description;
    
    @Column(name="appeal_result")
    private String appealResult;
    
    @Column(name="slsb_id")
    private String slsbId;
    @Column(name="deal_suggesttion")
    private String dealSuggesttion;
    @Column(name="deal_user")
    private String dealUser;
    @Column(name="deal_time")
    private Date dealTime;
    
    /**
     * 对应的签到编码
     */
    @Column(name="sign_id")
    private String signId;
    
    @Column(name="is_valid")
    private String isValid;

	public String getId()
	{  
	  return id;  
	}  
	public void setId(String id)
	{  
	  this.id = id;  
	}  
	public String getAppealerName()
	{  
	  return appealerName;  
	}  
	public void setAppealerName(String appealerName)
	{  
	  this.appealerName = appealerName;  
	}  
	public String getAppealerPhone()
	{  
	  return appealerPhone;  
	}  
	public void setAppealerPhone(String appealerPhone)
	{  
	  this.appealerPhone = appealerPhone;  
	}  
	public String getAppealerAddress()
	{  
	  return appealerAddress;  
	}  
	public void setAppealerAddress(String appealerAddress)
	{  
	  this.appealerAddress = appealerAddress;  
	}  
	public String getAppealImages()
	{  
	  return appealImages;  
	}  
	public void setAppealImages(String appealImages)
	{  
	  this.appealImages = appealImages;  
	}  
	public String getReporter()
	{  
	  return reporter;  
	}  
	public void setReporter(String reporter)
	{  
	  this.reporter = reporter;  
	}  
	public Date getReportTime()
	{  
	  return reportTime;  
	}  
	public void setReportTime(Date reportTime)
	{  
	  this.reportTime = reportTime;  
	}  
	  
	public String getDescription()
    {
        return description;
    }
    public void setDescription( String description )
    {
        this.description = description;
    }
    
    public String getSignId()
    {
        return signId;
    }
    public void setSignId( String signId )
    {
        this.signId = signId;
    }
    
    public String getAppealResult()
    {
        return appealResult;
    }
    public void setAppealResult( String appealResult )
    {
        this.appealResult = appealResult;
    }
    public String getSlsbId()
    {
        return slsbId;
    }
    public void setSlsbId( String slsbId )
    {
        this.slsbId = slsbId;
    }
    public String toString()
	{
	    return JSONObject.fromObject(this).toString();
	}
    public String getDealSuggesttion()
    {
        return dealSuggesttion;
    }
    public void setDealSuggesttion( String dealSuggesttion )
    {
        this.dealSuggesttion = dealSuggesttion;
    }
    public String getDealUser()
    {
        return dealUser;
    }
    public void setDealUser( String dealUser )
    {
        this.dealUser = dealUser;
    }
    public Date getDealTime()
    {
        return dealTime;
    }
    public void setDealTime( Date dealTime )
    {
        this.dealTime = dealTime;
    }
    public String getReporterName()
    {
        return reporterName;
    }
    public void setReporterName( String reporterName )
    {
        this.reporterName = reporterName;
    }
    public String getIsValid()
    {
        return isValid;
    }
    public void setIsValid( String isValid )
    {
        this.isValid = isValid;
    }

}
