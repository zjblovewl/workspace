
package net.ycii.fc.web;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.ycii.fc.client.BusiAddServerClient;
import net.ycii.fc.entity.BeautifulCountry;
import net.ycii.fc.entity.ClassRoom;
import net.ycii.fc.entity.FourVirtues;
import net.ycii.fc.entity.GoodPerson;
import net.ycii.fc.entity.GoodThings;
import net.ycii.fc.entity.ImpressSouth;
import net.ycii.fc.entity.Notice;
import net.ycii.fc.entity.PeopleThing;
import net.ycii.fc.entity.Sign;
import net.ycii.fc.entity.ThingReport;
import net.ycii.fc.entity.VolunteerService;
import net.ycii.fc.entity.Work;
import net.ycii.fc.service.IAddrBookService;
import net.ycii.fc.service.IAreaService;
import net.ycii.fc.service.IBeautifulCountryService;
import net.ycii.fc.service.IClassRoomService;
import net.ycii.fc.service.IFourVirtuesService;
import net.ycii.fc.service.IGoodPersionService;
import net.ycii.fc.service.IGoodThingsService;
import net.ycii.fc.service.IImpressSouthService;
import net.ycii.fc.service.IIndexImageService;
import net.ycii.fc.service.IIndexService;
import net.ycii.fc.service.INoticeService;
import net.ycii.fc.service.IPeopleThingService;
import net.ycii.fc.service.ISignService;
import net.ycii.fc.service.IThingReportService;
import net.ycii.fc.service.IVolunteerService;
import net.ycii.fc.service.IWorkService;
import net.ycii.fc.util.Base64Image;

import org.apache.cxf.common.util.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tyunsoft.base.common.CacheFactory;
import com.tyunsoft.base.entity.Dept;
import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.entity.User;
import com.tyunsoft.base.service.IDeptService;
import com.tyunsoft.base.service.IUserService;
import com.tyunsoft.base.utils.DateUtil;
import com.tyunsoft.base.utils.FileUtil;
import com.tyunsoft.base.utils.IDUtil;
import com.tyunsoft.base.utils.JsonUtil;
import com.tyunsoft.base.utils.MD5;
import com.tyunsoft.base.utils.PageUtil;
import com.tyunsoft.base.utils.Pager;
import com.tyunsoft.base.utils.StringUtil;

