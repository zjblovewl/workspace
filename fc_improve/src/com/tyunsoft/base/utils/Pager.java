
package com.tyunsoft.base.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * easyui page对象
 * 
 * @author flymz
 */
@SuppressWarnings( "unchecked" )
public class Pager
{

    // 总记录数
    private int total;

    // 每页数据
    private List rows = new ArrayList();
    
    private int pageCount;
    
    private boolean isStart;
    
    private boolean isEnd;

    public int getTotal()
    {
        return total;
    }

    public void setTotal( int total )
    {
        this.total = total;
    }

    public List getRows()
    {
        return rows;
    }

    public void setRows( List rows )
    {
        this.rows = rows;
    }

    public int getPageCount()
    {
        return pageCount;
    }

    public void setPageCount( int pageCount )
    {
        this.pageCount = pageCount;
    }

    public boolean isStart()
    {
        return isStart;
    }

    public void setStart( boolean isStart )
    {
        this.isStart = isStart;
    }

    public boolean isEnd()
    {
        return isEnd;
    }

    public void setEnd( boolean isEnd )
    {
        this.isEnd = isEnd;
    }
    
}
