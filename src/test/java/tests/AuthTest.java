package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class AuthTest {

    WebDriver driver;
    WebDriverWait wait;
    boolean keepBrowserOpen = true;
    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
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
    @Test
    public void registerTest() {
        driver.get("https://automationexercise.com/login");

        By nameField = By.cssSelector("input[data-qa='signup-name']");
        By emailField = By.cssSelector("input[data-qa='signup-email']");
        By signupButton = By.cssSelector("button[data-qa='signup-button']");
        By titleRadioButton= By.id("uniform-id_gender1");
        By passwordField = By.cssSelector("input[id='password']");
        By dayField =By.cssSelector("select[id='days']");
        By monthField =By.cssSelector("select[id='months']");
        By yearsField =By.cssSelector("select[id='years']");
        By newsletterLabel = By.cssSelector("label[for='newsletter']");
        By optionLabel = By.cssSelector("label[for='optin']");
        By firstNameField = By.cssSelector("input[id='first_name']");
        By lastNameField = By.cssSelector("input[id='last_name']");
        By companyNameField = By.cssSelector("input[id='company']");
        By address1Field = By.cssSelector("input[id='address1']");
        By address2Field = By.cssSelector("input[id='address2']");
        By cityField = By.cssSelector("input[id='city']");
        By countrySelect = By.cssSelector("select[id='country']");
        By stateField = By.cssSelector("input[id='state']");
        By zipcodeField = By.cssSelector("input[id='zipcode']");
        By mobileNumberField = By.cssSelector("input[id='mobile_number']");
        By createAccount = By.cssSelector("button[data-qa='create-account']");

        driver.findElement(nameField).sendKeys("Ahmed");
        String email = "ahmed" +System.currentTimeMillis()+"@gmail.com";
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(signupButton).click();
        driver.findElement(titleRadioButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys("Test@1234");
        Select day =new Select(driver.findElement(dayField));
        Select month =new Select(driver.findElement(monthField));
        Select year =new Select(driver.findElement(yearsField));
        Select country= new Select(driver.findElement(countrySelect));
        day.selectByValue("25");
        month.selectByValue("9");
        year.selectByValue("2002");
        country.selectByValue("Canada");
        driver.findElement(newsletterLabel).click();
        driver.findElement(optionLabel).click();
driver.findElement(firstNameField).sendKeys("Ahmed");

        driver.findElement(lastNameField).sendKeys("Albay");
        driver.findElement(companyNameField).sendKeys("Not Work at moment");

        driver.findElement(address1Field).sendKeys("menofia");

        driver.findElement(address2Field).sendKeys("ShibienELkom");
        driver.findElement(cityField).sendKeys("shibienElkom");

        driver.findElement(stateField).sendKeys("single");
        driver.findElement(zipcodeField).sendKeys("048");
        driver.findElement(mobileNumberField).sendKeys("01063403867");



driver.findElement(createAccount).click();
        By accountCreatedTitle = By.cssSelector("h2[data-qa='account-created']");

        String actual = wait.until(ExpectedConditions.visibilityOfElementLocated(accountCreatedTitle)).getText();
        Assert.assertEquals(actual, "ACCOUNT CREATED!");
    }
    @AfterMethod
    public void tearDown() {
        if (!keepBrowserOpen) {
            driver.quit();
        }

    }
}