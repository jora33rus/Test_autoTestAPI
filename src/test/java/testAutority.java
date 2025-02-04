import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class testAutority {

    @Test
public void testAutority(){
        Map<String, String> authData = new HashMap<>();
        authData.put("email","vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        Map<String, String> cookies =responseGetAuth.getCookies();
        Headers headers = responseGetAuth.getHeaders();
        int userIdOnAuth = responseGetAuth.jsonPath().getInt("user_id");

        assertEquals(200, responseGetAuth.statusCode(),"Непонятный статус код");
        assertTrue(cookies.containsKey("auth_sid"), "Ответ не содержит 'auth_sid' куки" );
        assertTrue(headers.hasHeaderWithName("x-csrf-token"), "Ответ не содержит заголовка 'x-csrf-token'");
        assertTrue(responseGetAuth.jsonPath().getInt("user_id") > 0, "id пользователя должно быть больше 0" );

        JsonPath responseChekAuth = RestAssured
                .given()
                .header("x-csrf-token", responseGetAuth.getHeader("x-csrf-token"))
                .cookie("auth_sid", responseGetAuth.getCookie("auth_sid"))
                .get("https://playground.learnqa.ru/api/user/auth")
                .jsonPath();

int userIdonChek = responseChekAuth.getInt("user_id");
assertTrue(userIdonChek > 0, "Unexpected user id:" + userIdonChek);

assertEquals(
        userIdOnAuth,
        userIdonChek,
        " user id from auth request is not equal to user_id from check request"
);

}


}
