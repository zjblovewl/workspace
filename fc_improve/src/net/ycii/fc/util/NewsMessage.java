package net.ycii.fc.util;

import java.util.List;

public class NewsMessage
{
 // 接收方帐号（收到的OpenID�? 
    private String ToUserName;  
    // �?��者微信号  
    private String FromUserName;  
    // 消息创建时间 （整型）  
    private long CreateTime;  
    // 消息类型（text/music/news�? 
    private String MsgType;  
    // �?x0001被标志时，星标刚收到的消�? 
    private int FuncFlag;  
  
    public String getToUserName() {  
        return ToUserName;  
    }  
  
    public void setToUserName(String toUserName) {  
        ToUserName = toUserName;  
    }  
  
    public String getFromUserName() {  
        return FromUserName;  
    }  
  
    public void setFromUserName(String fromUserName) {  
        FromUserName = fromUserName;  
    }  
  
    public long getCreateTime() {  
        return CreateTime;  
    }  
  
    public void setCreateTime(long createTime) {  
        CreateTime = createTime;  
    }  
  
    public String getMsgType() {  
        return MsgType;  
    }  
  
    public void setMsgType(String msgType) {  
        MsgType = msgType;  
    }  
  
    public int getFuncFlag() {  
        return FuncFlag;  
    }  
  
    public void setFuncFlag(int funcFlag) {  
        FuncFlag = funcFlag;  
    } 
    
  private int ArticleCount;
  private List<Article> Articles;

  public int getArticleCount()
  {
    return this.ArticleCount;
  }

  public void setArticleCount(int articleCount) {
    this.ArticleCount = articleCount;
  }

  public List<Article> getArticles() {
    return this.Articles;
  }

  public void setArticles(List<Article> articles) {
    this.Articles = articles;
  }
}