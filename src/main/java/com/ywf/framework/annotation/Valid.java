package com.ywf.framework.annotation;

import com.ywf.framework.enums.ValidEnum;

import java.lang.annotation.*;

/**
 * 配置类属性字段校验
 *
 * @Author YWF
 * @Date 2024/1/31 11:32
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Valid {

    ValidEnum type() default ValidEnum.NOT_LIMIT;

    String value();

    String message();
}
