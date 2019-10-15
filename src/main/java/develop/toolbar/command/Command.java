package develop.toolbar.command;

import develop.toolbar.CommandParseFailedException;
import develop.toolbar.ToolbarPropertiesFactory;

public abstract class Command {

    protected ToolbarPropertiesFactory factory;

    public Command(ToolbarPropertiesFactory toolbarPropertiesFactory) {
        this.factory = toolbarPropertiesFactory;
    }

    public abstract String keyword();

    public abstract void execute(String content) throws CommandParseFailedException;
}
