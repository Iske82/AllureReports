package seleniumTests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    private By emailLocator = By.name("email");
    private By passwordLocator = By.name("password");
    private By submitLocator = By.cssSelector("[type='submit']");
    private By findUserLocator = By.xpath("//b[text()='Isidor']");
    private By findIncorrectMessageLocator = By.xpath("//p[text()='Your email or password is incorrect!']");
    private By findLogoutLocator = By.linkText("Logout");

    public By getEmailLocator() {
        return emailLocator;
    }

    public WebElement getEmail() {
        WebElement email = driver.findElement(emailLocator);
        email.clear();
        return email;
    }

    public WebElement getPassword() {
        WebElement password = driver.findElement(passwordLocator);
        password.clear();
        return password;
    }

    public WebElement getSubmit() {
        WebElement submit = driver.findElement(submitLocator);
        return submit;
    }

    public WebElement getSpecificUser() {
        WebElement user = driver.findElement(findUserLocator);
        return user;
    }

    public WebElement getIncorrectLoginMessage() {
        WebElement message = driver.findElement(findIncorrectMessageLocator);
        return message;
    }
    public WebElement getLogout(){
        WebElement logout = driver.findElement(findLogoutLocator);
        return logout;
    }
}