package net.ycii.fc.service;

import java.util.List;
import java.util.Map;

import com.tyunsoft.base.utils.Pager;

public interface IReportService
{
    
    Pager queryReports(String userName,String dept,String startDate,String endDate,int pageNumber,int pageSize);
    
    Map<String,Object> query(String signId);

    List<Map<String,String>> queryRecordsWithCondition(String userName,String dept,String startDate,String endDate);
}
