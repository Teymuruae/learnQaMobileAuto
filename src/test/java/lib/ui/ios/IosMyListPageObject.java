package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListPageObject;

public class IosMyListPageObject extends MyListPageObject {

    public IosMyListPageObject(AppiumDriver driver) {
        super(driver);
    }

    static {
        ARTICLE_DESCRIPTION = XPATH + "//*[@resource-id = 'org.wikipedia:id/page_list_item_description'][@text = '%s']";
        ARTICLE_TITLE = XPATH + "//XCUIElementTypeStaticText[@name='%s']";
        ARTICLE_TITLES = XPATH + "//*[@resource-id = 'org.wikipedia:id/page_list_item_title']";
        DONT_SAVE_BUTTON = XPATH + "//XCUIElementTypeButton[@name='Не сохранено']";
        EDIT_BUTTON = XPATH + "//XCUIElementTypeButton[@name='Изменить']";
    }
}