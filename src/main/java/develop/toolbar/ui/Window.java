package develop.toolbar.ui;

import com.melloware.jintellitype.JIntellitype;
import develop.toolbar.CommandRegistry;
import develop.toolbar.SearchHistoryManager;
import develop.toolbar.ToolbarPropertiesFactory;
import develop.toolbar.properties.WindowProperties;
import develop.toolbar.utils.DimensionUtils;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class Window extends BaseWindow implements Runnable {

    @Getter
    private CommandPanel commandPanel;

    public Window(ToolbarPropertiesFactory toolbarPropertiesFactory, CommandRegistry commandRegistry, SearchHistoryManager searchHistoryManager) {
        super(toolbarPropertiesFactory);
        this.commandPanel = new CommandPanel(this, commandRegistry, searchHistoryManager);
        final WindowProperties windowProperties = toolbarPropertiesFactory.getToolbarProperties().getWindow();
        this.setSize(windowProperties.getWidth(), windowProperties.getHeight());
        this.setLocation((DimensionUtils.screenWidth - this.getWidth()) / 2, (DimensionUtils.screenHeight - this.getHeight()) / 2);
        this.setLayout(null);
        commandPanel.setBounds(0, 10, windowProperties.getWidth(), windowProperties.getHeight() - 20);
        this.add(commandPanel);

        final JIntellitype instance = JIntellitype.getInstance();
        JIntellitype.setLibraryLocation(System.getProperty("user.dir"));
        instance.registerHotKey(100, JIntellitype.MOD_ALT, 'Q');
        instance.addHotKeyListener(identifier -> Window.this.setVisible(!Window.this.isVisible()));
    }

    public void shake() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        final Point point = this.getLocation();
        int width = (int) point.getX();
        int height = (int) point.getY();
        for (int i = 0; i < 5; i++) {
            try {
                sleepShake(width - 10, height);
                sleepShake(width, height);
                sleepShake(width + 10, height);
                sleepShake(width, height);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sleepShake(int width, int height) throws InterruptedException {
        Thread.sleep(20);
        this.setLocation(width, height);
    }
}
