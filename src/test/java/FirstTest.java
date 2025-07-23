import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class FirstTest {

    private AppiumDriver driver;

    @BeforeEach
    void setUp() throws Exception {
        String separator = File.separator;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.1");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app",
                "%s%sapks/org.wikipedia.apk".formatted(System.getProperty("user.dir"), separator));

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void test() {
        final WebElement searchFieldToClick = waitForElementPresent(
                By.xpath("//*[@text = 'Search Wikipedia']"),
                "can't find search input");
        searchFieldToClick.click();

        final WebElement searchFieldToSendKeys = waitForElementPresent(
                By.xpath("//*[@text = 'Search Wikipedia']"),
                "can't find search input");
        searchFieldToSendKeys.sendKeys("Java");

        waitForElementPresent(
//                By.xpath("//*[@class = 'android.widget.LinearLayout']//*[@text = 'Object-oriented programming language']"),
                By.xpath("//*[@class = 'android.view.ViewGroup']//*[@text = 'Object-oriented programming language']"),
                "Cant find 'Object-oriented programming language' topic searching by Java",
                15
        );
    }

    @Test
    void test2() {
        waitForElementAndClick(
                By.xpath("//*[@text = 'Search Wikipedia']"),
                "can't find search input", 5);

        waitForElementAndSendKeys(
                By.xpath("//*[@text = 'Search Wikipedia']"),
                "Java",
                "can't find search input", 5);

        waitForElementPresent(
                By.xpath("//*[@class = 'android.view.ViewGroup']//*[@text = 'Object-oriented programming language']"),
                "Cant find 'Object-oriented programming language' topic searching by Java",
                15
        );
    }

    @Test
    void cancelSearchTest() {
        waitForElementAndClick(
                By.xpath("//*[@text = 'Search Wikipedia']"),
                "can't find search input", 5);

        waitForElementAndSendKeys(
                By.xpath("//*[@text = 'Search Wikipedia']"),
                "Java",
                "can't find search input", 5);

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "can't find search input",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "can't find X to cancel search", 5);

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X is still present on the page", 5);
    }

    @Test
    void javaTopicArticleTextTest() {
        waitForElementAndClick(
                By.xpath("//*[@text = 'Search Wikipedia']"),
                "can't find search input", 5);

        waitForElementAndSendKeys(
                By.xpath("//*[@text = 'Search Wikipedia']"),
                "Java",
                "can't find search input", 5);

        waitForElementAndClick(
                By.xpath("//*[@class = 'android.view.ViewGroup']//*[@text = 'Object-oriented programming language']"),
                "Cant find 'Object-oriented programming language' topic searching by Java",
                5
        );

        WebElement title = waitForElementPresent(
                By.xpath("//android.view.View[@content-desc='Java (programming language)']"),
                "Cant find topic title",
                15
        );

        final String articleTitle = title.getAttribute("name");

        Assertions.assertEquals("Java (programming language)", articleTitle,
                "Expected title text: Java (programming language). But actual: " + articleTitle);
    }

    @Test
    void swipeArticleTest() {
        waitForElementAndClick(
                By.xpath("//*[@text = 'Search Wikipedia']"),
                "can't find search input", 5);

        waitForElementAndSendKeys(
                By.xpath("//*[@text = 'Search Wikipedia']"),
                "Java",
                "can't find search input", 5);

        waitForElementAndClick(
                By.xpath("//*[@class = 'android.view.ViewGroup']//*[@text = 'Object-oriented programming language']"),
                "Cant find 'Object-oriented programming language' topic searching by Java",
                5
        );

        WebElement title = waitForElementPresent(
                By.xpath("//android.view.View[@content-desc='Java (programming language)']"),
                "Cant find topic title",
                15
        );

        swipeUp(2000);
        swipeUp(2000);
        swipeUp(2000);
        swipeUp(2000);
        swipeUp(2000);
        swipeUp(2000);
    }

    @Test
    void swipeToElementTest() {
        waitForElementAndClick(
                By.xpath("//*[@text = 'Search Wikipedia']"),
                "can't find search input", 5);

        waitForElementAndSendKeys(
                By.xpath("//*[@text = 'Search Wikipedia']"),
                "Appium",
                "can't find search input", 5);

        waitForElementAndClick(
                By.xpath("//*[@class = 'android.view.ViewGroup']//*[@text = 'Automation for Apps']"),
                "Cant find 'Automation for Apps' topic searching by Appium",
                5
        );

        WebElement title = waitForElementPresent(
                By.xpath("//android.view.View[@content-desc='Automation for Apps']"),
                "Cant find topic title",
                15
        );

        By by = By.xpath("//android.view.View[@content-desc='View article in browser']");
//        By by = By.xpath("//android.view.View[@content-desc='CC BY-SA 4.0']");

        swipeUpToFindElement(by, "Cant find the end of the article");

//        driver.findElement(by).click();
    }

    @Test
    void swipeLeftTest() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "can't find Skip button", 5);

        waitForElementAndClick(
                By.xpath("//*[@text = 'Search Wikipedia']"),
                "can't find search input", 5);

        waitForElementAndSendKeys(
                By.xpath("//*[@text = 'Search Wikipedia']"),
                "Java",
                "can't find search input", 5);

        waitForElementAndClick(
                By.xpath("//*[@class = 'android.view.ViewGroup']//*[@text = 'Object-oriented programming language']"),
                "Cant find 'Object-oriented programming language' topic searching by Java",
                5
        );

        waitForElementPresent(
                By.xpath("//android.view.View[@content-desc='Java (programming language)']"),
                "Cant find topic title",
                15
        );

        WebElement saveButton = waitForElementAndClick(
                By.id("org.wikipedia:id/page_save"),
                "Cant find save page button",
                5
        );

        saveButton.click();

        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@text = 'Add to another reading list']"),
                "Cant find 'Add to another reading list' button",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/create_button"),
                "Cant find 'Create' button",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                "Java",
                "Cant find 'Name of list' input field",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.Button[@text = 'OK']"),
                "Cant find 'Create' button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cant find 'Navigate up' button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cant find 'Navigate up' button",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/nav_tab_reading_lists"),
                "Cant find navigate button to my list",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text = 'Java']"),
                "Cant find Java list",
                5
        );

        By savedPage = By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_description'][@text = 'Object-oriented programming language']");
        waitForElementPresent(
                savedPage,
                "Cant find 'Object-oriented programming language' saved page",
                5
        );

        swipeLeft2(savedPage, "Cant find 'Object-oriented programming language' saved page");

        waitForElementNotPresent(savedPage, "Saved page 'Object-oriented programming language' is still present", 7);
    }

    @Test
    void notEmptySearchResultTest() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "can't find Skip button", 5);

        final By searchField = By.xpath("//*[@text = 'Search Wikipedia']");

        waitForElementAndClick(
                searchField,
                "can't find search input", 5);

        final String searchInput = "Linkin park discography";
        waitForElementAndSendKeys(
                searchField,
                searchInput,
                "can't find search input", 5);

        final By searchResultItems = By.xpath("//*[@resource-id ='org.wikipedia:id/search_results_display']" +
                "//*[@resource-id = 'org.wikipedia:id/page_list_item_title']");

        waitForElementPresent(
                searchResultItems,
                "Cant find anything by the request " + searchInput
        );

        int amountOfSearchResult = getAmountOfElements(searchResultItems);
        Assertions.assertTrue(amountOfSearchResult > 0, "Search result for %s is empty".formatted(searchInput));
    }


    @Test
    void amountOfEmptySearchTest() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "can't find Skip button", 5);

        final By searchField = By.xpath("//*[@text = 'Search Wikipedia']");

        waitForElementAndClick(
                searchField,
                "can't find search input", 5);


        final String searchInput = "ljkj;jgasd";
        waitForElementAndSendKeys(
                searchField,
                searchInput,
                "can't find search input", 5);

        final By searchResultItems = By.xpath("//*[@resource-id ='org.wikipedia:id/search_results_display']" +
                "//*[@resource-id = 'org.wikipedia:id/page_list_item_title']");

        waitForElementPresent(
                By.xpath("//android.widget.TextView[@text = 'No results']"),
                "cant find empty result label by the request " + searchInput
        );

        assertElementNotPresent(searchResultItems, "Search result by input %s is not empty".formatted(searchInput));
    }

    @Test
    void changeScreenOrientationOnSearchResultTest() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "can't find Skip button", 5);

        waitForElementAndClick(
                By.xpath("//*[@text = 'Search Wikipedia']"),
                "can't find search input", 5);

        waitForElementAndSendKeys(
                By.xpath("//*[@text = 'Search Wikipedia']"),
                "Java",
                "can't find search input", 5);

        waitForElementAndClick(
                By.xpath("//*[@class = 'android.view.ViewGroup']//*[@text = 'Object-oriented programming language']"),
                "Cant find 'Object-oriented programming language' topic searching by Java",
                5
        );

        String titleBeforeRotation = waitForElementAndGetAttributeValue(
                By.xpath("//android.view.View[@content-desc='Java (programming language)']"),
                "text",
                "Cant find topic title"
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String titleAfterRotation  = waitForElementAndGetAttributeValue(
                By.xpath("//android.view.View[@content-desc='Java (programming language)']"),
                "text",
                "Cant find topic title"
        );

        Assertions.assertEquals(titleBeforeRotation, titleAfterRotation,
                "Titles after rotation are different. Before: %s, After: %s".formatted(titleBeforeRotation, titleAfterRotation));

        driver.rotate(ScreenOrientation.PORTRAIT);

        String titleAfterSecondRotation  = waitForElementAndGetAttributeValue(
                By.xpath("//android.view.View[@content-desc='Java (programming language)']"),
                "text",
                "Cant find topic title"
        );

        Assertions.assertEquals(titleAfterRotation, titleAfterSecondRotation,
                "Titles after rotation are different. Before: %s, After: %s".formatted(titleBeforeRotation, titleAfterRotation));
    }

    @Test
    void checkArticleAfterCollapseAndExpandAppTest() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "can't find Skip button", 5);

        waitForElementAndClick(
                By.xpath("//*[@text = 'Search Wikipedia']"),
                "can't find search input", 5);

        waitForElementAndSendKeys(
                By.xpath("//*[@text = 'Search Wikipedia']"),
                "Java",
                "can't find search input", 5);

        waitForElementAndClick(
                By.xpath("//*[@class = 'android.view.ViewGroup']//*[@text = 'Object-oriented programming language']"),
                "Cant find 'Object-oriented programming language' topic searching by Java",
                5
        );

        waitForElementPresent(
                By.xpath("//android.view.View[@content-desc='Java (programming language)']"),
                "Cant find topic title"
        );

        driver.runAppInBackground(4);

        waitForElementPresent(
                By.xpath("//android.view.View[@content-desc='Java (programming language)']"),
                "Cant find topic title after expand the app"
        );
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        final WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        final WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        final WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        final WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {
        final WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    private void swipeUp(int swipeDuration) {
        final TouchAction touchAction = new TouchAction(driver);
        final Dimension size = driver.manage().window().getSize();
        final int x = size.getWidth() / 2; // то есть середина экрана по ширине
        final int startY = (int) (size.getHeight() * 0.8);
        final int endY = (int) (size.getHeight() * 0.2);

        touchAction.press(x, startY).waitAction(swipeDuration).moveTo(x, endY).release().perform();
    }

    private void swipeUpQuick() {
        swipeUp(200);
    }

    private void swipeUpToFindElement(By by, String errorMessage, int maxSwipes) {
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

    private void swipeUpToFindElement(By by, String errorMessage) {
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

    private void swipeLeft(By by, String errorMessage) {
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

    private void swipeLeft2(By by, String errorMessage) {
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

    private void swipeRight(By by, String errorMessage) {
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

    private int getAmountOfElements(By by) {
        return driver.findElements(by).size();
    }

    private void assertElementNotPresent(By by, String errorMessage) {
        Assertions.assertTrue(getAmountOfElements(by) == 0, errorMessage);
    }

    private String waitForElementAndGetAttributeValue(By by, String attributeName, String errorMessage) {
        final WebElement element = waitForElementPresent(by, errorMessage);

        return element.getAttribute(attributeName);
    }
}