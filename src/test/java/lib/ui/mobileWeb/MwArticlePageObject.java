package lib.ui.mobileWeb;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MwArticlePageObject extends ArticlePageObject {

    public MwArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        FOOTER_ELEMENT = CSS + "footer[class = 'mw-footer minerva-footer']";
        SAVE_ARTICLE_BUTTON = XPATH + "//span[@class = 'minerva-icon minerva-icon--star']//parent::a[@id ='ca-watch']";
        REMOVE_ARTICLE_BUTTON = XPATH + "//span[@class = 'minerva-icon minerva-icon--unStar']//parent::a[@id ='ca-watch']";
        TITLE = CSS + "#firstHeading .mw-page-title-main";
    }
}