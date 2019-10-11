package develop.toolbar.command;

import develop.toolbar.CommandParseFailedException;
import develop.toolbar.properties.CommandProperties;

import java.io.File;
import java.io.IOException;

public class OpenCommand extends Command {

    public OpenCommand(CommandProperties commandProperties) {
        super(commandProperties);
    }

    @Override
    public String keyword() {
        return "open";
    }

    @Override
    public void execute(String content) throws CommandParseFailedException {
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
