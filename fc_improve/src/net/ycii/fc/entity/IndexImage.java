package net.ycii.fc.entity;

import java.util.Date;

import net.sf.json.JSONObject;

import com.tyunsoft.base.annotation.Column;
import com.tyunsoft.base.annotation.Table;
import com.tyunsoft.base.entity.IEntity;

/**
 * 首页图片 对应实体类
 */
@Table(name="t_fc_index_image")
public class IndexImage implements IEntity
{
    
    /**
     * 
     */
    @Column(name="id",isKey=true)
    private String id;
    
    /**
     * 首页图片
     */
    @Column(name="image_path")
    private String imagePath;
    
    /**
     * 点击路径
     */
    @Column(name="click_url")
    private String clickUrl;
    
    /**
     * 内容
     */
    @Column(name="content")
    private String content;
    
    /**
     * 创建日期
     */
    @Column(name="create_date")
    private Date createDate;
    
    /**
     * 创建人
     */
    @Column(name="create_user")
    private String createUser;
    

	public String getId()
	{  
	  return id;  
	}  
	public void setId(String id)
	{  
	  this.id = id;  
	}  
	public String getImagePath()
	{  
	  return imagePath;  
	}  
	public void setImagePath(String imagePath)
	{  
	  this.imagePath = imagePath;  
	}  
	public String getClickUrl()
	{  
	  return clickUrl;  
	}  
	public void setClickUrl(String clickUrl)
	{  
	  this.clickUrl = clickUrl;  
	}  
	public String getContent()
	{  
	  return content;  
	}  
	public void setContent(String content)
	{  
	  this.content = content;  
	}  
	public Date getCreateDate()
	{  
	  return createDate;  
	}  
	public void setCreateDate(Date createDate)
	{  
	  this.createDate = createDate;  
	}  
	public String getCreateUser()
	{  
	  return createUser;  
	}  
	public void setCreateUser(String createUser)
	{  
	  this.createUser = createUser;  
	}  
	  
	public String toString()
	{
	    return JSONObject.fromObject(this).toString();
	}

}
