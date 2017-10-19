
package com.tyunsoft.base.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.tyunsoft.base.common.CacheFactory;
import com.tyunsoft.base.service.ICacheService;
import com.tyunsoft.base.service.IDeptService;

/**
 * 初始化系统的ApplicationContext
 * 
 * @author Flymz
 */
@Component
public class SystemApplicationContext implements ApplicationContextAware
{

    private static ApplicationContext context;

    public void setApplicationContext( ApplicationContext context )
            throws BeansException
    {
        SystemApplicationContext.context = context;

        // 部门数据缓存
        IDeptService deptService = (IDeptService)context
                .getBean( "deptService" );
        Logger.getLogger().consoleDebug( "部门数据缓存........." );
        CacheFactory.getInstance().setDeptList( deptService.list() );

        // 菜单数据缓存
        ICacheService cacheService = (ICacheService)context
                .getBean( "cacheService" );
        Logger.getLogger().consoleDebug( "菜单数据缓存........." );
        cacheService.setMenuCache();
        // 操作功能数据缓存
        Logger.getLogger().consoleDebug( "操作功能数据缓存........." );
        cacheService.setFunctionCache();
        //字典值数据缓存
        Logger.getLogger().consoleDebug( "字典数据缓存.........." );
        cacheService.setDictionaryCache();
        //设置信息缓存
        Logger.getLogger().consoleDebug( "系统设置数据缓存..........." );
        cacheService.setSetterCache();
    }

    public static ApplicationContext getContext()
    {
        return context;
    }
}
