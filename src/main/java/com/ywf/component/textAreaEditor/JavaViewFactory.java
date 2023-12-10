package com.ywf.component.textAreaEditor;

import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

public class JavaViewFactory implements ViewFactory {

    @Override
    public View create(Element elem) {
        return new XmlView(elem);
    }

}
