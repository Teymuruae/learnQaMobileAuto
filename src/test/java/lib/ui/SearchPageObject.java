package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {
    private AppiumDriver driver;

    private final By
            SEARCH_RESULT_LIST_ELEMENTS = By.xpath("//*[@resource-id ='org.wikipedia:id/search_results_display']" +
            "//*[@resource-id = 'org.wikipedia:id/page_list_item_title']"),
            SEARCH_INPUT_FIELD = By.xpath("//*[@text = 'Search Wikipedia']"),
            SEARCH_CANCEL_BUTTON = By.id("org.wikipedia:id/search_close_btn"),
            SEARCH_RESULT_EMPTY_LABEL = By.xpath("//android.widget.TextView[@text = 'No results']");

    private final String SEARCH_RESULT = "//*[@class = 'android.view.ViewGroup']//*[@text = '%s']";

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
                By.xpath(SEARCH_RESULT.formatted(resultText)), "Cant find search result text: %s".formatted(resultText));
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
                By.xpath("//*[@class = 'android.view.ViewGroup']//*[@text = '%s']".formatted(articleText)),
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
}