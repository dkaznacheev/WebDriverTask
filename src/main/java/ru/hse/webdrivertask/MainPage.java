package ru.hse.webdrivertask;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Optional;

public class MainPage extends PageObject {

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id="id_l.U.createNewUser")
    private WebElement createUserButton;

    public boolean createNewUser(String name) {
        createUserButton.click();
        CreateUserForm form = new CreateUserForm(driver);
        form.createUserWithName(name);
        if (form.isOpen()) {
            form.close();
            return false;
        }
        return true;
    }

    public String popupMessage() {
        List<WebElement> messages = driver.findElements(By.className("errorSeverity"));
        if (messages.isEmpty())
            return null;
        WebElement message = messages.get(0);
        return message.getAttribute("innerHTML");

    }
}
