package net.ycii.fc.service.impl;

import com.tyunsoft.base.annotation.dao.IAnnotaionDao;
import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.utils.ConditionConvert;
import com.tyunsoft.base.utils.Pager;
import com.tyunsoft.base.utils.SqlFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.ycii.api.TemplateApi;
import net.ycii.entity.TemplateMessage;
import net.ycii.fc.entity.AccessToken;
import net.ycii.fc.entity.AddrBook;
import net.ycii.fc.entity.Fire;
import net.ycii.fc.entity.FourVirtues;
import net.ycii.fc.entity.WxParty;
import net.ycii.fc.entity.WxUser;
import net.ycii.fc.service.ICoreService;
import net.ycii.fc.service.IFireService;
import net.ycii.fc.util.BaseConstant;
import net.ycii.fc.util.DateUtil;
import net.ycii.fc.util.GetLatAndLngByBaidu;
import net.ycii.fc.util.SessionHelper;
import net.ycii.fc.util.WeixinUtil;

import org.eclipse.jetty.util.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

@Service("fireService")
public class FireServiceImpl
  implements IFireService
{

  @Autowired
  private IAnnotaionDao dao;

  @Autowired
  private IDao iDao;
  
  @Autowired
  private ICoreService coreService;
  
  private static final Logger logger = LoggerFactory.getLogger( FireServiceImpl.class );

  public Pager list(int pageNumber, int pageSize, List<SearchCondition> conditions)
  {
    return this.dao.queryPage(getSql("sql_query_map_count"), getPageSql("sql_query_map_list", pageNumber, pageSize), ConditionConvert.convert(conditions), Fire.class);
  }

  public Pager listMobile(int pageNumber, int pageSize, List<SearchCondition> conditions)
  {
    return this.dao.queryPage(getSql("sql_query_fourvirtues_count"), getPageSql("sql_query_fourvirtues_list_mobile", pageNumber, pageSize), ConditionConvert.convert(conditions), FourVirtues.class);
  }

  public List<Object> listAll()
  {
    return this.dao.queryAllList(Fire.class);
  }

  public Object queryById(String id)
  {
    Fire fire = new Fire();
    fire.setId(id);
    return this.dao.queryByKey(Fire.class, fire);
  }

  public boolean deleteById(String id)
  {
    FourVirtues fv = new FourVirtues();
    fv.setId(id);
    int result = this.dao.deleteByKey(fv);
    return result == 1;
  }

  public int deleteAll()
  {
    AddrBook addrBook = new AddrBook();
    return this.dao.deleteAll(addrBook);
  }

  public boolean insert(Fire map, HttpServletRequest request)
  {
    map.setCreateUser(SessionHelper.getUserId());
    Date createTime = DateUtil.getDateFromStr(map.getTempTime());
    map.setCreateTime(createTime);
    if ((!map.getPostion().contains("省")) && (!map.getPostion().contains("市")))
    {
      map.setPostion("江苏省南京市集庆门大街地铁站");
    }
    Map resultMap = GetLatAndLngByBaidu.getLngAndLat(map.getPostion());
    if ((resultMap != null) && (resultMap.containsKey("lng")))
    {
      map.setLongitude(((Double)resultMap.get("lng")).toString());
      map.setLatitude(((Double)resultMap.get("lat")).toString());
    }
    map.setDefatulTime(new Date());
    
    if(map.getFireDescribe() != null && map.getFireDescribe().length() > 10000)
    {
        map.setFireDescribe( map.getFireDescribe().substring( 0, 9500 ) );
    }
    int result = this.dao.insert(map);
    
    List<WxUser> userLists = this.findAll();
    if(userLists != null && userLists.size() > 0)
    {
        logger.info( "给"+ userLists.size() +"位用户下发微信模版消息" );
        
        WxParty party = this.coreService.getWxPartyByAppId(1);
        AccessToken at = WeixinUtil.getAccessToken(party.getAppId(), party.getAppSecret());
        
        String url = BaseConstant.WX_DOMAIN_CALL_URL + "fire/showfire/" + map.getId() + ".htm";
        String place = map.getPostion();
        String fireTitle = map.getFire();
        logger.info( "详细url：" + url );
        logger.info( "位置:" + place );
        logger.info( "标题："  + fireTitle );
        for(WxUser user : userLists)
        {
            logger.info( user.getOpenId() );
            this.sendTemplate( url, user.getOpenId(), place, fireTitle, at.getAccess_token() );
        }
    }
    return result == 1;
  }
  
  public void sendTemplate(String url,String openId,String place,String fireTitle,String token_key)
  {
    try
    {
          TemplateMessage message = new TemplateMessage();
          message.setUrl( url );
          message.setTouser( openId );
          message.setTopcolor( "#FF0000" );
          message.setTemplate_id( BaseConstant.WX_TEMPLATE_ID);
          
          Map<String,Object> data = new HashMap<String,Object>();
          Map<String,Object> first = new HashMap<String,Object>();
          Map<String,Object> keynote1 = new HashMap<String,Object>();
          Map<String,Object> keynote2 = new HashMap<String,Object>();
          Map<String,Object> remark = new HashMap<String,Object>();
          first.put( "value", "重要火情通知" );
          first.put( "color", "#173177" );
          keynote1.put( "value", fireTitle);
          keynote1.put( "color", "#173177" );
          keynote2.put( "value", place );
          keynote2.put( "color", "#173177" );
          
          remark.put( "value", "查看详细" );
          remark.put( "color", "#FF0000" );
          
          
          data.put( "first",      first );
          data.put( "keyword1",   keynote1 );
          data.put( "keyword2",   keynote2 );
          data.put( "remark",     remark );
          message.setData( data );
          logger.info( "模版字符串:" + JSONObject.fromObject( message ).toString() );
          TemplateApi.sendTemplateMsg( message, token_key );
          logger.info( "发送成功" );
    } catch ( Exception e )
    {
        e.printStackTrace();
    }
      
  }

  private String getSql(String sqlId)
  {
    return SqlFactory.getInstance("fc_sql.xml", FireServiceImpl.class).getSql(sqlId);
  }

  private String getPageSql(String sqlId, int pageNumber, int pageSize)
  {
    return SqlFactory.getInstance("fc_sql.xml", FireServiceImpl.class).getPageSql(sqlId, pageNumber, pageSize);
  }

  public Fire getNewFire()
  {
    String sql = getSql("sql_query_fire_new_list");
    List result = new ArrayList();
    SqlRowSet rs = this.iDao.find(sql, new Object[0]);
    Fire fire = null;
    while (rs.next())
    {
      fire = new Fire();
      fire.setId(rs.getString("id"));
    }
    return fire;
  }

    @Override
    public List<WxUser> findAll()
    {
        String sql = getSql( "sql_query_wx_user_list" );
        List<WxUser> result = new ArrayList<WxUser>();
        SqlRowSet rs = this.iDao.find( sql, new Object[0] );
        WxUser user = null;
        while ( rs.next() )
        {
            user = new WxUser();
            user.setId( rs.getString( "id" ) );
            user.setOpenId( rs.getString( "openId" ) );
            user.setPartyId( rs.getLong( "partyId" ) );
            result.add( user );
        }
        return result;
    }
}