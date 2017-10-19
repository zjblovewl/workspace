
package com.tyunsoft.base.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tyunsoft.base.common.CacheFactory;
import com.tyunsoft.base.common.SessionHelper;
import com.tyunsoft.base.entity.Menu;
import com.tyunsoft.base.entity.User;
import com.tyunsoft.base.loginlogger.entity.LoginLogger;
import com.tyunsoft.base.loginlogger.service.ILoginLoggerService;
import com.tyunsoft.base.service.ICacheService;
import com.tyunsoft.base.service.IUserService;
import com.tyunsoft.base.utils.CheckMobile;
import com.tyunsoft.base.utils.JsonUtil;
import com.tyunsoft.base.utils.MD5;

/**
 * 用户登录Controller
 * @author  flymz 
 * @version  [版本号, 2013-3-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
public class LoginController
{

    @Autowired
    private IUserService userService;

    @Autowired
    private ICacheService cacheService;
    
    //记录用户登录日志信息
    @Autowired
    private ILoginLoggerService loginLoggerService;

    /**
     * 系统登录页面
     * @return 跳转到系统登录页面
     */
    @RequestMapping( value = "/free/index.htm" )
    public ModelAndView index(HttpServletRequest request)
    {
        String userAgent = request.getHeader( "USER-AGENT" ).toLowerCase();  
        if(CheckMobile.check( userAgent ))
        {
            return new ModelAndView("mobile_login");
        }
        else                                           
        {
            return new ModelAndView( CacheFactory.getInstance().getSetter( "login.page" ) );
        }
    }

    /**
     * 用户登录
     * @param request 请求
     * @param response 响应
     * @return 重定向到系统管理首页
     */
    @RequestMapping( value = "/free/login.htm" )
    public ModelAndView login( HttpServletRequest request,
            HttpServletResponse response )
    {
        String userId = request.getParameter( "userId" );
        String password = request.getParameter( "password" );
        User result = userService.login( userId, MD5.password( password ) );
        if ( null == result )
        {// 登录失败,跳转到登录页面
            request.setAttribute( "userId", userId );
            request.setAttribute( "error", "用户名或者密码错误!" );
            String userAgent = request.getHeader( "USER-AGENT" ).toLowerCase();  
            if(CheckMobile.check( userAgent ))
            {
                return new ModelAndView("mobile_login");
            }
            else                                           
            {
                return new ModelAndView( CacheFactory.getInstance().getSetter( "login.page" ) );
            }
        } else
        {

            HttpSession session = request.getSession( true );
            session.setAttribute( "userId", userId );
            session.setAttribute( "userName", result.getUserName() );
            session.setAttribute( "currentUser", result );
            session.setAttribute("deptId", result.getDeptId());
            session.setAttribute("deptName", result.getDeptName());
            session.setAttribute("deptLevel", result.getDeptLevel());
            //记录系统用户登录信息
            LoginLogger logger = new LoginLogger();
            logger.setId( SessionHelper.getUserId() );
            logger.setUserId( SessionHelper.getUserId() );
            logger.setLastLoginTime( new Date() );
            logger.setLoginIp( getIpAddr(request) );
            boolean updateResult = loginLoggerService.updateById( logger );
            if(!updateResult)
            {
                loginLoggerService.insert( logger );
            }
            // 设置用户菜单缓存
            cacheService.setUserMenuCache( SessionHelper.getUserId() );

            // 设置用户操作功能缓存
            cacheService.setUserFunctionCache( SessionHelper.getUserId() );
            String userAgent = request.getHeader( "USER-AGENT" ).toLowerCase();  
            if(CheckMobile.check( userAgent ))
            {
                return new ModelAndView("redirect:"+CacheFactory.getInstance().getSetter( "mobile.index.page" ));
            }
            else                                           
            {
                return new ModelAndView( "redirect:/main.htm" );
            }
        }
    }
    
    private String getIpAddr(HttpServletRequest request) { 
        String ip = request.getHeader("x-forwarded-for"); 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getRemoteAddr(); 
        } 
        return ip; 
    }

    @RequestMapping( value = "/main.htm" )
    public ModelAndView main( HttpServletRequest request )
    {
        // 从缓存中获取用户可访问的菜单权限
        List<Menu> menus = CacheFactory.getInstance().getUserMenu(SessionHelper.getUserId());

        String menuString = JsonUtil.list2Json( menus );
        request.setAttribute( "menuString", menuString );
        String mainStyle = CacheFactory.getInstance().getSetter( "main.style" );
        if(null == mainStyle || "".equals( mainStyle ))
        {
            mainStyle = "main";
        }
        else
        {
            request.setAttribute( "menus", menus );
        }
        return new ModelAndView( mainStyle );
    }

}
