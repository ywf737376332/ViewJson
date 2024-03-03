package com.ywf.framework.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ywf.framework.ioc.ResourceContext;

import java.io.*;

/**
 * 文件工具类
 *
 * @Author YWF
 * @Date 2024/3/3 19:58
 */
public class FileUtils {

    public static final int FILE_TYPE = 1;
    public static final int STREAM_TYPE = 2;

    /**
     * 读取JSON文件为实体对象
     *
     * @param resourceUrl
     * @param classType
     * @param <T>
     * @return
     */
    public static <T> T readJSONFile(String resourceUrl, Class<T> classType, int resourceType) {
        switch (resourceType) {
            case FILE_TYPE:
                try (FileReader reader = new FileReader(resourceUrl)) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(reader, classType);
                } catch (IOException e) {
                    throw new RuntimeException("JSON文件加载失败：" + e.getMessage());
                }
            case STREAM_TYPE:
                try (InputStream inputStream = ResourceContext.class.getClassLoader().getResourceAsStream(resourceUrl)) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(inputStream, classType);
                } catch (IOException e) {
                    throw new RuntimeException("JSON文件加载失败：" + e.getMessage());
                }
            default:
                return null;
        }
    }

    /**
     * 将对象按以格式化json的方式保存到文件
     *
     * @param object
     * @param fileName
     */
    public static void saveJSONFile(Object object, String fileName) {
        File file = new File(fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(JsonUtil.formatJSONObject(object));
        } catch (IOException e) {
            throw new RuntimeException("JSON文件保存失败：" + e.getMessage());
        }
    }

}
