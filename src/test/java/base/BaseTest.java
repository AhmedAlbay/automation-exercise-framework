package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {

    // The browser itself. One new instance is created before every test.
    protected WebDriver driver;

    // Smart wait: polls the page until a condition is true (max 30 seconds).
    protected WebDriverWait wait;

    // true  -> browser stays open after the test (useful while learning/debugging)
    // false -> browser closes after every test (REQUIRED before commit / CI)
    protected boolean keepBrowserOpen = false;

    @BeforeMethod
    public void setUp() {
        // Runs before EVERY @Test: open a fresh Chrome so tests stay independent.
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @AfterMethod
    public void tearDown() {
        // Runs after EVERY @Test (even if the test failed).
        if (!keepBrowserOpen && driver != null) {
            driver.quit();
        }
    }
}
