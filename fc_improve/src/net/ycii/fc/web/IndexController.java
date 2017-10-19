package net.ycii.fc.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ycii.api.MessageApi;
import net.ycii.context.MessageAutoReplayUtils;
import net.ycii.entity.WeiXinMessage;
import net.ycii.fc.service.IIndexService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tyunsoft.base.utils.JsonUtil;
import com.tyunsoft.base.utils.SHA1;

@Controller
@RequestMapping("index")
public class IndexController
{
    
    @Autowired
    private IIndexService indexService;
    
    private static final Logger logger = Logger.getLogger( IndexController.class );
    
    @RequestMapping("portal.htm")
    public ModelAndView portal()
    {
        
        return new ModelAndView("fc/baiduMap");
    }
    
    @RequestMapping("online.htm")
    public void online(HttpServletResponse response)
    {
        List<Map<String,String>> users = indexService.onlineUsers();
        JsonUtil.list2Json( response, users );
    }
    
    /**
     * <基本配置中配置token>
     * <功能详细描述>
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/call.htm",method = RequestMethod.GET)
    public void configToken(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String TOKEN = "fdafsdlilfqwer1234";
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");

        String[] str = { TOKEN, timestamp, nonce };
        Arrays.sort(str); // 字典序排序
        String bigStr = str[0] + str[1] + str[2];
        // SHA1加密
        String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();

        // 确认请求来至微信
        if (digest.equals(signature)) {
            response.getWriter().print(echostr);
        }
    }
    
    /**
     * <基本配置中配置token>
     * <功能详细描述>
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/call.htm",method = RequestMethod.POST)
    public void replayMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try
        {
            String contextType = request.getContentType();
            logger.info( "数据类型是:" + contextType );
            Scanner scanner = new Scanner( request.getInputStream(), "utf-8" );
            StringBuffer xml = new StringBuffer( "" );
            while ( scanner.hasNext() )
            {
                xml.append( scanner.nextLine() );
            }
            scanner.close();
            
            logger.info( "接口回调处理xml:"+xml );
            
            WeiXinMessage msg = MessageApi.extractWeiXinMessage( xml.toString() );
            
            logger.info( "msg:"+msg );
            
            int currentTime = (int) System.currentTimeMillis()/1000;
            
            String str = MessageAutoReplayUtils.replayTextXml( msg.getFromUserName(), msg.getToUserName(),currentTime , "哈咯，你成功了！！！娃哈哈" );
            
            response.setCharacterEncoding( "utf-8" );
            response.getWriter().write( str );
//            response.getWriter().write( );
            response.getWriter().close();  
            
            
            
            
            
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年7月9日 下午4:55:04
     * @param message
     * @param autoReplay [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
//    private String responseAutoMessage( TWxMessage message,TWxMessageAutoReplay autoReplay )
//    {
//        /**
//                回复内容类型
//            1：文字
//            2：图片
//            3：语音
//            4：视频
//            5：图文
//            */
//        //响应普通文字
//        if(autoReplay.getContentType().intValue()==1)
//        {
//            return MessageAutoReplayUtils.replayTextXml( message.getFromUserName(), message.getToUserName(), SystemUtils.getCurrentTime(), autoReplay.getAnswerText() );
//        }
//        //素材图片
//        else if(autoReplay.getContentType().intValue()==2){
//            return MessageAutoReplayUtils.replayImageXml( message.getFromUserName(), message.getToUserName(), SystemUtils.getCurrentTime(), autoReplay.getMaterialId());
//        }
//        //素材语音
//        else if(autoReplay.getContentType().intValue()==3){
//            return MessageAutoReplayUtils.replayVoiceXml( message.getFromUserName(), message.getToUserName(), SystemUtils.getCurrentTime(), autoReplay.getMaterialId());
//            
//        }
//        //素材视频
//        else if(autoReplay.getContentType().intValue()==4){
//            TWxMaterial material = weiXinMaterialService.getMaterial( autoReplay.getMaterialId() );
//            return MessageAutoReplayUtils.replayVideoXml(  message.getFromUserName(), message.getToUserName(), SystemUtils.getCurrentTime(),autoReplay.getMaterialId(), material.getTitle(),material.getDescription() );
//        }
//        //素材图文
//        else if(autoReplay.getContentType().intValue()==5){
//            //图文响应
//            TWxMaterial material = weiXinMaterialService.getMaterial( autoReplay.getMaterialId() );
//            return MessageAutoReplayUtils.replayNewsXml( message.getFromUserName(), message.getToUserName(), SystemUtils.getCurrentTime(), material.getNews() );
//        }
//        return "";
//    }
    
//    public static String resolveMessageType(String xml)
//    {
//        
////        
////        
////        TWxMessage message = new TWxMessage();
////        String[] ignore = {"createTime"};
////        BeanUtils.copyProperties( msg, message, ignore );
////        message.setCreateTime( new Date(Long.valueOf( msg.getCreateTime() )*1000) );
////        return saveWxMessage( message );
////        
////        
////        //响应字符串
////        String result = "";
////        
////        if(message == null )
////        {
////            throw new BusinessException( Message.SERVER_BUSY );
////        }
////        
////        TWxPublicAccount account = publicAccountService.getWxPublicAccoountByWxId( message.getToUserName() );
////        
////        if(account == null )
////        {
////            throw new BusinessException("开发者账号不正确！");
////        }
////        
////        //事件消息处理
////        if(StringUtil.isNotBlank( message.getEvent() ))
////        {
////            result = processWeixinInverseEventMsg( message, account );
////        }
////        else
////        {
////            result = processWeiXinInverseOtherMsg( message, account );
////        }
////        
////        return result;
//    }
    
}
