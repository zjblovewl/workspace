
package com.tyunsoft.base.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tyunsoft.base.common.FunctionBuilder;
import com.tyunsoft.base.entity.Role;
import com.tyunsoft.base.service.IRoleService;
import com.tyunsoft.base.utils.IDUtil;
import com.tyunsoft.base.utils.JsonUtil;

/**
 * 角色信息管理
 * 
 * @author flymz
 */
@Controller
@RequestMapping( "/role" )
public class RoleController
{

    @Autowired
    private IRoleService roleService;

    /**
     * 查询角色信息列表
     * 
     * @param request
     *            请求
     * @param response
     *            返回
     * @return 返回角色信息列表页面
     */
    @RequestMapping( value = "/search.htm" )
    public ModelAndView search( HttpServletRequest request,
            HttpServletResponse response )
    {
        // 查询当前菜单所拥有的角色
        request.setAttribute( "functionStr", FunctionBuilder.build() );
        return new ModelAndView( "sys/role_list" );
    }

    @RequestMapping( value = "/list.htm" )
    public void list( HttpServletResponse response )
    {
        List<Role> list = roleService.search();
        JsonUtil.list2Json( response, list );
    }

    /**
     * 跳转到增加角色信息页面
     * 
     * @return
     */
    @RequestMapping( value = "/tosave.htm" )
    public ModelAndView tosave( String roleId, HttpServletRequest request )
    {
        Role role = new Role();
        if ( null != roleId )
        {// 修改
            role = roleService.queryRole( roleId );
        }
        request.setAttribute( "role", role );
        return new ModelAndView( "sys/role_save" );
    }

    /**
     * 增加角色信息
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping( value = "/save.htm" )
    public void save( HttpServletRequest request, HttpServletResponse response )
    {
        String roleId = request.getParameter( "roleId" );

        String roleName = request.getParameter( "roleName" );
        if ( roleService.existRoleName( roleId, roleName ) )
        {
            JsonUtil.strOut( response, "exist" );
        } else
        {
            String roleRemark = request.getParameter( "roleRemark" );
            Role role = new Role();
            role.setRoleName( roleName );
            role.setRoleRemark( roleRemark );
            boolean result = false;
            if ( "".equals( roleId ) )
            {
                role.setRoleId( IDUtil.getUUIDStr() );
                result = roleService.addRole( role );
            } else
            {
                role.setRoleId( roleId );
                result = roleService.editRole( role );
            }
            JsonUtil.strOut( response, result ? "yes" : "no" );
        }

    }

    /**
     * 删除角色信息
     * 
     * @param request
     *            请求
     * @param response
     *            响应
     */
    @RequestMapping( value = "/delete.htm" )
    public void delete( HttpServletRequest request, HttpServletResponse response )
    {
        String roleId = request.getParameter( "roleId" );
        boolean result = roleService.deleteRole( roleId );
        JsonUtil.strOut( response, String.valueOf( result ) );
    }

    /**
     * 跳转到分配权限页面
     * 
     * @param request
     * @return
     */
    @RequestMapping( value = "/toSetterRole.htm" )
    public ModelAndView toSetterRole( HttpServletRequest request )
    {
        String roleId = request.getParameter( "roleId" );
        String roleName = request.getParameter( "roleName" );
        request.setAttribute( "roleId", roleId );
        request.setAttribute( "roleName", roleName );
        return new ModelAndView( "sys/role_fun_setter" );
    }
    
    /**
     * 跳转到分配角色应用权的页面
     * @param request 请求
     * @return 跳转到应用权页面
     */
    @RequestMapping( value = "/toSetterAppRole.htm" )
    public ModelAndView toSetterAppRole( HttpServletRequest request )
    {
        String roleId = request.getParameter( "roleId" );
        String roleName = request.getParameter( "roleName" );
        request.setAttribute( "roleId", roleId );
        request.setAttribute( "roleName", roleName );
        return new ModelAndView( "sys/role_app_setter" );
    }
    
    /**
     * 应用分配或者取消分配
     * @param request 请求
     * @param response 响应
     */
    @RequestMapping(value = "/setterAppRole.htm")
    public void setterAppRole( HttpServletRequest request ,HttpServletResponse response)
    {
        String roleId = request.getParameter( "roleId" );
        String flag = request.getParameter( "flag" );
        String appId = request.getParameter( "appId" );
        boolean result = roleService.saveAppRole( roleId, appId, flag );
        JsonUtil.boolOut( response, result );
    }

    /**
     * 保存权限分配信息
     * 
     * @return
     */
    @RequestMapping( value = "/setterRole.htm" )
    public ModelAndView setterRole( HttpServletRequest request )
    {
        String roleId = request.getParameter( "roleId" );
        // 获取访问权限
        Map<String, String[]> funs = request.getParameterMap();
        Iterator<String> it = funs.keySet().iterator();
        List<Map<String, String>> funList = new ArrayList<Map<String, String>>();
        String menuId = null;
        Map<String, String> fun = null;

        List<Map<String, String>> menuList = new ArrayList<Map<String, String>>();
        Map<String, String> menu = null;
        while ( it.hasNext() )
        {
            String key = it.next();
            if ( key.startsWith( "r_" ) )
            {
                fun = new HashMap<String, String>();
                menuId = key.substring( 2, key.lastIndexOf( "_" ) );
                fun.put( "1", roleId );
                fun.put( "2", menuId );
                fun.put( "3", key.substring( key.lastIndexOf( "_" ) + 1 ) );
                funList.add( fun );
            } else if ( key.startsWith( "m_" ) )
            {
                menu = new HashMap<String, String>();
                menuId = key.substring( 2 );
                menu.put( "1", roleId );
                menu.put( "2", menuId );
                menuList.add( menu );
            }
        }
        roleService.saveRoleFunctions( funList, menuList, roleId );

        return new ModelAndView( "redirect:/role/search.htm?m="
                + request.getParameter( "m" ) );
    }

}
