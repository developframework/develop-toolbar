package develop.toolbar.command;

import develop.toolbar.CommandParseFailedException;
import develop.toolbar.CommandRegistry;
import develop.toolbar.properties.BookmarkProperties;
import develop.toolbar.properties.ToolbarProperties;
import develop.toolbar.utils.CollectionAdvice;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RegisterCommand
@Component
public class OpenCommand extends Command {

    private CommandRegistry commandRegistry;

    public OpenCommand(ToolbarProperties toolbarProperties, CommandRegistry commandRegistry) {
        super(toolbarProperties);
        this.commandRegistry = commandRegistry;
    }

    @Override
    public String keyword() {
        return "open";
    }

    @Override
    public void execute(String content) throws CommandParseFailedException {
        Optional<BookmarkProperties> firstMatch = CollectionAdvice.getFirstMatch(toolbarProperties.getCommands().getBookmarks(), content, BookmarkProperties::getName);
        if (firstMatch.isPresent()) {
            BookmarkProperties bookmark = firstMatch.get();
            commandRegistry.executeCommand(bookmark.getContent());
        } else {
            File file = new File(content);
            if (file.exists()) {
                try {
                    String operate = file.isFile() ? "sublime_text.exe" : "start explorer";
                    Runtime.getRuntime().exec(String.format("cmd /c %s %s", operate, content));
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new CommandParseFailedException();
                }
            }
        }
    }
}
