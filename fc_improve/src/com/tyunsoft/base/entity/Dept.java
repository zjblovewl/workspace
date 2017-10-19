
package com.tyunsoft.base.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

/**
 * 部门信息Bean
 * 
 * @author flymz
 */
@Component
public class Dept implements Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    // 顶级部门编码
    public static final String TOP_DEPT_ID = "0";

    // 部门编码
    private String deptId;

    // 部门名称
    private String deptName;
    
    //部门简称
    private String deptShortName = "";

    // 部门电话
    private String deptPhone;

    // 部门传真
    private String deptFax;

    // 部门地址
    private String deptAddress;

    // 上级部门，顶级部门的上级部门编码为0
    private String pDeptId;

    // 上级部门名称，顶级部门名称为显示为“无”
    private String pDeptName;

    // 部门正职
    private String deptLeader;

    // 部门层级
    private int deptLevel;

    // 创建人
    private String creator;

    // 创建日期
    private Date createDate;

    // 备注
    private String deptRemark;

    // 下级部门
    private List<Dept> children = new ArrayList<Dept>();

    public String getId()
    {
        return deptId;
    }

    public String getDeptId()
    {
        return deptId;
    }

    public void setDeptId( String deptId )
    {
        this.deptId = deptId;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public String getText()
    {
        return deptName;
    }

    public void setDeptName( String deptName )
    {
        this.deptName = deptName;
    }

    public String getDeptPhone()
    {
        return deptPhone;
    }

    public void setDeptPhone( String deptPhone )
    {
        this.deptPhone = deptPhone;
    }

    public String getDeptFax()
    {
        return deptFax;
    }

    public void setDeptFax( String deptFax )
    {
        this.deptFax = deptFax;
    }

    public String getDeptAddress()
    {
        return deptAddress;
    }

    public void setDeptAddress( String deptAddress )
    {
        this.deptAddress = deptAddress;
    }

    public String getPDeptId()
    {
        return pDeptId;
    }

    public void setPDeptId( String deptId )
    {
        pDeptId = deptId;
    }

    public String getPDeptName()
    {
        return TOP_DEPT_ID.equals( pDeptId ) ? "无" : pDeptName;
    }

    public void setPDeptName( String deptName )
    {
        pDeptName = deptName;
    }

    public String getDeptLeader()
    {
        return deptLeader;
    }

    public void setDeptLeader( String deptLeader )
    {
        this.deptLeader = deptLeader;
    }

    public String getDeptRemark()
    {
        return deptRemark;
    }

    public void setDeptRemark( String deptRemark )
    {
        this.deptRemark = deptRemark;
    }

    public int getDeptLevel()
    {
        return deptLevel;
    }

    public void setDeptLevel( int deptLevel )
    {
        this.deptLevel = deptLevel;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate( Date createDate )
    {
        this.createDate = createDate;
    }

    public List<Dept> getChildren()
    {
        return children;
    }

    public void setChildren( List<Dept> children )
    {
        this.children = children;
    }

    public String getDeptShortName() {
		return deptShortName;
	}

	public void setDeptShortName(String deptShortName) {
		this.deptShortName = deptShortName;
	}

	public String toString()
    {
        return JSONObject.fromObject( this ).toString();
    }

}
