package develop.toolbar.command;

import develop.toolbar.properties.ToolbarProperties;
import develop.toolbar.utils.BrowseUtils;
import org.springframework.stereotype.Component;

@RegisterCommand
@Component
public class HttpCommand extends Command {

    public HttpCommand(ToolbarProperties toolbarProperties) {
        super(toolbarProperties);
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
