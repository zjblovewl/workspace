package net.ycii.fc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.ycii.fc.entity.AddrBook;
import net.ycii.fc.entity.GoodPerson;
import net.ycii.fc.service.IGoodPersionService;
import net.ycii.fc.util.Base64FileUtil;
import net.ycii.fc.util.DateUtil;
import net.ycii.fc.util.SessionHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tyunsoft.base.annotation.dao.IAnnotaionDao;
import com.tyunsoft.base.dao.IDao;
import com.tyunsoft.base.entity.Dept;
import com.tyunsoft.base.entity.SearchCondition;
import com.tyunsoft.base.utils.ConditionConvert;
import com.tyunsoft.base.utils.Pager;
import com.tyunsoft.base.utils.SqlFactory;

/**
 * 汶南好人业务实现层接口
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  周金兵
 * @version  [版本号, 2015年11月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("goodPersionService")
public class GoodPersionServiceImpl implements IGoodPersionService
{
    
    @Autowired
    private IAnnotaionDao dao;
    
    @Autowired
    private IDao iDao;    

    /**
     * 查询分页记录数
     * @param pageNumber 请求页数
     * @param pageSize 每页记录数
     * @param conditions 查询条件
     * @return 查询的分页对象
     */
    public Pager list( int pageNumber, int pageSize, List<SearchCondition> conditions  )
    {
        return dao.queryPage(getSql("sql_query_goodperson_count"), getPageSql("sql_query_goodperson_list",pageNumber,pageSize), ConditionConvert.convert(conditions), GoodPerson.class );
    }
    
    @Override
    public Pager listMobile( int pageNumber, int pageSize, List<SearchCondition> conditions )
    {
        return dao.queryPage(getSql("sql_query_goodperson_count_mobile"), getPageSql("sql_query_goodperson_list_mobile",pageNumber,pageSize), ConditionConvert.convert(conditions), GoodPerson.class );
    }
    
    /**
     * 一次性查询表中所有数据
     * @return 表中所有数据
     */
    public List<Object> listAll()
    {
        return dao.queryAllList(AddrBook.class);
    }

    /**
     * 根据主键查询对象
     * @param id 主键编码
     * @return 查询的对象
     */
    public Object queryById( String id )
    {
        GoodPerson goodPerson = new GoodPerson();
        goodPerson.setId( id );
        return dao.queryByKey( GoodPerson.class, goodPerson );
    }

    /**
     * 根据主键删除对象
     * @param id 主键编码
     * @return 删除是否成功
     */
    public boolean deleteById( String id )
    {
        GoodPerson goodPerson = new GoodPerson();
        goodPerson.setId( id );
        int result = dao.deleteByKey( goodPerson );
        return result == 1;
    }
    
    /**
     * 删除表中所有记录
     * @return 删除是否成功
     */
    public int deleteAll()
    {
    	AddrBook addrBook = new AddrBook();
    	return dao.deleteAll( addrBook );
    }

    /**
     * 新增通讯录记录
     * @param conveneNews 通讯录对象 
     * @return 新增是否成功
     */
    public boolean insert( GoodPerson goodPerson ,HttpServletRequest request)
    {
        
        //处理视频以wold以及excel
        MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;  
        
        //world、excel、pdf上传
        MultipartFile upFileInnerOne = multipartRequest.getFile("upFileInnerOne");
        //判断是否有
        if(upFileInnerOne!=null){
            if(upFileInnerOne.getSize()>0){
                String format = Base64FileUtil.getFileType( upFileInnerOne.getOriginalFilename() ) ;
                if(format.equals( "doc" ) || format.equals( "docx" ) || format.equals( "xls" ) || format.equals( "xlsx" )
                        || format.equals( "pdf" )){
                    String newFileName = DateUtil.getCurrentDateStr("yyyyMMddHHmmss")+"."+format;
                    String basePath = Base64FileUtil.getRealContextPath(SessionHelper
                            .getRequest())+"upload/";
                    try
                    {
                        Base64FileUtil.copyToPath(  upFileInnerOne.getInputStream(),basePath, newFileName );
                    } catch ( Exception e )
                    {
                        e.printStackTrace();
                        return false;
                    }
                    goodPerson.setAccessoryPath( "upload/"+newFileName );
                }
            }
        }
        
        //视频上传
        MultipartFile upFileInnerTwo = multipartRequest.getFile("upFileInnerTwo");
        //判断是否有
        if(upFileInnerTwo!=null){
            if(upFileInnerTwo.getSize()>0){
                String format = Base64FileUtil.getFileType( upFileInnerTwo.getOriginalFilename() ) ;
                if(format.equals( "rm" ) || format.equals( "rmvb" ) || format.equals( "wmv" ) || format.equals( "avi" )
                        || format.equals( "mp4" ) || format.equals( "3gp" ) || format.equals( "mkv" )){
                    String newFileName = DateUtil.getCurrentDateStr("yyyyMMddHHmmss")+"."+format;
                    String basePath = Base64FileUtil.getRealContextPath(SessionHelper
                            .getRequest())+"upload/";
                    try
                    {
                        Base64FileUtil.copyToPath(  upFileInnerTwo.getInputStream(),basePath, newFileName );
                    } catch ( Exception e )
                    {
                        e.printStackTrace();
                        return false;
                    }
                    goodPerson.setVideoPath( "upload/"+newFileName );
                }
            }
        }
        
        goodPerson.setCreateUser( SessionHelper.getUserId() );
        goodPerson.setOrderTime( new Date() );
        goodPerson.setType( 1 );//汶南好人类型
        int result = dao.insert( goodPerson );
        return result == 1;
    }

    /**
     * 根据ID主键更新信息
     * @param conveneNews 通讯录对象
     * @return 更新是否成功
     */
    public boolean updateById( GoodPerson goodPerson,HttpServletRequest request )
    {
      //处理视频以wold以及excel
        MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;  
        
        //world、excel、pdf上传
        MultipartFile upFileInnerOne = multipartRequest.getFile("upFileInnerOne");
        //判断是否有
        if(upFileInnerOne!=null){
            if(upFileInnerOne.getSize()>0){
                String format = Base64FileUtil.getFileType( upFileInnerOne.getOriginalFilename() ) ;
                if(format.equals( "doc" ) || format.equals( "docx" ) || format.equals( "xls" ) || format.equals( "xlsx" )
                        || format.equals( "pdf" )){
                    String newFileName = DateUtil.getCurrentDateStr("yyyyMMddHHmmss")+"."+format;
                    String basePath = Base64FileUtil.getRealContextPath(SessionHelper
                            .getRequest())+"upload/";
                    try
                    {
                        Base64FileUtil.copyToPath(  upFileInnerOne.getInputStream(),basePath, newFileName );
                    } catch ( Exception e )
                    {
                        e.printStackTrace();
                        return false;
                    }
                    goodPerson.setAccessoryPath( "upload/"+newFileName );
                }
            }
        }
        
        //视频上传
        MultipartFile upFileInnerTwo = multipartRequest.getFile("upFileInnerTwo");
        //判断是否有
        if(upFileInnerTwo!=null){
            if(upFileInnerTwo.getSize()>0){
                String format = Base64FileUtil.getFileType( upFileInnerTwo.getOriginalFilename() ) ;
                if(format.equals( "rm" ) || format.equals( "rmvb" ) || format.equals( "wmv" ) || format.equals( "avi" )
                        || format.equals( "mp4" ) || format.equals( "3gp" ) || format.equals( "mkv" )){
                    String newFileName = DateUtil.getCurrentDateStr("yyyyMMddHHmmss")+"."+format;
                    String basePath = Base64FileUtil.getRealContextPath(SessionHelper
                            .getRequest())+"upload/";
                    try
                    {
                        Base64FileUtil.copyToPath(  upFileInnerTwo.getInputStream(),basePath, newFileName );
                    } catch ( Exception e )
                    {
                        e.printStackTrace();
                        return false;
                    }
                    goodPerson.setVideoPath( "upload/"+newFileName );
                }
            }
        }
        goodPerson.setCreateUser( SessionHelper.getUserId() );
        goodPerson.setType( 1 );//汶南好人类型
        goodPerson.setUpdateTime( new Date() );
        int result = dao.updateByKey( goodPerson );
        return result == 1;
    }
    
    public List<Object> queryIdByOrderbyCreateTime()
    {
        return dao.queryList(getSql("sql_query_goodperson_list_mobile"), new Object[] { },GoodPerson.class);
    }
    
    @SuppressWarnings( {"rawtypes", "unused", "unchecked"} )
    public List<Object> queryDeptByName(Map<String,String> condition)
    {
        String sql = getSql("queryDeptByName");
        List result = new ArrayList();
        SqlRowSet rs = iDao.find(sql,new Object[] { condition.get( "deptName" )});
        Map map = new HashMap();
        Dept dept = null;
        while (rs.next())
        {
          dept = new Dept();
          dept.setDeptId(rs.getString("dept_id"));
          dept.setDeptName(rs.getString("dept_name"));
          dept.setDeptShortName(rs.getString("dept_short_name"));
          dept.setDeptPhone(rs.getString("dept_phone"));
          dept.setDeptFax(rs.getString("dept_fax"));
          dept.setDeptAddress(rs.getString("dept_address"));
          dept.setPDeptId(rs.getString("p_dept_id"));
          dept.setDeptLeader(rs.getString("dept_leader"));
          dept.setDeptRemark(rs.getString("dept_remark"));
          dept.setDeptLevel(rs.getInt("dept_level"));
          result.add(dept);
        }
        return result;        
    }
    
    public List<Object> queryUserByPhotoNum(Map<String,String> condition)
    {
        return dao.queryList(getSql("queryDeptByPhotoNum"), new Object[] { condition.get( "phoneNum" ) },AddrBook.class);
    }
    
    private String getSql(String sqlId)
    {
        return SqlFactory.getInstance( "fc_sql.xml", GoodPersionServiceImpl.class ).getSql( sqlId );
    }
    
    private String getPageSql(String sqlId, int pageNumber ,int pageSize)
    {
        return SqlFactory.getInstance( "fc_sql.xml", GoodPersionServiceImpl.class ).getPageSql( sqlId, pageNumber, pageSize );
    }

}
