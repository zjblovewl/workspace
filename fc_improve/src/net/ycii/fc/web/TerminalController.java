package net.ycii.fc.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ycii.fc.entity.Terminal;
import net.ycii.fc.service.ITerminalService;

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
 *用户终端信息对应的controller控制类
 */
@Controller
@RequestMapping("terminal")
public class TerminalController {
    
        @Autowired
        private ITerminalService terminalService;
        
        @InitBinder
        protected void initBinder(WebDataBinder binder) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        }

		/**
		  * 跳转到用户终端信息列表页面
		  *@param request 请求
		  *@return 转到跳转后的list页面
		  */
        @RequestMapping("forwardList.htm")
        public ModelAndView forwardList(HttpServletRequest request)
        {
            request.setAttribute( "functionStr", FunctionBuilder.build() );
            return new ModelAndView("fc/terminal_list");
        }
        
        @RequestMapping("list.htm")
        public void list(HttpServletRequest request,HttpServletResponse response)
        {
        	List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		
            PageEntity pageEntity = PageUtil.getPageNumAndSize( request );
            JsonUtil.bean2JsonForDate( response, terminalService.list( pageEntity.getPageNumber(), pageEntity.getPageSize(),conditions ) );
        }
        
        @RequestMapping("listAll.htm")
        public void listAll(HttpServletRequest request, HttpServletResponse response)
        {
            JsonUtil.list2JsonForDate(response, terminalService.listAll());
        }
        
        @RequestMapping("toSave.htm")
        public ModelAndView toSave(HttpServletRequest request,String id)
        {
            String action = "add";
            Terminal terminal = new Terminal();
            if(null != id && !"".equals(id))
            {
                Object obj = terminalService.queryById( id );
                if(obj instanceof Terminal)
                {
                    terminal = (Terminal) obj;
                }
                action = "edit";
            }
            else
            {
                terminal.setId(IDUtil.getUUIDStr());
            }
            request.setAttribute( "bean", terminal );
            request.setAttribute("action", action);
            return new ModelAndView("fc/terminal_save");
        }
        
        @RequestMapping("save.htm")
        public void save(Terminal terminal, HttpServletResponse response, HttpServletRequest request, String action)
        {
            boolean result = false;
            if("add".equals(action))
            {
                result = terminalService.insert( terminal );
            }
            else
            {
                result = terminalService.updateById( terminal );
            }
            
            JsonUtil.boolOut( response, result );
        }
        
        @RequestMapping("delete.htm")
        public void delete(String id,HttpServletResponse response)
        {
            boolean result = terminalService.deleteById( id );
            JsonUtil.boolOut( response, result );
        }
        
        @RequestMapping("view.htm")
        public ModelAndView view(String id,HttpServletRequest request)
        {
            Object terminal = terminalService.queryById(id);
            request.setAttribute("bean", terminal);
            return new ModelAndView("fc/terminal_view");
        }
    
}
