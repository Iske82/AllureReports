package restAssuredTests.regres;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import restAssured.reqres.User;
import restAssured.reqres.UsersMethods;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

@Epic("Reqres API")
@Feature("Users Endpoint")
public class TestUsersMethods {

    Response responseUsersPageOne = UsersMethods.getUsersPage(1);

    @Test(description = "Verify status code for users page 1")
    @Severity(SeverityLevel.CRITICAL)
    @Story("GET /api/users?page=1")
    public void shouldReturnValidStatusForSpecificUserPage() {
        responseUsersPageOne.then().statusCode(200);
    }

    @Test(description = "Check if page number is correct in response")
    @Severity(SeverityLevel.NORMAL)
    @Story("GET /api/users?page=1")
    public void shouldReturnCorrectUsersPage() {
        int pageNumber = responseUsersPageOne.path("page");
        Assert.assertEquals(pageNumber, 1, "page number doesn't match the search criteria");
    }

    @Test(description = "Validate email format for all users on page")
    @Severity(SeverityLevel.MINOR)
    @Story("GET /api/users?page=1")
    public void shouldValidateMailHasCorrectFormat() {
        List<String> eMails = responseUsersPageOne.jsonPath().getList("data.email");
        for (String email : eMails) {
            boolean isValid = email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
            Assert.assertTrue(isValid, "Invalid email format: " + email);
        }
    }

    @Test(description = "Verify expected top-level JSON keys exist")
    @Severity(SeverityLevel.TRIVIAL)
    @Story("GET /api/users?page=1")
    public void shouldVerifyJSONContainsExpectedKeys() {
        responseUsersPageOne.then()
                .body("$", hasKey("page"))
                .body("$", hasKey("per_page"))
                .body("$", hasKey("total"))
                .body("$", hasKey("total_pages"))
                .body("$", hasKey("data"))
                .body("$", hasKey("support"));
    }

    @Test(description = "Check that list of users has expected size")
    @Severity(SeverityLevel.NORMAL)
    @Story("GET /api/users")
    public void shouldReturnListOfUsers() {
        Map<String, Integer> summary = UsersMethods.getAllUsers();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(summary.get("Total Pages") >= 2, "Number of pages is not as expected");
        softAssert.assertTrue(summary.get("Total Records") >= 12, "Number of users is not as expected");
        softAssert.assertAll();
    }

    @Test(description = "Get a single existing user by ID")
    @Severity(SeverityLevel.CRITICAL)
    @Story("GET /api/users/1")
    public void shouldReturnExistingSingleUser() {
        Response res = UsersMethods.getSingleUser(1);
        res.then().statusCode(200);
        Assert.assertEquals(res.jsonPath().getInt("data.id"), 1, "First user isn't returned");
    }

    @Test(description = "Attempt to retrieve a non-existing user")
    @Severity(SeverityLevel.NORMAL)
    @Story("GET /api/users/23")
    public void shouldReturnNonExistingSingleUser() {
        Response res = UsersMethods.getSingleUser(23);
        res.then().statusCode(404);
    }

    @Test(description = "Create a new user successfully")
    @Severity(SeverityLevel.CRITICAL)
    @Story("POST /api/users")
    public void shouldCreateUser() {
        Response res = UsersMethods.createUser();
        String newUser = res.path("name");
        res.then().statusCode(201);
        Assert.assertEquals(newUser, "John Smith", "User name isn't the same as created one");
    }

    @Test(description = "Fully update a user with new name and job")
    @Severity(SeverityLevel.NORMAL)
    @Story("PUT /api/users/{id}")
    public void shouldUpdateUser() {
        User user = new User();
        user.setName("Sir John Smith");
        user.setJob("Senior QA Automation Engineer");
        Response res = UsersMethods.updateUser(user);
        res.then().statusCode(200);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(res.jsonPath().getString("name"), "Sir John Smith");
        softAssert.assertEquals(res.jsonPath().getString("job"), "Senior QA Automation Engineer");
        softAssert.assertAll();
    }

    @Test(description = "Partially update a user's job title")
    @Severity(SeverityLevel.NORMAL)
    @Story("PATCH /api/users/{id}")
    public void shouldPartiallyUpdateUser() {
        User user = new User();
        user.setJob("Senior QA Automation Engineer, level Pro");
        Response res = UsersMethods.updateUserPartially(user);
        res.then().statusCode(200);
        Assert.assertEquals(res.jsonPath().getString("job"), "Senior QA Automation Engineer, level Pro");
    }

    @Test(description = "Register a user with correct credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Story("POST /api/register")
    public void shouldRegisterUserSuccessfully() {
        User user = new User();
        user.setEmail("eve.holt@reqres.in");
        user.setPassword("pistol");
        Response res = UsersMethods.registerUser(user);
        res.then().statusCode(200);
        Assert.assertEquals(res.jsonPath().getString("token"), "QpwL5tke4Pnpja7X4");
    }

    @Test(description = "Fail to register a user without password")
    @Severity(SeverityLevel.NORMAL)
    @Story("POST /api/register")
    public void shouldRegisterUserUnsuccessfully() {
        User user = new User();
        user.setEmail("eve.holt@reqres.in");
        Response res = UsersMethods.registerUser(user);
        res.then().statusCode(400)
                .body("error", equalTo("Missing password"));
    }

    @Test(description = "Request a delayed user list response")
    @Severity(SeverityLevel.MINOR)
    @Story("GET /api/users?delay=3")
    public void shouldGetDelayedResponse() {
        Response res = UsersMethods.delayedResponse();
        res.then().statusCode(200);
    }

    @Test(description = "Log in a user successfully")
    @Severity(SeverityLevel.CRITICAL)
    @Story("POST /api/login")
    public void shouldLoginSuccessfully() {
        User user = new User();
        user.setEmail("eve.holt@reqres.in");
        user.setPassword("cityslicka");
        Response res = UsersMethods.login(user);
        res.then().statusCode(200);
    }

    @Test(description = "Fail login when password is missing")
    @Severity(SeverityLevel.NORMAL)
    @Story("POST /api/login")
    public void shouldLoginUnsuccessfully() {
        User user = new User();
        user.setEmail("eve.holt@reqres.in");
        Response res = UsersMethods.login(user);
        res.then().statusCode(400);
    }

    @Test(description = "Delete a user successfully")
    @Severity(SeverityLevel.NORMAL)
    @Story("DELETE /api/users/{id}")
    public void shouldDeleteUser() {
        Response res = UsersMethods.deleteUser();
        res.then().statusCode(204);
    }
}
