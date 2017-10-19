package net.ycii.entity;

import java.util.List;
import java.util.Map;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年5月14日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class WeiXinUsersResult extends BaseResult
{
    /**
     * 关注该公众账号的总用户数
     */
    private String openid;
    /**
     * 拉取的OPENID个数，最大值为10000
     */
    private Integer total;
    private Integer count;
    /**
     * 列表数据，OPENID的列表
     */
    private Map<String,List<String>> data;
    /**
     * 拉取列表的后一个用户的OPENID
     */
    private String next_openid;
    public Integer getCount()
    {
        return count;
    }
    public String getOpenid()
    {
        return openid;
    }
    public void setOpenid( String openid )
    {
        this.openid = openid;
    }
    public Integer getTotal()
    {
        return total;
    }
    public void setTotal( Integer total )
    {
        this.total = total;
    }
    public Map<String, List<String>> getData()
    {
        return data;
    }
    public void setData( Map<String, List<String>> data )
    {
        this.data = data;
    }
    public String getNext_openid()
    {
        return next_openid;
    }
    public void setNext_openid( String next_openid )
    {
        this.next_openid = next_openid;
    }
    public void setCount( Integer count )
    {
        this.count = count;
    }
    
/*    public static void main( String[] args )
    {
        String s = "{\"total\":2,\"count\":2,\"data\":{\"openid\":[\"\",\"OPENID1\",\"OPENID2\"]},\"next_openid\":\"NEXT_OPENID\"}";
        JSONObject json = JSONObject.fromObject( s );
        WeiXinUsersResult result = (WeiXinUsersResult)json.toBean( json, WeiXinUsersResult.class );
        System.out.println(JSONObject.fromObject( result ).toString());
        
    }*/
}
