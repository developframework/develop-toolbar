package develop.toolbar.command;

import develop.toolbar.CommandRegistry;
import develop.toolbar.ToolbarPropertiesFactory;
import develop.toolbar.ui.BookmarkWindow;
import org.springframework.beans.factory.annotation.Autowired;

@RegisterCommand
public class BookmarkCommand extends Command {

    @Autowired
    private CommandRegistry commandRegistry;

    public BookmarkCommand(ToolbarPropertiesFactory toolbarPropertiesFactory) {
        super(toolbarPropertiesFactory);
    }

    @Override
    public String keyword() {
        return "bookmark";
    }

    @Override
    public void execute(String content) {
        new BookmarkWindow(factory, commandRegistry, content);
    }

}
