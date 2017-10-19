package net.ycii.fc.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ycii.fc.service.IReportService;
import net.ycii.fc.util.ExportExcel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tyunsoft.base.entity.PageEntity;
import com.tyunsoft.base.utils.JsonUtil;
import com.tyunsoft.base.utils.PageUtil;
import com.tyunsoft.base.utils.Pager;
import com.tyunsoft.base.utils.StringUtil;

@Controller
@RequestMapping("report")
public class ReportController
{
    
    @Autowired
    private IReportService reportService;
    
    @RequestMapping("list.htm")
    public ModelAndView list(HttpServletRequest request)
    {
        return new ModelAndView("fc/report_list");
    }
    
    @RequestMapping("queryList.htm")
    public void queryList(HttpServletRequest request, HttpServletResponse response)
    {
        String userName = request.getParameter( "userName" );
        userName = userName == null ? "" : userName;
        String dept = request.getParameter( "dept" );
        dept = dept == null ? "" : dept;
        String startDate = request.getParameter( "startDate" );
        startDate = StringUtil.isBlank( startDate ) ? "1900-01-01 11:11:11" : startDate;
        String endDate = request.getParameter( "endDate" );
        endDate = StringUtil.isBlank( endDate ) ? "2999-01-01 11:11:11" : endDate;
        PageEntity pageEntity = PageUtil.getPageNumAndSize( request );
        Pager pager = reportService.queryReports( userName ,dept, startDate, endDate, pageEntity.getPageNumber(), pageEntity.getPageSize() );
        JsonUtil.bean2JsonForDate( response, pager );
    }
    
    @RequestMapping("view.htm")
    public ModelAndView view(HttpServletRequest request)
    {
        String signId = request.getParameter( "signId" );
        Map<String,Object> map = reportService.query( signId );
        request.setAttribute( "map", map );
        if(null != map)
        {
            String[] appealImages = String.valueOf( map.get( "appeal_images" ) ).split( "[|]" );
            request.setAttribute( "appealImages", appealImages );
            String[] recordImages = String.valueOf( map.get( "record_images" ) ).split( "[|]" );
            request.setAttribute( "recordImages", recordImages );
        }
        return new ModelAndView("fc/report_view");
    }

    
    @RequestMapping("exportRecord.htm")
    public String exportRecord(HttpServletRequest request,HttpServletResponse response) throws Exception 
    {
        String userName = request.getParameter( "userName" );
        userName = userName == null ? "" : userName;
        String dept = request.getParameter( "dept" );
        dept = dept == null ? "" : dept;
        String startDate = request.getParameter( "startDate" );
        startDate = StringUtil.isBlank( startDate ) ? "1900-01-01 11:11:11" : startDate;
        String endDate = request.getParameter( "endDate" );
        endDate = StringUtil.isBlank( endDate ) ? "2999-01-01 11:11:11" : endDate;
        List<Map<String,String>> lists = reportService.queryRecordsWithCondition(userName,dept,startDate,endDate);
        
        Map map = new HashMap();
        map.put("reportList",lists);
        ExportExcel exportExcel = new ExportExcel();
        exportExcel.export("数据统计报表", map, "reportExport.xls",response);
        return null;            
    }    
}
