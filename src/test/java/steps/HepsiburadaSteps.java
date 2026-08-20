package steps;

import com.thoughtworks.gauge.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.ProductPage;
import pages.SearchResultsPage;
import utils.ConfigReader;
import utils.DriverFactory;
import pages.HomePage;
import pages.LoginPage;
import pages.CartPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HepsiburadaSteps {

    private static final Logger logger =
            LoggerFactory.getLogger(HepsiburadaSteps.class);

    private HomePage homePage;
    private LoginPage loginPage;
    private SearchResultsPage searchResultsPage;
    private ProductPage productPage;
    private CartPage cartPage;

    @Step("Tarayıcıyı aç")
    public void tarayiciAc(){
        DriverFactory.initializeDriver();
    }

    @Step("Hepsiburada ana sayfasına git")
    public void anaSayfayaGit(){
        String baseUrl = ConfigReader.get("base_url");

        WebDriver driver = DriverFactory.getDriver();
        driver.get(baseUrl);

        homePage = new HomePage();
    }

    @Step("Çerezleri kabul et")
    public void acceptCookies() {
        homePage.acceptCookies();
    }

    @Step("Giriş yap sayfasına git")
    public void girisYapSayfasinaGit() {
        homePage.goToLoginPage();
    }

    @Step("Geçerli kullanıcı adı ve şifre ile giriş yap")
    public  void login(){

        String username = System.getenv("HB_USERNAME");
        String password = System.getenv("HB_PASSWORD");

        loginPage = new LoginPage();

        loginPage.waitUntilLoginPageReady();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
    }

    @Step("Kullanıcının başarılı şekilde giriş yaptığını doğrula")
    public void verifySuccessFulLogin() {
        Assertions.assertTrue(
                homePage.isUserLoggedIn(),
                "Kullanıcı başarılı şekilde giriş yapamadı."
        );
    }

    @Step("Arama alanına <keyword> yaz ve arama yap")
    public void searchProduct(String keyword) {
        homePage.search(keyword);
    }

    @Step("Arama sonuçlarının geldiğini ve ürün listesinin görünür olduğunu doğrula")
    public void verifySearchResults() {

        searchResultsPage = new SearchResultsPage();

        Assertions.assertTrue(
                searchResultsPage.isProductListVisible(),
                "Arama sonuçlarındaki ürün listesi görünür değil."
        );
    }

    @Step("İkinci satırdaki ilk ürünü bul ve ürün bilgilerini kaydet")
    public void saveSecondRowFirstProduct() {
        searchResultsPage.saveFirstProductOfSecondRow();
    }

    @Step("İkinci satırdaki ilk ürüne tıkla")
    public void clickSecondRowFirstProduct() {
        searchResultsPage.clickFirstProductOfSecondRow();
    }

    @Step("Seçilen ürünün detay sayfasına yönlendirildiğini doğrula")
    public void verifyProductDetailPage() {

        productPage = new ProductPage();

        String expectedProductName =
                searchResultsPage.getSelectedProductName();

        String actualProductName =
                productPage.getFullProductName();

        logger.info("Detay sayfası için beklenen ürün: {}", expectedProductName);
        logger.info("Ürün detay sayfasındaki ürün: {}", actualProductName);

        Assertions.assertEquals(
                expectedProductName,
                actualProductName,
                "Ürün detay sayfasındaki ürün adı, seçilen ürünle eşleşmiyor."
        );
        logger.info("Ürün detay sayfası doğrulaması başarılı.");
    }

    @Step("Ürünü sepete ekle")
    public void addProductToCart() {
        productPage.addToCart();
    }

    @Step("Ürünün sepete eklendiğini doğrula")
    public void verifyProductAddedToCart() {
        Assertions.assertTrue(
                productPage.isProductAddedToCart(),
                "Ürünün sepete eklendiğine dair onay mesajı görüntülenmedi."
        );
    }

    @Step("Sepeti aç")
    public void openCart() throws InterruptedException {
        productPage.openCart();
    }

    @Step("Eklenen ürünün sepette olduğunu ve temel ürün bilgilerinin eşleştiğini doğrula")
    public void verifyProductInCart() {

        cartPage = new CartPage();

        String expectedProductName =
                searchResultsPage.getSelectedProductName();

        try {
            String actualProductName =
                    cartPage.getProductName();

            logger.info("Sepete eklenmesi beklenen ürün: {}", expectedProductName);
            logger.info("Sepette bulunan ürün: {}", actualProductName);

            Assertions.assertEquals(
                    expectedProductName,
                    actualProductName,
                    "Sepetteki ürün adı, seçilen ürünle eşleşmiyor."
            );

            logger.info("Sepetteki ürün doğrulaması başarılı.");

        } finally {
            try {
                cartPage.clearCart();
            } catch (Exception e) {
                logger.error("Sepet temizlenirken hata oluştu.", e);
            }
        }
    }


}
