package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;

public class Platform {

    private Platform() {
    }

    private static Platform instance;

    public static Platform getInstance() {
        if (instance == null) {
            instance = new Platform();
        }
        return instance;
    }

    private static final String
            PLATFORM_IOS = "ios",
            PLATFORM_ANDROID = "android",
            APPIUM_URL = "http://127.0.0.1:4723/";

    private final String separator = File.separator;

    public AppiumDriver getDriver() throws Exception {
        final URL URL = new URL(APPIUM_URL);

        if (isAndroid()) {
            return new AndroidDriver(URL, getAndroidCapabilities());
        } else if (isIos()) {
            return new IOSDriver(URL, getIosCapabilities());
        } else {
            throw new Exception("Cannot detect type of driver. Platform value: " + getPlatformValue());
        }
    }

    private DesiredCapabilities getAndroidCapabilities() {
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("newCommandTimeout", 300);
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:deviceName", "AndroidTestDevice");
        capabilities.setCapability("appium:platformVersion", "8.1");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appium:appPackage", "org.wikipedia");
        capabilities.setCapability("appium:appActivity", ".main.MainActivity");
        capabilities.setCapability("appium:app",
                "%s%sapks/org.wikipedia.apk".formatted(System.getProperty("user.dir"), separator));
        return capabilities;
    }

    private DesiredCapabilities getIosCapabilities() {
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("newCommandTimeout", 300);
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("appium:deviceName", "iPhone 16");
        capabilities.setCapability("appium:platformVersion", "18.5");
        capabilities.setCapability("appium:automationName", "XCUITest");
        capabilities.setCapability("appium:app",
                "%s%sapks/Wikipedia.app".formatted(System.getProperty("user.dir"), separator));
        return capabilities;
    }

    private boolean isPlatform(String myPlatform) {
        return getPlatformValue().equals(myPlatform);
    }

    public boolean isAndroid() {
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIos() {
        return isPlatform(PLATFORM_IOS);
    }

    private String getPlatformValue() {
        return System.getenv("PLATFORM");
    }
}