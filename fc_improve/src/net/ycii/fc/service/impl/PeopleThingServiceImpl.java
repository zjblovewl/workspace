package net.ycii.fc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ycii.fc.entity.PeopleThing;
import net.ycii.fc.service.IPeopleThingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.annotation.dao.IAnnotaionDao;
import com.tyunsoft.base.common.SessionHelper;
import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.service.IUserService;
import com.tyunsoft.base.utils.PageUtil;
import com.tyunsoft.base.utils.Pager;
import com.tyunsoft.base.utils.SqlFactory;

/**
 * 民生实事相关的业务层接口实现
 * 
 * @author  flymz
 * @version  [v1.0, 2015年03月10日]
 */
@Service("peopleThingService")
public class PeopleThingServiceImpl implements IPeopleThingService
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
    public Pager list( int pageNumber, int pageSize, String userName ,String dept,String startDate, String endDate,String town,String village )
    {
        Pager pager = new Pager();
        int total = queryDao.findForInt( getSql("sql_query_peoplething_count"), new Object[]{"%"+dept+"%","%"+userName+"%",startDate,endDate,"%"+town+"%","%"+village+"%"} );
        pager.setTotal( total );
        SqlRowSet rs = queryDao.find( getPageSql("sql_query_peoplething_list",pageNumber,pageSize), new Object[]{"%"+dept+"%","%"+userName+"%",startDate,endDate,"%"+town+"%","%"+village+"%"} );
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        Map<String,String> map = null;
        int i = 0;
        while(rs.next())
        {
            map = new HashMap<String,String>();
            map.put( "index", String.valueOf( ++i ) );
            map.put( "deptName", rs.getString( "dept_name" ) );
            map.put( "userName", rs.getString( "user_name" ) );
            map.put( "userPhone", rs.getString( "user_phone" ) );
            map.put( "content", rs.getString( "content" ) );
            map.put( "progress", rs.getString( "progress" ) );
            map.put( "problem",rs.getString( "problem" ) );
            map.put( "createDate", rs.getString( "create_date" ) );
            map.put( "town", rs.getString( "town" ) );
            map.put( "village", rs.getString( "village" ) );
            String photos = rs.getString( "photos" );
            if(null != photos)
            {
                map.put( "photos", photos.replaceAll( "upload", PageUtil.getBasePath( SessionHelper.getRequest() ) +"upload" ) );
            }
            map.put( "id", rs.getString( "id" ) );
            list.add( map );
        }
        pager.setRows( list );
        return pager;
    }
    
    /**
     * 一次性查询表中所有数据
     * @return 表中所有数据
     */
    public List<Object> listAll()
    {
        return dao.queryAllList(PeopleThing.class);
    }

    /**
     * 根据主键查询对象
     * @param id 主键编码
     * @return 查询的对象
     */
    public Object queryById( String id )
    {
        PeopleThing peopleThing = new PeopleThing();
        peopleThing.setId( id );
        peopleThing = (PeopleThing)dao.queryByKey( PeopleThing.class, peopleThing );
        peopleThing.setCreateUserName( userService.query( peopleThing.getCreateUser() ).getUserName() );
        return peopleThing;
    }

    /**
     * 根据主键删除对象
     * @param id 主键编码
     * @return 删除是否成功
     */
    public boolean deleteById( String id )
    {
        PeopleThing peopleThing = new PeopleThing();
        peopleThing.setId( id );
        int result = dao.deleteByKey( peopleThing );
        return result == 1;
    }
    
    /**
     * 删除表中所有记录
     * @return 删除是否成功
     */
    public int deleteAll()
    {
    	PeopleThing peopleThing = new PeopleThing();
    	return dao.deleteAll( peopleThing );
    }

    /**
     * 新增民生实事记录
     * @param conveneNews 民生实事对象 
     * @return 新增是否成功
     */
    public boolean insert( PeopleThing peopleThing )
    {
        int result = dao.insert( peopleThing );
        return result == 1;
    }

    /**
     * 根据ID主键更新信息
     * @param conveneNews 民生实事对象
     * @return 更新是否成功
     */
    public boolean updateById( PeopleThing peopleThing )
    {
        int result = dao.updateByKey( peopleThing );
        return result == 1;
    }
    
    private String getSql(String sqlId)
    {
        return SqlFactory.getInstance( "fc_sql.xml", PeopleThingServiceImpl.class ).getSql( sqlId );
    }
    
    private String getPageSql(String sqlId, int pageNumber ,int pageSize)
    {
        return SqlFactory.getInstance( "fc_sql.xml", PeopleThingServiceImpl.class ).getPageSql( sqlId, pageNumber, pageSize );
    }

}
