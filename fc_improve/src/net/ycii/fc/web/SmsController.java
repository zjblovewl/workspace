package net.ycii.fc.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ycii.fc.entity.Sms;
import net.ycii.fc.entity.TreeNode;
import net.ycii.fc.service.ICommonService;
import net.ycii.fc.service.ISmsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tyunsoft.base.common.FunctionBuilder;
import com.tyunsoft.base.common.SessionHelper;
import com.tyunsoft.base.entity.PageEntity;
import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.utils.IDUtil;
import com.tyunsoft.base.utils.JsonUtil;
import com.tyunsoft.base.utils.PageUtil;

/**
 *短信信息对应的controller控制类
 */
@Controller
@RequestMapping("sms")
public class SmsController {
    
        @Autowired
        private ISmsService smsService;
        
        @Autowired
        ICommonService commonService;        
        
        @InitBinder
        protected void initBinder(WebDataBinder binder) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        }

		/**
		  * 跳转到短信信息列表页面
		  *@param request 请求
		  *@return 转到跳转后的list页面
		  */
        @RequestMapping("forwardList.htm")
        public ModelAndView forwardList(HttpServletRequest request)
        {
            request.setAttribute( "functionStr", FunctionBuilder.build() );
            return new ModelAndView("fc/sms_list");
        }
        
        @RequestMapping("list.htm")
        public void list(HttpServletRequest request,HttpServletResponse response)
        {
        	List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		
            PageEntity pageEntity = PageUtil.getPageNumAndSize( request );
            JsonUtil.bean2JsonForDate( response, smsService.list( pageEntity.getPageNumber(), pageEntity.getPageSize(),conditions ) );
        }
        
        @RequestMapping("listAll.htm")
        public void listAll(HttpServletRequest request, HttpServletResponse response)
        {
            JsonUtil.list2JsonForDate(response, smsService.listAll());
        }
        
        @RequestMapping("toSave.htm")
        public ModelAndView toSave(HttpServletRequest request,String id)
        {
            String action = "add";
            Sms sms = new Sms();
            if(null != id && !"".equals(id))
            {
                Object obj = smsService.queryById( id );
                if(obj instanceof Sms)
                {
                    sms = (Sms) obj;
                }
                action = "edit";
            }
            else
            {
                sms.setId(IDUtil.getUUIDStr());
            }
            List<TreeNode> nodes = commonService.getdeptUserTree();
            String jsonStr = JsonUtil.list2Json( nodes );
            request.setAttribute( "jsonStr", jsonStr.replace( "\"", "'" ) );            
            request.setAttribute( "bean", sms );
            request.setAttribute("action", action);
            return new ModelAndView("fc/sms_save");
        }
        
        @RequestMapping("save.htm")
        public void save(Sms sms, HttpServletResponse response, HttpServletRequest request, String action)
        {
            boolean result = false;
            if("add".equals(action))
            {
                sms.setCreateDate( new Date() );
                sms.setCreateUser( SessionHelper.getUserId() );
                result = smsService.insert( sms );
            }
            else
            {
                result = smsService.updateById( sms );
            }
            
            JsonUtil.boolOut( response, result );
        }
        
        @RequestMapping("delete.htm")
        public void delete(String id,HttpServletResponse response)
        {
            boolean result = smsService.deleteById( id );
            JsonUtil.boolOut( response, result );
        }
        
        @RequestMapping("view.htm")
        public ModelAndView view(String id,HttpServletRequest request)
        {
            Object sms = smsService.queryById(id);
            request.setAttribute("bean", sms);
            return new ModelAndView("fc/sms_view");
        }
    
}
