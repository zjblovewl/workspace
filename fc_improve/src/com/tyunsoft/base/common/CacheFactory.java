
package com.tyunsoft.base.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tyunsoft.base.entity.Dept;
import com.tyunsoft.base.entity.DictionaryValue;
import com.tyunsoft.base.entity.Function;
import com.tyunsoft.base.entity.Menu;

/**
 * 系统缓存管理工厂
 * 
 * @author flymz
 */
public class CacheFactory
{

    public static CacheFactory factory;

    // 部门list对象
    private List<Dept> deptList;

    // 系统菜单list对象
    private List<Menu> menuList;
    
    private Map<String,String> setterMap = new HashMap<String,String>();

    // 用户菜单list对象
    private Map<String,List<Menu>> userMenuMap = new HashMap<String,List<Menu>>();

    // 系统菜单操作功能对象
    private Map<String, List<Function>> funMap;

    // 用户操作功能对象缓存
    private Map<String, List<Function>> userFunMap;
    
    //字典值信息缓存
    private Map<String,List<DictionaryValue>> dictionaryMap = new HashMap<String, List<DictionaryValue>>();

    /**
     * 获取缓存对象实例
     * 
     * @return
     */
    public static CacheFactory getInstance()
    {
        if ( null == factory )
        {
            factory = new CacheFactory();
        }
        return factory;
    }

    public void setDeptList( List<Dept> deptList )
    {
        this.deptList = deptList;
    }

    public List<Dept> getDeptList()
    {
        return deptList;
    }

    public List<Menu> getMenuList()
    {
        return menuList;
    }

    public void setMenuList( List<Menu> menuList )
    {
        this.menuList = menuList;
    }

    public List<Menu> getUserMenu(String userId)
    {
        return userMenuMap.get( userId );
    }

    public void putUserMenu(String userId,List<Menu> list)
    {
        this.userMenuMap.put( userId, list );
    }

    public Map<String, List<Function>> getFunMap()
    {
        return funMap;
    }

    public void setFunMap( Map<String, List<Function>> funMap )
    {
        this.funMap = funMap;
    }

    public Map<String, List<Function>> getUserFunMap()
    {
        return userFunMap;
    }

    public void setUserFunMap( Map<String, List<Function>> userFunMap )
    {
        this.userFunMap = userFunMap;
    }
    
    public String getSetter(String name)
    {
        return setterMap.get( name );
    }

    public void setSetterMap( Map<String, String> setterMap )
    {
        this.setterMap = setterMap;
    }

    public void removeDictionary(String dicId)
    {
        this.dictionaryMap.remove( dicId );
    }
    
    public void putDictionary(String dicId,List<DictionaryValue> dicValueList)
    {
        this.dictionaryMap.put( dicId, dicValueList );
    }
    
    public List<DictionaryValue> getDictionaryValues(String dicId)
    {
        return this.dictionaryMap.get( dicId );
    }
    
    public String getDictionaryText(String dicId, String dicValue)
    {
        List<DictionaryValue>  dics = getDictionaryValues(dicId);
        String text = "";
        for (DictionaryValue dictionaryValue : dics) 
        {
            if(dictionaryValue.getDicValueId().equals(dicValue))
            {
                text = dictionaryValue.getDicValueLabel();
                break;
            }
        }
        return text;
    }

}
