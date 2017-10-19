
package com.tyunsoft.base.service;

/**
 * 缓存服务接口
 * 
 * @author flymz
 */
public interface ICacheService
{

    /**
     * 设置菜单缓存数据
     */
    void setMenuCache();

    /**
     * 设置菜单操作功能缓存数据
     */
    void setFunctionCache();

    /**
     * 设置用户菜单缓存
     * 
     * @param userId
     *            用户名称
     */
    void setUserMenuCache( String userId );

    /**
     * 设置用户操作功能菜单缓存
     * 
     * @param userId
     *            用户名称
     */
    void setUserFunctionCache( String userId );
    
    /**
     * 字典数据缓存功能
     */
    void setDictionaryCache();
    
    /**
     * 设置信息缓存功能
     */
    void setSetterCache();
}
