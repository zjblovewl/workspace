/*
 * 文 件 名:  ApplicationUtil.java
 * 版    权:  Foresoft Technologies,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2015年3月20日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package net.ycii.fc.util;
 
 import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
 /**
  * 
  * @author Kylin
  *
  */
 public class ApplicationUtil implements ApplicationContextAware{
     private static ApplicationContext applicationContext;  
     @Override
     public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
         ApplicationUtil.applicationContext = applicationContext;
     }
     public static Object getBean(String name){
         return applicationContext.getBean(name);
     }
}