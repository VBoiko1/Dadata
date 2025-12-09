package api.dadataTest;

import api.dadata.address.AddressIplocate;
import api.dadata.address.AddressRequest;
import api.dadata.address.AddressSuggestions;
import api.endpoints.DadataEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


public class AddressDataTest extends DadataEndpoints {


    @Test
    @DisplayName("Проверяет получение элементов справочника адресов по наименованию города")
    public void testPOSTSuggestAddressValid() {
        String requestPath = RestAssured.baseURI + POST_SUGGEST_ADDRESS;

        AddressRequest addressRequest = new AddressRequest(config.getProperty("POSTAddressValidValue"));

        List<AddressSuggestions> addressSuggestions = given()
                .header("Authorization", getToken())
                .contentType(ContentType.JSON)
                .when()
                .body(addressRequest)
                .post(requestPath)
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList("suggestions", AddressSuggestions.class);

        assertEquals(config.getProperty("POSTAddressValidValueExpected"), addressSuggestions.get(0).getValue());
        assertEquals(config.getProperty("POSTAddressValidValueRegionTypeExpected"), addressSuggestions.get(0).getData().getRegion_with_type());

    }


    @Test
    @DisplayName("Проверяет получение элементов справочника адресов по части наименованию города")
    public void testPOSTSSuggestAddressValid1() {
        String requestPath = RestAssured.baseURI + POST_SUGGEST_ADDRESS;

        AddressRequest addressRequest = new AddressRequest(config.getProperty("POSTAddressValidValue1"));

        List<AddressSuggestions> addressSuggestions = given()
                .header("Authorization", getToken())
                .contentType(ContentType.JSON)
                .when()
                .body(addressRequest)
                .post(requestPath)
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList("suggestions", AddressSuggestions.class);

        assertEquals(config.getProperty("POSTAddressValidValue1Expected"), addressSuggestions.get(1).getValue());
        assertEquals(config.getProperty("POSTAddressValidValueCountryExpected"), addressSuggestions.get(1).getData().getCountry());
    }


    @Test
    @DisplayName("Проверяет получение элементов справочника адресов по наименованию города в нижнем регистре")
    public void testPOSTSuggestAddressLowerCseValue() {
        String requestPath = RestAssured.baseURI + POST_SUGGEST_ADDRESS;

        AddressRequest addressRequest = new AddressRequest(config.getProperty("POSTAddressValidValue2"));

        List<AddressSuggestions> addressSuggestions = given()
                .header("Authorization", getToken())
                .contentType(ContentType.JSON)
                .when()
                .body(addressRequest)
                .post(requestPath)
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList("suggestions", AddressSuggestions.class);

        assertEquals(config.getProperty("POSTAddressValidValue2Expected"), addressSuggestions.get(1).getValue());
        assertEquals(config.getProperty("POSTAddressValidValue2CityTypeExpected"), addressSuggestions.get(1).getData().getCity_with_type());
    }


    @Test
    @DisplayName("Проверяет получение элементов справочника адресов по наименованию города в верхнем регистре")
    public void testPOSTSuggestAddressUpperCaseValue() {
        String requestPath = RestAssured.baseURI + POST_SUGGEST_ADDRESS;

        AddressRequest addressRequest = new AddressRequest(config.getProperty("POSTAddressValidValue3"));

        List<AddressSuggestions> addressSuggestions = given()
                .header("Authorization", getToken())
                .contentType(ContentType.JSON)
                .when()
                .body(addressRequest)
                .post(requestPath)
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList("suggestions", AddressSuggestions.class);

        assertEquals(config.getProperty("POSTAddressValidValue3Expected"), addressSuggestions.get(0).getValue());
        assertTrue(addressSuggestions.get(0).getUnrestricted_value().contains(config.getProperty("POSTAddressValue3UnrestrictedExpected")));
    }


