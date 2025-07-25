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
        articlePageObject.addArticleToMyList(myListFolder);

        for (int i = 0; i < 2; i++) {
            navigationUi.navigateUp();
        }
        navigationUi.clickMyList();
        myListPageObject
                .openFolderByName(myListFolder)
                .waitForArticleToAppearByTitle(articleTitle)
                .swipeByArticleTitleToDelete(articleTitle);
    }
}