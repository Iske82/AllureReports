package restAssuredTests.regres;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import restAssured.reqres.Resources;

import java.util.List;

import static org.hamcrest.Matchers.hasKey;

@Epic("Reqres API")
@Feature("Resource Endpoint")
public class TestResources {

    Response responseResourcesPage = Resources.getDefaultResourcesPage();

    @Test(description = "Verify status code for default resource page")
    @Severity(SeverityLevel.CRITICAL)
    @Story("GET /api/resources?page=1")
    @Description("Checks that the status code is 200 for the default resource page.")
    public void shouldReturnValidStatusForDefaultResourcePage() {
        responseResourcesPage.then()
                .statusCode(200);
    }

    @Test(description = "Verify correct resource page number is returned")
    @Severity(SeverityLevel.NORMAL)
    @Story("GET /api/resources?page=1")
    public void shouldReturnCorrectResourcePage() {
        int pageNumber = responseResourcesPage.path("page");
        Assert.assertEquals(pageNumber, 1, "page number is not correct");
    }

    @Test(description = "Validate per page, total, and total_pages values")
    @Severity(SeverityLevel.NORMAL)
    @Story("GET /api/resources?page=1")
    public void shouldReturnCorrectResourcePageValues() {
        int perPage = responseResourcesPage.path("per_page");
        int totalRecords = responseResourcesPage.path("total");
        int totalPages = responseResourcesPage.path("total_pages");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(perPage, 6, "Per page number is not correct");
        softAssert.assertEquals(totalRecords, 12, "Total records number is not correct");
        softAssert.assertEquals(totalPages, 2, "Actual pages number is not correct");

        softAssert.assertAll();
    }

    @Test(description = "Verify JSON contains expected keys")
    @Severity(SeverityLevel.MINOR)
    @Story("GET /api/resources?page=1")
    public void shouldVerifyJSONContainsExpectedKeys() {
        responseResourcesPage.then()
                .body("$", hasKey("page"))
                .body("$", hasKey("per_page"))
                .body("$", hasKey("total"))
                .body("$", hasKey("total_pages"));
    }

    @Test(description = "Ensure nested JSON contains a list of IDs")
    @Severity(SeverityLevel.TRIVIAL)
    @Story("GET /api/resources?page=1")
    public void shouldVerifyNestedJSONNotEmpty() {
        List<Integer> ids = responseResourcesPage.jsonPath().getList("data.id");
        Assert.assertTrue(ids.size() >= 6);
    }

    @Test(description = "Return single resource by ID")
    @Severity(SeverityLevel.CRITICAL)
    @Story("GET /api/resources/1")
    public void shouldReturnSingleResourcesPage() {
        Response res = Resources.getSingleResourcesPage(1);
        int pageId = res.path("data.id");
        Assert.assertEquals(pageId, 1, "Page number in single Resource isn't correct");
    }

    @Test(description = "Handle 404 for non-existing resource")
    @Severity(SeverityLevel.CRITICAL)
    @Story("GET /api/resources/23")
    public void shouldReturnNonExistingResource(){
        Response res = Resources.getSingleResourcesPage(23);
        res.then().statusCode(404);
    }
}
