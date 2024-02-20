package com.ywf.framework.handle;

import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.annotation.MainView;
import com.ywf.framework.annotation.PropertySource;
import com.ywf.framework.config.GlobalKEY;
import com.ywf.framework.utils.ObjectUtils;
import com.ywf.framework.utils.ReflectUtils;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final static Logger logger = LoggerFactory.getLogger(ConfigLoadHandler.class);

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
        logger.info("扫描到主页面启动类:{}", mainViewClassesAnno);
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
        logger.info("扫描到配置类:{}", configClassesAnno);
        //获取需要注入属性的所有字段
        Set<Field> fieldClassesAnno = reflections.getFieldsAnnotatedWith(Autowired.class);
        logger.info("扫描实例化注入的类:{}", fieldClassesAnno);
        // 从本地文件Properties中夹在配置文件到Properties
        PropertiesConfiguration configuration = ObjectUtils.getBean(GlobalKEY.USER_PRPPERTIES_CONFIG);
        for (Class<?> configClass : configClassesAnno) {
            logger.info("配置类实例化开始~");
            // 创建配置类
            Object configInstance = ReflectUtils.constructInstance(configClass);
            // 将Properties配置文件转换为配置类
            Object instanceObject = ReflectUtils.propConvertObject(configuration, configInstance);
            logger.info("配置类实例化信息{}", instanceObject);
            //对有Autowired注解的属性字段实例化注入
            fieldInstances(instanceObject, fieldClassesAnno);
            logger.info("配置类实例化结束~");
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
                logger.info("属性注入:{}", field.getDeclaringClass().getName()+"."+field.getName());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


}
