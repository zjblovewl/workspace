package net.ycii.fc.service.impl;

import com.tyunsoft.base.annotation.dao.IAnnotaionDao;
import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.utils.IDUtil;
import com.tyunsoft.base.utils.SqlFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.ycii.fc.entity.Fire;
import net.ycii.fc.entity.WxParty;
import net.ycii.fc.entity.WxUser;
import net.ycii.fc.service.ICoreService;
import net.ycii.fc.service.IFireService;
import net.ycii.fc.util.AutoReply;
import net.ycii.fc.util.BaseConstant;
import net.ycii.fc.util.DateUtil;
import net.ycii.fc.util.GetLatAndLngByBaidu;
import net.ycii.fc.util.MessageResponse;
import net.ycii.fc.util.MessageUtil;
import net.ycii.fc.util.SessionHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

@Service("coreService")
public class CoreServiceImpl
  implements ICoreService
{
  Logger log = LoggerFactory.getLogger(CoreServiceImpl.class);

  @Autowired
  private IAnnotaionDao dao;

  @Autowired
  private IDao iDao;

  @Autowired
  private IFireService fireService;

  public WxParty getWxPartyByAppId(long wxId)
  {
    List<WxParty> partys = getWxParty();
    WxParty tempParty = null;
    for (WxParty pt : partys)
    {
      if (pt.getPartyId() == wxId)
      {
        tempParty = new WxParty();
        tempParty = pt;
      }
    }
    return tempParty;
  }

  public List<WxParty> getWxParty()
  {
    String sql = getSql("sql_query_wx_list");
    List result = new ArrayList();
    SqlRowSet rs = this.iDao.find(sql, new Object[0]);
    WxParty party = null;
    while (rs.next())
    {
      party = new WxParty();
      party.setAppId(rs.getString("appId"));
      party.setAppSecret(rs.getString("appSecret"));
      party.setName(rs.getString("name"));
      party.setPartyId(rs.getLong("partyId"));
      party.setToken(rs.getString("token"));
      result.add(party);
    }
    return result;
  }

  private String getSql(String sqlId)
  {
    return SqlFactory.getInstance("fc_sql.xml", FourVirtuesServiceImpl.class).getSql(sqlId);
  }

  public String processRequest(long partyId, HttpServletRequest request)
  {
      String respMessage = null;
      try {
          // 默认返回的文本消息内容
          String respContent = "请求处理异常，请稍候尝试！";
          // xml请求解析
          // 调用消息工具类MessageUtil解析微信发来的xml格式的消息，解析的结果放在HashMap里；
          Map<String, String> requestMap = MessageUtil.parseXml(request);
          // 从HashMap中取出消息中的字段；
          // 发送方帐号（open_id）
          String fromUserName = requestMap.get("FromUserName");
          // 公众帐号
          String toUserName = requestMap.get("ToUserName");
          // 消息类型
          String msgType = requestMap.get("MsgType");
          // 消息内容
          String content = requestMap.get("Content");
          // 从HashMap中取出消息中的字段；
          log.info("fromUserName is:" +fromUserName+" toUserName is:" +toUserName+" msgType is:" +msgType);

          WxParty party = this.getWxPartyByAppId(partyId);
          // 文本消息
          if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
              if("绑定".equals(content)){
                  //return AutoReply.getLogin(party, fromUserName,toUserName);// 返回图文
              }
              else if("解绑".equals(content)){
//                  wxLoginService.unbindWxUser(partyId,fromUserName);
//                  respContent = "解绑成功！";
//                  return MessageResponse.getTextMessage(fromUserName , toUserName , respContent);
              }else{
                  respContent = "亲爱的" + party.getName() + "用户，您输入的指令无效！";
                  return MessageResponse.getTextMessage(fromUserName , toUserName , respContent);
              }
          } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {// 事件推送
              String eventType = requestMap.get("Event");// 事件类型
              if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {// 订阅
                  respContent = "欢迎关注" + party.getName() + "公众号！";
                  log.info( "openId : " + fromUserName );
                  log.info( "公众号id : " + partyId );
                  
                  WxUser user = new WxUser(IDUtil.getUUIDStr(),party.getPartyId(),fromUserName);
                  int result = this.dao.insert(user);
                  if(result > 0)
                  {
                      log.info( "订阅用户保存成功" );
                  }
                  else
                  {
                      log.info( "订阅用户保存失败" );
                  }
                  return MessageResponse.getTextMessage(fromUserName , toUserName , respContent);
                  
//                //respContent = "欢迎关注广西和教育！";
//                respContent = "感谢您关注广西和教育。\r\n";
//                respContent += "和教育为您打造教育资讯、资源、应用、社区为一体的开放式教育服务，与您共同关注孩子的成长。\r\n\r\n";
//                respContent += "【快乐暑假·分享快乐·柳州】活动火热进行中！详情点击-><a href='http://gxhd.meirixue.com/weixin/index.php'>快乐暑假</a>，报名参加都有机会获得精美奖品哦！\r\n\r\n";
//                respContent += "快捷回复数字获取：\r\n";
//                respContent += "回复数字“1”，获取账号注册流程；\r\n";
//                respContent += "回复数字“2”，获取账号归属资料。\r\n";
//                respContent += "其他地市活动即将开放，敬请期待！";
//                return MessageResponse.getTextMessage(fromUserName , toUserName , respContent);
              } else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {// 取消订阅
                  WxUser user = this.findWxUserByOpenId( fromUserName );
                  if(user != null)
                  {
                      WxUser tempUser = new WxUser();
                      tempUser.setId( user.getId() );;
                      dao.deleteByKey( tempUser );
                      log.info( "删除关注用户成功!" );
                  }
              } else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {// 自定义菜单点击事件
                  String eventKey = requestMap.get("EventKey");// 事件KEY值，与创建自定义菜单时指定的KEY值对应
                  log.info( eventKey );
                  Fire fire = this.fireService.getNewFire();
                  String id = "1";
                  if (fire != null)
                  {
                    id = fire.getId();
                  }
                  if (eventKey.equals("101"))
                    return AutoReply.getWelcome(party, fromUserName, toUserName, id);
                  if (eventKey.equals("201"))
                    return AutoReply.getWelcome(party, fromUserName, toUserName, id);
                  if (eventKey.equals("202"))
                    return AutoReply.getWelcome(party, fromUserName, toUserName, id);
                  }
          }
      }
      catch (Exception e) {
          e.printStackTrace();
      };

      return respMessage;
  }
  
  public WxUser findWxUserByOpenId(String openId)
  {
      List<WxUser> userList = fireService.findAll();
      WxUser user = null;
      if(userList != null && userList.size() > 0)
      {
          for(WxUser ue : userList)
          {
              if(ue.getOpenId().equals( openId ))
              {
                  user = new WxUser();
                  user = ue;
              }
          }
      }
      return user;
  }
}