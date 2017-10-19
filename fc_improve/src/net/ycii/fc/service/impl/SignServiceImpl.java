package net.ycii.fc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import net.ycii.fc.entity.Sign;
import net.ycii.fc.service.ISignService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.annotation.dao.IAnnotaionDao;
import com.tyunsoft.base.common.CacheFactory;
import com.tyunsoft.base.common.SessionHelper;
import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.utils.ConditionConvert;
import com.tyunsoft.base.utils.PageUtil;
import com.tyunsoft.base.utils.Pager;
import com.tyunsoft.base.utils.SqlFactory;

/**
 * 签到签退信息相关的业务层接口实现
 * 
 * @author  flymz
 * @version  [v1.0, 2015年02月11日]
 */
@Service("signService")
public class SignServiceImpl implements ISignService
{
    
    private static SignServiceImpl instance;
    
    @Autowired
    private IAnnotaionDao dao;
    
    @Autowired
    private IDao queryDao;

    public static SignServiceImpl getIntance()
    {
        return instance;
    }
    
    @PostConstruct
    public void init()
    {
        SignServiceImpl.instance = this;
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
        int total = queryDao.findForInt( getSql("sql_query_sign_count"), ConditionConvert.convert(conditions) );
        pager.setTotal( total );
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        Map<String,String> map = null;
        SqlRowSet rs = queryDao.find( getPageSql("sql_query_sign_list",pageNumber,pageSize), ConditionConvert.convert(conditions) );
        while(rs.next())
        {
            map = new HashMap<String, String>();
            map.put( "userId", rs.getString( "user_name" ) );
            map.put( "deptName", rs.getString( "dept_name" ) );
            map.put( "typeStr", CacheFactory.getInstance().getDictionaryText( "signtype", rs.getString( "type" ) ) );
            map.put( "address", rs.getString( "address" ) );
            map.put( "handAddress", rs.getString( "hand_address" ) );
            map.put( "signTime", rs.getString( "sign_time" ) );
            map.put( "id", rs.getString( "id" ) );
            list.add( map );
        }
        pager.setRows( list );
        return pager;
        //return dao.queryPage(getSql("sql_query_sign_count"), getPageSql("sql_query_sign_list",pageNumber,pageSize), ConditionConvert.convert(conditions), Sign.class );
    }
    
    /**
     * 一次性查询表中所有数据
     * @return 表中所有数据
     */
    public List<Object> listAll()
    {
        return dao.queryAllList(Sign.class);
    }

    public List<Map<String,String>> queryRecordsWithCondition(List<SearchCondition> conditions)
    {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        Map<String,String> map = null;
        SqlRowSet rs = queryDao.find( getSql("sql_query_sign_list"), ConditionConvert.convert(conditions) );
        while(rs.next())
        {
            map = new HashMap<String, String>();
            map.put( "userId", rs.getString( "user_name" ) );
            map.put( "deptName", rs.getString( "dept_name" ) );
            map.put( "typeStr", CacheFactory.getInstance().getDictionaryText( "signtype", rs.getString( "type" ) ) );
            map.put( "address", rs.getString( "address" ) );
            map.put( "handAddress", rs.getString( "hand_address" ) );
            map.put( "signImage", PageUtil.getBasePath( SessionHelper.getRequest() )+"upload/sign/"+rs.getString( "id" )+".jpg" );
            String handAddress = rs.getString( "hand_address" );
            if(null != handAddress && handAddress.indexOf( "," ) != -1)
            {
                String[] has = handAddress.split( "," );
                map.put( "town" , has[0] );
                map.put( "village", has[1] );
            }
            map.put( "signTime", rs.getString( "sign_time" ).replaceAll( "[.]0", "" ) );
            map.put( "id", rs.getString( "id" ) );
            list.add( map );
        }
        
        return list;
    }    
    
    /**
     * 根据主键查询对象
     * @param id 主键编码
     * @return 查询的对象
     */
    public Object queryById( String id )
    {
        Sign sign = new Sign();
        sign.setId( id );
        return dao.queryByKey( Sign.class, sign );
    }

    /**
     * 根据主键删除对象
     * @param id 主键编码
     * @return 删除是否成功
     */
    public boolean deleteById( String id )
    {
        Sign sign = new Sign();
        sign.setId( id );
        int result = dao.deleteByKey( sign );
        return result == 1;
    }
    
    /**
     * 删除表中所有记录
     * @return 删除是否成功
     */
    public int deleteAll()
    {
    	Sign sign = new Sign();
    	return dao.deleteAll( sign );
    }

    /**
     * 新增签到签退信息记录
     * @param conveneNews 签到签退信息对象 
     * @return 新增是否成功
     */
    public boolean insert( Sign sign )
    {
        int result = dao.insert( sign );
        return result == 1;
    }

    /**
     * 根据ID主键更新信息
     * @param conveneNews 签到签退信息对象
     * @return 更新是否成功
     */
    public boolean updateById( Sign sign )
    {
        int result = dao.updateByKey( sign );
        return result == 1;
    }
    
    private String getSql(String sqlId)
    {
        return SqlFactory.getInstance( "fc_sql.xml", SignServiceImpl.class ).getSql( sqlId );
    }
    
    private String getPageSql(String sqlId, int pageNumber ,int pageSize)
    {
        return SqlFactory.getInstance( "fc_sql.xml", SignServiceImpl.class ).getPageSql( sqlId, pageNumber, pageSize );
    }

}
