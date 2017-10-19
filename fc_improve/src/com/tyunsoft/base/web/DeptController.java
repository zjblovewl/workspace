
package com.tyunsoft.base.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tyunsoft.base.common.CacheFactory;
import com.tyunsoft.base.common.FunctionBuilder;
import com.tyunsoft.base.common.SessionHelper;
import com.tyunsoft.base.entity.Dept;
import com.tyunsoft.base.service.IDeptService;
import com.tyunsoft.base.utils.IDUtil;
import com.tyunsoft.base.utils.JsonUtil;

/**
 * 部门管理
 * 
 * @author flymz
 */
@Controller
@RequestMapping( "/dept" )
public class DeptController
{

    // 跳转到增加部门
    private static final String OPERATOR_ADD = "add";

    @Autowired
    private IDeptService deptService;

    /**
     * 跳转到部门管理页面
     * 
     * @param request
     *            请求
     * @param response
     *            响应
     * @return 返回到部门管理页面
     */
    @RequestMapping( value = "/search.htm" )
    public ModelAndView search( HttpServletRequest request,
            HttpServletResponse response )
    {
        String functionStr = FunctionBuilder
                .build( "icon-add,icon-cancel,icon-help" );
        request.setAttribute( "functionStr", functionStr );
        return new ModelAndView( "sys/dept_list" );
    }

    /**
     * 查询部门信息树
     */
    @RequestMapping( value = "/list.htm" )
    public void list( HttpServletResponse response )
    {
        List<Dept> list = deptService.list();
        JsonUtil.list2Json( response, list );
    }

    /**
     * 部门管理操作说明页面
     * 
     * @return
     */
    @RequestMapping( value = "/instruction.htm" )
    public ModelAndView instruction()
    {
        return new ModelAndView( "sys/dept_instruction" );
    }

    /**
     * 跳转到保存部门信息页面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping( value = "/tosave.htm" )
    public ModelAndView tosave( HttpServletRequest request,
            HttpServletResponse response )
    {
        String operator = request.getParameter( "operator" );
        String deptId = request.getParameter( "deptId" );
        String deptName = request.getParameter( "deptName" );
        Dept dept = new Dept();
        if ( OPERATOR_ADD.equals( operator ) )
        {

            dept.setPDeptId( deptId );
            dept.setPDeptName( deptName );
            Dept queryDept = deptService.query( deptId );
            int deptLevel = 0;
            if ( null != queryDept )
            {
                deptLevel = queryDept.getDeptLevel();
            }
            dept.setDeptLevel( deptLevel );
        } else
        {
            dept = deptService.query( deptId );
        }
        request.setAttribute( "dept", dept );
        request.setAttribute( "operator", operator );
        request.setAttribute( "functionStr", FunctionBuilder
                .build( "icon-save" ) );
        return new ModelAndView( "sys/dept_save" );
    }

    /**
     * 保存部门信息
     * 
     * @param dept
     *            待保存的信息
     */
    @RequestMapping( value = "/save.htm" )
    public void save( @ModelAttribute Dept dept, HttpServletRequest request,
            HttpServletResponse response )
    {
        boolean result = false;
        String deptId = IDUtil.getUUIDStr();
        if ( null == dept.getDeptId() || "".equals( dept.getDeptId() ) )
        {// 新增部门
            dept.setDeptId( deptId );
            dept.setCreateDate( new Date() );
            dept.setCreator( SessionHelper.getUserId() );
            dept.setDeptLevel( dept.getDeptLevel() + 1 );
            result = deptService.add( dept );
        } else
        {
            deptId = dept.getDeptId();
            result = deptService.update( dept );
        }
        // 重置部门缓存信息
        CacheFactory.getInstance().setDeptList( deptService.list() );
        JsonUtil.strOut( response, String.valueOf( result ) + "_" + deptId );
    }

    /**
     * 删除部门信息
     * 
     * @param deptId
     *            部门编码
     * @param response
     *            响应
     */
    @RequestMapping( value = "/delete.htm" )
    public void delete( String deptId, HttpServletResponse response )
    {

        boolean result = deptService.delete( deptId );
        // 重置部门缓存信息
        CacheFactory.getInstance().setDeptList( deptService.list() );
        JsonUtil.boolOut( response, result );
    }

}
