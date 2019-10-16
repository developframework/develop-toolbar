package develop.toolbar;

import develop.toolbar.structs.Clipboard;
import lombok.Cleanup;
import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class ClipboardManager {

    private String clipboardPath;

    @Getter
    private Map<String, Clipboard> clipboards = new HashMap<>();

    public ClipboardManager() {
        this.clipboardPath = System.getProperty("user.dir") + File.separator + "clipboard.txt";
        this.loadClipboards();
    }

    public void add(String name, String content, int deadMinutes) {
        clipboards.putIfAbsent(name, new Clipboard(name, content, deadMinutes == 0 ? null : LocalDateTime.now().plusMinutes(deadMinutes)));
        writeClipboards();
    }

    public void loadClipboards() {
        clipboards.clear();
        try {
            final File file = new File(clipboardPath);
            if (file.exists()) {
                @Cleanup FileInputStream inputStream = new FileInputStream(file);
                List<String> lines = IOUtils.readLines(inputStream, StandardCharsets.UTF_8);
                List<String> content = new LinkedList<>();
                for (String line : lines) {
                    if (line.startsWith(Clipboard.START_SIGN)) {
                        if (!content.isEmpty()) {
                            Clipboard clipboard = Clipboard.from(content);
                            clipboards.put(clipboard.getName(), clipboard);
                            content.clear();
                        }
                    } else {
                        content.add(line);
                    }
                }
                if (!content.isEmpty()) {
                    Clipboard clipboard = Clipboard.from(content);
                    clipboards.put(clipboard.getName(), clipboard);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 自动清理过期内容
        LocalDateTime now = LocalDateTime.now();
        for (Map.Entry<String, Clipboard> entry : clipboards.entrySet()) {
            LocalDateTime deadTime = entry.getValue().getDeadTime();
            if (deadTime != null && deadTime.isAfter(now)) {
                clipboards.remove(entry.getKey());
            }
        }
        writeClipboards();
    }

    private void writeClipboards() {
        StringBuilder sb = new StringBuilder();
        clipboards
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(entry -> sb.append(entry.getValue().toString()));
        try {
            @Cleanup FileOutputStream fileOutputStream = new FileOutputStream(clipboardPath);
            IOUtils.write(sb.toString(), fileOutputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() {
        try {
            @Cleanup FileOutputStream fileOutputStream = new FileOutputStream(clipboardPath);
            IOUtils.write("", fileOutputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void put(String key, StringBuilder sb, LocalDateTime deadTime) {
        if (key != null && sb.length() > 0) {
            clipboards.putIfAbsent(key, new Clipboard(key, sb.toString(), deadTime));
            sb.setLength(0);
        }
    }
}
