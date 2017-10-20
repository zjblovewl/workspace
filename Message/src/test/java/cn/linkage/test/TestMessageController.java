package cn.linkage.test;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.metadata.HanaCallMetaDataProvider;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.linkage.util.SpringJUnit4ClassRunnerWithLog4j;


/**
 * 测试苏城社区平台服务端接口 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  jack
 * @version  [版本号, 2017年9月14日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@RunWith(SpringJUnit4ClassRunnerWithLog4j.class)
@WebAppConfiguration 
@ContextConfiguration(locations={"classpath:/spring-mvc.xml","classpath:/spring-mybatis.xml"})  
@ActiveProfiles("develop")
public class TestMessageController
{
    @Autowired  
    private WebApplicationContext wac;
    
    private MockMvc mockMvc;  
    
    final Logger logger = LoggerFactory.getLogger( TestMessageController.class );
    
    @Before  
    public void setUp() {  
        mockMvc = webAppContextSetup(wac).build();
    }
    
    /**
     * <一句话功能简述>测试新增或删除下级单位
     * <功能详细描述> [参数说明]
     * @throws Exception 
     * 
     * @see [类、类#方法、类#成员]
     */
    @Test
    public void testSaveOrDelSubUnit() throws Exception
    {
        String name = "嘿嘿";
        String content = "哈哈哈";
        String phone = "13913868953";
        String email = "dafesaf@139.com";
        String qq = "323232434";
        String wechat = "jueduidanding";
        mockMvc .perform(post("/message/leaveMessage.jhm?name=" + name + "&content=" + content + "&phone=" + phone))
        .andDo(print());
    }
}
