package develop.toolbar.ui;

import develop.toolbar.ToolbarPropertiesFactory;
import develop.toolbar.utils.DimensionUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClipboardSettingWindow extends BaseWindow {

    private JTextField textField;

    private JLabel label;

    public ClipboardSettingWindow(ToolbarPropertiesFactory toolbarPropertiesFactory, String name, String content) throws HeadlessException {
        super(toolbarPropertiesFactory);

        this.setSize(400, 200);
        this.setLocation((DimensionUtils.screenWidth - this.getWidth()) / 2, (DimensionUtils.screenHeight - this.getHeight()) / 2);

        textField = new JTextField();
        textField.setBounds(300, 40, 50, 10);
        textField.setName(name);
        textField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        Box box1 = Box.createHorizontalBox();
        box1.add(textField);

        label = new JLabel(content);
        label.setSize(300, 150);
        Box box2 = Box.createHorizontalBox();
        box1.add(label);
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(box1);
        verticalBox.add(Box.createHorizontalStrut(10));
        verticalBox.add(box2);
        this.add(verticalBox);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        break;
                    case KeyEvent.VK_ESCAPE:
                        ClipboardSettingWindow.this.dispose();
                        break;
                }
            }
        });
        this.setVisible(true);
    }
}
