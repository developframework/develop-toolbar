package develop.toolbar.ui;

import com.melloware.jintellitype.JIntellitype;
import develop.toolbar.CommandRegistry;
import develop.toolbar.SearchHistoryManager;
import develop.toolbar.properties.ToolbarProperties;
import develop.toolbar.properties.WindowProperties;
import develop.toolbar.utils.DimensionUtils;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class Window extends BaseWindow {

    @Getter
    private CommandPanel commandPanel;

    public Window(ToolbarProperties toolbarProperties, CommandRegistry commandRegistry, SearchHistoryManager searchHistoryManager) {
        super(toolbarProperties);
        this.commandPanel = new CommandPanel(this, commandRegistry, searchHistoryManager);
        final WindowProperties windowProperties = toolbarProperties.getWindow();
        this.setSize(windowProperties.getWidth(), windowProperties.getHeight());
        this.setLocation(DimensionUtils.screenWidth / 2 - this.getWidth() / 2, DimensionUtils.screenHeight / 2 - this.getHeight() / 2);
        this.setLayout(null);
        commandPanel.setBounds(0, 10, windowProperties.getWidth(), windowProperties.getHeight() - 20);
        this.add(commandPanel);

        final JIntellitype instance = JIntellitype.getInstance();
        JIntellitype.setLibraryLocation(System.getProperty("user.dir"));
        instance.registerHotKey(100, JIntellitype.MOD_ALT, 'Q');
        instance.addHotKeyListener(identifier -> Window.this.setVisible(!Window.this.isVisible()));
    }

}
