package net.ycii.fc.service;

import java.util.List;
import java.util.Map;

public interface IIndexService
{

    void update(String position, String userId);

    
    List<Map<String,String>> onlineUsers();
}
