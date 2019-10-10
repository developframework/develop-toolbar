package develop.toolbar;

import develop.toolkit.base.utils.StringAdvice;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class CommandComboBox extends JTextField {

    private JComboBox<String> hintBox;

    private CommandRegistry commandRegistry = new CommandRegistry();

    private SearchHistoryManager searchHistoryManager = new SearchHistoryManager();

    public CommandComboBox(JFrame frame) {
        this.setFont(new Font("Courier New", Font.BOLD, 20));
        hintBox = new JComboBox<>() {
            public Dimension getPreferredSize() {
                return new Dimension(super.getPreferredSize().width, 0);
            }
        };
        this.setLayout(new BorderLayout());
        this.add(hintBox, BorderLayout.SOUTH);

        this.setMargin(new Insets(0, 10, 0, 10));

        //文本框按键事件
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String commandStr = CommandComboBox.this.getText();
                    commandRegistry.executeCommand(commandStr);
                    searchHistoryManager.appendHistory(commandStr);
                    CommandComboBox.this.setText("");
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    hintBox.requestFocus();
                    hintBox.showPopup();
                }
            }
        });

        //文本框事件
        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setHints(CommandComboBox.this.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setHints(CommandComboBox.this.getText());
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
                    CommandComboBox.this.setText(Objects.requireNonNull(hintBox.getSelectedItem()).toString());
                    CommandComboBox.this.requestFocus();
                }
            }
        });

        setHints(null);
        frame.add(this);
    }

    private void setHints(String commandStr) {
        hintBox.removeAllItems();
        searchHistoryManager.getHistories()
                .stream()
                .filter(hint -> StringAdvice.isNotEmpty(commandStr) && hint.startsWith(commandStr))
                .forEach(hintBox::addItem);
        if (hintBox.getItemCount() > 0) {
            hintBox.showPopup();
        }
    }
}
