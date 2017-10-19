
package com.tyunsoft.base.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 系统Session信息存取
 * 
 * @author Flymz
 */
public class SessionHelper
{

    @Autowired
    static HttpSession session;

    @Autowired
    static HttpServletRequest request;

    public static String getUserId()
    {
        session = ((ServletRequestAttributes)RequestContextHolder
                .getRequestAttributes()).getRequest().getSession();
        return String.valueOf( session.getAttribute( "userId" ) );
    }
    
    public static String getDeptId()
    {
    	session = ((ServletRequestAttributes)RequestContextHolder
                .getRequestAttributes()).getRequest().getSession();
        return String.valueOf( session.getAttribute( "deptId" ) );
    }

    public static HttpSession getSession()
    {
        return ((ServletRequestAttributes)RequestContextHolder
                .getRequestAttributes()).getRequest().getSession();
    }

    public static HttpServletRequest getRequest()
    {
        return ((ServletRequestAttributes)RequestContextHolder
                .getRequestAttributes()).getRequest();
    }

}
