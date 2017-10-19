
package com.tyunsoft.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.entity.User;
import com.tyunsoft.base.entity.UserRole;
import com.tyunsoft.base.service.IUserService;
import com.tyunsoft.base.utils.MD5;
import com.tyunsoft.base.utils.Pager;
import com.tyunsoft.base.utils.Read;
import com.tyunsoft.base.utils.SqlFactory;
import com.tyunsoft.base.utils.SqlXml;

/**
 * 用户管理业务层接口实现
 * 
 * @author flymz
 */
@Service
public class UserServiceImpl implements IUserService
{

    @Autowired
    private IDao dao;

    /**
     * 用户登录
     * 
     * @param userId
     *            用户名
     * @param password
     *            登录密码
     * @return 登录的用户，如果错误，则返回null
     */
    public User login( String userId, String password )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_user_login" );
        SqlRowSet rs = dao.find( sql, new Object[] {userId, password} );
        User user = null;
        while ( rs.next() )
        {
            user = new User();
            user.setUserId( rs.getString( "user_id" ) );
            user.setUserName( rs.getString( "user_name" ) );
            user.setSex( rs.getString( "sex" ) );
            user.setDeptId(rs.getString("dept_id"));
            user.setDeptName(rs.getString("dept_name"));
            user.setDeptLevel(rs.getInt("dept_level"));
        }
        return user;
    }

    /**
     * 获取分页数据
     * 
     * @param user
     *            查询条件
     * @param pageNumber
     *            请求页数
     * @param pageSize
     *            请求的每页记录数
     * @return 分页数据对象
     */
    public Pager list( User user, int pageNumber, int pageSize )
    {
        Pager pager = new Pager();
        Object[] params = new Object[] {user.getDeptId(),
                user.getDeptId() ,SqlXml.getLinkParam( user.getUserId() ),SqlXml.getLinkParam( user.getUserName() ),SqlXml.getLinkParam( user.getTown() ),SqlXml.getLinkParam( user.getVillage() )};
        String countSql = SqlFactory.getInstance().getSql(
                "sql_search_users_count" );
        int total = dao.findForInt( countSql, params );
        pager.setTotal( total );
        String pageSql = SqlFactory.getInstance().getPageSql(
                "sql_search_users", pageNumber, pageSize );
        SqlRowSet rs = dao.find( pageSql,params);
        List<User> list = new ArrayList<User>();
        User u = null;
        while ( rs.next() )
        {
            u = new User();
            u.setUserId( rs.getString( "user_id" ) );
            String[] roles = queryUserRoles( rs.getString( "user_id" ) );
            u.setRoleIds( roles[0] );
            u.setRoleNames( roles[1] );
            u.setUserName( rs.getString( "user_name" ) );
            u.setSex( rs.getString( "sex" ) );
            u.setIsOnJob( rs.getString( "is_on_job" ) );
            u.setDeptName( rs.getString( "dept_name" ) );
            u.setDutyName( rs.getString( "duty_name" ) );
            u.setUserPhone( rs.getString( "user_phone" ) );
            u.setTown( rs.getString( "town" ) );
            u.setVillage( rs.getString( "village" ) );
            list.add( u );
        }
        pager.setRows( list );
        return pager;
    }

    /**
     * 根据用户编码查询用户
     * 
     * @param userId
     *            用户编码
     * @return 用户信息
     */
    public User query( String userId )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_query_user" );
        User user = null;
        SqlRowSet rs = dao.find( sql, new Object[] {userId} );
        while ( rs.next() )
        {
            user = new User();
            user.setUserId( rs.getString( "user_id" ) );
            String[] roles = queryUserRoles( rs.getString( "user_id" ) );
            user.setRoleIds( roles[0] );
            user.setRoleNames( roles[1] );
            user.setUserName( rs.getString( "user_name" ) );
            user.setSex( rs.getString( "sex" ) );
            user.setDeptId( rs.getString( "dept_id" ) );
            user.setDutyId( rs.getString( "duty_id" ) );
            user.setIsOnJob( rs.getString( "is_on_job" ) );
            user.setUserPhone( rs.getString( "user_phone" ) );
            user.setTown( rs.getString( "town" ) );
            user.setVillage( rs.getString( "village" ) );
        }
        return user;
    }

    /**
     * 根据用户名查询用户的角色信息
     * 
     * @param userId
     *            用户名
     * @return 角色ID和角色名称数组
     */
    private String[] queryUserRoles( String userId )
    {
        // 首先查询当前用户所拥有的角色信息
        String sql = SqlFactory.getInstance().getSql( "sql_query_user_roles" );
        SqlRowSet rs = dao.find( sql, new Object[] {userId} );
        String roleIds = "";
        String roleNames = "";
        while ( rs.next() )
        {
            roleIds += rs.getString( "role_Id" ) + ",";
            roleNames += rs.getString( "role_name" ) + ",";
        }
        if ( roleIds.endsWith( "," ) )
        {
            roleIds = roleIds.substring( 0, roleIds.length() - 1 );
            roleNames = roleNames.substring( 0, roleNames.length() - 1 );
        }
        return new String[] {roleIds, roleNames};
    }

    /**
     * 删除用户
     * 
     * @param userIds
     *            用户编码，多个之间使用,号隔开
     * @return 删除是否成功
     */
    public boolean delete( String userIds )
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put( ":userids", SqlXml.getInParams( userIds ) );
        String sql = SqlFactory.getInstance().getSql( "sql_delete_users", map );
        int result = dao.update( sql );
        return result > 0;
    }

    /**
     * 初始化用户密码
     * 
     * @param userIds
     *            用户编码，多个之间使用,号隔开
     * @return 删除是否成功
     */
    public boolean resetPswd( String userIds )
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put( ":userids", SqlXml.getInParams( userIds ) );
        String sql = SqlFactory.getInstance().getSql( "sql_reset_pswd_users",
                map );
        int result = dao.update( sql, new Object[] {MD5.password( Read
                .getMsg( "system.default.user.password" ) )} );
        return result > 0;
    }

    /**
     * 新增操作
     * 
     * @param user
     *            用户信息
     * @return 新增是否成功
     */
    public boolean insert( User user )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_insert_user" );
        int result = dao.add( sql, new Object[] {user.getUserId(),
                user.getUserName(), user.getSex(), user.getDeptId(),
                user.getDutyId(), user.getUserPswd(), user.getCreator(),
                user.getCreateDate(),user.getUserPhone(),user.getTown(),user.getVillage()} );
        // 更新用户的角色信息
        updateUserRole( user );
        return result == 1;
    }

    /**
     * 更新用户角色信息
     * 
     * @param user
     *            用户
     */
    private void updateUserRole( User user )
    {
        String userId = user.getUserId();
        String[] roleIds = user.getRoleIds().split( "," );
        List<UserRole> uroles = new ArrayList<UserRole>();
        UserRole urole = null;
        if ( roleIds != null )
        {
            for ( int i = 0; i < roleIds.length; i++ )
            {
                urole = new UserRole();
                urole.setUserId( userId );
                urole.setRoleId( roleIds[i] );
                uroles.add( urole );
            }
        }
        saveUserRole( userId, uroles );
    }

    /**
     * 修改操作
     * 
     * @param user
     *            用户信息
     * @return 修改是否成功
     */
    public boolean update( User user )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_update_user" );
        int result = dao.update( sql, new Object[] {user.getUserName(),
                user.getSex(), user.getDeptId(), user.getDutyId(),
                user.getIsOnJob(),user.getUserPhone(),user.getTown(),user.getVillage(), user.getUserId()} );
        // 更新用户的角色信息
        updateUserRole( user );
        return result == 1;
    }

    /**
     * 查询用户角色
     * 
     * @param userId
     *            用户编码
     * @return 用户角色
     */
    public List<UserRole> queryUserRole( String userId )
    {
        // 首先查询当前用户所拥有的角色信息
        String sql = SqlFactory.getInstance().getSql( "sql_query_user_roles" );
        SqlRowSet rs = dao.find( sql, new Object[] {userId} );
        Map<String, String> roleMap = new HashMap<String, String>();
        while ( rs.next() )
        {
            roleMap.put( rs.getString( "role_id" ), rs.getString( "user_id" ) );
        }
        // 首先查询所有的角色信息列表
        String roleSql = SqlFactory.getInstance().getSql( "sql_search_roles" );
        rs = dao.find( roleSql );
        List<UserRole> urList = new ArrayList<UserRole>();
        UserRole ur = null;
        while ( rs.next() )
        {
            ur = new UserRole();
            ur.setRoleId( rs.getString( "role_id" ) );
            ur.setRoleName( rs.getString( "role_name" ) );
            ur.setUserId( roleMap.get( rs.getString( "role_id" ) ) );
            urList.add( ur );
        }
        return urList;
    }

    /**
     * 需要保存的角色信息
     * 
     * @param userId
     *            用户编码
     * @param role
     *            角色信息List
     * @return 保存是否成功
     */
    public boolean saveUserRole( String userId, List<UserRole> role )
    {
        // 首先进行用户下的角色删除
        String delSql = SqlFactory.getInstance().getSql(
                "sql_delete_user_roles" );
        dao.update( delSql, new Object[] {userId} );
        // 将用户的角色信息插入到用户角色表中
        String insertSql = SqlFactory.getInstance().getSql(
                "sql_insert_user_roles" );
        List<Map<String, String>> roles = new ArrayList<Map<String, String>>();
        Map<String, String> roleMap = null;

        for ( UserRole userRole : role )
        {
            roleMap = new HashMap<String, String>();
            roleMap.put( "1", userRole.getUserId() );
            roleMap.put( "2", userRole.getRoleId() );
            roles.add( roleMap );
        }
        int[] result = dao.batchUpdate( insertSql, roles );
        return result.length >= 0;
    }
    
    /**
     * 查询指定职务下的用户信息
     * @param dutyId 职务编码
     * @return 用户信息List
     */
    public List<User> queryDutyUsers(String dutyId)
    {
    	String sql = SqlFactory.getInstance().getSql("sql_query_duty_users");
    	SqlRowSet rs = dao.find(sql, new Object[]{dutyId});
    	List<User> result = new ArrayList<User>();
    	User user = null;
    	while(rs.next())
    	{
    		user = new User();
    		user.setUserId(rs.getString("user_id"));
    		user.setUserName(rs.getString("user_name"));
    		result.add(user);
    	}
    	return result;
    }
    
    /**
     * 修改用户密码
     * @param userId 用户编码
     * @param password 密码
     * @return 修改是否成功
     */
    public boolean changePassword(String userId,String password)
    {
    	String sql = SqlFactory.getInstance().getSql("sql_update_password");
    	int result = dao.update(sql, new Object[]{password,userId});
    	return result == 1;
    }

}
