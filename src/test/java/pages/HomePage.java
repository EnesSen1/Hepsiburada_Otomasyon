package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.LocatorReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class HomePage {

    private static final Logger logger =
            LoggerFactory.getLogger(HomePage.class);

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By accountMenu =
            LocatorReader.getLocator("homePage.accountMenu");

    private final By loginButton =
            LocatorReader.getLocator("homePage.loginButton");

    private final By accountInfo =
            LocatorReader.getLocator("homePage.accountInfo");

    private final By searchInput =
            LocatorReader.getLocator("homePage.searchInput");

    private final By cookieShadowHost =
            LocatorReader.getLocator("homePage.cookieShadowHost");

    private final By cookieAcceptButton =
            LocatorReader.getLocator("homePage.cookieAcceptButton");


    public HomePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(
                        ConfigReader.getInt("default_wait_seconds")
                )
        );
    }

    public void acceptCookies() {
        try {
            WebElement shadowHost = new WebDriverWait(
                    driver,
                    Duration.ofSeconds(
                            ConfigReader.getInt("cookie_wait_seconds")
                    )
            ).until(ExpectedConditions.presenceOfElementLocated(cookieShadowHost));

            SearchContext shadowRoot = shadowHost.getShadowRoot();

            WebElement acceptButton = shadowRoot.findElement(
                    cookieAcceptButton
            );

            acceptButton.click();

        } catch (TimeoutException | NoSuchElementException e) {
            logger.warn("Çerez bildirimi görünmedi, teste devam ediliyor.");
        }
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

    public void search(String keyword) {

        WebElement initialSearchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchInput)
        );

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", initialSearchBox);

        wait.until(driver -> {
            try {
                WebElement activeElement = driver.switchTo().activeElement();

                return "search-bar-input".equals(
                        activeElement.getAttribute("data-test-id")
                );
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });

        Actions actions = new Actions(driver);
        actions.sendKeys(keyword)
                .sendKeys(Keys.ENTER)
                .perform();
    }
}