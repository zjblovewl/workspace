package net.ycii.fc.entity;

import java.util.Date;

import net.sf.json.JSONObject;

import com.tyunsoft.base.annotation.Column;
import com.tyunsoft.base.annotation.Table;
import com.tyunsoft.base.entity.IEntity;

/**
 * 短信信息 对应实体类
 */
@Table(name="t_fc_sms")
public class Sms implements IEntity
{
    
    /**
     * 
     */
    @Column(name="id",isKey=true)
    private String id;
    
    /**
     * 接收人
     */
    @Column(name="send_users")
    private String sendUsers;
    
    /**
     * 短信内容
     */
    @Column(name="content")
    private String content;
    
    /**
     * 发送人
     */
    @Column(name="create_user")
    private String createUser;
    
    /**
     * 发送日期
     */
    @Column(name="create_date")
    private Date createDate;
    
    private String createUserIds;

	public String getId()
	{  
	  return id;  
	}  
	public void setId(String id)
	{  
	  this.id = id;  
	}  
	public String getSendUsers()
	{  
	  return sendUsers;  
	}  
	public void setSendUsers(String sendUsers)
	{  
	  this.sendUsers = sendUsers;  
	}  
	public String getContent()
	{  
	  return content;  
	}  
	public void setContent(String content)
	{  
	  this.content = content;  
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
	  
	public String toString()
	{
	    return JSONObject.fromObject(this).toString();
	}
    public String getCreateUserIds()
    {
        return createUserIds;
    }
    public void setCreateUserIds( String createUserIds )
    {
        this.createUserIds = createUserIds;
    }

}
