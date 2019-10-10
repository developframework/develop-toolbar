package develop.toolbar;

import com.melloware.jintellitype.JIntellitype;
import com.sun.awt.AWTUtilities;
import develop.toolbar.utils.DimensionUtils;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

@Component
public class Window extends JFrame {

    private int xOld;
    private int yOld;

    private CommandComboBox comboBox;

    public Window(CommandComboBox comboBox) {
        this.comboBox = comboBox;
        this.setSize(600, 60);
        this.setLocation(DimensionUtils.screenWidth / 2 - this.getWidth() / 2, DimensionUtils.screenHeight / 2 - this.getHeight() / 2);
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        this.setLayout(null);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xOld = e.getX();
                yOld = e.getY();
            }

        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Window.this.setLocation(
                        e.getXOnScreen() - xOld,
                        e.getYOnScreen() - yOld
                );
            }
        });

        comboBox.setBounds(0, 10, 600, 40);
        this.add(comboBox);

        AWTUtilities.setWindowOpacity(this, 0.8f);

        final JIntellitype instance = JIntellitype.getInstance();
        JIntellitype.setLibraryLocation(System.getProperty("user.dir"));
        instance.registerHotKey(100, JIntellitype.MOD_ALT, 'Q');
        instance.addHotKeyListener(identifier -> Window.this.setVisible(!Window.this.isVisible()));
    }

}
