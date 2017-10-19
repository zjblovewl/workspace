package net.ycii.fc.service;

import java.util.List;

import net.ycii.fc.entity.Notice;

import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.utils.Pager;

/**
 * 通知公告相关的业务层接口
 * 
 * @author  flymz
 * @version  [v1.0, 2015年02月11日]
 */
public interface INoticeService
{
	
	/**
     * 查询分页记录数
     * @param pageNumber 请求页数
     * @param pageSize 每页记录数
     * @param conditions 查询条件
     * @return 查询的分页对象
     */
    Pager list( int pageNumber, int pageSize, List<SearchCondition> conditions );
    
    Pager listMobile( int pageNumber, int pageSize, List<SearchCondition> conditions  );
    
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
     * 新增通知公告记录
     * @param conveneNews 通知公告对象 
     * @return 新增是否成功
     */
    boolean insert( Notice notice );

	/**
     * 根据ID主键更新信息
     * @param conveneNews 通知公告对象
     * @return 更新是否成功
     */
    boolean updateById(  Notice notice  );
    
    /**
     * 批量增加用户到用户表中
     * @param noticeId
     * @param users [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    void batchInsertUsers(String noticeId,String users);
    
    String queryUserNotices(String userId);

}
