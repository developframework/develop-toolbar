package develop.toolbar.structs;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "name")
public class Clipboard {

    public static final String START_SIGN = "/# ";

    public static final String SEPARATOR = "@";

    private String name;

    private String content;

    private LocalDateTime deadTime;

    public static Clipboard from(List<String> lines) {
        String title = lines.get(0).substring(START_SIGN.length()).trim();
        String[] parts = title.split(SEPARATOR);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < lines.size(); i++) {
            sb.append(lines.get(i));
        }
        return new Clipboard(
                parts[0],
                sb.toString(),
                parts.length == 2 ? LocalDateTime.parse(parts[1]) : null
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(Clipboard.START_SIGN).append(name);
        if (deadTime != null) {
            sb.append(SEPARATOR).append(LocalDateTime.now());
        }
        return sb.append("\r\n").append(content).append("\r\n").toString();
    }
}
