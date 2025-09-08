package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObject extends MainPageObject {

    public WelcomePageObject clickSkip() {
        waitForElementAndClick(
                XPATH + "//XCUIElementTypeStaticText[@name='Пропустить']",
                "Cannot find button 'Пропустить'",
                10
        );
        return this;
    }

    public WelcomePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public WelcomePageObject waitForLearnMoreLink() {
        waitForElementPresent(XPATH + "//XCUIElementTypeStaticText[@name='Узнать подробнее о Википедии']",
                "Cant find link by text 'Узнать подробнее о Википедии'");
        return this;
    }

    public WelcomePageObject waitForNewWayToExploreText() {
        waitForElementPresent(XPATH + "//XCUIElementTypeStaticText[@name='Новые способы изучения']",
                "Cant find text 'Новые способы изучения'");
        return this;
    }

    public WelcomePageObject waitForAddOrEditPreferredLangText() {
        waitForElementPresent(XPATH + "//XCUIElementTypeStaticText[@name='Добавить или изменить предпочтительные языки']",
                "Cant find text 'Добавить или изменить предпочтительные языки'");
        return this;
    }

    public WelcomePageObject waitForLearnMoreAboutDataCollectedText() {
        waitForElementPresent(XPATH + "//XCUIElementTypeStaticText[@name='Узнать подробнее о сборе данных']",
                "Cant find text 'Узнать подробнее о сборе данных'");
        return this;
    }

    public WelcomePageObject clickNextButton() {
        waitForElementAndClick(XPATH + "//XCUIElementTypeButton[@name='Далее']",
                "Cant find button 'Далее'", 10);
        return this;
    }

    public void clickGetStartedButton() {
        waitForElementAndClick(XPATH + "//XCUIElementTypeButton[@name='Начать']",
                "Cant find button 'Начать'", 10);
    }
}