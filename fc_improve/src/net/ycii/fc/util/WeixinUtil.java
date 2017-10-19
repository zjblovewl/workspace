package net.ycii.fc.util;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.security.SecureRandom;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import net.ycii.fc.entity.AccessToken;

public class WeixinUtil
{
  public static final String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
  public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

  public static String httpRequest(String requestUrl, String requestMethod, String outputStr)
  {
    StringBuffer buffer = new StringBuffer();
    try
    {
      TrustManager[] tm = { new MyX509TrustManager() };
      SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
      sslContext.init(null, tm, new SecureRandom());

      SSLSocketFactory ssf = sslContext.getSocketFactory();

      URL url = new URL(requestUrl);
      HttpsURLConnection httpUrlConn = (HttpsURLConnection)url.openConnection();
      httpUrlConn.setSSLSocketFactory(ssf);

      httpUrlConn.setDoOutput(true);
      httpUrlConn.setDoInput(true);
      httpUrlConn.setUseCaches(false);

      httpUrlConn.setRequestMethod(requestMethod);

      if ("GET".equalsIgnoreCase(requestMethod)) httpUrlConn.connect();

      if (outputStr != null) {
        OutputStream outputStream = httpUrlConn.getOutputStream();

        outputStream.write(outputStr.getBytes("UTF-8"));
        outputStream.close();
      }

      InputStream inputStream = httpUrlConn.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

      String str = null;
      while ((str = bufferedReader.readLine()) != null) {
        buffer.append(str);
      }
      bufferedReader.close();
      inputStreamReader.close();

      inputStream.close();
      inputStream = null;
      httpUrlConn.disconnect();

      return buffer.toString();
    } catch (ConnectException ce) {
      System.out.println("Weixin server connection timed out.");
    } catch (Exception e) {
      System.out.println("error.");
    }

    return null;
  }

  public static AccessToken getAccessToken(String appid, String appsecret)
  {
    AccessToken accessToken = null;

    String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET".replace("APPID", appid).replace("APPSECRET", appsecret);
    System.out.println("requestUrl:" + requestUrl);
    String json = httpRequest(requestUrl, "GET", null);

    System.out.println("json:" + json);
    try {
      accessToken = (AccessToken)new Gson().fromJson(json, AccessToken.class);
    } catch (Exception e) {
      accessToken = null;

      System.out.println("获取token失败 errcode:{} errmsg:{}");
    }
    return accessToken;
  }

  public static int getByteSize(String content)
  {
    int size = 0;
    if (content != null) {
      try
      {
        size = content.getBytes("utf-8").length;
      }
      catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }
    return size;
  }
}