package com.tyunsoft.base.entity;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 字典值信息
 * @author Flymz
 *
 */
public class DictionaryValue{

	/**
	 * 字典编码
	 */
	private String dicId;
	
	/**
	 * 字典值编码
	 */
	private String dicValueId;
	
	/**
	 * 字典值显示
	 */
	private String dicValueLabel;
	
	/**
	 * 字典值排序
	 */
	private int dicValueOrder;

	public String getDicId() {
		return dicId;
	}

	public void setDicId(String dicId) {
		this.dicId = dicId;
	}

	public String getDicValueId() {
		return dicValueId;
	}

	public void setDicValueId(String dicValueId) {
		this.dicValueId = dicValueId;
	}

	public String getDicValueLabel() {
		return dicValueLabel;
	}

	public void setDicValueLabel(String dicValueLabel) {
		this.dicValueLabel = dicValueLabel;
	}

	public int getDicValueOrder() {
		return dicValueOrder;
	}

	public void setDicValueOrder(int dicValueOrder) {
		this.dicValueOrder = dicValueOrder;
	}
	
	public String toString()
	{
		return JSONObject.fromObject(this).toString();
	}
	
	public Map<String,String> toMap(){
	    Map<String,String> map = new HashMap<String,String>();
	    map.put( "1", dicId );
	    map.put( "2", dicValueId );
	    map.put( "3", dicValueLabel );
	    map.put( "4", String.valueOf( dicValueOrder ) );
	    return map;
	}
	
}
