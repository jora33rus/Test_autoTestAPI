import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class HelloWorldTest {




    @Test
    public void testRestAssured(){
        Map<String, String> params = new HashMap<>();// вынос в отдельную переменную
        params.put("name", "John");// вынос в отдельную переменную. тут можно указать много параметров и значений
        Response response = RestAssured
                .given()// определяет что будет отправлено в запросе
                .queryParams (params)
                .get("https://playground.learnqa.ru/api/hello")
                .andReturn();
        response.prettyPrint();

    }

}
/*
.given()// определяет что будет отправлено в запросе
        .when // с каким методом и на какой эндпоинт будет отправлен запрос
        .then // как проверяется пришедший ответ
 */