import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class testCookie {
    @Test
    public void testCookie() {

        Map<String, String> data = new HashMap<>();// вынос в отдельную переменную
        data.put("login", "secret_login2");//для передачи заголовков
        data.put("password", "secret_pass2");

        Response response = RestAssured
                .given()// определяет что будет отправлено в запросе
                .body(data)
                .when()
                .post("https://playground.learnqa.ru/api/get_auth_cookie")
                .andReturn();

        System.out.println("\nPretty text:");
        response.prettyPrint();

        System.out.println("\nHeaders:");
       Headers responseHeaders = response.getHeaders();
        System.out.println(responseHeaders);

        System.out.println("\nCookies:");
        Map<String, String> responseCookies = response.getCookies();
        System.out.println(responseCookies);


    }

   ///получение авторизационной куки
    @Test
    public void testCookie2() {

        Map<String, String> data = new HashMap<>();// вынос в отдельную переменную
        data.put("login", "secret_login");//для передачи заголовков
        data.put("password", "secret_pass");

        Response response = RestAssured
                .given()// определяет что будет отправлено в запросе
                .body(data)
                .when()
                .post("https://playground.learnqa.ru/api/get_auth_cookie")
                .andReturn();

       String responseCookie = response.getCookie("auth_cookie");
        System.out.println(responseCookie);


    }

    //передача куки в последующий запрос

    @Test
    public void testForGetCookie() {
        Map<String, String> data = new HashMap<>();// вынос в отдельную переменную
        data.put("login", "secret_login");//для передачи заголовков
        data.put("password", "secret_pass");

        Response responseForGet = RestAssured //1.присваиваем переменной responseForGet ответ от первого запроса
                .given()// определяет что будет отправлено в запросе
                .body(data) // 2.шлем пост запрос положив данные в из переменной data
                .when()
                .post("https://playground.learnqa.ru/api/get_auth_cookie")
                .andReturn();

        String responseCookie = responseForGet.getCookie("auth_cookie");//3.из переменной responseForGet получаем куки
        // с названием auth_cookie и кладем в переменную responseCookie
        //подготавливаем второй запрос, создаем хэш мэп для авторизованной куки
        Map<String, String> cookies = new HashMap<>();
        if(responseCookie != null){ //если куки не = 0, то добавляем ее
            cookies.put("auth_cookie", responseCookie);
        }
        Response responseForCheck = RestAssured // делаем сам запрос, а результат кладем в переменную responseForCheck
                .given()
                .body(data)
                .cookies(cookies)
                .when()
                .post("https://playground.learnqa.ru/api/get_auth_cookie")
                .andReturn();

        responseForCheck.print(); //печатаем текст второго ответа

    }

}
