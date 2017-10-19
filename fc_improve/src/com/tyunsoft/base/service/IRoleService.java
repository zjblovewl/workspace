
package com.tyunsoft.base.service;

import java.util.List;
import java.util.Map;

import com.tyunsoft.base.entity.Role;

/**
 * 角色管理业务层接口
 * 
 * @author flymz
 */
public interface IRoleService
{

    /**
     * 查询角色名是否存在
     * 
     * @param roleId
     *            角色编码
     * @param roleName
     *            角色名称
     * @return 是否存在
     */
    boolean existRoleName( String roleId, String roleName );

    /**
     * 根据菜单编码查询菜单信息
     * 
     * @param roleId
     *            菜单编码
     * @return 菜单信息
     */
    Role queryRole( String roleId );

    /**
     * 增加角色信息
     * 
     * @param role
     *            角色信息
     * @return 是否成功
     */
    boolean addRole( Role role );

    /**
     * 修改角色信息
     * 
     * @param role
     *            角色信息
     * @return 是否成功
     */
    boolean editRole( Role role );

    /**
     * 删除角色信息
     * 
     * @param roleId
     *            角色编码
     * @return 删除是否成功
     */
    boolean deleteRole( String roleId );

    /**
     * 查询角色信息
     * 
     * @return 角色信息列表
     */
    List<Role> search();

    /**
     * 保存角色的权限分配信息
     * 
     * @param funs
     *            权限分配
     * @param menus
     *            菜单权限
     * @param roleId
     *            角色编码
     */
    void saveRoleFunctions( List<Map<String, String>> funs,
            List<Map<String, String>> menus, String roleId );
    
    /**
     * 保存应用角色配置信息
     * @param roleId 角色编码
     * @param appId 应用编码
     * @param flag 标志位
     */
    boolean saveAppRole(String roleId,String appId,String flag);
}
