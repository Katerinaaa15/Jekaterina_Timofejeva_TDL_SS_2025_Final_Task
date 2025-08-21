import base.BaseTest;
import org.openqa.selenium.Point;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import pages.CartPage;
import pages.LoginPage;
import pages.MenuPage;
import pages.ProductsPage;
import common.ConfigReader;
import common.ScreenshotUtil;

public class Tests extends BaseTest {
    @Test(description = "Cart Checkout Validation", groups = {"smoke"})
    public void test1_cartCheckoutValidation() {
        test = extent.createTest("Test 1 - Cart Checkout Validation");
        ProductsPage products;
        products = new ProductsPage(driver);
        CartPage cart;
        loginWithStandardUser();
        test.info("Navigating to Cart");
        products.goToCart();
        cart = new CartPage(driver);
        test.info("Verifying Cart page title");
        String screenshotPath2 = ScreenshotUtil.capture(driver, "cart_page");
        test.addScreenCaptureFromPath(screenshotPath2);
        Assert.assertEquals(cart.getTitle(), "Your Cart");
        test.info("Checking if cart is empty");
        Assert.assertTrue(cart.isCartEmpty());
        test.info("Validating checkout button color");
        String checkoutColor = getElementColorName(By.id("checkout"));
        test.info("Checkout button color: " + checkoutColor);
        Assert.assertEquals(checkoutColor, "green", "Checkout button color mismatch!");
        test.info("Clicking checkout");
        cart.clickCheckout();
        test.info("Verifying Cart page title again");
        Assert.assertEquals(cart.getTitle(), "Your Cart");

    }

    @Test(description = "Locked Out User Login", groups = {"smoke"})
    public void test2_lockedOutUser() {
        test = extent.createTest("Test 2 - Locked Out User");
        LoginPage login = new LoginPage(driver);
        test.info("Login with invalid (locked user) credentials");
        login.enterUsername("locked_out_user");
        login.enterPassword(ConfigReader.get("password"));
        test.info("Username field: " + login.getUsername());
        test.info("Password field: " + login.getPassword());
        String screenshotPath = ScreenshotUtil.capture(driver, "login_fields_with_locked_user");
        test.addScreenCaptureFromPath(screenshotPath);
        login.clickLogin();
        test.info("Verifying Home page login button");
        Assert.assertTrue(login.getLoginButton(),
                "Expected to be on Login page but navigated away!");
        test.info("Error message validation" );
        String expectedError = "Epic sadface: Sorry, this user has been locked out.";
        Assert.assertEquals(login.getErrorMessage(), expectedError,
                "Error message does not match expected text!");
        test.info("Validate error message has red background");
        String loginErrorColor = getElementColorName(By.cssSelector(".error-message-container"));
        test.info("Checkout button color: " + loginErrorColor);
        Assert.assertEquals(loginErrorColor, "red", "Checkout button color mismatch!");
    }

    @Test(description = "Add and Remove Product", groups = {"regression"})
    public void test3_addRemoveFleece() {
        test = extent.createTest("Test 3 - Add/Remove Fleece Jacket");
        ProductsPage products;
        products = new ProductsPage(driver);
        loginWithStandardUser();
        test.info("Product (Sauce Labs Fleece Jacket) adding to cart check");
        products.addFleeceToCart();
        test.info("Verifying Remove button is displayed after adding product to cart");
        Assert.assertTrue(products.isRemoveButtonVisible());
        String screenshotPath2 = ScreenshotUtil.capture(driver, "remove_button_visible");
        test.addScreenCaptureFromPath(screenshotPath2);
        test.info("Product (Sauce Labs Fleece Jacket) removing from the cart check");
        products.removeFleeceFromCart();
        Assert.assertTrue(products.isAddButtonVisible());
        test.info("Navigation to Cart page");
        products.goToCart();
        CartPage cart = new CartPage(driver);
        test.info("Verifying Cart page title");
        Assert.assertEquals(cart.getTitle(), "Your Cart");
        test.info(" Removed product (Sauce Labs Fleece Jacket) absence in the cart validation");
        Assert.assertFalse(cart.isItemPresent("Sauce Labs Fleece Jacket"));
    }

    @Test(description = "Logout and Re-Login with Visual User", groups = {"regression"})
    public void test4_logoutLoginWithVisualUser() {
        test = extent.createTest("Test 4 - Logout and Login as visual_user");
        LoginPage login = new LoginPage(driver);
        ProductsPage products;
        products = new ProductsPage(driver);
        loginWithStandardUser();
        Point standardCartLocation = products.getCartButtonLocation();
        test.info("Standard user cart location: " + standardCartLocation.toString());
        MenuPage menu = new MenuPage(driver);
        test.info("Menu opening check");
        menu.openMenu();
        Assert.assertTrue(menu.isMenuVisible());
        test.info("Logout from the menu");
        menu.clickLogout();
        test.info("Verification Home page login button");
        Assert.assertTrue(login.getLoginButton(),
                "Expected to be on Login page but navigated away!");

        String screenshotPath1 = ScreenshotUtil.capture(driver, "login_fields");
        test.addScreenCaptureFromPath(screenshotPath1);
        test.info("Logging in as visual user ");
        login.enterUsername("visual_user");
        login.enterPassword(ConfigReader.get("password"));
        login.clickLogin();
        String screenshotPath2 = ScreenshotUtil.capture(driver, "products_page");
        test.addScreenCaptureFromPath(screenshotPath2);
        test.info("Verifying Products page title");
        Assert.assertEquals(products.getTitle(), "Products");
        Point visualCartLocation = products.getCartButtonLocation();
        test.info("Visual user cart location: " + visualCartLocation.toString());
        test.info("[Cart] button different locations for standard_user and visual user");
        Assert.assertNotEquals(visualCartLocation, standardCartLocation,
                "Cart button location should differ between standard_user and visual_user!");
    }

    @Test(description = "Product Images Uniqueness check for problem user", groups = {"smoke", "regression"})
    public void test5_allProductsHaveSamePicture() {
        test = extent.createTest("Test 5 - All Products Images Are Not The Same");
        By imageLocator = By.cssSelector(".inventory_item_img img");
        LoginPage login = new LoginPage(driver);
        ProductsPage products;
        test.info("Logging in with invalid (problem user) credentials");
        login.enterUsername("problem_user");
        login.enterPassword(ConfigReader.get("password"));
        test.info("Username field: " + login.getUsername() + ", password field: " + login.getPassword());
        String screenshotPath = ScreenshotUtil.capture(driver, "login_fields");
        test.addScreenCaptureFromPath(screenshotPath);
        login.clickLogin();
        products = new ProductsPage(driver);
        test.info("Verifying Products page title");
        Assert.assertEquals(products.getTitle(), "Products");
        test.info("Verifying product images repeating");
        validateAllImagesAreIdentical(imageLocator);
    }
}
