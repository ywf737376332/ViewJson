package com.ywf.framework.annotation;

/**
 * 被此注解标注的类，里面需要配合@Bean注解使用
 * 可以将@Bean标注的所有方法产生的对象扫描进IOC容器中
 *
 * @Author YWF
 * @Date 2024/2/4 16:35
 */
public @interface Configuration {
}
