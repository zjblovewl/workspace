
package net.ycii.fc.entity;

import com.tyunsoft.base.annotation.Column;
import com.tyunsoft.base.annotation.Table;
import com.tyunsoft.base.entity.IEntity;

import java.io.Serializable;

@Table( name = "t_fc_wx_user" )
public class WxUser implements IEntity, Serializable
{
    private static final long serialVersionUID = 1L;

    @Column( name = "id", isKey = true )
    private String id;

    @Column( name = "partyId" )
    private long partyId;

    @Column( name = "openId" )
    private String openId;

    public WxUser()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public WxUser( String id,long partyId,String openId )
    {
        super();
        this.id = id;
        this.partyId = partyId;
        this.openId = openId;
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public long getPartyId()
    {
        return partyId;
    }

    public void setPartyId( long partyId )
    {
        this.partyId = partyId;
    }

    public String getOpenId()
    {
        return openId;
    }

    public void setOpenId( String openId )
    {
        this.openId = openId;
    }

}