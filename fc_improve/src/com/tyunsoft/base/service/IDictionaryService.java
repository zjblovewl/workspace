package com.tyunsoft.base.service;

import java.util.List;

import com.tyunsoft.base.entity.Dictionary;
import com.tyunsoft.base.entity.DictionaryValue;

/**
 * 字典管理相关业务接口
 * @author Flymz
 *
 */
public interface IDictionaryService {

	/**
	 * 查询字典信息列表
	 * @return 字典信息列表
	 */
	List<Dictionary> search();
	
	/**
	 * 根据字典编码查询字典值信息
	 * @param dicId 字典编码
	 * @return 字典值信息列表
	 */
	List<DictionaryValue> query(String dicId);
	
	/**
	 * 根据字典编码查询字典信息
	 * @param dicId 字典编码
	 * @return 字典信息
	 */
	Dictionary get(String dicId);
	
	/**
	 * 新增字典信息
	 * @param dic 字典信息
	 * @return 新增是否成功
	 */
	boolean insert(Dictionary dic);
	
	/**
	 * 删除字典信息
	 * @param dicId 字典ID
	 * @return 删除字典是否成功
	 */
	boolean delete(String dicId);
	
	/**
	 * 更新字典信息
	 * @param dic 字典信息
	 * @return 更新是否成功
	 */
	boolean update(Dictionary dic);
	
	/**
	 * 更新字典值信息
	 * @param dicId 所更新的字典编码
	 * @param vals 字典值信息
	 * @return 更新是否成功
	 */
	boolean updateDicValues(String dicId,List<DictionaryValue> vals);
	
}
