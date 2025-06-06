package seleniumTests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import selenium.HomePage;

public class TestHomePage {
    WebDriver driver;
    HomePage homePage;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.navigate().to("https://www.automationexercise.com/");

        homePage = new HomePage(driver);
    }

    @Test
    public void testTitle() {
        String title = homePage.getTitle();
        assertEquals(title, "Automation Exercise");
    }

    @Test
    public void testNavBarIsPresent() {
        assertTrue(homePage.getNavBar().isDisplayed());
    }

    @Test
    public void testHeaderIsPresent() {
        assertTrue(homePage.getHeader().isDisplayed());
    }

    @Test
    public void testCategory() {
        assertTrue(homePage.getCategory().isDisplayed());
    }

    @Test
    public void testMenuItems() {

        String[][] expected = {
                {"Home", "/"},
                {"Products", "/products"},
                {"Cart", "/view_cart"},
                {"Signup / Login", "/login"},
                {"Test Cases", "/test_cases"},
                {"API Testing", "/api_list"},
                {"Video Tutorials", "https://www.youtube.com/c/AutomationExercise"},
                {"Contact us", "/contact_us"}
        };

        for (int i = 0; i < expected.length; i++) {
            String actualText = homePage.getMenuLinkText(i);
            String actualHref = homePage.getMenuLinkHref(i);


            assertTrue(actualText.contains(expected[i][0]), "Text mismatch at index " + i);
            assertTrue(actualHref.contains(expected[i][1]), "Href mismatch at index " + i);
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
