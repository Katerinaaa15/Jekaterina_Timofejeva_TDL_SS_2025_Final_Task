package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
public class MenuPage {

    private WebDriver driver;

    private By menuButton = By.id("react-burger-menu-btn");
    private By logoutButton = By.id("logout_sidebar_link");
    private By sideMenu = By.cssSelector(".bm-menu");

    public MenuPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openMenu() {
        driver.findElement(menuButton).click();
    }

    public boolean isMenuVisible() {
        return driver.findElement(sideMenu).isDisplayed();
    }

    public void clickLogout() {
        driver.findElement(logoutButton).click();
    }
}
