package restAssuredTests.regres.main;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UsersMethods {
    static RequestSpecification reqSpec = new RequestSpecBuilder()
            .setBaseUri("https://reqres.in/api/")
            .addHeader("x-api-key", "reqres-free-v1")
            .build();

    public static Response getUsersPage(int usersPage) {
        return given()
                .spec(reqSpec)
                .queryParam("page", usersPage)
                .when()
                .get("users")
                .then()
                .extract().response();
    }

    public static Map<String, Integer> getAllUsers() {
        int totalRecords = 0;
        Response res = given()
                .spec(reqSpec)
                .queryParam("page", 1)
                .when()
                .get("users")
                .then()
                .extract().response();
        int totalPages = res.path("total_pages");
        for (int page = 1; page <= totalPages; page++) {
            Response resComplete = given()
                    .spec(reqSpec)
                    .queryParam("page", page)
                    .when()
                    .get("users")
                    .then()
                    .extract().response();
            int pageDataSize = resComplete.path("data.size()");
            totalRecords += pageDataSize;
        }
        Map<String, Integer> result = new HashMap<>();
        result.put("Total Pages", totalPages);
        result.put("Total Records", totalRecords);
        return result;
    }

    public static Response getSingleUser(int userId) {
        return  given()
                .spec(reqSpec)
                .when()
                .get("users/" + userId)
                .then()
                .extract().response();
    }

    public static Response createUser() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "John Smith");
        data.put("job", "QA Engineer");
        return given()
                .spec(reqSpec)
                .body(data)
                .header("Content-Type", "application/json")
                .when()
                .post("users")
                .then()
                .extract().response();
    }

    public static Response updateUser(User user) {
        return given()
                .spec(reqSpec)
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .put("users/1")
                .then()
                .extract().response();
    }

    public static Response updateUserPartially(User user) {
        return given()
                .spec(reqSpec)
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .put("users/1")
                .then()
                .extract().response();
    }
    public static Response registerUser(User user){
        return given()
                .spec(reqSpec)
                .body(user)
                .header("Content-Type", "application/json")
                .when()
                .post("register")
                .then()
                .extract().response();
    }

    public static Response delayedResponse() {
        return given()
                /*.config(RestAssuredConfig.config().httpClient(
                        HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", 10000)
                                .setParam("http.socket.timeout", 10000)
                                .setParam("http.connection-manager.timeout", 10000L)
                ))*/
                .spec(reqSpec)
                .queryParam("delay", 15)
                .when()
                .get("users")
                .then()
                .extract().response();
    }
    public static Response login(User user){
        return given()
                .spec(reqSpec)
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .post("login")
                .then()
                .extract().response();
    }

    public static Response deleteUser() {
        return given()
                .spec(reqSpec)
                .when()
                .delete("users/2")
                .then()
                .extract().response();
    }
}
