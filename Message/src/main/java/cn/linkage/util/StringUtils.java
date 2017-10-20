package cn.linkage.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * <一句话功能简述>字符串帮助工具类
 * <功能详细描述>
 * 
 * @author  jack
 * @version  [版本号, 2017年9月29日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class StringUtils
{
    public static boolean isChinaPhoneLegal(String str){  
        String regExp = "^((13[0-9])|(15[^4])|(18[0,1,2,3,5-9])|(17[0-8])|(147))\\d{8}$";    
        Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(str);  
        return m.matches();  
    }
    
    /**
     * 判断字符串是否为空
     * 
     * @param string 设置字符串
     * @return boolean 返回是否为空
     */
    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }
    
    public static boolean isEmptyOrNull(String string) {
        if (isEmpty(string)){
            return true;
        }else if (isEmpty(string.trim())){
            return true;
        }else if (string.trim().equalsIgnoreCase("null")){
            return true;
        }
        return false;
    }
}
