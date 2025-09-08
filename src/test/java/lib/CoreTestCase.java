package lib;

import io.appium.java_client.AppiumDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileOutputStream;
import java.time.Duration;
import java.util.Properties;

public class CoreTestCase {

    protected static RemoteWebDriver driver;

    @BeforeAll
    public static void setUp() throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = Platform.getInstance().getDriver();
        createAllurePropertyFile();
        rotateScreenPortrait();
    }

    @BeforeEach
    void beforeEach() {
        openWikiWebPageForMobileWeb();
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    protected static void rotateScreenPortrait() {
        if (driver instanceof AppiumDriver) {
            final AppiumDriver appiumDriver = (AppiumDriver) driver;
            appiumDriver.rotate(ScreenOrientation.PORTRAIT);
        }
    }

    protected void rotateScreenLandscape() {
        if (driver instanceof AppiumDriver) {
            final AppiumDriver appiumDriver = (AppiumDriver) driver;
            appiumDriver.rotate(ScreenOrientation.LANDSCAPE);
        }
    }

    protected void backgroundApp(int seconds) {
        if (driver instanceof AppiumDriver) {
            final AppiumDriver appiumDriver = (AppiumDriver) driver;
            appiumDriver.runAppInBackground(Duration.ofSeconds(seconds));
        }
    }

    protected void openWikiWebPageForMobileWeb() {
        if (Platform.getInstance().isMobileWeb()) {
            driver.get("https://en.m.wikipedia.org");
        }
    }

    private static void createAllurePropertyFile() {
        final String path = System.getProperty("allure.results.directory");

        try (FileOutputStream fos = new FileOutputStream(path + "/environment.properties")) {
            final Properties properties = new Properties();
            properties.setProperty("Environment", Platform.getInstance().getPlatformValue());
            properties.store(fos, "see github");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}