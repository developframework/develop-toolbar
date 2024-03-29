package develop.toolbar.command;

import develop.toolbar.ToolbarPropertiesFactory;
import develop.toolbar.utils.BrowseUtils;

@RegisterCommand
public class HttpCommand extends Command {

    public HttpCommand(ToolbarPropertiesFactory toolbarPropertiesFactory) {
        super(toolbarPropertiesFactory);
    }

    @Override
    public String keyword() {
        return "http";
    }

    @Override
    public void execute(String content) {
        BrowseUtils.browse("http://" + content);
    }
}
