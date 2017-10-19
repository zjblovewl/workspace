package com.ycii.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import net.ycii.api.MenuApi;
import net.ycii.entity.MenuNode;
import net.ycii.entity.MenuType;
import net.ycii.exception.WeiXinException;
import net.ycii.timer.TokenManager;

import org.junit.Test;

/**
 * <一句话功能简述>菜单Api测试类
 * <p><功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年5月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MenuApiTest    extends BaseBean
{
    
    @Test
    public void getAllMenuTest()
    {
        List<MenuNode> menuNode= MenuApi.getAllMenu( token_key );
        Assert.assertNotNull( menuNode );
    }
    
    @Test
    public void deleteMenuTest()
    {
        try
        {
            MenuApi.deleteMenu( token_key );
        } catch ( WeiXinException e )
        {
            e.printStackTrace();
        }
    }
    
    /**
     * <一句话功能简述>{
    "button": [
        {
            "type": "click",
            "name": "今日歌曲",
            "key": "V1001_TODAY_MUSIC"
        },
        {
            "name": "菜单",
            "sub_button": [
                {
                    "type": "view",
                    "name": "搜索",
                    "url": "http://www.soso.com/"
                },
                {
                    "type": "view",
                    "name": "视频",
                    "url": "http://v.qq.com/"
                },
                {
                    "type": "click",
                    "name": "赞一下我们",
                    "key": "V1001_GOOD"
                }
            ]
        }
    ]
}
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月27日 下午12:51:08 [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @Test
    public void createMenuTest()
    {
        deleteMenuTest();
        MenuNode root = new MenuNode();
        root.setName( "关于岳创" );
        root.setType( MenuType.click );
        root.setUrl( "http://www.baidu.com" );
        
        MenuNode aboutMenuNode = new MenuNode();
        aboutMenuNode.setType( MenuType.view );
        aboutMenuNode.setName( "岳创官网" );
        aboutMenuNode.setUrl( "http://www.ycii.net/" );
        
        MenuNode viewRegNode = new MenuNode();
        viewRegNode.setType( MenuType.view );
        viewRegNode.setName( "会员注册" );
        viewRegNode.setUrl( "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6f3014ea424079b5&redirect_uri=http%3a%2f%2ferp.ycii.net%2fcrm%2fregister%2fregWeiXinVipUser.jhtm%3fwxId%3dgh_99b79bfce993&response_type=code&scope=snsapi_base&state=123#wechat_redirect" );
        
        List<MenuNode> aboutlList = new ArrayList<MenuNode>();
        aboutlList.add( aboutMenuNode );
        //aboutlList.add( viewRegNode );
        
        root.setSub_button( aboutlList );
        
        List<MenuNode> menus = new ArrayList<MenuNode>();
        menus.add( root );
        try
        {
            MenuApi.createMenu( menus, token_key );
        } catch ( WeiXinException e )
        {
            e.printStackTrace();
        }
    }
    
}
