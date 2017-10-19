
package com.tyunsoft.base.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tyunsoft.base.common.FunctionBuilder;
import com.tyunsoft.base.entity.Duty;
import com.tyunsoft.base.service.IDutyService;
import com.tyunsoft.base.utils.IDUtil;
import com.tyunsoft.base.utils.JsonUtil;

/**
 * 职务管理
 * 
 * @author flymz
 */
@Controller
@RequestMapping( "/duty" )
public class DutyController
{

    @Autowired
    private IDutyService dutyService;

    /**
     * 跳转到职务信息列表页面
     * 
     * @param request
     *            请求
     * @return
     */
    @RequestMapping( value = "/search.htm" )
    public ModelAndView search( HttpServletRequest request )
    {
        request.setAttribute( "functionStr", FunctionBuilder.build() );
        return new ModelAndView( "sys/duty_list" );
    }

    /**
     * 查询职务信息列表数据
     * 
     * @param response
     */
    @RequestMapping( value = "/list.htm" )
    public void list( HttpServletResponse response )
    {
        List<Duty> dutys = dutyService.list();
        JsonUtil.list2Json( response, dutys );
    }

    /**
     * 跳转到新增或者编辑页面
     * 
     * @return
     */
    @RequestMapping( value = "/tosave.htm" )
    public ModelAndView tosave( String dutyId, HttpServletRequest request )
    {
        Duty duty = new Duty();
        if ( null != dutyId )
        {
            duty = dutyService.query( dutyId );
        }
        request.setAttribute( "duty", duty );
        return new ModelAndView( "sys/duty_save" );
    }

    /**
     * 保存职务信息
     * 
     * @param duty
     *            职务信息
     * @param response
     *            响应
     */
    @RequestMapping( value = "/save.htm" )
    public void save( Duty duty, HttpServletResponse response )
    {
        if ( dutyService.existName( duty.getDutyName(), duty.getDutyId() ) )
        {
            JsonUtil.strOut( response, "exist" );
        } else
        {
            boolean result = false;
            if ( "".equals( duty.getDutyId() ) )
            {
                duty.setDutyId( IDUtil.getUUIDStr() );
                result = dutyService.insert( duty );
            } else
            {
                result = dutyService.update( duty );
            }
            JsonUtil.boolOut( response, result );
        }
    }

    /**
     * 删除职务信息
     * 
     * @param dutyIds
     *            职务信息，使用,号隔开
     * @param response
     *            响应
     */
    @RequestMapping( value = "/delete.htm" )
    public void delete( String dutyIds, HttpServletResponse response )
    {
        boolean result = dutyService.delete( dutyIds );
        JsonUtil.boolOut( response, result );
    }
}
