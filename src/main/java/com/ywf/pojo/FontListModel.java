package com.ywf.pojo;

import com.ywf.component.setting.FrameFontsPanel;

import javax.swing.*;
import java.util.ArrayList;

/**
 * JList数据模型
 *
 * @Author YWF
 * @Date 2024/3/21 17:34
 */
public class FontListModel <T> extends AbstractListModel<String> {
    private final ArrayList<ModelEntity> modelList;

    public FontListModel(ArrayList<ModelEntity> modelList) {
        this.modelList = modelList;
    }

    @Override
    public int getSize() {
        return modelList.size();
    }

    @Override
    public String getElementAt(int index) {
        //加入语言翻译转换，以适应国际化
        return FrameFontsPanel.getMessage(modelList.get(index).getName());
    }

    public T getValueAt(int index) {
        return (T) modelList.get(index).getValue();
    }


}