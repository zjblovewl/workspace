package net.ycii.entity;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年5月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Token extends BaseResult {

	private String access_token;
	private int expires_in;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String accessToken) {
		access_token = accessToken;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expiresIn) {
		expires_in = expiresIn;
	}

}
