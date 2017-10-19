
package com.tyunsoft.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.entity.Dept;
import com.tyunsoft.base.service.IDeptService;
import com.tyunsoft.base.utils.SqlFactory;

/**
 * 部门管理业务层接口实现
 * 
 * @author flymz
 */
@Service( "deptService" )
public class DeptServiceImpl implements IDeptService
{

    @Autowired
    private IDao dao;

    /**
     * 获取部门列表树数据
     * 
     * @return 列表树
     */
    public List<Dept> list()
    {
        String sql = SqlFactory.getInstance().getSql( "sql_search_depts" );
        List<Dept> result = new ArrayList<Dept>();
        SqlRowSet rs = dao.find( sql );
        Map<String, Dept> map = new HashMap<String, Dept>();
        Dept dept = null;
        while ( rs.next() )
        {
            dept = new Dept();
            dept.setDeptId( rs.getString( "dept_id" ) );
            dept.setDeptName( rs.getString( "dept_name" ) );
            dept.setDeptShortName(rs.getString("dept_short_name"));
            dept.setDeptPhone( rs.getString( "dept_phone" ) );
            dept.setDeptFax( rs.getString( "dept_fax" ) );
            dept.setDeptAddress( rs.getString( "dept_address" ) );
            dept.setPDeptId( rs.getString( "p_dept_id" ) );
            dept.setDeptLeader( rs.getString( "dept_leader" ) );
            dept.setDeptRemark( rs.getString( "dept_remark" ) );
            dept.setDeptLevel( rs.getInt( "dept_level" ) );
            if ( Dept.TOP_DEPT_ID.equals( dept.getPDeptId() ) )
            {// 顶级部门
                result.add( dept );
                map.put( dept.getDeptId(), dept );
            } else
            {
                map.get( dept.getPDeptId() ).getChildren().add( dept );
                map.put( dept.getDeptId(), dept );
            }
        }

        return result;
    }
    
    /**
     * 查询所有二级部门
     * @return 二级部门
     */
    public List<Dept> children()
    {
    	String sql = SqlFactory.getInstance().getSql("sql_query_children_depts");
    	SqlRowSet rs = dao.find(sql);
    	List<Dept> result = new ArrayList<Dept>();
    	Dept dept = null;
    	while(rs.next())
    	{
    		dept = new Dept();
    		dept.setDeptId(rs.getString("DEPT_ID"));
    		dept.setDeptName(rs.getString("DEPT_NAME"));
    		result.add(dept);
    	}
    	return result;
    }

    /**
     * 增加部门
     * 
     * @param dept
     *            待增加的部门信息
     * @return 增加是否成功
     */
    public boolean add( Dept dept )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_add_dept" );
        int result = dao.add( sql, new Object[] {dept.getDeptId(),
                dept.getDeptName(),dept.getDeptShortName(),dept.getDeptPhone(), dept.getDeptFax(),
                dept.getDeptAddress(), dept.getPDeptId(), dept.getDeptLeader(),
                dept.getDeptLevel(), dept.getDeptRemark(), dept.getCreator(),
                dept.getCreateDate()} );
        return result == 1;
    }

    /**
     * 修改部门信息
     * 
     * @param dept
     *            待修改的部门信息
     * @return 修改是否成功
     */
    public boolean update( Dept dept )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_edit_dept" );
        int result = dao.update( sql, new Object[] {dept.getDeptName(),dept.getDeptShortName(),
                dept.getDeptPhone(), dept.getDeptFax(), dept.getDeptAddress(),
                dept.getDeptLeader(), dept.getDeptRemark(), dept.getDeptId()} );
        return result == 1;
    }

    /**
     * 删除部门
     * 
     * @param deptId
     *            需要删除的部门ID
     * @return 删除是否成功
     */
    public boolean delete( String deptId )
    {
        // 更新部门中的所有用户部门编码为空
        String userSql = SqlFactory.getInstance().getSql(
                "sql_update_user_dept" );
        dao.update( userSql, new Object[] {deptId} );
        String sql = SqlFactory.getInstance().getSql( "sql_delete_dept" );
        int result = dao.update( sql, new Object[] {deptId} );
        return result == 1;
    }

    /**
     * 根据部门编码查询部门的详细信息
     * 
     * @param deptId
     *            部门编码
     * @return 部门详细信息
     */
    public Dept query( String deptId )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_query_dept" );
        Dept dept = null;
        SqlRowSet rs = dao.find( sql, new Object[] {deptId} );
        while ( rs.next() )
        {
            dept = new Dept();
            dept.setDeptId( rs.getString( "dept_id" ) );
            dept.setDeptName( rs.getString( "dept_name" ) );
            dept.setDeptShortName(rs.getString("dept_short_name"));
            dept.setDeptPhone( rs.getString( "dept_phone" ) );
            dept.setDeptFax( rs.getString( "dept_fax" ) );
            dept.setDeptAddress( rs.getString( "dept_address" ) );
            dept.setPDeptId( rs.getString( "p_dept_id" ) );
            dept.setPDeptName( rs.getString( "p_dept_name" ) );
            dept.setDeptLeader( rs.getString( "dept_leader" ) );
            dept.setDeptRemark( rs.getString( "dept_remark" ) );
            dept.setDeptLevel( rs.getInt( "dept_level" ) );
        }
        return dept;
    }

}
