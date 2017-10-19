
package com.tyunsoft.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.entity.Duty;
import com.tyunsoft.base.service.IDutyService;
import com.tyunsoft.base.utils.SqlFactory;
import com.tyunsoft.base.utils.SqlXml;

/**
 * 职务信息管理业务层接口实现
 * 
 * @author flymz
 */
@Service
public class DutyServiceImpl implements IDutyService
{

    @Autowired
    private IDao dao;

    /**
     * 删除职务信息
     * 
     * @param dutyIds
     *            职务编码，多个用,号隔开
     * @return 删除是否成功
     */
    public boolean delete( String dutyIds )
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put( ":dutyids", SqlXml.getInParams( dutyIds ) );
        String sql = SqlFactory.getInstance().getSql( "sql_delete_dutys", map );
        int result = dao.update( sql );
        return result == 1;
    }

    /**
     * 新增职务信息
     * 
     * @param duty
     *            职务信息
     * @return 是否成功
     */
    public boolean insert( Duty duty )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_insert_duty" );
        int result = dao.update( sql, new Object[] {duty.getDutyId(),
                duty.getDutyName(), duty.getDutyDesc()} );
        return result == 1;
    }

    /**
     * 查询是否存在同名的职务名称
     * 
     * @param dutyName
     *            职务名称
     * @param dutyId
     *            职务编码
     * @return 是否存在
     */
    public boolean existName( String dutyName, String dutyId )
    {
        dutyId = "".equals( dutyId ) ? "-1" : dutyId;
        String sql = SqlFactory.getInstance().getSql( "sql_query_exist_name" );
        int result = dao.findForInt( sql, new Object[] {dutyName, dutyId,
                dutyId} );
        return result != 0;
    }

    /**
     * 查询职务信息列表
     * 
     * @return 职务信息列表
     */
    public List<Duty> list()
    {
        String sql = SqlFactory.getInstance().getSql( "sql_search_list" );
        SqlRowSet rs = dao.find( sql );
        List<Duty> dutys = new ArrayList<Duty>();
        Duty duty = null;
        while ( rs.next() )
        {
            duty = new Duty();
            duty.setDutyId( rs.getString( "duty_id" ) );
            duty.setDutyName( rs.getString( "duty_name" ) );
            duty.setDutyDesc( rs.getString( "duty_desc" ) );
            dutys.add( duty );
        }
        return dutys;
    }

    /**
     * 根据职务编码查询职务信息
     * 
     * @param dutyId
     *            职务编码
     * @return 职务信息
     */
    public Duty query( String dutyId )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_query_duty" );
        SqlRowSet rs = dao.find( sql, new Object[] {dutyId} );
        Duty duty = new Duty();
        while ( rs.next() )
        {
            duty.setDutyId( rs.getString( "duty_id" ) );
            duty.setDutyName( rs.getString( "duty_name" ) );
            duty.setDutyDesc( rs.getString( "duty_desc" ) );
        }
        return duty;
    }

    /**
     * 更新职务信息
     * 
     * @param duty
     *            职务信息
     * @return 更新是否成功
     */
    public boolean update( Duty duty )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_update_duty" );
        int result = dao.update( sql, new Object[] {duty.getDutyName(),
                duty.getDutyDesc(), duty.getDutyId()} );
        return result == 1;
    }

}
