
package com.tyunsoft.base.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tyunsoft.base.common.CacheFactory;
import com.tyunsoft.base.utils.JsonUtil;

/**
 * 缓存对象连接获取类
 * 
 * @author flymz
 */
@Controller
@RequestMapping( "/cache" )
public class CacheController
{

    /**
     * 获取部门树数据
     * 
     * @param response
     *            响应
     */
    @RequestMapping( value = "/deptTree.htm" )
    public void deptTree( HttpServletResponse response )
    {
        JsonUtil.list2Json( response, CacheFactory.getInstance().getDeptList() );
    }
    
    /**
     * 根据字典编码从字典缓存数据中获取字典值列表
     * @param dicId 字典编码
     * @param response 响应
     */
    @RequestMapping(value = "/dictionary.htm")
    public void dictionary(String dicId, HttpServletResponse response )
    {
        JsonUtil.list2Json( response,CacheFactory.getInstance().getDictionaryValues( dicId ) );
    }

}
