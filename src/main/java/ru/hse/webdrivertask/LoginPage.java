package ru.hse.webdrivertask;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageObject {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id="id_l.L.login")
    private WebElement loginForm;

    @FindBy(id="id_l.L.password")
    private WebElement passwordForm;

    @FindBy(id="id_l.L.loginButton")
    private WebElement confirm;

    public MainPage login(String user, String password) {
        loginForm.sendKeys(user);
        passwordForm.sendKeys(password);
        confirm.click();
        driver.get("http://localhost:8080/users");
        return new MainPage(driver);
    }
}
