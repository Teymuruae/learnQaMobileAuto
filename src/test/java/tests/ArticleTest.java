package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Features;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.management.DescriptorKey;

@Epic("Tests for articles")
public class ArticleTest extends CoreTestCase {

    private MainPageObject mainPageObject = new MainPageObject(driver);
    private SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
    private ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

    @Features(value = {
            @Feature(value = "Article"),
            @Feature(value = "Search")
    })
    @Description("Через поиск ищем статью про java и переходим в неё")
    @DisplayName("Проверка перехода на страницу статьи")
    @Severity(value = SeverityLevel.NORMAL)
    @Test
    void javaTopicArticleTextTest() {
        mainPageObject.skip();
        searchPageObject
                .initSearchInput()
                .typeSearchLine("Java")
                .clickArticle("Object-oriented programming language")
                .waitForTitleElement("Java (programming language)");

        mainPageObject.screenshot(mainPageObject.takeScreenshot("Java title"));
    }


    @DisplayName("Проверка свайпа статьи")
    @Severity(value = SeverityLevel.MINOR)
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

    @DisplayName("Проверка свайпа статьи до элемента")
    @Severity(value = SeverityLevel.MINOR)
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

    @DisplayName("Проверка наличия заголовка в статье")
    @Severity(value = SeverityLevel.NORMAL)
    @Test
    void assertArticleTitleIsPresentTest() {
        mainPageObject.skip();
        searchPageObject
                .initSearchInput()
                .typeSearchLine("Java")
                .clickArticle("Object-oriented programming language")
                .assertTitleIsPresent("Java (programming language)");
    }
}