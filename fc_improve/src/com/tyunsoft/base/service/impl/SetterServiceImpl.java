package com.tyunsoft.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.annotation.dao.IAnnotaionDao;
import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.entity.Setter;
import com.tyunsoft.base.service.ISetterService;
import com.tyunsoft.base.utils.ConditionConvert;
import com.tyunsoft.base.utils.Pager;
import com.tyunsoft.base.utils.SqlFactory;

/**
 * 系统设置相关的业务层接口实现
 * 
 * @author  flymz
 * @version  [v1.0, 2014年12月26日]
 */
@Service("setterService")
public class SetterServiceImpl implements ISetterService
{
    
    @Autowired
    private IAnnotaionDao dao;

    /**
     * 查询分页记录数
     * @param pageNumber 请求页数
     * @param pageSize 每页记录数
     * @param conditions 查询条件
     * @return 查询的分页对象
     */
    public Pager list( int pageNumber, int pageSize, List<SearchCondition> conditions  )
    {
        return dao.queryPage(getSql("sql_query_setter_count"), getPageSql("sql_query_setter_list",pageNumber,pageSize), ConditionConvert.convert(conditions), Setter.class );
    }
    
    /**
     * 一次性查询表中所有数据
     * @return 表中所有数据
     */
    public List<Object> listAll()
    {
        return dao.queryAllList(Setter.class);
    }

    /**
     * 根据主键查询对象
     * @param id 主键编码
     * @return 查询的对象
     */
    public Object queryById( String id )
    {
        Setter setter = new Setter();
        setter.setId( id );
        return dao.queryByKey( Setter.class, setter );
    }

    /**
     * 根据主键删除对象
     * @param id 主键编码
     * @return 删除是否成功
     */
    public boolean deleteById( String id )
    {
        Setter setter = new Setter();
        setter.setId( id );
        int result = dao.deleteByKey( setter );
        return result == 1;
    }
    
    /**
     * 删除表中所有记录
     * @return 删除是否成功
     */
    public int deleteAll()
    {
    	Setter setter = new Setter();
    	return dao.deleteAll( setter );
    }

    /**
     * 新增系统设置记录
     * @param conveneNews 系统设置对象 
     * @return 新增是否成功
     */
    public boolean insert( Setter setter )
    {
        int result = dao.insert( setter );
        return result == 1;
    }

    /**
     * 根据ID主键更新信息
     * @param conveneNews 系统设置对象
     * @return 更新是否成功
     */
    public boolean updateById( Setter setter )
    {
        int result = dao.updateByKey( setter );
        return result == 1;
    }
    
    /**
     * 检查设置名称是否存在
     * @param id 设置编码
     * @param name 设置名称
     * @return 是否存在
     */
    public boolean checkNameExist(String id,String name)
    {
        int result = dao.queryCount( getSql("sql_exist_setter_name"), new Object[]{id, name} );
        return result > 0;
    }
    
    private String getSql(String sqlId)
    {
        return SqlFactory.getInstance( "system_sql.xml", SetterServiceImpl.class ).getSql( sqlId );
    }
    
    private String getPageSql(String sqlId, int pageNumber ,int pageSize)
    {
        return SqlFactory.getInstance( "system_sql.xml", SetterServiceImpl.class ).getPageSql( sqlId, pageNumber, pageSize );
    }

}
