package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;

import java.io.InputStream;

public class LocatorReader {

    private static JsonNode root;

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();

            InputStream inputStream = LocatorReader.class
                    .getClassLoader()
                    .getResourceAsStream("locators.json");

            if (inputStream == null) {
                throw new RuntimeException("locators.json bulunamadı.");
            }

            root = mapper.readTree(inputStream);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Locator dosyası okunamadı.",
                    e
            );
        }
    }

    public static By getLocator(String path) {

        String[] keys = path.split("\\.");
        JsonNode node = root;

        for (String key : keys) {
            node = node.get(key);

            if (node == null) {
                throw new IllegalArgumentException(
                        "Locator bulunamadı: " + path
                );
            }
        }

        String locator = node.asText();
        String[] parts = locator.split(":", 2);

        if (parts.length != 2) {
            throw new IllegalArgumentException(
                    "Locator formatı hatalı: " + locator
            );
        }

        String type = parts[0].toLowerCase();
        String value = parts[1];

        return switch (type) {
            case "id" -> By.id(value);
            case "css" -> By.cssSelector(value);
            case "xpath" -> By.xpath(value);
            case "name" -> By.name(value);
            case "classname" -> By.className(value);
            case "tagname" -> By.tagName(value);
            case "linktext" -> By.linkText(value);
            case "partiallinktext" -> By.partialLinkText(value);

            default -> throw new IllegalArgumentException(
                    "Desteklenmeyen locator tipi: " + type
            );
        };
    }
}