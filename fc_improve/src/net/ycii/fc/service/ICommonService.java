/*
 * 文 件 名:  CommonServiceImpl.java
 * 版    权:  Foresoft Technologies,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2015年2月13日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package net.ycii.fc.service;

import java.util.List;

import net.ycii.fc.entity.TreeNode;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  qinyong
 * @version  [版本号, 2015年2月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ICommonService
{
    List<TreeNode> getdeptUserTree();
}
