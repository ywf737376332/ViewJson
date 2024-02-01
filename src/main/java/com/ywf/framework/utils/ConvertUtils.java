package com.ywf.framework.utils;

import cn.hutool.core.convert.Convert;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 数据类型转换
 *
 * @Author YWF
 * @Date 2024/1/28 18:01
 */
public class ConvertUtils {

    /**
     * 转换为字符串
     *
     * @param value
     * @return
     */
    public static String toStr(Object value) {
        return Convert.toStr(value);
    }

    /**
     * 转换为String数组
     *
     * @param value
     * @return
     */
    public static String[] toStrArray(Object value) {
        return Convert.toStrArray(value);
    }

    /**
     * 转换为字符
     *
     * @param value
     * @return
     */
    public static Character toChar(Object value) {
        return Convert.toChar(value);
    }

    /**
     * 转换为Character数组
     *
     * @param value
     * @return
     */
    public static Character[] toCharArray(Object value) {
        return Convert.toCharArray(value);
    }

    /**
     * 转换为byte
     *
     * @param value
     * @return
     */
    public static Byte toByte(Object value) {
        return Convert.toByte(value);
    }

    /**
     * 转换为Byte数组
     *
     * @param value 被转换的值
     */
    public static Byte[] toByteArray(Object value) {
        return Convert.toByteArray(value);
    }

    /**
     * 转换为Short
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Short toShort(Object value) {
        return Convert.toShort(value);
    }

    /**
     * 转换为Number
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Number toNumber(Object value) {
        return Convert.toNumber(value);
    }

    /**
     * 转换为Number数组
     *
     * @param value 被转换的值
     */
    public static Number[] toNumberArray(Object value) {
        return Convert.toNumberArray(value);
    }

    /**
     * 转换为int<br>
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Integer toInt(Object value) {
        return Convert.toInt(value);
    }

    /**
     * 转换为Integer数组<br>
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Integer[] toIntArray(Object value) {
        return Convert.toIntArray(value);
    }

    /**
     * 转换为long<br>
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Long toLong(Object value) {
        return Convert.toLong(value);
    }

    /**
     * 转换为Long数组
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Long[] toLongArray(Object value) {
        return Convert.toLongArray(value);
    }

    /**
     * 转换为double<br>
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Double toDouble(Object value) {
        return Convert.toDouble(value);
    }

    /**
     * 转换为Double数组<br>
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Double[] toDoubleArray(Object value) {
        return Convert.toDoubleArray(value);
    }

    /**
     * 转换为Float<br>
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Float toFloat(Object value) {
        return Convert.toFloat(value);
    }

    /**
     * 转换为Float数组
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Float[] toFloatArray(Object value) {
        return Convert.toFloatArray(value);
    }

    /**
     * 转换为boolean<br>
     * String支持的值为：true、false、yes、ok、no，1,0
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Boolean toBool(Object value) {
        return Convert.toBool(value);
    }

    /**
     * 转换为Boolean数组<br>
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Boolean[] toBooleanArray(Object value) {
        return Convert.toBooleanArray(value);
    }

    /**
     * 转换为BigInteger<br>
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static BigInteger toBigInteger(Object value) {
        return Convert.toBigInteger(value);
    }

    /**
     * 转换为BigDecimal
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static BigDecimal toBigDecimal(Object value) {
        return Convert.toBigDecimal(value);
    }

    /**
     * 转换为Date<br>
     *
     * @param value 被转换的值
     * @return 结果
     * @since 4.1.6
     */
    public static Date toDate(Object value) {
        return Convert.toDate(value);
    }

    /**
     * LocalDateTime
     *
     * @param value 被转换的值
     * @return 结果
     * @since 5.0.7
     */
    public static LocalDateTime toLocalDateTime(Object value) {
        return Convert.toLocalDateTime(value);
    }

    /**
     * 转换为Enum对象<br>
     *
     * @param <E>   枚举类型
     * @param clazz Enum的Class
     * @param value 值
     * @return Enum
     */
    public static <E extends Enum<E>> E toEnum(Class<E> clazz, Object value) {
        return Convert.toEnum(clazz, value);
    }

    /**
     * 转换为集合类
     *
     * @param collectionType 集合类型
     * @param elementType    集合中元素类型
     * @param value          被转换的值
     */
    public static Collection<?> toCollection(Class<?> collectionType, Class<?> elementType, Object value) {
        return Convert.toCollection(collectionType, elementType, value);
    }

    /**
     * 转换为ArrayList，元素类型默认Object
     *
     * @param value 被转换的值
     */
    public static List<?> toList(Object value) {
        return Convert.toList(value);
    }

    /**
     * 转换为HashSet
     *
     * @param <T>         元素类型
     * @param elementType 集合中元素类型
     * @param value       被转换的值
     * @return {@link HashSet}
     */
    @SuppressWarnings("unchecked")
    public static <T> Set<T> toSet(Class<T> elementType, Object value) {
        return Convert.toSet(elementType, value);
    }

    /**
     * 转换为Map
     *
     * @param <K>       键类型
     * @param <V>       值类型
     * @param keyType   键类型
     * @param valueType 值类型
     * @param value     被转换的值
     * @return {@link Map}
     * @since 4.6.8
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, Object value) {
        return Convert.toMap(keyType, valueType, value);
    }

    /**
     * 转换值为指定类型，不抛异常转换<br>
     * 当转换失败时返回{@code null}
     *
     * @param <T> 目标类型
     * @param type 目标类型
     * @param value 值
     * @return 转换后的值，转换失败返回null
     */
    public static <T> T convertQuietly(Type type, Object value) {
        return Convert.convertQuietly(type, value, null);
    }


}
