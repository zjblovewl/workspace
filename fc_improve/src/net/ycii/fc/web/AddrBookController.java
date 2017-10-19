package net.ycii.fc.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import net.ycii.fc.entity.AddrBook;
import net.ycii.fc.service.IAddrBookService;
import net.ycii.fc.util.ExportExcel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.tyunsoft.base.common.FunctionBuilder;
import com.tyunsoft.base.entity.PageEntity;
import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.utils.IDUtil;
import com.tyunsoft.base.utils.JsonUtil;
import com.tyunsoft.base.utils.PageUtil;

/**
 *通讯录对应的controller控制类
 */
@Controller
@RequestMapping("addrbook")
public class AddrBookController {
    
        @Autowired
        private IAddrBookService addrBookService;
        
        private File addrBookexcel;        
        
        @InitBinder
        protected void initBinder(WebDataBinder binder) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        }

		/**
		  * 跳转到通讯录列表页面
		  *@param request 请求
		  *@return 转到跳转后的list页面
		  */
        @RequestMapping("forwardList.htm")
        public ModelAndView forwardList(HttpServletRequest request)
        {
            request.setAttribute( "functionStr", FunctionBuilder.build() );
            return new ModelAndView("fc/addrbook_list");
        }
        
        @RequestMapping("list.htm")
        public void list(HttpServletRequest request,HttpServletResponse response)
        {
        	List<SearchCondition> conditions = new ArrayList<SearchCondition>();
	        	SearchCondition condition = null;
				condition = new SearchCondition();
				condition.setColumn("name");	   
				condition.setLinkSign("like");
				condition.setValue(request.getParameter("name"));
				conditions.add(condition);
				condition = new SearchCondition();
				condition.setColumn("department");	   
				condition.setLinkSign("like");
				condition.setValue(request.getParameter("department"));
				conditions.add(condition);
				condition = new SearchCondition();
				condition.setColumn("phone_num");	   
				condition.setLinkSign("like");
				condition.setValue(request.getParameter("phoneNum"));
				conditions.add(condition);
		
            PageEntity pageEntity = PageUtil.getPageNumAndSize( request );
            JsonUtil.bean2JsonForDate( response, addrBookService.list( pageEntity.getPageNumber(), pageEntity.getPageSize(),conditions ) );
        }
        
        @RequestMapping("listAll.htm")
        public void listAll(HttpServletRequest request, HttpServletResponse response)
        {
            JsonUtil.list2JsonForDate(response, addrBookService.listAll());
        }
        
        @RequestMapping("toSave.htm")
        public ModelAndView toSave(HttpServletRequest request,String id)
        {
            String action = "add";
            AddrBook addrBook = new AddrBook();
            if(null != id && !"".equals(id))
            {
                Object obj = addrBookService.queryById( id );
                if(obj instanceof AddrBook)
                {
                    addrBook = (AddrBook) obj;
                }
                action = "edit";
            }
            else
            {
                addrBook.setId(IDUtil.getUUIDStr());
            }
            request.setAttribute( "bean", addrBook );
            request.setAttribute("action", action);
            return new ModelAndView("fc/addrbook_save");
        }
        
        @RequestMapping("save.htm")
        public void save(AddrBook addrBook, HttpServletResponse response, HttpServletRequest request, String action)
        {
            boolean result = false;
            if("add".equals(action))
            {
                result = addrBookService.insert( addrBook );
            }
            else
            {
                result = addrBookService.updateById( addrBook );
            }
            
            JsonUtil.boolOut( response, result );
        }
        
        @RequestMapping("delete.htm")
        public void delete(String id,HttpServletResponse response)
        {
            boolean result = addrBookService.deleteById( id );
            JsonUtil.boolOut( response, result );
        }
        
        @RequestMapping("view.htm")
        public ModelAndView view(String id,HttpServletRequest request)
        {
            Object addrBook = addrBookService.queryById(id);
            request.setAttribute("bean", addrBook);
            return new ModelAndView("fc/addrbook_view");
        }
        
        @SuppressWarnings( "rawtypes" )
        @RequestMapping("templateDownload.htm")
        public ModelAndView templateDownload(HttpServletRequest request,HttpServletResponse response)
        {
            ExportExcel exportExcel = new ExportExcel();
            exportExcel.export("addrBookTemplate", new HashMap(), "addrBookTemplate.xls",response);
            return null;  
        }
        
        @RequestMapping("batchInsert.htm")
        public void batchInsert(HttpServletRequest request,HttpServletResponse response)
        {
            String errorMsg;
            MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;  
            
            MultipartFile addrBookexcel = multipartRequest.getFile("addrBookexcel");        
            if(null == addrBookexcel)
            {
                errorMsg = "请选择文件！";
                priOutMgs(response,errorMsg);
                return;                
            }
            try {
                InputStream in = new BufferedInputStream(addrBookexcel.getInputStream());
                jxl.Workbook rwb = Workbook.getWorkbook(in);
                int sheets = rwb.getNumberOfSheets();
                boolean result = true;
                for (int i = 0; i < sheets; i++) {
                    Sheet rs = rwb.getSheet(i);
                    int columnNum = rs.getColumns();
                    int rowNum = rs.getRows();
                    // 验证excel第一行是否符合要求
                    if (!(  rs.getCell(0, 0).getContents().trim().equals("姓名")
                            && rs.getCell(1, 0).getContents().trim().equals("部门")
                            && rs.getCell(2, 0).getContents().trim().equals("手机号")
                           )) {
                        errorMsg = "表头个数或者名称不对";
                        priOutMgs(response,errorMsg);
                        return;
                    }
                    Map<String,String> condition = null;
                    for (int row = 1; row < rowNum; row++) {
                        AddrBook addrBook = new AddrBook();
                        for (int column = 0; column < columnNum; column++) {
                            Cell crl = rs.getCell(column, row);
                            switch (column) {
                            case 0: {
                                String userName = crl.getContents();
                                if(StringUtils.isEmpty( userName ))
                                {
                                    errorMsg = "第"+row+"行，用户名不能为空";
                                    priOutMgs(response,errorMsg);
                                    return;
                                }
                                
                                condition = new HashMap<String, String>();
                                condition.put( "userName", userName );
                                condition.put( "deptName", rs.getCell(column+1, row).getContents() );
                                
                                List<Object> list = addrBookService.queryUserWithCondition( condition );
                                
                                if(null != list && list.size()>0)
                                {
                                    errorMsg = "第"+row+"行，该部门下，已经存在相同名称用户，请更换用户名";
                                    priOutMgs(response,errorMsg);
                                    return;
                                }
                                addrBook.setName( userName );
                                break;
                            }
                            case 1: {
                                String deptName = crl.getContents();
                                if(StringUtils.isEmpty( deptName ))
                                {
                                    errorMsg = "第"+row+"行，部门名称不能为空";
                                    priOutMgs(response,errorMsg);
                                    return;
                                }                                
                                
                                condition = new HashMap<String, String>();
                                condition.put( "deptName", crl.getContents() );
                                
                                List<Object> list = addrBookService.queryDeptByName( condition );
                                
                                if(null == list || list.size() == 0)
                                {
                                    errorMsg = "第"+row+"行，不存在该部门，请重新填写";
                                    priOutMgs(response,errorMsg);
                                    return;
                                }
                                addrBook.setDepartment( deptName );
                                break;
                            }
                            case 2: {
                                String content = crl.getContents();
                                if(StringUtils.isEmpty( content ))
                                {
                                    errorMsg = "第"+row+"行，手机号码不能为空";
                                    priOutMgs(response,errorMsg);
                                    return;
                                }                                 
                                
                                try
                                {
                                    Double.parseDouble(content);
                                } catch ( Exception e )
                                {
                                    errorMsg = "第"+row+"行，第"+(column+1)+"列数据格式错误";
                                    priOutMgs(response,errorMsg);
                                    return;
                                }
                                
                                Pattern pattern = Pattern.compile("^1[358]\\d{9}$");
                                Matcher isNum = pattern.matcher(content);
                                if( !isNum.matches() )
                                {
                                    errorMsg = "第"+row+"行，该手机号码格式错误，请修正";
                                    priOutMgs(response,errorMsg);
                                    return;
                                }
                                
                                condition = new HashMap<String, String>();
                                condition.put( "phoneNum", content );
                                
                                List<Object> list = addrBookService.queryUserByPhotoNum( condition );
                                
                                if(null != list && list.size()>0)
                                {
                                    errorMsg = "第"+row+"行，该手机号码已经存在，请更换";
                                    priOutMgs(response,errorMsg);
                                    return;
                                }                                
                                
                                addrBook.setPhoneNum( content );
                                break;
                            }
                            default:
                                break;
                            }
                        }
                        
                        addrBook.setId(IDUtil.getUUIDStr());
                        //数据库插入
                        result = result&&addrBookService.insert( addrBook );
                        
                        if(!result){
                            errorMsg = "第"+row+"行数据库插入错误，请修改数据";
                            priOutMgs(response,errorMsg);
                            return;                            
                        }
                    }
                }
               
                in.close();
            } catch (Exception e) {
                errorMsg = "未知错误！";
                priOutMgs(response,errorMsg);
                return;
            }
            //标记正确
            errorMsg = "";
            priOutMgs(response,errorMsg);
            return;
            
        }
        
        private void priOutMgs(HttpServletResponse response,String errorMsg)
        {
            JsonUtil.strOut( response, JsonUtil.bean2JsonStr( "{\"errorMsg\":\""+errorMsg+"\"}" ) );
        }

        public File getAddrBookexcel()
        {
            return addrBookexcel;
        }

        public void setAddrBookexcel( File addrBookexcel )
        {
            this.addrBookexcel = addrBookexcel;
        }
    
}
