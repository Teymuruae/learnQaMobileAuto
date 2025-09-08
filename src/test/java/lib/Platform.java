package lib;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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
            PLATFORM_MOBILE_WEB = "mobileWeb",
            APPIUM_URL = "http://127.0.0.1:4723/";

    private final String separator = File.separator;

    public RemoteWebDriver getDriver() throws Exception {
        final URL URL = new URL(APPIUM_URL);

        if (isAndroid()) {
            return new AndroidDriver(URL, getAndroidCapabilities());
        } else if (isIos()) {
            return new IOSDriver(URL, getIosCapabilities());
        } else if (isMobileWeb()) {
            return new ChromeDriver(getChromeOptions());
        } else {
            throw new Exception("Cannot detect type of driver. Platform value: " + getPlatformValue());
        }
    }

    private DesiredCapabilities getAndroidCapabilities() {
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("newCommandTimeout", 300);
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:deviceName", "AndroidTestDevice");
        capabilities.setCapability("appium:platformVersion", "14");
//        capabilities.setCapability("appium:platformVersion", "8.1");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appium:appPackage", "org.wikipedia");
        capabilities.setCapability("appium:appActivity", ".main.MainActivity");
        capabilities.setCapability("appium:app",
                "%s%sapks/org.wikipedia2.apk".formatted(System.getProperty("user.dir"), separator));
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

    private ChromeOptions getChromeOptions() {
        final Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 360);
        deviceMetrics.put("height", 640);
        deviceMetrics.put("pixelRatio", 3.0);

        final Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) " +
                "AppleWebKit/535.19 (KHTML, like Gecko) " +
                "Chrome/18.0.1025.166 Mobile Safari/535.19");

        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        chromeOptions.addArguments("window-size=340,640");

        return chromeOptions;
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

    public boolean isMobileWeb() {
        return isPlatform(PLATFORM_MOBILE_WEB);
    }

    public String getPlatformValue() {
        return System.getenv("PLATFORM");
    }
}