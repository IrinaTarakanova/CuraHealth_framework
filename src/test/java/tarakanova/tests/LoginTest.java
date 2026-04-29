package tarakanova.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import tarakanova.base.BaseTest;
import tarakanova.pages.HomePage;
import tarakanova.pages.LoginPage;

public class LoginTest extends BaseTest {
    @Test
    public void loginWithInvalidCredentials(){
        HomePage homePage = new HomePage(driver);
        homePage.clickMakeAppointment();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Mike Joe", "password123");


        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Login failed");
     }

}
