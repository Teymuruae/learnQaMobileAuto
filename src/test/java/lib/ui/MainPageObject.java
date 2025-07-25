package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainPageObject {

    private final AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }

    public MainPageObject skip() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "can't find Skip button", 5);
        return this;
    }

    public WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        final WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    public boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        final WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        final WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        final WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {
        final WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    public void swipeUp(int swipeDuration) {
        final TouchAction touchAction = new TouchAction(driver);
        final Dimension size = driver.manage().window().getSize();
        final int x = size.getWidth() / 2; // то есть середина экрана по ширине
        final int startY = (int) (size.getHeight() * 0.8);
        final int endY = (int) (size.getHeight() * 0.2);

        touchAction.press(x, startY).waitAction(swipeDuration).moveTo(x, endY).release().perform();
    }

    public void swipeUpQuick() {
        swipeUp(200);
    }

    public void swipeUpToFindElement(By by, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(by, "Cant find element by swiping up. \n" + errorMessage, 0);
                return;
            }
            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    public void swipeUpToFindElement(By by, String errorMessage) {
        final StopWatch stopWatch = StopWatch.createStarted();

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
                    waitForElementPresent(by, "Cant find element by swiping up. \n" + errorMessage, 0);
                    return;
                }
                swipeUpQuick();
            }
        }
    }

    public void swipeLeft(By by, String errorMessage) {
        final WebElement element = waitForElementPresent(by, errorMessage, 10);

        final int leftX = element.getLocation().getX();
        final int rightX = leftX + element.getSize().getWidth();
        final int upperY = element.getLocation().getY();
        final int lowerY = upperY + element.getSize().getHeight();
        final int middleY = (upperY + lowerY) / 2;

        final TouchAction action = new TouchAction(driver);

        action
                .press(rightX, middleY)
                .waitAction(300)
                .moveTo(leftX, middleY)
                .release()
                .perform();
    }

    public void swipeLeft2(By by, String errorMessage) {
        final WebElement element = waitForElementPresent(by, errorMessage, 10);

        final int startX = element.getLocation().getX();
        final int startY = element.getLocation().getY();

        final int offset = 200;
        int endX = Math.max(0, startX - offset);

        new TouchAction(driver)
                .press(startX, startY)
                .waitAction(800)
                .moveTo(endX, startY)
                .release()
                .perform();
    }

    public void swipeRight(By by, String errorMessage) {
        final WebElement element = waitForElementPresent(by, errorMessage, 10);

        final int startX = element.getLocation().getX();
        final int startY = element.getLocation().getY();

        new TouchAction(driver)
                .press(startX, startY)
                .waitAction(800)
                .moveTo(startX + 200, startY)
                .release()
                .perform();
    }

    public int getAmountOfElements(By by) {
        return driver.findElements(by).size();
    }

    public void assertElementNotPresent(By by, String errorMessage) {
        Assertions.assertTrue(getAmountOfElements(by) == 0, errorMessage);
    }

    public String waitForElementAndGetAttributeValue(By by, String attributeName, String errorMessage) {
        final WebElement element = waitForElementPresent(by, errorMessage);

        return element.getAttribute(attributeName);
    }

    public void assertElementHasText(By by, String expectedText, String errorMessage) {
        final String actualText = waitForElementAndGetAttributeValue(by, "text",
                "Element %s not found".formatted(by.toString()));
        Assertions.assertEquals(expectedText, actualText, errorMessage);
    }

    public void assertElementHasText(By by, String expectedText) {
        final String actualText = waitForElementAndGetAttributeValue(by, "text",
                "Element %s not found".formatted(by.toString()));
        Assertions.assertEquals(expectedText, actualText,
                "Texts are different. Expected: %s Actual: %s".formatted(expectedText, actualText));
    }

    public void assertAllElementsContainsText(By by, String expectedText) {
        final WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.withMessage("cant find element " + by.toString() + "\n");
        final List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));

        for (int i = 0; i < elements.size(); i++) {
            final String actualText = elements.get(i).getText();
            Assertions.assertTrue(actualText.contains(expectedText),
                    "Element by number %d with text %s doesnt contains %s".formatted(i, actualText, expectedText));
        }
    }
}
