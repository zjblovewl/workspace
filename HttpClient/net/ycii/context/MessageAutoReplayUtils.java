package net.ycii.context;

import java.util.List;

import net.ycii.entity.MediaNews;
import static net.ycii.context.WeiXinApplicationContext.nullToEmpty;

/**
 * 自动回复消息xml
 * <一句话功能简述>
 * <p><功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年7月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MessageAutoReplayUtils
{
    /**
     * <自动回复文本xml字符串>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年7月8日 下午4:51:15
     * @param toUserName    接收方帐号（收到的OpenID）
     * @param FromUserName  开发者微信号
     * @param createTime    创建时间（整型）
     * @param content       文本内容
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String replayTextXml(String toUserName,String FromUserName,Integer createTime,String content){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>\n");
        sb.append("<ToUserName><![CDATA[").append( nullToEmpty(toUserName) ).append( "]]></ToUserName>\n");
        sb.append("<FromUserName><![CDATA[").append( nullToEmpty(FromUserName) ).append( "]]></FromUserName>\n");
        sb.append("<CreateTime>").append( nullToEmpty(createTime) ).append( "</CreateTime>\n");
        sb.append("<MsgType><![CDATA[text]]></MsgType>\n");
        sb.append("<Content><![CDATA[").append( content ).append( "]]></Content>");
        sb.append("</xml>");
        return sb.toString();
    }
    /**
     * <自动回复图文xml>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年7月8日 下午4:51:36
     * @param toUserName        接收方帐号（收到的OpenID）
     * @param FromUserName      开发者微信号
     * @param createTime        消息创建时间 （整型）
     * @param news  title:标题,digest：描述,show_cover_pic：小图标链接,url:图文链接
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String replayNewsXml(String toUserName,String FromUserName,Integer createTime,List<MediaNews> news){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>\n");
        sb.append("<ToUserName><![CDATA[").append( toUserName ).append( "]]></ToUserName>\n");
        sb.append("<FromUserName><![CDATA[").append( FromUserName ).append( "]]></FromUserName>\n");
        sb.append("<CreateTime>").append( createTime ).append( "</CreateTime>\n");
        sb.append("<MsgType><![CDATA[news]]></MsgType>\n");
        sb.append("<ArticleCount><![CDATA[").append( news.size() ).append( "]]></ArticleCount>\n");
        sb.append("<Articles>\n");
        for(MediaNews temp:news){
            sb.append("<item>\n");
            sb.append("<Title><![CDATA[").append( temp.getTitle() ).append( "]]></Title>\n");
            sb.append("<Description><![CDATA[").append( nullToEmpty(temp.getDigest()) ).append( "]]></Description>\n");
            sb.append("<PicUrl><![CDATA[").append( nullToEmpty(temp.getTempImageUrl()) ).append( "]]></PicUrl>\n");
            sb.append("<Url><![CDATA[").append( nullToEmpty(temp.getUrl()) ).append( "]]></Url>\n");
            sb.append("</item>\n");
        }
        sb.append("</Articles>\n");
        sb.append("</xml>");
        return sb.toString();
    }
    
    /**
     * <自动回复图片素材xml>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年7月8日 下午4:51:51
     * @param toUserName        接收方帐号（收到的OpenID）
     * @param FromUserName      开发者微信号
     * @param createTime        消息创建时间 （整型）
     * @param mediaId           通过素材管理接口上传多媒体文件，得到的id
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String replayImageXml(String toUserName,String FromUserName,Integer createTime,String mediaId){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        sb.append("<ToUserName><![CDATA[").append( toUserName ).append( "]]></ToUserName>\n");
        sb.append("<FromUserName><![CDATA[").append( FromUserName ).append( "]]></FromUserName>\n");
        sb.append("<CreateTime>").append( createTime ).append( "</CreateTime>\n");
        sb.append("<MsgType><![CDATA[Image]]></MsgType>");
        sb.append("<Image>");
        sb.append("<MediaId><![CDATA[").append( mediaId ).append( "]]></MediaId>");
        sb.append("</Image>");
        sb.append("</xml>");
        return sb.toString();
    }
    
    /**
     * <回复音乐xml>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年7月8日 下午4:57:11
     * @param toUserName        接收方帐号（收到的OpenID）
     * @param FromUserName      开发者微信号
     * @param createTime        消息创建时间 （整型）
     * @param title             音乐标题
     * @param description       音乐描述
     * @param musicUrl          音乐链接
     * @param hQMusicUrl        高质量音乐链接，WIFI环境优先使用该链接播放音乐 
     * @param thumbMediaId      缩略图的媒体id，通过素材管理接口上传多媒体文件，得到的id
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String replayMusicXml(String toUserName,String FromUserName,Integer createTime,
            String title,String description,String musicUrl,String hQMusicUrl,String thumbMediaId){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        sb.append("<ToUserName><![CDATA[").append( toUserName ).append( "]]></ToUserName>\n");
        sb.append("<FromUserName><![CDATA[").append( FromUserName ).append( "]]></FromUserName>\n");
        sb.append("<CreateTime>").append( createTime ).append( "</CreateTime>\n");
        sb.append("<MsgType><![CDATA[music]]></MsgType>");
        sb.append("<Music>");
        sb.append("<Title>").append( title ).append( "</Title>\n");
        sb.append("<Description>").append( description ).append( "</Description>\n");
        sb.append("<MusicUrl>").append( nullToEmpty(musicUrl) ).append( "</MusicUrl>\n");
        sb.append("<HQMusicUrl>").append( nullToEmpty(hQMusicUrl) ).append( "</HQMusicUrl>\n");
        sb.append("<ThumbMediaId>").append( nullToEmpty(thumbMediaId) ).append( "</ThumbMediaId>\n");
        sb.append("</Music>");
        sb.append("</xml>");
        return sb.toString();
    }
    
    /**
     * <自动回复语音xml>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年7月8日 下午4:52:14
     * @param toUserName    接收方帐号（收到的OpenID）
     * @param FromUserName  开发者微信号
     * @param createTime    消息的创建时间戳（整型）
     * @param mediaId       通过素材管理接口上传多媒体文件，得到的id
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String replayVoiceXml(String toUserName,String FromUserName,Integer createTime,String mediaId){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        sb.append("<ToUserName><![CDATA[").append( toUserName ).append( "]]></ToUserName>\n");
        sb.append("<FromUserName><![CDATA[").append( FromUserName ).append( "]]></FromUserName>\n");
        sb.append("<CreateTime>").append( createTime ).append( "</CreateTime>\n");
        sb.append("<MsgType><![CDATA[voice]]></MsgType>");
        sb.append("<Voice>");
        sb.append("<MediaId><![CDATA[").append( mediaId ).append( "]]></MediaId>");
        sb.append("</Voice>");
        sb.append("</xml>");
        return sb.toString();
    }
    
    /**
     * <自动回复视频xml>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年7月8日 下午4:52:31
     * @param toUserName        接收方帐号（收到的OpenID）
     * @param FromUserName      开发者微信号
     * @param createTime        消息创建时间戳 （整型）
     * @param mediaId           通过素材管理接口上传多媒体文件，得到的id
     * @param title             视频消息的标题
     * @param description       视频消息的描述
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String replayVideoXml(String toUserName,String FromUserName,Integer createTime,String mediaId,String title,String description){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        sb.append("<ToUserName><![CDATA[").append( toUserName ).append( "]]></ToUserName>\n");
        sb.append("<FromUserName><![CDATA[").append( FromUserName ).append( "]]></FromUserName>\n");
        sb.append("<CreateTime>").append( createTime ).append( "</CreateTime>\n");
        sb.append("<MsgType><![CDATA[video]]></MsgType>");
        sb.append("<Video>");
        sb.append("<MediaId><![CDATA[").append( mediaId ).append( "]]></MediaId>");
        sb.append("<Title><![CDATA[").append( nullToEmpty(title) ).append( "]]></Title>");
        sb.append("<Description><![CDATA[").append( nullToEmpty(description) ).append( "]]></Description>");
        sb.append("</Video>");
        sb.append("</xml>");
        return sb.toString();
    }
    
    
    
}
