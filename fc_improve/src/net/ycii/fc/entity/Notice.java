package net.ycii.fc.entity;

import java.util.Date;

import net.sf.json.JSONObject;

import com.tyunsoft.base.annotation.Column;
import com.tyunsoft.base.annotation.Table;
import com.tyunsoft.base.common.CacheFactory;
import com.tyunsoft.base.entity.IEntity;

/**
 * 通知公告 对应实体类
 */
@Table(name="t_fc_notice_info")
public class Notice implements IEntity
{
    
    /**
     * 
     */
    @Column(name="id",isKey=true)
    private String id;
    
    /**
     * 标题
     */
    @Column(name="title")
    private String title;
    
    /**
     * 发布时间
     */
    @Column(name="pub_time")
    private Date pubTime;
    
    /**
     * 公告图片
     */
    @Column(name="pub_image")
    private String pubImage;
    
    /**
     * 作者
     */
    @Column(name="author")
    private String author;
    
    /**
     * 内容
     */
    @Column(name="content")
    private String content;
    
    /**
     * 
     */
    @Column(name="create_user")
    private String createUser;

    /**
     * 关联的员工
     */
    @Column(name="relUser")
    private String relUser;
    
    @Column(name="rel_user_name")
    private String relUserName;
    
    @Column(name="notice_file")
    private String noticeFile;
    
    @Column(name="send_msg")
    private String sendMsg;

	public String getId()
	{  
	  return id;  
	}  
	public void setId(String id)
	{  
	  this.id = id;  
	}  
	public String getTitle()
	{  
	  return title;  
	}  
	public void setTitle(String title)
	{  
	  this.title = title;  
	}  
	public Date getPubTime()
	{  
	  return pubTime;  
	}  
	public void setPubTime(Date pubTime)
	{  
	  this.pubTime = pubTime;  
	}  
	public String getPubImage()
	{  
	  return pubImage;  
	}  
	public void setPubImage(String pubImage)
	{  
	  this.pubImage = pubImage;  
	}  
	public String getAuthor()
	{  
	  return author;  
	}  
	public void setAuthor(String author)
	{  
	  this.author = author;  
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
	
	public String getSendMsgStr()
	{
	    return CacheFactory.getInstance().getDictionaryText("yesno", sendMsg);
	}
	  
	public String getSendMsg()
    {
        return sendMsg;
    }
    public void setSendMsg( String sendMsg )
    {
        this.sendMsg = sendMsg;
    }
    public String toString()
	{
	    return JSONObject.fromObject(this).toString();
	}
    public String getRelUser()
    {
        return relUser;
    }
    public void setRelUser( String relUser )
    {
        this.relUser = relUser;
    }
    public String getNoticeFile()
    {
        return noticeFile;
    }
    public void setNoticeFile( String noticeFile )
    {
        this.noticeFile = noticeFile;
    }
    public String getRelUserName()
    {
        return relUserName;
    }
    public void setRelUserName( String relUserName )
    {
        this.relUserName = relUserName;
    }

}
