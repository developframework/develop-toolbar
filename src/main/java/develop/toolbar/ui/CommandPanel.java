package develop.toolbar.ui;

import develop.toolbar.CommandRegistry;
import develop.toolbar.SearchHistoryManager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.stream.Stream;

public class CommandPanel extends JPanel {

    private JTextField textField;

    private JComboBox<String> hintBox;

    private CommandRegistry commandRegistry;

    private SearchHistoryManager searchHistoryManager;

    private int upCount;

    private static final Border ERROR_BORDER = BorderFactory.createLineBorder(new Color(0xFF030F), 2);

    public CommandPanel(Window window, CommandRegistry commandRegistry, SearchHistoryManager searchHistoryManager) {
        this.commandRegistry = commandRegistry;
        this.searchHistoryManager = searchHistoryManager;
        this.setLayout(new BorderLayout());
        textField = new JTextField();
        textField.setFont(new Font("微软雅黑", Font.BOLD, 20));
        textField.setForeground(new Color(0x55524C));
        textField.setMargin(new Insets(0, 10, 0, 10));
        textField.setFocusTraversalKeysEnabled(false);
        this.add(textField);

        hintBox = new JComboBox<String>() {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(super.getPreferredSize().width, 0);
            }
        };
        hintBox.setFont(new Font("微软雅黑", Font.BOLD, 16));
        hintBox.setForeground(new Color(0x736F66));
        this.add(hintBox, BorderLayout.SOUTH);

        //文本框按键事件
        textField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER: {
                        final String commandStr = textField.getText();
                        if (commandRegistry.executeCommand(commandStr)) {
                            searchHistoryManager.appendHistory(commandStr);
                            textField.setText("");
                            window.setVisible(false);
                            upCount = 0;
                        } else {
                            window.shake();
                            textField.setBorder(ERROR_BORDER);
                        }
                    }
                    break;
                    case KeyEvent.VK_TAB: {
                        setHints(textField.getText());
                        if (hintBox.getItemCount() > 0) {
                            hintBox.requestFocus();
                            hintBox.showPopup();
                        }
                    }
                    break;
                    case KeyEvent.VK_DOWN: {
                        final List<String> histories = searchHistoryManager.getHistories();
                        if (!histories.isEmpty()) {
                            textField.setText(histories.get(upCount));
                            if (--upCount < 0) upCount = 0;
                        }
                    }
                    break;
                    case KeyEvent.VK_UP: {
                        final List<String> histories = searchHistoryManager.getHistories();
                        if (!histories.isEmpty()) {
                            textField.setText(histories.get(upCount));
                            if (++upCount > histories.size() - 1) upCount = histories.size() - 1;
                        }
                    }
                    break;
                }
            }
        });

        //提示框事件
        hintBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (hintBox.getSelectedIndex() == 0) {
                        textField.requestFocus();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    textField.requestFocus();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Object selectedItem = hintBox.getSelectedItem();
                    if (selectedItem != null) {
                        textField.setText(selectedItem.toString());
                        textField.requestFocus();
                    }
                }
            }
        });
        setHints(null);
    }

    private void setHints(String commandStr) {
        hintBox.removeAllItems();
        Stream<String> stream = searchHistoryManager.getHistories().stream();
        if (commandStr != null) {
            stream = stream.filter(hint -> hint.startsWith(commandStr));
        }
        stream.forEach(hintBox::addItem);
        textField.setBorder(null);
    }

    public void clearHistory() {
        searchHistoryManager.clear();
        setHints(null);
    }
}
