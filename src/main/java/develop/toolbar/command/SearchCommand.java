package develop.toolbar.command;

import develop.toolbar.CommandParseFailedException;
import develop.toolbar.properties.CommandProperties;
import develop.toolbar.properties.SearchProperties;
import develop.toolbar.utils.BrowseUtils;
import develop.toolbar.utils.StringAdvice;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SearchCommand extends Command {

    public SearchCommand(CommandProperties commandProperties) {
        super(commandProperties);
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
        for (SearchProperties searchProperties : commandProperties.getSearch()) {
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
