package com.ywf.component.textAreaEditor;

import javax.swing.text.Document;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.ViewFactory;

public class JavaCodeEditor extends StyledEditorKit {

    @Override
    public Document createDefaultDocument() {
        return super.createDefaultDocument();
    }

    @Override
    public ViewFactory getViewFactory() {
        return new JavaViewFactory();
    }

}
