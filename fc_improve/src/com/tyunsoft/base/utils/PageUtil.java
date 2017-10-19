
package com.tyunsoft.base.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tyunsoft.base.entity.PageEntity;

/**
 * <p>
 * File: PageUtil.java
 * </p>
 * <p>
 * Date:2008-6-1
 * </p>
 * <p>
 * Description: 关于分页操作计算的基础类
 * </p>
 * 
 * @author flymz
 * @version 1.0
 * @history
 */
public class PageUtil
{

    /**
     * 默认每页显示记录数
     */
    public final static int PAGE_SIZE = 18;

    public final static String getBasePath( HttpServletRequest request )
    {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + path + "/";
        return basePath;
    }

    public final static void write( HttpServletResponse response, String string )
    {
        try
        {
            response.getWriter().write( string );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    /**
     * 获取分页所需要的数据
     * 
     * @param countSize
     *            总记录条数
     * @param pageSize
     *            每页显示记录数
     * @param pageNumber
     *            当前页号
     * @return int[0] 表示pageCount 总页数, int[1] 表示currPageSize 当前页的记录条数, int[2]
     *         表示本页的开始记录, int[3] 表示本页的结束记录数
     */
    public static int[] getPageData( int countSize, int pageSize, int pageNumber )
    {
        int[] data = new int[4];
        // 获取总页数
        int pageCount = 0;
        if ( countSize % pageSize == 0 )
        {
            pageCount = countSize / pageSize;
        } else if ( countSize < pageSize )
        {
            pageCount = 1;
        } else
        {
            pageCount = countSize / pageSize + 1;
        }
        data[0] = pageCount;
        // 获取当前页记录条数
        // if(pageNumber==0) pageNumber=1;
        int currPageSize = 0;
        if ( pageNumber < pageCount )
        {// 当前页不是最后一页,那么当前页记录条数为每页显示记录条数
            currPageSize = pageSize;
        } else
        {
            currPageSize = countSize - (pageNumber - 1) * pageSize;
        }
        data[1] = currPageSize;
        // 获取开始记录数和结束记录数
        int form = 0;
        int to = 0;
        form = (pageNumber - 1) * pageSize;
        data[2] = form + 1;
        to = (pageNumber - 1) * pageSize + currPageSize;
        data[3] = to;
        return data;
    }
    
    public static PageEntity getPageNumAndSize(HttpServletRequest request)
    {
        PageEntity page = new PageEntity();
        int pageNumber = 0;
        int pageSize = 10;
        if ( null != request.getParameter( "page" ) )
        {
            pageNumber = Integer.parseInt( request.getParameter( "page" ) );
        }
        if ( null != request.getParameter( "rows" ) )
        {
            pageSize = Integer.parseInt( request.getParameter( "rows" ) );
        }
        if(pageNumber == 0)
        {
            pageNumber = 1;
        }
        page.setPageNumber( pageNumber );
        page.setPageSize( pageSize );
        return page;
    }

    public static int[] getPageData( int countSize, int pageNumber )
    {
        int[] data = new int[4];
        // 获取总页数
        int pageCount = 0;
        if ( countSize % PAGE_SIZE == 0 )
        {
            pageCount = countSize / PAGE_SIZE;
        } else if ( countSize < PAGE_SIZE )
        {
            pageCount = 1;
        } else
        {
            pageCount = countSize / PAGE_SIZE + 1;
        }
        if ( pageNumber > pageCount )
        {
            pageNumber = pageCount;
        }
        if ( pageCount == 0 )
        {
            pageCount = pageNumber = 1;
        }
        data[0] = pageCount;
        // 获取当前页记录条数
        // if(pageNumber==0) pageNumber=1;
        int currPageSize = 0;
        if ( pageNumber < pageCount )
        {// 当前页不是最后一页,那么当前页记录条数为每页显示记录条数
            currPageSize = PAGE_SIZE;
        } else
        {
            currPageSize = countSize - (pageNumber - 1) * PAGE_SIZE;
        }
        data[1] = currPageSize;
        // 获取开始记录数和结束记录数
        int form = 0;
        int to = 0;
        form = (pageNumber - 1) * PAGE_SIZE;
        data[2] = form + 1;
        to = (pageNumber - 1) * PAGE_SIZE + currPageSize;
        data[3] = to;
        return data;
    }

}
