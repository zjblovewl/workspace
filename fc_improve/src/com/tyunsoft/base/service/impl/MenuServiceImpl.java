
package com.tyunsoft.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.entity.Function;
import com.tyunsoft.base.entity.Menu;
import com.tyunsoft.base.service.IMenuService;
import com.tyunsoft.base.utils.SqlFactory;

/**
 * 菜单管理业务层接口实现
 * 
 * @author flymz
 */
@Service
public class MenuServiceImpl implements IMenuService
{

    // 顶级菜单编码
    private static final String P_MENU_ID = "0";

    @Autowired
    private IDao dao;

    /**
     * 查询菜单信息 -- 一次性查询出以后进行上下级封装
     * 
     * @return 查询结果
     */
    public List<Menu> list()
    {
        String sql = SqlFactory.getInstance().getSql( "sql_search_menus" );
        SqlRowSet rs = dao.find( sql );
        List<Menu> menus = new ArrayList<Menu>();
        Map<String, Menu> topMenu = new HashMap<String, Menu>();
        Menu menu = null;
        while ( rs.next() )
        {
            menu = new Menu();
            String pMenuId = rs.getString( "p_menu_id" );
            menu.setMenuId( rs.getString( "menu_id" ) );
            menu.setMenuName( rs.getString( "menu_name" ) );
            menu.setMenuIcon( rs.getString( "menu_icon" ) );
            menu.setMenuLink( rs.getString( "menu_link" ) );
            menu.setMenuOrder( rs.getInt( "menu_order" ) );
            menu.setPMenuId( pMenuId );
            if ( P_MENU_ID.equals( pMenuId ) )
            {
                topMenu.put( menu.getMenuId(), menu );
                menus.add( menu );
            } else
            {
            	if(topMenu.get(menu.getPMenuId()) != null)
            	{
            		List<Menu> children = topMenu.get( menu.getPMenuId() )
                            .getChildren();
                    children.add( menu );
            	}
            }
        }
        return menus;
    }

    /**
     * 获取包括功能的菜单列表
     * 
     * @param roleId
     *            所要获取功能的角色
     * @return 功能列表
     */
    public List<Menu> listWithFunctions( String roleId )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_search_functions" );
        SqlRowSet rs = dao.find( sql );
        Map<String, List<Function>> funs = new HashMap<String, List<Function>>();
        Function fun = null;
        // 当前角色的菜单功能
        Map<String, String> roleFuns = queryRoleFunctions( roleId );
        while ( rs.next() )
        {
            String menuId = rs.getString( "menu_id" );

            fun = new Function();
            fun.setFunId( rs.getString( "fun_id" ) );
            fun.setFunName( rs.getString( "fun_name" ) );
            fun.setFunOrder( rs.getInt( "fun_order" ) );
            String funsStr = roleFuns.get( menuId );
            if ( null != funsStr
                    && funsStr.indexOf( "," + fun.getFunId() + "," ) != -1 )
            {// 当前角色分配了该功能
                fun.setChecked( true );
            }
            if ( funs.get( menuId ) == null )
            {
                List<Function> list = new ArrayList<Function>();
                list.add( fun );
                funs.put( menuId, list );
            } else
            {
                funs.get( menuId ).add( fun );
            }
        }

        // 菜单列表
        List<Menu> menuList = list();

        // 角色的菜单权限
        Map<String, String> menuMap = queryRoleMenu( roleId );
        // 将操作功能添加到菜单中
        for ( Menu menu : menuList )
        {
            List<Menu> children = menu.getChildren();
            for ( Menu child : children )
            {
                child.setFunctions( funs.get( child.getMenuId() ) );
                child
                        .setHasViewPower( menuMap.get( child.getMenuId() ) != null );
            }
        }

