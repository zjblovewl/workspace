
package net.ycii.fc.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ycii.fc.entity.ThingReport;
import net.ycii.fc.service.IThingReportService;
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
 * 事件上报对应的controller控制类
 */
@Controller
@RequestMapping( "thingreport" )
public class ThingReportController
{

    @Autowired
    private IThingReportService thingReportService;

    @InitBinder
    protected void initBinder( WebDataBinder binder )
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss" );
        binder.registerCustomEditor( Date.class, new CustomDateEditor(
                dateFormat, true ) );
    }

    /**
     * 跳转到事件上报列表页面
     * 
     * @param request
     *            请求
     * @return 转到跳转后的list页面
     */
    @RequestMapping( "forwardList.htm" )
    public ModelAndView forwardList( HttpServletRequest request )
    {
        request.setAttribute( "functionStr", FunctionBuilder.build() );
        return new ModelAndView( "fc/thingreport_list" );
    }

    @RequestMapping( "list.htm" )
    public void list( HttpServletRequest request, HttpServletResponse response )
    {
        List<SearchCondition> conditions = new ArrayList<SearchCondition>();
        SearchCondition condition = null;
        condition = new SearchCondition();
        condition.setColumn( "dept" );
        condition.setLinkSign( "like" );
        condition.setValue( request.getParameter( "dept" ) );
        conditions.add( condition );

        condition = new SearchCondition();
        condition.setColumn( "appealer_name" );
        condition.setLinkSign( "like" );
        condition.setValue( request.getParameter( "appealerName" ) );
        conditions.add( condition );

        condition = new SearchCondition();
        condition.setColumn( "appeal_result" );
        condition.setLinkSign( "like" );
        condition.setValue( request.getParameter( "appealResult" ) );
        conditions.add( condition );

        condition = new SearchCondition();
        condition.setColumn( "startDate" );
        condition.setLinkSign( "betweendatetime" );
        condition.setStartValue( request.getParameter( "startDate" ) );
        condition.setEndValue( request.getParameter( "endDate" ) );
        conditions.add( condition );

        PageEntity pageEntity = PageUtil.getPageNumAndSize( request );
        JsonUtil.bean2JsonForDate(
                response,
                thingReportService.list( pageEntity.getPageNumber(),
                        pageEntity.getPageSize(), conditions ) );
    }

    @RequestMapping( "listAll.htm" )
    public void listAll( HttpServletRequest request,
            HttpServletResponse response )
    {
        JsonUtil.list2JsonForDate( response, thingReportService.listAll() );
    }

    @RequestMapping( "toSave.htm" )
    public ModelAndView toSave( HttpServletRequest request, String id )
    {
        String action = "add";
        ThingReport thingReport = new ThingReport();
        if ( null != id && !"".equals( id ) )
        {
            Object obj = thingReportService.queryById( id );
            if ( obj instanceof ThingReport )
            {
                thingReport = (ThingReport)obj;
            }
            action = "edit";
        }
        else
        {
            thingReport.setId( IDUtil.getUUIDStr() );
        }
        request.setAttribute( "bean", thingReport );
        request.setAttribute( "action", action );
        return new ModelAndView( "fc/thingreport_save" );
    }

    @RequestMapping( "save.htm" )
    public void save( ThingReport thingReport, HttpServletResponse response,
            HttpServletRequest request, String action )
    {
        boolean result = false;
        if ( "add".equals( action ) )
        {
            result = thingReportService.insert( thingReport );
        }
        else
        {
            result = thingReportService.updateById( thingReport );
        }

        JsonUtil.boolOut( response, result );
    }

    @RequestMapping( "delete.htm" )
    public void delete( String id, HttpServletResponse response )
    {
        boolean result = thingReportService.deleteById( id );
        JsonUtil.boolOut( response, result );
    }

    @RequestMapping( "view.htm" )
    public ModelAndView view( String id, HttpServletRequest request )
    {
        Object thingReport = thingReportService.queryById( id );
        request.setAttribute( "bean", thingReport );
        String[] images = ((ThingReport)thingReport).getAppealImages().split(
                "[|]" );
        request.setAttribute( "images", images );
        return new ModelAndView( "fc/thingreport_view" );
    }

    @RequestMapping("export.htm")
    public void export( HttpServletRequest request , HttpServletResponse response)
    {
        List<SearchCondition> conditions = new ArrayList<SearchCondition>();
        SearchCondition condition = null;
        condition = new SearchCondition();
        condition.setColumn( "dept" );
        condition.setLinkSign( "like" );
        condition.setValue( request.getParameter( "dept" ) );
        conditions.add( condition );

        condition = new SearchCondition();
        condition.setColumn( "appealer_name" );
        condition.setLinkSign( "like" );
        condition.setValue( request.getParameter( "appealerName" ) );
        conditions.add( condition );

        condition = new SearchCondition();
        condition.setColumn( "appeal_result" );
        condition.setLinkSign( "like" );
        condition.setValue( request.getParameter( "appealResult" ) );
        conditions.add( condition );

        condition = new SearchCondition();
        condition.setColumn( "startDate" );
        condition.setLinkSign( "betweendatetime" );
        condition.setStartValue( request.getParameter( "startDate" ) );
        condition.setEndValue( request.getParameter( "endDate" ) );
        conditions.add( condition );

        PageEntity pageEntity = PageUtil.getPageNumAndSize( request );
        List list = thingReportService.list( 1,
                100000, conditions ).getRows();
        Map map = new HashMap();
        map.put("reportList",list);
        ExportExcel exportExcel = new ExportExcel();
        exportExcel.export("问题办理报表", map, "thingReportExport.xls",response);
    }

}
