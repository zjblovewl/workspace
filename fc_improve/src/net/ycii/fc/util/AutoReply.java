package net.ycii.fc.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.util.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.ycii.fc.entity.WxParty;

public class AutoReply
{
    private static final Logger logger = LoggerFactory.getLogger( AutoReply.class );
    
    public static String getWelcome(WxParty party, String fromUserName, String toUserName, String id)
    {
      logger.info( "封装数据：" + party.getName() + ":" + fromUserName + ":" + toUserName + ":" + id );
      List<Article> articleList = new ArrayList<Article>();
      Article article = new Article();  
      article.setTitle(party.getName());  
      article.setDescription("点击进入" + party.getName());  
      article.setPicUrl(BaseConstant.WX_DOMAIN_CALL_URL + "images/welcome.jpg");  
      article.setUrl(BaseConstant.WX_DOMAIN_CALL_URL + "fire/showfire/" + id + ".htm");
      articleList.add(article);
      return MessageResponse.getNewsMessage(fromUserName , toUserName , articleList);
  }
}