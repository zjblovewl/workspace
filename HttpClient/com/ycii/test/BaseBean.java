package com.ycii.test;

import net.ycii.api.WeiXinApi;

import org.junit.BeforeClass;

/**
 * <一句话功能简述>基类
 * <p><功能详细描述>
 * @author  kaylves
 * @version  [版本号, 2015年5月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BaseBean
{
    public static final String APP_ID= "wx3eb8682f8f38a5d6";
    public static final String appsecret= "203255f40cba0410fa0174a4663e2b01";
    //
    //public static final String APP_ID= "wx6f3014ea424079b5";
    //public static final String appsecret= "c252ad94582e94f73a481b04aee3c060";
    
    //测试账号2
    //public static final String APP_ID= "wx4c088c26ebb49b3d";
    //public static final String appsecret= "9f2dc6188466636bf00e3e369196c53a";
    public static String token_key= "QeW2yR8eHJGUKf7i_kW3e8ljAmBWOBFJEpn80rA97QeGfoctkvZY0wDXYatExCD4ER9TmhEw8jYSRHqUz3TaxUYl4VNN4-y6asea2AcBNYpbHq5P2XFC8yUGpNNrvxOXDQBgADAKXI";
    
    @BeforeClass
    public static void beforeClass(){
        token_key = WeiXinApi.getAccessToken( APP_ID, appsecret);
    }
    
    public static void main( String[] args )
    {
        System.out.println(System.currentTimeMillis());
    }
}
