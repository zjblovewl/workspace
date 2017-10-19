
package com.tyunsoft.base.service;

import java.util.List;

import com.tyunsoft.base.entity.Dept;

/**
 * 部门管理业务层接口
 * 
 * @author flymz
 */
public interface IDeptService
{
    /**
     * 获取部门列表树数据
     * 
     * @return 列表树
     */
    List<Dept> list();
    
    /**
     * 查询所有二级部门
     * @return 二级部门
     */
    List<Dept> children();

    /**
     * 增加部门
     * 
     * @param dept
     *            待增加的部门信息
     * @return 增加是否成功
     */
    boolean add( Dept dept );

    /**
     * 修改部门信息
     * 
     * @param dept
     *            待修改的部门信息
     * @return 修改是否成功
     */
    boolean update( Dept dept );

    /**
     * 删除部门
     * 
     * @param deptId
     *            需要删除的部门ID
     * @return 删除是否成功
     */
    boolean delete( String deptId );

    /**
     * 根据部门编码查询部门的详细信息
     * 
     * @param deptId
     *            部门编码
     * @return 部门详细信息
     */
    Dept query( String deptId );
}
