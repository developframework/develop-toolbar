package develop.toolbar.command;

import develop.toolbar.ToolbarPropertiesFactory;

@RegisterCommand
public class RefreshCommand extends Command {

    public RefreshCommand(ToolbarPropertiesFactory toolbarPropertiesFactory) {
        super(toolbarPropertiesFactory);
    }

    @Override
    public String keyword() {
        return "refresh";
    }

    @Override
    public void execute(String content) {
        factory.load();
    }
}
