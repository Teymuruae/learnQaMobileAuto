package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListPageObjectFactory;
import lib.ui.factories.NavigationUiPageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.jupiter.api.Test;

public class MyListTest extends CoreTestCase {

    private MainPageObject mainPageObject = new MainPageObject(driver);
    private SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
    private ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
    private NavigationUi navigationUi = NavigationUiPageObjectFactory.get(driver);
    private MyListPageObject myListPageObject = MyListPageObjectFactory.get(driver);

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

        articlePageObject
                .addArticleToMyNewList(myListFolder)
                .toMainMenu(articleTitle, 2);

        navigationUi.clickMyList();

        if (Platform.getInstance().isAndroid()) {
            myListPageObject
                    .openFolderByName(myListFolder);
        } else {
            myListPageObject.closeIosModalWindow();
        }

        myListPageObject
                .removeArticleByTitle(articleTitle);
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

        articlePageObject
                .addArticleToMyNewList(myListFolder)
                .toMainMenu(javaArticleTitle, 2);

        searchPageObject
                .initSearchInput()
                .typeSearchLine(javaVersionHistoryArticleTitle)
                .clickArticle(javaVersionHistoryArticleTitle)
                .waitForTitleElement(javaVersionHistoryArticleTitle);

        articlePageObject
                .addArticleToMyExistList(myListFolder)
                .toMainMenu(javaVersionHistoryArticleTitle, 2);

        navigationUi.clickMyList();

        if (Platform.getInstance().isAndroid()) {
            myListPageObject
                    .openFolderByName(myListFolder);
        } else {
            myListPageObject.closeIosModalWindow();
        }

        myListPageObject
                .assertAllArticleTitleTexts(javaArticleTitle, javaVersionHistoryArticleTitle)
                .removeArticleByTitle(javaArticleTitle)
                .assertAllArticleTitleTexts(javaVersionHistoryArticleTitle)
                .enterArticleByTitle(javaVersionHistoryArticleTitle)
                .waitForTitleElement(javaVersionHistoryArticleTitle);
    }
}