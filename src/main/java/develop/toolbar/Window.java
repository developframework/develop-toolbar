package develop.toolbar;

import develop.toolbar.utils.DimensionUtils;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Window extends JFrame {

    private int xOld;
    private int yOld;

    public Window() {
        this.setSize(600, 60);
        this.setLocation(DimensionUtils.screenWidth / 2 - this.getWidth() / 2, DimensionUtils.screenHeight / 2 - this.getHeight() / 2);
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        this.setLayout(null);
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
                Window.this.setLocation(
                        e.getXOnScreen() - xOld,
                        e.getYOnScreen() - yOld
                );
            }
        });

        CommandComboBox comboBox = new CommandComboBox(this);
        comboBox.setBounds(0, 10, 600, 40);
        this.setVisible(true);
    }

}
