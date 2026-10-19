package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// Page Object for the login form on https://automationexercise.com/login
// It knows HOW to talk to the page (locators + actions). It does NOT decide pass/fail.
public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators (kept in one place: if the site changes, we fix them here only)
    private final By emailField = By.cssSelector("input[data-qa='login-email']");
    private final By passwordField = By.cssSelector("input[data-qa='login-password']");
    private final By loginButton = By.cssSelector("button[data-qa='login-button']");
    private final By errorMessage = By.xpath("//p[contains(text(),'incorrect')]");

    // The test passes its driver here (composition: the test "has a" LoginPage)
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // One user action: type email, type password, click Login
    public void login(String email, String password) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    // Waits for the error message and returns its text (the Assert stays in the test)
    public String getTextErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }
}