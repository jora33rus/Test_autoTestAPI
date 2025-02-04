import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class testheaders {

    @Test
    public void testheaders() {

        Map<String, String> headers = new HashMap<>();// вынос в отдельную переменную
        headers.put("myHeader2", "myValue1");//для передачи заголовков
        headers.put("myHeader1", "myValue2");

        Response response = RestAssured
                .given()// определяет что будет отправлено в запросе
                .headers(headers)
                .when()
                .get("https://playground.learnqa.ru/api/show_all_headers")
                .andReturn();

       response.prettyPrint();

       Headers responseHeaders = response.getHeaders();
        System.out.println(responseHeaders);
    }
    @Test
    public void testheadersRedirect() {

        Map<String, String> headers = new HashMap<>();// вынос в отдельную переменную
        headers.put("myHeader2", "myValue1");//для передачи заголовков
        headers.put("myHeader1", "myValue2");

        Response response = RestAssured
                .given()// определяет что будет отправлено в запросе
                .redirects()
                .follow(false) //запрещаем редирект дальше
                .headers(headers)
                .when()
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();

        response.prettyPrint();

        String locationHeader = response.getHeader("Location"); // для получения одного заголовка
        System.out.println(locationHeader);
    }
}


