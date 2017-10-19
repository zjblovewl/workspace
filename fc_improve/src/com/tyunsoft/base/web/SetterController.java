package com.tyunsoft.base.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tyunsoft.base.common.CacheFactory;
import com.tyunsoft.base.common.FunctionBuilder;
import com.tyunsoft.base.common.SessionHelper;
import com.tyunsoft.base.entity.PageEntity;
import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.entity.Setter;
import com.tyunsoft.base.service.ISetterService;
import com.tyunsoft.base.utils.IDUtil;
import com.tyunsoft.base.utils.JsonUtil;
import com.tyunsoft.base.utils.PageUtil;

/**
 *系统设置对应的controller控制类
 */
@Controller
@RequestMapping("setter")
public class SetterController {
    
        @Autowired
        private ISetterService setterService;
        
        @InitBinder
        protected void initBinder(WebDataBinder binder) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        }

		/**
		  * 跳转到系统设置列表页面
		  *@param request 请求
		  *@return 转到跳转后的list页面
		  */
        @RequestMapping("forwardList.htm")
        public ModelAndView forwardList(HttpServletRequest request)
        {
            request.setAttribute( "functionStr", FunctionBuilder.build() );
            return new ModelAndView("sys/setter_list");
        }
        
        @RequestMapping("list.htm")
        public void list(HttpServletRequest request,HttpServletResponse response)
        {
        	List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		
            PageEntity pageEntity = PageUtil.getPageNumAndSize( request );
            JsonUtil.bean2JsonForDate( response, setterService.list( pageEntity.getPageNumber(), pageEntity.getPageSize(),conditions ) );
        }
        
        @RequestMapping("listAll.htm")
        public void listAll(HttpServletRequest request, HttpServletResponse response)
        {
            JsonUtil.list2JsonForDate(response, setterService.listAll());
        }
        
        @RequestMapping("checkNameExist.htm")
        public void checkNameExist(String id,String name, HttpServletResponse response)
        {
            JsonUtil.boolOut( response, setterService.checkNameExist( id, name ) );
        }
        
        @RequestMapping("toSave.htm")
        public ModelAndView toSave(HttpServletRequest request,String id)
        {
            String action = "add";
            Setter setter = new Setter();
            if(null != id && !"".equals(id))
            {
                Object obj = setterService.queryById( id );
                if(obj instanceof Setter)
                {
                    setter = (Setter) obj;
                }
                action = "edit";
            }
            else
            {
                setter.setId(IDUtil.getUUIDStr());
            }
            request.setAttribute( "bean", setter );
            request.setAttribute("action", action);
            return new ModelAndView("sys/setter_save");
        }
        
        @RequestMapping("save.htm")
        public void save(Setter setter, HttpServletResponse response, HttpServletRequest request, String action)
        {
            boolean result = false;
            if("add".equals(action))
            {
                setter.setCreateUser( SessionHelper.getUserId() );
                setter.setCreateDate( new Date() );
                result = setterService.insert( setter );
            }
            else
            {
                setter.setUpdateDate( new Date() );
                setter.setUpdateUser( SessionHelper.getUserId() );
                result = setterService.updateById( setter );
            }
            if(result)
            {
                List<Object> setters = setterService.listAll();
                Setter set = null;
                Map<String,String> setterMap = new HashMap<String,String>();
                for ( Object object : setters )
                {
                    set = (Setter)object;
                    setterMap.put( set.getSetterName(), set.getSetterValue() );
                }
                CacheFactory.getInstance().setSetterMap( setterMap );
            }
            JsonUtil.boolOut( response, result );
        }
        
        @RequestMapping("delete.htm")
        public void delete(String id,HttpServletResponse response)
        {
            boolean result = setterService.deleteById( id );
            JsonUtil.boolOut( response, result );
        }
        
        @RequestMapping("view.htm")
        public ModelAndView view(String id,HttpServletRequest request)
        {
            Object setter = setterService.queryById(id);
            request.setAttribute("bean", setter);
            return new ModelAndView("sys/setter_view");
        }
    
}
