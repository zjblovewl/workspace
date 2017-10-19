
package com.ycii.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import net.sf.json.JSONObject;
import net.ycii.api.MaterialApi;
import net.ycii.api.MessageApi;
import net.ycii.context.MessageAutoReplayUtils;
import net.ycii.entity.MediaFileType;
import net.ycii.entity.MediaUploadResult;
import net.ycii.entity.News;
import net.ycii.entity.WeiXinMessage;
import net.ycii.exception.WeiXinException;

import org.junit.Test;

public class MessageApiTest extends BaseBean
{
    public static final String OPEN_ID = "ol_bNwgzy3Z0UYpjfyN01Lt1QUBQ";
    
    String xml = "<xml> " + "<ToUserName><![CDATA[gh_78c405e06efa]]></ToUserName>"
            + "<FromUserName><![CDATA[ol_bNwgzy3Z0UYpjfyN01Lt1QUBQ]]></FromUserName>"
            + "<CreateTime>1348831860</CreateTime>"
            + "<MsgType><![CDATA[text]]></MsgType>"
            + "<Content><![CDATA[this is a test]]></Content>"
            + "<MsgId>1234567890123456</MsgId> " + "</xml>";
    
    String unXml = "<xml>"
            + "<ToUserName><![CDATA[gh_78c405e06efa]]></ToUserName>"
            + "<FromUserName><![CDATA[o0MlxuH_VABbHTLxSkmVY9kEJfms]]></FromUserName>"
            + "<CreateTime>1432203911</CreateTime>"
            + "<MsgType><![CDATA[event]]></MsgType>"
            + "<Event><![CDATA[unsubscribe]]></Event>"
            + "<EventKey><![CDATA[]]></EventKey></xml>";
    @Test
    public void extractTest()
    {
        WeiXinMessage msg = MessageApi.extractWeiXinMessage( xml );
        JSONObject object = JSONObject.fromObject( msg );
        System.out.println( object.toString() );
    }
    
