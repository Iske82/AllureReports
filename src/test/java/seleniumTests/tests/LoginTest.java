package seleniumTests.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import seleniumTests.pages.LoginPage;

import java.util.concurrent.TimeUnit;

import static org.testng.AssertJUnit.assertTrue;

public class LoginTest {
    WebDriver driver;
    LoginPage login;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // Use "--headless=new" for Chrome 109+
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.navigate().to("https://www.automationexercise.com/login");

        login = new LoginPage(driver);
    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testPositiveLogin() {
        login.getEmail().sendKeys("hodoy86379@pricegh.com");
        login.getPassword().sendKeys("testAutomation");
        login.getSubmit().click();
        assertTrue(login.getSpecificUser().isDisplayed());
    }

    @Test
    public void testNegativeLoginIncorrectPassword() {
        login.getEmail().sendKeys("hodoy86379@pricegh.com");
        login.getPassword().sendKeys("test");
        login.getSubmit().click();
        assertTrue(login.getIncorrectLoginMessage().isDisplayed());
    }

    @Test
    public void testNegativeLoginIncorrectEmail() {
        login.getEmail().sendKeys("hodoy86379@pricegh");
        login.getPassword().sendKeys("testAutomation");
        login.getSubmit().click();
        assertTrue(login.getIncorrectLoginMessage().isDisplayed());
    }

    @Test
    public void testNegativeLoginMissingEmail() {
        login.getPassword().sendKeys("testAutomation");
        login.getSubmit().click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Boolean isValid = (Boolean) js.executeScript("return arguments[0].checkValidity();", login.getEmail());

        if (!isValid) {
            System.out.println("Email field is invalid (triggered 'Please fill out this field.').");
        } else {
            System.out.println("Email field is valid.");
        }
    }

    @Test
    public void testNegativeLoginMissingPassword() {
        login.getEmail().sendKeys("hodoy86379@pricegh");
        login.getSubmit().click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Boolean isValid = (Boolean) js.executeScript("return arguments[0].checkValidity();", login.getPassword());

        if (!isValid) {
            System.out.println("Password field is invalid (triggered 'Please fill out this field.').");
        } else {
            System.out.println("Password field is valid.");
        }
    }

    @Test
    public void testLogout() {
        login.getEmail().sendKeys("hodoy86379@pricegh.com");
        login.getPassword().sendKeys("testAutomation");
        login.getSubmit().click();
        login.getLogout().click();
        assertTrue(login.getPassword().isDisplayed());
    }
}
