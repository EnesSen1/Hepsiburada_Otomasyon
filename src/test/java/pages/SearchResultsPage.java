package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.LocatorReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class SearchResultsPage {

    private static final Logger logger =
            LoggerFactory.getLogger(SearchResultsPage.class);

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By productList =
            LocatorReader.getLocator("searchResultsPage.productList");

    private final By productCards =
            LocatorReader.getLocator("searchResultsPage.productCards");

    private final By productLink =
            LocatorReader.getLocator("searchResultsPage.productLink");

    private final By productInfo =
            LocatorReader.getLocator("searchResultsPage.productInfo");

    private String selectedProductName;
    private String selectedProductPrice;

    public SearchResultsPage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(
                        ConfigReader.getInt("default_wait_seconds")
                )
        );
    }

    public boolean isProductListVisible() {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(productList)
        );

        return !driver.findElements(productCards).isEmpty();
    }

    public WebElement getFirstProductOfSecondRow() {

        List<WebElement> products = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(productCards)
        );

        int firstRowY = products.get(0).getLocation().getY();

        for (WebElement product : products) {
            if (product.getLocation().getY() > firstRowY) {
                return product;
            }
        }

        throw new RuntimeException("İkinci satırdaki ürün bulunamadı.");
    }

    public void saveFirstProductOfSecondRow() {

        WebElement product = getFirstProductOfSecondRow();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                product
        );

        WebElement productLinkElement = wait.until(driver -> {
            try {
                return product.findElement(productLink);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                return null;
            }
        });

        selectedProductName = productLinkElement.getAttribute("title");

        WebElement productInfoElement = wait.until(driver -> {
            try {
                return product.findElement(productInfo);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                return null;
            }
        });

        String ariaLabel = productInfoElement.getAttribute("aria-label");

        selectedProductPrice = ariaLabel
                .split(",")[1]
                .replace("fiyat:", "")
                .trim();

        logger.info("Seçilen ürün: {}", selectedProductName);
        logger.info("Fiyat: {}", selectedProductPrice);
    }
    public String getSelectedProductName() {
        return selectedProductName;
    }

    public void clickFirstProductOfSecondRow() {

        String currentWindow = driver.getWindowHandle();

        WebElement product = getFirstProductOfSecondRow();

        WebElement productLinkElement = product.findElement(productLink);

        productLinkElement.click();

        wait.until(driver -> driver.getWindowHandles().size() > 1);

        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(currentWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }
}