package lib.ui;

import io.appium.java_client.MobileElement;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import lib.Platform;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class MainPageObject {

    public static final String
            CSS = "css:",
            XPATH = "xpath:",
            ID = "id:";

    private final RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public MainPageObject skip() {
        if (Platform.getInstance().isAndroid()) {
            waitForElementAndClick(
                    "id:org.wikipedia:id/fragment_onboarding_skip_button",
                    "can't find Skip button", 5);
        } else if (Platform.getInstance().isIos()) {
            new WelcomePageObject(driver).clickSkip();
        }
        return this;
    }

    public WebElement waitForElementPresent(String locator, String errorMessage, long timeoutInSeconds) {
        final By by = getLocatorByString(locator);
        final WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
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

    public void tryClickElementWithFewAttempts(String locator, String errorMessage, int amountOfAttempts) {
        int currentAttempts = 0;
        boolean needMoreAttempts = true;

        while (needMoreAttempts) {
            try {
                waitForElementAndClick(locator, errorMessage, 2);
                needMoreAttempts = false;
            } catch (Exception e) {
                if (currentAttempts > amountOfAttempts) {
                    waitForElementAndClick(locator, errorMessage, 2);
                }
            }
            ++currentAttempts;
        }
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


    public void scrollWebPage(Direction direction) {
        String dir = direction.getValue().equals(Direction.DOWN.getValue())
                ? ""
                : "-";
        if (Platform.getInstance().isMobileWeb()) {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("window.scrollBy(0, 250)");
        }
    }

    public void scrollWebPageTillElementNotVisible(String locator, String errorMessage, int maxSwipes, Direction direction) {
        int alreadySwiped = 0;

        final WebElement element = waitForElementPresent(locator, errorMessage);

        while (!isElementLocatedOnTheScreen(locator)) {
            scrollWebPage(direction);
            ++alreadySwiped;

            if (alreadySwiped < maxSwipes) {
                Assertions.assertTrue(element.isDisplayed(), errorMessage);
            }
        }
    }

    public boolean isElementLocatedOnTheScreen(String locator) {
        int elementLocationByY = waitForElementPresent(locator,
                "Cannot find element by locator " + locator, 1).getLocation().getY();
        if (Platform.getInstance().isMobileWeb()) {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            final Object jsResult = jsExecutor.executeScript("return window.pageYOffset");
            elementLocationByY -= Integer.parseInt(jsResult.toString());
        }
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
        } else if ("css".equals(type)) {
            return By.cssSelector(locator);
        } else {
            throw new IllegalArgumentException("Cannot get type of locator " + locatorWithType);
        }
    }

    @Step("Закрытие модального окна")
    public void closeIosModalWindow() {
        String closeButtonLocator = XPATH + "//XCUIElementTypeButton[@name='Закрыть']";

        By closeButton = getLocatorByString(closeButtonLocator);
        if (driver.findElements(closeButton).size() > 0) {
            waitForElementAndClick(closeButtonLocator, "cant find modal window close button", 5);
        }
    }

    public String takeScreenshot(String name) {
        final TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        final String path = System.getProperty("user.dir") + "/" + name + "_screenshot.png";

        try {
            FileUtils.copyFile(source, new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    @Attachment
    public static byte[] screenshot(String path) {
        byte[] bytes = new byte[0];

        try {
           bytes = Files.readAllBytes(Path.of(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
