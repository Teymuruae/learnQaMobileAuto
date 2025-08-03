package tests;

import lib.CoreTestCase;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class SearchPageTest extends CoreTestCase {

    private MainPageObject mainPageObject = new MainPageObject(driver);
    private SearchPageObject searchPageObject = new SearchPageObject(driver);

    @Test
    public void searchTest() {
        mainPageObject.skip();
        searchPageObject
                .initSearchInput()
                .typeSearchLine("Java")
                .waitForSearchResult("Object-oriented programming language");
    }

    @Test
    void cancelSearchTest() {
        mainPageObject.skip();
        searchPageObject
                .initSearchInput()
                .typeSearchLine("Java")
                .waitForCancelSearchButtonToAppear()
                .clickCancelSearchButton()
                .waitForCancelSearchButtonToDisappear();
    }

    @Test
    void notEmptySearchResultTest() {
        final String searchInput = "Linkin park discography";
        mainPageObject.skip();

        searchPageObject
                .initSearchInput()
                .typeSearchLine(searchInput);

        int amountOfSearchResult = searchPageObject.getAmountOfFoundArticles();

        Assertions.assertTrue(amountOfSearchResult > 0, "Search result for %s is empty".formatted(searchInput));
    }

    @Test
    void amountOfEmptySearchTest() {
        final String searchInput = "ljkj;jgasd";
        mainPageObject.skip();

        searchPageObject
                .initSearchInput()
                .typeSearchLine(searchInput)
                .waitForEmptyResultsLabel()
                .assertThereIsNoResultOfSearch();
    }

    @Test
    void searchFieldPlaceholderTextTest() {
        mainPageObject
                .skip()
                .assertElementHasText(By
                        .xpath("//*[@text = 'Search Wikipedia']"), "Search Wikipedia");
    }

    @Test
    void cancelSearchTest2() {
        final String searchInput = "Java";

        mainPageObject.skip();
        searchPageObject
                .initSearchInput()
                .typeSearchLine(searchInput)
                .assertSearchResultNotEmpty()
                .clickCancelSearchButton()
                .assertThereIsNoResultOfSearch();
    }

    @Test
    void checkSearchResultsContainsSearchInputTextTest() {
        final String searchInput = "Java";
        mainPageObject.skip();

        searchPageObject
                .initSearchInput()
                .typeSearchLine(searchInput)
                .assertAllSearchResultsContainText(searchInput);
    }
}