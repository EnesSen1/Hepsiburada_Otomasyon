package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.LocatorReader;

import java.time.Duration;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By productNames =
            LocatorReader.getLocator("cartPage.productNames");

    private final By deleteAllProducts =
            LocatorReader.getLocator("cartPage.deleteAllProducts");

    private final By confirmDeleteAllProducts =
            LocatorReader.getLocator("cartPage.confirmDeleteAllProducts");

    public CartPage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(
                        ConfigReader.getInt("default_wait_seconds")
                )
        );
    }

    public String getProductName() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(productNames)
        ).getText().trim();
    }

    public void clearCart() {

        wait.until(
                ExpectedConditions.elementToBeClickable(deleteAllProducts)
        ).click();

        wait.until(
                ExpectedConditions.elementToBeClickable(confirmDeleteAllProducts)
        ).click();
    }
}