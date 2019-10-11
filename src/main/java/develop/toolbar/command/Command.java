package develop.toolbar.command;

import develop.toolbar.CommandParseFailedException;
import develop.toolbar.properties.CommandProperties;

public abstract class Command {

    protected CommandProperties commandProperties;

    public Command(CommandProperties commandProperties) {
        this.commandProperties = commandProperties;
    }

    public abstract String keyword();

    public abstract void execute(String content) throws CommandParseFailedException;
}
