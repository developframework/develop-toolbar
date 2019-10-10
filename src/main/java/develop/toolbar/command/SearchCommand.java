package develop.toolbar.command;

import develop.toolbar.CommandParseFailedException;
import develop.toolbar.utils.BrowseUtils;
import develop.toolkit.base.struct.TwoValues;
import develop.toolkit.base.utils.StringAdvice;

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
        if(i < 0) {
            throw new CommandParseFailedException();
        }
        TwoValues<String, String> twoValues = StringAdvice.cutOff(content, i);
        BrowseUtils.browse(
                String.format(
                        switch (twoValues.getFirstValue()) {
                            case "baidu" -> "https://www.baidu.com/s?wd=%s";
                            case "google" -> "https://www.google.com/search?q=%s";
                            case "mvn" -> "https://search.maven.org/search?q=%s";
                            default -> throw new CommandParseFailedException();
                        },
                        URLEncoder.encode(twoValues.getSecondValue().trim(), StandardCharsets.UTF_8)
                )
        );
    }
}
