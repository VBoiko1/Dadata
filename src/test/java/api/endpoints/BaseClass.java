package api.endpoints;


import api.dadata.helpers.ConfigContainer;
import io.restassured.RestAssured;

public class BaseClass {

    protected ConfigContainer config;

    protected static final String INVALID_TOKEN = "Token 77ff8ae67e0f2fdda18cab781e5be39b053cd777";

    public BaseClass(){
        ConfigContainer configContainer = ConfigContainer.getInstance();
        configContainer.loadProperties();
        this.config = configContainer;
        RestAssured.baseURI = config.getProperty("BaseDadataURI");
    }

    public static String getToken() {
        String token = System.getenv("DADATA_TOKEN");

        if (token == null || token.trim().isEmpty()) {
            throw new RuntimeException("Установите переменную окружения DADATA_TOKEN!");
        }

        return "Token " + token.trim();
    }

    public static String getInvalidToken() {
        return INVALID_TOKEN;
    }

}
