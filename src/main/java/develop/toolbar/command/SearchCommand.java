package develop.toolbar.command;

import develop.toolbar.CommandParseFailedException;
import develop.toolbar.properties.SearchProperties;
import develop.toolbar.properties.ToolbarProperties;
import develop.toolbar.utils.BrowseUtils;
import develop.toolbar.utils.StringAdvice;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RegisterCommand
@Component
public class SearchCommand extends Command {

    public SearchCommand(ToolbarProperties toolbarProperties) {
        super(toolbarProperties);
    }

    @Override
    public String keyword() {
        return "search";
    }

    @Override
    public void execute(String content) throws CommandParseFailedException {
        int i = content.indexOf(" ");
        if (i < 0) {
            throw new CommandParseFailedException();
        }
        String[] parts = StringAdvice.cutOff(content, i);
        String keyword;
        try {
            keyword = URLEncoder.encode(parts[1], StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }
        String urlFormat = null;
        for (SearchProperties searchProperties : toolbarProperties.getCommands().getSearch()) {
            if (searchProperties.getMethod().equals(parts[0])) {
                urlFormat = searchProperties.getUrl();
                break;
            }
        }
        if (urlFormat == null) {
            throw new CommandParseFailedException();
        }
        BrowseUtils.browse(
                String.format(urlFormat, keyword)
        );
    }
}
