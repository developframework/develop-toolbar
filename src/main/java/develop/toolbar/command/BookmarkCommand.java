package develop.toolbar.command;

import develop.toolbar.CommandRegistry;
import develop.toolbar.properties.ToolbarProperties;
import develop.toolbar.ui.BookmarkWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RegisterCommand
@Component
public class BookmarkCommand extends Command {

    @Autowired
    private CommandRegistry commandRegistry;

    public BookmarkCommand(ToolbarProperties toolbarProperties) {
        super(toolbarProperties);
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
