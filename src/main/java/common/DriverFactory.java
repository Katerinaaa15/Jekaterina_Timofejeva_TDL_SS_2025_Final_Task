package common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverFactory {

    private static WebDriver driver;

    private DriverFactory() {}

    public static WebDriver getDriver() {
        if (driver == null) {
            String browser = ConfigReader.get("browser");

            switch (browser.toLowerCase()) {
                case "chrome":
//                  System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver_mac_64"); // chromedriver for mac 64
                    System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver_windows_64.exe"); // chromedriver for windows 64
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--incognito --disable-features=PasswordCheck");
//                    options.addArguments("--incognito user-data-dir=C:/chrome_profile_test"); // start browser in incognito and use chrome clean profile
                    driver = new ChromeDriver(options);
                    break;
                default:
                    throw new RuntimeException("Unsupported browser: " + browser);
            }
            driver.manage().window().maximize();

            String implicitWait = ConfigReader.get("implicitWait");
            String pageLoadTimeout = ConfigReader.get("pageLoadTimeout");

            if (implicitWait != null && !implicitWait.isEmpty()) {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(implicitWait)));
            }

            if (pageLoadTimeout != null && !pageLoadTimeout.isEmpty()) {
                driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Long.parseLong(pageLoadTimeout)));
            }
        }
        return driver;
    }
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}