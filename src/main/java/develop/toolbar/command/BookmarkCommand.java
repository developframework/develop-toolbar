package develop.toolbar.command;

import develop.toolbar.CommandRegistry;
import develop.toolbar.properties.ToolbarProperties;
import develop.toolbar.ui.BookmarkWindow;
import org.springframework.stereotype.Component;

@RegisterCommand
@Component
public class BookmarkCommand extends Command {

    private CommandRegistry commandRegistry;

    public BookmarkCommand(ToolbarProperties toolbarProperties, CommandRegistry commandRegistry) {
        super(toolbarProperties);
        this.commandRegistry = commandRegistry;
    }

    @Override
    public String keyword() {
        return "bookmark";
    }

    @Override
    public void execute(String content) {
        new BookmarkWindow(toolbarProperties, commandRegistry, content);
    }
}
