package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class AuthTest extends BaseTest {

    @Test
    public void invalidLoginShowsError() {
        driver.get("https://automationexercise.com/login");
        LoginPage loginPage= new LoginPage(driver);
        loginPage.login("Ahmed@gmail.com" , "A@fsg5321");
        Assert.assertEquals(loginPage.getTextErrorMessage(), "Your email or password is incorrect!");
    }

    // ------------------------------------------------------------------
    // TC-01 + TC-07: Register a new user (full form), then delete the account
    // ------------------------------------------------------------------

    @Test
    public void registerTest() {
        driver.get("https://automationexercise.com/login");

        // ---- Locators: signup page ----
        By nameField = By.cssSelector("input[data-qa='signup-name']");
        By emailField = By.cssSelector("input[data-qa='signup-email']");
        By signupButton = By.cssSelector("button[data-qa='signup-button']");

        // ---- Locators: account information page ----
        By titleRadioButton = By.id("uniform-id_gender1");
        By passwordField = By.cssSelector("input[id='password']");
        By dayField = By.cssSelector("select[id='days']");
        By monthField = By.cssSelector("select[id='months']");
        By yearsField = By.cssSelector("select[id='years']");
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

        // ---- Locators: result pages ----
        By accountCreatedTitle = By.cssSelector("h2[data-qa='account-created']");
        By continueButton = By.cssSelector("a[data-qa='continue-button']");
        By deleteAccount = By.xpath("//a[@href='/delete_account']");
        By accountDeletedTitle = By.cssSelector("h2[data-qa='account-deleted']");

        // Step 1: signup page. The email must be unique on every run,
        // otherwise the site answers "Email Address already exist!"
        String email = "ahmed" + System.currentTimeMillis() + "@gmail.com";
        driver.findElement(nameField).sendKeys("Ahmed");
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(signupButton).click();

        // Step 2: a NEW page opened -> wait for a known element (password) before touching anything else
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys("Test@1234");
        driver.findElement(titleRadioButton).click(); // radio button: just click it

        // Dropdowns (<select>) must be handled with the Select class
        Select day = new Select(driver.findElement(dayField));
        Select month = new Select(driver.findElement(monthField));
        Select year = new Select(driver.findElement(yearsField));
        Select country = new Select(driver.findElement(countrySelect));
        day.selectByValue("25");
        month.selectByValue("9");
        year.selectByValue("2002");
        country.selectByValue("Canada");

        // Checkboxes (clicked through their labels)
        driver.findElement(newsletterLabel).click();
        driver.findElement(optionLabel).click();

        // Text fields
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

        // Step 3: verify the account was created
        String actual = wait.until(ExpectedConditions.visibilityOfElementLocated(accountCreatedTitle)).getText();
        Assert.assertEquals(actual, "ACCOUNT CREATED!");

        // Step 4: Continue (logs us in) -> Delete Account (cleanup) -> verify deletion
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(deleteAccount)).click();

        String actualDelete = wait.until(ExpectedConditions.visibilityOfElementLocated(accountDeletedTitle)).getText();
        Assert.assertEquals(actualDelete, "ACCOUNT DELETED!");
    }

    // ------------------------------------------------------------------
    // TC-03 + TC-06: Login with a valid account, then logout
    // The test creates its own account, so it does not depend on other tests.
    // ------------------------------------------------------------------

    @Test
    public void loginAndLogoutTest() {
        // Unique email per run + a fixed password
        String email = "ahmed" + System.currentTimeMillis() + "@gmail.com";
        String password = "Test@1234";
        System.out.println(email);
        // Locators used by this test
        By logoutLink = By.xpath("//a[@href='/logout']");
        By loggedInAs = By.xpath("//a[contains(text(),'Logged in as')]");
        By loginEmail = By.cssSelector("input[data-qa='login-email']");
        By loginPassword = By.cssSelector("input[data-qa='login-password']");
        By loginButton = By.cssSelector("button[data-qa='login-button']");
        By deleteAccount = By.xpath("//a[@href='/delete_account']");
        By accountDeletedTitle = By.cssSelector("h2[data-qa='account-deleted']");

        // 1) Test data setup: create a new account (we end up logged in)
        registerNewUser(email, password);
        System.out.println(email);
        // 2) TC-06 Logout: click Logout and verify we are back on the login page
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginEmail));
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"), "Should be back on the login page after logout");

        // 3) TC-03 Login: use the same account and verify "Logged in as ..."
        driver.findElement(loginEmail).sendKeys(email);
        driver.findElement(loginPassword).sendKeys(password);
        driver.findElement(loginButton).click();
        String loggedText = wait.until(ExpectedConditions.visibilityOfElementLocated(loggedInAs)).getText();
        Assert.assertTrue(loggedText.contains("Ahmed"), "Header should show the logged in user name");

        // 4) Cleanup: delete the account so we do not leave data on the site
        wait.until(ExpectedConditions.elementToBeClickable(deleteAccount)).click();
        String deletedText = wait.until(ExpectedConditions.visibilityOfElementLocated(accountDeletedTitle)).getText();
        Assert.assertEquals(deletedText, "ACCOUNT DELETED!");
    }

    // ------------------------------------------------------------------
    // Helper (NOT a test): registers a new user using only the required fields.
    // Starts on the login page and finishes with the user logged in.
    // ------------------------------------------------------------------

    private void registerNewUser(String email, String password) {
        driver.get("https://automationexercise.com/login");

        // Signup page: name + email
        driver.findElement(By.cssSelector("input[data-qa='signup-name']")).sendKeys("Ahmed");
        driver.findElement(By.cssSelector("input[data-qa='signup-email']")).sendKeys(email);
        driver.findElement(By.cssSelector("button[data-qa='signup-button']")).click();

        // New page: wait for the password field, then fill the required fields only
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys(password);
        driver.findElement(By.id("first_name")).sendKeys("Ahmed");
        driver.findElement(By.id("last_name")).sendKeys("Albay");
        driver.findElement(By.id("address1")).sendKeys("Menofia");
        driver.findElement(By.id("state")).sendKeys("Menofia");
        driver.findElement(By.id("city")).sendKeys("Shibin El Kom");
        driver.findElement(By.id("zipcode")).sendKeys("32511");
        driver.findElement(By.id("mobile_number")).sendKeys("01000000000");
        driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();

        // Wait for the success page, then press Continue (this logs the user in)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2[data-qa='account-created']")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-qa='continue-button']"))).click();
    }
}