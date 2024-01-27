package com.ywf.framework.init;

import com.typesafe.config.*;
import com.ywf.pojo.ApplicationConfig;

import java.io.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/1/27 15:43
 */
public class SysConfigLoad implements Serializable {

    private static final long serialVersionUID = 1L;
    final static String configFile = System.getProperty("user.home") + File.separator + "jsonView" +File.separator + "jsonView.conf";

    public static void main(String[] args) throws IOException {
        //ApplicationConfig applicationConfig = loadSysConfig();
        //System.out.println(applicationConfig);
        writeSysConfig(configFile);

    }

    public static ApplicationConfig loadSysConfig() {
        Config config = ConfigFactory.parseFile(new File(configFile));
        String name = config.getString("name");
        String age = config.getString("age");
        Config address = config.getConfig("address");
        config.getBoolean("");
        String street = address.getString("street");
        String city = address.getString("city");
        String state = address.getString("state");
        ApplicationConfig user = new ApplicationConfig();

        return user;
    }

    public static void writeSysConfig(String outpath) {
        Config builder = ConfigFactory.empty();
        // 子项配置集合
        Config childConfig = ConfigFactory.empty()
                .withValue("width", ConfigValueFactory.fromAnyRef(1283))
                .withValue("height", ConfigValueFactory.fromAnyRef(1033)).resolve();

        Config configBuilder = ConfigFactory.empty()
                .withValue("textAreaEditState", ConfigValueFactory.fromAnyRef(true))
                .withValue("textAreaBreakLineState", ConfigValueFactory.fromAnyRef(false))
                .withValue("textAreaShowlineNumState", ConfigValueFactory.fromAnyRef(true))
                .withValue("showToolBarState", ConfigValueFactory.fromAnyRef(true))
                .withValue("showMenuBarState", ConfigValueFactory.fromAnyRef(true))
                .withValue("textAreaReplaceBlankSpaceState", ConfigValueFactory.fromAnyRef(false))
                .withValue("lastSystemThemes", ConfigValueFactory.fromAnyRef("FlatLaf Light"))
                .withValue("chineseConverState", ConfigValueFactory.fromAnyRef(2))
                .withValue("pictureQualityState", ConfigValueFactory.fromAnyRef(1))
                .withValue("screenSize", ConfigValueFactory.fromAnyRef("childConfig")).resolve();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outpath))) {
            String hoconContent = configBuilder.root().render(ConfigRenderOptions.concise());
            writer.write(hoconContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
