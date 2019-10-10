package develop.toolbar;

import develop.toolbar.utils.IOAdvice;
import lombok.Cleanup;
import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchHistoryManager {

    private String historyPath;

    @Getter
    private List<String> histories;

    public SearchHistoryManager() {
        this.historyPath = System.getProperty("user.dir") + File.separator + "history.txt";
        histories = readHistory();
    }

    /**
     * 读取查询历史
     *
     * @return
     */
    private List<String> readHistory() {
        try {
            final File file = new File(historyPath);
            if (file.exists()) {
                @Cleanup FileInputStream inputStream = new FileInputStream(file);
                return IOAdvice.readLines(inputStream).collect(Collectors.toList());
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
            IOAdvice.writeLines(histories, historyPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
