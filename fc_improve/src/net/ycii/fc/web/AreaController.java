package net.ycii.fc.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ycii.fc.entity.TreeNode;
import net.ycii.fc.service.IAreaService;
import net.ycii.fc.service.ICommonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tyunsoft.base.common.FunctionBuilder;
import com.tyunsoft.base.entity.PageEntity;
import com.tyunsoft.base.utils.JsonUtil;
import com.tyunsoft.base.utils.PageUtil;
import com.tyunsoft.base.utils.Pager;

@Controller
@RequestMapping("area")
public class AreaController
{
    
    @Autowired
    private IAreaService areaService;
    
    @Autowired
    private ICommonService commonService;

    @RequestMapping("forwardList.htm")
    public ModelAndView forwardList(HttpServletRequest request)
    {
        List<TreeNode> nodes = commonService.getdeptUserTree();
        String jsonStr = JsonUtil.list2Json( nodes );
        request.setAttribute( "jsonStr", jsonStr.replace( "\"", "'" ) ); 
        request.setAttribute( "functionStr", FunctionBuilder.build() );
        return new ModelAndView("fc/area_list");
    }
    
    @RequestMapping("list.htm")
    public void list(String townName,String villageName,HttpServletRequest request,HttpServletResponse response)
    {
        townName = townName == null ? "" : townName;
        villageName = villageName == null ? "" : villageName;
        PageEntity pageEntity = PageUtil.getPageNumAndSize( request );
        Pager pager = areaService.list( townName, villageName, pageEntity.getPageNumber(), pageEntity.getPageSize() );
        JsonUtil.bean2JsonForDate( response, pager);
    }
    
    @RequestMapping("saveUsers.htm")
    public void saveUsers(HttpServletRequest request,HttpServletResponse response)
    {
        String id = request.getParameter( "id" );
        String users = request.getParameter( "users" );
        boolean result = areaService.setAreaUsers( users, id );
        JsonUtil.boolOut( response, result );
    }
}
