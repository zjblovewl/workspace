package net.ycii.fc.entity;

import java.util.Date;

import net.sf.json.JSONObject;

import com.tyunsoft.base.annotation.Column;
import com.tyunsoft.base.annotation.Table;
import com.tyunsoft.base.entity.IEntity;

/**
 * 民生实事 对应实体类
 */
@Table(name="t_fc_people_info")
public class PeopleThing implements IEntity
{
    
    /**
     * 
     */
    @Column(name="id",isKey=true)
    private String id;
    
    /**
     * 实事内容
     */
    @Column(name="content")
    private String content;
    
    /**
     * 实际进展
     */
    @Column(name="progress")
    private String progress;
    
    /**
     * 取照图片
     */
    @Column(name="photos")
    private String photos;
    
    /**
     * 存在困难及问题
     */
    @Column(name="problem")
    private String problem;
    
    /**
     * 创建人
     */
    @Column(name="create_user")
    private String createUser;
    
    @Column(name="hand_address")
    private String handAddress;
    
    @Column(name="town")
    private String town;
    
    @Column(name="village")
    private String village;
    
    private String createUserName;
    
    /**
     * 创建日期
     */
    @Column(name="create_date")
    private Date createDate;
    

	public String getId()
	{  
	  return id;  
	}  
	public void setId(String id)
	{  
	  this.id = id;  
	}  
	public String getContent()
	{  
	  return content;  
	}  
	public void setContent(String content)
	{  
	  this.content = content;  
	}  
	public String getProgress()
	{  
	  return progress;  
	}  
	public void setProgress(String progress)
	{  
	  this.progress = progress;  
	}  
	public String getPhotos()
	{  
	  return photos;  
	}  
	public void setPhotos(String photos)
	{  
	  this.photos = photos;  
	}  
	public String getProblem()
	{  
	  return problem;  
	}  
	public void setProblem(String problem)
	{  
	  this.problem = problem;  
	}  
	public String getCreateUser()
	{  
	  return createUser;  
	}  
	public void setCreateUser(String createUser)
	{  
	  this.createUser = createUser;  
	}  
	
	public String getCreateUserName()
    {
        return createUserName;
    }
    public void setCreateUserName( String createUserName )
    {
        this.createUserName = createUserName;
    }
    public Date getCreateDate()
	{  
	  return createDate;  
	}  
	public void setCreateDate(Date createDate)
	{  
	  this.createDate = createDate;  
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
    public String getHandAddress()
    {
        return handAddress;
    }
    public void setHandAddress( String handAddress )
    {
        this.handAddress = handAddress;
    }
    public String toString()
	{
	    return JSONObject.fromObject(this).toString();
	}

}
