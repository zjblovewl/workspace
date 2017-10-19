
package net.ycii.fc.entity;

import net.sf.json.JSONObject;

import com.tyunsoft.base.annotation.Column;
import com.tyunsoft.base.annotation.Table;
import com.tyunsoft.base.entity.IEntity;

/**
 * 通讯录 对应实体类
 */
@Table( name = "t_fc_addrbook" )
public class AddrBook implements IEntity
{

    /**
     * 
     */
    @Column( name = "id", isKey = true )
    private String id;

    /**
     * 姓名
     */
    @Column( name = "name" )
    private String name;

    /**
     * 部门
     */
    @Column( name = "department" )
    private String department;

    /**
     * 手机号
     */
    @Column( name = "phone_num" )
    private String phoneNum;

    @Column( name = "town" )
    private String town;

    @Column( name = "village" )
    private String village;

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment( String department )
    {
        this.department = department;
    }

    public String getPhoneNum()
    {
        return phoneNum;
    }

    public void setPhoneNum( String phoneNum )
    {
        this.phoneNum = phoneNum;
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

    public String toString()
    {
        return JSONObject.fromObject( this ).toString();
    }

}
