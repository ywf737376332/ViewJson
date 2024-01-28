package com.ywf.framework.utils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据类型转换
 *
 * @Author YWF
 * @Date 2024/1/28 18:01
 */
public class ConvertUtils {

    public static int parseInt(Object value) {
        if (value == null) {
            return 0;
        }
        return Integer.parseInt(value.toString());
    }

    public static short parseShort(Object value) {
        if (value == null) {
            return 0;
        }
        return Short.parseShort(value.toString());
    }

    public static long parseLong(Object value) {
        if (value == null) {
            return 0L;
        }
        return Long.parseLong(value.toString());
    }

    public static float parseFloat(Object value) {
        if (value == null) {
            return 0.0f;
        }
        return Float.parseFloat(value.toString());
    }

    public static double parseDouble(Object value) {
        if (value == null) {
            return 0.0d;
        }
        return Double.parseDouble(value.toString());
    }

    public static boolean parseBoolean(Object value) {
        if (value == null) {
            return false;
        }
        return Boolean.parseBoolean(value.toString());
    }

    public static String parseString(Object value) {
        if (value == null) {
            return null;
        }
        return (String) value;
    }

    public static Date parseDate(Object value) {
        if (value == null) {
            return null;
        }
        return (Date) value;
    }

    public static Boolean parseBigBoolean(Object value) {
        if (value == null) {
            return null;
        }
        return Boolean.valueOf(value.toString());
    }

    public static Integer parseInteger(Object value) {
        if (value == null) {
            return null;
        }
        return Integer.valueOf(value.toString());
    }

    public static Long parseBigLong(Object value) {
        if (value == null) {
            return null;
        }
        return Long.valueOf(value.toString());
    }

    public static Float parseBigFloat(Object value) {
        if (value == null) {
            return null;
        }
        return Float.valueOf(value.toString());
    }

    public static Double parseBigDouble(Object value) {
        if (value == null) {
            return null;
        }
        return Double.valueOf(value.toString());
    }

    public static BigDecimal parseBigBigDecimal(Object value) {
        if (value == null) {
            return null;
        }
        return BigDecimal.valueOf(Double.valueOf(value.toString()));
    }


}
