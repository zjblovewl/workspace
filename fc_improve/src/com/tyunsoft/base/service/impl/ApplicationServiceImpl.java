
package com.tyunsoft.base.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.entity.Application;
import com.tyunsoft.base.entity.PageEntity;
import com.tyunsoft.base.service.IApplicationService;
import com.tyunsoft.base.utils.Pager;
import com.tyunsoft.base.utils.SqlFactory;

/**
 * 应用信息管理实现
 * 
 * @author flymz
 * @version [版本号, 2013-3-24]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
public class ApplicationServiceImpl implements IApplicationService
{

    @Autowired
    private IDao dao;

    /**
     * 删除应用
     * 
     * @param appId
     *            应用编码
     * @return 删除是否成功
     */
    public boolean delete( String appId )
    {
        // 首先删除用户所选择的应用关联关系
        String userAppDelSql = SqlFactory.getInstance().getSql(
                "sql_delete_user_app" );
        dao.delete( userAppDelSql, appId );
        // 再删除应用角色和应用的关联关系
        String roleAppDelSql = SqlFactory.getInstance().getSql(
                "sql_delete_role_app" );
        dao.delete( roleAppDelSql, appId );
        // 最后删除应用的基本信息
        String sql = SqlFactory.getInstance().getSql( "sql_delete_app" );
        int result = dao.delete( sql, appId );
        return result == 1;
    }

    /**
     * 查询桌面应用名称是否重名
     * 
     * @param appId
     *            应用编码
     * @param appName
     *            应用名称
     * @return 是否存在
     */
    public boolean existAppName( String appId, String appName )
    {
        appId = "".equals( appId ) ? "-1" : appId;
        String sql = SqlFactory.getInstance().getSql(
                "sql_query_app_name_exist" );
        int result = dao.findForInt( sql, new Object[] {appId, appId, appName} );
        return result > 0;
    }

    /**
     * 新增桌面应用信息
     * 
     * @param app
     *            待新增的桌面应用
     * @return 新增是否成功
     */
    public boolean insert( Application app )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_insert_app" );
        int result = dao.update( sql, new Object[] {app.getAppId(),
                app.getAppName(), app.getAppLink(), app.getAppHeight(),
                app.getAppDesc(), app.getAppIsSmall(), app.getAppIcon(),
                app.getAppCreator(), app.getAppCreateDate()} );
        return result == 1;
    }

    /**
     * 查询桌面应用列表
     * 
     * @param page
     *            page对象，包含pageNumber 和 pageSize
     * @return 桌面应用信息列表
     */
    public Pager list( PageEntity page )
    {
        Pager pager = new Pager();
        String countSql = SqlFactory.getInstance().getSql(
                "sql_search_app_count" );
        int total = dao.findForInt( countSql );
        pager.setTotal( total );
        String sql = SqlFactory.getInstance().getPageSql( "sql_search_app", page.getPageNumber(), page.getPageSize() );
        SqlRowSet rs = dao.find( sql );
        List<Application> apps = new ArrayList<Application>();
        Application app = null;
        while(rs.next())
        {
            app = new Application();
            app.setAppId( rs.getString( "app_id" ) );
            app.setAppName( rs.getString( "app_name" ) );
            app.setAppLink( rs.getString( "app_link" ) );
            app.setAppHeight( rs.getInt( "app_height" ) );
            app.setAppDesc( rs.getString( "app_desc" ) );
            app.setAppIsSmall( rs.getString( "app_is_small" ) );
            app.setAppIcon( rs.getString( "app_icon" ) );
            app.setAppCreateDate( rs.getDate( "app_create_date" ) );
            apps.add( app );
        }
        pager.setRows( apps );
        return pager;
    }

    /**
     * 为角色分配查询应用信息
     * @param page  分页信息
     * @param flag 0表示查询待授权应用，1表示查询已授权应用
     * @param roleId 角色编码
     * @return 待授权或者已授权应用信息
     */
    public Pager listForRole( PageEntity page, String flag, String roleId )
    {
        if("0".equals( flag ))
        {
            return listForAppToSetter( page, roleId );
        }else{
            return listForAppFromSetter( page, roleId );
        }
    }
    
    /**
     * 查询待授权应用
     * @param page 分页信息
     * @param roleId 当前分配的角色编码
     * @return 待授权应用分页数据
     */
    private Pager listForAppToSetter( PageEntity page, String roleId )
    {
        Pager pager = new Pager();
        String sql = SqlFactory.getInstance().getSql( "sql_search_app_to_setter_count");
        int total = dao.findForInt( sql, new Object[]{roleId} );
        pager.setTotal( total );
        String querySql = SqlFactory.getInstance().getPageSql( "sql_search_app_to_setter_list", page.getPageNumber(), page.getPageSize() );
        SqlRowSet rs = dao.find( querySql, new Object[]{roleId} );
        List<Application> list = new ArrayList<Application>();
        Application app = null;
        while(rs.next())
        {
            app = new Application();
            app.setAppId( rs.getString( "app_id" ) );
            app.setAppName( rs.getString( "app_name" ) );
            app.setAppDesc( rs.getString( "app_desc" ));
            list.add( app );
        }
        pager.setRows( list );
        return pager; 
    }
    
    /**
     * 查询已授权应用
     * @param page 分页信息
     * @param roleId 当前分配的角色编码
     * @return 已授权应用分页数据
     */
    private Pager listForAppFromSetter( PageEntity page, String roleId ){
        Pager pager = new Pager();
        String sql = SqlFactory.getInstance().getSql( "sql_search_app_from_setter_count");
        int total = dao.findForInt( sql, new Object[]{roleId} );
        pager.setTotal( total );
        String querySql = SqlFactory.getInstance().getPageSql( "sql_search_app_from_setter_list", page.getPageNumber(), page.getPageSize() );
        SqlRowSet rs = dao.find( querySql, new Object[]{roleId} );
        List<Application> list = new ArrayList<Application>();
        Application app = null;
        while(rs.next())
        {
            app = new Application();
            app.setAppId( rs.getString( "app_id" ) );
            app.setAppName( rs.getString( "app_name" ) );
            app.setAppDesc( rs.getString( "app_desc" ) );
            list.add( app );
        }
        pager.setRows( list );
        return pager;
    }
    
    /**
     * 更新应用信息
     * 
     * @param app
     *            待更新的应用信息
     * @return 更新是否成功
     */
    public boolean update( Application app )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_update_app" );
        int result = dao.update( sql, new Object[] {app.getAppName(),
                app.getAppLink(), app.getAppHeight(), app.getAppDesc(),
                app.getAppIsSmall(), app.getAppIcon(), app.getAppId()} );
        return result == 1;
    }

    /**
     * 根据应用编码查询应用信息
     * 
     * @param appId
     *            应用编码
     * @return 应用信息
     */
    public Application query( String appId )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_query_app" );
        SqlRowSet rs = dao.find( sql, new Object[]{appId} );
        Application app = new Application();
        while(rs.next())
        {
            app.setAppId( rs.getString( "app_id" ) );
            app.setAppName( rs.getString( "app_name" ) );
            app.setAppLink( rs.getString( "app_link" ) );
            app.setAppHeight( rs.getInt( "app_height" ) );
            app.setAppDesc( rs.getString( "app_desc" ) );
            app.setAppIsSmall( rs.getString( "app_is_small" ) );
            app.setAppIcon( rs.getString( "app_icon" ) );
        }
        return app;
    }
    
    /**
     * 批量更新我的桌面应用信息
     * @param params 参数
     */
    public void bacthUpdateLocation(List<Map<String,String>> params){
    	String sql = SqlFactory.getInstance().getSql( "sql_update_app_location" );
    	dao.batchUpdate(sql, params);
    }

    /**
     * 查询用户的应用信息
     * @param userId 用户编码
     * @return 列表
     */
    public List<Application> queryUserApps(String userId)
    {
        String sql = SqlFactory.getInstance().getSql( "query_user_apps" );
        List<Application> list = new ArrayList<Application>();
        Application app = null;
        SqlRowSet rs = dao.find( sql, new Object[]{userId} );
        while(rs.next())
        {
            app = new Application();
            app.setAppId( rs.getString( "app_id" ) );
            app.setAppName( rs.getString( "app_name" ) );
            app.setAppLink( rs.getString( "app_link" ) );
            app.setAppHeight( rs.getInt( "app_height" ) );
            app.setAppIsSmall( rs.getString( "app_is_small" ) );
            app.setAppRow( rs.getInt( "app_row" ) );
            app.setAppColumn( rs.getInt( "app_column" ) );
            list.add( app );
        }
        return list;
    }
    
    /**
     * 查询用户所在角色的所有应用信息
     * @param userId 用户编码
     * @return 应用
     */
    public List<Application> queryUserRoleApps(String userId)
    {
        String sql = SqlFactory.getInstance().getSql( "query_user_role_apps" );
        List<Application> list = new ArrayList<Application>();
        Application app = null;
        SqlRowSet rs = dao.find( sql, new Object[]{userId ,userId} );
        while(rs.next())
        {
            app = new Application();
            app.setAppId( rs.getString( "app_id" ) );
            app.setAppName( rs.getString( "app_name" ) );
            app.setAppDesc( rs.getString( "app_desc" ) );
            list.add( app );
        }
        return list;
    }
    
    /**
     * 取消或者增加用户应用
     * @param appId 应用编码
     * @param userId 用户编码
     * @param flag 0表示新增，1表示取消
     * @return 操作是否成功
     */
    public boolean cancelOrAddUserApp(String appId,String userId,String flag)
    {
        String sql = null;
        if("0".equals( flag ))
        {
            sql = SqlFactory.getInstance().getSql( "add_user_app" );   
        }else{
            sql = SqlFactory.getInstance().getSql( "delete_user_app" );
        }
        int result = dao.update( sql, new Object[]{userId,appId} );
        return result == 1;
    }
 

}
