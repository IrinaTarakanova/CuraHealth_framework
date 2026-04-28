package tarakanova.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import tarakanova.base.BasePage;

public class HomePage extends BasePage {

    public WebDriver driver;

    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    private final By makeApppointment = By.id("btn-make-appointment");

    public void clickMakeAppointment() {
        click(makeApppointment);
    }

}
