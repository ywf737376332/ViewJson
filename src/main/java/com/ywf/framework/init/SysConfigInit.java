package com.ywf.framework.init;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.ywf.framework.constant.PropsConstant;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.enums.PictureQualityEnum;
import com.ywf.framework.enums.SystemThemesEnum;
import com.ywf.framework.enums.TextConvertEnum;
import com.ywf.framework.utils.PropertiesUtil;
import com.ywf.pojo.ApplicationConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.Date;
/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/2 22:23
 */
public class SysConfigInit extends PropsConstant{

    private static PropertiesUtil systemProperties = PropertiesUtil.instance();
    public final static Date startTime = new Date();


    public static void initSysConfig() {
        // 系统配置文件初始化
        configFileInit(getSysConfigFilePath());
        // 系统配置属性初始化
        configInitInit();
    }

    private static void configFileInit(String configFilePath) {
        File file = new File(configFilePath);
        //判断父路径是否存在，不存在，则创建
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        // 创建配置文件
        File configFile = new File(configFilePath);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("系统配置文件文件初始化失败");
            }
        }
    }

    /**
     * 获取系统配置文件
     *
     * @date 2023/12/3 20:04
     */
    public static String getSysConfigFilePath() {
        // 获取用户目录
        String userHome = SYSTEM_CONFIG_HOME;
        // 创建文件夹路径
        String folderPath = userHome + SYSTEM_CONFIG_FOLDER_PATH;
        // 创建配置文件路径
        return folderPath + SYSTEM_CONFIG_FILE_PATH;
    }

    /**
     * 首次启动时加载配置文件并设置组件的属性值
     */
    private static void configInitInit() {
        // 编辑框是否可编辑
        cashValue(TEXTAREA_EDIT_STATE_KEY, "true");
        // 是否换行
        cashValue(TEXTAREA_BREAK_LINE_KEY, "false");
        // 是否转换中文
        cashValue(TEXTAREA_CHINESE_CONVERT_STATE_KEY, StrUtil.toString(TextConvertEnum.CONVERT_CLOSED.getConverType()));
        // 图片质量
        cashValue(SHARE_PICTURE_QUALITY_STATE_KEY, StrUtil.toString(PictureQualityEnum.MIDDLE_PICTURE_QUALITY.getPictureQualityState()));
        // 是否显示行号
        cashValue(TEXTAREA_SHOW_LINE_NUM_KEY, "false");
        // 显示工具栏
        cashValue(SHOW_TOOL_BAR_KEY, "true");
        cashValue(SHOW_MENU_BAR_KEY, "true");
        // 屏幕尺寸大小初始化
        cashValue(SCREEN_SIZE_WIDTH_KEY, StrUtil.toString(SystemConstant.WINDOWS_MIN_WIDTH));
        cashValue(SCREEN_SIZE_HEIGHT_KEY, StrUtil.toString(SystemConstant.WINDOWS_MIN_HEIGHT));
        // 主题初始化
        cashValue(SystemConstant.SYSTEM_THEMES_KEY, SystemThemesEnum.FlatLightLafThemesStyle.getThemesKey());



    }

    private static boolean isExists(String key) {
        return StrUtil.isEmpty(systemProperties.getValue(key));
    }

    private static void cashValue(String key, String value) {
        if (isExists(key)) {
            systemProperties.setValue(key, value);
        }
    }

    private static ApplicationConfig applicationConfig = new ApplicationConfig();

    private static void initEntity(Field[] fields,Object instance,String key, String value){
        try {
            for (Field field : fields) {
                System.out.println("Field: %s"+field.getType().getName());
                if (field.getName().equals("serialVersionUID")){
                    continue;
                }
                if (field.getName().equals(key)){
                    field.setAccessible(true);
                    switch (field.getType().getName()){
                        case "java.lang.Boolean":
                            field.set(instance, BooleanUtil.toBoolean(value));
                            break;
                        case "java.lang.String":
                            field.set(instance, StrUtil.toString(value));
                            break;
                        case "java.lang.Integer":
                            field.set(instance, NumberUtil.parseInt(value));
                            break;
                        default:

                    }
                }
            }
        }catch (Exception e){
            System.out.println("出错了"+e);
        }
    }

    public static void main(String[] args) throws IOException {
        try {
            Class<?> clazz  = Class.forName("com.ywf.pojo.ApplicationConfig");
            Object instance = clazz.getConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();
            initEntity(fields,instance,SHOW_TOOL_BAR_KEY, "true");
            initEntity(fields,instance,SHOW_MENU_BAR_KEY, "true");
            initEntity(fields,instance,TEXTAREA_SHOW_LINE_NUM_KEY, "true");
            initEntity(fields,instance,TEXTAREA_CHINESE_CONVERT_STATE_KEY, "1");

            System.out.println("instance:"+instance);

        }catch (Exception e) {
            throw new RuntimeException("系统配置文件初始化失败"+e);
        }


        /*Properties properties = new Properties();
        properties.load(new FileInputStream(getSysConfigFilePath()));
        ApplicationConfig config = new ApplicationConfig();
        config.setChineseConverState(Integer.parseInt(properties.getProperty("chineseConverState")));
        ApplicationConfig.ScreenSize screenSize = new ApplicationConfig.ScreenSize();
        screenSize.setWidth(Integer.parseInt(properties.getProperty("screenSizeKey.width")));
        screenSize.setWidth(Integer.parseInt(properties.getProperty("screenSizeKey.height")));
        config.setScreenSize(screenSize);
        System.out.println("config" + config.toString());*/
    }
}
