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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ClipboardManager {

    private String clipboardPath;

    @Getter
    private Map<String, String> clipboards = new HashMap<>();

    public ClipboardManager() {
        this.clipboardPath = System.getProperty("user.dir") + File.separator + "clipboard.txt";
    }

    public Map<String, String> readClipboards() {
        clipboards.clear();
        try {
            final File file = new File(clipboardPath);
            if (file.exists()) {
                @Cleanup FileInputStream inputStream = new FileInputStream(file);
                List<String> lines = IOUtils.readLines(inputStream, StandardCharsets.UTF_8);
                StringBuilder sb = new StringBuilder();
                String key = null;
                for (String line : lines) {
                    if (line.isEmpty()) {
                        continue;
                    }
                    if (line.startsWith("#")) {
                        put(key, sb);
                        key = line.substring(1).trim();
                    } else {
                        if (sb.length() > 0) {
                            sb.append(" ");
                        }
                        sb.append(line.trim());
                    }
                }
                put(key, sb);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return clipboards;
    }

    public void writeClipboards() {
        StringBuilder sb = new StringBuilder();
        clipboards
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(entry -> {
                    sb.append("# ").append(entry.getKey()).append("\r\n");
                    sb.append(entry.getValue()).append("\r\n");
                });
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

    private void put(String key, StringBuilder sb) {
        if (key != null && sb.length() > 0) {
            clipboards.putIfAbsent(key, sb.toString());
            sb.setLength(0);
        }
    }
}
