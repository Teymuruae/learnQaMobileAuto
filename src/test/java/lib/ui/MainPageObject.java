package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class MainPageObject {

    public static final String
            XPATH = "xpath:",
            ID = "id:";

    private final AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }

    public MainPageObject skip() {
        waitForElementAndClick(
                "id:org.wikipedia:id/fragment_onboarding_skip_button",
                "can't find Skip button", 5);
        return this;
    }

    public WebElement waitForElementPresent(String locator, String errorMessage, long timeoutInSeconds) {
        final By by = getLocatorByString(locator);
        final WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void assertElementIsPresent(String locator, String errorMessage) {
        final By by = getLocatorByString(locator);
        Assertions.assertTrue(driver.findElement(by).isDisplayed(), errorMessage);
    }

    public WebElement waitForElementPresent(String locator, String errorMessage) {
        return waitForElementPresent(locator, errorMessage, 5);
    }

    public boolean waitForElementNotPresent(String locator, String errorMessage, long timeoutInSeconds) {
        final By by = getLocatorByString(locator);
        final WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClick(String locator, String errorMessage, long timeoutInSeconds) {
        final WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String errorMessage, long timeoutInSeconds) {
        final WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public WebElement waitForElementAndClear(String locator, String errorMessage, long timeoutInSeconds) {
        final WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    public void swipeUp(int swipeDuration) {
        final TouchAction touchAction = new TouchAction(driver);
        final Dimension size = driver.manage().window().getSize();
        final int x = size.getWidth() / 2; // то есть середина экрана по ширине
        final int startY = (int) (size.getHeight() * 0.8);
        final int endY = (int) (size.getHeight() * 0.2);
        final PointOption pointOption = PointOption.point(x, startY);
        touchAction.press(pointOption).waitAction(WaitOptions.waitOptions(Duration.ofMillis(swipeDuration)))
                .moveTo(PointOption.point(x, endY)).release().perform();
    }

    public void swipeUpQuick() {
        swipeUp(200);
    }

    public void swipeUpToFindElement(String locator, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;
        final By by = getLocatorByString(locator);
        while (driver.findElements(by).size() == 0) {
            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(locator, "Cant find element by swiping up. \n" + errorMessage, 0);
                return;
            }
            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    public void swipeUpToFindElement(String locator, String errorMessage) {
        final StopWatch stopWatch = StopWatch.createStarted();
        final By by = getLocatorByString(locator);
        MobileElement el = (MobileElement) driver.findElement(by);
        if (el.isDisplayed()) {
            Dimension winSize = driver.manage().window().getSize();
            int screenHeight = winSize.getHeight();

            for (int i = 0; i < 10; i++) {
                int elCenterY = el.getCenter().getY();

                if (elCenterY > 0 && elCenterY < screenHeight) {
                    return;
                }
                swipeUpQuick();
            }
            throw new RuntimeException("Не удалось проскролить до элемента " + by);
        } else {
            while (driver.findElements(by).size() == 0) {
                if (stopWatch.getTime(TimeUnit.SECONDS) > 10) {
                    waitForElementPresent(locator, "Cant find element by swiping up. \n" + errorMessage, 0);
                    return;
                }
                swipeUpQuick();
            }
        }
    }

    public void swipeLeft(String locator, String errorMessage) {
        final WebElement element = waitForElementPresent(locator, errorMessage, 10);

        final int leftX = element.getLocation().getX();
        final int rightX = leftX + element.getSize().getWidth();
        final int upperY = element.getLocation().getY();
        final int lowerY = upperY + element.getSize().getHeight();
        final int middleY = (upperY + lowerY) / 2;

        final TouchAction action = new TouchAction(driver);

        action
                .press(PointOption.point(rightX, middleY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)))
                .moveTo(PointOption.point(leftX, middleY))
                .release()
                .perform();
    }

    public void swipeLeft2(String locator, String errorMessage) {
        final WebElement element = waitForElementPresent(locator, errorMessage, 10);

        final int startX = element.getLocation().getX();
        final int startY = element.getLocation().getY();

        final int offset = 200;
        int endX = Math.max(0, startX - offset);

        new TouchAction(driver)
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(800)))
                .moveTo(PointOption.point(endX, startY))
                .release()
                .perform();
    }

    public void swipeRight(String locator, String errorMessage) {
        final WebElement element = waitForElementPresent(locator, errorMessage, 10);

        final int startX = element.getLocation().getX();
        final int startY = element.getLocation().getY();

        new TouchAction(driver)
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(800)))
                .moveTo(PointOption.point(startX + 200, startY))
                .release()
                .perform();
    }

    public int getAmountOfElements(String locator) {
        final By by = getLocatorByString(locator);
        return driver.findElements(by).size();
    }

    public void assertElementNotPresent(String locator, String errorMessage) {
        Assertions.assertTrue(getAmountOfElements(locator) == 0, errorMessage);
    }

    public String waitForElementAndGetAttributeValue(String locator, String attributeName, String errorMessage) {
        final WebElement element = waitForElementPresent(locator, errorMessage);

        return element.getAttribute(attributeName);
    }

    public void assertElementHasText(String locator, String expectedText, String errorMessage) {
        final String actualText = waitForElementAndGetAttributeValue(locator, "text",
                "Element %s not found".formatted(locator));
        Assertions.assertEquals(expectedText, actualText, errorMessage);
    }

    public void assertElementHasText(String locator, String expectedText) {
        final String actualText = waitForElementAndGetAttributeValue(locator, "text",
                "Element %s not found".formatted(locator));
        Assertions.assertEquals(expectedText, actualText,
                "Texts are different. Expected: %s Actual: %s".formatted(expectedText, actualText));
    }

    public void assertAllElementsContainsText(String locator, String expectedText) {
        final By by = getLocatorByString(locator);
        final WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.withMessage("cant find element " + by.toString() + "\n");
        final List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));

        for (int i = 0; i < elements.size(); i++) {
            final String actualText = elements.get(i).getText();
            Assertions.assertTrue(actualText.contains(expectedText),
                    "Element by number %d with text %s doesnt contains %s".formatted(i, actualText, expectedText));
        }
    }

    protected By getLocatorByString(String locatorWithType) {
        final String[] explodedLocator = locatorWithType.split(Pattern.quote(":"), 2);
        final String type = explodedLocator[0];
        final String locator = explodedLocator[1];

        if ("xpath".equals(type)) {
            return By.xpath(locator);
        } else if ("id".equals(type)) {
            return By.id(locator);
        } else {
            throw new IllegalArgumentException("Cannot get type of locator " + locatorWithType);
        }
    }
}
