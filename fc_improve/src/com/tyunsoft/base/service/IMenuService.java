
package com.tyunsoft.base.service;

import java.util.List;
import java.util.Map;

import com.tyunsoft.base.entity.Function;
import com.tyunsoft.base.entity.Menu;

/**
 * 菜单管理业务层接口
 * 
 * @author flymz
 */
public interface IMenuService
{

    /**
     * 查询菜单信息
     * 
     * @return
     */
    List<Menu> list();

    /**
     * 获取包括操作功能的菜单列表
     * 
     * @param roleId
     *            角色编码
     * @return 权限菜单列表
     */
    List<Menu> listWithFunctions( String roleId );

    /**
     * 根据用户编码查询用户的菜单
     * 
     * @param userId
     *            用户编码
     * @return 用户所具有的菜单权限
     */
    List<Menu> searchUserMenus( String userId );

    /**
     * 查询用户的菜单权限
     * 
     * @param userId
     *            用户编码
     * @return 菜单权限对象
     */
    Map<String, List<Function>> queryUserMenuFunctions( String userId );

    /**
     * 查询菜单信息
     * 
     * @param menuId
     *            菜单编码
     * @return 菜单信息
     */
    Menu queryMenu( String menuId );

    /**
     * 查询顶级菜单
     * 
     * @return
     */
    List<Menu> queryTopMenus();

    /**
     * 根据上级菜单，查询子菜单
     * 
     * @param pMenuId
     * @return
     */
    List<Menu> queryChildrenMenus( String pMenuId );

    /**
     * 增加菜单
     * 
     * @param menu
     *            菜单信息
     * @return 增加是否成功
     */
    boolean add( Menu menu );

    /**
     * 更新菜单
     * 
     * @param menu
     *            菜单信息
     * @return 更新是否成功
     */
    boolean update( Menu menu );

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
    boolean checkMenuNameExist( String menuName, String pMenuId, String menuId );

    /**
     * 删除菜单
     * 
     * @param menuId
     *            菜单编码
     * @return 删除是否成功
     */
    boolean delete( String menuId );

    /**
     * 查询菜单的所有功能
     * 
     * @param menuId
     *            菜单编码
     * @return 菜单功能列表
     */
    List<Function> queryMenuFunctions( String menuId );

    /**
     * 保存菜单操作功能
     * 
     * @param menuId
     *            菜单编码
     * @param funs
     *            操作功能
     */
    void saveMenuFunctions( String menuId, List<Function> funs );

    /**
     * 进行菜单拍下
     * 
     * @param menu
     *            需要排序的菜单
     */
    void order( List<Menu> menu );

}
