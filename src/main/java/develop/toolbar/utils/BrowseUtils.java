package develop.toolbar.utils;

import java.awt.*;
import java.net.URI;

public final class BrowseUtils {

    public static void browse(String uri) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(URI.create(uri));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
