
package com.tyunsoft.base.common;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tyunsoft.base.entity.Function;
import com.tyunsoft.base.entity.Menu;
import com.tyunsoft.base.utils.PageUtil;

/**
 * 系统Session与操作权限控制
 * 
 * @author flymz
 */
@Repository
public class SystemInterceptor extends HandlerInterceptorAdapter
{

    // 不需要进行权限控制的访问模块
    private static final String NO_SESSION_URL = "/free/";

    public boolean preHandle( HttpServletRequest request,
            HttpServletResponse response, Object handler ) throws Exception
    {
        request.setCharacterEncoding( "UTF-8" );
        response.setCharacterEncoding( "UTF-8" );
        response.setContentType( "text/html;charset=UTF-8" );
        String uri = request.getRequestURI();
        // 是否需要进行过滤，对不需要过滤的模块进行排除
        boolean beFilter = true;
        // 不需要进行session过滤
        if ( uri.indexOf( NO_SESSION_URL ) != -1 )
        {
            beFilter = false;
        }

        // 需要进行过滤
        if ( beFilter )
        {
            // 进行用户是否登录过滤
            Object userId = request.getSession().getAttribute( "userId" );
            if ( userId == null )
            {// 用户登录已经失效
                // ajax请求
                PrintWriter out = response.getWriter();
                StringBuilder builder = new StringBuilder();
                if ( isAjax( request ) )
                {
                    builder.append( "sessionout" );
                } else
                {
                    builder
                            .append( "<script type=\"text/javascript\" charset=\"UTF-8\">" );
                    builder.append( "alert(\"页面过期，请重新登录!\");" );
                    builder.append( "window.top.location.href=\"" );
                    builder.append( PageUtil.getBasePath( request ) );
                    builder.append( "\";</script>" );
                }
                out.print( builder.toString() );
                out.close();
                return false;
            } else
            {
                // 1.判断链接中是否存在系统菜单参数 m ，如果存在，设置到Attribute中
                String m = request.getParameter( "m" );
                if ( null != m )
                {
                    request.setAttribute( "m", m );
                }

                if ( uri.indexOf( "/main.htm" ) == -1 )
                {
                    StringBuffer result = new StringBuffer( "" );

                    List<Menu> menuList = CacheFactory.getInstance()
                            .getMenuList();
                    List<Menu> userMenuList = CacheFactory.getInstance()
                            .getUserMenu( SessionHelper.getUserId() );
                    Map<String, List<Function>> funMap = CacheFactory
                            .getInstance().getFunMap();
                    Map<String, List<Function>> userFunMap = CacheFactory
                            .getInstance().getUserFunMap();

                    boolean isMenu = false;
                    boolean isFun = false;
                    boolean hasLimit = false;
                    // 判断当前链接是否是菜单
                    for ( Menu menu : menuList )
                    {
                    	String menuLink = menu.getMenuLink();
                    	menuLink = menuLink == null ? "" : menuLink;
                        if ( uri.indexOf(menuLink) != -1 )
                        {// 是需要进行权限过滤的菜单，判断当前用户是否有菜单权限
                            isMenu = true;
                            if(null != userMenuList)
                            {
	                            for ( Menu um : userMenuList )
	                            {
	                                if ( null == um.getMenuLink()
	                                        || uri.indexOf( um.getMenuLink() ) != -1 )
	                                {
	                                    hasLimit = true;
	                                    break;
	                                }
	                            }
                            }
                            if ( hasLimit )
                            {
                                break;
                            }
                        }
                    }
                    if ( isMenu )
                    {// 菜单
                        if ( !hasLimit )
                        {
                            // 用户没有打开的权限
                            result.append( "非法请求，您没有访问权限!" );
                        }

                    } else
                    {
                        Iterator<String> it = funMap.keySet().iterator();
                        while ( it.hasNext() )
                        {
                            String menuId = it.next();
                            List<Function> funs = funMap.get( menuId );
                            for ( Function fun : funs )
                            {
                                if ( uri.indexOf( fun.getFunLink() ) != -1 )
                                {
                                    isFun = true;
                                    break;
                                }
                            }
                            if ( isFun )
                            {
                                break;
                            }
                        }

                        if ( isFun )
                        {// 是系统功能
                            if ( null == m )
                            {
                                result.append( "非法请求，您没有权限访问!" );
                            } else
                            {
                                String key = SessionHelper.getUserId() + "_"
                                        + m;
                                List<Function> funs = userFunMap.get( key );
                                if ( null == funs )
                                {
                                    result.append( "非法请求，您没有权限访问!" );
                                } else
                                {
                                    for ( Function fun : funs )
                                    {
                                        if ( uri.indexOf( fun.getFunLink() ) != -1 )
                                        {
                                            hasLimit = true;
                                            break;
                                        }
                                    }
                                    if ( !hasLimit )
                                    {
                                        result.append( "非法请求，您没有权限访问!" );
                                    }
                                }
                            }
                        }

                    }

                    if ( !"".equals( result.toString() ) )
                    {
                        PrintWriter out = response.getWriter();
                        if ( isAjax( request ) )
                        {
                            out.print( "nolimit" );
                        } else
                        {
                            out.print( result.toString() );
                        }
                        out.close();
                        return false;
                    }
                }
            }
        }

        return super.preHandle( request, response, handler );
    }

    private boolean isAjax( HttpServletRequest request )
    {
        String head = request.getHeader( "x-requested-with" );
        if ( "XMLHttpRequest".equals( head ) )
        {
            return true;
        }
        return false;
    }

}
