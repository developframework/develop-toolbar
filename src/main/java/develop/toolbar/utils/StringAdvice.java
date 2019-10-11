package develop.toolbar.utils;

public final class StringAdvice {

    public static String[] cutOff(String string, int index) {
        return new String[]{
                string.substring(0, index).trim(),
                string.substring(index).trim()
        };
    }
}
