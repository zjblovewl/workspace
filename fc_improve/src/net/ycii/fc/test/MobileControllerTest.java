package net.ycii.fc.test;



import junit.framework.Assert;
import net.sf.json.JSONObject;
import net.ycii.fc.web.MobileController;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tyunsoft.base.utils.PageUtil;


/**
 * 控制层测试类
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年11月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MobileControllerTest extends BaseController
{
    @Autowired
    private MobileController mobileController;
    
    
    /**
     * <汶南好人列表集合接口>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
//    @Test
//    public void goodBehaviorListTest()
//    {
//        request.setMethod( RequestMethod.POST.toString() );
//        request.setRequestURI( "/mobile/free/goodBehaviorList.htm" );
//        try
//        {
//            JSONObject obj = new JSONObject();
//            obj.put( "currentPage", 1 );
//            obj.put( "pageSize", 10 );
//            obj.put( "type", 1 );
//            request.setParameter( "data", obj.toString() );
//            handlerAdapter.handle( request, response, mobileController );
//            Assert.assertNotNull( response.getContentAsString() );
//            System.out.println(response.getContentAsString());
//            
//        } catch ( Exception e )
//        {
//            e.printStackTrace();
//        }
//    }
    
    @Test
    public void goodBehaviorViewTest()
    {
        request.setMethod( RequestMethod.POST.toString() );
        request.setRequestURI( "/mobile/free/goodBeahaviorView.htm" );
        try
        {
            JSONObject obj = new JSONObject();
            obj.put( "type", 4);
            request.setParameter( "data", obj.toString() );
            handlerAdapter.handle( request, response, mobileController );
            Assert.assertNotNull( response.getContentAsString() );
            System.out.println(response.getContentAsString());
            
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    /**
     * <美丽乡村列表集合接口>                              
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @Test
    public void beautifulCountryListTest()
    {
        request.setMethod( RequestMethod.POST.toString() );
        request.setRequestURI( "/mobile/free/beautifulCountryList.htm" );
        try
        {
            JSONObject obj = new JSONObject();
            obj.put( "currentPage", 1 );
            obj.put( "pageSize", 10 );
            request.setParameter( "data", obj.toString() );
            handlerAdapter.handle( request, response, mobileController );
            Assert.assertNotNull( response.getContentAsString() );
            System.out.println(response.getContentAsString());
            
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    /**
     * <加载志愿服务详细接口>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @Test
    public void beautifulCountryViewTest()
    {
        request.setMethod( RequestMethod.POST.toString() );
        request.setRequestURI( "/mobile/free/beautifulCountryView.htm" );
        try
        {
            JSONObject obj = new JSONObject();
            obj.put( "id", "150F004B2C10154E3C34B1F18A03FF03" );
            request.setParameter( "data", obj.toString() );
            handlerAdapter.handle( request, response, mobileController );
            Assert.assertNotNull( response.getContentAsString() );
            System.out.println(response.getContentAsString());
            
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    /**
     * <美丽乡村列表集合接口>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @Test
    public void classRoomListTest()
    {
        request.setMethod( RequestMethod.POST.toString() );
        request.setRequestURI( "/mobile/free/classRoomList.htm" );
        try
        {
            System.out.println(PageUtil.getBasePath( request ));
            JSONObject obj = new JSONObject();
            obj.put( "currentPage", 1 );
            obj.put( "pageSize", 10 );
            request.setParameter( "data", obj.toString() );
            handlerAdapter.handle( request, response, mobileController );
            Assert.assertNotNull( response.getContentAsString() );
            System.out.println(response.getContentAsString());
            
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    /**
     * <加载志愿服务详细接口>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @Test
    public void classRoomViewTest()
    {
        request.setMethod( RequestMethod.POST.toString() );
        request.setRequestURI( "/mobile/free/classRoomView.htm" );
        try
        {
            JSONObject obj = new JSONObject();
            obj.put( "id", "150FB8998F801EDEC1E4F2651D4C93DA" );
            request.setParameter( "data", obj.toString() );
            handlerAdapter.handle( request, response, mobileController );
            Assert.assertNotNull( response.getContentAsString() );
            System.out.println(response.getContentAsString());
            
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    /**
     * <美丽乡村列表集合接口>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @Test
    public void impressSouthListTest()
    {
        request.setMethod( RequestMethod.POST.toString() );
        request.setRequestURI( "/mobile/free/impressSouthList.htm" );
        try
        {
            JSONObject obj = new JSONObject();
            obj.put( "currentPage", 1 );
            obj.put( "pageSize", 10 );
            request.setParameter( "data", obj.toString() );
            handlerAdapter.handle( request, response, mobileController );
            Assert.assertNotNull( response.getContentAsString() );
            System.out.println(response.getContentAsString());
            
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    /**
     * <加载志愿服务详细接口>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @Test
    public void impressSouthViewTest()
    {
        request.setMethod( RequestMethod.POST.toString() );
        request.setRequestURI( "/mobile/free/impressSouthView.htm" );
        try
        {
            JSONObject obj = new JSONObject();
            obj.put( "id", "150EF0EFC97020A6AEAA2B636C1CD813" );
            request.setParameter( "data", obj.toString() );
            handlerAdapter.handle( request, response, mobileController );
            Assert.assertNotNull( response.getContentAsString() );
            System.out.println(response.getContentAsString());
            
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 民生实事接口测试
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @Test
    public void peoplethingTest(){
        request.setMethod( RequestMethod.POST.toString() );
        request.setRequestURI( "/mobile/free/peoplething.htm" );
        try
        {
            JSONObject obj = new JSONObject();
            obj.put( "content", "真烦啊" );
            obj.put( "progress", "进展" );
            obj.put( "problem", "问题" );
            obj.put( "user_id", "flymz" );
            obj.put( "handAddress", "沈家庄社区,沈家庄" );
            request.setParameter( "data", obj.toString() );
            handlerAdapter.handle( request, response, mobileController );
            Assert.assertNotNull( response.getContentAsString() );
            System.out.println(response.getContentAsString());
            
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
}
