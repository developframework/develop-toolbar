package develop.toolbar.ui;

import develop.toolbar.ClipboardManager;
import develop.toolbar.ToolbarPropertiesFactory;
import develop.toolbar.structs.Clipboard;
import develop.toolbar.utils.DimensionUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ClipboardListWindow extends BaseWindow {

    private ClipboardManager clipboardManager;

    private List<Clipboard> clipboards;

    private JTable table;

    public ClipboardListWindow(ToolbarPropertiesFactory toolbarPropertiesFactory, ClipboardManager clipboardManager) {
        super(toolbarPropertiesFactory);
        this.clipboardManager = clipboardManager;
        this.clipboards = clipboardManager.getAll();
        this.setSize(1000, 600);
        this.setLocation((DimensionUtils.screenWidth - this.getWidth()) / 2, (DimensionUtils.screenHeight - this.getHeight()) / 2);
        this.setLayout(new BorderLayout());

        Object[][] data = new Object[clipboards.size()][3];
        for (int i = 0; i < clipboards.size(); i++) {
            Clipboard clipboard = clipboards.get(i);
            data[i] = new String[]{clipboard.getName(), clipboard.getContent(), clipboard.getDeadTime() == null ? "forever" : clipboard.getDeadTime().toString()};
        }
        DefaultTableModel model = new DefaultTableModel(data, new String[]{"name", "content", "dead time"}) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 14));

        table.setRowHeight(40);
        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    clickRow();
                }
            }
        });
        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        clickRow();
                    case KeyEvent.VK_ESCAPE:
                        ClipboardListWindow.this.dispose();
                        break;
                }
            }
        });
        this.add(table.getTableHeader(), BorderLayout.NORTH);
        this.add(table);
        this.setVisible(true);
    }

    private void clickRow() {
        String name = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
        String text = clipboardManager.getValue(name);
        clipboardManager.setClipboardValue(text);
    }
}
