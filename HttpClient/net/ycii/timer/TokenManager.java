package net.ycii.timer;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import net.ycii.api.WeiXinApi;
import net.ycii.entity.Token;

/**
 * <一句话功能简述>Token 管理
 * <功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年5月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TokenManager {
    
    /**
     * token cache
     */
	private static Map<String,String> tokenMap = new LinkedHashMap<String,String>();
	
	/**
	 * timer cache
	 */
	private static Map<String,Timer> timerMap = new HashMap<String, Timer>();


	/**
	 * 初始化token 刷新，每118分钟刷新一次。
	 * @param appid
	 * @param secret
	 */
	public static void init(final String appid,final String secret){
		if(timerMap.containsKey(appid)){
			timerMap.get(appid).cancel();
		}
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Token token = WeiXinApi.token(appid,secret);
				tokenMap.put(appid,token.getAccess_token());
			}
		},0,1000*60*118);
		timerMap.put(appid,timer);
	}

	/**
	 * 获取 access_token
	 * @param appid
	 * @return
	 */
	public static String getToken(String appid){
		return tokenMap.get(appid);
	}

	/**
	 * 获取第一个appid 的 access_token
	 * 适用于单一微信号
	 * @param appid
	 * @return
	 */
	public static String getDefaultToken(){
		Object[] objs = tokenMap.values().toArray();
		return objs.length>0?objs[0].toString():null;
	}

}
