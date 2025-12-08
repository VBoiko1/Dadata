package api;

import api.endpoints.BaseClass;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specification {

    public static RequestSpecification requestSpec(String url) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", BaseClass.getToken())
                .build();
    }

    public static RequestSpecification requestSpecInvalidToken(String url) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", BaseClass.getInvalidToken())
                .build();
    }

    public static RequestSpecification requestSpecWithoutToken(String url) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification responseSpecOK200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public static ResponseSpecification responseSpecError400() {
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();
    }

    public static ResponseSpecification responseSpecError401() {
        return new ResponseSpecBuilder()
                .expectStatusCode(401)
                .build();
    }


    public static ResponseSpecification responseSpecError403() {
        return new ResponseSpecBuilder()
                .expectStatusCode(403)
                .build();
    }


    }


