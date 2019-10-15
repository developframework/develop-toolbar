package develop.toolbar.command;

import develop.toolbar.ToolbarPropertiesFactory;
import develop.toolbar.properties.ClipBoardProperties;
import develop.toolbar.utils.CollectionAdvice;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

@RegisterCommand
public class ClipBoardCommand extends Command {

    public ClipBoardCommand(ToolbarPropertiesFactory toolbarPropertiesFactory) {
        super(toolbarPropertiesFactory);
    }

    @Override
    public String keyword() {
        return "clip";
    }

    @Override
    public void execute(String content) {
        CollectionAdvice
                .getFirstMatch(factory.getToolbarProperties().getCommands().getClipboards(), content.trim(), ClipBoardProperties::getName)
                .map(ClipBoardProperties::getContent)
                .ifPresent(text -> {
                    Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    systemClipboard.setContents(new StringSelection(text), null);
                });

    }
}
