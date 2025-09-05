package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lib.Platform;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;
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
        if (Platform.getInstance().isAndroid()) {
            waitForElementAndClick(
                    "id:org.wikipedia:id/fragment_onboarding_skip_button",
                    "can't find Skip button", 5);
        } else {
            new WelcomePageObject(driver).clickSkip();
        }
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

    enum Direction {
        LEFT("left"),
        UP("up"),
        DOWN("down");

        private String value;

        Direction(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    public void swipe(Direction direction, int swipeDuration) {
        if (Platform.getInstance().isIos()) {

            Dimension size = driver.manage().window().getSize();
            int x = size.getWidth() / 2;
            int startY = (int) (size.getHeight() * 0.8);
            int endY = (int) (size.getHeight() * 0.2);
//
//            JavascriptExecutor js = (JavascriptExecutor) driver;
//            js.executeScript("mobile: dragFromToForDuration",
//                    ImmutableMap.of("fromX", x, "fromY", startY,
//                            "toX", x, "toY", endY,
//                            "duration", swipeDuration / 1000.0));

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("mobile: swipe", Map.of(
                    "startX", x,
                    "startY", startY,
                    "endX", x,
                    "endY", endY,
                    "duration", swipeDuration / 1000,
                    "direction", direction.getValue(),
//                    "direction", "up",
                    "percent", 3.0 // Сила прокрутки
            ));
        } else {
            //for old appium
//            final TouchAction touchAction = new TouchAction(driver);
//            final Dimension size = driver.manage().window().getSize();
//            final int x = size.getWidth() / 2; // то есть середина экрана по ширине
//            final int startY = (int) (size.getHeight() * 0.8);
//            final int endY = (int) (size.getHeight() * 0.2);
//            final PointOption pointOption = PointOption.point(x, startY);
//            touchAction.press(pointOption).waitAction(WaitOptions.waitOptions(Duration.ofMillis(swipeDuration)))
//                    .moveTo(PointOption.point(x, endY)).release().perform();

            //for new appium. Because TouchAction not supported by the new appium
            Dimension size = driver.manage().window().getSize();

            String dir = "";
            if (direction.getValue().equals(Direction.UP)) {
                dir = Direction.DOWN.getValue();
            } else if (direction.getValue().equals(Direction.DOWN)) {
                dir = Direction.UP.getValue();
            }

            driver.executeScript("mobile: scrollGesture", Map.of(
                    "left", 0,
                    "top", (int) (size.getHeight() * 0.2),
                    "width", size.getWidth(),
                    "height", (int) (size.getHeight() * 0.6),
                    "direction", dir,
//                    "direction", "down",
                    "percent", 3.0, // Сила прокрутки
                    "speed", swipeDuration // Аналог duration
            ));
        }
    }

    public void swipeUp(int swipeDuration) {
        swipe(Direction.UP, swipeDuration);
    }

    public void swipeQuick(Direction direction) {
        swipe(direction, 1000);
    }

    public void swipeToFindElement(String locator, String errorMessage, int maxSwipes, Direction direction) {
        int alreadySwiped = 0;
        final By by = getLocatorByString(locator);
        while (driver.findElements(by).size() == 0) {
            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(locator, "Cant find element by swiping up. \n" + errorMessage, 0);
                return;
            }
            swipeQuick(direction);
            ++alreadySwiped;
        }
    }

    public void swipeToFindElement(String locator, String errorMessage, Direction direction) {
        final StopWatch stopWatch = StopWatch.createStarted();
        final By by = getLocatorByString(locator);

        if (driver.findElements(by).size() > 0) {
            MobileElement el = (MobileElement) driver.findElement(by);
            Dimension winSize = driver.manage().window().getSize();
            int screenHeight = winSize.getHeight();

            for (int i = 0; i < 10; i++) {
                int elCenterY = el.getCenter().getY();

                if (elCenterY > 0 && elCenterY < screenHeight) {
                    return;
                }
                swipeQuick(direction);
            }
            throw new RuntimeException("Не удалось проскролить до элемента " + by);
        } else {
            while (driver.findElements(by).size() == 0) {
                if (stopWatch.getTime(TimeUnit.SECONDS) > 10) {
                    waitForElementPresent(locator, "Cant find element by swiping up. \n" + errorMessage, 0);
                    return;
                }
                swipeQuick(direction);
            }
        }
    }

    public void swipeTillElementAppear(String locator, String errorMessage, int maxSwipes, Direction direction) {
        int alreadySwiped = 0;

        while (!isElementLocatedOnTheScreen(locator)) {
            if (alreadySwiped > maxSwipes) {
                Assertions.assertTrue(isElementLocatedOnTheScreen(locator), errorMessage);
            }
            swipeQuick(direction);
            ++alreadySwiped;
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
//        final WebElement element = waitForElementPresent(locator, errorMessage, 10);
//
//        final int startX = element.getLocation().getX();
//        final int startY = element.getLocation().getY();
//
//        final int offset = 200;
//        int endX = Math.max(0, startX - offset);
//
//        new TouchAction(driver)
//                .press(PointOption.point(startX, startY))
//                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(800)))
//                .moveTo(PointOption.point(endX, startY))
//                .release()
//                .perform();

        WebElement element = waitForElementPresent(locator, errorMessage, 10);

        int startX = element.getLocation().getX() + element.getSize().getWidth() / 2;
        int startY = element.getLocation().getY() + element.getSize().getHeight() / 2;
        int endX = Math.max(0, startX - 200);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("mobile: swipeGesture", Map.of(
                "left", startX - 100,    // Рассчитай область свайпа
                "top", startY - 100,
                "width", 200,
                "height", 200,
                "direction", "left",     // Направление
                "percent", 0.75          // Сила свайпа (75%)
        ));
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

    public boolean isElementLocatedOnTheScreen(String locator) {
        int elementLocationByY = waitForElementPresent(locator,
                "Cannot find element by locator " + locator, 1).getLocation().getY();
        Dimension winSize = driver.manage().window().getSize();
        int screenHeight = winSize.getHeight();
        return elementLocationByY < screenHeight;
    }

    public int getAmountOfElements(String locator) {
        final By by = getLocatorByString(locator);
        return driver.findElements(by).size();
    }

    public boolean isElementExist(String locator) {
        final By by = getLocatorByString(locator);
        List<WebElement> elements = driver.findElements(by);
        return !elements.isEmpty();
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

    public void closeIosModalWindow() {
        String closeButtonLocator = XPATH + "//XCUIElementTypeButton[@name='Закрыть']";

        By closeButton = getLocatorByString(closeButtonLocator);
        if (driver.findElements(closeButton).size() > 0) {
            waitForElementAndClick(closeButtonLocator, "cant find modal window close button", 5);
        }
    }
}
