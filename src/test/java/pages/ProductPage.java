package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.LocatorReader;

import java.time.Duration;

public class ProductPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By productBrand =
            LocatorReader.getLocator("productPage.productBrand");

    private final By productTitle =
            LocatorReader.getLocator("productPage.productTitle");

    private final By addToCartButton =
            LocatorReader.getLocator("productPage.addToCartButton");

    private final By productAddedMessage =
            LocatorReader.getLocator("productPage.productAddedMessage");

    private final By closeCartPopup =
            LocatorReader.getLocator("productPage.closeCartPopup");

    private final By shoppingCart =
            LocatorReader.getLocator("productPage.shoppingCart");

    public ProductPage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(
                        ConfigReader.getInt("default_wait_seconds")
                )
        );
    }

    public String getFullProductName() {

        String brand = wait.until(
                ExpectedConditions.visibilityOfElementLocated(productBrand)
        ).getText().trim();

        String title = wait.until(
                ExpectedConditions.visibilityOfElementLocated(productTitle)
        ).getText().trim();

        if (title.toLowerCase().startsWith(brand.toLowerCase())) {
            return title;
        }

        return brand + " " + title;
    }

    public void addToCart() {
        wait.until(
                ExpectedConditions.elementToBeClickable(addToCartButton)
        ).click();
    }

    public boolean isProductAddedToCart() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(productAddedMessage)
        ).isDisplayed();
    }

    public void closeCartPopup() {
        wait.until(
                ExpectedConditions.elementToBeClickable(closeCartPopup)
        ).click();
    }

    public void openCart() throws InterruptedException {
        Thread.sleep(1500);
        closeCartPopup();

        Thread.sleep(500);
        wait.until(
                ExpectedConditions.elementToBeClickable(shoppingCart)
        ).click();
    }
}