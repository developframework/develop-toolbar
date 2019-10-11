package develop.toolbar;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.stream.Stream;

public class CommandComboBox extends JTextField {

    private JComboBox<String> hintBox;

    private CommandRegistry commandRegistry;

    private SearchHistoryManager searchHistoryManager;

    @SuppressWarnings("unchecked")
    public CommandComboBox(Window window, CommandRegistry commandRegistry, SearchHistoryManager searchHistoryManager) {
        this.commandRegistry = commandRegistry;
        this.searchHistoryManager = searchHistoryManager;
        this.setFont(new Font("微软雅黑", Font.BOLD, 20));
        this.setForeground(new Color(0x55524C));
//        this.setBorder(BorderFactory.createEtchedBorder());
        hintBox = new JComboBox<String>() {
            public Dimension getPreferredSize() {
                return new Dimension(super.getPreferredSize().width, 0);
            }
        };
        hintBox.setRenderer(new BasicComboBoxRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setSize(600, 100);
                return c;
            }
        });
        hintBox.setFont(new Font("微软雅黑", Font.BOLD, 18));
        hintBox.setForeground(new Color(0x736F66));
        this.setMargin(new Insets(0, 10, 0, 10));
        this.setLayout(new BorderLayout());
        this.add(hintBox, BorderLayout.SOUTH);
        this.setFocusTraversalKeysEnabled(false);
        this.setMargin(new Insets(0, 10, 0, 10));

        //文本框按键事件
        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER: {
                        String commandStr = CommandComboBox.this.getText();
                        if (commandRegistry.executeCommand(commandStr)) {
                            searchHistoryManager.appendHistory(commandStr);
                            CommandComboBox.this.setText("");
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
//                    case KeyEvent.VK_UP: {
//                        if(hintBox.getItemCount() > 0) {
//                            System.out.println("aaa");
//                        }
//                    }
                }
            }
        });

        //文本框事件
        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setHints(CommandComboBox.this.getText());
                if (hintBox.getItemCount() > 0) {
                    hintBox.showPopup();
                } else if (hintBox.getItemCount() == 0) {
                    hintBox.hidePopup();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setHints(CommandComboBox.this.getText());
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
                        CommandComboBox.this.requestFocus();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    CommandComboBox.this.requestFocus();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Object selectedItem = hintBox.getSelectedItem();
                    if (selectedItem != null) {
                        CommandComboBox.this.setText(selectedItem.toString());
                        CommandComboBox.this.requestFocus();
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
}
