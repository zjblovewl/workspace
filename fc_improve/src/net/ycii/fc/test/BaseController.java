package net.ycii.fc.test;

import net.sf.json.JSONObject;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年3月12日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@ContextConfiguration(locations = { "file:WebRoot/WEB-INF/applicationContext.xml","file:WebRoot/WEB-INF/spring-servlet.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class BaseController {
    @Autowired
    protected AnnotationMethodHandlerAdapter handlerAdapter;
    protected MockHttpServletRequest request;
    protected MockHttpServletResponse response;
    
    @Before
    public void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        response.setCharacterEncoding("UTF-8");
    }
    
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年7月15日 上午10:06:51
     * @param json
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean isSuccess(String json){
        return JSONObject.fromObject( json ).getString( "success" ).equals( "0" );
    }

}
