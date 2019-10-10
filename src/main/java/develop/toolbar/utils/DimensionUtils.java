package develop.toolbar.utils;

import java.awt.*;

public final class DimensionUtils {

    public static int screenWidth;
    public static int screenHeight;

    static {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;
    }

    /**
     * 宽百分值
     * @param value
     * @return
     */
    public static int percentWidth(int value) {
        return value * screenWidth / 100;
    }

    /**
     * 高百分值
     * @param value
     * @return
     */
    public static int percentHeight(int value) {
        return value * screenHeight / 100;
    }

    /**
     * 百分尺寸
     * @param width
     * @param height
     * @return
     */
    public static Dimension percentDimension(int width, int height) {
        return new Dimension(percentWidth(width), percentHeight(height));
    }
}
