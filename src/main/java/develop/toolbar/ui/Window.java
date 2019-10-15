package develop.toolbar.ui;

import com.melloware.jintellitype.JIntellitype;
import develop.toolbar.CommandRegistry;
import develop.toolbar.SearchHistoryManager;
import develop.toolbar.ToolbarPropertiesFactory;
import develop.toolbar.properties.WindowProperties;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class Window extends BaseWindow {

    @Getter
    private CommandPanel commandPanel;

    public Window(ToolbarPropertiesFactory toolbarPropertiesFactory, CommandRegistry commandRegistry, SearchHistoryManager searchHistoryManager) {
        super(toolbarPropertiesFactory);
        this.commandPanel = new CommandPanel(this, commandRegistry, searchHistoryManager);
        final WindowProperties windowProperties = toolbarPropertiesFactory.getToolbarProperties().getWindow();
        this.setSize(windowProperties.getWidth(), windowProperties.getHeight());
        this.setLayout(null);
        commandPanel.setBounds(0, 10, windowProperties.getWidth(), windowProperties.getHeight() - 20);
        this.add(commandPanel);

        final JIntellitype instance = JIntellitype.getInstance();
        JIntellitype.setLibraryLocation(System.getProperty("user.dir"));
        instance.registerHotKey(100, JIntellitype.MOD_ALT, 'Q');
        instance.addHotKeyListener(identifier -> Window.this.setVisible(!Window.this.isVisible()));
    }

}
