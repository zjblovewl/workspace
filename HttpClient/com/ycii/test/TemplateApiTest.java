package com.ycii.test;

import java.util.HashMap;
import java.util.Map;

import net.ycii.api.TemplateApi;
import net.ycii.entity.TemplateMessage;
import net.ycii.exception.WeiXinException;

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
public class TemplateApiTest    extends BaseBean
{
    @Test
    public void setIndustryTest(){
        try
        {
            TemplateApi.setIndustry( "1", "4", token_key );
        } catch ( WeiXinException e )
        {
            e.printStackTrace();
        }
    }
    
    /**
     * <获得模板ID>
     * <获得模板ID>
     * @author  kaylves
     * @time  2015年6月16日 下午1:43:25 [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @Test
    public void addTemplateTest(){
        try
        {
            TemplateApi.addTemplate( "OPENTM201535984", token_key );
        } catch ( WeiXinException e )
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void sendTemplateTest(){
        try
        {
            TemplateMessage message = new TemplateMessage();
            
            message.setUrl( "http://www.dmrj.top/fc_improve/fire/showfire/15E0E23E5BE01CCEA696F376C313292F.htm" );
            message.setTouser( "oJvD3w2sEmpzZ6LqUs43WfI_XdpI" );
            message.setTopcolor( "#FF0000" );
            message.setTemplate_id( "ucdZLNydngvMMYP-Cjh6KSRJX6qVyqCv6KkJ_UJ8HoM" );
            
            Map<String,Object> data = new HashMap<String,Object>();
            Map<String,Object> first = new HashMap<String,Object>();
            Map<String,Object> keynote1 = new HashMap<String,Object>();
            Map<String,Object> keynote2 = new HashMap<String,Object>();
            Map<String,Object> keynote3 = new HashMap<String,Object>();
            Map<String,Object> remark = new HashMap<String,Object>();
            first.put( "value", "恭喜你签到成功！" );
            first.put( "color", "#173177" );
            keynote1.put( "value", "恭喜你签到成功！" );
            keynote1.put( "color", "#173177" );
            keynote2.put( "value", "恭喜你签到成功！" );
            keynote2.put( "color", "#173177" );
            keynote3.put( "value", "恭喜你签到成功！" );
            keynote3.put( "color", "#173177" );
            
            remark.put( "value", "查看详细" );
            remark.put( "color", "#FF0000" );
            
            
            data.put( "first",      first );
            data.put( "keynote1",   keynote1 );
            data.put( "keynote2",   keynote2 );
            data.put( "keynote3",   keynote3 );
            data.put( "remark",     remark );
            message.setData( data );
            TemplateApi.sendTemplateMsg( message, token_key );
        } catch ( WeiXinException e )
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void sendTemplateTest2(){
        try
        {
            TemplateMessage message = new TemplateMessage();
            
            message.setUrl( "http://weixin.qq.com/download" );
            message.setTouser( "o0MlxuFdmfllHhkcah_550H24ijY" );
            message.setTopcolor( "#FF0000" );
            message.setTemplate_id( "HQ3tI-2odw_vhXF79iCJz1uQsIKsQTfVn8ei_rnfh6U" );
            
            Map<String,Object> data = new HashMap<String,Object>();
            
            Map<String,Object> first = new HashMap<String,Object>();
            Map<String,Object> keyword1 = new HashMap<String,Object>();
            Map<String,Object> keyword2 = new HashMap<String,Object>();
            Map<String,Object> keyword3 = new HashMap<String,Object>();
            Map<String,Object> keyword4 = new HashMap<String,Object>();
            
            Map<String,Object> remark = new HashMap<String,Object>();
            first.put( "value", "签到成功，请继续保持。" );
            first.put( "color", "#173177" );
            keyword1.put( "value", "100023" );
            keyword1.put( "color", "#173177" );
            keyword2.put( "value", "2014年10月9日 11:06" );
            keyword2.put( "color", "#173177" );
            keyword3.put( "value", "赠送1活动积分" );
            keyword3.put( "color", "#173177" );
            keyword4.put( "value", "1天" );
            keyword4.put( "color", "#173177" );
            
            remark.put( "value", "感谢您对我们的关注和支持" );
            remark.put( "color", "#FF0000" );
            
            
            data.put( "first",      first );
            data.put( "keyword1",   keyword1 );
            data.put( "keyword2",   keyword2 );
            data.put( "keyword3",   keyword3 );
            data.put( "keyword4",   keyword4 );
            data.put( "remark",     remark );
            message.setData( data );
            TemplateApi.sendTemplateMsg( message, token_key );
        } catch ( WeiXinException e )
        {
            e.printStackTrace();
        }
    }
}
