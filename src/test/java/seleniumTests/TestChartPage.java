package seleniumTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import selenium.CartPage;

import java.util.concurrent.TimeUnit;

import static org.testng.AssertJUnit.assertTrue;


public class TestChartPage {
    WebDriver driver;
    CartPage cart;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // Use "--headless=new" for Chrome 109+
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.navigate().to("https://www.automationexercise.com/view_cart");
        cart = new CartPage(driver);
    }
    @Test
    public void testEmptyCart(){
        assertTrue(cart.getEmptyCart().isDisplayed());
    }
    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
