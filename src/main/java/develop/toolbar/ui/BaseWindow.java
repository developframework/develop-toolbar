package develop.toolbar.ui;

import com.sun.awt.AWTUtilities;
import develop.toolbar.ToolbarPropertiesFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public abstract class BaseWindow extends JFrame {

    private int xOld, yOld;
    protected ToolbarPropertiesFactory toolbarPropertiesFactory;

    public BaseWindow(ToolbarPropertiesFactory toolbarPropertiesFactory) throws HeadlessException {
        this.toolbarPropertiesFactory = toolbarPropertiesFactory;
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        AWTUtilities.setWindowOpacity(this, toolbarPropertiesFactory.getToolbarProperties().getWindow().getOpacity());

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
