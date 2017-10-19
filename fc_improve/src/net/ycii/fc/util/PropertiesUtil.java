/*
 * 文 件 名:  PropertiesUtil.java
 * 版    权:  Foresoft Technologies,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2015年3月19日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package net.ycii.fc.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropertiesUtil
{
  private static PropertiesUtil oInstance = new PropertiesUtil();
  private static Properties oProperties;

  protected void loadProperties(String propertieName)
  {
    try
    {
      oProperties = new Properties();

      ClassLoader oClassLoader = Thread.currentThread()
        .getContextClassLoader();

      if (oClassLoader == null) {
        oClassLoader = oInstance.getClass().getClassLoader();
      }

      InputStream is = oClassLoader
        .getResourceAsStream(propertieName);

      if (is != null) {
        oProperties.load(is);
        is.close();
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

 public static void writeProperties()
 {
     Resource res = new ClassPathResource("accountLogin.properties");
     OutputStream out = null;
     try {
         Properties props = PropertiesLoaderUtils.loadAllProperties("accountLogin.properties");
         props.setProperty("", "");
         props.store(out = new FileOutputStream(res.getFile()), "");
     } catch (IOException e) {
         System.out.println("GGGGGGG");
     } finally {
         try {
             if (out != null)
                 out.close();
         } catch (IOException e) {
             e.printStackTrace();
         }

     }     
 }
  
  public static String getProperty(String propertieName, String key)
  {
    oInstance.loadProperties(propertieName);
    return oProperties.getProperty(key);
  }

  public static int getInt(String propertieName, String sPropertyName, int iDefaultValue)
  {
    try
    {
      String sProperty = getProperty(propertieName, sPropertyName);
      return Integer.parseInt(sProperty); } catch (Exception e) {
    }
    return iDefaultValue;
  }

  public static String getString(String propertieName, String sPropertyName, String sDefaultValue)
  {
    try
    {
      return getProperty(propertieName, sPropertyName); } catch (Exception e) {
    }
    return sDefaultValue;
  }

  public static HashMap getProperties(String propertieName, String keyGroup)
  {
    HashMap hashmap = new HashMap();
    oInstance.loadProperties(propertieName);
    Enumeration enumeration = oProperties.keys();
    while (enumeration.hasMoreElements()) {
      String tempKey = (String)enumeration.nextElement();
      if (tempKey.startsWith(keyGroup)) {
        hashmap.put(tempKey, oProperties.get(tempKey));
      }
    }
    return hashmap;
  }

  public static boolean getBoolean(String propertieName, String key, boolean bDefaultValue)
  {
    try
    {
      String s = getProperty(propertieName, key);
      if (s != null) {
        return (s.equalsIgnoreCase("true")) || (s.equalsIgnoreCase("t"));
      }
      return bDefaultValue;
    } catch (Exception e) {
    }
    return bDefaultValue;
  }

  public static String getDBName(String propertieName)
  {
    String dbUrl = getProperty(propertieName, "db.url");
    if (dbUrl != null) {
      String[] splits = dbUrl.split(":");
      if ((splits != null) && (splits.length > 2)) {
        return splits[1];
      }
    }
    return null;
  }

  public static void main(String[] args) {
        String property = getProperty("set.properties", "slsbWebService");
        System.out.println("property = " + property);
  }
}