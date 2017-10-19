
package com.tyunsoft.base.web;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tyunsoft.base.common.FunctionBuilder;
import com.tyunsoft.base.entity.Function;
import com.tyunsoft.base.entity.Menu;
import com.tyunsoft.base.service.ICacheService;
import com.tyunsoft.base.service.IMenuService;
import com.tyunsoft.base.utils.Constants;
import com.tyunsoft.base.utils.IDUtil;
import com.tyunsoft.base.utils.JsonUtil;

/**
 * 菜单管理
 * 
 * @author flymz
 */
@Controller
@RequestMapping( "/menu" )
public class MenuController
{

    @Autowired
    private IMenuService menuService;

    @Autowired
    private ICacheService cacheService;

    /**
     * 跳转到menu列表页面
     * 
     * @param request
     *            请求
     * @param response
     *            返回
     * @return
     */
    @RequestMapping( value = "/search.htm" )
    public ModelAndView search( String menuId, HttpServletRequest request,
            HttpServletResponse response )
    {
        // 查询当前菜单所拥有的角色
        request.setAttribute( "functionStr", FunctionBuilder.build() );
        return new ModelAndView( "sys/menu_list" );
    }

    /**
     * 获取所有的菜单信息
     * 
     * @param response
     *            返回响应
     */
    @RequestMapping( value = "/list.htm" )
    public void list( HttpServletResponse response )
    {
        List<Menu> list = menuService.list();
        JsonUtil.list2Json( response, list );
    }

    /**
     * 根据角色编码获取包括操作功能的菜单列表
     * 
     * @param response
     */
    @RequestMapping( value = "/listWithFunctions.htm" )
    public void listWithFunctions( HttpServletRequest request,
            HttpServletResponse response )
    {
        String roleId = request.getParameter( "roleId" );
        List<Menu> list = menuService.listWithFunctions( roleId );
        JsonUtil.list2Json( response, list );
    }

    /**
     * 跳转到菜单编辑页面
     * 
     * @param request
     *            请求
     * @param response
     *            响应
     * @return 跳转到菜单编辑页面
     */
    @RequestMapping( value = "/toSave.htm" )
    public ModelAndView toSave( String menuId, HttpServletRequest request,
            HttpServletResponse response )
    {
        String operator = request.getParameter( "operator" );
        Menu menu = null;
        if ( Constants.OPERATOR_ADD.equals( operator ) )
        {// 新增
            menu = new Menu();
        } else
        {
            menu = menuService.queryMenu( menuId );
        }
        List<String> icons = queryMenuIcons( request );
        request.setAttribute( "icons", icons );
        request.setAttribute( "menu", menu );
        request.setAttribute( "operator", operator );
        return new ModelAndView( "sys/menu_edit" );
    }

    /**
     * 保存菜单信息
     * 
     * @param menu
     *            菜单信息
     * @param operator
     *            操作方式，add or edit
     * @param response
     *            响应
     */
    @RequestMapping( value = "/save.htm" )
    public void save( @ModelAttribute Menu menu, String operator,
            HttpServletResponse response )
    {
        boolean result = false;
        if ( Constants.OPERATOR_ADD.equals( operator ) )
        {// 新增菜单操作
            menu.setMenuId( IDUtil.getUUIDStr() );
            result = menuService.add( menu );
        } else
        {
            result = menuService.update( menu );
        }

        // 菜单缓存重新设置
        if ( result )
        {
            cacheService.setMenuCache();
        }

        JsonUtil.boolOut( response, result );
    }

    /**
     * 检测菜单名称是否已经存在
     * 
     * @param menuName
     *            菜单名称
     * @param pMenuId
     *            上级编码
     * @param menuId
     *            当前菜单编码（修改时进行判断）
     * @param response响应
     */
    @RequestMapping( value = "/checkMenuNameExist.htm" )
    public void checkMenuNameExist( String menuName, String pMenuId,
            String menuId, HttpServletResponse response )
    {
        boolean result = menuService.checkMenuNameExist( menuName, pMenuId, ""
                .equals( menuId ) ? "0" : menuId );
        JsonUtil.boolOut( response, result );
    }

    /**
     * 删除菜单
     * 
     * @param menuId
     *            菜单编码
     * @param response
     *            响应
     */
    @RequestMapping( value = "/delete.htm" )
    public void delete( String menuId, HttpServletResponse response )
    {
        boolean result = menuService.delete( menuId );
        if ( result )
        {
            cacheService.setMenuCache();
        }
        JsonUtil.boolOut( response, result );
    }

