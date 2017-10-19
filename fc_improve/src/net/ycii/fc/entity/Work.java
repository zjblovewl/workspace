
package net.ycii.fc.entity;

import java.util.Date;

import net.sf.json.JSONObject;

import com.tyunsoft.base.annotation.Column;
import com.tyunsoft.base.annotation.Table;
import com.tyunsoft.base.entity.IEntity;

/**
 * 工作记录 对应实体类
 */
@Table( name = "t_fc_work" )
public class Work implements IEntity
{

    /**
     * 
     */
    @Column( name = "id", isKey = true )
    private String id;

    /**
     * 会议纪要
     */
    @Column( name = "meeting" )
    private String meeting;

    /**
     * 完成情况
     */
    @Column( name = "performance" )
    private String performance;

    /**
     * 记录图片
     */
    @Column( name = "record_images" )
    private String recordImages;

    /**
     * 记录地点
     */
    @Column( name = "record_address" )
    private String recordAddress;

    /**
     * 记录人
     */
    @Column( name = "recorder" )
    private String recorder;
    
    /**
     * 记录人姓名
     */
    private String recorderName;

    /**
     * 记录时间
     */
    @Column( name = "record_time" )
    private Date recordTime;

    /**
     * 签到编码
     */
    @Column( name = "sign_id" )
    private String signId;

    @Column(name="is_valid")
    private String isValid;
    
    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getMeeting()
    {
        return meeting;
    }

    public void setMeeting( String meeting )
    {
        this.meeting = meeting;
    }

    public String getPerformance()
    {
        return performance;
    }

    public void setPerformance( String performance )
    {
        this.performance = performance;
    }

    public String getRecordImages()
    {
        return recordImages;
    }

    public void setRecordImages( String recordImages )
    {
        this.recordImages = recordImages;
    }

    public String getRecordAddress()
    {
        return recordAddress;
    }

    public void setRecordAddress( String recordAddress )
    {
        this.recordAddress = recordAddress;
    }

    public String getRecorder()
    {
        return recorder;
    }

    public void setRecorder( String recorder )
    {
        this.recorder = recorder;
    }

    public Date getRecordTime()
    {
        return recordTime;
    }

    public void setRecordTime( Date recordTime )
    {
        this.recordTime = recordTime;
    }

    public String getSignId()
    {
        return signId;
    }

    public void setSignId( String signId )
    {
        this.signId = signId;
    }

    public String getRecorderName()
    {
        return recorderName;
    }

    public void setRecorderName( String recorderName )
    {
        this.recorderName = recorderName;
    }

    public String getIsValid()
    {
        return isValid;
    }

    public void setIsValid( String isValid )
    {
        this.isValid = isValid;
    }

    public String toString()
    {
        return JSONObject.fromObject( this ).toString();
    }

}
