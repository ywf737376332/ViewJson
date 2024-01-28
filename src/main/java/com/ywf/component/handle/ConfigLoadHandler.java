package com.ywf.component.handle;

import com.ywf.framework.annotation.PropertySource;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/1/28 22:14
 */
public class ConfigLoadHandler {

    private static final ConcurrentHashMap<String, Object> PROTOTYPE_MAP = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        String basePackages = "com.ywf";
        // 初始化工具类
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(basePackages).addScanners(new SubTypesScanner()).addScanners(new FieldAnnotationsScanner()));

        // 获取某个包下类型注解对应的类
        Set<Class<?>> typeClass = reflections.getTypesAnnotatedWith(PropertySource.class, true);
        for (Class<?> aClass : typeClass) {
            System.out.println("当前类：" + aClass.getName());
        }
    }

}
