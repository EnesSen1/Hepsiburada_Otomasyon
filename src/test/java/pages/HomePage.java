package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverFactory;

import java.time.Duration;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By accountMenu = By.id("myAccount");
    private final By loginButton = By.id("login");
    private final By accountInfo = By.cssSelector("[data-test-id='account']");

    public HomePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void goToLoginPage() {
        Actions actions = new Actions(driver);

        actions.moveToElement(
                wait.until(ExpectedConditions.visibilityOfElementLocated(accountMenu))
        ).perform();

        wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        ).click();
    }

    public boolean isUserLoggedIn() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(accountInfo)
        ).isDisplayed();
    }

}
