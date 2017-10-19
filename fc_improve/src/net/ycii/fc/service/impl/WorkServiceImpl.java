package net.ycii.fc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ycii.fc.entity.Work;
import net.ycii.fc.service.IWorkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.annotation.dao.IAnnotaionDao;
import com.tyunsoft.base.common.SessionHelper;
import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.service.IUserService;
import com.tyunsoft.base.utils.ConditionConvert;
import com.tyunsoft.base.utils.PageUtil;
import com.tyunsoft.base.utils.Pager;
import com.tyunsoft.base.utils.SqlFactory;

/**
 * 工作记录相关的业务层接口实现
 * 
 * @author  flymz
 * @version  [v1.0, 2015年02月26日]
 */
@Service("workService")
public class WorkServiceImpl implements IWorkService
{
    
    @Autowired
    private IAnnotaionDao dao;
    
    @Autowired
    private IDao queryDao;
    
    @Autowired
    private IUserService userService;

    /**
     * 查询分页记录数
     * @param pageNumber 请求页数
     * @param pageSize 每页记录数
     * @param conditions 查询条件
     * @return 查询的分页对象
     */
    public Pager list( int pageNumber, int pageSize, List<SearchCondition> conditions  )
    {
        Pager pager = new Pager();
        pager.setTotal( dao.queryCount( getSql("sql_query_work_count"), ConditionConvert.convert(conditions) ) );
        SqlRowSet rs = queryDao.find( getPageSql("sql_query_work_list",pageNumber,pageSize), ConditionConvert.convert(conditions) );
        List<Map<String,String>> result = new ArrayList<Map<String,String>>();
        Map<String,String> map = null;
        while(rs.next())
        {
             map = new HashMap<String,String>();
             map.put( "id", rs.getString( "id" ) );
             map.put( "meeting", rs.getString( "meeting" ) );
             map.put( "performance", rs.getString( "performance" ) );
             map.put( "recordTime", rs.getString( "record_time" ) );
             map.put( "userName", rs.getString( "user_name" ) );
             map.put( "deptName", rs.getString( "dept_name" ) );
             map.put( "recordAddress", rs.getString( "record_address" ) );
             String recordImages = rs.getString( "record_images" );
             if(recordImages != null)
             {
                 recordImages = recordImages.replaceAll( "upload", PageUtil.getBasePath( SessionHelper.getRequest() )+"upload" );
             }else
             {
                 recordImages = "";
             }
             map.put( "recordImages", recordImages );
             result.add( map );
        }
        pager.setRows( result );
        
        return pager;//dao.queryPage(getSql("sql_query_work_count"), getPageSql("sql_query_work_list",pageNumber,pageSize), ConditionConvert.convert(conditions), Work.class );
    }
    
    /**
     * 查询分页记录数
     * @param pageNumber 请求页数
     * @param pageSize 每页记录数
     * @param conditions 查询条件
     * @return 查询的分页对象
     */
    public Pager listUserWorks( int pageNumber, int pageSize, List<SearchCondition> conditions  )
    {
        return dao.queryPage(getSql("sql_query_user_work_count"), getPageSql("sql_query_user_work_list",pageNumber,pageSize), ConditionConvert.convert(conditions), Work.class );
    }
    
    /**
     * 一次性查询表中所有数据
     * @return 表中所有数据
     */
    public List<Object> listAll()
    {
        return dao.queryAllList(Work.class);
    }

    /**
     * 根据主键查询对象
     * @param id 主键编码
     * @return 查询的对象
     */
    public Object queryById( String id )
    {
        Work work = new Work();
        work.setId( id );
        work = (Work)dao.queryByKey( Work.class, work );
        work.setRecorderName( userService.query( work.getRecorder() ).getUserName() );
        return work;
    }

    /**
     * 根据主键删除对象
     * @param id 主键编码
     * @return 删除是否成功
     */
    public boolean deleteById( String id )
    {
        Work work = new Work();
        work.setId( id );
        int result = dao.deleteByKey( work );
        return result == 1;
    }
    
    /**
     * 删除表中所有记录
     * @return 删除是否成功
     */
    public int deleteAll()
    {
    	Work work = new Work();
    	return dao.deleteAll( work );
    }

    /**
     * 新增工作记录记录
     * @param conveneNews 工作记录对象 
     * @return 新增是否成功
     */
    public boolean insert( Work work )
    {
      //先更新当前签到的所有工作记录的isValid为0
        dao.executeUpdate( getSql("sql_update_work_is_valid"), new Object[]{work.getSignId()} );
        work.setIsValid( "1" );
        int result = dao.insert( work );
        return result == 1;
    }

    /**
     * 根据ID主键更新信息
     * @param conveneNews 工作记录对象
     * @return 更新是否成功
     */
    public boolean updateById( Work work )
    {
        int result = dao.updateByKey( work );
        return result == 1;
    }
    
    private String getSql(String sqlId)
    {
        return SqlFactory.getInstance( "fc_sql.xml", WorkServiceImpl.class ).getSql( sqlId );
    }
    
    private String getPageSql(String sqlId, int pageNumber ,int pageSize)
    {
        return SqlFactory.getInstance( "fc_sql.xml", WorkServiceImpl.class ).getPageSql( sqlId, pageNumber, pageSize );
    }

}
