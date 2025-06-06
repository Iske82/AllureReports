package seleniumTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
        driver = new ChromeDriver();
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
