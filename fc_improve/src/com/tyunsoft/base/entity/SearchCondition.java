package com.tyunsoft.base.entity;

/**
 * 查询条件
 * 
 * @author Flyer.zuo
 * @version [v1.0, 2014年9月11日]
 */
public class SearchCondition {

    
    public static final String LIKE = "like";
    
    public static final String EQUALS = "=";
    
    public static final String BETWEEN = "between";
    
    public static final String EQUALS_ONE = "eone";
    
    /**
     * 数据库字段名
     */
    private String column;

    /**
     * 连接符 like = between
     */
    private String linkSign;

    /**
     * 值，如果是like = 则取value，否则取startValue endValue
     */
    private String value = "";

    /**
     * 值，如果是like = 则取value，否则取startValue endValue
     */
    private String startValue = "";

    /**
     * 值，如果是like = 则取value，否则取startValue endValue
     */
    private String endValue = "";

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getLinkSign() {
        return linkSign;
    }

    public void setLinkSign(String linkSign) {
        this.linkSign = linkSign;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStartValue() {
        return startValue;
    }

    public void setStartValue(String startValue) {
        this.startValue = startValue;
    }

    public String getEndValue() {
        return endValue;
    }

    public void setEndValue(String endValue) {
        this.endValue = endValue;
    }

}
