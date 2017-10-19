
package com.tyunsoft.base.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ycii.fc.util.ExportExcel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tyunsoft.base.common.FunctionBuilder;
import com.tyunsoft.base.common.SessionHelper;
import com.tyunsoft.base.entity.PageEntity;
import com.tyunsoft.base.entity.User;
import com.tyunsoft.base.entity.UserRole;
import com.tyunsoft.base.service.IUserService;
import com.tyunsoft.base.utils.Constants;
import com.tyunsoft.base.utils.JsonUtil;
import com.tyunsoft.base.utils.MD5;
import com.tyunsoft.base.utils.PageUtil;
import com.tyunsoft.base.utils.Pager;
import com.tyunsoft.base.utils.Read;

/**
 * 用户管理控制类
 * 
 * @author flymz
 */
@Controller
@RequestMapping( "/user" )
public class UserController
{

    @Autowired
    private IUserService userService;

    /**
     * 跳转到用户列表页面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping( value = "/frame.htm" )
    public ModelAndView frame( HttpServletRequest request,
            HttpServletResponse response )
    {
        return new ModelAndView( "sys/user_frame" );
    }

    /**
     * 转到用户列表页面
     * 
     * @param request
     *            请求
     * @return
     */
    @RequestMapping( value = "/search.htm" )
    public ModelAndView search( HttpServletRequest request )
    {
        String deptId = request.getParameter( "deptId" );
        if ( null == deptId )
        {
            deptId = "0";
        }
        request.setAttribute( "deptId", deptId );
        request.setAttribute( "functionStr", FunctionBuilder.build() );
        return new ModelAndView( "sys/user_list" );
    }

    /**
     * 查询用户分页数据
     * 
     * @param user
     *            查询条件
     * @param request
     *            请求
     * @param response
     *            响应
     */
    @RequestMapping( value = "/list.htm" )
    public void list( @ModelAttribute User user, HttpServletRequest request,
            HttpServletResponse response )
    {
        PageEntity pageEntity = PageUtil.getPageNumAndSize( request );

        Pager pager = userService.list( user, pageEntity.getPageNumber(), pageEntity.getPageSize() );
        JsonUtil.bean2Json( response, pager );
    }

    /**
     * 跳转到新增/修改用户页面
     * 
     * @param userId
     *            用户编码
     * @param deptId
     *            部门编码
     * @param request
     *            请求
     * @return 跳转页面
     */
    @RequestMapping( value = "/tosave.htm" )
    public ModelAndView tosave( String userId, String deptId,
            HttpServletRequest request )
    {
        User user = new User();
        String operator = Constants.OPERATOR_ADD;
        if ( null != userId )
        {
            user = userService.query( userId );
            operator = Constants.OPERATOR_EDIT;
        } else
        {
            user.setDeptId( deptId );
        }
        request.setAttribute( "deptId", deptId == null ? "0" : deptId );
        request.setAttribute( "operator", operator );
        request.setAttribute( "user", user );
        return new ModelAndView( "sys/user_save" );
    }

    /**
     * 保存用户信息
     * 
     * @param user
     *            用户
     * @param request
     *            请求
     * @param response
     *            响应
     */
    @RequestMapping( value = "/save.htm" )
    public void save( @ModelAttribute User user, HttpServletRequest request,
            HttpServletResponse response )
    {
        String operator = request.getParameter( "operator" );
        boolean result = false;
        if ( Constants.OPERATOR_ADD.equals( operator ) )
        {// 新增操作
            user.setCreator( SessionHelper.getUserId() );
            user.setCreateDate( new Date() );
            user.setUserPswd( MD5.password( Read
                    .getMsg( "system.default.user.password" ) ) );
            result = userService.insert( user );
        } else
        {
            result = userService.update( user );
        }
        JsonUtil.strOut( response, String.valueOf( result ) );
    }

    /**
     * 删除所选择的用户
     * 
     * @param userIds
     *            用户编码，多个用户使用,号隔开
     * @param response
     *            响应
     */
    @RequestMapping( value = "/delete.htm" )
    public void delete( String userIds, HttpServletResponse response )
    {
        boolean result = userService.delete( userIds );
        JsonUtil.strOut( response, String.valueOf( result ) );
    }

    /**
     * 跳转到用户角色弹出页面
     * 
     * @return
     */
    @RequestMapping( value = "/toUserRole.htm" )
    public ModelAndView toUserRole( String userId, HttpServletRequest request )
    {
        request.setAttribute( "userId", userId );
        return new ModelAndView( "sys/user_role_list" );
    }

    /**
     * 重置用户密码
     * 
     * @param userIds
     *            用户编码
     * @param response
     *            响应
     */
    @RequestMapping( value = "/resetPswd.htm" )
    public void resetPswd( String userIds, HttpServletResponse response )
    {
        boolean result = userService.resetPswd( userIds );
        JsonUtil.boolOut( response, result );
    }

    /**
     * 查询用户角色信息对象
     * 
     * @param userId
     *            用户名
     * @param response
     *            响应
     */
    @RequestMapping( value = "/listUserRole.htm" )
    public void listUserRole( HttpServletRequest request,
            HttpServletResponse response )
    {
        String userId = request.getParameter( "userId" );
        List<UserRole> list = userService.queryUserRole( userId );
        JsonUtil.list2Json( response, list );
    }

    /**
     * 保存用户角色信息
     */
    @RequestMapping( value = "/saveUserRole.htm" )
    public void saveUserRole( HttpServletRequest request,
            HttpServletResponse response )
    {
        String userId = request.getParameter( "userId" );
        String roleIds = request.getParameter( "roleIds" );
        String[] roleArray = roleIds.split( "," );
        List<UserRole> roles = new ArrayList<UserRole>();
        UserRole ur = null;
        if ( null != roleArray )
        {
            for ( String roleId : roleArray )
            {
                if ( !"".equals( roleId ) )
                {
                    ur = new UserRole();
                    ur.setRoleId( roleId );
                    ur.setUserId( userId );
                    roles.add( ur );
                }
            }
        }
        boolean result = userService.saveUserRole( userId, roles );
        JsonUtil.strOut( response, String.valueOf( result ) );
    }
    
    /**
     * 跳转到修改密码界面
     * @param request 请求
     * @param response 响应
     * @return 跳转到修改密码页面
     */
    @RequestMapping(value="/forwardChangePswd.htm")
    public ModelAndView forwardChangePswd( HttpServletRequest request,
            HttpServletResponse response )
    {
    	return new ModelAndView("sys/change_password");
    }
    
    /**
     * 修改密码
     * @param request 请求
     * @param response 响应
     * @return 修改密码
     */
    @RequestMapping(value="/changePassword.htm")
    public void changePassword( HttpServletRequest request,
            HttpServletResponse response )
    {
    	String password = request.getParameter("password");
    	String userId = SessionHelper.getUserId();
    	boolean result = userService.changePassword(userId, MD5.password(password));
    	JsonUtil.boolOut(response, result);
    }
    
    @RequestMapping("exportUser.htm")
    public String exportUser(HttpServletRequest request,HttpServletResponse response) throws Exception 
    {
        // request.getParameter("userName")
        User user = new User();
        user.setUserId( "" );
        user.setDeptId( "0" );
        user.setTown( "" );
        user.setVillage( "" );
        List<?> list = userService.list( user, 1, 100000 ).getRows();
        
        Map map = new HashMap();
        map.put("userList",list);
        ExportExcel exportExcel = new ExportExcel();
        exportExcel.export("用户数据", map, "userExport.xls",response);
        return null;            
    }

}
