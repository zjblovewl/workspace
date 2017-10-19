
package net.ycii.fc.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ycii.fc.entity.Sign;
import net.ycii.fc.service.ISignService;
import net.ycii.fc.util.ExportExcel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tyunsoft.base.common.FunctionBuilder;
import com.tyunsoft.base.entity.PageEntity;
import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.utils.IDUtil;
import com.tyunsoft.base.utils.JsonUtil;
import com.tyunsoft.base.utils.PageUtil;

/**
 * 签到签退信息对应的controller控制类
 */
@Controller
@RequestMapping( "sign" )
public class SignController
{

    @Autowired
    private ISignService signService;

    @InitBinder
    protected void initBinder( WebDataBinder binder )
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss" );
        binder.registerCustomEditor( Date.class, new CustomDateEditor(
                dateFormat, true ) );
    }

    /**
     * 跳转到签到签退信息列表页面
     * 
     * @param request
     *            请求
     * @return 转到跳转后的list页面
     */
    @RequestMapping( "forwardList.htm" )
    public ModelAndView forwardList( HttpServletRequest request )
    {
        request.setAttribute( "functionStr", FunctionBuilder.build() );
        return new ModelAndView( "fc/sign_list" );
    }

    @RequestMapping( "list.htm" )
    public void list( HttpServletRequest request, HttpServletResponse response )
    {
        List<SearchCondition> conditions = new ArrayList<SearchCondition>();
        SearchCondition condition = null;
        condition = new SearchCondition();
        condition.setColumn( "user_id" );
        condition.setLinkSign( "like" );
        condition.setValue( request.getParameter( "userId" ) );
        conditions.add( condition );

        condition = new SearchCondition();
        condition.setColumn( "dept" );
        condition.setLinkSign( "like" );
        condition.setValue( request.getParameter( "dept" ) );
        conditions.add( condition );

        condition = new SearchCondition();
        condition.setColumn( "startDate" );
        condition.setLinkSign("betweendatetime");
        condition.setStartValue( request.getParameter( "startDate" ) );
        condition.setEndValue( request.getParameter( "endDate" ) );
        conditions.add( condition );
        
        condition = new SearchCondition();
        condition.setColumn( "type" );
        condition.setLinkSign( "=" );
        condition.setValue( request.getParameter( "type" ) );
        conditions.add( condition );

        PageEntity pageEntity = PageUtil.getPageNumAndSize( request );
        JsonUtil.bean2JsonForDate(
                response,
                signService.list( pageEntity.getPageNumber(),
                        pageEntity.getPageSize(), conditions ) );
    }

    @RequestMapping( "listAll.htm" )
    public void listAll( HttpServletRequest request,
            HttpServletResponse response )
    {
        JsonUtil.list2JsonForDate( response, signService.listAll() );
    }

    @RequestMapping( "toSave.htm" )
    public ModelAndView toSave( HttpServletRequest request, String id )
    {
        String action = "add";
        Sign sign = new Sign();
        if ( null != id && !"".equals( id ) )
        {
            Object obj = signService.queryById( id );
            if ( obj instanceof Sign )
            {
                sign = (Sign)obj;
            }
            action = "edit";
        }
        else
        {
            sign.setId( IDUtil.getUUIDStr() );
        }
        request.setAttribute( "bean", sign );
        request.setAttribute( "action", action );
        return new ModelAndView( "fc/sign_save" );
    }

    @RequestMapping( "save.htm" )
    public void save( Sign sign, HttpServletResponse response,
            HttpServletRequest request, String action )
    {
        boolean result = false;
        if ( "add".equals( action ) )
        {
            result = signService.insert( sign );
        }
        else
        {
            result = signService.updateById( sign );
        }

        JsonUtil.boolOut( response, result );
    }

    @RequestMapping( "delete.htm" )
    public void delete( String id, HttpServletResponse response )
    {
        boolean result = signService.deleteById( id );
        JsonUtil.boolOut( response, result );
    }

    @RequestMapping( "view.htm" )
    public ModelAndView view( String id, HttpServletRequest request )
    {
        Object sign = signService.queryById( id );
        request.setAttribute( "bean", sign );
        return new ModelAndView( "fc/sign_view" );
    }

    @RequestMapping( "exportRecord.htm" )
    public String exportRecord( HttpServletRequest request,
            HttpServletResponse response ) throws Exception
    {
        List<SearchCondition> conditions = new ArrayList<SearchCondition>();
        SearchCondition condition = null;
        condition = new SearchCondition();
        condition.setColumn( "user_id" );
        condition.setLinkSign( "like" );
        condition.setValue( request.getParameter( "userId" ) );
        conditions.add( condition );
        
        condition = new SearchCondition();
        condition.setColumn( "dept" );
        condition.setLinkSign("like");
        condition.setValue( request.getParameter( "dept" ) );
        conditions.add( condition );
        
        condition = new SearchCondition();
        condition.setColumn( "sign_time" );
        condition.setLinkSign("betweendatetime");
        condition.setStartValue( request.getParameter( "startDate" ) );
        condition.setEndValue( request.getParameter( "endDate" ) );
        conditions.add( condition );
        
        condition = new SearchCondition();
        condition.setColumn( "type" );
        condition.setLinkSign( "=" );
        condition.setValue( request.getParameter( "type" ) );
        conditions.add( condition );
        
        

        List lists = signService.queryRecordsWithCondition( conditions );

        Map map = new HashMap();
        map.put( "signList", lists );
        ExportExcel exportExcel = new ExportExcel();
        exportExcel.export( "用户签到签退记录", map, "signListExport.xls",
                response );
        return null;
    }

}
