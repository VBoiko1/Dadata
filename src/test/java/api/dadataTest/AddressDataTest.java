package api.dadataTest;

import api.dadata.address.AddressIplocate;
import api.dadata.address.AddressRequest;
import api.dadata.address.AddressSuggestions;
import api.endpoints.DadataEndpoints;
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

        AddressRequest addressRequest = AddressRequest.builder()
                .query(config.getProperty("POSTAddressValidValue"))
                .build();

        List<AddressSuggestions> addressSuggestions = given()
                .auth().preemptive().oauth2(getToken())
                .contentType(ContentType.JSON)
                .when()
                .body(addressRequest)
                .post(POST_SUGGEST_ADDRESS)
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

        AddressRequest addressRequest = AddressRequest.builder()
                .query(config.getProperty("POSTAddressValidValue1"))
                .build();

        List<AddressSuggestions> addressSuggestions = given()
                .auth().preemptive().oauth2(getToken())
                .contentType(ContentType.JSON)
                .when()
                .body(addressRequest)
                .post(POST_SUGGEST_ADDRESS)
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

        AddressRequest addressRequest = AddressRequest.builder()
                .query(config.getProperty("POSTAddressValidValue2"))
                .build();

        List<AddressSuggestions> addressSuggestions = given()
                .auth().preemptive().oauth2(getToken())
                .contentType(ContentType.JSON)
                .when()
                .body(addressRequest)
                .post(POST_SUGGEST_ADDRESS)
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

        AddressRequest addressRequest = AddressRequest.builder()
                .query(config.getProperty("POSTAddressValidValue3"))
                .build();

        List<AddressSuggestions> addressSuggestions = given()
                .auth().preemptive().oauth2(getToken())
                .contentType(ContentType.JSON)
                .when()
                .body(addressRequest)
                .post(POST_SUGGEST_ADDRESS)
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

        AddressRequest addressRequest = AddressRequest.builder()
                .query(config.getProperty("POSTAddressValidValueEN"))
                .language(config.getProperty("POSTSuggestAddressLanguage"))
                .build();


        List<AddressSuggestions> addressSuggestions = given()
                .auth().preemptive().oauth2(getToken())
                .contentType(ContentType.JSON)
                .when()
                .body(addressRequest)
                .post(POST_SUGGEST_ADDRESS)
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

        AddressRequest addressRequest =  AddressRequest.builder()
                .query("")
                .build();

        List<AddressSuggestions> addressSuggestions = given()
                .auth().preemptive().oauth2(getToken())
                .contentType(ContentType.JSON)
                .when()
                .body(addressRequest)
                .post(POST_SUGGEST_ADDRESS)
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList("suggestions", AddressSuggestions.class);

        assertTrue(addressSuggestions.isEmpty());
    }


    @Test
    @DisplayName("Проверяет получение элементов справочника по количеству выдаваемых результатов (По умолчанию 10)")
    public void testPOSTSuggestAddressCountResult() {

        AddressRequest addressRequest = AddressRequest.builder()
                .query(config.getProperty("POSTAddressValidValue4"))
                .build();

        List<AddressSuggestions> addressSuggestions = given()
                .auth().preemptive().oauth2(getToken())
                .contentType(ContentType.JSON)
                .when()
                .body(addressRequest)
                .post(POST_SUGGEST_ADDRESS)
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList("suggestions", AddressSuggestions.class);

        assertEquals(10, addressSuggestions.size());
    }


    @Test
    @DisplayName("Проверяет получение элементов справочника по заданному количеству выдаваемых результатов.(Макс. = 20)")
    public void testPOSTSuggestAddressCountValue() {

        AddressRequest addressRequest = AddressRequest.builder()
                .query(config.getProperty("POSTAddressValidValue4"))
                .count(22)
                .build();

        List<AddressSuggestions> addressSuggestions = given()
                .auth().preemptive().oauth2(getToken())
                .contentType(ContentType.JSON)
                .when()
                .body(addressRequest)
                .post(POST_SUGGEST_ADDRESS)
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList("suggestions", AddressSuggestions.class);

        assertEquals(20, addressSuggestions.size());
    }


    @Test
    @DisplayName("Проверка с некорректным телом запроса")
    public void testPOSTSuggestAddressInvalidValue() {

        given()
                .auth().preemptive().oauth2(getToken())
                .contentType(ContentType.JSON)
                .when()
                .body("{addressRequest}")
                .post(POST_SUGGEST_ADDRESS)
                .then()
                .statusCode(400)
                .log().all();


    }


    @Test
    @DisplayName("Проверка получение элементов справочника с не существующим токеном")
    public void testPOSTSuggestAddressInvalidTokenAuth() {

        AddressRequest addressRequest = AddressRequest.builder()
                .query(config.getProperty("POSTAddressInvalidToken"))
                .build();

        given()
                .auth().preemptive().oauth2(getInvalidToken())
                .contentType(ContentType.JSON)
                .when()
                .body(addressRequest)
                .post(POST_SUGGEST_ADDRESS)
                .then()
                .statusCode(403)
                .log().all();

    }


    @Test
    @DisplayName("Проверка получение элементов справочника без токена")
    public void testPOSTSuggestAddressWithoutToken() {

        AddressRequest addressRequest = AddressRequest.builder()
                .query(config.getProperty("POSTAddressWithoutToken"))
                .build();

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(addressRequest)
                .post(POST_SUGGEST_ADDRESS)
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

        AddressIplocate addressIplocates = given()
                .auth().preemptive().oauth2(getToken())
                .contentType(ContentType.JSON)
                .param(GET_IPLOCATE_ADDRESS_PARAM, config.getProperty("GETIplocateAddressValueIp"))
                .when()
                .get(GET_IPLOCATE_ADDRESS)
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

        AddressIplocate addressIplocate = given()
                .auth().preemptive().oauth2(getToken())
                .contentType(ContentType.JSON)
                .param(GET_IPLOCATE_ADDRESS_PARAM, config.getProperty("GETIplocateAddressInvalidIp"))
                .when()
                .get(GET_IPLOCATE_ADDRESS)
                .then()
                .statusCode(200)
                .log().all()
                .extract().response().jsonPath().getObject("location", AddressIplocate.class);

        assertNull(addressIplocate);

    }

    @Test
    @DisplayName("Проверка запрос без обязательного параметра ip")
    public void testGETIplocateAddressWithoutIp() {

        AddressIplocate addressIplocate = given()
                .auth().preemptive().oauth2(getToken())
                .contentType(ContentType.JSON)
                .when()
                .get(GET_IPLOCATE_ADDRESS)
                .then()
                .statusCode(200)
                .log().all()
                .extract().response().jsonPath().getObject("location", AddressIplocate.class);

        assertNotNull(addressIplocate);
    }


    @Test
    @DisplayName("Проверка запрос c параметром language.")
    public void testGETIplocateAddressLanguageEn() {

        AddressIplocate addressIplocate = given()
                .auth().preemptive().oauth2(getToken())
                .contentType(ContentType.JSON)
                .param(GET_IPLOCATE_ADDRESS_PARAM, config.getProperty("GETIplocateAddressValueIp1"))
                .param(GET_IPLOCATE_ADDRESS_PARAM_LNG, config.getProperty("GETIplocateAddressParamEN"))
                .when()
                .get(GET_IPLOCATE_ADDRESS)
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

        given()
                .auth().preemptive().oauth2(getInvalidToken())
                .contentType(ContentType.JSON)
                .param(GET_IPLOCATE_ADDRESS_PARAM, config.getProperty("GETIplocateAddressValueIp2"))
                .when()
                .get(GET_IPLOCATE_ADDRESS)
                .then()
                .statusCode(403)
                .log().all()
                .extract().response().jsonPath().getObject("location", AddressIplocate.class);
    }


    @Test
    @DisplayName("Проверка запрос без авторизации")
    public void testGETIplocateAddressWithoutToken() {

        given()
                .contentType(ContentType.JSON)
                .param(GET_IPLOCATE_ADDRESS_PARAM, config.getProperty("GETIplocateAddressValueIp3"))
                .when()
                .get(GET_IPLOCATE_ADDRESS)
                .then()
                .statusCode(401)
                .log().all()
                .extract().response().jsonPath().getObject("location", AddressIplocate.class);
    }

}
