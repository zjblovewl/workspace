
package com.tyunsoft.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 列对象注解 
 * @author  Flyer.zuo
 * @version  [v1.0, 2014年8月12日]
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface Column
{
    
    /**
     * 列的数据库字段名
     * @return 注解列对应的数据库字段
     */
    String name();

    /**
     * 列类型，默认是varchar类型
     * @return 注解列的类型
     */
    ColumnType type() default ColumnType.VARCHAR;

    /**
     * 是否主键
     * @return 该列是否是主键
     */
    boolean isKey() default false;
    
    /**
     * 字典编码
     * @return 如果该列使用字典，则需要注解对应的字典编码
     */
    String dictionaryCode() default "";

}
