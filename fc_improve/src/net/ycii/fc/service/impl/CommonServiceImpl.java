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
package net.ycii.fc.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.ycii.fc.entity.TreeNode;
import net.ycii.fc.service.ICommonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.annotation.dao.IAnnotaionDao;
import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.utils.SqlFactory;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  qinyong
 * @version  [版本号, 2015年2月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("commonService")
public class CommonServiceImpl implements ICommonService
{
    @Autowired
    private IDao queryDao;
    
    @Autowired
    private IAnnotaionDao dao;
    
    public List<TreeNode> getdeptUserTree()
    {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        SqlRowSet rs = queryDao.find(getSql( "common_tree_getDeptUserTree" ));
        TreeNode treeNode = null;
        while ( rs.next() )
        {
            treeNode = new TreeNode();
            treeNode.setNid( rs.getString( "nid" ) );
            treeNode.setnPid( rs.getString( "nPid" ) );
            treeNode.setName( rs.getString( "name" ) );
            treeNode.setTitle( rs.getString( "title" ) );
            treeNode.setIsParent( rs.getString( "isParent" ) );
            treeNodes.add( treeNode );
        }
        return treeNodes;
    }
    
    private String getSql(String sqlId)
    {
      return SqlFactory.getInstance("fc_sql.xml", CommonServiceImpl.class).getSql(sqlId);
    }    
    
    
}
