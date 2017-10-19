package net.ycii.fc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyunsoft.base.annotation.dao.IAnnotaionDao;
import com.tyunsoft.base.utils.ConditionConvert;
import com.tyunsoft.base.utils.Pager;
import com.tyunsoft.base.utils.SqlFactory;
import com.tyunsoft.base.entity.SearchCondition;
import net.ycii.fc.entity.IndexImage;
import net.ycii.fc.service.IIndexImageService;

/**
 * 首页图片相关的业务层接口实现
 * 
 * @author  flymz
 * @version  [v1.0, 2015年04月07日]
 */
@Service("indexImageService")
public class IndexImageServiceImpl implements IIndexImageService
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
        return dao.queryPage(getSql("sql_query_indeximage_count"), getPageSql("sql_query_indeximage_list",pageNumber,pageSize), ConditionConvert.convert(conditions), IndexImage.class );
    }
    
    /**
     * 一次性查询表中所有数据
     * @return 表中所有数据
     */
    public List<Object> listAll()
    {
        return dao.queryAllList(IndexImage.class);
    }

    /**
     * 根据主键查询对象
     * @param id 主键编码
     * @return 查询的对象
     */
    public Object queryById( String id )
    {
        IndexImage indexImage = new IndexImage();
        indexImage.setId( id );
        return dao.queryByKey( IndexImage.class, indexImage );
    }

    /**
     * 根据主键删除对象
     * @param id 主键编码
     * @return 删除是否成功
     */
    public boolean deleteById( String id )
    {
        IndexImage indexImage = new IndexImage();
        indexImage.setId( id );
        int result = dao.deleteByKey( indexImage );
        return result == 1;
    }
    
    /**
     * 删除表中所有记录
     * @return 删除是否成功
     */
    public int deleteAll()
    {
    	IndexImage indexImage = new IndexImage();
    	return dao.deleteAll( indexImage );
    }

    /**
     * 新增首页图片记录
     * @param conveneNews 首页图片对象 
     * @return 新增是否成功
     */
    public boolean insert( IndexImage indexImage )
    {
        int result = dao.insert( indexImage );
        return result == 1;
    }

    /**
     * 根据ID主键更新信息
     * @param conveneNews 首页图片对象
     * @return 更新是否成功
     */
    public boolean updateById( IndexImage indexImage )
    {
        int result = dao.updateByKey( indexImage );
        return result == 1;
    }
    
    private String getSql(String sqlId)
    {
        return SqlFactory.getInstance( "fc_sql.xml", IndexImageServiceImpl.class ).getSql( sqlId );
    }
    
    private String getPageSql(String sqlId, int pageNumber ,int pageSize)
    {
        return SqlFactory.getInstance( "fc_sql.xml", IndexImageServiceImpl.class ).getPageSql( sqlId, pageNumber, pageSize );
    }

}
