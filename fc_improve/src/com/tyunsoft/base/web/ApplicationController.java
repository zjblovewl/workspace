
package com.tyunsoft.base.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tyunsoft.base.common.FunctionBuilder;
import com.tyunsoft.base.common.SessionHelper;
import com.tyunsoft.base.entity.Application;
import com.tyunsoft.base.entity.PageEntity;
import com.tyunsoft.base.service.IApplicationService;
import com.tyunsoft.base.utils.IDUtil;
import com.tyunsoft.base.utils.JsonUtil;
import com.tyunsoft.base.utils.PageUtil;
import com.tyunsoft.base.utils.Pager;

/**
 * 桌面应用管理控制类
 * 
 * @author flymz
 * @version [版本号, 2013-3-24]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping( "/app" )
public class ApplicationController
{

    @Autowired
    private IApplicationService applicationService;

    /**
     * 跳转到桌面应用列表页
     * 
     * @return 跳转页面
     */
    @RequestMapping( value = "/search.htm" )
    public ModelAndView search(HttpServletRequest request)
    {
        request.setAttribute( "functionStr", FunctionBuilder.build() );
        return new ModelAndView( "sys/app_list" );
    }

    /**
     * 应用分页数据查询
     * 
     * @param request
     *            请求
     * @param response
     *            返回
     */
    @RequestMapping( value = "/list.htm" )
    public void list( HttpServletRequest request, HttpServletResponse response )
    {
        PageEntity pageEntity = PageUtil.getPageNumAndSize( request );
        Pager list = applicationService.list( pageEntity );
        JsonUtil.bean2JsonForDate( response, list );
    }
    
    /**
     * 查询已授权/待授权应用信息 flag为0表示待授权应用，1表示已授权应用
     * @param request 请求
     * @param response 响应
     */
    @RequestMapping( value = "/listForRole.htm" )
    public void listForRole( HttpServletRequest request, HttpServletResponse response )
    {
        String flag = request.getParameter( "flag" );
        String roleId = request.getParameter( "roleId" );
        PageEntity pageEntity = PageUtil.getPageNumAndSize( request );
        Pager list = applicationService.listForRole( pageEntity, flag, roleId );
        JsonUtil.bean2Json( response, list );
    }

    /**
     * 跳转到保存应用信息页面
     * 
     * @param appId
     *            应用编码
     * @param request
     *            请求
     * @return 跳转到保存应用信息页面
     */
    @RequestMapping(value="/toSave.htm")
    public ModelAndView toSave( String appId, HttpServletRequest request )
    {
        Application app = new Application();
        if ( null != appId )
        {// 修改
            app = applicationService.query( appId );
        }
        request.setAttribute( "app", app );
        return new ModelAndView( "sys/app_save" );
    }

    /**
     * 保存应用信息
     * 
     * @param application
     *            应用信息
     * @param response
     *            响应
     */
    @RequestMapping( value = "/save.htm" )
    public void save( @ModelAttribute Application application,
            HttpServletResponse response )
    {
        if ( applicationService.existAppName( application.getAppId(),
                application.getAppName() ) )
        {
            JsonUtil.strOut( response, "exist" );
        } else
        {
            boolean result = false;
            if ( "".equals( application.getAppId() ) )
            {// 新增
                application.setAppId( IDUtil.getUUIDStr() );
                application.setAppCreator( SessionHelper.getUserId() );
                application.setAppCreateDate( new Date() );
                result = applicationService.insert( application );
            } else
            {
                result = applicationService.update( application );
            }

            JsonUtil.boolOut( response, result );
        }

    }

    /**
     * 删除应用信息
     * 
     * @param appId
     *            应用编码
     * @param response
     *            响应
     */
    @RequestMapping( value = "/delete.htm" )
    public void delete( String appId, HttpServletResponse response )
    {
        boolean result = applicationService.delete( appId );
        JsonUtil.boolOut( response, result );
    }
    
    @RequestMapping(value="/defaultPortal.htm")
    public ModelAndView defaultPortal()
    {
        
        return new ModelAndView("defaultPortal");
    }
    
    /**
     * 跳转到Portal页面
     * @param request 请求
     * @param response 响应
     * @return 跳转到portal页面
     */
    @RequestMapping(value="/portal.htm")
    public ModelAndView portal(HttpServletRequest request,HttpServletResponse response)
    {
        List<Application> list = applicationService.queryUserApps( SessionHelper.getUserId() );
        StringBuffer portals_one = new StringBuffer(1024);
        StringBuffer portals_two = new StringBuffer(1024);
        StringBuffer portals_three = new StringBuffer(1024);
        for (Application application : list) {
			if(application.getAppColumn() == 1)
			{
				portals_one.append(application.getAppId()).append(",");
			}else if(application.getAppColumn() == 2)
			{
				portals_two.append(application.getAppId()).append(",");
			}else if(application.getAppColumn() == 3)
			{
				portals_three.append(application.getAppId()).append(",");
			}
		}
       
        String result = "";
        if(!"".equals(portals_one.toString()))
        {
        	result = portals_one.substring(0, portals_one.length() - 1) + ":";
        }else{
        	result = ":";
        }
        
        if(!"".equals(portals_two.toString())){
        	result += portals_two.substring(0, portals_two.length() - 1) + ":";
        }else{
        	result += ":";
        }
        
        if(!"".equals(portals_three.toString())){
        	result += portals_three.substring(0, portals_three.length() - 1) + ":";
        }else{
        	result += ":";
        }
        if(result.endsWith(":"))
        {
        	result = result.substring(0, result.length() - 1);
        }
       
        request.setAttribute("result", result);	
        request.setAttribute( "list", list );
        return new ModelAndView("portal");
    }
    
    
    /**
     * 保存App被更新后的位置
     * @param request 请求
     * @param response 响应
     */
    @RequestMapping(value="/saveAppLocation.htm")
    public void saveAppLocation(HttpServletRequest request,HttpServletResponse response)
    {
    	String ids = request.getParameter("location");
    	String[] idArray = ids.split(":");
    	List<Map<String,String>> params = new ArrayList<Map<String,String>>();
    	Map<String, String> map = null;
    	for (int i = 0; i < idArray.length; i++) {
    		map = new HashMap<String,String>();
			String[] rowIds = idArray[i].split(",");
			for (int j = 0;j<rowIds.length;j++) 
			{
				map.put("1", String.valueOf(i+1));
				map.put("2", String.valueOf(j+1));
				map.put("3", rowIds[j]);
				map.put("4",SessionHelper.getUserId());
				params.add(map);
			}
		}
    	applicationService.bacthUpdateLocation(params);
    }
    
    
    /**
     * 弹出用户应用设置界面
     * @return 跳转到用户应用设置页面
     */
    @RequestMapping(value="/toUserApp.htm")
    public ModelAndView toUserApp(){
        return new ModelAndView("sys/user_app_setter");
    }
    
    /**
     * 查询用户的应用信息 flag=0表示可分配到桌面的应用  flag=1表示已分配到桌面的应用
     * @param flag  flag=0表示可分配到桌面的应用  flag=1表示已分配到桌面的应用
     * @param response 响应
     */
    @RequestMapping(value="listUserApp.htm")
    public void listUserApp(String flag,HttpServletResponse response){
        List<Application> list = null;
        if("0".equals( flag ))
        {
            list = applicationService.queryUserRoleApps( SessionHelper.getUserId() );
        }else
        {
            list = applicationService.queryUserApps( SessionHelper.getUserId() );
        }
        JsonUtil.list2Json( response, list );
    }
    
    /**
     * 增加或者取消用户应用
     * @param appId 应用编码
     * @param flag 0表示新增，1表示取消
     * @param response 响应
     */
    @RequestMapping(value="/cancelOrAddUserApp.htm")
    public void cancelOrAddUserApp(String appId,String flag,HttpServletResponse response)
    {
        boolean result = applicationService.cancelOrAddUserApp( appId, SessionHelper.getUserId(), flag );
        JsonUtil.boolOut( response, result );
    }

}
