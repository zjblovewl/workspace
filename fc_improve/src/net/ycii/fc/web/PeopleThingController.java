package net.ycii.fc.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ycii.fc.entity.PeopleThing;
import net.ycii.fc.service.IPeopleThingService;
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
import com.tyunsoft.base.utils.IDUtil;
import com.tyunsoft.base.utils.JsonUtil;
import com.tyunsoft.base.utils.PageUtil;
import com.tyunsoft.base.utils.StringUtil;

/**
 *民生实事对应的controller控制类
 */
@Controller
@RequestMapping("peoplething")
public class PeopleThingController {
    
        @Autowired
        private IPeopleThingService peopleThingService;
        
        @InitBinder
        protected void initBinder(WebDataBinder binder) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        }

		/**
		  * 跳转到民生实事列表页面
		  *@param request 请求
		  *@return 转到跳转后的list页面
		  */
        @RequestMapping("forwardList.htm")
        public ModelAndView forwardList(HttpServletRequest request)
        {
            request.setAttribute( "functionStr", FunctionBuilder.build() );
            return new ModelAndView("fc/peoplething_list");
        }
        
        @RequestMapping("list.htm")
        public void list(HttpServletRequest request,HttpServletResponse response)
        {
        	String userName = request.getParameter( "userName" );
        	userName = null == userName ? "" : userName;
        	
        	String dept = request.getParameter( "dept" );
            dept = dept == null ? "" : dept;
            String startDate = request.getParameter( "startDate" );
            startDate = StringUtil.isBlank( startDate ) ? "1900-01-01 11:11:11" : startDate;
            String endDate = request.getParameter( "endDate" );
            endDate = StringUtil.isBlank( endDate ) ? "2999-01-01 11:11:11" : endDate;
            String town = request.getParameter( "town" );
            town = town == null ? "" : town;
            String village = request.getParameter( "village" );
            village = village == null ? "" : village;
            PageEntity pageEntity = PageUtil.getPageNumAndSize( request );
            JsonUtil.bean2JsonForDate( response, peopleThingService.list( pageEntity.getPageNumber(), pageEntity.getPageSize(),userName,dept,startDate,endDate,town,village ) );
        }
        
        
        @RequestMapping("export.htm")
        public void export(HttpServletRequest request,HttpServletResponse response)
        {
            String userName = request.getParameter( "userName" );
            userName = null == userName ? "" : userName;
            
            String dept = request.getParameter( "dept" );
            dept = dept == null ? "" : dept;
            String startDate = request.getParameter( "startDate" );
            startDate = StringUtil.isBlank( startDate ) ? "1900-01-01 11:11:11" : startDate;
            String endDate = request.getParameter( "endDate" );
            endDate = StringUtil.isBlank( endDate ) ? "2999-01-01 11:11:11" : endDate;
            String town = request.getParameter( "town" );
            town = town == null ? "" : town;
            String village = request.getParameter( "village" );
            village = village == null ? "" : village;
            
            PageEntity pageEntity = PageUtil.getPageNumAndSize( request );
            List list = peopleThingService.list( pageEntity.getPageNumber(), 1000000,userName,dept,startDate,endDate,town,village ) .getRows();
            Map map = new HashMap();
            map.put("msssList",list);
            ExportExcel exportExcel = new ExportExcel();
            exportExcel.export("民生实事", map, "msss.xls",response);
        }
        
        @RequestMapping("listAll.htm")
        public void listAll(HttpServletRequest request, HttpServletResponse response)
        {
            JsonUtil.list2JsonForDate(response, peopleThingService.listAll());
        }
        
        @RequestMapping("toSave.htm")
        public ModelAndView toSave(HttpServletRequest request,String id)
        {
            String action = "add";
            PeopleThing peopleThing = new PeopleThing();
            if(null != id && !"".equals(id))
            {
                Object obj = peopleThingService.queryById( id );
                if(obj instanceof PeopleThing)
                {
                    peopleThing = (PeopleThing) obj;
                }
                action = "edit";
            }
            else
            {
                peopleThing.setId(IDUtil.getUUIDStr());
            }
            request.setAttribute( "bean", peopleThing );
            request.setAttribute("action", action);
            return new ModelAndView("fc/peoplething_save");
        }
        
        @RequestMapping("save.htm")
        public void save(PeopleThing peopleThing, HttpServletResponse response, HttpServletRequest request, String action)
        {
            boolean result = false;
            if("add".equals(action))
            {
                result = peopleThingService.insert( peopleThing );
            }
            else
            {
                result = peopleThingService.updateById( peopleThing );
            }
            
            JsonUtil.boolOut( response, result );
        }
        
        @RequestMapping("delete.htm")
        public void delete(String id,HttpServletResponse response)
        {
            boolean result = peopleThingService.deleteById( id );
            JsonUtil.boolOut( response, result );
        }
        
        @RequestMapping("view.htm")
        public ModelAndView view(String id,HttpServletRequest request)
        {
            Object peopleThing = peopleThingService.queryById(id);
            request.setAttribute("bean", peopleThing);
            if(null != ((PeopleThing)peopleThing).getPhotos())
            {
                String[] images = ((PeopleThing)peopleThing).getPhotos().split( "[|]" );
                request.setAttribute( "images", images );
            }
            
            return new ModelAndView("fc/peoplething_view");
        }
    
}
