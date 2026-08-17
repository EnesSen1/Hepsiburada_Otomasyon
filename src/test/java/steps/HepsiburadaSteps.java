package steps;

import com.thoughtworks.gauge.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;
import pages.HomePage;
import pages.LoginPage;


public class HepsiburadaSteps {

    private HomePage homePage;
    private LoginPage loginPage;

    @Step("Tarayıcıyı aç")
    public void  tarayiciAc(){
        DriverFactory.initializeDriver();
    }

    @Step("<url> adresine git")
    public void adreseGit(String url){
        WebDriver driver = DriverFactory.getDriver();
        driver.get(url);
    }

    @Step("Giriş yap sayfasına git")
    public void girisYapSayfasinaGit() {
        homePage = new HomePage();
        homePage.goToLoginPage();
    }

    @Step("Geçerli kullanıcı adı ve şifre ile giriş yap")
    public  void login(){

        String username = System.getenv("HB_USERNAME");
        String password = System.getenv("HB_PASSWORD");

        loginPage = new LoginPage();

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
}
