package develop.toolbar.command;

import develop.toolbar.CommandParseFailedException;

public interface Command {

    String keyword();

    void execute(String content) throws CommandParseFailedException;
}
