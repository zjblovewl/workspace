package com.tyunsoft.base.loginlogger.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.tyunsoft.base.loginlogger.entity.LoginLogger;
import com.tyunsoft.base.loginlogger.service.ILoginLoggerService;
import com.tyunsoft.base.utils.IDUtil;
import com.tyunsoft.base.utils.JsonUtil;
import com.tyunsoft.base.utils.PageUtil;
import com.tyunsoft.base.utils.Pager;

/**
 *登录日志对应的controller控制类
 */
@Controller
@RequestMapping("loginlogger")
public class LoginLoggerController {
    
        @Autowired
        private ILoginLoggerService loginLoggerService;
        
        @InitBinder
        protected void initBinder(WebDataBinder binder) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        }

		/**
		  * 跳转到登录日志列表页面
		  *@param request 请求
		  *@return 转到跳转后的list页面
		  */
        @RequestMapping("forwardList.htm")
        public ModelAndView forwardList(HttpServletRequest request)
        {
            request.setAttribute( "functionStr", FunctionBuilder.build() );
            return new ModelAndView("sys/loginlogger_list");
        }
        
        @RequestMapping("list.htm")
        public void list(HttpServletRequest request,HttpServletResponse response)
        {
        	List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		
            PageEntity pageEntity = PageUtil.getPageNumAndSize( request );
            Pager pager = loginLoggerService.list( pageEntity.getPageNumber(), pageEntity.getPageSize(),conditions );
            System.out.println("查询日志记录数："+pager.getRows());
            JsonUtil.bean2JsonForDate( response,  pager);
        }
        
        @RequestMapping("listAll.htm")
        public void listAll(HttpServletRequest request, HttpServletResponse response)
        {
            JsonUtil.list2JsonForDate(response, loginLoggerService.listAll());
        }
        
        @RequestMapping("toSave.htm")
        public ModelAndView toSave(HttpServletRequest request,String id)
        {
            String action = "add";
            LoginLogger loginLogger = new LoginLogger();
            if(null != id && !"".equals(id))
            {
                Object obj = loginLoggerService.queryById( id );
                if(obj instanceof LoginLogger)
                {
                    loginLogger = (LoginLogger) obj;
                }
                action = "edit";
            }
            else
            {
                loginLogger.setId(IDUtil.getUUIDStr());
            }
            request.setAttribute( "bean", loginLogger );
            request.setAttribute("action", action);
            return new ModelAndView("loginlogger/loginlogger_save");
        }
        
        @RequestMapping("save.htm")
        public void save(LoginLogger loginLogger, HttpServletResponse response, HttpServletRequest request, String action)
        {
            boolean result = false;
            if("add".equals(action))
            {
                result = loginLoggerService.insert( loginLogger );
            }
            else
            {
                result = loginLoggerService.updateById( loginLogger );
            }
            
            JsonUtil.boolOut( response, result );
        }
        
        @RequestMapping("delete.htm")
        public void delete(String id,HttpServletResponse response)
        {
            boolean result = loginLoggerService.deleteById( id );
            JsonUtil.boolOut( response, result );
        }
        
        @RequestMapping("view.htm")
        public ModelAndView view(String id,HttpServletRequest request)
        {
            Object loginLogger = loginLoggerService.queryById(id);
            request.setAttribute("bean", loginLogger);
            return new ModelAndView("loginlogger/loginlogger_view");
        }
    
}
