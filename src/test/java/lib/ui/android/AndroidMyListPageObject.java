package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListPageObject;

public class AndroidMyListPageObject extends MyListPageObject {

    public AndroidMyListPageObject(AppiumDriver driver) {
        super(driver);
    }

    static {
        FOLDER_BY_NAME_TPL = XPATH + "//*[@text = '%s']";
        ARTICLE_DESCRIPTION = XPATH + "//*[@resource-id = 'org.wikipedia:id/page_list_item_description'][@text = '%s']";
        ARTICLE_TITLE = XPATH + "//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][@text = '%s']";
        ARTICLE_TITLES = XPATH + "//*[@resource-id = 'org.wikipedia:id/page_list_item_title']";
    }
}