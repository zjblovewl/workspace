package cn.linkage.dao;

import java.util.List;

import cn.linkage.entity.Message;

public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    /**
     * <一句话功能简述>获取所有留言
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @see [类、类#方法、类#成员]
     */
    List<Message> getAllMessage();
}