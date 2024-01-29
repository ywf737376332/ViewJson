package com.ywf.framework.utils;

import org.apache.commons.configuration.PropertiesConfiguration;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 利用反射将Properties对象转换为对象
 *
 * @Author YWF
 * @Date 2024/1/28 13:47
 */
public class RelectionUtils {

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
                method.invoke(obj, ConvertUtils.parseInt(val));
                break;
            case "short":
                method = obj.getClass().getMethod(name, short.class);
                method.invoke(obj, ConvertUtils.parseShort(val));
                break;
            case "long":
                method = obj.getClass().getMethod(name, long.class);
                method.invoke(obj, ConvertUtils.parseLong(val));
                break;
            case "float":
                method = obj.getClass().getMethod(name, float.class);
                method.invoke(obj, ConvertUtils.parseFloat(val));
                break;
            case "double":
                method = obj.getClass().getMethod(name, double.class);
                method.invoke(obj, ConvertUtils.parseDouble(val));
                break;
            case "boolean":
                method = obj.getClass().getMethod(name, boolean.class);
                method.invoke(obj, ConvertUtils.parseBoolean(val));
                break;
            case "java.lang.String":
                method = obj.getClass().getMethod(name, String.class);
                method.invoke(obj, ConvertUtils.parseString(val));
                break;
            case "java.util.Date":
                method = obj.getClass().getMethod(name, Date.class);
                method.invoke(obj, ConvertUtils.parseDate(val));
                break;
            case "java.lang.Boolean":
                method = obj.getClass().getMethod(name, Boolean.class);
                method.invoke(obj, ConvertUtils.parseBigBoolean(val));
                break;
            case "java.lang.Integer":
                method = obj.getClass().getMethod(name, Integer.class);
                method.invoke(obj, ConvertUtils.parseInteger(val));
                break;
            case "java.lang.Long":
                method = obj.getClass().getMethod(name, Long.class);
                method.invoke(obj, ConvertUtils.parseBigLong(val));
                break;
            case "java.lang.Float":
                method = obj.getClass().getMethod(name, Float.class);
                method.invoke(obj, ConvertUtils.parseBigFloat(val));
                break;
            case "java.lang.Double":
                method = obj.getClass().getMethod(name, Double.class);
                method.invoke(obj, ConvertUtils.parseBigDouble(val));
                break;
            case "java.math.BigDecimal":
                method = obj.getClass().getMethod(name, BigDecimal.class);
                method.invoke(obj, ConvertUtils.parseBigBigDecimal(val));
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
        for (Field field : target.getClass().getDeclaredFields()) {
            String name = field.getName();
            String typeName = field.getGenericType().getTypeName();
            try {
                if (name.equals("serialVersionUID")) {
                    continue;
                }
                boolean isSuccess = innerStaticClassSetValue(sourceProps, target, typeName, name);
                if (isSuccess) {
                    continue;
                }
                Object val = sourceProps.getProperty(name);
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
        for (Field field : source.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                String name = field.getName();
                if (name.equals("serialVersionUID")) {
                    continue;
                }
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
            boolean isStaticClass = Modifier.isStatic(clazz.getModifiers()) && clazz.getEnclosingClass() != null ? true : false;
            if (isStaticClass) {
                Object innerClass = constructInstance(clazz);
                for (Field field : clazz.getDeclaredFields()) {
                    String name = field.getName();
                    String typeInnerName = field.getGenericType().getTypeName();
                    if (field.getName().equals("serialVersionUID")) {
                        continue;
                    }
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
            boolean isStaticClass = Modifier.isStatic(clazz.getModifiers()) && clazz.getEnclosingClass() != null ? true : false;
            if (isStaticClass) {
                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);
                    String name = field.getName();
                    if (name.equals("serialVersionUID")) {
                        continue;
                    }
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
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return (T)constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException("当前 [" + clazz.getName() + "] 类缺少默认的构造方法");
        }
    }

}
