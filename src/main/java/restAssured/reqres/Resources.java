package restAssured.reqres;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Resources {
    static RequestSpecification reqSpec = new RequestSpecBuilder()
            .setBaseUri("https://reqres.in/api/")
            .addHeader("x-api-key", "reqres-free-v1")
            .build();

    public static Response getDefaultResourcesPage() {
        return given()
                .spec(reqSpec)
                .when()
                .get("unknown")
                .then()
                .statusCode(200)
                .extract().response();
    }

    public static Response getSingleResourcesPage(int pageNumber) {
        return given()
                .spec(reqSpec)
                .when()
                .get("unknown/" + pageNumber)
                .then()
                .extract().response();
    }
}