    /**
     * 查询顶级菜单
     * 
     * @param response
     *            响应
     */
    @RequestMapping( value = "/queryTopMenus.htm" )
    public void queryTopMenus( HttpServletResponse response )
    {
        List<Menu> menuList = menuService.queryTopMenus();
        JsonUtil.list2Json( response, menuList );
    }

    /**
     * 根据上级菜单查询子菜单信息
     * 
     * @param pMenuId
     *            上级菜单编码
     * @param response
     */
    @RequestMapping( value = "/queryChildrenMenus.htm" )
    public void queryChildrenMenus( String pMenuId, HttpServletResponse response )
    {
        List<Menu> menuList = menuService.queryChildrenMenus( pMenuId );
        JsonUtil.list2Json( response, menuList );
    }

    /**
     * 查询Icon目录下的图标
     * 
     * @param request
     *            请求
     * @return 图标列表
     */
    private List<String> queryMenuIcons( HttpServletRequest request )
    {
        String path = request.getSession().getServletContext().getRealPath(
                "/images/menuicons" );
        File f = new File( path );
        List<String> result = new ArrayList<String>();
        if ( f.exists() && f.isDirectory() )
        {
            String[] files = f.list();
            if ( files != null )
            {
                for ( String string : files )
                {
                    result.add( string );
                }
            }
        }
        return result;
    }

    /**
     * 跳转到操作功能编辑页面
     * 
     * @param menuId
     *            菜单编码
     * @return
     */
    @RequestMapping( value = "/toFunction.htm" )
    public ModelAndView toFunction( String menuId, String menuName,
            HttpServletRequest request )
    {
        request.setAttribute( "menuId", menuId );
        request.setAttribute( "menuName", menuName );
        return new ModelAndView( "sys/function_edit" );
    }

    /**
     * 获取菜单下的所有功能
     * 
     * @param menuId
     *            菜单编码
     */
    @RequestMapping( value = "/menuFunctions.htm" )
    public void menuFunctions( String menuId, HttpServletResponse response )
    {
        List<Function> funs = menuService.queryMenuFunctions( menuId );
        JsonUtil.list2Json( response, funs );
    }

    /**
     * 保存菜单的操作功能信息
     * 
     * @param funs
     * @param response
     */
    @SuppressWarnings( "unchecked" )
    @RequestMapping( value = "/saveMenuFunIcons.htm" )
    public void saveMenuFunIcons( HttpServletRequest request,
            HttpServletResponse response )
    {
        String rowdata = request.getParameter( "rowdata" );
        String menuId = request.getParameter( "menuId" );
        List<Function> funs = (List<Function>)JSONArray.toCollection( JSONArray
                .fromObject( rowdata ), Function.class );
        menuService.saveMenuFunctions( menuId, funs );
        
        //缓存操作功能数据
        cacheService.setFunctionCache();
        JsonUtil.boolOut( response, true );
    }

    /**
     * 查询所有的功能图标
     * 
     * @param response
     */
    @RequestMapping( value = "/queryFunIcons.htm" )
    public void queryFunIcons( HttpServletResponse response )
    {
        List<Map<String, String>> icons = new ArrayList<Map<String, String>>();
        Map<String, String> map = null;
        String[] iconArr = Constants.OPERATOR_ICONS.split( "[|]" );
        for ( String icon : iconArr )
        {
            map = new HashMap<String, String>();
            map.put( "text", icon );
            map.put( "id", icon );
            map.put( "iconCls", icon );
            icons.add( map );
        }
        JsonUtil.list2Json( response, icons );
    }

    /**
     * 跳转到菜单排序
     * 
     * @return 返回
     */
    @RequestMapping( value = "/toOrder.htm" )
    public ModelAndView toOrder()
    {
        return new ModelAndView( "sys/menu_order" );
    }

    /**
     * 菜单排序
     * 
     * @return 返回
     */
    @SuppressWarnings( "unchecked" )
    @RequestMapping( value = "/order.htm" )
    public void order( HttpServletRequest request, HttpServletResponse response )
    {
        String orderData = request.getParameter( "orderData" );
        List<Menu> menus = (List<Menu>)JSONArray.toCollection( JSONArray
                .fromObject( orderData ), Menu.class );
        menuService.order( menus );
        // 重设菜单缓存数据
        cacheService.setMenuCache();
    }

}
