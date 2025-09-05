package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.ui.factories.ArticlePageObjectFactory;
import org.junit.jupiter.api.Assertions;

public abstract class SearchPageObject extends MainPageObject {

    private AppiumDriver driver;

    protected static String
            SEARCH_RESULT_LIST_ELEMENTS,
            SEARCH_INPUT_FIELD,
            SEARCH_REMOVE_X_BUTTON,
            SEARCH_RESULT_EMPTY_LABEL,
            SEARCH_RESULT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_TITLE_AND_DESC;

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public SearchPageObject initSearchInput() {
        waitForElementAndClick(SEARCH_INPUT_FIELD, "cant find and click search input field", 5);
        return this;
    }

    public SearchPageObject typeSearchLine(String searchText) {
        waitForElementAndSendKeys(SEARCH_INPUT_FIELD, searchText,
                "Cant find and type into seach input field", 5);
        return this;
    }

    public SearchPageObject waitForSearchResult(String resultText) {
        waitForElementPresent(
                SEARCH_RESULT.formatted(resultText), "Cant find search result text: %s".formatted(resultText));
        return this;
    }

    public SearchPageObject waitForElementByTitleAndDescription(String title, String description) {
        waitForElementPresent(SEARCH_RESULT_BY_TITLE_AND_DESC.formatted(title, description),
                "Search result with title %s and description %s not found"
                        .formatted(title, description));
        return this;
    }

    public SearchPageObject waitForSearchRemoveXButtonToAppear() {
        waitForElementPresent(SEARCH_REMOVE_X_BUTTON, "can't find X to cancel search");
        return this;
    }

    public SearchPageObject waitForSearchRemoveXButtonToDisappear() {
        waitForElementNotPresent(SEARCH_REMOVE_X_BUTTON, "X is still present on the page", 5);
        return this;
    }

    public SearchPageObject clickSearchRemoveXButton() {
        waitForElementAndClick(SEARCH_REMOVE_X_BUTTON, "can't find X to cancel search", 5);
        return this;
    }

    public SearchPageObject clickSearchCancelButton() {
        waitForElementAndClick(SEARCH_CANCEL_BUTTON, "can't find cancel search button", 5);
        return this;
    }

    public ArticlePageObject clickArticle(String articleText) {
        waitForElementAndClick(
                SEARCH_RESULT
                        .formatted(articleText),
                "Cant find article " + articleText,
                5
        );
        return ArticlePageObjectFactory.get(driver);
    }

    public int getAmountOfFoundArticles() {
        waitForElementPresent(
                SEARCH_RESULT_LIST_ELEMENTS,
                "Cant find anything by the request "
        );

        return getAmountOfElements(SEARCH_RESULT_LIST_ELEMENTS);
    }

    public SearchPageObject assertSearchResultNotEmpty() {
        Assertions.assertTrue(getAmountOfFoundArticles() > 0, "Search result should be not empty, but" +
                "was empty");
        return this;
    }

    public SearchPageObject waitForEmptyResultsLabel() {
        waitForElementPresent(
                SEARCH_RESULT_EMPTY_LABEL,
                "cant find empty result label by the request "
        );
        return this;
    }

    public SearchPageObject assertThereIsNoResultOfSearch() {
        assertElementNotPresent(SEARCH_RESULT_LIST_ELEMENTS, "Search result is not empty, but suppose to be");
        return this;
    }

    public SearchPageObject assertAllSearchResultsContainText(String text) {
        assertAllElementsContainsText(SEARCH_RESULT_LIST_ELEMENTS, text);
        return this;
    }
}