package com.ywf.framework.annotation;

import java.lang.annotation.*;

/**
 * 属性文件注入注解
 *
 * @Author YWF
 * @Date 2024/1/28 22:07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface PropertySource {

    String value() default "";

}
