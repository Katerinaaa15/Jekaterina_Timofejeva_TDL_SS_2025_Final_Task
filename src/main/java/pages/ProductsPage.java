package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

public class ProductsPage {

    private WebDriver driver;

    private By title = By.cssSelector(".title");
    private By cartButton = By.cssSelector(".shopping_cart_link");
    private By fleeceAddToCart = By.id("add-to-cart-sauce-labs-fleece-jacket");
    private By fleeceRemove = By.id("remove-sauce-labs-fleece-jacket");

    public ProductsPage(WebDriver driver) {

        this.driver = driver;
    }

    public String getTitle() {

        return driver.findElement(title).getText();
    }

    public Point getCartButtonLocation() {

        return driver.findElement(cartButton).getLocation();
    }

    public void goToCart() {
        driver.findElement(cartButton).click();
    }

    public void addFleeceToCart() {

        driver.findElement(fleeceAddToCart).click();
    }

    public void removeFleeceFromCart() {

        driver.findElement(fleeceRemove).click();
    }

    public boolean isRemoveButtonVisible() {

        return driver.findElements(fleeceRemove).size() > 0;
    }

    public boolean isAddButtonVisible() {

        return driver.findElements(fleeceAddToCart).size() > 0;
    }
}