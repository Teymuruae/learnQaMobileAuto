package lib.ui.mobileWeb;

import lib.ui.MyListPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MwMyListPageObject extends MyListPageObject {

    public MwMyListPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        ARTICLE_DESCRIPTION = XPATH + "//*[@resource-id = 'org.wikipedia:id/page_list_item_description'][@text = '%s']";
        ARTICLE_TITLE = XPATH + "//li[@class = 'page-summary with-watchstar' and @title = '%s']";
        ARTICLE_TITLES = XPATH + "//*[@resource-id = 'org.wikipedia:id/page_list_item_title']";
        REMOVE_STAR_BUTTON = XPATH + "//li[@class = 'page-summary with-watchstar' and @title = '%s']//a[@type" +
                " = 'button' and contains(@class, 'watch-this-article watched')]";
    }
}