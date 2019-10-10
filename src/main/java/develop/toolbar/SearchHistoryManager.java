package develop.toolbar;

import develop.toolkit.base.utils.IOAdvice;
import lombok.Getter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchHistoryManager {

    private Path historyPath;

    @Getter
    private List<String> histories;

    public SearchHistoryManager() {
        this.historyPath = Path.of(System.getProperty("user.dir"), "history.txt");
        histories = readHistory();
    }

    /**
     * 读取查询历史
     *
     * @return
     */
    private List<String> readHistory() {
        try {
            if (Files.exists(historyPath)) {
                return IOAdvice.readLines(historyPath.toString()).collect(Collectors.toList());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 追加历史
     *
     * @param content
     */
    public void appendHistory(String content) {
        histories.remove(content);
        histories.add(0, content);
        try {
            IOAdvice.writeLines(histories, historyPath.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
