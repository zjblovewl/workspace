package net.ycii.fc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ycii.fc.entity.Notice;
import net.ycii.fc.service.INoticeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.annotation.dao.IAnnotaionDao;
import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.utils.ConditionConvert;
import com.tyunsoft.base.utils.Pager;
import com.tyunsoft.base.utils.SqlFactory;

/**
 * 通知公告相关的业务层接口实现
 * 
 * @author  flymz
 * @version  [v1.0, 2015年02月11日]
 */
@Service("noticeService")
public class NoticeServiceImpl implements INoticeService
{
    
    @Autowired
    private IAnnotaionDao dao;
    
    @Autowired
    private IDao queryDao;

    /**
     * 查询分页记录数
     * @param pageNumber 请求页数
     * @param pageSize 每页记录数
     * @param conditions 查询条件
     * @return 查询的分页对象
     */
    public Pager list( int pageNumber, int pageSize, List<SearchCondition> conditions  )
    {
        return dao.queryPage(getSql("sql_query_notice_count"), getPageSql("sql_query_notice_list",pageNumber,pageSize), ConditionConvert.convert(conditions), Notice.class );
    }
    
    /**
     * 查询分页记录数
     * @param pageNumber 请求页数
     * @param pageSize 每页记录数
     * @param conditions 查询条件
     * @return 查询的分页对象
     */
    public Pager listMobile( int pageNumber, int pageSize, List<SearchCondition> conditions  )
    {
        return dao.queryPage(getSql("sql_query_notice_count_mobile"), getPageSql("sql_query_notice_list_mobile",pageNumber,pageSize), ConditionConvert.convert(conditions), Notice.class );
    }
    
    /**
     * 一次性查询表中所有数据
     * @return 表中所有数据
     */
    public List<Object> listAll()
    {
        return dao.queryAllList(Notice.class);
    }

    /**
     * 根据主键查询对象
     * @param id 主键编码
     * @return 查询的对象
     */
    public Object queryById( String id )
    {
        Notice notice = new Notice();
        notice.setId( id );
        return dao.queryByKey( Notice.class, notice );
    }

    /**
     * 根据主键删除对象
     * @param id 主键编码
     * @return 删除是否成功
     */
    public boolean deleteById( String id )
    {
        Notice notice = new Notice();
        notice.setId( id );
        int result = dao.deleteByKey( notice );
        return result == 1;
    }
    
    /**
     * 删除表中所有记录
     * @return 删除是否成功
     */
    public int deleteAll()
    {
    	Notice notice = new Notice();
    	return dao.deleteAll( notice );
    }

    /**
     * 新增通知公告记录
     * @param conveneNews 通知公告对象 
     * @return 新增是否成功
     */
    public boolean insert( Notice notice )
    {
        int result = dao.insert( notice );
        return result == 1;
    }

    /**
     * 根据ID主键更新信息
     * @param conveneNews 通知公告对象
     * @return 更新是否成功
     */
    public boolean updateById( Notice notice )
    {
        int result = dao.updateByKey( notice );
        return result == 1;
    }
    
    /**
     * 批量增加用户到用户表中
     * @param noticeId
     * @param users [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void batchInsertUsers(String noticeId,String users)
    {
        List<Map<String,String>> params = new ArrayList<Map<String,String>>();
        Map<String,String> map = null;
        String[] userArray = users.split( "," );
        for ( String user : userArray )
        {
            if(!"".equals( user ))
            {
                map = new HashMap<String,String>();
                map.put( "1", noticeId );
                map.put( "2", user );
                params.add( map );
            }
        }
        queryDao.batchUpdate( getSql("sql_insert_notice_users"), params );
    }
    
    private void deleteUserNotices(String userId)
    {
         queryDao.delete( getSql("sql_delete_notice_users"), userId );
    }
    
    public String queryUserNotices(String userId)
    {
        SqlRowSet rs = queryDao.find( getSql("sql_query_user_notices"), new Object[]{userId} );
        StringBuffer result = new StringBuffer();
        while(rs.next())
        {
            result.append( rs.getString( "id" ) ).append( "," );
        }
        deleteUserNotices(userId);
        return result.toString();
    }
    
    private String getSql(String sqlId)
    {
        return SqlFactory.getInstance( "fc_sql.xml", NoticeServiceImpl.class ).getSql( sqlId );
    }
    
    private String getPageSql(String sqlId, int pageNumber ,int pageSize)
    {
        return SqlFactory.getInstance( "fc_sql.xml", NoticeServiceImpl.class ).getPageSql( sqlId, pageNumber, pageSize );
    }

}
