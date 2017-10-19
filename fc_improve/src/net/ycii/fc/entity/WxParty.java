package net.ycii.fc.entity;

import com.tyunsoft.base.annotation.Column;
import com.tyunsoft.base.annotation.Table;
import com.tyunsoft.base.entity.IEntity;
import java.io.Serializable;

@Table(name="t_fc_wx")
public class WxParty
  implements IEntity, Serializable
{
  private static final long serialVersionUID = 1L;

  @Column(name="partyId", isKey=true)
  private long partyId;

  @Column(name="name")
  private String name;

  @Column(name="appid")
  private String appId;

  @Column(name="appsecret")
  private String appSecret;

  @Column(name="token")
  private String token;

  public long getPartyId()
  {
    return this.partyId;
  }

  public void setPartyId(long partyId)
  {
    this.partyId = partyId;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getAppId()
  {
    return this.appId;
  }

  public void setAppId(String appId)
  {
    this.appId = appId;
  }

  public String getAppSecret()
  {
    return this.appSecret;
  }

  public void setAppSecret(String appSecret)
  {
    this.appSecret = appSecret;
  }

  public String getToken()
  {
    return this.token;
  }

  public void setToken(String token)
  {
    this.token = token;
  }
}