
package com.tyunsoft.base.common;

import java.util.List;

import com.tyunsoft.base.entity.Function;

/**
 * 菜单功能创建
 * 
 * @author Flymz
 */
public class FunctionBuilder
{

    /**
     * 创建操作按钮权限菜单
     * 
     * @param request
     *            请求
     * @return 操作按钮权限菜单字符串
     */
    public static String build()
    {
        String menuId = SessionHelper.getRequest().getParameter( "m" );
        String userId = SessionHelper.getUserId();
        return build( userId, menuId );
    }

    /**
     * 根据指定的操作功能编码构建菜单
     * 
     * @param funs
     *            操作功能编码，使用,号隔开
     * @return 操作按钮字符串
     */
    public static String build( String funs )
    {
        String menuId = SessionHelper.getRequest().getParameter( "m" );
        String userId = SessionHelper.getUserId();
        return build( funs, userId, menuId );
    }

    private static String build( String funsStr, String userId, String menuId )
    {
        funsStr += ",";
        String key = userId + "_" + menuId;
        List<Function> funs = CacheFactory.getInstance().getUserFunMap().get(
                key );
        StringBuffer result = new StringBuffer(
                "<div style=\"margin-bottom:5px\">\n" );
        if ( null != funs )
        {
            for ( Function function : funs )
            {
                if ( funsStr.indexOf( function.getFunId() + "," ) != -1 )
                {
                    result
                            .append( "<a href=\"javascript:;\" id=\"" )
                            .append( function.getFunId() )
                            .append(
                                    "\" class=\"easyui-linkbutton\" data-options=\"iconCls:'" )
                            .append( function.getFunId() ).append(
                                    "',plain:true\">" ).append(
                                    function.getFunName() ).append( "</a>\n" );
                }
            }
        } else
        {
            result
                    .append( "<div style=\"padding:5px;\"><font color=\"#cccccc\">无任何操作权限</font></div>\n" );
        }
        result.append( "</div>" );
        return result.toString();
    }

    private static String build( String userId, String menuId )
    {
        String key = userId + "_" + menuId;
        List<Function> funs = CacheFactory.getInstance().getUserFunMap().get(
                key );
        StringBuffer result = new StringBuffer(
                "<div style=\"margin-bottom:5px\">\n" );
        if ( null != funs )
        {
            for ( Function function : funs )
            {
                result
                        .append( "<a href=\"javascript:;\" id=\"" )
                        .append( function.getFunId() )
                        .append(
                                "\" class=\"easyui-linkbutton\" data-options=\"iconCls:'" )
                        .append( function.getFunId() ).append(
                                "',plain:true\">" ).append(
                                function.getFunName() ).append( "</a>\n" );
            }
        } else
        {
            result
                    .append( "<div style=\"padding:5px;\"><font color=\"#cccccc\">无任何操作权限</font></div>\n" );
        }
        result.append( "</div>" );
        return result.toString();
    }

}
