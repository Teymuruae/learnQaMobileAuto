package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidArticlePageObject extends ArticlePageObject {

    public AndroidArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        FOOTER_ELEMENT = XPATH + "//android.view.View[@content-desc='View article in browser']";
        SAVE_ARTICLE_BUTTON = ID + "org.wikipedia:id/page_save";
        ADD_TO_ANOTHER_LIST_BUTTON = XPATH + "//android.widget.TextView[@text = 'Add to another reading list']";
        CREATE_NEW_READING_LIST_BUTTON = ID + "org.wikipedia:id/create_button";
        NAME_OF_READING_LIST_INPUT_FIELD = ID + "org.wikipedia:id/text_input";
        CREATE_NEW_READING_LIST_MODAL_OK_BUTTON = XPATH + "//android.widget.Button[@text = 'OK']";
        READING_LIST_FOLDER = XPATH + "//android.widget.TextView[@text='%s' and @resource-id = 'org.wikipedia:id/item_title' ]";
        TITLE = XPATH + "//android.widget.TextView[@text='%s']";
//        TITLE = XPATH + "//android.view.View[@content-desc='%s']";
    }
}