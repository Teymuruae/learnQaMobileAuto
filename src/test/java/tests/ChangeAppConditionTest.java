package tests;

import io.appium.java_client.AppiumDriver;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;

import javax.xml.xpath.XPath;

public class ChangeAppConditionTest extends CoreTestCase {

    private MainPageObject mainPageObject = new MainPageObject(driver);
    private SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
    private ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

    @Test
    void changeScreenOrientationOnSearchResultTest() {
        if (Platform.getInstance().isMobileWeb()) {
            return;
        }

        final String articleTitle = "Java (programming language)";

            final AppiumDriver appiumDriver = (AppiumDriver) driver;

            try {
                mainPageObject.skip();
                searchPageObject
                        .initSearchInput()
                        .typeSearchLine("Java")
                        .clickArticle("Object-oriented programming language")
                        .waitForTitleElement(articleTitle);

                String titleBeforeRotation = mainPageObject.waitForElementAndGetAttributeValue(
                        MainPageObject.XPATH + "//android.view.View[@content-desc='Java (programming language)']",
                        "name",
                        "Cant find topic title"
                );

                rotateScreenLandscape();

                String titleAfterRotation = mainPageObject.waitForElementAndGetAttributeValue(
                        MainPageObject.XPATH + "//android.view.View[@content-desc='Java (programming language)']",
                        "name",
                        "Cant find topic title"
                );

                Assertions.assertEquals(titleBeforeRotation, titleAfterRotation,
                        "Titles after rotation are different. Before: %s, After: %s".formatted(titleBeforeRotation, titleAfterRotation));

                rotateScreenPortrait();

                String titleAfterSecondRotation = mainPageObject.waitForElementAndGetAttributeValue(
                        MainPageObject.XPATH + "//android.view.View[@content-desc='Java (programming language)']",
                        "name",
                        "Cant find topic title"
                );

                Assertions.assertEquals(titleAfterRotation, titleAfterSecondRotation,
                        "Titles after rotation are different. Before: %s, After: %s".formatted(titleBeforeRotation, titleAfterRotation));
            } finally {
                final ScreenOrientation orientation = appiumDriver.getOrientation();
                if (orientation.value().equals(ScreenOrientation.LANDSCAPE.value())) {
                    rotateScreenPortrait();
                }
            }
    }

    @Test
    void checkArticleAfterCollapseAndExpandAppTest() {
        if (Platform.getInstance().isMobileWeb()) {
            return;
        }

        final String articleTitle = "Java (programming language)";
        mainPageObject.skip();
        searchPageObject
                .initSearchInput()
                .typeSearchLine("Java")
                .clickArticle("Object-oriented programming language")
                .waitForTitleElement(articleTitle);

        backgroundApp(2);

        articlePageObject.waitForTitleElement(articleTitle);
    }
}