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
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "can't find Skip button", 5);

        mainPageObject.assertElementHasText(By.xpath("//*[@text = 'Search Wikipedia']"), "Search Wikipedia");
    }

    @Test
    void cancelSearchTest2() {
        final String searchInput = "Java";
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "can't find Skip button", 5);

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text = 'Search Wikipedia']"),
                "can't find search input", 5);

        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@text = 'Search Wikipedia']"),
                searchInput,
                "can't find search input", 5);

        final By searchResultItems = By.xpath("//*[@resource-id ='org.wikipedia:id/search_results_display']" +
                "//*[@resource-id = 'org.wikipedia:id/page_list_item_title']");

        mainPageObject.waitForElementPresent(
                searchResultItems,
                "Cant find anything by the request " + searchInput
        );

        int amountOfSearchResult = mainPageObject.getAmountOfElements(searchResultItems);
        Assertions.assertTrue(amountOfSearchResult > 0, "Search result for %s is empty".formatted(searchInput));

        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "can't find X to cancel search", 5);

        mainPageObject.assertElementNotPresent(searchResultItems, "Search result for %s is  not empty".formatted(searchInput));
    }

    @Test
    void checkSearchResultsContainsSearchInputTextTest() {
        final String searchInput = "Java";
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "can't find Skip button", 5);

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text = 'Search Wikipedia']"),
                "can't find search input", 5);

        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@text = 'Search Wikipedia']"),
                searchInput,
                "can't find search input", 5);

        final By searchResultItems = By.xpath("//*[@resource-id ='org.wikipedia:id/search_results_display']" +
                "//*[@resource-id = 'org.wikipedia:id/page_list_item_title']");

        mainPageObject.assertAllElementsContainsText(searchResultItems, searchInput);
    }
}