package restAssured.gorest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;


public class AuthorizationPage {
    private static final String token = ConfigLoader.getApiToken();
    private static String id;
    private static int postId;
    static RequestSpecification reqSpec = new RequestSpecBuilder()
            .setBaseUri("https://gorest.co.in/public/v2/")
            .setContentType("application/json")
            .build();

    public static void createUser(User user) {
        Response res = given()
                .spec(reqSpec)
                .header("Authorization", token)
                .when()
                .body(user)
                .post("users")
                .then()
                .extract().response();
        id = res.jsonPath().getString("id");
        res.then()
                .statusCode(201);
        System.out.println("User id is " + id);
    }

    public static Response getUserInformation() {
        return given()
                .spec(reqSpec)
                .header("Authorization", token)
                .when()
                .get("users/" + id)
                .then()
                .extract().response();
    }

    public static Response createUserUpdate(User user) {
        return given()
                .spec(reqSpec)
                .header("Authorization", token)
                .body(user)
                .when()
                .put("users/" + id)
                .then()
                .extract().response();
    }
    public static Response createUserUpdateWithPatch(User user){
        return given()
                .spec(reqSpec)
                .header("Authorization", token)
                .body(user)
                .when()
                .patch("users/" + id)
                .then()
                .extract().response();
    }

    public static Response createUserPost(User user) {
        Response res = given()
                .spec(reqSpec)
                .header("Authorization", token)
                .body(user)
                .when()
                .post("users/" + id + "/posts")
                .then()
                .extract().response();
        postId = res.jsonPath().getInt("id");
        return res;
    }

    public static Response getUserPostInformation() {
        return given()
                .spec(reqSpec)
                .header("Authorization", token)
                .when()
                .get("users/" + id + "/posts")
                .then()
                .extract().response();
    }

    public static void createUserPostComments(User user) {
        given()
                .spec(reqSpec)
                .header("Authorization", token)
                .body(user)
                .when()
                .post("posts/" + postId + "/comments")
                .then()
                .log().all()
                .statusCode(201);
    }

    public static void createUserTodos(User user) {
        Response res = given()
                .spec(reqSpec)
                .header("Authorization", token)
                .body(user)
                .when()
                .post("users/" + id + "/todos")
                .then()
                .extract().response();
        res.then()
                .statusCode(201);
    }

    public static Response getUserTodoInformation() {
        return given()
                .spec(reqSpec)
                .header("Authorization", token)
                .when()
                .get("users/" + id + "/todos")
                .then()
                .extract().response();
    }

    public static void deleteUser() {
        given()
                .spec(reqSpec)
                .header("Authorization", token)
                .when()
                .delete("users/" + id)
                .then()
                .statusCode(204);
    }
}
