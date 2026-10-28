package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.SignupPage;

public class AuthTest extends BaseTest {

    // ------------------------------------------------------------------
    // TC-04: Login with wrong credentials shows an error
    // ------------------------------------------------------------------

    @Test
    public void invalidLoginShowsError() {
        driver.get("https://automationexercise.com/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Ahmed@gmail.com", "A@fsg5321");

        // The Page Object returns the text, the TEST decides pass or fail
        Assert.assertEquals(loginPage.getTextErrorMessage(), "Your email or password is incorrect!");
    }

    // ------------------------------------------------------------------
    // TC-01 + TC-07: Register a new user, then delete the account
    // ------------------------------------------------------------------

    @Test
    public void registerTest() {
        driver.get("https://automationexercise.com/login");

        // The email must be unique on every run, otherwise the site answers "Email Address already exist!"
        String email = "ahmed" + System.currentTimeMillis() + "@gmail.com";
        String password = "Test@1234";

        // Register through the Page Object
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("Ahmed", email);
        signupPage.fillAccountInformation(password);

        // Verify the account was created, THEN press Continue (the title disappears after Continue)
        Assert.assertEquals(signupPage.getAccountCreatedTitle(), "ACCOUNT CREATED!");
        signupPage.clickContinue();

        // Cleanup: delete the account and verify (these steps move to an AccountPage later)
        By deleteAccount = By.xpath("//a[@href='/delete_account']");
        By accountDeletedTitle = By.cssSelector("h2[data-qa='account-deleted']");

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

        // Locators used by this test
        By logoutLink = By.xpath("//a[@href='/logout']");
        By loggedInAs = By.xpath("//a[contains(text(),'Logged in as')]");
        By loginEmail = By.cssSelector("input[data-qa='login-email']");
        By deleteAccount = By.xpath("//a[@href='/delete_account']");
        By accountDeletedTitle = By.cssSelector("h2[data-qa='account-deleted']");

        // 1) Test data setup: create a new account (we end up logged in)
        registerNewUser(email, password);

        // 2) TC-06 Logout: click Logout and verify we are back on the login page
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginEmail));
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"), "Should be back on the login page after logout");

        // 3) TC-03 Login: use the same account (through LoginPage) and verify "Logged in as ..."
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);
        String loggedText = wait.until(ExpectedConditions.visibilityOfElementLocated(loggedInAs)).getText();
        Assert.assertTrue(loggedText.contains("Ahmed"), "Header should show the logged in user name");

        // 4) Cleanup: delete the account so we do not leave data on the site
        wait.until(ExpectedConditions.elementToBeClickable(deleteAccount)).click();
        String deletedText = wait.until(ExpectedConditions.visibilityOfElementLocated(accountDeletedTitle)).getText();
        Assert.assertEquals(deletedText, "ACCOUNT DELETED!");
    }

    // ------------------------------------------------------------------
    // Helper (NOT a test): registers a new user through SignupPage.
    // Starts on the login page and finishes with the user logged in.
    // ------------------------------------------------------------------

    private void registerNewUser(String email, String password) {
        driver.get("https://automationexercise.com/login");

        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("Ahmed", email);
        signupPage.fillAccountInformation(password);

        // Setup check: if the account was not created, fail here with a clear message
        Assert.assertEquals(signupPage.getAccountCreatedTitle(), "ACCOUNT CREATED!");
        signupPage.clickContinue(); // logs the user in
    }
}