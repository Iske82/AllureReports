package restAssuredTests.gorest;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import restAssured.gorest.AuthorizationPage;
import restAssured.gorest.User;

import static org.hamcrest.Matchers.equalTo;

public class TestGoRest {

    @BeforeClass
    public void setUp() {
        User user = new User("John", "mytestemail@testThree.com", "male", "active");
        AuthorizationPage.createUser(user);
    }

    @Test
    public void shouldReturnCorrectUserNameAfterCreation() {
        Response res = AuthorizationPage.getUserInformation();
        res.then()
                .statusCode(201);
        String expectedName = res.jsonPath().getString("name");
        String actualName = "John";
        Assert.assertEquals(actualName, expectedName, "Name doesn't match expected value.");
    }

    @Test
    public void shouldUpdateUserEmailSuccessfully() {
        User user = new User();
        String expectedMail = "updated-mytestemail@testThree.com";
        user.setEmail(expectedMail);
        user.setName("John");
        user.setGender("male");
        user.setStatus("active");
        Response res = AuthorizationPage.createUserUpdate(user);
        String actualMail = res.jsonPath().getString("email");
        Assert.assertEquals(actualMail, expectedMail, "Updated email doesn't match expected value.");
    }

    @Test
    public void shouldCreateUserPost() {
        User user = new User();
        user.setTitle("Post title");
        user.setBody("Post body");
        Response res = AuthorizationPage.createUserPost(user);
        res.then()
                .body("title", equalTo("Post title"))
                .body("body", equalTo("Post body"))
                .log().all()
                .statusCode(202);
    }

    @Test
    public void shouldReturnUserPostInformation() {
        Response res = AuthorizationPage.getUserPostInformation();
        System.out.println(res.asString());
        String body = res.jsonPath().getString("body");
        String title = res.jsonPath().getString("title");
        System.out.println("Body of the post is " + body + " with a title " + title);
        SoftAssert softAssert= new SoftAssert();
        softAssert.assertEquals(res.jsonPath().getString("[0].title"), "Post title", "Expected title doesn't match the created one");
        softAssert.assertEquals(res.jsonPath().getString("[0].body"), "Post body", "Expected body doesn't match the created one");
        softAssert.assertAll();
    }

    @Test
    public void shouldCreateUserTodo() {
        User user = new User();
        user.setStatus("pending");
        user.setTitle("To do title");
        AuthorizationPage.createUserTodos(user);
    }

    @Test
    public void shouldValidateUserTodoInformation() {
        String actualTitle = AuthorizationPage.getUserTodoInformation().jsonPath().getString("[0].title");
        Assert.assertEquals(actualTitle, "To do title", "Title doesn't match expected value.");
    }

    @Test(dependsOnMethods = "shouldCreateUserPost")
    public void shouldCreateUserPostComment() {
        User user = new User();
        user.setBody("My body");
        user.setEmail("MyEmail@MyEmail.com");
        user.setName("myName");
        AuthorizationPage.createUserPostComments(user);
    }

    @AfterClass
    public void tearDown() {
        AuthorizationPage.deleteUser();
    }
}
