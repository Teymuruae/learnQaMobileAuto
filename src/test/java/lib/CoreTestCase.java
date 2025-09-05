package lib;

import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.ScreenOrientation;

import java.time.Duration;

public class CoreTestCase {

    protected static AppiumDriver driver;

    @BeforeAll
    public static void setUp() throws Exception {
        driver = Platform.getInstance().getDriver();
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
}