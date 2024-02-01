package com.ywf.framework.handle;

import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.annotation.MainView;
import com.ywf.framework.annotation.PropertySource;
import com.ywf.framework.utils.ObjectUtils;
import com.ywf.framework.utils.ReflectUtils;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;

/**
 * 容器初始化执行器
 *
 * @Author YWF
 * @Date 2024/1/28 22:14
 */
public class ConfigLoadHandler {

    private String basePackages;

    public ConfigLoadHandler() {
    }

    public ConfigLoadHandler(String basePackages) {
        this.basePackages = basePackages;
    }

    public <T> T appViewInit(String basePackages) {
        // 初始化工具类
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(basePackages).addScanners(new SubTypesScanner()).addScanners(new FieldAnnotationsScanner()));
        // 获取某个包下类型注解对应的类
        Set<Class<?>> mainViewClassesAnno = reflections.getTypesAnnotatedWith(MainView.class, true);
        if (mainViewClassesAnno.size() > 1) {
            throw new RuntimeException("应用启动失败,存在多个MainView注解的启动类");
        }
        if (mainViewClassesAnno.size() == 0) {
            throw new RuntimeException("应用启动失败,不存在MainView注解的启动类");
        }
        Class<?> mainViewClasses = mainViewClassesAnno.iterator().next();
        Object mainView = ReflectUtils.constructInstance(mainViewClasses);
        return (T) mainView;
    }

    /**
     * 根据Autowired注解,对所有标注过的属性进行实例化注入
     *
     * @param basePackages
     * @param rootPath
     */
    public void configLoadAutowired(String basePackages, String rootPath) {
        // 初始化工具类
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(basePackages).addScanners(new SubTypesScanner()).addScanners(new FieldAnnotationsScanner()));
        // 获取某个包下类型注解对应的类
        Set<Class<?>> configClassesAnno = reflections.getTypesAnnotatedWith(PropertySource.class, true);
        //获取需要注入属性的所有字段
        Set<Field> fieldClassesAnno = reflections.getFieldsAnnotatedWith(Autowired.class);
        // 从本地文件Properties中夹在配置文件到Properties
        //PropertiesConfigurationContext propertiesContext = new PropertiesConfigurationContext(rootPath);
        //PropertiesConfiguration configuration = propertiesContext.load();
        PropertiesConfiguration configuration = ObjectUtils.getBean(ApplicationContext.USER_PATH + PropertiesConfiguration.class);
        for (Class<?> configClass : configClassesAnno) {
            // 创建配置类
            Object configInstance = ReflectUtils.constructInstance(configClass);
            // 将Properties配置文件转换为配置类
            Object instanceObject = ReflectUtils.propConvertObject(configuration, configInstance);
            //对有Autowired注解的属性字段实例化注入
            fieldInstances(instanceObject, fieldClassesAnno);
        }
    }

    /**
     * 属性字段实例化注入
     *
     * @param config
     * @param fieldClassesAnno
     */
    private void fieldInstances(Object config, Set<Field> fieldClassesAnno) {
        Iterator<Field> iterator = fieldClassesAnno.iterator();
        while (iterator.hasNext()) {
            Field field = iterator.next();
            Class<?> declaringClass = field.getDeclaringClass();
            try {
                field.set(declaringClass, config);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


}
