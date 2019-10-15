package develop.toolbar.command;

import develop.toolbar.CommandParseFailedException;
import develop.toolbar.CommandRegistry;
import develop.toolbar.ToolbarPropertiesFactory;
import develop.toolbar.properties.BookmarkProperties;
import develop.toolbar.utils.CollectionAdvice;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RegisterCommand
public class OpenCommand extends Command {

    private CommandRegistry commandRegistry;

    public OpenCommand(ToolbarPropertiesFactory toolbarPropertiesFactory, CommandRegistry commandRegistry) {
        super(toolbarPropertiesFactory);
        this.commandRegistry = commandRegistry;
    }

    @Override
    public String keyword() {
        return "open";
    }

    @Override
    public void execute(String content) throws CommandParseFailedException {
        Optional<BookmarkProperties> firstMatch = CollectionAdvice.getFirstMatch(factory.getToolbarProperties().getCommands().getBookmarks(), content, BookmarkProperties::getName);
        if (firstMatch.isPresent()) {
            BookmarkProperties bookmark = firstMatch.get();
            commandRegistry.executeCommand(bookmark.getContent());
        } else {
            File file = new File(content);
            if (file.exists()) {
                try {
                    if (file.isDirectory()) {
                        openExplorer(file.getAbsolutePath());
                    } else {
                        if (file.getName().endsWith(".exe")) {
                            openExe(file.getAbsolutePath());
                        } else {
                            openTextFile(file.getAbsolutePath());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new CommandParseFailedException();
                }
            }
        }
    }

    private void openExe(String content) throws IOException {
        Runtime.getRuntime().exec(String.format("\"%s\"", content));
    }

    private void openExplorer(String content) throws IOException {
        Runtime.getRuntime().exec(String.format("cmd /c start explorer \"%s\"", content));
    }

    private void openTextFile(String content) throws IOException {
        Runtime.getRuntime().exec(String.format("cmd /c start \"%s\" \"%s\"", factory.getToolbarProperties().getSoftware().getTextEditor(), content));
    }
}

