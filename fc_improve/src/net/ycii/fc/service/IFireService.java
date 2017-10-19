package net.ycii.fc.service;

import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.utils.Pager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.ycii.fc.entity.Fire;
import net.ycii.fc.entity.WxUser;

public abstract interface IFireService
{
  public abstract Pager list(int paramInt1, int paramInt2, List<SearchCondition> paramList);

  public abstract Pager listMobile(int paramInt1, int paramInt2, List<SearchCondition> paramList);

  public abstract List<Object> listAll();

  public abstract Fire getNewFire();

  public abstract Object queryById(String paramString);

  public abstract boolean deleteById(String paramString);

  public abstract int deleteAll();

  public abstract boolean insert(Fire paramFire, HttpServletRequest paramHttpServletRequest);
  
  /**
   * <一句话功能简述>查询所有关注微信用户
   * <功能详细描述>
   * @return [参数说明]
   * 
   * @return List<WxUser> [返回类型说明]
   * @exception throws [违例类型] [违例说明]
   * @see [类、类#方法、类#成员]
   */
  List<WxUser> findAll();
}