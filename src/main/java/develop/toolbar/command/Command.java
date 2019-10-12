package develop.toolbar.command;

import develop.toolbar.CommandParseFailedException;
import develop.toolbar.properties.ToolbarProperties;

public abstract class Command {

    protected ToolbarProperties toolbarProperties;

    public Command(ToolbarProperties toolbarProperties) {
        this.toolbarProperties = toolbarProperties;
    }

    public abstract String keyword();

    public abstract void execute(String content) throws CommandParseFailedException;
}
