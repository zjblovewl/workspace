package net.ycii.fc.util;

public class MusicMessage
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
    
  private Music Music;

  public Music getMusic()
  {
    return this.Music;
  }

  public void setMusic(Music music) {
    this.Music = music;
  }
}