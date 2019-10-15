package develop.toolbar.command;

import develop.toolbar.ToolbarPropertiesFactory;
import develop.toolbar.properties.Clipboard;
import develop.toolbar.ui.ClipboardSettingWindow;
import develop.toolbar.utils.CollectionAdvice;
import develop.toolbar.utils.StringAdvice;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

@RegisterCommand
public class ClipBoardCommand extends Command {

    public ClipBoardCommand(ToolbarPropertiesFactory toolbarPropertiesFactory) {
        super(toolbarPropertiesFactory);
    }

    @Override
    public String keyword() {
        return "cb";
    }

    @Override
    public void execute(String content) {
        if (content.startsWith("add") || content.startsWith("rm")) {
            String clipboardValue = getClipboardValue();
            if (clipboardValue != null) {
                String[] parts = StringAdvice.cutOff(content, content.indexOf(" "));
                new ClipboardSettingWindow(factory, parts[1], getClipboardValue());
            }
        } else {
            CollectionAdvice
                    .getFirstMatch(factory.getToolbarProperties().getCommands().getClipboards(), content.trim(), Clipboard::getName)
                    .map(Clipboard::getContent)
                    .ifPresent(this::setClipboardValue);
        }
    }

    private String getClipboardValue() {
        java.awt.datatransfer.Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = systemClipboard.getContents("unknown");
        if (transferable != null) {
            try {
                if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    return (String) transferable.getTransferData(DataFlavor.stringFlavor);
                }
            } catch (UnsupportedFlavorException | IOException e) {
            }
        }
        return null;
    }

    private void setClipboardValue(String text) {
        java.awt.datatransfer.Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        systemClipboard.setContents(new StringSelection(text), null);
    }
}
