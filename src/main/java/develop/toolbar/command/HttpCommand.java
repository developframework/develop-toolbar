package develop.toolbar.command;

import develop.toolbar.utils.BrowseUtils;

public class HttpCommand implements Command {
    @Override
    public String keyword() {
        return "http";
    }

    @Override
    public void execute(String content) {
        BrowseUtils.browse("http://" + content);
    }
}
