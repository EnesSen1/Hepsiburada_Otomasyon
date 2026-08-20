package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.LocatorReader;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameInput =
            LocatorReader.getLocator("loginPage.usernameInput");

    private final By passwordInput =
            LocatorReader.getLocator("loginPage.passwordInput");

    private final By loginButton =
            LocatorReader.getLocator("loginPage.loginButton");

    public LoginPage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(
                        ConfigReader.getInt("default_wait_seconds")
                )
        );
    }

    public void waitUntilLoginPageReady() {
        wait.until(
                ExpectedConditions.elementToBeClickable(usernameInput)
        );
    }

    public void enterUsername(String username) {
        WebElement usernameElement = wait.until(
                ExpectedConditions.elementToBeClickable(usernameInput)
        );

        usernameElement.clear();
        usernameElement.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement passwordElement = wait.until(
                ExpectedConditions.elementToBeClickable(passwordInput)
        );

        passwordElement.clear();
        passwordElement.sendKeys(password);
    }

    public void clickLoginButton() {
        WebElement loginButtonElement = wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        );

        loginButtonElement.click();
    }
}