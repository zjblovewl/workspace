package net.ycii.fc.service;

import java.util.List;
import java.util.Map;

import com.tyunsoft.base.utils.Pager;

public interface IAreaService
{
    
    Pager list(String townName,String villageName,int pageNumber,int pageSize);
    
    /**
     * 设置区域用户
     * @param users 用户帐号，采用逗号分隔
     * @param id 编码
     * @return [参数说明]
     */
    boolean setAreaUsers(String users,String id);

    /**
     * 查询用户所在区域信息
     * @param userId
     * @return [参数说明]
     * 
     * @return List<Map<String,String>> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    List<Map<String,String>> queryUserAreas(String userId);
}
