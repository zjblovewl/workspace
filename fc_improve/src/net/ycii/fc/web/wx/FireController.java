package net.ycii.fc.web.wx;

import com.tyunsoft.base.common.FunctionBuilder;
import com.tyunsoft.base.entity.PageEntity;
import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.utils.IDUtil;
import com.tyunsoft.base.utils.JsonUtil;
import com.tyunsoft.base.utils.PageUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ycii.fc.entity.Fire;
import net.ycii.fc.entity.FourVirtues;
import net.ycii.fc.service.IFireService;
import net.ycii.fc.util.ExportExcel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("fire")
public class FireController
{

  @Autowired
  private IFireService mapService;
  
  Logger logger = LoggerFactory.getLogger( FireController.class );

  @InitBinder
  protected void initBinder(WebDataBinder binder)
  {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
  }

  @RequestMapping("forwardList.htm")
  public ModelAndView forwardList(HttpServletRequest request)
  {
    request.setAttribute("functionStr", FunctionBuilder.build());
    return new ModelAndView("wx/firemanage/fire_list");
  }

  @RequestMapping("list.htm")
  public void list(HttpServletRequest request, HttpServletResponse response)
  {
    List conditions = new ArrayList();
    SearchCondition condition = null;
    condition = new SearchCondition();
    condition.setColumn("postion");
    condition.setLinkSign("like");
    condition.setValue(request.getParameter("postion"));
    conditions.add(condition);

    PageEntity pageEntity = PageUtil.getPageNumAndSize(request);
    JsonUtil.bean2JsonForDate(response, this.mapService.list(pageEntity.getPageNumber(), pageEntity.getPageSize(), conditions));
  }

  @RequestMapping("listAll.htm")
  public void listAll(HttpServletRequest request, HttpServletResponse response)
  {
    JsonUtil.list2JsonForDate(response, this.mapService.listAll());
  }

  @RequestMapping("toSave.htm")
  public ModelAndView toSave(HttpServletRequest request, String id)
  {
    String action = "add";
    Fire map = new Fire();
    if ((id != null) && (!"".equals(id)))
    {
      Object obj = this.mapService.queryById(id);
      if ((obj instanceof FourVirtues))
      {
        map = (Fire)obj;
      }
      action = "edit";
    }
    else
    {
      map.setId(IDUtil.getUUIDStr());
    }
    request.setAttribute("bean", map);
    request.setAttribute("action", action);
    return new ModelAndView("wx/firemanage/fire_save");
  }

  @RequestMapping("save.htm")
  public void save(Fire map, HttpServletResponse response, HttpServletRequest request, String action)
  {
    boolean result = false;
    if ("add".equals(action))
    {
        String provience = "江苏省";
        String city = request.getParameter( "city" );
        String country = request.getParameter( "country" );
        String detailplace = request.getParameter( "detailplace" );
        String postion = provience + city + country +  detailplace;
        logger.info( "地址为:" + postion );
        map.setPostion( postion );
        
        
      result = this.mapService.insert(map, request);
    }

    JsonUtil.boolOut(response, result);
  }

  @RequestMapping("delete.htm")
  public void delete(String id, HttpServletResponse response)
  {
    boolean result = this.mapService.deleteById(id);
    JsonUtil.boolOut(response, result);
  }

  @RequestMapping("view.htm")
  public ModelAndView view(String id, HttpServletRequest request)
  {
    Object obj = this.mapService.queryById(id);

    FourVirtues fv = (FourVirtues)obj;
    request.setAttribute("bean", obj);
    request.setAttribute("videoPath", fv.getVideoPath());
    request.setAttribute("accessoryPath", fv.getAccessoryPath());
    return new ModelAndView("wx/firemanage/fire_view");
  }

  @RequestMapping("templateDownload.htm")
  public ModelAndView templateDownload(HttpServletRequest request, HttpServletResponse response)
  {
    ExportExcel exportExcel = new ExportExcel();
    exportExcel.export("addrBookTemplate", new HashMap(), "addrBookTemplate.xls", response);
    return null;
  }

  @RequestMapping("/showfire/{fireId}.htm")
  public String showFire(@PathVariable String fireId, HttpServletRequest request)
  {
    Fire fire = (Fire)this.mapService.queryById(fireId);
//    request.setAttribute("fire", fire);
//    return "h5/showfire";
    String postion = fire.getPostion();
    String lng = fire.getLongitude();
    String lat = fire.getLatitude();
    
    String centerPlace = "";
    if ((postion != null) && (!postion.equals("")))
    {
      if ((postion.indexOf("省") != -1) && (postion.indexOf("市") != -1))
      {
        int firstIdx = postion.indexOf("省");
        int lastIdx = postion.indexOf("市");
        centerPlace = postion.substring(firstIdx + 1, lastIdx);
      }
    }

    request.setAttribute("postion", postion);
    request.setAttribute("centerPlace", centerPlace);
    request.setAttribute("lng", lng);
    request.setAttribute("lat", lat);
    return "h5/showmap";
  }

  @RequestMapping("/showmap.htm")
  public String showMap(String postion, String lng, String lat, HttpServletRequest request)
  {
    String centerPlace = "";
    if ((postion != null) && (!postion.equals("")))
    {
      if ((postion.indexOf("省") != -1) && (postion.indexOf("市") != -1))
      {
        int firstIdx = postion.indexOf("省");
        int lastIdx = postion.indexOf("市");
        centerPlace = postion.substring(firstIdx + 1, lastIdx);
      }
    }

    request.setAttribute("postion", postion);
    request.setAttribute("centerPlace", centerPlace);
    request.setAttribute("lng", lng);
    request.setAttribute("lat", lat);
    return "h5/showmap";
  }
}