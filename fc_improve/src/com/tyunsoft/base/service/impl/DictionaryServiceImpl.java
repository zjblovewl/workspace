
package com.tyunsoft.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.entity.Dictionary;
import com.tyunsoft.base.entity.DictionaryValue;
import com.tyunsoft.base.service.IDictionaryService;
import com.tyunsoft.base.utils.SqlFactory;

/**
 * 字典信息管理业务接口实现
 * 
 * @author Flymz
 */
@Service
public class DictionaryServiceImpl implements IDictionaryService
{

    @Autowired
    private IDao dao;

    /**
     * 删除字典信息
     * 
     * @param dicId
     *            字典编码
     * @return 删除是否成功
     */
    public boolean delete( String dicId )
    {
        //删除字典数据
        String sql = SqlFactory.getInstance().getSql( "sql_delete_dic" );
        int result = dao.delete( sql, dicId );
        //删除字典值数据
        sql = SqlFactory.getInstance().getSql( "sql_delete_dic_values" );
        dao.delete( sql, dicId );
        return result == 1;
    }

    /**
     * 新增字典信息
     * 
     * @param dic
     *            字典信息
     * @return 新增是否成功
     */
    public boolean insert( Dictionary dic )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_insert_dic" );
        int result = dao.update( sql, new Object[] {dic.getDicId(),
                dic.getDicName(), new Date()} );
        return result == 1;
    }

    /**
     * 根据字典编码查询字典值信息
     * @param dicId 字典编码
     * @return 字典值信息列表
     */
    public List<DictionaryValue> query( String dicId )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_search_dictionary_values" );
        SqlRowSet rs = dao.find( sql, new Object[]{dicId} );
        List<DictionaryValue> list = new ArrayList<DictionaryValue>();
        DictionaryValue dv = null;
        while(rs.next())
        {
            dv = new DictionaryValue();
            dv.setDicValueId( rs.getString( "dic_value_id" ) );
            dv.setDicValueLabel( rs.getString( "dic_value_label" ) );
            dv.setDicValueOrder( rs.getInt( "dic_value_order" ) );
            list.add( dv );
        }
        return list;
    }

    /**
     * 查询字典信息列表
     * 
     * @return 字典信息列表
     */
    public List<Dictionary> search()
    {
        String sql = SqlFactory.getInstance().getSql( "sql_search_dic_list" );
        SqlRowSet rs = dao.find( sql );
        List<Dictionary> list = new ArrayList<Dictionary>();
        Dictionary dic = null;
        while ( rs.next() )
        {
            dic = new Dictionary();
            dic.setDicId( rs.getString( "dic_id" ) );
            dic.setDicName( rs.getString( "dic_name" ) );
            dic.setCreateDate( rs.getDate( "create_date" ) );
            list.add( dic );
        }
        return list;
    }

    /**
     * 更新字典信息
     * 
     * @param dic
     *            字典信息
     * @return 更新是否成功
     */
    public boolean update( Dictionary dic )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_update_dic" );
        int result = dao.update( sql, new Object[] {dic.getDicName(),
                dic.getDicId()} );
        return result == 1;
    }

    @Override
    public boolean updateDicValues( String dicId, List<DictionaryValue> vals )
    {
        //先删除数据
        String sql = SqlFactory.getInstance().getSql( "sql_delete_dic_values" );
        dao.delete( sql, dicId );
        //再新增字典数据
        sql = SqlFactory.getInstance().getSql( "sql_insert_dic_values" );
        List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
        for(int i = 0;i<vals.size();i++)
        {
            DictionaryValue val = vals.get( i );
            val.setDicValueOrder( i+1 );
            val.setDicId( dicId );
            mapList.add( val.toMap() );
        }
        int[] result = dao.batchUpdate( sql, mapList );
        return result.length >=0 ;
    }

    /**
     * 根据字典编码查询字典信息
     * 
     * @param dicId
     *            字典编码
     * @return 字典信息
     */
    public Dictionary get( String dicId )
    {
        String sql = SqlFactory.getInstance().getSql( "sql_query_dic" );
        SqlRowSet rs = dao.find( sql, new Object[] {dicId} );
        Dictionary dic = new Dictionary();
        while ( rs.next() )
        {
            dic.setDicId( rs.getString( "dic_id" ) );
            dic.setDicName( rs.getString( "dic_name" ) );
        }
        return dic;
    }

}
