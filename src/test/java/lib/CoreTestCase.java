package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;
import java.time.Duration;

public class CoreTestCase {

    private static final String
            PLATFORM_IOS = "ios",
            PLATFORM_ANDROID = "android";

    protected static AppiumDriver driver;
    private static String url = "http://127.0.0.1:4723/";
//    private static String url = "http://127.0.0.1:4723/wd/hub";

    @BeforeAll
    public static void setUp() throws Exception {
        getCapabilitiesByPlatformEnv();
        rotateScreenPortrait();
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    protected static void rotateScreenPortrait() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void rotateScreenLandscape() {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgroundApp(int seconds) {
        driver.runAppInBackground(Duration.ofSeconds(seconds));
    }


    private static DesiredCapabilities getCapabilitiesByPlatformEnv() throws Exception {
        final String separator = File.separator;
        final String platform = System.getenv("PLATFORM");
        final DesiredCapabilities capabilities = new DesiredCapabilities();

        if (PLATFORM_ANDROID.equals(platform)) {
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("appium:deviceName", "AndroidTestDevice");
            capabilities.setCapability("appium:platformVersion", "8.1");
            capabilities.setCapability("appium:automationName", "UiAutomator2");
            capabilities.setCapability("appium:appPackage", "org.wikipedia");
            capabilities.setCapability("appium:appActivity", ".main.MainActivity");
            capabilities.setCapability("appium:app",
                    "%s%sapks/org.wikipedia.apk".formatted(System.getProperty("user.dir"), separator));
            driver = new AndroidDriver(new URL(url), capabilities);
        } else if (PLATFORM_IOS.equals(platform)) {
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("appium:deviceName", "iPhone 16");
            capabilities.setCapability("appium:platformVersion", "18.5");
            capabilities.setCapability("appium:automationName", "XCUITest");
            capabilities.setCapability("appium:app",
                    "%s%sapks/Wikipedia.app".formatted(System.getProperty("user.dir"), separator));
            driver = new IOSDriver(new URL(url), capabilities);
        } else {
            throw new Exception("Unrecognized platform " + platform);
        }
        return capabilities;
    }
}