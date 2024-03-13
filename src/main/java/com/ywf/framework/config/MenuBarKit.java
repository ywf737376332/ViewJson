package com.ywf.framework.config;

import com.ywf.action.MenuEventService;
import com.ywf.action.QRCodeEventService;
import com.ywf.component.MenuBarBuilder;
import com.ywf.framework.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Menu事件注册
 *
 * @Author YWF
 * @Date 2024/3/1 22:25
 */
public class MenuBarKit {

    private static final JFrame frame;

    private final static Logger logger = LoggerFactory.getLogger(MenuBarBuilder.class);

    static {
        frame = ObjectUtils.getBean(GlobalKEY.MAIN_FRAME);
    }

    public static class NewTabAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().addTabbedSplitEditorActionPerformed();
        }
    }

    public static class SavePictAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().saveJsonToImageActionPerformed(frame);
        }
    }

    public static class SaveFileAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().saveJsonToFileActionPerformed(frame);
        }
    }

    public static class FavoritesAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            logger.warn("点击了收藏按钮：{}", frame.getSize());
        }
    }

    public static class ExitAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().closeWindowsFrameActionPerformed(frame);
        }
    }

    public static class CompressAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().compressionJsonActionPerformed(frame);
        }
    }

    public static class EscapeAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().escapeJsonActionPerformed();
        }
    }

    public static class UnescapeAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().unEscapeJsonActionPerformed();
        }
    }

    public static class FormatAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().formatJsonActionPerformed(frame);
        }
    }

    public static class FindAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().showFindDialogActionPerformed();
        }
    }

    public static class CleanAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().cleanJsonActionPerformed();
        }
    }

    public static class UpdateVersionLogAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().updateLogActionPerformed();
        }
    }

    public static class PrivacyPolicyAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().privacyPolicyActionPerformed();
        }
    }

    public static class OfficialWebsiteAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().linkWebSiteActionPerformed();
        }
    }

    public static class ExpressThanksAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().expressThanksActionPerformed();
        }
    }

    public static class AboutAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().aboutActionPerformed();
        }
    }

    public static class ShowToolBarAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().showToolBarActionPerformed();
        }
    }

    public static class ShowMenuBarAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().showMenuBarActionPerformed();
        }
    }

    public static class EditSetupAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().editSwitchActionPerformed();
        }
    }

    public static class LineSetupAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().lineSetupActionPerformed();
        }
    }

    public static class ShowlineNumAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().showLineNumActionPerformed();
        }
    }

    public static class CopyCodeAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().copyJsonActionPerformed(frame);
        }
    }

    public static class CopyPictAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().copyJsonToPictActionPerformed(frame);
        }
    }

    public static class ShowQrcodeAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            QRCodeEventService.getInstance().showQrcodeActionPerformed(frame);
        }
    }

    public static class ShowToolBarTextAction extends MenuAction {
        @Override
        public void actionPerformedImpl(ActionEvent e) {
            MenuEventService.getInstance().showToolBarTextActionPerformed(frame);
        }
    }

}