        return menuList;
    }

    /**
     * 查询角色的菜单权限
     * 
     * @param roleId
     *            角色编码
     * @return 菜单权限Map
     */
    private Map<String, String> queryRoleMenu( String roleId )
    {
        String sql = SqlFactory.getInstance().getSql(
                "sql_query_menus_by_roleid" );
        SqlRowSet rs = dao.find( sql, new Object[] {roleId} );
        Map<String, String> result = new HashMap<String, String>();
        while ( rs.next() )
        {
            result.put( rs.getString( "menu_id" ), rs.getString( "role_id" ) );
        }
        return result;
    }

    /**
     * 根据当前用户的权限查询用户所能操作的功能点
     * 
     * @param roleId
     * @return
     */
    private Map<String, String> queryRoleFunctions( String roleId )
    {
        String sql = SqlFactory.getInstance().getSql(
                "sql_query_functions_by_roleid" );
        SqlRowSet rs = dao.find( sql, new Object[] {roleId} );
        Map<String, String> result = new HashMap<String, String>();
        String funsStr = null;
        while ( rs.next() )
        {
            String menuId = rs.getString( "menu_id" );
            String funId = rs.getString( "fun_id" );
            if ( result.get( menuId ) == null )
            {
                funsStr = "," + funId + ",";
                result.put( menuId, funsStr );
            } else
            {
                result.put( menuId, result.get( menuId ) + funId + "," );
            }
        }
        return result;
    }

    /**
     * 根据用户编码查询用户的菜单
     * 
     * @param userId
     *            用户编码
     * @return 用户所具有的菜单权限
     */
    public List<Menu> searchUserMenus( String userId )
    {
        // 查询一级菜单
        String sql = SqlFactory.getInstance().getSql( "sql_search_top_menus" );
        SqlRowSet rs = dao.find( sql );
        Map<String, List<Menu>> map = queryUserMenus( userId );
        List<Menu> result = new ArrayList<Menu>();
        Menu menu = null;
        while ( rs.next() )
        {
            String menuId = rs.getString( "menu_id" );
            if ( map.get( menuId ) != null )
            {// 当前菜单有二级菜单
                menu = new Menu();
                menu.setMenuId( rs.getString( "menu_id" ) );
                menu.setMenuName( rs.getString( "menu_name" ) );
                menu.setPMenuId( rs.getString( "p_menu_id" ) );
                menu.setMenuIcon( rs.getString( "menu_icon" ) );
                menu.setChildren( map.get( menuId ) );
                result.add( menu );
            }
        }
        return result;
    }

    /**
     * 查询用户所具有的菜单
     * 
     * @param userId
     *            用户编码
     * @return 菜单
     */
    private Map<String, List<Menu>> queryUserMenus( String userId )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_search_user_menus" );
        SqlRowSet rs = dao.find( sql, new Object[] {userId} );
        Map<String, List<Menu>> result = new HashMap<String, List<Menu>>();
        List<Menu> menuList = null;
        Menu menu = null;
        while ( rs.next() )
        {
            menu = new Menu();
            String pMenuId = rs.getString( "p_menu_id" );
            menu.setMenuId( rs.getString( "menu_id" ) );
            menu.setMenuName( rs.getString( "menu_name" ) );
            menu.setMenuIcon( rs.getString( "menu_icon" ) );
            menu.setPMenuId( pMenuId );
            menu.setMenuLink( rs.getString( "menu_link" ) );
            if ( result.get( pMenuId ) == null )
            {
                menuList = new ArrayList<Menu>();
                menuList.add( menu );
                result.put( pMenuId, menuList );
            } else
            {
                result.get( pMenuId ).add( menu );
            }
        }
        return result;
    }

    /**
     * 查询用户的菜单权限
     * 
     * @param userId
     *            用户编码
     * @return 菜单权限对象
     */
    public Map<String, List<Function>> queryUserMenuFunctions( String userId )
    {
        String sql = SqlFactory.getInstance().getSql(
                "sql_query_user_menu_functions" );
        SqlRowSet rs = dao.find( sql, new Object[] {userId} );
        Map<String, List<Function>> result = new HashMap<String, List<Function>>();
        Function fun = null;
        while ( rs.next() )
        {
            String menuId = rs.getString( "menu_id" );
            String key = userId + "_" + menuId;
            fun = new Function();
            fun.setFunId( rs.getString( "fun_id" ) );
            fun.setFunName( rs.getString( "fun_name" ) );
            fun.setMenuId( rs.getString( "menu_id" ) );
            if ( result.get( key ) == null )
            {
                List<Function> list = new ArrayList<Function>();
                list.add( fun );
                result.put( key, list );
            } else
            {
                result.get( key ).add( fun );
            }
        }
        return result;
    }

    /**
     * 查询菜单信息
     * 
     * @param menuId
     *            菜单编码
     * @return 菜单信息
     */
    public Menu queryMenu( String menuId )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_query_menu" );
        SqlRowSet rs = dao.find( sql, new Object[] {menuId} );
        Menu menu = null;
        while ( rs.next() )
        {
            menu = new Menu();
            menu.setMenuId( rs.getString( "menu_id" ) );
            menu.setMenuName( rs.getString( "menu_name" ) );
            menu.setMenuIcon( rs.getString( "menu_icon" ) );
            menu.setMenuLink( rs.getString( "menu_link" ) );
            menu.setPMenuId( rs.getString( "p_menu_id" ) );
        }
        return menu;
    }

    /**
     * 查询顶级菜单
     * 
     * @return 顶级菜单
     */
    public List<Menu> queryTopMenus()
    {
        String sql = SqlFactory.getInstance().getSql( "sql_search_top_menus" );
        SqlRowSet rs = dao.find( sql );
        List<Menu> result = new ArrayList<Menu>();
        Menu menu = null;
        while ( rs.next() )
        {
            menu = new Menu();
            menu.setMenuId( rs.getString( "menu_id" ) );
            menu.setMenuName( rs.getString( "menu_name" ) );
            menu.setPMenuId( rs.getString( "p_menu_id" ) );
            menu.setMenuIcon( rs.getString( "menu_icon" ) );
            menu.setMenuOrder( rs.getInt( "menu_order" ) );
            result.add( menu );
        }
        return result;
    }

    /**
     * 根据上级菜单，查询子菜单
     * 
     * @param pMenuId
     * @return
     */
    public List<Menu> queryChildrenMenus( String pMenuId )
    {
        String sql = SqlFactory.getInstance().getSql(
                "sql_search_children_menus" );
        SqlRowSet rs = dao.find( sql, new Object[] {pMenuId} );
        List<Menu> result = new ArrayList<Menu>();
        Menu menu = null;
        while ( rs.next() )
        {
            menu = new Menu();
            menu.setMenuId( rs.getString( "menu_id" ) );
            menu.setMenuName( rs.getString( "menu_name" ) );
            menu.setPMenuId( rs.getString( "p_menu_id" ) );
            menu.setMenuIcon( rs.getString( "menu_icon" ) );
            menu.setMenuOrder( rs.getInt( "menu_order" ) );
            result.add( menu );
        }
        return result;
    }

    /**
     * 增加菜单
     * 
     * @param menu
     *            菜单信息
     * @return 增加是否成功
     */
    public boolean add( Menu menu )
    {
        String maxOrdersql = SqlFactory.getInstance().getSql(
                "sql_query_max_order" );
        int maxOrder = dao.findForInt( maxOrdersql, new Object[] {menu
                .getPMenuId()} );

        String sql = SqlFactory.getInstance().getSql( "sql_add_menu" );
        int result = dao.update( sql, new Object[] {menu.getMenuId(),
                menu.getMenuName(), maxOrder + 1, menu.getMenuIcon(),
                menu.getPMenuId() == null ? "0" : menu.getPMenuId(), menu.getMenuLink()} );
        return result == 1;
    }

    /**
     * 更新菜单
     * 
     * @param menu
     *            菜单信息
     * @return 更新是否成功
     */
    public boolean update( Menu menu )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_update_menu" );
        int result = dao.update( sql, new Object[] {menu.getMenuName(),
                menu.getMenuIcon(), menu.getMenuLink(), menu.getPMenuId(),
                menu.getMenuId()} );
        return result == 1;
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
     */
    public boolean checkMenuNameExist( String menuName, String pMenuId,
            String menuId )
    {
        String sql = SqlFactory.getInstance().getSql(
                "sql_query_menu_name_exist" );
        int count = dao.findForInt( sql, new Object[] {pMenuId, menuName,
                menuId, menuId} );
        return count != 0;
    }

    /**
     * 删除菜单
     * 
     * @param menuId
     *            菜单编码
     * @return 删除是否成功
     */
    public boolean delete( String menuId )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_delete_menu" );
        int result = dao.delete( sql, menuId );
        // 删除菜单下的所有操作功能
        String funDelSql = SqlFactory.getInstance().getSql(
                "sql_delete_menu_functions" );
        dao.delete( funDelSql, menuId );
        // 删除给所有用户分配的该菜单权限
        String menuRoleDelSql = SqlFactory.getInstance().getSql(
                "sql_delete_menu_roles" );
        dao.delete( menuRoleDelSql, menuId );
        // 删除菜单所对应的所有已分配的操作功能权限
        String roleMenuFunDelSql = SqlFactory.getInstance().getSql(
                "sql_delete_role_menu_functions" );
        dao.delete( roleMenuFunDelSql, menuId );
        return result == 1;
    }

    /**
     * 查询菜单的所有功能
     * 
     * @param menuId
     *            菜单编码
     * @return 菜单功能列表
     */
    public List<Function> queryMenuFunctions( String menuId )
    {
        String sql = SqlFactory.getInstance().getSql(
                "sql_search_menu_functions" );
        SqlRowSet rs = dao.find( sql, new Object[] {menuId} );
        List<Function> funs = new ArrayList<Function>();
        Function fun = null;
        while ( rs.next() )
        {
            fun = new Function();
            fun.setFunId( rs.getString( "fun_id" ) );
            fun.setFunName( rs.getString( "fun_name" ) );
            fun.setFunOrder( rs.getInt( "fun_order" ) );
            fun.setFunLink( rs.getString( "fun_link" ) );
            funs.add( fun );
        }
        return funs;
    }

    /**
     * 保存菜单操作功能
     * 
     * @param menuId
     *            菜单编码
     * @param funs
     *            操作功能
     */
    public void saveMenuFunctions( String menuId, List<Function> funs )
    {
        // 删除菜单的功能
        String delSql = SqlFactory.getInstance().getSql(
                "sql_delete_menu_functions" );
        dao.delete( delSql, menuId );
        List<Map<String, String>> params = new ArrayList<Map<String, String>>();
        Map<String, String> map = null;
        if ( null != funs && !funs.isEmpty() )
        {
            for ( int i = 0; i < funs.size(); i++ )
            {
                Function function = funs.get( i );
                map = new HashMap<String, String>();
                map.put( "1", menuId );
                map.put( "2", function.getFunId() );
                map.put( "3", function.getFunName() );
                map.put( "4", String.valueOf( i + 1 ) );
                map.put( "5", function.getFunLink() );
                params.add( map );
            }
            // 增加菜单的功能
            String insertSql = SqlFactory.getInstance().getSql(
                    "sql_insert_menu_functions" );
            dao.batchUpdate( insertSql, params );
        }

    }

    /**
     * 进行菜单拍下
     * 
     * @param menu
     *            需要排序的菜单
     */
    public void order( List<Menu> menu )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_order_menu" );
        List<Map<String, String>> batchList = new ArrayList<Map<String, String>>();
        Map<String, String> map = null;
        for ( Menu m : menu )
        {
            map = new HashMap<String, String>();
            map.put( "1", String.valueOf( m.getMenuOrder() ) );
            map.put( "2", m.getMenuId() );
            batchList.add( map );
        }
        dao.batchUpdate( sql, batchList );
    }

}
