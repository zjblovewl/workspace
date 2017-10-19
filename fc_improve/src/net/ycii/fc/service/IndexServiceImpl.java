package net.ycii.fc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ycii.fc.service.impl.TerminalServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.utils.SqlFactory;

@Service
public class IndexServiceImpl implements IIndexService
{

   @Autowired 
    private IDao dao;
    
    @Override
    public void update( String position, String userId )
    {
        String sql = getSql("sql_update_user_position");
        int result = dao.update( sql, new Object[]{position,new Date(),userId} );
        if(result == 0)
        {
            dao.update( getSql("sql_insert_user_position"), new Object[]{userId,position,new Date()} );
        }
    }

    @Override
    public List<Map<String, String>> onlineUsers()
    {
        SqlRowSet rs = dao.find( getSql("sql_query_online_users") );
        List<Map<String,String>> result = new ArrayList<Map<String,String>>();
        Map<String,String> map = null;
        while(rs.next())
        {
            map = new HashMap<String,String>();
            map.put( "userName", rs.getString( "user_name" ) );
            map.put( "userPhone", rs.getString( "user_phone" ) );
            map.put( "position", rs.getString( "position" )  );
            result.add( map );
        }
        return result;
    }
    
    private String getSql(String sqlId)
    {
        return SqlFactory.getInstance( "fc_sql.xml", TerminalServiceImpl.class ).getSql( sqlId );
    }
    
}
