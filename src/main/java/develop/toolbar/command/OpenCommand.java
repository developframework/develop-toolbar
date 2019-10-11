package develop.toolbar.command;

import develop.toolbar.CommandParseFailedException;

import java.io.File;
import java.io.IOException;

public class OpenCommand implements Command {

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
