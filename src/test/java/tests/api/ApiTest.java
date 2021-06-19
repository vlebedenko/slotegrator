package tests.api;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static config.UserConfig.API_PASS;
import static config.UserConfig.CODED_TOKEN;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class ApiTest {

    private String bodyAccess = "{\n" +
            "    \"grant_type\": \"client_credentials\",\n" +
            "    \"scope\": \"guest:default\"\n" +
            "}";

    private String accessToken;

    private static String playerLogin;
    private static String playerPassword;
    private static String playerEmail;
    private static String playerName;
    private static String playerSurName;
    private static String newPlayerBody;
    private static String playerAuthBody;
    private static String playerAccessToken;
    private static String id;


    @BeforeAll
    public static void testDataGenerating() {
        int length = 7;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        long unixTime = System.currentTimeMillis();
        String time = Long.toString(unixTime);

        playerLogin = "login" + time;
        playerPassword = Base64.getEncoder().encodeToString((API_PASS).getBytes(StandardCharsets.UTF_8));
        playerEmail = generatedString + "@somemail.com";
        playerName = "Firstname" + generatedString;
        playerSurName = "Surname" + generatedString;
        newPlayerBody = "{\n" +
                "\"username\": \"" + playerLogin + "\",\n" +
                "\"password_change\": \"" + playerPassword + "\",\n" +
                "\"password_repeat\": \"" + playerPassword + "\",\n" +
                "\"email\": \"" + playerEmail + "\",\n" +
                "\"name\": \"" + playerName + "\",\n" +
                "\"surname\": \"" + playerSurName + "\"\n" +
                "}";
        playerAuthBody = "{\n" +
                "\t\"grant_type\":\"password\",     \n" +
                "\t\"username\":\"" + playerLogin + "\",      \n" +
                "\t\"password\":\"" + playerPassword + "\" \n" +
                "}";

    }

    @Test
    public void apiCasinoTest() {

        /** Получение токена для регистрации игрока **/

        accessToken = given()
                .auth()
                .preemptive()
                .basic(CODED_TOKEN, API_PASS)
                .contentType(JSON)
                .accept(JSON)
                //.header("authorization", "Basic " + encodedKey)
                .baseUri("http://test-api.d6.dev.devcaz.com")
                .basePath("/v2")
                .body(bodyAccess)
                .when()
                .post("/oauth2/token")
                .then()
                .statusCode(200)
                .extract().path("access_token");

        /** Создание нового игрока **/

        ValidatableResponse response =
                given()
                        .header("authorization", "Bearer " + accessToken)
                        .contentType(JSON)
                        .accept(JSON)
                        .baseUri("http://test-api.d6.dev.devcaz.com")
                        .basePath("/v2")
                        .body(newPlayerBody)
                        .when()
                        .post("/players")
                        .then()
                        .statusCode(201)
                        .assertThat()
                        .body(matchesJsonSchemaInClasspath("playerSchema.json"));

        id = response.extract().path("id").toString();

        /** Авторизация игрока **/

        playerAccessToken = given()
                .auth()
                .preemptive()
                .basic(CODED_TOKEN, API_PASS)
                .contentType(JSON)
                .accept(JSON)
                //.header("authorization", "Basic " + encodedKey)
                .baseUri("http://test-api.d6.dev.devcaz.com")
                .basePath("/v2")
                .body(playerAuthBody)
                .when()
                .post("/oauth2/token")
                .then()
                .statusCode(200)
                .extract().path("access_token");

        /** Запрос информации по игроку **/

        RestAssured
                .given()
                .header("authorization", "Bearer " + playerAccessToken)
                .contentType(JSON)
                .accept(JSON)
                .baseUri("http://test-api.d6.dev.devcaz.com")
                .basePath("/v2")
                .body(newPlayerBody)
                .when()
                .get("/players/" + id)
                .then()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("playerSchema.json"));

        /** Запрос информации по другому игроку **/

        RestAssured
                .given()
                .header("authorization", "Bearer " + playerAccessToken)
                .contentType(JSON)
                .accept(JSON)
                .baseUri("http://test-api.d6.dev.devcaz.com")
                .basePath("/v2")
                .body(newPlayerBody)
                .when()
                .get("/players/" + 8464)
                .then()
                .statusCode(404);
    }
}
