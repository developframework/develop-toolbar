package develop.toolbar;

import lombok.Cleanup;
import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
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
                return IOUtils.readLines(inputStream, StandardCharsets.UTF_8);
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
            @Cleanup FileOutputStream fileOutputStream = new FileOutputStream(historyPath);
            IOUtils.writeLines(histories, "\n", fileOutputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
