package develop.toolbar.command;

import develop.toolbar.ClipboardManager;
import develop.toolbar.ToolbarPropertiesFactory;
import develop.toolbar.properties.ClipboardProperties;
import develop.toolbar.ui.ClipboardListWindow;
import develop.toolbar.ui.ClipboardSettingWindow;
import develop.toolbar.utils.CollectionAdvice;
import develop.toolbar.utils.StringAdvice;

@RegisterCommand
public class ClipBoardCommand extends Command {

    private ClipboardManager clipboardManager;

    public ClipBoardCommand(ToolbarPropertiesFactory toolbarPropertiesFactory, ClipboardManager clipboardManager) {
        super(toolbarPropertiesFactory);
        this.clipboardManager = clipboardManager;
    }

    @Override
    public String keyword() {
        return "cb";
    }

    @Override
    public void execute(String content) {
        if (content == null || content.equals("list")) {
            new ClipboardListWindow(factory, clipboardManager);
        } else if (content.startsWith("add")) {
            String[] parts = StringAdvice.cutOff(content, content.indexOf(" "));
            new ClipboardSettingWindow(factory, clipboardManager, parts[1]);
        } else if (content.startsWith("rm")) {
            clipboardManager.remove(content);
        } else {
            String text = CollectionAdvice
                    .getFirstMatch(factory.getToolbarProperties().getCommands().getClipboards(), content.trim(), ClipboardProperties::getName)
                    .map(ClipboardProperties::getContent)
                    .orElseGet(() -> clipboardManager.getValue(content));
            clipboardManager.setClipboardValue(text);
        }
    }

}
