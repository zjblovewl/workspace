package com.tyunsoft.base.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

/**
 * 字典信息表
 * @author Flymz
 *
 */
public class Dictionary {

	/**
	 * 字典编码
	 */
	private String dicId;
	
	/**
	 * 字典显示值
	 */
	private String dicName;
	
	/**
	 * 字典创建日期
	 */
	private Date createDate;
	
	/**
	 * 字典所包含的值
	 */
	private List<DictionaryValue> dicValue = new ArrayList<DictionaryValue>();

	public String getId(){
		return dicId;
	}
	
	public String getDicId() {
		return dicId;
	}

	public void setDicId(String dicId) {
		this.dicId = dicId;
	}

	public String getText(){
		return dicName+"("+dicId+")";
	}
	
	public String getDicName() {
		return dicName;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<DictionaryValue> getDicValue() {
		return dicValue;
	}

	public void setDicValue(List<DictionaryValue> dicValue) {
		this.dicValue = dicValue;
	}

	public String toString()
	{
		return JSONObject.fromObject(this).toString();
	}
}
