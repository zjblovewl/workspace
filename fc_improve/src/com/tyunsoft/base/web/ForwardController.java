
package com.tyunsoft.base.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 系统普通页面跳转Controller
 * 
 * @author flymz
 */
@Controller
public class ForwardController
{

    @RequestMapping( value = "/include.htm" )
    public ModelAndView include()
    {
        return new ModelAndView( "include" );
    }

}
