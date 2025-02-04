import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class responseCode {

    @Test
    public void testStatusCode() {

        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();

        int statusCode = response.getStatusCode();
        System.out.println(statusCode);

    }

// проверка редиректа на другой сайт
    @Test
    public void testRedirect() {

        Response response = RestAssured
                .given()
                .redirects()
                .follow(false) // запрос сначала наткнулся на 303, пошел по новому адресу и получил код 200
                .when()
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();

        int statusCode = response.getStatusCode();
        System.out.println(statusCode);

    }
}