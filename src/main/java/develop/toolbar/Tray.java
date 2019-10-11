package develop.toolbar;

import com.melloware.jintellitype.JIntellitype;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class Tray {

    private TrayIcon trayIcon;

    public Tray() {
        SystemTray systemTray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/tray.png"));
        PopupMenu popupMenu = new PopupMenu();
        MenuItem exit = new MenuItem("exit");
        exit.addActionListener(event -> destroy());
        popupMenu.add(exit);
        trayIcon = new TrayIcon(image, "develop-toolbar", popupMenu);
        try {
            systemTray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void destroy() {
        SystemTray.getSystemTray().remove(trayIcon);
        JIntellitype.getInstance().cleanUp();
        System.exit(0);
    }
}
