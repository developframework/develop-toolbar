package develop.toolbar.command;

import develop.toolbar.CommandParseFailedException;
import develop.toolbar.ToolbarPropertiesFactory;
import develop.toolbar.ui.Window;

@RegisterCommand
public class ClearCommand extends Command {

    private Window window;

    public ClearCommand(ToolbarPropertiesFactory toolbarPropertiesFactory, Window window) {
        super(toolbarPropertiesFactory);
        this.window = window;
    }

    @Override
    public String keyword() {
        return "clear";
    }

    @Override
    public void execute(String content) throws CommandParseFailedException {
        window.getCommandPanel().clearHistory();
    }
}
