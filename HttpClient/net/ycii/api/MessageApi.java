package net.ycii.api;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.json.JSONObject;
import net.ycii.context.WeiXinApplicationContext;
import net.ycii.entity.News;
import net.ycii.entity.WeiXinMessage;
import net.ycii.exception.WeiXinException;
import net.ycii.util.HttpClientUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


/**
 * <一句话功能简述>消息Api
 * <p><功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年5月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MessageApi
{
    /**
     * <一句话功能简述>解析消息内容
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月20日 下午12:04:04
     * @param xml
     * @return [参数说明]
     * 
     * @return WeiXinMessage [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static WeiXinMessage extractWeiXinMessage(String xml)
    {
        WeiXinMessage message = null;
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db;
            db = dbf.newDocumentBuilder();
            StringReader sr = new StringReader( xml );
            InputSource is = new InputSource( sr );
            Document document = db.parse( is );

            Element root = document.getDocumentElement();
            
            NodeList toUserNameList = root.getElementsByTagName( "ToUserName" );
            NodeList FromUserNameList = root.getElementsByTagName( "FromUserName" );
            NodeList CreateTimeList = root.getElementsByTagName( "CreateTime" );
            NodeList MsgTypeList = root.getElementsByTagName( "MsgType" );
            
            String msgType = MsgTypeList.item( 0 ).getTextContent();
            message = new WeiXinMessage();
            message.setToUserName( toUserNameList.item( 0 ).getTextContent() );
            message.setFromUserName( FromUserNameList.item( 0 ).getTextContent() );
            message.setCreateTime( CreateTimeList.item( 0 ).getTextContent() );
            message.setMsgType( msgType );
            /**
             * 消息类型
             * text         文本消息
             * image        图片消息
             * voice        语音消息
             * video        视频消息
             * shortvideo   小视频消息
             * location     地理位置消息
             * link         连接消息
             */
            if("text".equals( msgType) )
            {
                
                NodeList contentList = root.getElementsByTagName( "Content" );
                NodeList msgIdList = root.getElementsByTagName( "MsgId" );
                message.setContent( contentList.item( 0 ).getTextContent() );
                message.setMsgId(  msgIdList.item( 0 ).getTextContent()  );
            }
            else if("image".equals( msgType ))
            {
                NodeList picUrlList = root.getElementsByTagName( "PicUrl" );
                NodeList mediaIdList = root.getElementsByTagName( "MediaId" );
                message.setPicUrl( picUrlList.item( 0 ).getTextContent() );
                message.setMediaId( mediaIdList.item( 0 ).getTextContent() );
            }
            else if("voice".equals( msgType ))
            {
                NodeList mediaIdList = root.getElementsByTagName( "MediaId" );
                NodeList formatList = root.getElementsByTagName( "Format" );
                message.setFormat( formatList.item( 0 ).getTextContent() );
                message.setMediaId( mediaIdList.item( 0 ).getTextContent() );
            }
            else if("voice".equals( msgType ))
            {
                NodeList mediaIdList = root.getElementsByTagName( "MediaId" );
                NodeList ThumbMediaIdList = root.getElementsByTagName( "ThumbMediaId" );
                message.setMediaId( mediaIdList.item( 0 ).getTextContent() );
                message.setThumbMediaId ( ThumbMediaIdList.item( 0 ).getTextContent() );
            }
            else if("shortvideo".equals( msgType ))
            {
                NodeList mediaIdList = root.getElementsByTagName( "MediaId" );
                NodeList ThumbMediaIdList = root.getElementsByTagName( "ThumbMediaId" );
                message.setMediaId( mediaIdList.item( 0 ).getTextContent() );
                message.setThumbMediaId ( ThumbMediaIdList.item( 0 ).getTextContent() );
            }            
            else if("location".equals( msgType ))
            {
                NodeList Location_XList = root.getElementsByTagName( "Location_X" );
                NodeList Location_YList = root.getElementsByTagName( "Location_Y" );
                NodeList ScaleList = root.getElementsByTagName( "Scale" );
                NodeList LabelList = root.getElementsByTagName( "Label" );
                message.setLocationX( Location_XList.item( 0 ).getTextContent() );
                message.setLocationY( Location_YList.item( 0 ).getTextContent() );
                message.setScale( ScaleList.item( 0 ).getTextContent()  );
                message.setLabel( LabelList.item( 0 ).getTextContent() );
            }
            else if("link".equals( msgType ))
            {
                NodeList TitleList = root.getElementsByTagName( "Title" );
                NodeList DescriptionList = root.getElementsByTagName( "Description" );
                NodeList urlList = root.getElementsByTagName( "Url" );
                message.setTitle( TitleList.item( 0 ).getTextContent() );
                message.setDescription( DescriptionList.item( 0 ).getTextContent() );
                message.setUrl( urlList.item( 0 ).getTextContent()  );
            } 
            else if("event".equals( msgType ))
            {
                NodeList EventList = root.getElementsByTagName( "Event" );
                
                message.setEvent( EventList.item( 0 ).getTextContent() );
                
                NodeList EventKeyList = root.getElementsByTagName( "EventKey" );
                if(EventKeyList.getLength()>0)
                {
                    message.setEventKey( EventKeyList.item( 0 ).getTextContent() );
                }
                NodeList Ticket = root.getElementsByTagName( "Ticket" );
                if(Ticket.getLength()>0)
                {
                    message.setTicket( Ticket.item( 0 ).getTextContent() );                
                }
                NodeList Latitude = root.getElementsByTagName( "Latitude" );
                if(Latitude.getLength()>0)
                {
                    message.setLatitude( Latitude.item( 0 ).getTextContent() );                
                }
                NodeList Longitude = root.getElementsByTagName( "Longitude" );
                if(Longitude.getLength()>0)
                {
                    message.setLongitude( Longitude.item( 0 ).getTextContent() );                
                }
                NodeList Precision = root.getElementsByTagName( "Precision" );
                if(Precision.getLength()>0)
                {
                    message.setPrecision( Precision.item( 0 ).getTextContent() );                
                }
            } 
            
        } catch ( Exception e )
        {
            e.printStackTrace();
            return null;
        }
        return message;
    }
    
    /**
     * <一句话功能简述>发送文本消息
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月21日 上午11:35:12
     * @param openId            普通用户openid
     * @param content           文本消息内容
     * @param accessToken       调用接口凭证
     * @throws WeiXinException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void sendCustomerTextMsg(String openId,String content,String accessToken ) throws WeiXinException
    {
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        
        JSONObject textObject = new JSONObject();
        textObject.put( "content",        content );
        
        jsonObject.put( "msgtype",        "text" );
        jsonObject.put( "touser",         openId);
        jsonObject.put( "text",           textObject);
        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.CUSTOMER_SEND_URL+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals(json.getString( "errcode" )))
        {
            throw new WeiXinException( json.getString( "errmsg" ) );
        }
        
    }
    /**
     * <一句话功能简述>发送图片消息
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月21日 上午11:39:30
     * @param openId            普通用户openid
     * @param mediaId           发送的图片的媒体ID
     * @param accessToken       调用接口凭证
     * @throws WeiXinException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void sendCustomerImageMsg(String openId,String mediaId,String accessToken) throws WeiXinException
    {
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        
        JSONObject imageObject = new JSONObject();
        imageObject.put( "media_id",       mediaId );
        
        jsonObject.put( "msgtype",        "image" );
        jsonObject.put( "touser",         openId);
        jsonObject.put( "image",          imageObject);
        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.CUSTOMER_SEND_URL+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals(json.getString( "errcode" )))
        {
            throw new WeiXinException( json.getString( "errmsg" ) );
        } 
    }
    
    
    public static void sendCustomerVoiceMsg(String openId,String mediaId,String accessToken) throws WeiXinException
    {
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        
        JSONObject voiceObject = new JSONObject();
        voiceObject.put( "media_id",        mediaId );

        jsonObject.put( "msgtype",        "video" );
        jsonObject.put( "touser",         openId);
        jsonObject.put( "voice",          voiceObject);
        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.CUSTOMER_SEND_URL+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals(json.getString( "errcode" )))
        {
            throw new WeiXinException( "发送视频客服消息失败！" );
        }         
    }
    
    /**
     * <一句话功能简述>发送视频消息
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月21日 上午11:45:56
     * @param openId            普通用户openid
     * @param mediaId           发送的视频的媒体ID
     * @param thumbMediaId      缩略图的媒体ID
     * @param title             视频消息的标题
     * @param description       视频消息的描述
     * @param accessToken       调用接口凭证
     * @throws WeiXinException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void sendCustomerVideoMsg(String openId,String mediaId,String thumbMediaId,String title,String description,String accessToken) throws WeiXinException
    {
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        
        JSONObject imageObject = new JSONObject();
        imageObject.put( "media_id",        mediaId );
        imageObject.put( "thumb_media_id",  thumbMediaId );
        imageObject.put( "title",           title );
        imageObject.put( "description",     description );

        jsonObject.put( "msgtype",        "video" );
        jsonObject.put( "touser",         openId);
        jsonObject.put( "video",          imageObject);
        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.CUSTOMER_SEND_URL+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals(json.getString( "errcode" )))
        {
            throw new WeiXinException( "发送视频客服消息失败！" );
        }         
    }
    
    /**
     * <一句话功能简述>发送音乐消息
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月21日 上午11:48:14
     * @param openId                普通用户openid
     * @param title                 音乐标题
     * @param description           音乐描述
     * @param musicurl              音乐url
     * @param hqmusicurl            高品质音乐url
     * @param thumb_mediaId         高品质音乐链接，wifi环境优先使用该链接播放音乐
     * @param accessToken           调用接口凭证
     * @throws WeiXinException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void sendCustomerMusicMsg(String openId,String title,String description,String musicurl,String hqmusicurl,String thumbMediaId,String accessToken) throws WeiXinException
    {
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        
        JSONObject musicObject = new JSONObject();
        musicObject.put( "musicurl",        musicurl );
        musicObject.put( "hqmusicurl",      hqmusicurl );
        musicObject.put( "thumb_mediaId",   thumbMediaId );
        musicObject.put( "title",           title );
        musicObject.put( "description",     description );
        
        jsonObject.put( "msgtype",        "voice" );
        jsonObject.put( "touser",         openId);
        jsonObject.put( "music",          musicObject);
        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.CUSTOMER_SEND_URL+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals(json.getString( "errcode" )))
        {
            throw new WeiXinException( "发送音乐客服消息失败！" );
        }           
    }
    
    /**
     * <一句话功能简述>发送图文消息
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月21日 下午2:03:31
     * @param openId
     * @param news
     * @param accessToken
     * @throws WeiXinException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void sendCustomerImageTextMsg(String openId,List<News> news,String accessToken) throws WeiXinException
    {
        
        if(news.size()>10)
        {
            throw new WeiXinException( "图文消息一次最多只能发送10条！" );
        }
        
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        
        JSONObject articles = new JSONObject();
        articles.put( "articles", news );
        
        
        jsonObject.put( "msgtype",          "news" );
        jsonObject.put( "touser",           openId);
        jsonObject.put( "news",             articles);
        
        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.CUSTOMER_SEND_URL+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals(json.getString( "errcode" )))
        {
            throw new WeiXinException( "发送图文客服消息失败！" );
        }           
    }
    
    /**
     * <分组群发文本消息>分组群发文本消息
     * <功能详细描述>分组群发文本消息
     * @author  kaylves
     * @time  2015年5月21日 下午2:59:39
     * @param groupId
     * @param content
     * @param accessToken
     * @throws WeiXinException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void sendGroupTextMsg(String groupId,String content,String accessToken ) throws WeiXinException
    {
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        //文本对象 
        JSONObject textObject = new JSONObject();
        textObject.put( "content",        content );
        //过虑对象
        JSONObject filterObject = new JSONObject();
        filterObject.put( "is_to_all",  false );
        filterObject.put( "group_id",   groupId );
        
        
        jsonObject.put( "filter",           filterObject );
        jsonObject.put( "text",           textObject);
        jsonObject.put( "msgtype",        "text" );
        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.MESSAGE_SENDALL_URL+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals(json.getString( "errcode" )))
        {
            throw new WeiXinException( json.getString( "errmsg" ) );
        }
    }
    
    /**
     * <根据分组id群发图文消息>
     * <功能详细描述>
     * @param groupId
     * @param mediaId
     * @param accessToken
     * @throws WeiXinException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void sendGroupImageTextMsg(String groupId,String mediaId,String accessToken ) throws WeiXinException
    {
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        //过虑对象
        JSONObject filterObject = new JSONObject();
        filterObject.put( "is_to_all",  false );
        filterObject.put( "group_id",   groupId );
        
        JSONObject mediaIdObjedct = new JSONObject();
        mediaIdObjedct.put( "media_id", mediaId );
        
        jsonObject.put( "filter",           filterObject );
        jsonObject.put( "mpnews", mediaIdObjedct );
        jsonObject.put( "msgtype",        "mpnews" );
        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.MESSAGE_SENDALL_URL+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals(json.getString( "errcode" )))
        {
            throw new WeiXinException( json.getString( "errmsg" ) );
        }
    }
    
    /**
     * <根据openid列表群发消息>
     * <根据openid列表群发消息>
     * @author  kaylves
     * @time  2015年5月21日 下午3:09:04
     * @param openIds
     * @param content
     * @param accessToken
     * @throws WeiXinException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void sendOpenIdsTextMsg(List<String> openIds,String content,String accessToken ) throws WeiXinException
    {
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        //文本对象 
        JSONObject textObject = new JSONObject();
        textObject.put( "content",        content );
        
        
        jsonObject.put( "msgtype",        "text" );
        jsonObject.put( "touser",         openIds );
        jsonObject.put( "text",           textObject);
        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.MESSAGE_SENDALL_URL+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals(json.getString( "errcode" )))
        {
            throw new WeiXinException( json.getString( "errmsg" ) );
        }
    }
    
    /**
     * <根据openid群发图文消息>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月21日 下午3:11:03
     * @param openIds
     * @param mediaId
     * @param accessToken
     * @throws WeiXinException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void sendOpenIdsImageTextMsg(List<String> openIds,String mediaId,String accessToken ) throws WeiXinException
    {
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        //图文对象 
        JSONObject imageText = new JSONObject();
        imageText.put( "media_id",        mediaId );
        
        
        jsonObject.put( "msgtype",        "mpnews" );
        jsonObject.put( "touser",         openIds );
        jsonObject.put( "mpnews",         imageText);
        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.MESSAGE_SENDALL_URL+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals(json.getString( "errcode" )))
        {
            throw new WeiXinException( json.getString( "errmsg" ) );
        }
    }    
    
    /**
     * <根据openid列表群发声音消息>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月21日 下午3:11:54
     * @param openIds
     * @param mediaId
     * @param accessToken
     * @throws WeiXinException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void sendOpenIdsVoiceMsg(List<String> openIds,String mediaId,String accessToken ) throws WeiXinException
    {
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        //图文对象 
        JSONObject voiceObject = new JSONObject();
        voiceObject.put( "media_id",        mediaId );
        
        
        jsonObject.put( "msgtype",        "voice" );
        jsonObject.put( "touser",         openIds );
        jsonObject.put( "voice",         voiceObject);
        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.MESSAGE_SENDALL_URL+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals(json.getString( "errcode" )))
        {
            throw new WeiXinException( json.getString( "errmsg" ) );
        }
    }  
    
    public static void sendOpenIdsImageMsg(List<String> openIds,String mediaId,String accessToken ) throws WeiXinException
    {
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        //图文对象 
        JSONObject imageObject = new JSONObject();
        imageObject.put( "media_id",        mediaId );
        
        
        jsonObject.put( "msgtype",        "image" );
        jsonObject.put( "touser",         openIds );
        jsonObject.put( "image",         imageObject);
        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.MESSAGE_SENDALL_URL+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals(json.getString( "errcode" )))
        {
            throw new WeiXinException( json.getString( "errmsg" ) );
        }
    }  
    
    public static void sendOpenIdsVideoMsg(List<String> openIds,String mediaId,String title,String description,String accessToken ) throws WeiXinException
    {
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        //图文对象 
        JSONObject imageObject = new JSONObject();
        imageObject.put( "media_id",        mediaId );
        imageObject.put( "title",           title );
        imageObject.put( "description",     description );

        
        jsonObject.put( "msgtype",        "mpvideo" );
        jsonObject.put( "touser",         openIds );
        jsonObject.put( "mpvideo",         imageObject);
        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.MESSAGE_SENDALL_URL+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals(json.getString( "errcode" )))
        {
            throw new WeiXinException( json.getString( "errmsg" ) );
        }
    } 
    
    /**
     * <查看群发消息状态>
     * <查看群发消息状态>
     * @author  kaylves
     * @time  2015年5月21日 下午3:26:39
     * @param msgId
     * @param accessToken
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean getSendAllMsgStatus(String msgId,String accessToken)
    {
        Map<String,String> paraMap = new HashMap<String,String>();
        paraMap.put( "access_token", accessToken );
        String result = HttpClientUtils.get( WeiXinApplicationContext.MESSAGE_SENDALL_GET_STATUS_URL ,paraMap );
        
        
        JSONObject json = JSONObject.fromObject( result );
        
        return "SEND_SUCCESS".equals( json.getString( "msg_status") );
    }
    
    /**
     * <删除群发消息>
     * <删除群发消息>
     * @author  kaylves
     * @time  2015年5月21日 下午3:18:35
     * @param msgId
     * @param accessToken
     * @throws WeiXinException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void deleteSendAllMsg(String msgId,String accessToken ) throws WeiXinException
    {
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put( "msg_id",        Long.valueOf( msgId ) );
        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.MESSAGE_SENDALL_URL+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals(json.getString( "errcode" )))
        {
            throw new WeiXinException( json.getString( "errmsg" ) );
        }
    } 
    
    
    
    /**
     * <分组群发图片消息>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月21日 下午3:00:16
     * @param groupId
     * @param mediaId
     * @param accessToken
     * @throws WeiXinException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void sendGroupImageMsg(String groupId,String mediaId,String accessToken ) throws WeiXinException
    {
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        //图片对象 
        JSONObject textObject = new JSONObject();
        textObject.put( "media_id",        mediaId );
        //过虑对象
        JSONObject filterObject = new JSONObject();
        filterObject.put( "is_to_all",  false );
        filterObject.put( "group_id",   groupId );
        
        
        jsonObject.put( "msgtype",        "image" );
        jsonObject.put( "image",           textObject);
        jsonObject.put( "filter", filterObject );
        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.MESSAGE_SENDALL_URL+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals(json.getString( "errcode" )))
        {
            throw new WeiXinException( json.getString( "errmsg" ) );
        }
    }
    
    /**
     * <分组群发语音消息>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月21日 下午3:01:37
     * @param groupId
     * @param mediaId
     * @param accessToken
     * @throws WeiXinException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void sendGroupVoiceMsg(String groupId,String mediaId,String accessToken ) throws WeiXinException
    {
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        //图片对象 
        JSONObject textObject = new JSONObject();
        textObject.put( "media_id",        mediaId );
        //过虑对象
        JSONObject filterObject = new JSONObject();
        filterObject.put( "is_to_all",  false );
        filterObject.put( "group_id",   groupId );
        
        
        jsonObject.put( "msgtype",        "voice" );
        jsonObject.put( "voice",           textObject);
        jsonObject.put( "filter", filterObject );
        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.MESSAGE_SENDALL_URL+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals(json.getString( "errcode" )))
        {
            throw new WeiXinException( json.getString( "errmsg" ) );
        }
    }
    
    public static void sendGroupVideoMsg(String mediaId,String groupId,String accessToken) throws WeiXinException
    {
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        
        JSONObject filterObject = new JSONObject();
        filterObject.put( "is_to_all",  false );
        filterObject.put( "group_id",   groupId );
        
        
        JSONObject mediaObject = new JSONObject();
        mediaObject.put( "media_id", mediaId );
        
        jsonObject.put( "filter", filterObject );
        jsonObject.put( "mpvideo", mediaObject );
        jsonObject.put( "msgtype", "mpvideo" );
        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.MESSAGE_SENDALL_URL+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals(json.getString( "errcode" )))
        {
            throw new WeiXinException( json.getString( "errmsg" ) );
        }
    } 
    
    
    
}
