package com.ywf.component.splitDemo;


import com.ywf.framework.init.SysConfigInit;
import com.ywf.framework.ioc.ConfigurableApplicationContext;
import com.ywf.framework.utils.FileUtils;
import com.ywf.framework.utils.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;


/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/2/22 11:50
 */
public class TestMain {
    private final static Logger logger = LoggerFactory.getLogger(TestMain.class);

    public static void main(String[] args) {
        //test002();
        test003();
    }

    public static void test001() {

    }

    public static void test002() {

    }

    public static void test003() {
        ConfigurableApplicationContext applicationContextDefault = FileUtils.readJSONFile("config/application.json", ConfigurableApplicationContext.class, FileUtils.STREAM_TYPE);
        System.out.println("applicationContextDefault:" + applicationContextDefault.toString());
        String applicationRootPath = SysConfigInit.getSystemRootPath();
        ConfigurableApplicationContext applicationContext = FileUtils.readJSONFile(applicationRootPath + "/config/application.json", ConfigurableApplicationContext.class, FileUtils.FILE_TYPE);
        System.out.println("applicationContext:" + applicationContext.toString());


        compareObjects(applicationContextDefault, applicationContext, applicationRootPath);
    }

    public static <T> void compareObjects(T defaultSettings, T userSettings, String applicationRootPath) {
        int counts = 0;
        for (Field field : ReflectUtils.getFields(userSettings.getClass())) {
            field.setAccessible(true);
            try {
                field.setAccessible(true);
                Object fieldValue = field.get(userSettings);
                String fieldName = field.getName();
                if (fieldValue == null) {
                    Object defaultValue = getProperty(defaultSettings, fieldName);
                    field.set(userSettings, defaultValue);
                    counts++;
                    logger.info("配置加载中... 加载次数：{}, 键：{},值：{}", counts, fieldName, defaultValue);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        if (counts > 0) {
            FileUtils.saveJSONFile(userSettings, applicationRootPath + "/config/application.json");
            logger.info("加载应用配置信息到用户目录，映射结束，配置信息保存到文件");
        }
        logger.info("加载应用配置信息到用户目录，程序执行结束~");
    }

    private static <T> Object getProperty(T obj, String property) {
        Class<?> clazz = obj.getClass();
        try {
            Field field = clazz.getDeclaredField(property);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException e) {
            // 处理异常
        } catch (IllegalAccessException e) {
            // 处理异常
        }
        return null;
    }
}