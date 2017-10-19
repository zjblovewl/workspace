package com.tyunsoft.base.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tyunsoft.base.common.CacheFactory;
import com.tyunsoft.base.common.FunctionBuilder;
import com.tyunsoft.base.entity.Dictionary;
import com.tyunsoft.base.entity.DictionaryValue;
import com.tyunsoft.base.service.IDictionaryService;
import com.tyunsoft.base.utils.Constants;
import com.tyunsoft.base.utils.JsonUtil;

/**
 * 字典管理相关控制器转换
 * 
 * @author Flymz
 * 
 */
@Controller
@RequestMapping("/dic")
public class DictionaryController {

	@Autowired
	private IDictionaryService dictionaryService;
	
	/**
	 * 跳转到字典管理框架页
	 * @param request 请求
	 * @return 字典管理框架页
	 */
	@RequestMapping(value = "/frame.htm")
	public ModelAndView frame(HttpServletRequest request) {
		String functionStr = FunctionBuilder
        .build();
		request.setAttribute( "functionStr", functionStr );
		return new ModelAndView("sys/dic_frame");
	}
	
	/**
	 * 查询所有的字典列表信息
	 * @return 字典列表信息
	 */
	@RequestMapping(value="/dicList.htm")
	public void dicList(HttpServletResponse response){
		List<Dictionary> list = dictionaryService.search();
		JsonUtil.list2JsonForDate(response, list);
	}
	
	/**
	 * 跳转到保存字典信息页面
	 * @param dicId 字典编码
	 * @param request 请求
	 * @return 保存字典信息页面
	 */
	@RequestMapping(value="/toSave.htm")
	public ModelAndView toSave(String dicId,HttpServletRequest request)
	{
		Dictionary dic = new Dictionary();
		if(null != dicId)
		{
			dic = dictionaryService.get(dicId);
		}
		request.setAttribute("dic", dic);
		return new ModelAndView("sys/dic_save");
	}
	
	/**
	 * 保存字典信息
	 * @param dic 字典信息
	 * @param flag 标志位 add / edit
	 * @param response 响应
	 * 
	 */
	@RequestMapping(value="/save.htm")
	public void save( @ModelAttribute Dictionary dic,String flag,HttpServletResponse response)
	{
		boolean result;
		if(Constants.OPERATOR_ADD.equals(flag))
		{
			result = dictionaryService.insert(dic);
		}
		else
		{
			result = dictionaryService.update(dic);
		}
		JsonUtil.boolOut(response, result);
	}
	
	/**
	 * 删除字典信息
	 * @param dicId 字典编码
	 * @param response 响应
	 */
	@RequestMapping(value="/delete.htm")
	public void delete(String dicId,HttpServletResponse response)
	{
		boolean result = dictionaryService.delete(dicId);
		//更新缓存
		CacheFactory.getInstance().removeDictionary( dicId );
		JsonUtil.boolOut(response, result);
	}
	
	/**
	 * 根据字典编码跳转到字典值列表页面
	 * @param dicId 字典编码
	 * @return 转到字典列表页面
	 */
	@RequestMapping(value="/search.htm")
	public ModelAndView search(String dicId,HttpServletRequest request){
	    request.setAttribute( "dicId", dicId );
		return new ModelAndView("sys/dic_list"); 
	}
	
	/**
	 * 根据字典编码查询字典值信息
	 * @param dicId 字典编码
	 * @param response 响应
	 */
	@RequestMapping(value="/listDicValues.htm")
	public void listDicValues(String dicId,HttpServletResponse response)
	{
	    List<DictionaryValue> list = dictionaryService.query( dicId );
	    JsonUtil.list2Json( response, list );
	}
	
	/**
	 * 保存字典值信息
	 * @param request 请求
	 * @param response 响应
	 */
	@SuppressWarnings("unchecked")
    @RequestMapping(value="/saveDicValues.htm")
	public void saveDicValues(HttpServletRequest request,HttpServletResponse response)
	{
	    String rowdata = request.getParameter( "rowdata" );
	    String dicId = request.getParameter( "dicId" );
	    List<DictionaryValue> list = (List<DictionaryValue>)JSONArray.toCollection( JSONArray.fromObject( rowdata ), DictionaryValue.class );
	    boolean result = dictionaryService.updateDicValues( dicId, list );
	    //更新缓存
	    CacheFactory.getInstance().putDictionary( dicId, list );
	    JsonUtil.boolOut( response, result );
	}

}
