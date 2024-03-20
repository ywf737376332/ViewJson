package com.ywf.component.setting;

import com.ywf.framework.base.BorderBuilder;
import com.ywf.framework.base.ThemeColor;
import com.ywf.framework.init.SysConfigInit;
import com.ywf.framework.ui.EditScrollPane;
import com.ywf.component.TreeBuilder;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 配置展示面板
 *
 * @Author YWF
 * @Date 2024/3/18 22:00
 */
public class DefaultSettingPanel extends JPanel {

    public DefaultSettingPanel() {
        super();
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 300));
        setBorder(BorderBuilder.emptyBorder(0, 20, 20, 20));
        init();
    }

    private void init() {
        SwingUtilities.invokeLater(() -> {
            JTree tree = TreeBuilder.getInstance().initTree(readeFile().toString());
            EditScrollPane scrollPane = new EditScrollPane(tree);
            scrollPane.setBorder(BorderBuilder.border(1,ThemeColor.themeColor));
            scrollPane.setFocusable(false);
            add(scrollPane, BorderLayout.CENTER);
            tree.setBorder(BorderBuilder.emptyBorder(5));
        });
    }

    private StringBuilder readeFile() {
        StringBuilder stringBuilder = null;
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        try {
            File file = new File(SysConfigInit.getSystemRootFilePath()); // 替换为你的文件路径
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return stringBuilder;
    }
}
