package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// Page Object for the registration flow:
//   /login (New User Signup)  ->  /signup (Enter Account Information)  ->  /account_created
public class SignupPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ---- Locators: signup form on /login ----
    private final By nameField = By.cssSelector("input[data-qa='signup-name']");
    private final By emailField = By.cssSelector("input[data-qa='signup-email']");
    private final By signupButton = By.cssSelector("button[data-qa='signup-button']");

    // ---- Locators: account information page ----
    private final By titleRadioButton = By.id("uniform-id_gender1");
    private final By passwordField = By.cssSelector("input[id='password']");
    private final By dayField = By.cssSelector("select[id='days']");
    private final By monthField = By.cssSelector("select[id='months']");
    private final By yearsField = By.cssSelector("select[id='years']");
    private final By newsletterLabel = By.cssSelector("label[for='newsletter']");
    private final By optionLabel = By.cssSelector("label[for='optin']");
    private final By firstNameField = By.cssSelector("input[id='first_name']");
    private final By lastNameField = By.cssSelector("input[id='last_name']");
    private final By companyNameField = By.cssSelector("input[id='company']");
    private final By address1Field = By.cssSelector("input[id='address1']");
    private final By address2Field = By.cssSelector("input[id='address2']");
    private final By cityField = By.cssSelector("input[id='city']");
    private final By countrySelect = By.cssSelector("select[id='country']");
    private final By stateField = By.cssSelector("input[id='state']");
    private final By zipcodeField = By.cssSelector("input[id='zipcode']");
    private final By mobileNumberField = By.cssSelector("input[id='mobile_number']");
    private final By createAccount = By.cssSelector("button[data-qa='create-account']");

    // ---- Locators: result page ----
    private final By accountCreatedTitle = By.cssSelector("h2[data-qa='account-created']");
    private final By continueButton = By.cssSelector("a[data-qa='continue-button']");

    public SignupPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // Step 1: type name + email on /login and press Signup
    public void signup(String name, String email) {
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(signupButton).click();
    }

    // Step 2: fill the REQUIRED fields only and press Create Account.
    // Only the password is a parameter for now; the rest is fixed test data.
    public void fillAccountInformation(String password) {
        // A NEW page opened -> wait for the password field first
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
        driver.findElement(firstNameField).sendKeys("Ahmed");
        driver.findElement(lastNameField).sendKeys("Albay");
        driver.findElement(address1Field).sendKeys("Menofia");
        driver.findElement(stateField).sendKeys("Menofia");
        driver.findElement(cityField).sendKeys("Shibin El Kom");
        driver.findElement(zipcodeField).sendKeys("32511");
        driver.findElement(mobileNumberField).sendKeys("01000000000");
        driver.findElement(createAccount).click();
    }

    // Step 3: wait for the success title and return its text (the Assert stays in the test)
    public String getAccountCreatedTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(accountCreatedTitle)).getText();
    }

    // Step 4: press Continue (this logs the user in)
    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }
}