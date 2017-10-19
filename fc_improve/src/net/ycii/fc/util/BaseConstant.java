package net.ycii.fc.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;



public class BaseConstant
{
  public static final String BaiDuMapAk = "0HY3TGTWOgAFESr6pXlzVOE1LPnh0Poh";
  public static final String WX_DOMAIN_CALL_URL = "http://cb3134a8.ngrok.io/fc_improve/";
  public static final String WX_TEMPLATE_ID = "ucdZLNydngvMMYP-Cjh6KSRJX6qVyqCv6KkJ_UJ8HoM";
  
  private static final String WX_VIEW_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx127797bfb713d1ee&redirect_uri=http%3A%2F%2Fcb3134a8.ngrok.io%2Ffc_improce%2Fcore%2FgetOpenUserInfoByWeb.htm&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
  
  private static String getEncode(String str)
  {
      
    String urlString = "";
    try
    {
        urlString = URLEncoder.encode(str, "UTF-8");
    } catch ( UnsupportedEncodingException e )
    {
        e.printStackTrace();
    }  
    return urlString;
  }
  
  public static void main( String[] args )
  {
      System.out.println(getEncode( "https://cb3134a8.ngrok.io/fc_improve/core/getOpenUserInfoByWeb.htm" ));
  }
  
}