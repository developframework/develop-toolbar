package develop.toolbar.command;

import develop.toolbar.CommandParseFailedException;
import develop.toolbar.utils.BrowseUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SearchCommand implements Command {

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
        final String method = content.substring(0, i);
        String keyword;
        try {
            keyword = URLEncoder.encode(content.substring(i).trim(), StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }
        String uri;
        switch (method) {
            case "baidu":
                uri = "https://www.baidu.com/s?wd=%s";
                break;
            case "google":
                uri = "https://www.google.com/search?q=%s";
                break;
            case "mvn":
                uri = "https://search.maven.org/search?q=%s";
                break;
            default:
                throw new CommandParseFailedException();
        }
        BrowseUtils.browse(
                String.format(uri, keyword)
        );
    }
}
