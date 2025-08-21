package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {

    private WebDriver driver;

    private By title = By.cssSelector(".title");
    private By checkoutButton = By.id("checkout");
    private By cartItems = By.cssSelector(".cart_item");

    public CartPage(WebDriver driver) {

        this.driver = driver;
    }

    public String getTitle() {

        return driver.findElement(title).getText();
    }

    public boolean isCartEmpty() {

        return driver.findElements(cartItems).isEmpty();
    }


    public void clickCheckout() {

        driver.findElement(checkoutButton).click();
    }

    public boolean isItemPresent(String itemName) {
        return driver.findElements(By.xpath("//div[text()='" + itemName + "']")).size() > 0;
    }
}
