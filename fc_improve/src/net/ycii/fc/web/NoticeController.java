
package net.ycii.fc.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ycii.fc.entity.Notice;
import net.ycii.fc.entity.TreeNode;
import net.ycii.fc.service.ICommonService;
import net.ycii.fc.service.INoticeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tyunsoft.base.common.FunctionBuilder;
import com.tyunsoft.base.common.SessionHelper;
import com.tyunsoft.base.entity.PageEntity;
import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.utils.FileUtil;
import com.tyunsoft.base.utils.IDUtil;
import com.tyunsoft.base.utils.JsonUtil;
import com.tyunsoft.base.utils.PageUtil;

/**
 * 通知公告对应的controller控制类
 */
@Controller
@RequestMapping( "notice" )
public class NoticeController
{

    @Autowired
    private INoticeService noticeService;

    @Autowired
    ICommonService commonService;

    @InitBinder
    protected void initBinder( WebDataBinder binder )
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss" );
        binder.registerCustomEditor( Date.class, new CustomDateEditor(
                dateFormat, true ) );
    }

    /**
     * 跳转到通知公告列表页面
     * 
     * @param request
     *            请求
     * @return 转到跳转后的list页面
     */
    @RequestMapping( "forwardList.htm" )
    public ModelAndView forwardList( HttpServletRequest request )
    {
        request.setAttribute( "functionStr", FunctionBuilder.build() );
        return new ModelAndView( "fc/notice_list" );
    }

    @RequestMapping( "list.htm" )
    public void list( HttpServletRequest request, HttpServletResponse response )
    {
        List<SearchCondition> conditions = new ArrayList<SearchCondition>();
        SearchCondition condition = null;
        condition = new SearchCondition();
        condition.setColumn( "title" );
        condition.setLinkSign( "like" );
        condition.setValue( request.getParameter( "title" ) );
        conditions.add( condition );

        PageEntity pageEntity = PageUtil.getPageNumAndSize( request );
        JsonUtil.bean2JsonForDate(
                response,
                noticeService.list( pageEntity.getPageNumber(),
                        pageEntity.getPageSize(), conditions ) );
    }

    @RequestMapping( "listAll.htm" )
    public void listAll( HttpServletRequest request,
            HttpServletResponse response )
    {
        JsonUtil.list2JsonForDate( response, noticeService.listAll() );
    }

    @RequestMapping( "toSave.htm" )
    public ModelAndView toSave( HttpServletRequest request, String id )
    {
        String action = "add";
        Notice notice = new Notice();
        if ( null != id && !"".equals( id ) )
        {
            Object obj = noticeService.queryById( id );
            if ( obj instanceof Notice )
            {
                notice = (Notice)obj;
            }
            action = "edit";
        }
        else
        {
            notice.setId( IDUtil.getUUIDStr() );
        }
        List<TreeNode> nodes = commonService.getdeptUserTree();
        String jsonStr = JsonUtil.list2Json( nodes );
        request.setAttribute( "jsonStr", jsonStr.replace( "\"", "'" ) );
        request.setAttribute( "bean", notice );
        request.setAttribute( "action", action );
        return new ModelAndView( "fc/notice_save" );
    }

    @RequestMapping( "save.htm" )
    public void save( Notice notice, HttpServletResponse response,
            HttpServletRequest request, String action )
    {
        notice.setPubImage( FileUtil.upload( request, "upload/notice",
                notice.getId(), "pubImageFile" ) );
        notice.setNoticeFile( FileUtil.upload( request, "upload/noticefile",
                notice.getId(), "noticeFileFile" ) );
        boolean result = false;

        String relUser = notice.getRelUser();

        noticeService.batchInsertUsers( notice.getId(), relUser );
        
        if ( "add".equals( action ) )
        {
            notice.setCreateUser( SessionHelper.getUserId() );
            result = noticeService.insert( notice );
        }
        else
        {
            result = noticeService.updateById( notice );
        }

        JsonUtil.boolOut( response, result );
    }

    @RequestMapping( "delete.htm" )
    public void delete( String id, HttpServletResponse response )
    {
        boolean result = noticeService.deleteById( id );
        JsonUtil.boolOut( response, result );
    }

    @RequestMapping( "view.htm" )
    public ModelAndView view( String id, HttpServletRequest request )
    {
        Object notice = noticeService.queryById( id );
        request.setAttribute( "bean", notice );
        return new ModelAndView( "fc/notice_view" );
    }

}
