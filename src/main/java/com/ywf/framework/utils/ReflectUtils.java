package com.ywf.framework.utils;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.WeakConcurrentMap;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

/**
 * 利用反射将Properties对象转换为对象
 * 对字段进行缓存，加快反射速度
 *
 * @Author YWF
 * @Date 2024/1/28 13:47
 */
public class ReflectUtils {

    private final static Logger logger = LoggerFactory.getLogger(ReflectUtils.class);

    /**
     * 字段缓存
     */
    private static final WeakConcurrentMap<Class<?>, Field[]> FIELDS_CACHE = new WeakConcurrentMap<>();

    /**
     * 获取所有的字段
     *
     * @param beanClass
     * @return
     */
    public static Field[] getFields(Class<?> beanClass) {
        Assert.notNull(beanClass);
        return FIELDS_CACHE.computeIfAbsent(beanClass, () -> Arrays.stream(beanClass.getDeclaredFields())
                .filter(field -> !field.getName().equals("serialVersionUID"))
                .toArray(Field[]::new));
    }

    /**
     * 设置数据类对象的属性
     *
     * @param obj  数据类对象的实例
     * @param name 属性名
     * @param type 属性类型名
     * @param val  需要存入的属性值
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private static <T> void setValues(T obj, String name, String type, Object val) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = null;
        // 通过属性类型来获取相应的方法以及强制转化属性值并初始化相应属性
        // 这里考虑到了大部分常用的数据类型，可拿来即用。
        name = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
        switch (type) {
            case "int":
                method = obj.getClass().getMethod(name, int.class);
                method.invoke(obj, ConvertUtils.toInt(val));
                break;
            case "short":
                method = obj.getClass().getMethod(name, short.class);
                method.invoke(obj, ConvertUtils.toShort(val));
                break;
            case "long":
                method = obj.getClass().getMethod(name, long.class);
                method.invoke(obj, ConvertUtils.toLong(val));
                break;
            case "float":
                method = obj.getClass().getMethod(name, float.class);
                method.invoke(obj, ConvertUtils.toFloat(val));
                break;
            case "double":
                method = obj.getClass().getMethod(name, double.class);
                method.invoke(obj, ConvertUtils.toDouble(val));
                break;
            case "boolean":
                method = obj.getClass().getMethod(name, boolean.class);
                method.invoke(obj, ConvertUtils.toBool(val));
                break;
            case "java.lang.String":
                method = obj.getClass().getMethod(name, String.class);
                method.invoke(obj, ConvertUtils.toStr(val));
                break;
            case "java.util.Date":
                method = obj.getClass().getMethod(name, Date.class);
                method.invoke(obj, ConvertUtils.toDate(val));
                break;
            case "java.lang.Boolean":
                method = obj.getClass().getMethod(name, Boolean.class);
                method.invoke(obj, ConvertUtils.toBool(val));
                break;
            case "java.lang.Integer":
                method = obj.getClass().getMethod(name, Integer.class);
                method.invoke(obj, ConvertUtils.toInt(val));
                break;
            case "java.lang.Long":
                method = obj.getClass().getMethod(name, Long.class);
                method.invoke(obj, ConvertUtils.toLong(val));
                break;
            case "java.lang.Float":
                method = obj.getClass().getMethod(name, Float.class);
                method.invoke(obj, ConvertUtils.toFloat(val));
                break;
            case "java.lang.Double":
                method = obj.getClass().getMethod(name, Double.class);
                method.invoke(obj, ConvertUtils.toDouble(val));
                break;
            case "java.math.BigDecimal":
                method = obj.getClass().getMethod(name, BigDecimal.class);
                method.invoke(obj, ConvertUtils.toBigDecimal(val));
                break;
        }
    }

    /**
     * properties 转换为对象
     *
     * @param sourceProps
     * @param target
     * @param <T>
     */
    public static <T> T propConvertObject(PropertiesConfiguration sourceProps, T target) {
        for (Field field : getFields(target.getClass())) {
            String name = field.getName();
            String typeName = field.getGenericType().getTypeName();
            try {
                boolean isSuccess = innerStaticClassSetValue(sourceProps, target, typeName, name);
                if (isSuccess) {
                    continue;
                }
                Object val = sourceProps.getProperty(name);
                if (val == null) {
                    logger.warn("当前字段：【{}】为空", name);
                }
                setValues(target, name, typeName, val);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return target;
    }


    public static <T> PropertiesConfiguration objectConvertProp(T source) {
        PropertiesConfiguration targetProps = new PropertiesConfiguration();
        for (Field field : getFields(source.getClass())) {
            try {
                field.setAccessible(true);
                String name = field.getName();
                Object value = field.get(source);
                boolean isSuccess = innerStaticClassGetValue(targetProps, value, name);
                if (isSuccess) {
                    continue;
                }
                targetProps.setProperty(name, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return targetProps;
    }

    /**
     * 静态内部类实例化赋值
     *
     * @param sourceProps
     * @param target
     * @param typeName
     * @param innerClassField
     * @param <T>
     * @return
     */
    private static <T> boolean innerStaticClassSetValue(PropertiesConfiguration sourceProps, T target, String typeName, String innerClassField) {
        // 判断是否内部静态类
        boolean isSuccess = false;
        try {
            Class<?> clazz = Class.forName(typeName);
            boolean isStaticClass = isStatic(clazz);
            if (isStaticClass) {
                Object innerClass = constructInstance(clazz);
                for (Field field : getFields(clazz)) {
                    String name = field.getName();
                    String typeInnerName = field.getGenericType().getTypeName();
                    Object val = sourceProps.getProperty(innerClassField + "." + name);
                    setValues(innerClass, name, typeInnerName, val);
                }
                String innerClassSetMethod = "set" + (innerClassField.substring(0, 1).toUpperCase() + innerClassField.substring(1));
                Method method = target.getClass().getMethod(innerClassSetMethod, clazz);
                method.invoke(target, innerClass);
                isSuccess = true;
            }
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return isSuccess;
    }

    private static <T> boolean innerStaticClassGetValue(PropertiesConfiguration targetProps, T source, String innerClassField) {
        // 判断是否内部静态类
        boolean isSuccess = false;
        try {
            Class<?> clazz = source.getClass();
            boolean isStaticClass = isStatic(clazz);
            if (isStaticClass) {
                for (Field field : getFields(clazz)) {
                    field.setAccessible(true);
                    String name = field.getName();
                    Object value = field.get(source);
                    targetProps.setProperty(innerClassField + "." + name, value);
                }
                isSuccess = true;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return isSuccess;
    }

    public static <T> T constructInstance(Class<T> clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("当前 [" + clazz.getName() + "] 实例化失败");
        }
    }

    /**
     * 通过反射获取属性的值
     *
     * @param obj       对象
     * @param fieldName 属性名称
     * @param <T>
     * @return
     */
    public static <T> Object getFieldValue(T obj, String fieldName) {
        Class<?> clazz = obj.getClass();
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("不存在该属性名：" + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException("字段访问受限：" + e.getMessage());
        }
    }

    public static <T> int copyObjectFiledValue(T defaultSettings, T userSettings) {
        int counts = 0;
        for (Field field : getFields(userSettings.getClass())) {
            field.setAccessible(true);
            try {
                field.setAccessible(true);
                Object fieldValue = field.get(userSettings);
                String fieldName = field.getName();

                if (fieldValue == null || (isStatic(fieldValue.getClass()) && checkNullField(fieldValue))) {
                    Object defaultValue = getFieldValue(defaultSettings, fieldName);
                    field.set(userSettings, defaultValue);
                    counts++;
                    logger.info("配置拷贝中... 拷贝次数：{}, 属性名称：{},默认值：{}", counts, fieldName, defaultValue);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return counts;
    }

    private static <T> boolean checkNullField(T object) {
        for (Field field : getFields(object.getClass())) {
            field.setAccessible(true);
            try {
                field.setAccessible(true);
                Object fieldValue = field.get(object);
                String fieldName = field.getName();
                // 出现空字段，跳出循环，直接整个赋值，没必要再判断后续字段
                if (fieldValue == null) {
                    logger.warn("当前属性：{}，出现空字段：{}", object, fieldName);
                    return true;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    private static boolean isStatic(Class<?> clazz) {
        return Modifier.isStatic(clazz.getModifiers()) && clazz.getEnclosingClass() != null ? true : false;
    }
}
