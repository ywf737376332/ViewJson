package com.ywf.component.toolToast;

import cn.hutool.core.util.StrUtil;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/2/27 22:32
 */
public class Main {

    public static void main(String[] args) {
        String str = "2022.5.5";
        String searchStr = ".";
        int count = StrUtil.count(str, searchStr);
        System.out.println(count); // 输出结果为2
    }

}
