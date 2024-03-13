package com.ywf.component.loading;

import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.core.util.StrUtil;
import com.ywf.component.JSONRSyntaxTextArea;
import com.ywf.component.toast.Toast;
import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.constant.MessageConstant;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.enums.TextConvertEnum;
import com.ywf.framework.enums.TextTypeEnum;
import com.ywf.framework.ioc.ConfigurableApplicationContext;
import com.ywf.framework.utils.ImageUtils;
import com.ywf.framework.utils.JsonUtil;
import com.ywf.framework.utils.WindowUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 后台执行事件集合
 *
 * @Author YWF
 * @Date 2024/3/10 20:06
 */
public class BackgroundTaskKit {

    @Autowired
    public static ConfigurableApplicationContext applicationContext;

    /**
     * 复制内容到图片
     */
    public static class CopyJsonToPictAction extends BackgroundTask {
        private JSONRSyntaxTextArea rSyntaxTextArea;

        public CopyJsonToPictAction(JSONRSyntaxTextArea rSyntaxTextArea) {
            this.rSyntaxTextArea = rSyntaxTextArea;
        }

        @Override
        public void actionPerformedImpl() {
            try {
                //Thread.sleep(8000);
                int pictureScale = applicationContext.getPictureQualityState();
                //绘制图片
                BufferedImage image = ImageUtils.generateTextAreaImage(rSyntaxTextArea, pictureScale);
                // 保存图片到剪贴板
                ImageUtils.imageToClipboard(image);
            } catch (Exception e) {
                throw new RuntimeException("图片复制失败: " + e.getMessage());
            }
        }

        @Override
        public void successTips() {
            Toast.success(WindowUtils.getFrame(), MessageConstant.SYSTEM_COPY_IMAGE_SUCCESS_TIP);
        }
    }

    /**
     * 复制内容到剪贴板
     */
    public static class CopyJsonAction extends BackgroundTask {
        private JSONRSyntaxTextArea rSyntaxTextArea;

        public CopyJsonAction(JSONRSyntaxTextArea rSyntaxTextArea) {
            this.rSyntaxTextArea = rSyntaxTextArea;
        }

        @Override
        public void actionPerformedImpl() {
            try {
                StringSelection stringSelection = new StringSelection(rSyntaxTextArea.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            } catch (Exception e) {
                throw new RuntimeException("图片复制失败: " + e.getMessage());
            }
        }

        @Override
        public void successTips() {
            Toast.success(WindowUtils.getFrame(), MessageConstant.SYSTEM_COPY_JSON_SUCCESS_TIP);
        }
    }

    public static class FormatJsonAction extends BackgroundTask {
        private JSONRSyntaxTextArea rSyntaxTextArea;
        private String formatAfterText = null;

        public FormatJsonAction(JSONRSyntaxTextArea rSyntaxTextArea) {
            this.rSyntaxTextArea = rSyntaxTextArea;
        }

        @Override
        public void actionPerformedImpl() {
            String text = rSyntaxTextArea.getText();
            TextTypeEnum textType = rSyntaxTextArea.getTextType();
            int converState = rSyntaxTextArea.getChineseConverState();
            switch (TextConvertEnum.findConverEnumByState(converState)) {
                case CH_TO_UN:
                    // 1.先替换回车后面的空格
                    // 2.再替换回车和换行，
                    text = UnicodeUtil.toUnicode(text.replaceAll("(?<=\\n)[ \\t]+", "").replaceAll("[\\t\\n\\r]", ""));
                    formatAfterText = JsonUtil.contentFormat(textType, text);
                    break;
                case UN_TO_CH:
                    formatAfterText = JsonUtil.contentFormat(textType, UnicodeUtil.toString(text));
                    break;
                default:
                    formatAfterText = JsonUtil.contentFormat(textType, text);
            }
        }

        @Override
        public void successTips() {
            if (StrUtil.isNotBlank(formatAfterText)) {
                rSyntaxTextArea.setText(formatAfterText);
            }
        }
    }

    /**
     * RSyntaxTextArea 内容按照相应的格式保存为图片
     */
    public static class SaveJsonToImageAction extends BackgroundTask {
        private JSONRSyntaxTextArea rSyntaxTextArea;
        private File fileToSave;
        private JFileChooser fileChooser;
        private int pictureScale;

        public SaveJsonToImageAction(JSONRSyntaxTextArea rSyntaxTextArea, JFileChooser fileChooser, int pictureScale) {
            this.rSyntaxTextArea = rSyntaxTextArea;
            this.fileChooser = fileChooser;
            this.pictureScale = pictureScale;
        }

        @Override
        public void actionPerformedImpl() {
            fileToSave = fileChooser.getSelectedFile();
            BufferedImage image = new BufferedImage(rSyntaxTextArea.getWidth() * pictureScale, rSyntaxTextArea.getHeight() * pictureScale, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            g2d.scale(pictureScale, pictureScale); // 根据画布大小调整缩放比例
            rSyntaxTextArea.print(g2d);
            g2d.dispose();
            try {
                ImageIO.write(image, "png", new File(fileToSave.getPath() + SystemConstant.SAVE_IMAGE_EXTENSION));
            } catch (IOException e) {
                Toast.error(frame, MessageConstant.SYSTEM_IMAGE_SAVE_FAIL + e.getMessage());
                throw new RuntimeException("Error saving image: " + e.getMessage());
            }
        }

        @Override
        public void successTips() {
            Toast.success(frame, MessageConstant.SYSTEM_IMAGE_SAVE_SUCCESS + fileToSave.getAbsolutePath() + SystemConstant.SAVE_IMAGE_EXTENSION);
        }
    }

    /**
     * RSyntaxTextArea 内容按照相应的格式保存为文件
     */
    public static class SaveJsonToFileAction extends BackgroundTask {
        private JSONRSyntaxTextArea rSyntaxTextArea;
        private JFileChooser fileChooser;
        private File fileToSave;

        public SaveJsonToFileAction(JSONRSyntaxTextArea rSyntaxTextArea, JFileChooser fileChooser) {
            this.rSyntaxTextArea = rSyntaxTextArea;
            this.fileChooser = fileChooser;
        }

        @Override
        public void actionPerformedImpl() {
            fileToSave = fileChooser.getSelectedFile();
            try {
                FileWriter fileWriter = new FileWriter(fileToSave + SystemConstant.SAVE_JSON_EXTENSION);
                fileWriter.write(rSyntaxTextArea.getText());
                fileWriter.close();
            } catch (IOException e) {
                Toast.error(frame, MessageConstant.SYSTEM_FILE_SAVE_FAIL + e.getMessage());
                throw new RuntimeException("Error saving file: " + e.getMessage());
            }
        }

        @Override
        public void successTips() {
            Toast.success(frame, MessageConstant.SYSTEM_FILE_SAVE_SUCCESS + fileToSave.getAbsolutePath() + SystemConstant.SAVE_JSON_EXTENSION);
        }
    }

}
