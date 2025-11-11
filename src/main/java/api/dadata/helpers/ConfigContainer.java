package api.dadata.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfigContainer {


    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream input = ConfigContainer.class.getClassLoader().getResourceAsStream("dadata.properties")) {

            if (input == null) {
                throw new RuntimeException("Файл не найден");
            }
            properties.load(new InputStreamReader(input, StandardCharsets.UTF_8));

        } catch (IOException e) {
            throw new RuntimeException("Error loading config", e);
        }
    }


    public static String getConfigProperty(String key) {
        return ConfigContainer.properties.getProperty(key);
    }
}
