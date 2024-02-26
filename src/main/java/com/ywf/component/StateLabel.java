package com.ywf.component;

import com.formdev.flatlaf.extras.components.FlatLabel;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/2/26 15:34
 */
public class StateLabel extends FlatLabel {

    private String value;

    public StateLabel() {
    }

    public StateLabel(String value) {
        super();
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
