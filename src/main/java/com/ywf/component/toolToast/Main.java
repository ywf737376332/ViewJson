package com.ywf.component.toolToast;

import com.ywf.framework.utils.StrUtils;
import com.ywf.framework.utils.TypeUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.util.Map;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/3/9 9:39
 */
public class Main {

    public static void main(String[] args) {
        //validate();

        boolean isYaml = isYaml("12312");
        System.out.println("isYaml:"+isYaml);
    }

    private static void validate() {
        String yamlStr = "ewrewrewrewe"; // 待校验的YAML字符串
        try {
            Yaml yaml = new Yaml();
            Object obj = yaml.load(yamlStr); // 尝试解析YAML字符串
            System.out.println("YAML语法有效");
        } catch (YAMLException e) {
            System.out.println("YAML语法无效：" + e.getMessage());
        }
    }

    public static boolean isYaml(String content) {
        try {
            Yaml yaml = new Yaml();
            String tmpText = content.replaceAll("^---.*\n", "---\n");
            tmpText = tmpText.replaceAll("!ruby.*\n", "\n");
            Map<String,Object> load = yaml.load(tmpText);
            System.out.println("校验通过");
            if (load == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
