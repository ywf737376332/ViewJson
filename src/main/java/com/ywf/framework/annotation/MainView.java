package com.ywf.framework.annotation;

import java.lang.annotation.*;

/**
 * 启动界面
 *
 * @Author YWF
 * @Date 2024/1/29 15:26
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Documented
public @interface MainView {
}
