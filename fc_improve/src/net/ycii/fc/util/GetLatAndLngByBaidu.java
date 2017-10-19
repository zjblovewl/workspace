package net.ycii.fc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetLatAndLngByBaidu
{
  private static final Logger logger = LoggerFactory.getLogger(GetLatAndLngByBaidu.class);

  public static Map<String, Double> getLngAndLat(String address)
  {
    Map map = new HashMap();
    String url = "http://api.map.baidu.com/geocoder/v2/?address=" + address + "&output=json&ak=" + "0HY3TGTWOgAFESr6pXlzVOE1LPnh0Poh";
    try {
      String json = loadJSON(url);
      JSONObject obj = JSONObject.fromObject(json);
      if (obj.get("status").toString().equals("0")) {
        double lng = obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
        double lat = obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
        map.put("lng", Double.valueOf(getDecimal(lng)));
        map.put("lat", Double.valueOf(getDecimal(lat)));
      }
      else {
        logger.debug("未找到相匹配的经纬度！");
      }
    }
    catch (Exception e) {
      logger.error("未找到相匹配的经纬度，请检查地址");
    }
    return map;
  }

  public static double getDecimal(double num) {
    if (Double.isNaN(num)) {
      return 0.0D;
    }
    BigDecimal bd = new BigDecimal(num);
    num = bd.setScale(6, 4).doubleValue();
    return num;
  }

  public static String loadJSON(String url) {
    StringBuilder json = new StringBuilder();
    try {
      URL oracle = new URL(url);
      URLConnection yc = oracle.openConnection();
      BufferedReader in = new BufferedReader(new InputStreamReader(
        yc.getInputStream(), "UTF-8"));
      String inputLine = null;
      while ((inputLine = in.readLine()) != null) {
        json.append(inputLine);
      }
      in.close();
    } catch (MalformedURLException localMalformedURLException) {
    } catch (IOException localIOException) {
    }
    return json.toString();
  }

  public static void main(String[] args)
  {
    getLngAndLat("宁波天一广场");
  }
}