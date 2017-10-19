
package com.tyunsoft.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.common.CacheFactory;
import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.entity.Dictionary;
import com.tyunsoft.base.entity.Function;
import com.tyunsoft.base.entity.Menu;
import com.tyunsoft.base.entity.Setter;
import com.tyunsoft.base.service.ICacheService;
import com.tyunsoft.base.service.IDictionaryService;
import com.tyunsoft.base.service.IMenuService;
import com.tyunsoft.base.service.ISetterService;
import com.tyunsoft.base.utils.SqlFactory;

/**
 * 缓存服务接口实现
 * 
 * @author flymz
 */
@Service( "cacheService" )
public class CacheServiceImpl implements ICacheService
{

    @Autowired
    private IDao dao;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IDictionaryService dictionaryService;
    
    @Autowired
    private ISetterService setterService;

    /**
     * 设置菜单操作功能缓存数据
     */
    public void setFunctionCache()
    {
        String sql = SqlFactory.getInstance().getSql( "sql_search_functions" );
        SqlRowSet rs = dao.find( sql );
        Map<String, List<Function>> funs = new HashMap<String, List<Function>>();
        Function fun = null;

        while ( rs.next() )
        {
            String menuId = rs.getString( "menu_id" );
            fun = new Function();
            fun.setMenuId( menuId );
            fun.setFunId( rs.getString( "fun_id" ) );
            fun.setFunName( rs.getString( "fun_name" ) );
            fun.setFunOrder( rs.getInt( "fun_order" ) );
            if ( null == funs.get( menuId ) )
            {
                List<Function> list = new ArrayList<Function>();
                list.add( fun );
                funs.put( menuId, list );
            } else
            {
                funs.get( menuId ).add( fun );
            }
        }
        CacheFactory.getInstance().setFunMap( funs );
    }

    /**
     * 设置菜单缓存数据
     */
    public void setMenuCache()
    {
        List<Menu> list = menuService.list();
        CacheFactory.getInstance().setMenuList( list );

        // 缓存菜单数据的时候重新缓存操作功能数据
        setFunctionCache();
    }

    /**
     * 设置用户操作功能菜单缓存
     * 
     * @param userId
     *            用户名称
     */
    public void setUserFunctionCache( String userId )
    {
        Map<String, List<Function>> userFunMap = menuService
                .queryUserMenuFunctions( userId );
        CacheFactory.getInstance().setUserFunMap( userFunMap );
    }

    /**
     * 设置用户菜单缓存
     * 
     * @param userId
     *            用户名称
     */
    public void setUserMenuCache( String userId )
    {
        List<Menu> list = menuService.searchUserMenus( userId );
        CacheFactory.getInstance().putUserMenu( userId, list );
    }

    /**
     * 字典数据缓存功能
     */
    public void setDictionaryCache()
    {
        List<Dictionary> list = dictionaryService.search();
        for ( Dictionary dictionary : list )
        {
            CacheFactory.getInstance().putDictionary( dictionary.getDicId(),
                    dictionaryService.query( dictionary.getDicId() ) );
        }

    }
    
    /**
     * 设置信息缓存功能
     */
    public void setSetterCache()
    {
        List<Object> setters = setterService.listAll();
        Setter set = null;
        Map<String,String> setterMap = new HashMap<String,String>();
        for ( Object object : setters )
        {
            set = (Setter)object;
            setterMap.put( set.getSetterName(), set.getSetterValue() );
        }
        CacheFactory.getInstance().setSetterMap( setterMap );
    }

}
