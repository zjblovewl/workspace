package net.ycii.fc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ycii.fc.service.IAreaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.utils.Pager;
import com.tyunsoft.base.utils.SqlFactory;

@Service
public class AreaServiceImpl implements IAreaService
{

    @Autowired
    private IDao dao;
    
    @Override
    public Pager list( String townName, String villageName, int pageNumber,
            int pageSize )
    {
        Pager pager = new Pager();
        int total = dao.findForInt( getSql("sql_query_town_and_villages_count"), new Object[]{"%"+townName+"%","%"+villageName+"%"} );
        pager.setTotal( total );
        List<Map<String,String>> rows = new ArrayList<Map<String,String>>();
        Map<String,String> map = null;
        SqlRowSet rs = dao.find( getPageSql( "sql_query_town_and_villages", pageNumber, pageSize ), new Object[]{"%"+townName+"%","%"+villageName+"%"} );
        while(rs.next())
        {
            map = new HashMap<String,String>();
            map.put( "id", rs.getString( "id" ) );
            map.put( "townName", rs.getString( "town" ) );
            map.put( "villageName", rs.getString( "village" ) );
            map.put( "users", queryAreaUsers(rs.getString( "id" )) );
            rows.add( map );
        }
        pager.setRows( rows );
        return pager;
    }
    
    private String queryAreaUsers(String id)
    {
        SqlRowSet rs = dao.find( getSql("sql_query_user_names_by_address_id"), new Object[]{id} );
        StringBuffer str = new StringBuffer();
        while(rs.next())
        {
            str.append( rs.getString( "user_name" )+"("+rs.getString( "user_phone" )+")" ).append( "," );
        }
        if(str.toString().endsWith( "," ))
        {
            return str.substring( 0, str.length() - 1 );
        }
        return "";
    }
    
    /**
     * 设置区域用户
     * @param users 用户帐号，采用逗号分隔
     * @param id 编码
     * @return [参数说明]
     */
    public boolean setAreaUsers(String users,String id)
    {
        dao.update( getSql("sql_delete_area_users"), new Object[]{id} );
        String[] userArray = users.split( "[|]" );
        for ( String string : userArray )
        {
            if(!"".equals( string ))
            {
                dao.update( getSql("sql_insert_area_users"), new Object[]{id,string} );
            }
        }
        
        return true;
    }

    /**
     * 查询用户所在区域信息
     * @param userId
     * @return [参数说明]
     * 
     * @return List<Map<String,String>> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public List<Map<String,String>> queryUserAreas(String userId)
    {
        SqlRowSet rs = dao.find( getSql("sql_query_user_areas"), new Object[]{userId} );
        List<Map<String,String>> rows = new ArrayList<Map<String,String>>();
        Map<String,String> map = null;
        while(rs.next())
        {
            map = new HashMap<String,String>();
            map.put( "town", rs.getString( "town" ) );
            map.put( "village", rs.getString( "village" ) );
            rows.add( map );
        }
        return rows;
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
