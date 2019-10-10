package develop.toolbar.utils;

import lombok.NonNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * 输入输出流增强
 *
 * @author qiushui on 2019-02-21.
 */
public final class IOAdvice {

    /**
     * 文件读取行
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static Stream<String> readLines(@NonNull InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name());
        List<String> lines = new LinkedList<>();
        while (scanner.hasNext()) {
            lines.add(scanner.nextLine());
        }
        scanner.close();
        return lines.stream();
    }

    /**
     * 写出文本行到文件
     *
     * @param lines
     * @param filename
     * @throws IOException
     */
    public static void writeLines(List<String> lines, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
        }
    }

}
