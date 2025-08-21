package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private WebDriver driver;

    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMsg = By.cssSelector("[data-test='error']");
    private By errorContainer = By.cssSelector(".error-message-container");

    public LoginPage(WebDriver driver) {

        this.driver = driver;
    }

    public void enterUsername(String username) {

        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {

        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {

        driver.findElement(loginButton).click();
    }

    public String getErrorMessage() {

        return driver.findElement(errorMsg).getText();
    }

    public String getUsername() {

        return driver.findElement(usernameField).getAttribute("value");
    }

    public String getPassword() {

        return driver.findElement(passwordField).getAttribute("value");
    }


    public boolean getLoginButton() {

        return driver.findElement(loginButton).isDisplayed();
    }

    public String getErrorBgColor() {

        return driver.findElement(errorContainer).getCssValue("background-color");
    }
}
