package net.ycii.fc.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.ycii.fc.entity.BeautifulCountry;

import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.utils.Pager;

/**
 * 美丽乡村业务接口层
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年11月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface IBeautifulCountryService
{
	
	/**
     * 查询分页记录数
     * @param pageNumber 请求页数
     * @param pageSize 每页记录数
     * @param conditions 查询条件
     * @return 查询的分页对象
     */
    Pager list( int pageNumber, int pageSize, List<SearchCondition> conditions );
    
    /**
     * <手机端获取美丽乡村列表集合>
     * <功能详细描述>
     * @param pageNumber
     * @param pageSize
     * @param conditions
     * @return [参数说明]
     * 
     * @return Pager [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    Pager listMobile( int pageNumber, int pageSize, List<SearchCondition> conditions );
    
    /**
     * 一次性查询表中所有数据
     * @return 表中所有数据
     */
    List<Object> listAll();

	/**
     * 根据主键查询对象
     * @param id 主键编码
     * @return 查询的对象
     */
    Object queryById( String id );
	
	/**
     * 根据主键删除对象
     * @param id 主键编码
     * @return 删除是否成功
     */
    boolean deleteById( String id );
    
    /**
     * 删除表中所有记录
     * @return 删除影响的记录数
     */
    int deleteAll();

	/**
     * 新增
     * @param conveneNews 通讯录对象 
     * @return 新增是否成功
     */
    boolean insert( BeautifulCountry bf,HttpServletRequest request );

	/**
     * 根据ID主键更新信息
     * @param conveneNews 通讯录对象
     * @return 更新是否成功
     */
    boolean updateById( BeautifulCountry bf,HttpServletRequest request  );
    
    List<Object> queryUserWithCondition(Map<String,String> condition);
    
    List<Object> queryUserByPhotoNum(Map<String,String> condition);
    
    List<Object> queryDeptByName(Map<String,String> condition);

}
