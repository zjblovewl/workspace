package net.ycii.fc.service.impl;

import java.util.List;

import net.ycii.fc.entity.Terminal;
import net.ycii.fc.service.ITerminalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.annotation.dao.IAnnotaionDao;
import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.utils.ConditionConvert;
import com.tyunsoft.base.utils.Pager;
import com.tyunsoft.base.utils.SqlFactory;

/**
 * 用户终端信息相关的业务层接口实现
 * 
 * @author  flymz
 * @version  [v1.0, 2015年02月11日]
 */
@Service("terminalService")
public class TerminalServiceImpl implements ITerminalService
{
    
    @Autowired
    private IAnnotaionDao dao;

    /**
     * 查询分页记录数
     * @param pageNumber 请求页数
     * @param pageSize 每页记录数
     * @param conditions 查询条件
     * @return 查询的分页对象
     */
    public Pager list( int pageNumber, int pageSize, List<SearchCondition> conditions  )
    {
        return dao.queryPage(getSql("sql_query_terminal_count"), getPageSql("sql_query_terminal_list",pageNumber,pageSize), ConditionConvert.convert(conditions), Terminal.class );
    }
    
    /**
     * 一次性查询表中所有数据
     * @return 表中所有数据
     */
    public List<Object> listAll()
    {
        return dao.queryAllList(Terminal.class);
    }

    /**
     * 根据主键查询对象
     * @param id 主键编码
     * @return 查询的对象
     */
    public Object queryById( String id )
    {
        Terminal terminal = new Terminal();
        terminal.setId( id );
        return dao.queryByKey( Terminal.class, terminal );
    }

    /**
     * 根据主键删除对象
     * @param id 主键编码
     * @return 删除是否成功
     */
    public boolean deleteById( String id )
    {
        Terminal terminal = new Terminal();
        terminal.setId( id );
        int result = dao.deleteByKey( terminal );
        return result == 1;
    }
    
    /**
     * 删除表中所有记录
     * @return 删除是否成功
     */
    public int deleteAll()
    {
    	Terminal terminal = new Terminal();
    	return dao.deleteAll( terminal );
    }

    /**
     * 新增用户终端信息记录
     * @param conveneNews 用户终端信息对象 
     * @return 新增是否成功
     */
    public boolean insert( Terminal terminal )
    {
        int result = dao.insert( terminal );
        return result == 1;
    }

    /**
     * 根据ID主键更新信息
     * @param conveneNews 用户终端信息对象
     * @return 更新是否成功
     */
    public boolean updateById( Terminal terminal )
    {
        int result = dao.updateByKey( terminal );
        return result == 1;
    }
    
    private String getSql(String sqlId)
    {
        return SqlFactory.getInstance( "fc_sql.xml", TerminalServiceImpl.class ).getSql( sqlId );
    }
    
    private String getPageSql(String sqlId, int pageNumber ,int pageSize)
    {
        return SqlFactory.getInstance( "fc_sql.xml", TerminalServiceImpl.class ).getPageSql( sqlId, pageNumber, pageSize );
    }

}
