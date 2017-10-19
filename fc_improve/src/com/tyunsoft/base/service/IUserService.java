
package com.tyunsoft.base.service;

import java.util.List;

import com.tyunsoft.base.entity.User;
import com.tyunsoft.base.entity.UserRole;
import com.tyunsoft.base.utils.Pager;

/**
 * 用户管理业务层接口
 * 
 * @author flymz
 */
public interface IUserService
{

    /**
     * 用户登录
     * 
     * @param userId
     *            用户名
     * @param password
     *            登录密码
     * @return 登录的用户，如果错误，则返回null
     */
    User login( String userId, String password );

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
    Pager list( User user, int pageNumber, int pageSize );

    /**
     * 根据用户编码查询用户
     * 
     * @param userId
     *            用户编码
     * @return 用户信息
     */
    User query( String userId );

    /**
     * 删除用户
     * 
     * @param userIds
     *            用户编码，多个之间使用,号隔开
     * @return 删除是否成功
     */
    boolean delete( String userIds );

    /**
     * 初始化用户密码
     * 
     * @param userIds
     *            用户编码，多个之间使用,号隔开
     * @return 删除是否成功
     */
    boolean resetPswd( String userIds );

    /**
     * 新增操作
     * 
     * @param user
     *            用户信息
     * @return 新增是否成功
     */
    boolean insert( User user );

    /**
     * 修改操作
     * 
     * @param user
     *            用户信息
     * @return 修改是否成功
     */
    boolean update( User user );

    /**
     * 查询用户角色
     * 
     * @param userId
     *            用户编码
     * @return 用户角色
     */
    List<UserRole> queryUserRole( String userId );

    /**
     * 需要保存的角色信息
     * 
     * @param userId
     *            用户编码
     * @param role
     *            角色信息List
     * @return 保存是否成功
     */
    boolean saveUserRole( String userId, List<UserRole> role );
    
    /**
     * 查询指定职务下的用户信息
     * @param dutyId 职务编码
     * @return 用户信息List
     */
    List<User> queryDutyUsers(String dutyId);
    
    /**
     * 修改用户密码
     * @param userId 用户编码
     * @param password 密码
     * @return 修改是否成功
     */
    boolean changePassword(String userId,String password);
}
