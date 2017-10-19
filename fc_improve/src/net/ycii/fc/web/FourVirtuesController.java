package net.ycii.fc.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ycii.fc.entity.FourVirtues;
import net.ycii.fc.service.IFourVirtuesService;
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
 * 四德榜控制层
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  周金兵
 * @version  [版本号, 2015年11月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping("fourVirtues")
public class FourVirtuesController {
    
        @Autowired
        private IFourVirtuesService fourVirtuesService;
        
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
            return new ModelAndView("bg/fourvirtues/fourvirtues_list");
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
            JsonUtil.bean2JsonForDate( response, fourVirtuesService.list( pageEntity.getPageNumber(), pageEntity.getPageSize(),conditions ) );
        }
        
        @RequestMapping("listAll.htm")
        public void listAll(HttpServletRequest request, HttpServletResponse response)
        {
            JsonUtil.list2JsonForDate(response, fourVirtuesService.listAll());
        }
        
        @RequestMapping("toSave.htm")
        public ModelAndView toSave(HttpServletRequest request,String id)
        {
            String action = "add";
            FourVirtues fourVirtues = new FourVirtues();
            if(null != id && !"".equals(id))
            {
                Object obj = fourVirtuesService.queryById( id );
                if(obj instanceof FourVirtues)
                {
                    fourVirtues = (FourVirtues) obj;
                }
                action = "edit";
                request.setAttribute( "videoPath", fourVirtues.getVideoPath() );
                request.setAttribute( "accessoryPath", fourVirtues.getAccessoryPath() );
            }
            else
            {
                fourVirtues.setId(IDUtil.getUUIDStr());
            }
            request.setAttribute( "bean", fourVirtues );
            request.setAttribute("action", action);
            return new ModelAndView("bg/fourvirtues/fourvirtues_save");
        }
        
        @RequestMapping("save.htm")
        public void save(FourVirtues fourVirtues, HttpServletResponse response, HttpServletRequest request, String action)
        {
            boolean result = false;
            if("add".equals(action))
            {
                result = fourVirtuesService.insert( fourVirtues,request );
            }
            else
            {
                result = fourVirtuesService.updateById( fourVirtues,request );
            }
            
            JsonUtil.boolOut( response, result );
        }
        
        @RequestMapping("delete.htm")
        public void delete(String id,HttpServletResponse response)
        {
            boolean result = fourVirtuesService.deleteById( id );
            JsonUtil.boolOut( response, result );
        }
        
        @RequestMapping("view.htm")
        public ModelAndView view(String id,HttpServletRequest request)
        {
            Object obj = fourVirtuesService.queryById(id);
            
            FourVirtues fv = (FourVirtues)obj;
            request.setAttribute("bean", obj);
            request.setAttribute( "videoPath", fv.getVideoPath());
            request.setAttribute( "accessoryPath", fv.getAccessoryPath() );
            return new ModelAndView("bg/fourvirtues/fourvirtues_view");
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
