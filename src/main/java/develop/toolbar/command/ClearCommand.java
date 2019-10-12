package develop.toolbar.command;

import develop.toolbar.CommandParseFailedException;
import develop.toolbar.properties.ToolbarProperties;
import develop.toolbar.ui.Window;
import org.springframework.stereotype.Component;

@RegisterCommand
@Component
public class ClearCommand extends Command {

    private Window window;

    public ClearCommand(ToolbarProperties toolbarProperties, Window window) {
        super(toolbarProperties);
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
