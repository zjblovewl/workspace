package net.ycii.fc.service.impl;

import java.util.List;
import java.util.Map;

import net.ycii.fc.entity.Sms;
import net.ycii.fc.service.ISmsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.annotation.dao.IAnnotaionDao;
import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.utils.ConditionConvert;
import com.tyunsoft.base.utils.Pager;
import com.tyunsoft.base.utils.SqlFactory;

/**
 * 短信信息相关的业务层接口实现
 * 
 * @author  flymz
 * @version  [v1.0, 2015年03月13日]
 */
@Service("smsService")
public class SmsServiceImpl implements ISmsService
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
        return dao.queryPage(getSql("sql_query_sms_count"), getPageSql("sql_query_sms_list",pageNumber,pageSize), ConditionConvert.convert(conditions), Sms.class );
    }
    
    /**
     * 一次性查询表中所有数据
     * @return 表中所有数据
     */
    public List<Object> listAll()
    {
        return dao.queryAllList(Sms.class);
    }

    /**
     * 根据主键查询对象
     * @param id 主键编码
     * @return 查询的对象
     */
    public Object queryById( String id )
    {
        Sms sms = new Sms();
        sms.setId( id );
        return dao.queryByKey( Sms.class, sms );
    }

    /**
     * 根据主键删除对象
     * @param id 主键编码
     * @return 删除是否成功
     */
    public boolean deleteById( String id )
    {
        Sms sms = new Sms();
        sms.setId( id );
        int result = dao.deleteByKey( sms );
        return result == 1;
    }
    
    /**
     * 删除表中所有记录
     * @return 删除是否成功
     */
    public int deleteAll()
    {
    	Sms sms = new Sms();
    	return dao.deleteAll( sms );
    }

    /**
     * 新增短信信息记录
     * @param conveneNews 短信信息对象 
     * @return 新增是否成功
     */
    public boolean insert( Sms sms )
    {
        int result = dao.insert( sms );
        return result == 1;
    }

    /**
     * 根据ID主键更新信息
     * @param conveneNews 短信信息对象
     * @return 更新是否成功
     */
    public boolean updateById( Sms sms )
    {
        int result = dao.updateByKey( sms );
        return result == 1;
    }
    
    private String getSql(String sqlId)
    {
        return SqlFactory.getInstance( "fc_sql.xml", SmsServiceImpl.class ).getSql( sqlId );
    }
    
    private String getPageSql(String sqlId, int pageNumber ,int pageSize)
    {
        return SqlFactory.getInstance( "fc_sql.xml", SmsServiceImpl.class ).getPageSql( sqlId, pageNumber, pageSize );
    }

}
