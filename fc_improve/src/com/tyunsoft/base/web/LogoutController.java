
package com.tyunsoft.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogoutController
{

    @RequestMapping( value = "/free/logout.htm" )
    public ModelAndView logout( HttpServletRequest request )
    {

        HttpSession session = request.getSession();
        session.removeAttribute( "currentUser" );
        session.removeAttribute( "userId" );
        session.removeAttribute( "userName" );
        session.invalidate();
        return new ModelAndView( "redirect:/index.jsp" );
    }

}
