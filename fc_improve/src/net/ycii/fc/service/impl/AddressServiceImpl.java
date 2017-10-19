package net.ycii.fc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.annotation.dao.IAnnotaionDao;
import com.tyunsoft.base.utils.ConditionConvert;
import com.tyunsoft.base.utils.Pager;
import com.tyunsoft.base.utils.SqlFactory;
import com.tyunsoft.base.entity.SearchCondition;
import net.ycii.fc.entity.Address;
import net.ycii.fc.service.IAddressService;

/**
 * 联络人管理相关的业务层接口实现
 * 
 * @author  flymz
 * @version  [v1.0, 2015年04月07日]
 */
@Service("addressService")
public class AddressServiceImpl implements IAddressService
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
        return dao.queryPage(getSql("sql_query_address_count"), getPageSql("sql_query_address_list",pageNumber,pageSize), ConditionConvert.convert(conditions), Address.class );
    }
    
    /**
     * 一次性查询表中所有数据
     * @return 表中所有数据
     */
    public List<Object> listAll()
    {
        return dao.queryAllList(Address.class);
    }

    /**
     * 根据主键查询对象
     * @param id 主键编码
     * @return 查询的对象
     */
    public Object queryById( String id )
    {
        Address address = new Address();
        address.setId( id );
        return dao.queryByKey( Address.class, address );
    }

    /**
     * 根据主键删除对象
     * @param id 主键编码
     * @return 删除是否成功
     */
    public boolean deleteById( String id )
    {
        Address address = new Address();
        address.setId( id );
        int result = dao.deleteByKey( address );
        return result == 1;
    }
    
    /**
     * 删除表中所有记录
     * @return 删除是否成功
     */
    public int deleteAll()
    {
    	Address address = new Address();
    	return dao.deleteAll( address );
    }

    /**
     * 新增联络人管理记录
     * @param conveneNews 联络人管理对象 
     * @return 新增是否成功
     */
    public boolean insert( Address address )
    {
        int result = dao.insert( address );
        return result == 1;
    }

    /**
     * 根据ID主键更新信息
     * @param conveneNews 联络人管理对象
     * @return 更新是否成功
     */
    public boolean updateById( Address address )
    {
        int result = dao.updateByKey( address );
        return result == 1;
    }
    
    private String getSql(String sqlId)
    {
        return SqlFactory.getInstance( "fc_sql.xml", AddressServiceImpl.class ).getSql( sqlId );
    }
    
    private String getPageSql(String sqlId, int pageNumber ,int pageSize)
    {
        return SqlFactory.getInstance( "fc_sql.xml", AddressServiceImpl.class ).getPageSql( sqlId, pageNumber, pageSize );
    }

}
