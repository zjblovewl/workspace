package net.ycii.fc.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ycii.fc.entity.Work;
import net.ycii.fc.service.IWorkService;
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
 *工作记录对应的controller控制类
 */
@Controller
@RequestMapping("work")
public class WorkController {
    
        @Autowired
        private IWorkService workService;
        
        @InitBinder
        protected void initBinder(WebDataBinder binder) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        }

		/**
		  * 跳转到工作记录列表页面
		  *@param request 请求
		  *@return 转到跳转后的list页面
		  */
        @RequestMapping("forwardList.htm")
        public ModelAndView forwardList(HttpServletRequest request)
        {
            request.setAttribute( "functionStr", FunctionBuilder.build() );
            return new ModelAndView("fc/work_list");
        }
        
        @RequestMapping("list.htm")
        public void list(HttpServletRequest request,HttpServletResponse response)
        {
            List<SearchCondition> conditions = new ArrayList<SearchCondition>();
            SearchCondition condition = null;
            condition = new SearchCondition();
            condition.setColumn( "dept" );
            condition.setLinkSign( "like" );
            condition.setValue( request.getParameter( "dept" ) );
            conditions.add( condition );
            
            condition = new SearchCondition();
            condition.setColumn("user");      
            condition.setLinkSign("like");
            condition.setValue(request.getParameter("user"));
            conditions.add(condition);
            
            condition = new SearchCondition();
            condition.setColumn( "startDate" );
            condition.setLinkSign("betweendatetime");
            condition.setStartValue( request.getParameter( "startDate" ) );
            condition.setEndValue( request.getParameter( "endDate" ) );
            conditions.add( condition );
		
            PageEntity pageEntity = PageUtil.getPageNumAndSize( request );
            JsonUtil.bean2JsonForDate( response, workService.list( pageEntity.getPageNumber(), pageEntity.getPageSize(),conditions ) );
        }
        
        @RequestMapping("export.htm")
        public void export(HttpServletRequest request,HttpServletResponse response)
        {
            List<SearchCondition> conditions = new ArrayList<SearchCondition>();
            SearchCondition condition = null;
            condition = new SearchCondition();
            condition.setColumn( "dept" );
            condition.setLinkSign( "like" );
            condition.setValue( request.getParameter( "dept" ) );
            conditions.add( condition );
            
            condition = new SearchCondition();
            condition.setColumn("user");      
            condition.setLinkSign("like");
            condition.setValue(request.getParameter("user"));
            conditions.add(condition);
            
            condition = new SearchCondition();
            condition.setColumn( "startDate" );
            condition.setLinkSign("betweendatetime");
            condition.setStartValue( request.getParameter( "startDate" ) );
            condition.setEndValue( request.getParameter( "endDate" ) );
            conditions.add( condition );
        
            PageEntity pageEntity = PageUtil.getPageNumAndSize( request );
            List list = workService.list( pageEntity.getPageNumber(), 1000000,conditions ).getRows();
            Map map = new HashMap();
            map.put("workList",list);
            ExportExcel exportExcel = new ExportExcel();
            exportExcel.export("工作记录报表", map, "workList.xls",response);
        }
        
        @RequestMapping("listAll.htm")
        public void listAll(HttpServletRequest request, HttpServletResponse response)
        {
            JsonUtil.list2JsonForDate(response, workService.listAll());
        }
        
        @RequestMapping("toSave.htm")
        public ModelAndView toSave(HttpServletRequest request,String id)
        {
            String action = "add";
            Work work = new Work();
            if(null != id && !"".equals(id))
            {
                Object obj = workService.queryById( id );
                if(obj instanceof Work)
                {
                    work = (Work) obj;
                }
                action = "edit";
            }
            else
            {
                work.setId(IDUtil.getUUIDStr());
            }
            request.setAttribute( "bean", work );
            request.setAttribute("action", action);
            return new ModelAndView("fc/work_save");
        }
        
        @RequestMapping("save.htm")
        public void save(Work work, HttpServletResponse response, HttpServletRequest request, String action)
        {
            boolean result = false;
            if("add".equals(action))
            {
                result = workService.insert( work );
            }
            else
            {
                result = workService.updateById( work );
            }
            
            JsonUtil.boolOut( response, result );
        }
        
        @RequestMapping("delete.htm")
        public void delete(String id,HttpServletResponse response)
        {
            boolean result = workService.deleteById( id );
            JsonUtil.boolOut( response, result );
        }
        
        @RequestMapping("view.htm")
        public ModelAndView view(String id,HttpServletRequest request)
        {
            Object work = workService.queryById(id);
            request.setAttribute("bean", work);
            String[] images = ((Work)work).getRecordImages().split( "[|]" );
            request.setAttribute( "images", images );
            return new ModelAndView("fc/work_view");
        }
    
}
