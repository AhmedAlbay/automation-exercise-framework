package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class AuthTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void invalidLoginShowsError() {
        driver.get("https://automationexercise.com/login");

        By emailField = By.cssSelector("input[data-qa='login-email']");
        By passwordField = By.cssSelector("input[data-qa='login-password']");
        By loginButton = By.cssSelector("button[data-qa='login-button']");
        By errorMessage = By.xpath("//p[contains(text(),'incorrect')]");

        driver.findElement(emailField).sendKeys("Ahmed@gmail.com");
        driver.findElement(passwordField).sendKeys("123456");
        driver.findElement(loginButton).click();

        String actual = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
        Assert.assertEquals(actual, "Your email or password is incorrect!");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}