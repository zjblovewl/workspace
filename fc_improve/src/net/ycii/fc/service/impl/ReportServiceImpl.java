package net.ycii.fc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ycii.fc.service.IReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.annotation.dao.IAnnotaionDao;
import com.tyunsoft.base.common.SessionHelper;
import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.utils.PageUtil;
import com.tyunsoft.base.utils.Pager;
import com.tyunsoft.base.utils.SqlFactory;

@Service
public class ReportServiceImpl implements IReportService
{

    @Autowired
    private IAnnotaionDao dao;
    
    @Autowired
    private IDao queryDao;
    
    @Override
    public Pager queryReports( String userName,String dept,String startDate,String endDate,int pageNumber,int pageSize )
    {
        Pager pager = new Pager();
        int count = queryDao.findForInt( getSql("sql_query_sign_things_reports_count"), new Object[]{"%"+dept+"%","%"+userName+"%",startDate,endDate} );
        pager.setTotal( count );
        SqlRowSet rs = queryDao.find( getPageSql("sql_query_sign_things_reports",pageNumber,pageSize), new Object[]{"%"+dept+"%","%"+userName+"%",startDate,endDate} );
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        Map<String,String> map = null;
        while(rs.next())
        {
            map = new HashMap<String,String>();
            map.put( "signId", rs.getString( "id" ) );
            map.put( "userName", rs.getString( "user_name" ));
            map.put( "signTime", rs.getString( "sign_time" ) );
            map.put( "deptName", rs.getString( "dept_name" ) );
            map.put( "userPhone", rs.getString( "user_phone" ) );
            map.put( "description", rs.getString( "description" ) );
            map.put( "appealerAddress", rs.getString( "appealer_address" ) );
            list.add( map );
        }
        pager.setRows( list ); 
        return pager;
    }
    
    public Map<String,Object> query(String signId)
    {
        List<Map<String,Object>> result = dao.queryForList( getSql("sql_query_sign_things_report"), new Object[]{signId} );
        if(result.size() > 0)
        {
            return result.get( 0 );
        }
        else
        {
            return null;
        }
    }

    public List<Map<String,String>> queryRecordsWithCondition(String userName,String dept,String startDate,String endDate)
    {
        SqlRowSet rs = queryDao.find( getSql("sql_export_sign_things_reports"), new Object[]{"%"+userName+"%","%"+dept+"%",startDate,endDate} );
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        Map<String,String> map = null;
        int i = 0;
        while(rs.next())
        {
            map = new HashMap<String,String>();
            map.put( "xuhao", String.valueOf( ++i  ));
            map.put( "deptName", rs.getString( "dept_name" ));
            String handAddress = rs.getString( "hand_address" );
            if(handAddress != null && handAddress.indexOf( "," )!=-1)
            {
                String[] address = handAddress.split( "," );
                map.put( "town", address[0] );
                map.put( "village", address[1] );
            }
            map.put( "userName", rs.getString( "user_name" ) );
            map.put( "userPhone", rs.getString( "user_phone" ));
            map.put( "inSignTime", rs.getString( "in_sign_time" ));
            map.put( "inAddress", rs.getString( "in_address" ));
            if(null != rs.getString( "in_id" ))
            {
                map.put( "inImgPath", PageUtil.getBasePath( SessionHelper.getRequest() )+"upload/sign/"+rs.getString( "in_id" )+".jpg");
            }
            map.put( "meeting", rs.getString( "meeting" ));
            String recordImages = rs.getString( "record_images" );
            if(null != recordImages)
            {
              recordImages = recordImages.replaceAll( "upload", PageUtil.getBasePath( SessionHelper.getRequest() )+"upload" );  
            }
            map.put( "recordImages", recordImages);
            map.put( "appealerName", rs.getString( "appealer_name" ));
            map.put( "appealerPhone", rs.getString( "appealer_phone" ));
            map.put( "appealerAddress", rs.getString( "appealer_address" ));
            map.put( "description", rs.getString( "description" ));
            String appealImages = rs.getString( "appeal_images" );
            if(null != appealImages)
            {
                appealImages = appealImages.replaceAll( "upload", PageUtil.getBasePath( SessionHelper.getRequest() )+"upload" );  
            }
            map.put( "appealImages", appealImages);
            map.put( "outSignTime", rs.getString( "out_sign_time" ));
            map.put( "outAddress", rs.getString( "out_address" ));
            if(null != rs.getString( "out_id" ))
            {
                map.put( "outImgPath", PageUtil.getBasePath( SessionHelper.getRequest() )+"upload/sign/"+rs.getString( "out_id" )+".jpg");
            }
            list.add( map );
        }
        return list;
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
