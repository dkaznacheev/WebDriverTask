package ru.hse.webdrivertask;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateUserForm extends PageObject {

    @FindBy(id="id_l.U.cr.login")
    private WebElement loginField;

    @FindBy(id="id_l.U.cr.password")
    private WebElement passwordField;

    @FindBy(id="id_l.U.cr.confirmPassword")
    private WebElement confirmPasswordField;

    @FindBy(id="id_l.U.cr.createUserOk")
    private WebElement okButton;

    @FindBy(id="id_l.U.cr.createUserCancel")
    private WebElement cancelButton;

    public CreateUserForm(WebDriver driver) {
        super(driver);
    }

    public void createUserWithParams(String name, String password, String confirm) {
        loginField.sendKeys(name);
        passwordField.sendKeys(password);
        confirmPasswordField.sendKeys(confirm);
        okButton.click();
    }

    public boolean isOpen() {
        return !driver.findElements(By.id("id_l.U.cr.createUserDialog")).isEmpty();
    }

    public void close() {
        cancelButton.click();
    }

    public void createUserWithName(String name) {
        createUserWithParams(name, "1", "1");
    }

}