/**
 * 手机客户端相关接口
 * 
 * @author Flyer.zuo
 * @version [版本号, 2015年2月11日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping( "mobile/free" )
public class MobileController
{

    @Autowired
    private IUserService userService;

    @Autowired
    private INoticeService noticeService;

    @Autowired
    private IAddrBookService addrBookService;

    @Autowired
    private ISignService signService;
    
    @Autowired
    private IWorkService workService;
    
    @Autowired
    private IThingReportService thingReportService;
    
    @Autowired
    private IPeopleThingService peopleThingService;
    
    @Autowired
    private IIndexService indexService;
    
    @Autowired
    private IAreaService areaService;
    
    @Autowired
    private IDeptService deptService;
    
    @Autowired
    private IIndexImageService indexImageService;
    
    @Autowired
    private IGoodPersionService goodPersonService;
    
    @Autowired
    private IFourVirtuesService fourVirtuesService;
    
    @Autowired
    private IGoodThingsService goodThingsService;
    
    @Autowired
    private IVolunteerService volunteerService;
    
    @Autowired
    private IBeautifulCountryService beautifulCountryService;
    
    @Autowired
    private IClassRoomService classRoomService;
    
    @Autowired
    private IImpressSouthService impressService;

    /**
     * 手机端用户登录
     * 
     * @param data
     *            数据
     * @param response
     *            [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping( "login.htm" )
    public void login( String data, HttpServletResponse response )
    {
        JSONObject json = JSONObject.fromObject( data );
        String loginUserId = json.getString( "loginUserId" );
        String passWord = json.getString( "loginPassword" );
        User user = userService.login( loginUserId, MD5.password( passWord ) );
        JSONObject result = new JSONObject();
        if ( null != user )
        {
            result.put( "code", "0" );
            result.put( "userId", user.getUserId() );
            result.put( "loginId", user.getUserId() );
        }
        else
        {
            result.put( "code", "1" );
            result.put( "message", "用户名或者密码不正确!" );
        }
        JsonUtil.strOut( response, result.toString() );
    }
    
    @RequestMapping("indexImage.htm")
    public void indexImage(HttpServletResponse response)
    {
        List<Object> list = indexImageService.listAll();
        JSONObject result = new JSONObject();
        result.put( "code", "0" );
        result.put( "totalCount", list.size() );
        result.put( "item", JsonUtil.list2JsonStr( list ));
        JsonUtil.strOut( response, result.toString() );
    }
    
    /**
     * 修改密码
     * @param data
     * @param response [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("changePassword.htm")
    public void changePassword(String data,HttpServletResponse response)
    {
        JSONObject json = JSONObject.fromObject( data );
        String userId = json.getString( "userId" );
        String beforePassword = json.getString( "beforePassword" );
        String newPassword = json.getString( "newPassword" );
        User user= userService.login( userId, MD5.password( beforePassword ) );
        String returnResult = "";
        boolean r = false;
        if(null != user)
        {
            r = userService.changePassword( userId, MD5.password( newPassword ) );
            returnResult = String.valueOf( r );
        }
        else
        {
            returnResult = "原密码不正确或者非法操作!";
        }
        JSONObject result = new JSONObject();
        result.put( "code", "0" );
        result.put( "result", returnResult);
        JsonUtil.strOut( response, result.toString() );
    }

    /**
     * 请求通知公告列表信息
     * 
     * @param data
     *            业务数据
     * @param response
     *            [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping( "noticeList.htm" )
    public void noticeList( String data, HttpServletResponse response )
    {
        JSONObject json = JSONObject.fromObject( data );
        String currentPage = json.getString( "currentPage" );
        String pageSizeStr = json.getString( "pageSize" );
        int pageNumber = 1;
        int pageSize = 10;
        if ( StringUtil.isNotBlank( currentPage ) )
        {
            pageNumber = Integer.parseInt( currentPage );
        }
        if ( StringUtil.isNotBlank( pageSizeStr ) )
        {
            pageSize = Integer.parseInt( pageSizeStr );
        }
        
        List<SearchCondition> conditions = new ArrayList<SearchCondition>();
        SearchCondition condition = new SearchCondition();
        condition.setColumn( "title" );
        condition.setLinkSign( "like" );
        condition.setValue( "" );
        conditions.add( condition );
        condition = new SearchCondition();
        condition.setColumn( "relUser" );
        condition.setLinkSign( "like" );
        condition.setValue( ","+json.getString( "relUser" )+"," );
        conditions.add( condition );
        
        Pager pager = noticeService.listMobile( pageNumber, pageSize, conditions );
        JSONObject result = new JSONObject();
        result.put( "code", "0" );
        result.put( "totalCount", pager.getTotal() );
        result.put( "item", JsonUtil.list2JsonStr( pager.getRows() ) );
        JsonUtil.strOut( response, result.toString() );
    }

    /**
     * 查看公共信息
     * 
     * @param data
     * @param response
     *            [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping( "noticeView.htm" )
    public void noticeView( String data, HttpServletResponse response )
    {
        JSONObject json = JSONObject.fromObject( data );
        String id = json.getString( "id" );
        Notice notice = (Notice)noticeService.queryById( id );
        JSONObject result = new JSONObject();
        if ( StringUtil.isBlank( notice.getId() ) )
        {
            result.put( "code", "1" );
            result.put( "message", "公告信息不存在!" );
        }
        else
        {
            result.put( "code", "0" );
            result.put( "notice", JsonUtil.bean2JsonStr( notice ) );
        }

        JsonUtil.bean2JsonForDate( response, result );
    }

    /**
     * 通讯录列表
     * 
     * @param data
     * @param response
     *            [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping( "addrList.htm" )
    public void addrList( String data, HttpServletResponse response )
    {
        JSONObject result = new JSONObject();
        String cache = CacheFactory.getInstance().getSetter( "is.address.book.user" );
        if(!"true".equals( cache ))
        {
            List<Object> addrs = addrBookService.listAll();
            result.put( "totalCount", addrs.size() );
            result.put( "item", addrs );
        }
        else
        {
            List<Dept> depts = deptService.children();
            User user = new User();
            user.setUserId( "" );
            user.setDeptId( "0" );
            List<?> users = userService.list( user, 1, 100000 ).getRows();
            result.put( "depts", depts );
            result.put( "users", users );
            result.put( "totalCount", users.size() );
        }
        result.put( "code", "0" );
        JsonUtil.strOut( response, result.toString() );
     
    }

    /**
     * 更新版本号
     * 
     * @param data
     *            [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping( "download.htm" )
    public void download( String data, HttpServletRequest request,
            HttpServletResponse response )
    {
        JSONObject json = JSONObject.fromObject( data );
        String version = json.getString( "version" );
        version = version.replaceAll( "[.]", "" );
        String currentVersionDesc = CacheFactory.getInstance().getSetter(
                "apk.update.desc" );
        String currentVersion = CacheFactory.getInstance().getSetter(
                "apk.update.newest.version" );
        currentVersion = currentVersion.replaceAll( "[.]", "" );
        JSONObject result = new JSONObject();
        if ( Integer.parseInt( version ) < Integer.parseInt( currentVersion ) )
        {
            result.put( "code", "0" );
            result.put( "flag", "1" );
            result.put( "downloadPath", PageUtil.getBasePath( request )
                    + "apk/wqzs.apk" );
            result.put( "desc", currentVersionDesc );
            result.put(
                    "versionNo",
                    CacheFactory.getInstance().getSetter(
                            "apk.update.newest.version" ) );
        }
        else
        {
            result.put( "code", "0" );
            result.put( "flag", "0" );
        }
        JsonUtil.strOut( response, result.toString() );
    }

    /**
     * 用户签到签退
     * 
     * @param data
     * @param response
     *            [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping( "sign.htm" )
    public void sign( String data, HttpServletRequest request,
            HttpServletResponse response )
    {
        Date signDate = new Date();
        String id = IDUtil.getUUIDStr();
        Gson gson = new Gson();
        Type types = new TypeToken<Sign>() {  
        }.getType();  
        Sign sign = gson.fromJson( data, types );
        sign.setId( id );
        sign.setSignTime(signDate);
        //保存图片
        String path = getPhysicalPath( request, "upload/sign/" );
        path += id + ".jpg";
        Base64Image.GenerateImage( sign.getImgPath(), path );
        boolean insertResult = signService.insert( sign );
        JSONObject result = new JSONObject();
        if ( insertResult )
        {
            result.put( "code", "0" );
            result.put( "message",DateUtil.getDateStr( signDate, DateUtil.TS_FORMAT ) );
            result.put( "signId", id );
        }
        else
        {
            result.put( "code", "1" );
            result.put( "message", "签到签退出错!" );
        }
        JsonUtil.strOut( response, result.toString() );
    }
    
    /**
     * 及时事件上报 问题上报
     * @param data 数据内容Json字符串
     * @param request 请求
     * @param response 响应
     */
    @RequestMapping("report.htm")
    public void report(String data,HttpServletRequest request, HttpServletResponse response)
    {
        ThingReport queryReport = new  ThingReport();
        boolean reqFlag = true;
        Gson gson = new Gson();
        Type types = new TypeToken<ThingReport>() {  
        }.getType();  
        ThingReport report = gson.fromJson( data, types );
        //JSONObject json = JSONObject.fromObject( data );
        String id = report.getId();
        if(StringUtils.isEmpty(id))
        {
            id = IDUtil.getUUIDStr();
            //report = new  ThingReport();
            report.setId( id );
        }
        else
        {
            reqFlag = false;
            queryReport = (ThingReport)thingReportService.queryById( id );
        }
        JSONObject result = new JSONObject();
        String images = null;
        try
        {
            images = FileUtil.uploadAll( request, "upload/thingreport", id );
        }catch(Exception e)
        {
            result.put( "code", "1" );
            result.put( "message", "上报事件时上传图片失败~" );
            JsonUtil.strOut( response, result.toString() );
            e.printStackTrace();
            return;
        }
        
        boolean reportResult;
        if(reqFlag)
        {
            //report.setAppealerName( json.getString( "appealerName" ) );
            //report.setAppealerPhone( json.getString( "appealerPhone" ) );
            //report.setAppealerAddress( json.getString( "appealerAddress" ) );
            //report.setReporter( json.getString( "reporter" ) );
            //report.setDescription( json.getString( "description" ) );
            report.setReportTime( new Date() );
            report.setAppealImages( images );
            //report.setSignId( json.getString( "signId" ) );
            report.setIsValid( "1" );
            report.setAppealResult( "已提交" );            
            reportResult = thingReportService.insert( report );
        }
        else
        {
            queryReport.setAppealerName( report.getAppealerName() );
            queryReport.setAppealerPhone( report.getAppealerPhone() );
            queryReport.setAppealerAddress( report.getAppealerAddress() );
            queryReport.setDescription( report.getDescription() );
            //report.setAppealerName( json.getString( "appealerName" ) );
            //report.setAppealerPhone( json.getString( "appealerPhone" ) );
            //report.setAppealerAddress( json.getString( "appealerAddress" ) );
            //report.setDescription( json.getString( "description" ) );
            queryReport.setDealSuggesttion( "" );//再次提交的时候将原来的回退意见给清空
            queryReport.setAppealImages( images );
            queryReport.setAppealResult( "已提交" );//将状态更新为提交状态
            report = queryReport;
            reportResult = thingReportService.updateById( report );
        }
        BusiAddServerClient client = new BusiAddServerClient();
        String formId = "";
        try{
            String a = client.busiAddServer(report);
            System.out.println("==================AppealerAddress>>>>>>>>>>>>>>>>>>>"+report.getAppealerAddress());
            Document doc = DocumentHelper.parseText(a.toString());
            Element root1 = doc.getRootElement();
            Element content1 = root1.element("CONTENT");
            Element form1 = content1.element("FORM");
            formId = form1.element("FORM_ID").getText(); 

            System.out.println(a);
        } catch (Exception e) {
            e.printStackTrace();

        }        
        boolean b = true;
        if(reqFlag)
        {
            report.setSlsbId( formId );
            b = thingReportService.updateById( report );
        }
        if(reportResult && b)
        {
            result.put( "code", "0" );
        }
        else
        {
            result.put( "code", "1" );
            result.put( "message", "事件上报失败~!" );
        }
        JsonUtil.strOut( response, result.toString() );
    }
    
    /**
     * 用户的及时事件上报列表
     * @param data 参数
     * @param response 响应
     */
    @RequestMapping("reportList.htm")
    public void reportList(String data,HttpServletResponse response)
    {
        JSONObject json = JSONObject.fromObject( data );
        String currentPage = json.getString( "currentPage" );
        String pageSizeStr = json.getString( "pageSize" );
        int pageNumber = 1;
        int pageSize = 10;
        if ( StringUtil.isNotBlank( currentPage ) )
        {
            pageNumber = Integer.parseInt( currentPage );
        }
        if ( StringUtil.isNotBlank( pageSizeStr ) )
        {
            pageSize = Integer.parseInt( pageSizeStr );
        }
        List<SearchCondition> conditions = new ArrayList<SearchCondition>();
        SearchCondition condition = new SearchCondition();
        condition.setColumn( "reporter" );
        condition.setLinkSign( SearchCondition.EQUALS_ONE );
        condition.setValue( json.getString( "userId" ) );
        conditions.add( condition );
        Pager pager = thingReportService.listUserReports( pageNumber, pageSize, conditions );
        JSONObject result = new JSONObject();
        result.put( "code", "0" );
        result.put( "totalCount", pager.getTotal() );
        result.put( "item", JsonUtil.list2JsonStr( pager.getRows() ) );
        JsonUtil.strOut( response, result.toString() );
    }
    
    /**
     * 用户的工作记录
     * @param data 参数
     * @param response 响应
     */
    @RequestMapping("workList.htm")
    public void workList(String data,HttpServletResponse response)
    {
        JSONObject json = JSONObject.fromObject( data );
        String currentPage = json.getString( "currentPage" );
        String pageSizeStr = json.getString( "pageSize" );
        int pageNumber = 1;
        int pageSize = 10;
        if ( StringUtil.isNotBlank( currentPage ) )
        {
            pageNumber = Integer.parseInt( currentPage );
        }
        if ( StringUtil.isNotBlank( pageSizeStr ) )
        {
            pageSize = Integer.parseInt( pageSizeStr );
        }
        List<SearchCondition> conditions = new ArrayList<SearchCondition>();
        SearchCondition condition = new SearchCondition();
        condition.setColumn( "recorder" );
        condition.setLinkSign( SearchCondition.EQUALS_ONE );
        condition.setValue( json.getString( "userId" ) );
        conditions.add( condition );
        Pager pager = workService.listUserWorks( pageNumber, pageSize, conditions );
        JSONObject result = new JSONObject();
        result.put( "code", "0" );
        result.put( "totalCount", pager.getTotal() );
        result.put( "item", JsonUtil.list2JsonStr( pager.getRows() ) );
        JsonUtil.strOut( response, result.toString() );
    }

    /**
     * 工作内容上报
     * @param data 数据
     * @param request  请求
     * @param response 响应
     */
    @RequestMapping("work.htm")
    public void work(String data,HttpServletRequest request, HttpServletResponse response)
    {
        String id = IDUtil.getUUIDStr();
        JSONObject result = new JSONObject();
        String images = null;
        try
        {
            images = FileUtil.uploadAll( request, "upload/work", id );
        }catch(Exception e)
        {
            result.put( "code", "1" );
            result.put( "message", "工作记录提交失败,上传图片时失败~!" );
            JsonUtil.strOut( response, result.toString() );
            e.printStackTrace();
            return;
        }
        Gson gson = new Gson();
        Type types = new TypeToken<Work>() {  
        }.getType();  
        Work work = gson.fromJson( data, types );
        //JSONObject json = JSONObject.fromObject( data );
        //Work work = new Work();
        work.setId( id );
        //work.setMeeting( json.getString( "meeting" ) );
        //work.setPerformance( json.getString( "performance" ) );
        //work.setRecordAddress( json.getString( "recordAddress" ) );
        //work.setRecorder( json.getString( "recorder" ) );
        work.setRecordImages( images );
        work.setRecordTime( new Date() );
        //work.setSignId( json.getString( "signId" ) );
        
        boolean workResult = workService.insert( work );
        if(workResult)
        {
            result.put( "code", "0" );
        }
        else
        {
            result.put( "code", "1" );
            result.put( "message", "工作记录提交失败~!" );
        }
        JsonUtil.strOut( response, result.toString() );
    }
    
    /**
     * 提交民生实事信息
     * @param data 提交数据
     * @param request
     * @param response [参数说明]
     */
    @RequestMapping("peoplething.htm")
    public void peoplething(String data,HttpServletRequest request, HttpServletResponse response)
    {
        String id = IDUtil.getUUIDStr();
        JSONObject result = new JSONObject();
        String images = null;
        try
        {
            if(StringUtil.isNotBlank( images )){
                images = FileUtil.uploadAll( request, "upload/peoplething", id );
            }
        }catch(Exception e)
        {
            result.put( "code", "1" );
            result.put( "message", "民生实事提交图片信息时出现问题~!" );
            JsonUtil.strOut( response, result.toString() );
            e.printStackTrace();
            return;
        }
        JSONObject json = JSONObject.fromObject( data );
        PeopleThing pt = new PeopleThing();
        pt.setId( id );
        pt.setContent( json.getString( "content" ) );
        pt.setPhotos( images );
        pt.setProblem( json.getString( "problem" ) );
        pt.setProgress( json.getString( "progress" ) );
        pt.setCreateUser( json.getString( "user_id" ) );
        pt.setHandAddress( json.getString( "handAddress" ) );
        String handAddress = json.getString( "handAddress" );
        if(handAddress.indexOf( "," ) != -1)
        {
            String[] has = handAddress.split( "," );
            pt.setTown( has[0] );
            pt.setVillage( has[1] );
        }
        pt.setCreateDate( new Date() );
        
        boolean executeResult = peopleThingService.insert( pt );
        if(executeResult)
        {
            result.put( "code", "0" );
        }
        else
        {
            result.put( "code", "1" );
            result.put( "message", "民生实事提交失败~!" );
        }
        JsonUtil.strOut( response, result.toString() );
    }
    
    @RequestMapping("position.htm")
    public void position(String data,HttpServletRequest request, HttpServletResponse response)
    {
        JSONObject json = JSONObject.fromObject( data );
        indexService.update( json.getString( "position" ), json.getString( "user_id" ) );
        JSONObject result = new JSONObject();
        result.put( "code", "0" );
        result.put( "notices", noticeService.queryUserNotices( json.getString( "user_id" ) ) );
        JsonUtil.strOut( response, result.toString() );
    }
    
    @RequestMapping("userArea.htm")
    public void userArea(String data,HttpServletRequest request, HttpServletResponse response)
    {
        JSONObject json = JSONObject.fromObject( data );
        String userId = json.getString( "user_id" );
        List<Map<String,String>> areas = areaService.queryUserAreas( userId );
        JSONObject result = new JSONObject();
        result.put( "code", "0" );
        result.put( "areas", areas );
        JsonUtil.strOut( response, result.toString() );
    }
    
    private static String getPhysicalPath( HttpServletRequest request,
            String savePath )
    {
        String path = FileUtil.class.getClassLoader().getResource( "/" )
                .getPath();
        path = path.replaceAll( "WEB-INF/classes/", savePath );
        File f = new File( path );
        if ( !f.exists() )
        {
            f.mkdirs();
        }
        return path;
    }
    
    @RequestMapping("goodBeahaviorView")
    public void goodBehaviorView(String data,HttpServletResponse response){
        /**
         * type 类型
         * 1、汶南好人
         * 2、凡人好事
         * 3、四德榜
         * 4、志愿服务
         */
        JSONObject json = JSONObject.fromObject( data );
        Integer type = json.getInt( "type" );
        JSONObject result = new JSONObject();
        if(type==1){
            List<Object> objList = goodPersonService.queryIdByOrderbyCreateTime();
            if(objList.size()>0){
                GoodPerson goodPerson = (GoodPerson)objList.get( 0 );
                if ( StringUtil.isBlank( goodPerson.getId() ) )
                {
                    result.put( "code", "1" );
                    result.put( "message", "汶南好人信息不存在!" );
                }
                else
                {
                    result.put( "code", "0" );
                    result.put( "detail", JsonUtil.bean2JsonStr( goodPerson ) );
                }
            }else{
                result.put( "code", "1" );
                result.put( "message", "汶南好人信息不存在!" );
            }
            
        }
        else if(type==2){
            List<Object> objList = goodThingsService.queryIdByOrderbyCreateTime();
            if(objList.size()>0){
                GoodThings goodThings = (GoodThings)objList.get( 0 );
                if ( StringUtil.isBlank( goodThings.getId() ) )
                {
                    result.put( "code", "1" );
                    result.put( "message", "凡人好事信息不存在!" );
                }
                else
                {
                    result.put( "code", "0" );
                    result.put( "detail", JsonUtil.bean2JsonStr( goodThings ) );
                }
            }else{
                result.put( "code", "1" );
                result.put( "message", "凡人好事信息不存在!" );
            }
        }
        else if(type==3){
            List<Object> objList = fourVirtuesService.queryIdByOrderbyCreateTime();
            if(objList.size()>0){
                FourVirtues fourVirtues = (FourVirtues)objList.get( 0 );
                if ( StringUtil.isBlank( fourVirtues.getId() ) )
                {
                    result.put( "code", "1" );
                    result.put( "message", "四德榜信息不存在!" );
                }
                else
                {
                    result.put( "code", "0" );
                    result.put( "detail", JsonUtil.bean2JsonStr( fourVirtues ) );
                }
            }else{
                result.put( "code", "1" );
                result.put( "message", "四德榜信息不存在!" );
            }
        }
        else if(type==4){
            List<Object> objList = volunteerService.queryIdByOrderbyCreateTime();
            if(objList.size()>0){
                VolunteerService vs = (VolunteerService)objList.get( 0 );
                if ( StringUtil.isBlank( vs.getId() ) )
                {
                    result.put( "code", "1" );
                    result.put( "message", "志愿服务信息不存在!" );
                }
                else
                {
                    result.put( "code", "0" );
                    result.put( "detail", JsonUtil.bean2JsonStr( vs ) );
                }
            }else{
                result.put( "code", "1" );
                result.put( "message", "志愿服务信息不存在!" );
            }
        }
        

        JsonUtil.bean2JsonForDate( response, result );
    }
    
    /**
     * 美丽乡村列表集合
     * 
     * @param data
     *            业务数据
     * @param response
     *            [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping( "beautifulCountryList.htm" )
    public void beautifulCountryList( String data, HttpServletResponse response )
    {
        JSONObject json = JSONObject.fromObject( data );
        String currentPage = json.getString( "currentPage" );
        String pageSizeStr = json.getString( "pageSize" );
        int pageNumber = 1;
        int pageSize = 10;
        if ( StringUtil.isNotBlank( currentPage ) )
        {
            pageNumber = Integer.parseInt( currentPage );
        }
        if ( StringUtil.isNotBlank( pageSizeStr ) )
        {
            pageSize = Integer.parseInt( pageSizeStr );
        }
        
        List<SearchCondition> conditions = new ArrayList<SearchCondition>();
        SearchCondition condition = new SearchCondition();
        condition.setColumn( "title" );
        condition.setLinkSign( "like" );
        condition.setValue( "" );
        conditions.add( condition );
        
        Pager pager = beautifulCountryService.listMobile( pageNumber, pageSize, conditions );
        JSONObject result = new JSONObject();
        result.put( "code", "0" );
        result.put( "totalCount", pager.getTotal() );
        result.put( "item", JsonUtil.list2JsonStr( pager.getRows() ) );
        JsonUtil.strOut( response, result.toString() );
    }
    
    /**
     * 查看美丽乡村详情
     * 
     * @param data
     * @param response
     *            [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping( "beautifulCountryView.htm" )
    public void beautifulCountryView( String data, HttpServletResponse response )
    {
        JSONObject json = JSONObject.fromObject( data );
        String id = json.getString( "id" );
        BeautifulCountry vs = (BeautifulCountry)beautifulCountryService.queryById( id );
        JSONObject result = new JSONObject();
        if ( StringUtil.isBlank( vs.getId() ) )
        {
            result.put( "code", "1" );
            result.put( "message", "美丽乡村信息不存在!" );
        }
        else
        {
            result.put( "code", "0" );
            result.put( "detail", JsonUtil.bean2JsonStr( vs ) );
        }

        JsonUtil.bean2JsonForDate( response, result );
    }
    
    /**
     * 青云山大讲堂集合
     * 
     * @param data
     *            业务数据
     * @param response
     *            [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping( "classRoomList.htm" )
    public void classRoomList( String data, HttpServletResponse response )
    {
        JSONObject json = JSONObject.fromObject( data );
        String currentPage = json.getString( "currentPage" );
        String pageSizeStr = json.getString( "pageSize" );
        int pageNumber = 1;
        int pageSize = 10;
        if ( StringUtil.isNotBlank( currentPage ) )
        {
            pageNumber = Integer.parseInt( currentPage );
        }
        if ( StringUtil.isNotBlank( pageSizeStr ) )
        {
            pageSize = Integer.parseInt( pageSizeStr );
        }
        
        List<SearchCondition> conditions = new ArrayList<SearchCondition>();
        SearchCondition condition = new SearchCondition();
        condition.setColumn( "title" );
        condition.setLinkSign( "like" );
        condition.setValue( "" );
        conditions.add( condition );
        
        Pager pager = classRoomService.listMobile( pageNumber, pageSize, conditions );
        JSONObject result = new JSONObject();
        result.put( "code", "0" );
        result.put( "totalCount", pager.getTotal() );
        result.put( "item", JsonUtil.list2JsonStr( pager.getRows() ) );
        JsonUtil.strOut( response, result.toString() );
    }
    
    /**
     * 查看美丽乡村详情
     * 
     * @param data
     * @param response
     *            [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping( "classRoomView.htm" )
    public void classRoomView( String data, HttpServletResponse response )
    {
        JSONObject json = JSONObject.fromObject( data );
        String id = json.getString( "id" );
        ClassRoom vs = (ClassRoom)classRoomService.queryById( id );
        JSONObject result = new JSONObject();
        if ( StringUtil.isBlank( vs.getId() ) )
        {
            result.put( "code", "1" );
            result.put( "message", "青云山大讲堂信息不存在!" );
        }
        else
        {
            result.put( "code", "0" );
            result.put( "detail", JsonUtil.bean2JsonStr( vs ) );
        }

        JsonUtil.bean2JsonForDate( response, result );
    }
    
    /**
     * 印象汶南集合
     * 
     * @param data
     *            业务数据
     * @param response
     *            [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping( "impressSouthList.htm" )
    public void impressSouthList( String data, HttpServletResponse response )
    {
        JSONObject json = JSONObject.fromObject( data );
        String currentPage = json.getString( "currentPage" );
        String pageSizeStr = json.getString( "pageSize" );
        int pageNumber = 1;
        int pageSize = 10;
        if ( StringUtil.isNotBlank( currentPage ) )
        {
            pageNumber = Integer.parseInt( currentPage );
        }
        if ( StringUtil.isNotBlank( pageSizeStr ) )
        {
            pageSize = Integer.parseInt( pageSizeStr );
        }
        
        List<SearchCondition> conditions = new ArrayList<SearchCondition>();
        SearchCondition condition = new SearchCondition();
        condition.setColumn( "title" );
        condition.setLinkSign( "like" );
        condition.setValue( "" );
        conditions.add( condition );
        
        Pager pager = impressService.listMobile( pageNumber, pageSize, conditions );
        JSONObject result = new JSONObject();
        result.put( "code", "0" );
        result.put( "totalCount", pager.getTotal() );
        result.put( "item", JsonUtil.list2JsonStr( pager.getRows() ) );
        JsonUtil.strOut( response, result.toString() );
    }
    
    /**
     * 查看美丽乡村详情
     * 
     * @param data
     * @param response
     *            [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping( "impressSouthView.htm" )
    public void impressSouthView( String data, HttpServletResponse response )
    {
        JSONObject json = JSONObject.fromObject( data );
        String id = json.getString( "id" );
        ImpressSouth vs = (ImpressSouth)impressService.queryById( id );
        JSONObject result = new JSONObject();
        if ( StringUtil.isBlank( vs.getId() ) )
        {
            result.put( "code", "1" );
            result.put( "message", "印象汶南信息不存在!" );
        }
        else
        {
            result.put( "code", "0" );
            result.put( "detail", JsonUtil.bean2JsonStr( vs ) );
        }

        JsonUtil.bean2JsonForDate( response, result );
    }
}
