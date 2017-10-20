package cn.linkage.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.linkage.entity.Message;
import cn.linkage.service.MessageService;
import cn.linkage.util.DateUtil;
import cn.linkage.util.ResultVo;
import cn.linkage.util.StringUtils;

/**
 * 
 * <一句话功能简述>提供留言服务接口
 * <功能详细描述>
 * 
 * @author  jack
 * @version  [版本号, 2017年9月29日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping("/message")
public class MessageController
{
    Logger log = LoggerFactory.getLogger(MessageController.class);
    
    @Autowired(required=true)
    private MessageService messageService;
    
    private static ObjectMapper objectMapper = new ObjectMapper();
    
    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    /**
     * <一句话功能简述>新增留言功能
     * <功能详细描述>
     * @param request
     * @param response
     * @return [参数说明]
     * 
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/leaveMessage.jhm" , method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody ResultVo leaveMessage(HttpServletRequest request,HttpServletResponse response,Message message)
    {
        response.setCharacterEncoding( "UTF-8" );
        if(StringUtils.isEmptyOrNull( message.getName() ))
        {
            return new ResultVo( 1, "姓名不能为空", null );
        }
        
        if(message.getName().trim().length() > 150)
        {
            return new ResultVo( 1, "姓名长度不能超过150", null );
        }
        
        if(StringUtils.isEmptyOrNull( message.getContent() ))
        {
            return new ResultVo( 1, "留言内容不能为空", null );
        }
        
        if(message.getContent().trim().length() > 500)
        {
            return new ResultVo( 1, "留言内容长度不能超过500", null );
        }
        
        if(StringUtils.isEmptyOrNull( message.getPhone() ))
        {
            return new ResultVo( 1, "手机号不能为空", null );
        }
        
        if(!StringUtils.isChinaPhoneLegal( message.getPhone() ))
        {
            return new ResultVo( 1, "手机号格式错误", null );
        }
        
        message.setAddtime( DateUtil.formatDate(new Date()) );
        int rest = messageService.saveMessage( message );
        if(rest > 0)
        {
            List<Message> list = messageService.getAllMessage();
            return new ResultVo( 0, "留言成功", list );
        }
        else
        {
            return new ResultVo( 0, "留言失败", null );
        }
        
    }
    
    /**
     * <一句话功能简述>新增留言功能
     * <功能详细描述>
     * @param request
     * @param response
     * @return [参数说明]
     * 
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/showMessage.jhm" , method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody ResultVo showMessage(HttpServletRequest request,HttpServletResponse response,Message message)
    {
        response.setCharacterEncoding( "UTF-8" );
        List<Message> list = messageService.getAllMessage();
        return new ResultVo( 0, "获取留言列表成功", list );
    }
}
