package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Objects;

public class SmokeTest {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void homePageOpens() {
        driver.get("https://automationexercise.com");
        Assert.assertTrue(Objects.requireNonNull(driver.getTitle()).contains("Automation Exercise"));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}