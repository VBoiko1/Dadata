package api.endpoints;

import api.dadata.helpers.ConfigContainer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

public class BaseClass {

    @BeforeEach
    public void setUp() {
        // Сбрасываем спецификации RestAssured перед каждым тестом
        RestAssured.reset();
    }

    protected static final String URL = "https://suggestions.dadata.ru/suggestions/";

    protected static final String TOKEN = "Token 77ff8ae67e0f2fdda18cab781e5be39b053cd387";

    protected static final String INVALID_TOKEN = "Token 77ff8ae67e0f2fdda18cab781e5be39b053cd777";

    protected ConfigContainer config;

    public BaseClass() {

        this.config = ConfigContainer.getInstance();

    }


    public static String getToken() {
        return TOKEN;
    }

    public static String getInvalidToken() {
        return INVALID_TOKEN;
    }


}
