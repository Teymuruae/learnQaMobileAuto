package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {
    private AppiumDriver driver;

    private final String
            SEARCH_RESULT_LIST_ELEMENTS = XPATH + "//*[@resource-id ='org.wikipedia:id/search_results_display']" +
            "//*[@resource-id = 'org.wikipedia:id/page_list_item_title']",
            SEARCH_INPUT_FIELD = XPATH + "//*[@text = 'Search Wikipedia']",
            SEARCH_CANCEL_BUTTON = ID + "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_EMPTY_LABEL = XPATH + "//android.widget.TextView[@text = 'No results']",
            SEARCH_RESULT = XPATH + "//*[@class = 'android.view.ViewGroup']//*[@text = '%s']",
            SEARCH_RESULT_BY_TITLE_AND_DESC = XPATH + "//*[@text = '%s' and @resource-id = " +
                    "'org.wikipedia:id/page_list_item_title']//following-sibling::android.widget.TextView[@text = '%s']";

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

    public SearchPageObject waitForCancelSearchButtonToAppear() {
        waitForElementPresent(SEARCH_CANCEL_BUTTON, "can't find X to cancel search");
        return this;
    }

    public SearchPageObject waitForCancelSearchButtonToDisappear() {
        waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "X is still present on the page", 5);
        return this;
    }

    public SearchPageObject clickCancelSearchButton() {
        waitForElementAndClick(SEARCH_CANCEL_BUTTON, "can't find X to cancel search", 5);
        return this;
    }

    public ArticlePageObject clickArticle(String articleText) {
        waitForElementAndClick(
                XPATH + "//*[@class = 'android.view.ViewGroup']//*[@text = '%s' and not(@resource-id = 'org.wikipedia:id/search_src_text')]"
                        .formatted(articleText),
                "Cant find article " + articleText,
                5
        );
        return new ArticlePageObject(driver);
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