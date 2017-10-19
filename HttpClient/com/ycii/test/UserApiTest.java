package com.ycii.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import net.sf.json.JSONObject;
import net.ycii.api.UserApi;
import net.ycii.api.WeiXinApi;
import net.ycii.entity.Group;
import net.ycii.entity.WXUserAuthorization;
import net.ycii.entity.WeiXinUser;
import net.ycii.entity.WeiXinUsersResult;
import net.ycii.exception.WeiXinException;

import org.junit.Test;

public class UserApiTest extends BaseBean
{
    

    
    /**
     * <一句话功能简述>创建分组测试
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月5日 下午12:03:33 [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @Test
    public void createGroupTest()
    {
        try
        {
            Group group =  UserApi.createGroup( "微信test2", "PAcPEqKZSsTtyGDdDwZGl4oAW0bZ9xNobZNqPIJK2jyH1fztHi01Pz_3rKNzPccUZVngJLhrHKaCpSKRL_KilCMCcAEqGWZphm5uk7fiVn0" );
            System.out.println(group.getId());
        } catch ( WeiXinException e )
        {
            e.printStackTrace();
        }
    }

    @Test
    public void updateGroupTest()
    {
        try
        {
            Group group = new Group();
            group.setId( "100" );
            group.setName( "pgmh" );
            UserApi.updateGroup( group, token_key );
        } catch ( WeiXinException e )
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void deleteGroupTest()
    {
        try
        {
            UserApi.deleteGroup( 117, "NIBbMo6I1u3948bG5_KXgoAwmrdiuhoNtlbaajyI5dvjeklUYHqlLoCQ6btX_ArY1Uez11Vydrjj7Ez_MtvnWIvy878aa65ix4gT8WvtDO0" );
        } catch ( WeiXinException e )
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void moveGroupTest()
    {
        try
        {
            List<String> openIds = new ArrayList<String>();
            openIds.add( "oKeoWuJei6k1WD28DDdwHgMkwKtg" );
            openIds.add( "oKeoWuAQj44WPsIf-PeWrga9pe-o" );
            UserApi.moveGroup(openIds,"0","vIIIpp5CaYKpdcoq7Y46T9yf3aUDAIP_dNnK_NXol4ZOH4yWt4Yuyrt7EYNBuCQdxJSgjbDIiKSdqmKvszgFAP5K7_b2wqg7bdjfnNd0dxA");
        } catch ( WeiXinException e )
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void getUserGroupIdTest()
    {
        String groupId = UserApi.getUserGroupId("od8XIjsmk6QdVTETa9jLtGWA6KBc", token_key );
        System.out.println(groupId);
    }

    @Test
    public void getAllGroup()
    {
        List<Group> list = UserApi.getAllGroup( token_key );
        Assert.assertNotNull( list );
    }
    
    @Test
    public void getAccessTokenTest()
    {
        WeiXinApi.getAccessToken( APP_ID, appsecret );
    }

    /**
     * <一句话功能简述>获取用户授权码
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月14日 上午10:31:37 [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @Test
    public void getUserAuthorizationCodeTest()
    {
        WXUserAuthorization author =UserApi.getUserAuthorizationCode( APP_ID ,appsecret ,"0111c7b0e4b2da1535e871d081bb24bY");
        JSONObject json = JSONObject.fromObject( author );
        System.out.println(json);
    }
    
    @Test
    public void getWeiXinUserInfoTest()
    {
        WeiXinUser author =UserApi.getWeiXinUserInfo("ol_bNwgzy3Z0UYpjfyN01Lt1QUBQ",token_key);
        JSONObject json = JSONObject.fromObject( author );
        System.out.println(json);
    }
    
    @Test
    public void getWeiXinUsers()
    {
        WeiXinUsersResult result =UserApi.getWeiXinUsers( token_key, null );
        JSONObject json = JSONObject.fromObject( result );
        System.out.println(json);
    }
    
}
