package com.ywf.framework.utils;

import com.ywf.framework.constant.MessageConstant;

/**
 * 时间计算工具类
 *
 * @Author YWF
 * @Date 2024/3/10 21:35
 */
public class TimeUtils {

    public static String viewTime(long millseconds) {
        // millseconds = millseconds + 50000 + 3540000 + 82800000L + 1036800000L * 10;
        long seconds, minutes, hours, days;
        StringBuilder sb = new StringBuilder();
        seconds = millseconds / 1000;
        minutes = seconds / 60;
        hours = minutes / 60;
        days = hours / 24;
        // 计算显示的具体时间组合
        seconds = seconds - minutes * 60;
        minutes = minutes - hours * 60;
        hours = hours - days * 24;
        if (days != 0)
            return sb.append(days).append(MessageConstant.SYSTEM_STATE_BAR_DAYS).append(hours).append(MessageConstant.SYSTEM_STATE_BAR_HOURS).append(minutes).append(MessageConstant.SYSTEM_STATE_BAR_MINUTES).append(seconds).append(MessageConstant.SYSTEM_STATE_BAR_SECONDS).toString();
        if (hours != 0)
            return sb.append(hours).append(MessageConstant.SYSTEM_STATE_BAR_HOURS).append(minutes).append(MessageConstant.SYSTEM_STATE_BAR_MINUTES).append(seconds).append(MessageConstant.SYSTEM_STATE_BAR_SECONDS).toString();
        if (minutes != 0)
            return sb.append(minutes).append(MessageConstant.SYSTEM_STATE_BAR_MINUTES).append(seconds).append(MessageConstant.SYSTEM_STATE_BAR_SECONDS).toString();
        return seconds + MessageConstant.SYSTEM_STATE_BAR_SECONDS;
        //return sb.append(days).append(" 天 ").append(hours).append(" 小时 ").append(minutes).append(" 分钟 ").append(seconds).append(" 秒").toString();
    }
}
