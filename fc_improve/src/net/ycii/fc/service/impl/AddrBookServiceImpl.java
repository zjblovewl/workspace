package net.ycii.fc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ycii.fc.entity.AddrBook;
import net.ycii.fc.service.IAddrBookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.annotation.dao.IAnnotaionDao;
import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.entity.Dept;
import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.utils.ConditionConvert;
import com.tyunsoft.base.utils.Pager;
import com.tyunsoft.base.utils.SqlFactory;

/**
 * 通讯录相关的业务层接口实现
 * 
 * @author  flymz
 * @version  [v1.0, 2015年02月11日]
 */
@Service("addrBookService")
public class AddrBookServiceImpl implements IAddrBookService

{
    
    @Autowired
    private IAnnotaionDao dao;
    
    @Autowired
    private IDao iDao;    

    /**
     * 查询分页记录数
     * @param pageNumber 请求页数
     * @param pageSize 每页记录数
     * @param conditions 查询条件
     * @return 查询的分页对象
     */
    public Pager list( int pageNumber, int pageSize, List<SearchCondition> conditions  )
    {
        return dao.queryPage(getSql("sql_query_addrbook_count"), getPageSql("sql_query_addrbook_list",pageNumber,pageSize), ConditionConvert.convert(conditions), AddrBook.class );
    }
    
    /**
     * 一次性查询表中所有数据
     * @return 表中所有数据
     */
    public List<Object> listAll()
    {
        return dao.queryAllList(AddrBook.class);
    }

    /**
     * 根据主键查询对象
     * @param id 主键编码
     * @return 查询的对象
     */
    public Object queryById( String id )
    {
        AddrBook addrBook = new AddrBook();
        addrBook.setId( id );
        return dao.queryByKey( AddrBook.class, addrBook );
    }

    /**
     * 根据主键删除对象
     * @param id 主键编码
     * @return 删除是否成功
     */
    public boolean deleteById( String id )
    {
        AddrBook addrBook = new AddrBook();
        addrBook.setId( id );
        int result = dao.deleteByKey( addrBook );
        return result == 1;
    }
    
    /**
     * 删除表中所有记录
     * @return 删除是否成功
     */
    public int deleteAll()
    {
    	AddrBook addrBook = new AddrBook();
    	return dao.deleteAll( addrBook );
    }

    /**
     * 新增通讯录记录
     * @param conveneNews 通讯录对象 
     * @return 新增是否成功
     */
    public boolean insert( AddrBook addrBook )
    {
        int result = dao.insert( addrBook );
        return result == 1;
    }

    /**
     * 根据ID主键更新信息
     * @param conveneNews 通讯录对象
     * @return 更新是否成功
     */
    public boolean updateById( AddrBook addrBook )
    {
        int result = dao.updateByKey( addrBook );
        return result == 1;
    }
    
    public List<Object> queryUserWithCondition(Map<String,String> condition)
    {
        return dao.queryList(getSql("queryAddrWithCondition_byName"), new Object[] { condition.get( "userName" ),condition.get( "deptName" ) },AddrBook.class);
    }
    
    @SuppressWarnings( {"rawtypes", "unused", "unchecked"} )
    public List<Object> queryDeptByName(Map<String,String> condition)
    {
        String sql = getSql("queryDeptByName");
        List result = new ArrayList();
        SqlRowSet rs = iDao.find(sql,new Object[] { condition.get( "deptName" )});
        Map map = new HashMap();
        Dept dept = null;
        while (rs.next())
        {
          dept = new Dept();
          dept.setDeptId(rs.getString("dept_id"));
          dept.setDeptName(rs.getString("dept_name"));
          dept.setDeptShortName(rs.getString("dept_short_name"));
          dept.setDeptPhone(rs.getString("dept_phone"));
          dept.setDeptFax(rs.getString("dept_fax"));
          dept.setDeptAddress(rs.getString("dept_address"));
          dept.setPDeptId(rs.getString("p_dept_id"));
          dept.setDeptLeader(rs.getString("dept_leader"));
          dept.setDeptRemark(rs.getString("dept_remark"));
          dept.setDeptLevel(rs.getInt("dept_level"));
          result.add(dept);
        }
        return result;        
    }
    
    public List<Object> queryUserByPhotoNum(Map<String,String> condition)
    {
        return dao.queryList(getSql("queryDeptByPhotoNum"), new Object[] { condition.get( "phoneNum" ) },AddrBook.class);
    }
    
    private String getSql(String sqlId)
    {
        return SqlFactory.getInstance( "fc_sql.xml", AddrBookServiceImpl.class ).getSql( sqlId );
    }
    
    private String getPageSql(String sqlId, int pageNumber ,int pageSize)
    {
        return SqlFactory.getInstance( "fc_sql.xml", AddrBookServiceImpl.class ).getPageSql( sqlId, pageNumber, pageSize );
    }

}
