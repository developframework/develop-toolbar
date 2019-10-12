package develop.toolbar.ui;

import develop.toolbar.properties.ToolbarProperties;
import develop.toolbar.utils.DimensionUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public abstract class BaseWindow extends JFrame {

    private int xOld, yOld;
    protected ToolbarProperties toolbarProperties;

    public BaseWindow(ToolbarProperties toolbarProperties) throws HeadlessException {
        this.toolbarProperties = toolbarProperties;
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        this.setLocation((DimensionUtils.screenWidth - this.getWidth()) / 2, (DimensionUtils.screenHeight - this.getHeight()) / 2);
//        AWTUtilities.setWindowOpacity(this, toolbarProperties.getWindow().getOpacity());

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xOld = e.getX();
                yOld = e.getY();
            }

        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                BaseWindow.this.setLocation(
                        e.getXOnScreen() - xOld,
                        e.getYOnScreen() - yOld
                );
            }
        });
    }
}
