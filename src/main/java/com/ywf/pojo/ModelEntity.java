package com.ywf.pojo;

/**
 * JList数据模型实体类
 *
 * @Author YWF
 * @Date 2024/3/21 17:34
 */
public class ModelEntity<T> {
    private String name;
    private T value;

    public ModelEntity(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}