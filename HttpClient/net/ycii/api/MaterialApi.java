package net.ycii.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.ycii.context.WeiXinApplicationContext;
import net.ycii.entity.MediaFileType;
import net.ycii.entity.MediaNews;
import net.ycii.entity.MediaResult;
import net.ycii.entity.MediaResultList;
import net.ycii.entity.MediaUploadResult;
import net.ycii.entity.MediaUsedInfo;
import net.ycii.exception.WeiXinException;
import net.ycii.util.HttpClientUtils;


/**
 * <微信素材接口Api>
 * <p><功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年5月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MaterialApi
{
    
    /**
     * <新增临时素材>
     * <功能详细描述>
     * 图片（image）: 1M，支持JPG格式
                语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式
                视频（video）：10MB，支持MP4格式
                缩略图（thumb）：64KB，支持JPG格式
     * @author  kaylves
     * @time  2015年5月22日 上午9:21:43
     * @param meida
     * @param updateFile
     * @param accessToken [参数说明]
     * 
     * @return MediaUploadResult [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static MediaUploadResult createTempMaterial(MediaFileType meida ,File mediaFile,String accessToken)
    {
        Map<String,String> paraMap = new HashMap<String,String>();
        paraMap.put( "type", meida.toString() );
        
        Map<String,File> files = new HashMap<String,File>();
        files.put( "media",  mediaFile);
        
        String result = HttpClientUtils.multipartPost( WeiXinApplicationContext.MEDIA_UPLOAD_URL+"?access_token="+accessToken,files,paraMap );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(json.containsKey( "errmsg" ))
        {
            return null;
        }
        return (MediaUploadResult)JSONObject.toBean( json,MediaUploadResult.class );
    }
    
    /**
     * <获取临时素材>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月22日 上午11:07:40
     * @param mediaId
     * @param accessToken
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getTempMaterial(String mediaId,String accessToken)
    {
        Map<String,String> paraMap = new LinkedHashMap<String,String>();
        paraMap.put( "access_token", accessToken );
        paraMap.put( "media_id",     mediaId );

        String result = HttpClientUtils.get( WeiXinApplicationContext.MEDIA_GET_URL,paraMap );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(json.containsKey( "errmsg" ))
        {
            return null;
        }
        return result;
    }
    
    /**
     * <新增永久性其它素材>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月22日 上午11:30:48
     * @param meida
     * @param mediaFile
     * @param videoTitle
     * @param introduction
     * @param accessToken
     * @return [参数说明]
     * 
     * @return MediaUploadResult [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static MediaUploadResult createForeverMaterial(MediaFileType meida ,File mediaFile,String videoTitle,String introduction,String accessToken)
    {
        Map<String,String> paraMap = new HashMap<String,String>();
        paraMap.put( "type", meida.toString() );
        
        if(meida==MediaFileType.video)
        {
            JSONObject jsonObject = new JSONObject();
            
            jsonObject.put( "title",           videoTitle );
            jsonObject.put( "introduction",    introduction );
            paraMap.put( "description",        jsonObject.toString() );
        }
        
        
        
        Map<String,File> files = new HashMap<String,File>();
        files.put( "media",  mediaFile);
        
        String result = HttpClientUtils.multipartPost( WeiXinApplicationContext.MEDIA_ADD_MATERIAL_URL+"?access_token="+accessToken,files,paraMap );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(json.containsKey( "errmsg" ))
        {
            return null;
        }
        return (MediaUploadResult)JSONObject.toBean( json,MediaUploadResult.class );
    }
    
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月22日 下午4:17:31
     * @param meida
     * @param mediaFile
     * @param accessToken
     * @return [参数说明]
     * 
     * @return MediaUploadResult [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static MediaUploadResult createForeverImageTextMaterial(List<MediaNews> articles,String accessToken)
    {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put( "articles", articles );
        
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.MEDIA_ADD_NEWS_URL+"?access_token="+accessToken,jsonObject.toString());
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(json.containsKey( "errmsg" ))
        {
            return null;
        }
        return (MediaUploadResult)JSONObject.toBean( json,MediaUploadResult.class );
    }
    
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月27日 上午11:18:48
     * @param media_id      要修改的图文消息的id
     * @param index         要更新的文章在图文消息中的位置（多图文消息时，此字段才有意义），第一篇为0
     * @param articles      
     * @param accessToken
     * @throws WeiXinException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void updateForeverImageTextMaterial(String media_id,int index,MediaNews articles,String accessToken) throws WeiXinException
    {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put( "media_id", media_id );
        jsonObject.put( "index",    index );
        jsonObject.put( "articles", articles );
        
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.MEDIA_UPDATE_NEWS_URL+"?access_token="+accessToken,jsonObject.toString());
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals(json.getString( "errcode" )))
        {
            throw new WeiXinException( json.getString( "errmsg" ) );
        }
    }
    
    /**
     * <删除永久素材>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月22日 上午11:38:35
     * @param mediaId
     * @param accessToken
     * @throws WeiXinException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void deleteForeverMaterial(String mediaId,String accessToken ) throws WeiXinException
    {
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        
        
        jsonObject.put( "media_id",         mediaId);
        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.MEDIA_DEL_MATERIAL_URL+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(!"0".equals(json.getString( "errcode" )))
        {
            throw new WeiXinException( json.getString( "errmsg" ) );
        }
        
    }
    
    /**
     * <获取素材总数>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月22日 上午11:49:19
     * @param accessToken
     * @return [参数说明]
     * 
     * @return MediaUsedInfo [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static MediaUsedInfo getMediaUsedInfo(String accessToken)
    {
        Map<String,String> paraMap = new LinkedHashMap<String,String>();
        paraMap.put( "access_token", accessToken );

        String result = HttpClientUtils.get( WeiXinApplicationContext.MEDIA_GET_MEDIA_COUNT,paraMap );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(json.containsKey( "errmsg" ))
        {
            return null;
        }
        return (MediaUsedInfo)JSONObject.toBean( json, MediaUsedInfo.class );
    }
    
    /**
     * <获取图文信息列表>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月22日 下午4:15:31
     * @param type
     * @param offset
     * @param count
     * @param accessToken
     * @return [参数说明]
     * 
     * @return MediaResultList [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static MediaResultList getMediaList(MediaFileType type,Integer offset,Integer count,String accessToken)
    {
        //创建请求参数体
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put( "type",         type.toString());
        jsonObject.put( "offset",       offset);
        jsonObject.put( "count",        count);

        
        //发送请求
        String result = HttpClientUtils.postJSON( WeiXinApplicationContext.BATCHGET_MATERIAL+"?access_token="+accessToken, jsonObject.toString() );
        
        JSONObject json = JSONObject.fromObject( result );
        
        if(json.containsKey( "errcode" ))
        {
            return null;
        } 
        Map<String,Class<?>> map = new HashMap<String, Class<?>>();
        map.put( "item", MediaResult.class);
        map.put( "news_item", MediaNews.class);
        return (MediaResultList)JSONObject.toBean( json, MediaResultList.class, map);
    }
    
    /**
     * <获取临时素材>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月22日 上午11:07:40
     * @param mediaId
     * @param accessToken
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void getVoiceMaterial(String mediaId,String accessToken)
    {
        Map<String,String> paraMap = new LinkedHashMap<String,String>();
        paraMap.put( "access_token", accessToken );
        paraMap.put( "media_id",     mediaId );

        InputStream result = HttpClientUtils.getInputStream( WeiXinApplicationContext.MEDIA_JSSDK_GET_URL,paraMap );
        
        InputStream inputStream = result;
        byte[] data = new byte[1024];
        int len = 0;
        FileOutputStream fileOutputStream = null;
        try {
        fileOutputStream = new FileOutputStream("D:\\test1.speex");
        while ((len = inputStream.read(data)) != -1) {
            fileOutputStream.write(data, 0, len);
        }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
