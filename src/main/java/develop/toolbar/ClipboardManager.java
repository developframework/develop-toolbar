package develop.toolbar;

import develop.toolbar.structs.Clipboard;
import lombok.Cleanup;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ClipboardManager {

    private String clipboardPath;

    private Map<String, Clipboard> clipboards = new HashMap<>();

    public ClipboardManager() {
        this.clipboardPath = System.getProperty("user.dir") + File.separator + "clipboard.txt";
        this.loadClipboards();
    }

    public List<Clipboard> getAll() {
        return clipboards
                .values()
                .stream()
                .sorted(Comparator.comparing(Clipboard::getName))
                .collect(Collectors.toList());
    }

    public String getValue(String name) {
        autoClear();
        Clipboard clipboard = clipboards.get(name);
        return clipboard != null ? clipboard.getContent() : null;
    }

    public void add(String name, String content, int deadMinutes) {
        clipboards.put(name, new Clipboard(name, content, deadMinutes == 0 ? null : LocalDateTime.now().plusMinutes(deadMinutes)));
        writeClipboards();
    }

    public void remove(String name) {
        clipboards.remove(name);
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
                    if (line.startsWith(Clipboard.START_SIGN) && content.size() > 1) {
                        Clipboard clipboard = Clipboard.from(content);
                        clipboards.put(clipboard.getName(), clipboard);
                        content.clear();
                    }
                    content.add(line);
                }
                if (content.size() > 1) {
                    Clipboard clipboard = Clipboard.from(content);
                    clipboards.put(clipboard.getName(), clipboard);
                    content.clear();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        autoClear();
        writeClipboards();
    }

    public void setClipboardValue(String text) {
        if (text != null) {
            java.awt.datatransfer.Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            systemClipboard.setContents(new StringSelection(text.trim()), null);
        }
    }

    public String getClipboardValue() {
        java.awt.datatransfer.Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = systemClipboard.getContents("unknown");
        if (transferable != null) {
            try {
                if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    return (String) transferable.getTransferData(DataFlavor.stringFlavor);
                }
            } catch (UnsupportedFlavorException | IOException e) {
            }
        }
        return null;
    }

    private void autoClear() {
        // 自动清理过期内容
        LocalDateTime now = LocalDateTime.now();
        List<String> needRemoveList = clipboards
                .entrySet()
                .stream()
                .filter(entry -> {
                    LocalDateTime deadTime = entry.getValue().getDeadTime();
                    return deadTime != null && now.isAfter(deadTime);
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        for (String name : needRemoveList) {
            clipboards.remove(name);
        }
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
}
