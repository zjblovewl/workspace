package net.ycii.fc.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ycii.fc.entity.GoodThings;
import net.ycii.fc.service.IGoodThingsService;
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
 * 凡人好事控制层
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  周金兵
 * @version  [版本号, 2015年11月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping("goodThings")
public class GoodThingsController {
    
        @Autowired
        private IGoodThingsService goodThingsService;
        
        @InitBinder
        protected void initBinder(WebDataBinder binder) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        }

		/**
		 * <跳转到四德榜的集合列表页面>
		 * <功能详细描述>
		 * @param request
		 * @return [参数说明]
		 * 
		 * @return ModelAndView [返回类型说明]
		 * @exception throws [违例类型] [违例说明]
		 * @see [类、类#方法、类#成员]
		 */
        @RequestMapping("forwardList.htm")
        public ModelAndView forwardList(HttpServletRequest request)
        {
            request.setAttribute( "functionStr", FunctionBuilder.build() );
            return new ModelAndView("bg/goodthings/goodthings_list");
        }
        
        @RequestMapping("list.htm")
        public void list(HttpServletRequest request,HttpServletResponse response)
        {
        	List<SearchCondition> conditions = new ArrayList<SearchCondition>();
	        	SearchCondition condition = null;
				condition = new SearchCondition();
				condition.setColumn("title");	   
				condition.setLinkSign("like");
				condition.setValue(request.getParameter("title"));
				conditions.add(condition);
		
            PageEntity pageEntity = PageUtil.getPageNumAndSize( request );
            JsonUtil.bean2JsonForDate( response, goodThingsService.list( pageEntity.getPageNumber(), pageEntity.getPageSize(),conditions ) );
        }
        
        @RequestMapping("listAll.htm")
        public void listAll(HttpServletRequest request, HttpServletResponse response)
        {
            JsonUtil.list2JsonForDate(response, goodThingsService.listAll());
        }
        
        @RequestMapping("toSave.htm")
        public ModelAndView toSave(HttpServletRequest request,String id)
        {
            String action = "add";
            GoodThings goodThings = new GoodThings();
            if(null != id && !"".equals(id))
            {
                Object obj = goodThingsService.queryById( id );
                if(obj instanceof GoodThings)
                {
                    goodThings = (GoodThings) obj;
                }
                action = "edit";
                request.setAttribute( "videoPath", goodThings.getVideoPath() );
                request.setAttribute( "accessoryPath", goodThings.getAccessoryPath() );
            }
            else
            {
                goodThings.setId(IDUtil.getUUIDStr());
            }
            request.setAttribute( "bean", goodThings );
            request.setAttribute("action", action);
            return new ModelAndView("bg/goodthings/goodthings_save");
        }
        
        @RequestMapping("save.htm")
        public void save(GoodThings goodThings, HttpServletResponse response, HttpServletRequest request, String action)
        {
            boolean result = false;
            if("add".equals(action))
            {
                result = goodThingsService.insert( goodThings,request );
            }
            else
            {
                result = goodThingsService.updateById( goodThings,request );
            }
            
            JsonUtil.boolOut( response, result );
        }
        
        @RequestMapping("delete.htm")
        public void delete(String id,HttpServletResponse response)
        {
            boolean result = goodThingsService.deleteById( id );
            JsonUtil.boolOut( response, result );
        }
        
        @RequestMapping("view.htm")
        public ModelAndView view(String id,HttpServletRequest request)
        {
            Object obj = goodThingsService.queryById(id);
            
            GoodThings gh = (GoodThings)obj;
            request.setAttribute("bean", obj);
            request.setAttribute( "videoPath", gh.getVideoPath());
            request.setAttribute( "accessoryPath", gh.getAccessoryPath() );
            return new ModelAndView("bg/goodthings/goodthings_view");
        }
        
        @SuppressWarnings( "rawtypes" )
        @RequestMapping("templateDownload.htm")
        public ModelAndView templateDownload(HttpServletRequest request,HttpServletResponse response)
        {
            ExportExcel exportExcel = new ExportExcel();
            exportExcel.export("addrBookTemplate", new HashMap(), "addrBookTemplate.xls",response);
            return null;  
        }
        
        
//        private void priOutMgs(HttpServletResponse response,String errorMsg)
//        {
//            JsonUtil.strOut( response, JsonUtil.bean2JsonStr( "{\"errorMsg\":\""+errorMsg+"\"}" ) );
//        }
}
