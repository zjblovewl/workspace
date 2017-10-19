
package com.tyunsoft.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表注解对象
 * @author  Flyer.zuo
 * @version  [v1.0, 2014年8月12日]
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
public @interface Table
{
    String name();
}
