package com.tyunsoft.base.service;

import java.util.List;
import java.util.Map;

import com.tyunsoft.base.entity.Application;
import com.tyunsoft.base.entity.PageEntity;
import com.tyunsoft.base.utils.Pager;

/**
 * 桌面应用管理业务层接口
 * @author  flymz
 * @version  [版本号, 2013-3-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface IApplicationService
{
    /**
     * 查询桌面应用列表
     * @param page page对象，包含pageNumber 和 pageSize
     * @return 桌面应用信息列表
     */
    Pager list(PageEntity page);
    
    /**
     * 为角色分配查询应用信息
     * @param page  分页信息
     * @param flag 0表示查询待授权应用，1表示查询已授权应用
     * @param roleId 角色编码
     * @return 待授权或者已授权应用信息
     */
    Pager listForRole(PageEntity page,String flag,String roleId);
    
    /**
     * 新增桌面应用信息
     * @param app 待新增的桌面应用
     * @return 新增是否成功
     */
    boolean insert(Application app);
    
    /**
     * 根据应用编码查询应用信息
     * @param appId 应用编码
     * @return 应用信息
     */
    Application query(String appId);
    
    /**
     * 查询桌面应用名称是否重名 
     * @param appId 应用编码
     * @param appName 应用名称
     * @return 是否存在
     */
    boolean existAppName(String appId,String appName);
    
    /**
     * 更新应用信息
     * @param app 待更新的应用信息
     * @return 更新是否成功
     */
    boolean update(Application app);
    
    /**
     * 删除应用
     * @param appId 应用编码
     * @return 删除是否成功
     */
    boolean delete(String appId);
    
    /**
     * 查询用户的应用信息
     * @param userId 用户编码
     * @return 列表
     */
    List<Application> queryUserApps(String userId);
    
    /**
     * 批量更新我的桌面应用信息
     * @param params 参数
     */
    void bacthUpdateLocation(List<Map<String,String>> params);
    
    /**
     * 查询用户所在角色的所有应用信息
     * @param userId 用户编码
     * @return 应用
     */
    List<Application> queryUserRoleApps(String userId);
    
    /**
     * 取消或者增加用户应用
     * @param appId 应用编码
     * @param userId 用户编码
     * @param flag 0表示新增，1表示取消
     * @return 操作是否成功
     */
    boolean cancelOrAddUserApp(String appId,String userId,String flag);
    
}
