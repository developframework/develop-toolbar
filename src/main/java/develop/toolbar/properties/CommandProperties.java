package develop.toolbar.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author qiushui on 2019-10-11.
 */
@Getter
@Setter
public class CommandProperties {

    private List<SearchProperties> search;

    private List<BookmarkProperties> bookmarks;

    private List<AliasProperties> aliases;

    private List<Clipboard> clipboards;
}
