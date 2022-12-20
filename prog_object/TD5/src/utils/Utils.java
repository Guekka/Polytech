package utils;

import java.nio.file.Path;

public final class Utils {
    private Utils() {
        // prevent instantiation
    }

    public static String pathStem(Path path) {
        String name = path.getFileName().toString();
        // remove extension
        int pos = name.lastIndexOf('.');
        if (pos > 0) {
            name = name.substring(0, pos);
        }
        return name;
    }

    public static String pathStem(String path) {
        return pathStem(Path.of(path));
    }

    public static String parseCamelCase(String s) {
        return s.replaceAll("([A-Z]|[1-9]+)", " $1").trim();
    }

}
