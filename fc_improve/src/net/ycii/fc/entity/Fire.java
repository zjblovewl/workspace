package net.ycii.fc.entity;

import com.tyunsoft.base.annotation.Column;
import com.tyunsoft.base.annotation.Table;
import com.tyunsoft.base.entity.IEntity;
import java.io.Serializable;
import java.util.Date;
import net.sf.json.JSONObject;

@Table(name="t_fc_fire")
public class Fire
  implements IEntity, Serializable
{
  private static final long serialVersionUID = 1L;

  @Column(name="id", isKey=true)
  private String id;

  @Column(name="create_time")
  private Date createTime;

  @Column(name="default_time")
  private Date defatulTime;

  @Column(name="create_user")
  private String createUser;

  @Column(name="postion")
  private String postion;

  @Column(name="longitude")
  private String longitude;

  @Column(name="latitude")
  private String latitude;

  @Column(name="fire")
  private String fire;

  @Column(name="fire_describe")
  private String fireDescribe;
  private String tempTime;

  public Date getDefatulTime()
  {
    return this.defatulTime;
  }

  public void setDefatulTime(Date defatulTime)
  {
    this.defatulTime = defatulTime;
  }

  public String getTempTime()
  {
    return this.tempTime;
  }

  public void setTempTime(String tempTime)
  {
    this.tempTime = tempTime;
  }

  public String getId()
  {
    return this.id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }

  public String getCreateUser()
  {
    return this.createUser;
  }

  public void setCreateUser(String createUser)
  {
    this.createUser = createUser;
  }

  public String getPostion()
  {
    return this.postion;
  }

  public void setPostion(String postion)
  {
    this.postion = postion;
  }

  public String getLongitude()
  {
    return this.longitude;
  }

  public void setLongitude(String longitude)
  {
    this.longitude = longitude;
  }

  public String getLatitude()
  {
    return this.latitude;
  }

  public void setLatitude(String latitude)
  {
    this.latitude = latitude;
  }

  public String getFire()
  {
    return this.fire;
  }

  public void setFire(String fire)
  {
    this.fire = fire;
  }

  public String getFireDescribe()
  {
    return this.fireDescribe;
  }

  public void setFireDescribe(String fireDescribe)
  {
    this.fireDescribe = fireDescribe;
  }

  public String toString()
  {
    return JSONObject.fromObject(this).toString();
  }
}