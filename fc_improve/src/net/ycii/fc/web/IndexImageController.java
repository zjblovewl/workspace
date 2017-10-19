package net.ycii.fc.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

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
import com.tyunsoft.base.common.SessionHelper;
import com.tyunsoft.base.entity.PageEntity;
import com.tyunsoft.base.utils.IDUtil;
import com.tyunsoft.base.utils.JsonUtil;
import com.tyunsoft.base.utils.PageUtil;
import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.utils.FileUtil;

import net.ycii.fc.entity.IndexImage;
import net.ycii.fc.service.IIndexImageService;

/**
 *首页图片对应的controller控制类
 */
@Controller
@RequestMapping("indeximage")
public class IndexImageController {
    
        @Autowired
        private IIndexImageService indexImageService;
        
        @InitBinder
        protected void initBinder(WebDataBinder binder) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        }

		/**
		  * 跳转到首页图片列表页面
		  *@param request 请求
		  *@return 转到跳转后的list页面
		  */
        @RequestMapping("forwardList.htm")
        public ModelAndView forwardList(HttpServletRequest request)
        {
            request.setAttribute( "functionStr", FunctionBuilder.build() );
            return new ModelAndView("fc/indeximage_list");
        }
        
        @RequestMapping("list.htm")
        public void list(HttpServletRequest request,HttpServletResponse response)
        {
        	List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		
            PageEntity pageEntity = PageUtil.getPageNumAndSize( request );
            JsonUtil.bean2JsonForDate( response, indexImageService.list( pageEntity.getPageNumber(), pageEntity.getPageSize(),conditions ) );
        }
        
        @RequestMapping("listAll.htm")
        public void listAll(HttpServletRequest request, HttpServletResponse response)
        {
            JsonUtil.list2JsonForDate(response, indexImageService.listAll());
        }
        
        @RequestMapping("toSave.htm")
        public ModelAndView toSave(HttpServletRequest request,String id)
        {
            String action = "add";
            IndexImage indexImage = new IndexImage();
            if(null != id && !"".equals(id))
            {
                Object obj = indexImageService.queryById( id );
                if(obj instanceof IndexImage)
                {
                    indexImage = (IndexImage) obj;
                }
                action = "edit";
            }
            else
            {
                indexImage.setId(IDUtil.getUUIDStr());
            }
            request.setAttribute( "bean", indexImage );
            request.setAttribute("action", action);
            return new ModelAndView("fc/indeximage_save");
        }
        
        @RequestMapping("save.htm")
        public void save(IndexImage indexImage, HttpServletResponse response, HttpServletRequest request, String action)
        {
        	  	indexImage.setImagePath(FileUtil.upload(request, "upload/indeximage",indexImage.getId(), "imagePathFile"));
            boolean result = false;
            if("add".equals(action))
            {
                indexImage.setCreateDate( new Date() );
                indexImage.setCreateUser( SessionHelper.getUserId() );
                result = indexImageService.insert( indexImage );
            }
            else
            {
                result = indexImageService.updateById( indexImage );
            }
            
            JsonUtil.boolOut( response, result );
        }
        
        @RequestMapping("delete.htm")
        public void delete(String id,HttpServletResponse response)
        {
            boolean result = indexImageService.deleteById( id );
            JsonUtil.boolOut( response, result );
        }
        
        @RequestMapping("view.htm")
        public ModelAndView view(String id,HttpServletRequest request)
        {
            Object indexImage = indexImageService.queryById(id);
            request.setAttribute("bean", indexImage);
            return new ModelAndView("fc/indeximage_view");
        }
    
}
