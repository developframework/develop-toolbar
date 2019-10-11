package develop.toolbar.command;

import develop.toolbar.properties.CommandProperties;
import develop.toolbar.utils.BrowseUtils;

public class HttpCommand extends Command {

    public HttpCommand(CommandProperties commandProperties) {
        super(commandProperties);
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
