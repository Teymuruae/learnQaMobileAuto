package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class IosArticlePageObject extends ArticlePageObject {

    public IosArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    static {
        FOOTER_ELEMENT = XPATH + "//XCUIElementTypeLink[@name='View article in browser']";
        SAVE_ARTICLE_BUTTON = XPATH + "//XCUIElementTypeButton[@name='Сохранить на потом']";
        TITLE = XPATH + "//XCUIElementTypeStaticText[@name='%s']";
    }
}