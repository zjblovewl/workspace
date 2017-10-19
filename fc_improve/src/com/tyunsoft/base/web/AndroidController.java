package com.tyunsoft.base.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tyunsoft.base.utils.JsonUtil;
import com.tyunsoft.base.utils.Read;
/**
 * android 检查版本更新
 * 
 * @author  Flyer.zuo
 * @version  [版本号, 2014年10月29日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
public class AndroidController
{

    @RequestMapping("free/checkVersion.htm")
    public void checkVersion(HttpServletResponse response)
    {
        JsonUtil.strOut( response, Read.getMsg( "android.version" ) );
    }
    
}
