package api.dadata.helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfigContainer {

    // Статическое поле для хранения единственного экземпляра
    private static ConfigContainer instance;

    //  Поле для хранения свойств
    private final Properties properties;

    //  Приватный конструктор - нельзя создать извне
    private ConfigContainer() {
        this.properties = new Properties();

    }

    //  Публичный статический метод для получения экземпляра
    public static synchronized ConfigContainer getInstance() {
        if (instance == null) {
            instance = new ConfigContainer();
        }
        return instance;
    }

    //  Метод загрузки значений
    public ConfigContainer loadProperties() {
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("dadata.properties")) {

            if (input == null) {
                throw new RuntimeException("Файл 'dadata.properties' не найден в classpath");
            }

            // UTF-8
            properties.load(new InputStreamReader(input, StandardCharsets.UTF_8));

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке конфигурации", e);
        }
        return this;
    }


    public void loadConfig(String path) {
        try (InputStream input = new FileInputStream(path)) {

            // Загружаем с UTF-8 кодировкой
            properties.load(new InputStreamReader(input, StandardCharsets.UTF_8));

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке конфигурации из файла: " + path, e);
        }
    }

    //  Метод для получения значений
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
