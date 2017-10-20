package cn.linkage.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
    
    private static String dateFormat2 = "yyyy-MM-dd HH:mm:ss";
    
    public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat2);;
        String strDate = formatter.format(date);
        return strDate;
    }

}
