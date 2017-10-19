/*
 * 文 件 名:  CommonController.java
 * 版    权:  Foresoft Technologies,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2015年2月13日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package net.ycii.fc.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ycii.fc.entity.TreeNode;
import net.ycii.fc.service.ICommonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tyunsoft.base.utils.JsonUtil;

/**
 * 通用或者公共请求
 * 
 * @author  qinyong
 * @version  [版本号, 2015年2月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping("common")
public class CommonController
{
    @Autowired
    ICommonService commonService;
    
    @RequestMapping("initTree.htm")
    public ModelAndView initTree(HttpServletRequest request, HttpServletResponse response)
    {
        List<TreeNode> nodes = commonService.getdeptUserTree();
        String jsonStr = JsonUtil.list2Json( nodes );
        request.setAttribute( "jsonStr", jsonStr.replace( "\"", "'" ) );
        return new ModelAndView("commonTree");
    }
}
