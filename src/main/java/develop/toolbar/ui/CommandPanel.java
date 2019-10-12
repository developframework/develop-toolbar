package develop.toolbar.ui;

import develop.toolbar.CommandRegistry;
import develop.toolbar.SearchHistoryManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.stream.Stream;

public class CommandPanel extends JPanel {

    private JTextField textField;

    private JComboBox<String> hintBox;

    private CommandRegistry commandRegistry;

    private SearchHistoryManager searchHistoryManager;

    public CommandPanel(Window window, CommandRegistry commandRegistry, SearchHistoryManager searchHistoryManager) {
        this.commandRegistry = commandRegistry;
        this.searchHistoryManager = searchHistoryManager;
        this.setLayout(new BorderLayout());
        textField = new JTextField();
        textField.setFont(new Font("微软雅黑", Font.BOLD, 20));
        textField.setForeground(new Color(0x55524C));
        textField.setBorder(BorderFactory.createEtchedBorder());
        textField.setMargin(new Insets(0, 10, 0, 10));
        textField.setFocusTraversalKeysEnabled(false);
        this.add(textField);

        hintBox = new JComboBox<String>() {
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
                        String commandStr = textField.getText();
                        if (commandRegistry.executeCommand(commandStr)) {
                            searchHistoryManager.appendHistory(commandStr);
                            textField.setText("");
                            window.setVisible(false);
                        }
                    }
                    break;
                    case KeyEvent.VK_TAB:
                    case KeyEvent.VK_DOWN: {
                        if (hintBox.getItemCount() > 0) {
                            hintBox.requestFocus();
                            hintBox.showPopup();
                        }
                    }
                    break;
                }
            }
        });

        //文本框事件
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setHints(textField.getText());
                if (hintBox.getItemCount() > 0) {
                    hintBox.showPopup();
                } else if (hintBox.getItemCount() == 0) {
                    hintBox.hidePopup();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setHints(textField.getText());
                if (hintBox.getItemCount() > 0) {
                    hintBox.showPopup();
                } else if (hintBox.getItemCount() == 0) {
                    hintBox.hidePopup();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

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
    }

    public void clearHistory() {
        searchHistoryManager.clear();
        setHints(null);
    }
}
