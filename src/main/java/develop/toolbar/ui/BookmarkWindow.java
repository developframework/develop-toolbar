package develop.toolbar.ui;

import develop.toolbar.CommandRegistry;
import develop.toolbar.properties.BookmarkProperties;
import develop.toolbar.properties.ToolbarProperties;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookmarkWindow extends BaseWindow {

    private List<BookmarkProperties> bookmarks;

    private JTable table;

    private CommandRegistry commandRegistry;

    public BookmarkWindow(ToolbarProperties toolbarProperties, CommandRegistry commandRegistry, String content) {
        super(toolbarProperties);
        this.commandRegistry = commandRegistry;
        this.bookmarks = toolbarProperties.getCommands()
                .getBookmarks()
                .stream()
                .filter(bookmarks -> content == null || bookmarks.getContent().startsWith(content))
                .sorted(Comparator.comparing(BookmarkProperties::getContent))
                .collect(Collectors.toList());
        this.setSize(1000, 600);
        this.setLayout(new BorderLayout());

        Object[][] data = new Object[bookmarks.size()][2];
        for (int i = 0; i < bookmarks.size(); i++) {
            BookmarkProperties bookmark = bookmarks.get(i);
            data[i] = new String[]{bookmark.getName(), bookmark.getContent()};
        }
        DefaultTableModel model = new DefaultTableModel(data, new String[]{"bookmark", "content"}) {

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
                        break;
                    case KeyEvent.VK_ESCAPE:
                        BookmarkWindow.this.dispose();
                        break;
                }
            }
        });
        this.add(table.getTableHeader(), BorderLayout.NORTH);
        this.add(table);
        this.setVisible(true);
    }

    private void clickRow() {
        String command = (String) table.getModel().getValueAt(table.getSelectedRow(), 1);
        commandRegistry.executeCommand(command);
        BookmarkWindow.this.dispose();
    }
}
