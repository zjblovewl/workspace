package net.ycii.fc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import net.ycii.fc.entity.ThingReport;
import net.ycii.fc.service.IThingReportService;

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
 * 事件上报相关的业务层接口实现
 * 
 * @author  flymz
 * @version  [v1.0, 2015年02月26日]
 */
@Service("thingReportService")
public class ThingReportServiceImpl implements IThingReportService
{
    private static ThingReportServiceImpl instance;
    @Autowired
    private IAnnotaionDao dao;
    @Autowired
    private IDao queryDao;
    
    @Autowired
    private IUserService userService;
    
    public static ThingReportServiceImpl getIntance()
    {
        return instance;
    }
    
    @PostConstruct
    public void init()
    {
        ThingReportServiceImpl.instance = this;
    }
    

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
        pager.setTotal( dao.queryCount( getSql("sql_query_thingreport_count"), ConditionConvert.convert(conditions) ) );
        SqlRowSet rs = queryDao.find( getPageSql("sql_query_thingreport_list",pageNumber,pageSize), ConditionConvert.convert(conditions) );
        List<Map<String,String>> result = new ArrayList<Map<String,String>>();
        Map<String,String> map = null;
        while(rs.next())
        {
             map = new HashMap<String,String>();
             map.put( "id", rs.getString( "id" ) );
             map.put( "appealerName", rs.getString( "appealer_name" ) );
             map.put( "appealerPhone", rs.getString( "appealer_phone" ) );
             map.put( "appealerAddress", rs.getString( "appealer_address" ) );
             map.put( "deptName", rs.getString( "dept_name" ) );
             map.put( "reporter", rs.getString( "user_name" ) );
             map.put( "description", rs.getString( "description" ) );
             map.put( "appealResult", rs.getString( "appeal_result" ) );
             map.put( "reportTime", rs.getString( "report_time" ).substring( 0, 19 ) );
             map.put( "deptName", rs.getString( "dept_name" ) );
             String appealImages = rs.getString( "appeal_images" );
             if(null != appealImages)
             {
                 appealImages = appealImages.replaceAll( "upload", PageUtil.getBasePath( SessionHelper.getRequest() ) + "upload" );
             }
             map.put( "appealImages",appealImages );
             result.add( map );
        }
        pager.setRows( result );
        return pager;//dao.queryPage(getSql("sql_query_thingreport_count"), getPageSql("sql_query_thingreport_list",pageNumber,pageSize), ConditionConvert.convert(conditions), ThingReport.class );
    }
    
    public Map<String,String> getCompanyInfo(String userId)
    {
        Map<String,String> map = new HashMap<String,String>();
        SqlRowSet rs = queryDao.find( getSql("sql_query_company_info"),new Object[] {userId});
        while(rs.next())
        {
             map = new HashMap<String,String>();
             map.put( "deptName", rs.getString( "dept_name" ) );
             map.put( "userPhone", rs.getString( "user_phone" ) );
             map.put( "userName", rs.getString( "user_name" ) );
             map.put( "userAddress", rs.getString( "town" ) );
        }
        return map;
    }    
    
    /**
     * 查询分页记录数
     * @param pageNumber 请求页数
     * @param pageSize 每页记录数
     * @param conditions 查询条件
     * @return 查询的分页对象
     */
    public Pager listUserReports( int pageNumber, int pageSize, List<SearchCondition> conditions  )
    {
        return dao.queryPage(getSql("sql_query_user_thingreport_count"), getPageSql("sql_query_user_thingreport_list",pageNumber,pageSize), ConditionConvert.convert(conditions), ThingReport.class );
    }
    
    /**
     * 一次性查询表中所有数据
     * @return 表中所有数据
     */
    public List<Object> listAll()
    {
        return dao.queryAllList(ThingReport.class);
    }

    /**
     * 根据主键查询对象
     * @param id 主键编码
     * @return 查询的对象
     */
    public Object queryById( String id )
    {
        ThingReport thingReport = new ThingReport();
        thingReport.setId( id );
        thingReport = (ThingReport)dao.queryByKey( ThingReport.class, thingReport );
        thingReport.setReporterName( userService.query( thingReport.getReporter() ).getUserName() );
        return thingReport;
    }
    public Object queryByFormId( String formId )
    {
        return dao.query( getSql("sql_query_thingreport_by_slsbid"), new Object[]{formId}, ThingReport.class );
    }

    /**
     * 根据主键删除对象
     * @param id 主键编码
     * @return 删除是否成功
     */
    public boolean deleteById( String id )
    {
        ThingReport thingReport = new ThingReport();
        thingReport.setId( id );
        int result = dao.deleteByKey( thingReport );
        return result == 1;
    }
    public boolean deleteBySlsbId( String slsbId )
    {
//        int result = queryDao.delete( "sql_delete_thingreport_by_slsbid", slsbId );
        int result = queryDao.delete( getSql("sql_delete_thingreport_by_slsbid"), slsbId );
        return result == 1;
    }
    
    /**
     * 删除表中所有记录
     * @return 删除是否成功
     */
    public int deleteAll()
    {
    	ThingReport thingReport = new ThingReport();
    	return dao.deleteAll( thingReport );
    }

    /**
     * 新增事件上报记录
     * @param conveneNews 事件上报对象 
     * @return 新增是否成功
     */
    public boolean insert( ThingReport thingReport )
    {
        //先更新当前签到的所有问题办理的isValid为0
        dao.executeUpdate( getSql("sql_update_thing_report_is_valid"), new Object[]{thingReport.getSignId()} );
        
        int result = dao.insert( thingReport );
        return result == 1;
    }

    /**
     * 根据ID主键更新信息
     * @param conveneNews 事件上报对象
     * @return 更新是否成功
     */
    public boolean updateById( ThingReport thingReport )
    {
        int result = dao.updateByKey( thingReport );
        return result == 1;
    }
    
    private String getSql(String sqlId)
    {
        return SqlFactory.getInstance( "fc_sql.xml", ThingReportServiceImpl.class ).getSql( sqlId );
    }
    
    private String getPageSql(String sqlId, int pageNumber ,int pageSize)
    {
        return SqlFactory.getInstance( "fc_sql.xml", ThingReportServiceImpl.class ).getPageSql( sqlId, pageNumber, pageSize );
    }

}
