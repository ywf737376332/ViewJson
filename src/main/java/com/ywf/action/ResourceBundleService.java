package com.ywf.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 国际化资源加载
 *
 * @Author YWF
 * @Date 2023/12/7 22:37
 */
public class ResourceBundleService {

    private final static Logger logger = LoggerFactory.getLogger(ResourceBundleService.class);
    private static final String MSG = "Message";
    private ResourceBundle resourceBundle;

    volatile private static ResourceBundleService instance = null;

    private ResourceBundleService() {
        resourceBundle = ResourceBundle.getBundle(MSG, Locale.getDefault());
    }

    public static ResourceBundleService getInstance() {
        if (instance == null) {
            synchronized (ResourceBundleService.class) {
                if (instance == null) {
                    instance = new ResourceBundleService();
                }
            }
        }
        return instance;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}
