package tests;

import lib.CoreTestCase;
import lib.ui.*;
import org.junit.jupiter.api.Test;

public class MyListTest extends CoreTestCase {

    private MainPageObject mainPageObject = new MainPageObject(driver);
    private SearchPageObject searchPageObject = new SearchPageObject(driver);
    private ArticlePageObject articlePageObject = new ArticlePageObject(driver);
    private NavigationUi navigationUi = new NavigationUi(driver);
    private MyListPageObject myListPageObject = new MyListPageObject(driver);

    @Test
    void swipeLeftTest() {
        final String myListFolder = "JavaList";
        final String articleTitle = "Java (programming language)";
        mainPageObject.skip();
        searchPageObject
                .initSearchInput()
                .typeSearchLine("Java")
                .clickArticle("Object-oriented programming language")
                .waitForTitleElement(articleTitle);
        articlePageObject.addArticleToMyNewList(myListFolder);

        for (int i = 0; i < 2; i++) {
            navigationUi.navigateUp();
        }
        navigationUi.clickMyList();
        myListPageObject
                .openFolderByName(myListFolder)
                .waitForArticleToAppearByTitle(articleTitle)
                .swipeByArticleTitleToDelete(articleTitle);
    }

    @Test
    void saveTwoAndRemoveOneArticleTest() {
        final String myListFolder = "JavaList";
        final String javaArticleTitle = "Java (programming language)";
        final String javaVersionHistoryArticleTitle = "Java version history";

        mainPageObject.skip();
        searchPageObject
                .initSearchInput()
                .typeSearchLine(javaArticleTitle)
                .clickArticle(javaArticleTitle)
                .waitForTitleElement(javaArticleTitle);
        articlePageObject.addArticleToMyNewList(myListFolder);

        searchPageObject
                .initSearchInput()
                .typeSearchLine(javaVersionHistoryArticleTitle)
                .clickArticle(javaVersionHistoryArticleTitle)
                .waitForTitleElement(javaVersionHistoryArticleTitle);
        articlePageObject.addArticleToMyExistList(myListFolder);

        for (int i = 0; i < 3; i++) {
            navigationUi.navigateUp();
        }

        navigationUi.clickMyList();
        myListPageObject
                .openFolderByName(myListFolder)
                .assertAllArticleTitleTexts(javaArticleTitle, javaVersionHistoryArticleTitle)
                .swipeByArticleTitleToDelete(javaArticleTitle)
                .assertAllArticleTitleTexts(javaVersionHistoryArticleTitle)
                .enterArticleByTitle(javaVersionHistoryArticleTitle)
                .waitForTitleElement(javaVersionHistoryArticleTitle);
    }
}