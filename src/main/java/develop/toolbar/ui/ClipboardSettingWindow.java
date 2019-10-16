package develop.toolbar.ui;

import develop.toolbar.ClipboardManager;
import develop.toolbar.ToolbarPropertiesFactory;
import develop.toolbar.structs.DeadTime;
import develop.toolbar.utils.DimensionUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

public class ClipboardSettingWindow extends BaseWindow {

    private JTextField textField;

    private JTextArea textArea;

    private JComboBox<DeadTime> comboBox;

    private ClipboardManager clipboardManager;

    public ClipboardSettingWindow(ToolbarPropertiesFactory toolbarPropertiesFactory, ClipboardManager clipboardManager, String name, String content) throws HeadlessException {
        super(toolbarPropertiesFactory);

        this.setSize(500, 500);
        this.setLocation((DimensionUtils.screenWidth - this.getWidth()) / 2, (DimensionUtils.screenHeight - this.getHeight()) / 2);
        this.setLayout(null);
        textField = new JTextField();
        textField.setBounds(10, 10, 480, 30);
        textField.setText(name);
        textField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        textField.requestFocus();
        this.add(textField);

        Vector<DeadTime> data = new Vector<>();
        data.add(new DeadTime("永不过期", 0));
        data.add(new DeadTime("10 minutes", 10));
        data.add(new DeadTime("30 minutes", 30));
        data.add(new DeadTime("1 hours", 60));
        data.add(new DeadTime("1 days", 24 * 60));
        comboBox = new JComboBox<>(data);
        comboBox.setBounds(10, 50, 480, 30);
        comboBox.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        this.add(comboBox);

        textArea = new JTextArea(content == null ? "" : content);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(10, 90, 480, 400);
        this.add(scroll);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        clipboardManager.add(textField.getText(), textArea.getText(), 0);
                    case KeyEvent.VK_ESCAPE:
                        ClipboardSettingWindow.this.dispose();
                        break;
                }
            }
        });
        this.setVisible(true);
    }
}
