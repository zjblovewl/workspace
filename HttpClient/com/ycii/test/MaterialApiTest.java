package com.ycii.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import net.sf.json.JSONObject;
import net.ycii.api.MaterialApi;
import net.ycii.entity.MediaFileType;
import net.ycii.entity.MediaNews;
import net.ycii.entity.MediaResultList;
import net.ycii.entity.MediaUploadResult;
import net.ycii.entity.MediaUsedInfo;
import net.ycii.exception.WeiXinException;

import org.junit.Test;

/**
 * <一句话功能简述>
 * <p><功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年5月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MaterialApiTest extends BaseBean
{
    
    @Test
    public void createTempMaterialTest(){
        File newFile = new File("d:/测试图片.jpg");
        System.out.println(newFile.length());
        MediaUploadResult result =  MaterialApi.createTempMaterial( MediaFileType.image, newFile, token_key );
        Assert.assertNotNull( result );
    }

    @Test
    public void getTempMaterialTest(){
        String result =  MaterialApi.getTempMaterial( "IZLEnCKQz1ZZtJ9e3mEucE4qKS-a8ZTY48GzGaGBcPVgfOPUekiDwBrPVV4n3GNZ", token_key );
        Assert.assertNotNull( result );
    }
    
    @Test
    public void getVoiceMaterialTest(){
        MaterialApi.getVoiceMaterial( "tvf1vSXq-o0atwvYjrn6ZD-KRpxi5lmkSHkRu_Tl8hpqKd5IWwMFSDILLoI_l36t", token_key );
    }
    
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月22日 上午11:33:05 [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @Test
    public void createForeverMaterialTest(){
        File newFile = new File("g:/视频1.mp4");
        MediaUploadResult result =  MaterialApi.createForeverMaterial( MediaFileType.video, newFile,"标题","描述描述啊", token_key );
        Assert.assertNotNull( result );
    }
    
    @Test
    public void createForeverImageTextMaterialTest(){
        MediaNews m1 = new MediaNews();
        m1.setAuthor( "ycii" );
        m1.setDigest( "摘要" );
        m1.setThumb_media_id( "SXrT_vCfG1haj5HHp2M4ukDxw3RPo5O2d75eFWJTVQk" );
        m1.setContent( "这是内容" );
        m1.setTitle( "标题" );
        m1.setContent_source_url(null );
        List<MediaNews> articles = new ArrayList<MediaNews>();
        articles.add( m1 );
        MediaUploadResult result =  MaterialApi.createForeverImageTextMaterial(articles, token_key );
        Assert.assertNotNull( result );
    }
    
    @Test
    public void deleteForeverMaterialTest()
    {
        try
        {
            MaterialApi.deleteForeverMaterial( "cd-kFVlL16MXye81yCql15p7EVX-SF7kqkW_JlPouQw", token_key );
        } catch ( WeiXinException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test
    public void getMediaUsedInfoTest(){
        MediaUsedInfo result =  MaterialApi.getMediaUsedInfo( token_key );
        Assert.assertNotNull( result );
    }
    
    
    @Test
    public void getMediaListTest(){
        MediaResultList result =  MaterialApi.getMediaList( MediaFileType.news,0,1,token_key );
        System.out.println(result.getItem().get( 0 ).getContent().getNews_item().get( 0 ).getUrl());
        System.out.println("result:"+JSONObject.fromObject( result ));
    }
}
