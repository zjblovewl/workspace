
package net.ycii.fc.entity;

import java.io.Serializable;




import java.util.Date;

import net.sf.json.JSONObject;

import com.tyunsoft.base.annotation.Column;
import com.tyunsoft.base.annotation.Table;
import com.tyunsoft.base.entity.IEntity;

/**
 * 凡人好事实体类
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  周金兵
 * @version  [版本号, 2015年11月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Table( name = "t_fc_good_things" )
public class GoodThings implements IEntity,Serializable
{

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @Column( name = "id", isKey = true )
    private String id;

    /**
     * 发布时间
     */
    @Column( name = "create_time" )
    private String createTime;

    /**
     * 发布人
     */
    @Column( name = "create_user" )
    private String createUser;

    /**
     * 标题
     */
    @Column( name = "title" )
    private String title;
    
    /**
     * 内容
     */
    @Column(name="content")
    private String content;
    
    /**
     * 视频路径
     */
    @Column(name="video_path")
    private String videoPath;
    
    /**
     * 附件路径
     */
    @Column(name="accessory_path")
    private String accessoryPath;
    
    /**
     * 类型
     * 1、汶南好人
     * 2、四德榜
     * 3、凡人好事
     * 4、志愿服务
     */
    @Column(name="type")
    private Integer type;
    
    /**
     * 创建时间
     */
    @Column(name="order_time")
    private Date orderTime;
    
    @Column(name="update_time")
    private Date updateTime;
    
    public Date getOrderTime()
    {
        return orderTime;
    }

    public void setOrderTime( Date orderTime )
    {
        this.orderTime = orderTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime( Date updateTime )
    {
        this.updateTime = updateTime;
    }

    public String getVideoPath()
    {
        return videoPath;
    }

    public void setVideoPath( String videoPath )
    {
        this.videoPath = videoPath;
    }

    public String getAccessoryPath()
    {
        return accessoryPath;
    }

    public void setAccessoryPath( String accessoryPath )
    {
        this.accessoryPath = accessoryPath;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType( Integer type )
    {
        this.type = type;
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime( String createTime )
    {
        this.createTime = createTime;
    }

    public String getCreateUser()
    {
        return createUser;
    }

    public void setCreateUser( String createUser )
    {
        this.createUser = createUser;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent( String content )
    {
        this.content = content;
    } 
    
    public String toString()
    {
        return JSONObject.fromObject( this ).toString();
    }
    
}
