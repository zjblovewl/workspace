
package com.tyunsoft.base.service;

import java.util.List;

import com.tyunsoft.base.entity.Duty;

/**
 * 职务信息管理业务层接口
 * 
 * @author flymz
 */
public interface IDutyService
{

    /**
     * 查询职务信息列表
     * 
     * @return 职务信息列表
     */
    List<Duty> list();

    /**
     * 新增职务信息
     * 
     * @param duty
     *            职务信息
     * @return 是否成功
     */
    boolean insert( Duty duty );

    /**
     * 根据职务编码查询职务信息
     * 
     * @param dutyId
     *            职务编码
     * @return 职务信息
     */
    Duty query( String dutyId );

    /**
     * 查询是否存在同名的职务名称
     * 
     * @param dutyName
     *            职务名称
     * @param dutyId
     *            职务编码
     * @return 是否存在
     */
    boolean existName( String dutyName, String dutyId );

    /**
     * 更新职务信息
     * 
     * @param duty
     *            职务信息
     * @return 更新是否成功
     */
    boolean update( Duty duty );

    /**
     * 删除职务信息
     * 
     * @param dutyIds
     *            职务编码，多个用,号隔开
     * @return 删除是否成功
     */
    boolean delete( String dutyIds );

}
