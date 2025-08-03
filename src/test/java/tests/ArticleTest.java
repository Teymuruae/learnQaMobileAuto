package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class ArticleTest extends CoreTestCase {

    private MainPageObject mainPageObject = new MainPageObject(driver);
    private SearchPageObject searchPageObject = new SearchPageObject(driver);
    private ArticlePageObject articlePageObject = new ArticlePageObject(driver);

    @Test
    void javaTopicArticleTextTest() {
        mainPageObject.skip();
        searchPageObject
                .initSearchInput()
                .typeSearchLine("Java")
                .clickArticle("Object-oriented programming language")
                .waitForTitleElement("Java (programming language)");
    }

    @Test
    void swipeArticleTest() {
        mainPageObject.skip();
        searchPageObject
                .initSearchInput()
                .typeSearchLine("Java")
                .clickArticle("Object-oriented programming language")
                .waitForTitleElement("Java (programming language)");

        mainPageObject.swipeUp(2000);
        mainPageObject.swipeUp(2000);
        mainPageObject.swipeUp(2000);
        mainPageObject.swipeUp(2000);
        mainPageObject.swipeUp(2000);
    }

    @Test
    void swipeToElementTest() {
        mainPageObject.skip();
        searchPageObject
                .initSearchInput()
                .typeSearchLine("Appium")
                .clickArticle("Automation for Apps")
                .waitForTitleElement("Automation for Apps");

        articlePageObject.swipeToFooter();
    }
}