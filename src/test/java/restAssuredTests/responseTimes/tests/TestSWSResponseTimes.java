package restAssuredTests.responseTimes.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("SWS API")
public class TestSWSResponseTimes {

    @Test(description = "Measure response time on Test environment with link resolving")
    @Severity(SeverityLevel.CRITICAL)
    void testFunctestWithLInkResolving() {
        Response res = given()
                .when()
                .get("https://sws-internal.functest.cwc-ota.awssdu.nl/c-NDFR-BWBR0002672-14a/c-NDFR-BWBR0002672-14a.html")
                .then()
                .extract().response();
        long time = res.time();
        res.then().statusCode(200);
        System.out.println("Response time on Test environment with link resolving is " + time + " ms");
        Assert.assertTrue(time < 10000, "response time is too slow! Expected < 10000 ms but was: " + time + " ms");
        Allure.step("Response time: " + time + " ms");
    }

    @Test(description = "Measure response time on Test environment without link resolving")
    @Severity(SeverityLevel.CRITICAL)
    void testFunctestWithoutLInkResolving() {
        Response res = given()
                .when()
                .get("https://sws-internal.functest.cwc-ota.awssdu.nl/c-NDFR-BWBR0002672-14a/c-NDFR-BWBR0002672-14a.html?resolve-links=false")
                .then()
                .extract().response();
        long time = res.time();
        res.then().statusCode(200);
        System.out.println("Response time on Test environment without link resolving is " + time + " ms");
        Assert.assertTrue(time < 10000, "response time is too slow! Expected < 10000 ms but was: " + time + " ms");
        Allure.step("Response time: " + time + " ms");
    }
    @Test(description = "Measure response time of TOC file on Test environment with link resolving")
    @Severity(SeverityLevel.CRITICAL)
    void testTOCFunctestWithLInkResolving() {
        Response res = given()
                .when()
                .get("https://sws-internal.functest.cwc-ota.awssdu.nl/c-NDFR-BWBR0002672-14a/c-NDFR-BWBR0002672-14a-toc.html")
                .then()
                .extract().response();
        long time = res.time();
        res.then().statusCode(200);
        System.out.println("Response TOC time on Test environment with link resolving is " + time + " ms");
        Assert.assertTrue(time < 10000, "response TOC time is too slow! Expected < 10000 ms but was: " + time + " ms");
        Allure.step("Response time: " + time + " ms");
    }

    @Test(description = "Measure response time on Production environment with link resolving")
    @Severity(SeverityLevel.CRITICAL)
    void testProductionWithLInkResolving() {
        Response res = given()
                .when()
                .get("http://sws.sdu.nl/c-NDFR-BWBR0002672-14a/c-NDFR-BWBR0002672-14a.html")
                .then()
                .extract().response();
        long time = res.time();
        res.then().statusCode(200);
        System.out.println("Response time on Production environment with link resolving is " + time + " ms");
        Assert.assertTrue(time < 10000, "response time is too slow! Expected < 10000 ms but was: " + time + " ms");
        Allure.step("Response time: " + time + " ms");
    }

    @Test(description = "Measure response time on Production environment without link resolving")
    @Severity(SeverityLevel.CRITICAL)
    void testProductionsWithoutLInkResolving() {
        Response res = given()
                .when()
                .get("http://sws.sdu.nl/c-NDFR-BWBR0002672-14a/c-NDFR-BWBR0002672-14a.html?resolve-links=false")
                .then()
                .extract().response();
        long time = res.time();
        System.out.println(res.statusCode());
        System.out.println("Response time on Production environment without link resolving is " + time + " ms");
        Assert.assertTrue(time < 10000, "response time is too slow! Expected < 10000 ms but was: " + time + " ms");
        Allure.step("Response time: " + time + " ms");
    }
}
