package cn.linkage.service;

import java.util.List;

import cn.linkage.entity.Message;


/**
 * 
 * <一句话功能简述>留言接口
 * <功能详细描述>
 * 
 * @author  jack
 * @version  [版本号, 2017年9月29日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface MessageService
{
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param message
     * @return [参数说明]
     * 
     * @see [类、类#方法、类#成员]
     */
    int saveMessage(Message message);
    
    /**
     * <一句话功能简述>获取所有留言
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @see [类、类#方法、类#成员]
     */
    List<Message> getAllMessage();
}
