package base;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import common.ConfigReader;
import common.DriverFactory;
import common.ScreenshotUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import pages.LoginPage;
import pages.ProductsPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import java.util.List;
import java.awt.*;
import java.lang.reflect.Method;

public class BaseTest {
    protected WebDriver driver;
    protected static ExtentReports extent;
    protected ExtentTest test;

    public void loginWithStandardUser() {
        LoginPage login = new LoginPage(driver);
        ProductsPage products;
        test.info("Logging in with valid credentials");
        login.enterUsername(ConfigReader.get("username"));
        login.enterPassword(ConfigReader.get("password"));
        test.info("Username field: " + login.getUsername() + ", password field: " + login.getPassword());
        String screenshotPath = ScreenshotUtil.capture(driver, "login_fields");
        test.addScreenCaptureFromPath(screenshotPath);
        login.clickLogin();
        products = new ProductsPage(driver);
        test.info("Verifying Products page title");
        String screenshotPath1 = ScreenshotUtil.capture(driver, "products_page");
        test.addScreenCaptureFromPath(screenshotPath1);
        Assert.assertEquals(products.getTitle(), "Products");
    }

    public void validateAllImagesAreIdentical(By locator) {
        List<WebElement> images = driver.findElements(locator);
        Assert.assertFalse(images.isEmpty(), "No images found with locator: " + locator);
        test.info("Found " + images.size() + " images to validate.");
        String referenceSrc = images.get(0).getAttribute("src");
        test.info("Reference image src: " + referenceSrc);

        for (WebElement img : images) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", img);
            String currentSrc = img.getAttribute("src");
            Assert.assertNotNull(currentSrc, "Image src is null");
            test.info("Comparing image src: " + currentSrc);
            Assert.assertEquals(currentSrc, referenceSrc, "Image src mismatch");
        }
        test.pass("All images have the same src: " + referenceSrc);
    }

    public String getElementColorName(By locator) {
        WebElement element = driver.findElement(locator);
        String rgba = element.getCssValue("background-color");
        String[] parts = rgba.replace("rgba(", "").replace("rgb(", "").replace(")", "").split(",");
        int r = Integer.parseInt(parts[0].trim());
        int g = Integer.parseInt(parts[1].trim());
        int b = Integer.parseInt(parts[2].trim());
        Color color = new Color(r, g, b);
        String colorName = getColorNameFromRgb(color);
        System.out.println("Color of element [" + locator.toString() + "]: " + colorName);

        return colorName;
    }

    private String getColorNameFromRgb(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        if (r == 226 && g == 35 && b == 26) return "red";
        if (r == 61 && g == 220 && b == 145) return "green";
        return "unknown";
    }

    @BeforeSuite(alwaysRun = true)
    public void setupReportAndConfigs() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("src/main/java/utils/extentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        ConfigReader.loadConfig();
    }

    @BeforeMethod(alwaysRun = true)
    public void setupWebDriver(Method method) {
        driver = DriverFactory.getDriver();
        driver.get(ConfigReader.get("url"));
    }


    @AfterMethod(alwaysRun = true)
    public void collectTestResults(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = ScreenshotUtil.capture(driver, result.getName());
            test.fail(result.getThrowable());
            test.addScreenCaptureFromPath(screenshotPath);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test passed");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("Test skipped: " + result.getThrowable());
        }
    }

    @AfterSuite(alwaysRun = true)
    public void closeReportAndDriver() {
        if (extent != null) {
            extent.flush();
        }
        DriverFactory.quitDriver();
    }
}
