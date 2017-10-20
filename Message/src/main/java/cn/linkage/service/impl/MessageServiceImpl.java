package cn.linkage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkage.dao.MessageMapper;
import cn.linkage.entity.Message;
import cn.linkage.service.MessageService;

@Service("messageService")
public class MessageServiceImpl implements MessageService
{
    @Autowired(required=true)
    private MessageMapper messageMapper;

    @Override
    public int saveMessage( Message message )
    {
        return messageMapper.insertSelective( message );
    }

    @Override
    public List<Message> getAllMessage()
    {
        return messageMapper.getAllMessage();
    }

}
