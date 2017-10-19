package net.ycii.entity;

/**
 * <一句话功能简述>消息类型
 * <p><功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年5月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public enum MessageEventType
{
    /**
     * 订阅
     */
    subscribe,
    /**
     * 取消订阅
     */
    unsubscribe,
    /**
     *用户已关注时的事件推送
     */
    SCAN,
    /**
     * 上报地理位置事件
     */
    LOCATION,
    /**
     * 自定义菜单事件
     */
    CLICK
}
