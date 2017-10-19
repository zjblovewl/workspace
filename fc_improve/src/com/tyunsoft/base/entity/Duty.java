
package com.tyunsoft.base.entity;

import java.io.Serializable;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

/**
 * 职务信息Bean
 * 
 * @author flymz
 */
@Component
public class Duty implements Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    // 职务编码
    private String dutyId;

    // 职务名称
    private String dutyName;

    // 职务描述
    private String dutyDesc;

    public String getDutyId()
    {
        return dutyId;
    }

    public void setDutyId( String dutyId )
    {
        this.dutyId = dutyId;
    }

    public String getDutyName()
    {
        return dutyName;
    }

    public void setDutyName( String dutyName )
    {
        this.dutyName = dutyName;
    }

    public String getDutyDesc()
    {
        return dutyDesc;
    }

    public void setDutyDesc( String dutyDesc )
    {
        this.dutyDesc = dutyDesc;
    }

    public static long getSerialVersionUID()
    {
        return serialVersionUID;
    }

    public String toString()
    {
        return JSONObject.fromObject( this ).toString();
    }

}