    @Test
    @DisplayName("Проверяет получение элементов справочника адресов на английском языке")
    public void testPOSTSuggestAddressENLanguageValue() {
        String requestPath = RestAssured.baseURI + POST_SUGGEST_ADDRESS;

        AddressRequest addressRequest = new AddressRequest(config.getProperty("POSTAddressValidValueEN"),
                config.getProperty("POSTSuggestAddressLanguage")
        );

        List<AddressSuggestions> addressSuggestions = given()
                .header("Authorization", getToken())
                .contentType(ContentType.JSON)
                .when()
                .body(addressRequest)
                .post(requestPath)
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList("suggestions", AddressSuggestions.class);

        assertTrue(addressSuggestions.get(0).getValue().contains((config.getProperty("POSTAddressValidValueEnExpected"))));
        assertEquals(config.getProperty("POSTAddressValidValueEnCityExpected"), addressSuggestions.get(0).getData().getCity());

    }


    @Test
    @DisplayName("Запрос с пустым Query")
    public void testPOSTSuggestAddressEmptyValue() {
        String requestPath = RestAssured.baseURI + POST_SUGGEST_ADDRESS;

        AddressRequest addressRequest = new AddressRequest("");

        List<AddressSuggestions> addressSuggestions = given()
                .header("Authorization", getToken())
                .contentType(ContentType.JSON)
                .when()
                .body(addressRequest)
                .post(requestPath)
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList("suggestions", AddressSuggestions.class);

        assertTrue(addressSuggestions.isEmpty());
    }


    @Test
    @DisplayName("Проверяет получение элементов справочника по количеству выдаваемых результатов (По умолчанию 10)")
    public void testPOSTSuggestAddressCountResult() {
        String requestPath = RestAssured.baseURI + POST_SUGGEST_ADDRESS;

        AddressRequest addressRequest = new AddressRequest(config.getProperty("POSTAddressValidValue4"));

        List<AddressSuggestions> addressSuggestions = given()
                .header("Authorization", getToken())
                .contentType(ContentType.JSON)
                .when()
                .body(addressRequest)
                .post(requestPath)
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList("suggestions", AddressSuggestions.class);

        assertEquals(10, addressSuggestions.size());
    }


    @Test
    @DisplayName("Проверяет получение элементов справочника по заданному количеству выдаваемых результатов.(Макс. = 20)")
    public void testPOSTSuggestAddressCountValue() {
        String requestPath = RestAssured.baseURI + POST_SUGGEST_ADDRESS;

        AddressRequest addressRequest = new AddressRequest(config.getProperty("POSTAddressValidValue4"), 22);

        List<AddressSuggestions> addressSuggestions = given()
                .header("Authorization", getToken())
                .contentType(ContentType.JSON)
                .when()
                .body(addressRequest)
                .post(requestPath)
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList("suggestions", AddressSuggestions.class);

        assertEquals(20, addressSuggestions.size());
    }


    @Test
    @DisplayName("Проверка с некорректным телом запроса")
    public void testPOSTSuggestAddressInvalidValue() {
        String requestPath = RestAssured.baseURI + POST_SUGGEST_ADDRESS;

        given()
                .header("Authorization", getToken())
                .contentType(ContentType.JSON)
                .when()
                .body("{addressRequest}")
                .post(requestPath)
                .then()
                .statusCode(400)
                .log().all();


    }


    @Test
    @DisplayName("Проверка получение элементов справочника с не существующим токеном")
    public void testPOSTSuggestAddressInvalidTokenAuth() {
        String requestPath = RestAssured.baseURI + POST_SUGGEST_ADDRESS;

        AddressRequest addressRequest = new AddressRequest(config.getProperty("POSTAddressInvalidToken"));

        given()
                .header("Authorization", getInvalidToken())
                .contentType(ContentType.JSON)
                .when()
                .body(addressRequest)
                .post(requestPath)
                .then()
                .statusCode(403)
                .log().all();

    }


    @Test
    @DisplayName("Проверка получение элементов справочника без токена")
    public void testPOSTSuggestAddressWithoutToken() {
        String requestPath = RestAssured.baseURI + POST_SUGGEST_ADDRESS;

        AddressRequest addressRequest = new AddressRequest(config.getProperty("POSTAddressWithoutToken"));

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(addressRequest)
                .post(requestPath)
                .then()
                .statusCode(401)
                .log().all();

    }


