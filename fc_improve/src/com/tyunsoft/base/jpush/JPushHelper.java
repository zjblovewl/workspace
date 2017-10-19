
package com.tyunsoft.base.jpush;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.DeviceEnum;
import cn.jpush.api.push.NotificationParams;
import cn.jpush.api.push.ReceiverTypeEnum;

public class JPushHelper
{

    private static final String masterSecret = "d267a82a377e0c280dcf3bc4";

    private static final String appKey = "6c6f819b62fb8a9771399933";
    
    public static final String REGISTRATION_ID = "0900e8d85ef";
    public static final String TAG = "tag_api";

    public static void main(String[] args) {
        sendNotificationForAllUser("http://www.baidu.com","快乐社区行，方便你我他");
    }

    /**
     * 给单个用户发送消息
     * @param content 消息内容
     * @param url 打开的URL
     * @param registrationID 用户的registrationID推送编码
     */
    public  static void sendNotificationToUser(String  content, String url,String registrationID) {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey,864000, DeviceEnum.Android, false);
        NotificationParams params = new NotificationParams();
        params.setReceiverType(ReceiverTypeEnum.REGISTRATION_ID);
        params.setReceiverValue(registrationID);
        params.setReceiverType(ReceiverTypeEnum.TAG);
        params.setReceiverValue(TAG);
        
        Map<String,Object> extras = new HashMap<String,Object>();
        extras.put( "url", url );
        jpushClient.sendNotification(content, params, extras);

    }
    
    /**
     * 发送信息给所有用户
     * @param url 打开的URL页面
     * @param content 消息内容
     */
    public  static void sendNotificationForAllUser(String url, String  content) {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey,864000, DeviceEnum.Android, false);
        NotificationParams params = new NotificationParams();
        params.setReceiverType( ReceiverTypeEnum.APP_KEY);
        params.setReceiverValue( appKey );
        
        Map<String,Object> extras = new HashMap<String,Object>();
        extras.put( "url",  url  );
        jpushClient.sendNotification( content, params, extras );
    }
    
}
