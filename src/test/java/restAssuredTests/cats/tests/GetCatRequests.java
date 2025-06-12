package restAssuredTests.cats.tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

public class GetCatRequests {
    @Test
    public void getTenResults(){
        Response res = given()
                .baseUri("https://api.thecatapi.com")
                .basePath("v1/images/search")
                .param("limit", 10)
                .when()
                .get();
        assertTrue(res.statusCode()==200);
    }

    @Test
    public void getBengalImages(){
        Response res = given()
                .when()
                .header("x-api-key", "live_fSy1TZADYswR1fOYMVb0clf5OrxUJYHIl0wGDkL4xAENQSUgEOIbWP7zepbie5wV")
                .get("https://api.thecatapi.com/v1/images/search?limit=10&breed_ids=beng");
      assertTrue(res.statusCode() == 200);
    }
}
