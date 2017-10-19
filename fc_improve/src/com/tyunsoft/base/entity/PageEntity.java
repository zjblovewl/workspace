package com.tyunsoft.base.entity;

import net.sf.json.JSONObject;

/**
 * 
 * 分页对象
 * 
 * @author  flymz
 * @version  [版本号, 2013-3-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PageEntity
{

    //当前页
    private int pageNumber;
    
    //每页记录数
    private int pageSize;

    public int getPageNumber()
    {
        return pageNumber;
    }

    public void setPageNumber( int pageNumber )
    {
        this.pageNumber = pageNumber;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize( int pageSize )
    {
        this.pageSize = pageSize;
    }
    
    public String toString(){
        return JSONObject.fromObject( this ).toString();
    }
}