    /**
     * Проверки API: город по IP-адресу
     */
    @Test
    @DisplayName("Проверка получения элемента справочника по IP")
    public void testGETIplocateAddress() {
        String requestPath = RestAssured.baseURI + GET_IPLOCATE_ADDRESS;

        AddressIplocate addressIplocates = given()
                .header("Authorization", getToken())
                .contentType(ContentType.JSON)
                .param(GET_IPLOCATE_ADDRESS_PARAM, config.getProperty("GETIplocateAddressValueIp"))
                .when()
                .get(requestPath)
                .then()
                .statusCode(200)
                .log().all()
                .extract().response().jsonPath().getObject("location", AddressIplocate.class);

        assertEquals(config.getProperty("GETIplocateAddressValueIpExpected"), addressIplocates.getValue());
        assertEquals(config.getProperty("GETIplocateAddressValueCityExpected"), addressIplocates.getData().getCity());
    }


    @Test
    @DisplayName("Проверка с указанием не валидного IP")
    public void testGETIplocateAddressInvalidIp() {
        String requestPath = RestAssured.baseURI + GET_IPLOCATE_ADDRESS;

        AddressIplocate addressIplocate = given()
                .header("Authorization", getToken())
                .contentType(ContentType.JSON)
                .param(GET_IPLOCATE_ADDRESS_PARAM, config.getProperty("GETIplocateAddressInvalidIp"))
                .when()
                .get(requestPath)
                .then()
                .statusCode(200)
                .log().all()
                .extract().response().jsonPath().getObject("location", AddressIplocate.class);

        assertNull(addressIplocate);

    }

    @Test
    @DisplayName("Проверка запрос без обязательного параметра ip")
    public void testGETIplocateAddressWithoutIp() {
        String requestPath = RestAssured.baseURI + GET_IPLOCATE_ADDRESS;

        AddressIplocate addressIplocate = given()
                .header("Authorization", getToken())
                .contentType(ContentType.JSON)
                .when()
                .get(requestPath)
                .then()
                .statusCode(200)
                .log().all()
                .extract().response().jsonPath().getObject("location", AddressIplocate.class);

        assertNotNull(addressIplocate);
    }


    @Test
    @DisplayName("Проверка запрос c параметром language.")
    public void testGETIplocateAddressLanguageEn() {
        String requestPath = RestAssured.baseURI + GET_IPLOCATE_ADDRESS;

        AddressIplocate addressIplocate = given()
                .header("Authorization", getToken())
                .contentType(ContentType.JSON)
                .param(GET_IPLOCATE_ADDRESS_PARAM, config.getProperty("GETIplocateAddressValueIp1"))
                .param(GET_IPLOCATE_ADDRESS_PARAM_LNG, config.getProperty("GETIplocateAddressParamEN"))
                .when()
                .get(requestPath)
                .then()
                .statusCode(200)
                .log().all()
                .extract().response().jsonPath().getObject("location", AddressIplocate.class);

        assertTrue(addressIplocate.getValue().contains(config.getProperty("GETIplocateAddressValueIp1Expected")));
        assertEquals(config.getProperty("GETIplocateAddressValue1CityExpected"), addressIplocate.getData().getCity());
    }


    @Test
    @DisplayName("Проверка запрос c не верным Токеном")
    public void testGETIplocateAddressInvalidToken() {
        String requestPath = RestAssured.baseURI + GET_IPLOCATE_ADDRESS;

        given()
                .header("Authorization", getInvalidToken())
                .contentType(ContentType.JSON)
                .param(GET_IPLOCATE_ADDRESS_PARAM, config.getProperty("GETIplocateAddressValueIp2"))
                .when()
                .get(requestPath)
                .then()
                .statusCode(403)
                .log().all()
                .extract().response().jsonPath().getObject("location", AddressIplocate.class);
    }


    @Test
    @DisplayName("Проверка запрос без авторизации")
    public void testGETIplocateAddressWithoutToken() {

        String requestPath = RestAssured.baseURI + GET_IPLOCATE_ADDRESS;

        given()
                .contentType(ContentType.JSON)
                .param(GET_IPLOCATE_ADDRESS_PARAM, config.getProperty("GETIplocateAddressValueIp3"))
                .when()
                .get(requestPath)
                .then()
                .statusCode(401)
                .log().all()
                .extract().response().jsonPath().getObject("location", AddressIplocate.class);
    }

}
