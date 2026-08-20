package utils;

public class ConfigReader {

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static String get(String key) {
        String value = System.getenv(key);

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    "Config değeri bulunamadı: " + key
            );
        }

        return value;
    }
}