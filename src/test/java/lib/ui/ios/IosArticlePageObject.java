package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IosArticlePageObject extends ArticlePageObject {

    public IosArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        FOOTER_ELEMENT = XPATH + "//XCUIElementTypeLink[@name='View article in browser']";
        SAVE_ARTICLE_BUTTON = XPATH + "//XCUIElementTypeButton[@name='Сохранить на потом']";
        TITLE = XPATH + "//XCUIElementTypeStaticText[@name='%s']";
    }
}