    /**
     * <文本消息测试>
     * <测试文本消息>
     * @author  kaylves
     * @time  2015年5月21日 下午2:18:15 [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @Test
    public void sendTextMsgTest()
    {
        try
        {
            MessageApi.sendCustomerTextMsg( OPEN_ID, "嗨！斗战胜费！", token_key );
        } catch ( WeiXinException e )
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void sendGroupImageTextMsgTest(){
        try
        {
            MessageApi.sendGroupImageTextMsg( "0", "WUExX2Xi0dD30tS_XtEbR3HQLeDbhkD-tueEiAD5MGE", "Cjh8hclDttNf1XF_m9n39vTazbYSSi6TfH0cJXTc-_SrI-mbW1IbMP11WrHkLA_4sA0eYw5-vLOGLtdIwtZZwsQ07a92FGJsCrMrxHSBabw" );
        } catch ( WeiXinException e )
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void sendOpenIdsImageTextMsgTest(){
        try
        {
            List<String> openIds = new ArrayList<String>();
            openIds.add( "oKeoWuJei6k1WD28DDdwHgMkwKtg" );
            openIds.add( "oKeoWuAQj44WPsIf-PeWrga9pe-o" );
            MessageApi.sendOpenIdsImageTextMsg( openIds, "WUExX2Xi0dD30tS_XtEbR88WeMyvBW6hbXuAanBSH88", "Cjh8hclDttNf1XF_m9n39vTazbYSSi6TfH0cJXTc-_SrI-mbW1IbMP11WrHkLA_4sA0eYw5-vLOGLtdIwtZZwsQ07a92FGJsCrMrxHSBabw" );
        } catch ( WeiXinException e )
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void sendGroupTextMsgTest()
    {
        try
        {
            String groupId = "153";
            String content = "aaaa";
            String accessToken = "F6Gdz-XRRCD7v6quWacqR_Bh7739AAiP8lVxFMXwVWQaRgca_eaZY8O58-P6YEdRND9Jt8y1jLKTu7T4caEBzComMmz9LDvB7n9Tbtck03k";
            MessageApi.sendGroupTextMsg( groupId, content, accessToken );
        } catch ( WeiXinException e )
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void sendGroupVoiceMsgTest()
    {
        try
        {
            String accessToken = "RCaN_l78-mRMRJwMzRl45jrroRbftKkOwzVAxM814yh-2zLBh_eXdafU8bs-rUCSwNNFqJH1tqdAfHdWXOQKipbSHhJlnvRnz-jLf0EckQ0";
            File newFile = new File("f:/6010.wav");
            MediaUploadResult result =  MaterialApi.createForeverMaterial( MediaFileType.voice, newFile,null,null, accessToken );
            System.out.println(result.getMedia_id());
            Assert.assertNotNull( result );
            
            String groupId = "100";
            MessageApi.sendGroupVoiceMsg( groupId, result.getMedia_id(), accessToken );
        } catch ( WeiXinException e )
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void sendGroupVideoMsgTest()
    {
        try
        {
            String accessToken = "8AH31iFlL5veBUWqKqNhDNaWiDVJHXtIKFFXxkregAflPR8lO2srC3oYClGVcn-AEhrerHFznB_0zH2Q0ccYxEW48_sNH-fvnZBVrKQh2os";
            File newFile = new File("f:/视频2.mp4");
            MediaUploadResult result =  MaterialApi.createForeverMaterial( MediaFileType.video, newFile,"标题","描述描述啊", accessToken );
            System.out.println(result.getMedia_id());
            Assert.assertNotNull( result );
            
            String groupId = "100";
            MessageApi.sendGroupVideoMsg( result.getMedia_id(), groupId, accessToken );
        } catch ( WeiXinException e )
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void sendImageMsgTest()
    {
        try
        {
            MessageApi.sendCustomerImageMsg( OPEN_ID, "c_5xPAgDrtU1n0l7zupKGviOSBqqsm3ooRUCBnoTn-3ieo1uSmSq9am9dJOpgQcW", token_key );
        } catch ( WeiXinException e )
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void sendImageTextTest()
    {
        try
        {
            News news = new News();
            news.setDescription( "hello" );
            news.setPicurl( "http://mmbiz.qpic.cn/mmbiz/1VDaIfxCeJnBHo9NQ99caFVHroiaXxqrC0mIFXX5PrB21z5P07Xia0fTDyjbibW9C2dN5OSBoKc4vtBFjWV6qQqrg/0" );
            news.setTitle( "kaylves" );
            news.setUrl( "http://mp.weixin.qq.com/s?__biz=MzAxMDMzMjkyMQ==&mid=208101066&idx=1&sn=c0f53e3d483c3cc1e67902a532cb41bb#rd" );
            List<News> list = new ArrayList<News>();
            list.add( news );
            MessageApi.sendCustomerImageTextMsg( OPEN_ID, list , token_key );
        } catch ( WeiXinException e )
        {
            e.printStackTrace();
        }
    }
    
    /**
     * <一句话功能简述>发送语音测试
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月21日 下午2:42:45 [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @Test
    public void sendVoiceTest()
    {
        try
        {
            MessageApi.sendCustomerVoiceMsg( OPEN_ID, "8_u44VCznFNuiUj3XZiZq-5SW9Qi2CKwcCVXzvOmUbiuu8ATV-uxzeL23J63kxP2",token_key );
        } catch ( WeiXinException e )
        {
            e.printStackTrace();
        }        
    }
    
    /**
     * <一句话功能简述>发送视频测试
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月21日 下午2:48:29 [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @Test
    public void sendVideoTest()
    {
        try
        {
            MessageApi.sendCustomerVideoMsg( OPEN_ID, "4ABnf0PFvzcdfdzrkyHOlLN4FQgz5t7X7B52CL_8TyJUbE868tObNWKeYMkwOB5q", "es1QC6mba082mYtRs8JP6uKTJcLZ7bNAil0ry-t0hERagA3m-1iPdtj9MT1207Rd", "帅哥", "描述", token_key );
        } catch ( WeiXinException e )
        {
            e.printStackTrace();
        }        
    }
    
    @Test
    public void sendAllMsgTest()
    {
        try
        {
            MessageApi.sendGroupTextMsg( "0", "我只为自己代言！我叫Kaylves!", token_key );
        } catch ( WeiXinException e )
        {
            e.printStackTrace();
        }        
    }

    @Test
    public void sendOpenIdsTextMsg()
    {
        try
        {
            List<String> openIds = new ArrayList<String>();
            openIds.add( "o0MlxuFdmfllHhkcah_550H24ijY" );
            MessageApi.sendOpenIdsTextMsg( openIds, "我叫Kaylves!我只为自己代言！", token_key );
        } catch ( WeiXinException e )
        {
            e.printStackTrace();
        }        
    }
    
    @Test
    public void sendOpenIdsVideoMsgTest(){
        try
        {
            String token_key ="KiGIR6U3gQfl6gvujrKrjIImHvgPKI60_bVR0mnMyJSgDSwgmADzKqoi4lPtkz_vBt-tSxgWo14SZI8LlcMZpATGXJh7AG8wDGoTp870p9c";
            File newFile = new File("f:/视频2.mp4");
            MediaUploadResult result =  MaterialApi.createForeverMaterial( MediaFileType.video, newFile,"标题","描述描述啊", token_key );
            Assert.assertNotNull( result );
            
            List<String> openIds = new ArrayList<String>();
            openIds.add( "oKeoWuHJitDA68X6IV_G1NkdvpJc" );
            openIds.add( "oKeoWuDC2OIKcrVLuAic5S9rgWyA" );
            MessageApi.sendOpenIdsVideoMsg( openIds, result.getMedia_id(), "标题", "介绍", token_key );
        } catch ( Exception e )
        {
            
        }
    }
    
    @Test
    public void replayTextXmlTest(){
        System.out.println(MessageAutoReplayUtils.replayTextXml( "", "FromUserName", 1234, "content" ));
    }
    
    @Test
    public void replayImageXmlTest(){
        String result = MessageAutoReplayUtils.replayImageXml( "fromuser", "kaylves", 112, "1234asdfadsf" );
        System.out.println(result);
    }
    
    @Test
    public void replayVideoXmlTest(){
        String result = MessageAutoReplayUtils.replayVideoXml("toUserName2","FromUser3Name",124455667,"mediaId2a","title2","description3");
        System.out.println(result);
    }
    
}
