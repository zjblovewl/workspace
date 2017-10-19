
package com.tyunsoft.base.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.entity.Role;
import com.tyunsoft.base.service.IRoleService;
import com.tyunsoft.base.utils.SqlFactory;

/**
 * 角色管理业务层实现
 * 
 * @author flymz
 */
@Service
public class RoleServiceImpl implements IRoleService
{

    @Autowired
    private IDao dao;

    /**
     * 增加角色信息
     * 
     * @param role
     *            角色信息
     */
    public boolean addRole( Role role )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_add_role" );
        int result = dao.add( sql, new Object[] {role.getRoleId(),
                role.getRoleName(), role.getRoleRemark()} );
        return result == 1;
    }

    /**
     * 修改角色信息
     * 
     * @param role
     *            角色信息
     * @return 是否成功
     */
    public boolean editRole( Role role )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_edit_role" );
        int result = dao.add( sql, new Object[] {role.getRoleName(),
                role.getRoleRemark(), role.getRoleId()} );
        return result == 1;
    }

    /**
     * 根据菜单编码查询菜单信息
     * 
     * @param roleId
     *            菜单编码
     * @return 菜单信息
     */
    public Role queryRole( String roleId )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_query_role" );
        SqlRowSet rs = dao.find( sql, new Object[] {roleId} );
        Role role = new Role();
        while ( rs.next() )
        {
            role.setRoleId( rs.getString( "role_id" ) );
            role.setRoleName( rs.getString( "role_name" ) );
            role.setRoleRemark( rs.getString( "role_remark" ) );
        }
        return role;
    }

    /**
     * 查询角色名是否存在
     * 
     * @param roleId
     *            角色编码
     * @param roleName
     *            角色名称
     * @return 是否存在
     */
    public boolean existRoleName( String roleId, String roleName )
    {
        String sql = SqlFactory.getInstance().getSql(
                "sql_query_role_name_for_exist" );
        int result = dao.findForInt( sql, new Object[] {roleName, roleId,
                roleId} );
        return result > 0;
    }

    /**
     * 查询角色信息
     * 
     * @return 角色信息列表
     */
    public List<Role> search()
    {
        String sql = SqlFactory.getInstance().getSql( "sql_search_roles" );
        List<Role> list = new ArrayList<Role>();
        Role role = null;
        SqlRowSet rs = dao.find( sql );
        while ( rs.next() )
        {
            role = new Role();
            role.setRoleId( rs.getString( "role_id" ) );
            role.setRoleName( rs.getString( "role_name" ) );
            role.setRoleRemark( rs.getString( "role_remark" ) );
            list.add( role );
        }
        return list;
    }

    /**
     * 删除角色信息
     * 
     * @param roleId
     *            角色编码
     * @return 删除是否成功
     */
    public boolean deleteRole( String roleId )
    {
        // 删除角色
        String sql = SqlFactory.getInstance().getSql( "sql_delete_role" );
        int result = dao.update( sql, new Object[] {roleId} );
        // 删除角色所对应的菜单分配信息
        String menuRoleDelSql = SqlFactory.getInstance().getSql(
                "sql_delete_role_menus" );
        dao.delete( menuRoleDelSql, roleId );
        // 删除角色所对应分配的操作功能
        String funDelSql = SqlFactory.getInstance().getSql(
                "sql_delete_role_menu_functions" );
        dao.delete( funDelSql, roleId );
        // 删除用户所分配的该角色
        String userRoleDelSql = SqlFactory.getInstance().getSql(
                "sql_delete_role_users_rel" );
        dao.delete( userRoleDelSql, roleId );
        return result == 1;
    }

    /**
     * 保存角色的权限分配信息
     * 
     * @param funs
     *            权限分配
     * @param roleId
     *            角色编码
     */
    public void saveRoleFunctions( List<Map<String, String>> funs,
            List<Map<String, String>> menus, String roleId )
    {
        // 首先删除该角色的所有权限分配信息
        String delSql = SqlFactory.getInstance().getSql(
                "sql_delete_role_menu_functions" );
        dao.delete( delSql, roleId );
        // 将分配的权限赋予当前的角色
        String sql = SqlFactory.getInstance().getSql(
                "sql_set_role_menu_functions" );
        dao.batchUpdate( sql, funs );

        // 删除该角色的菜单权限
        delSql = SqlFactory.getInstance().getSql( "sql_delete_role_menus" );
        dao.delete( delSql, roleId );

        // 对该角色进行菜单权限赋予
        sql = SqlFactory.getInstance().getSql( "sql_set_role_menus" );
        dao.batchUpdate( sql, menus );
    }

    /**
     * 保存应用角色配置信息
     * @param roleId 角色编码
     * @param appId 应用编码
     * @param flag 标志位
     */
    public boolean saveAppRole( String roleId, String appId, String flag )
    {
        String sql = SqlFactory.getInstance().getSql( "delete_app_role" );
        if("add".equals( flag ))
        {
            sql = SqlFactory.getInstance().getSql( "insert_app_role" );
        }
        int result = dao.update( sql, new Object[]{appId,roleId} );
        return result == 1;
    }

}
