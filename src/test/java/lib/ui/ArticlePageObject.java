package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.factories.NavigationUiPageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.openqa.selenium.WebElement;

public abstract class ArticlePageObject extends MainPageObject {

    private final AppiumDriver driver;

    protected static String
            FOOTER_ELEMENT,
            SAVE_ARTICLE_BUTTON,
            ADD_TO_ANOTHER_LIST_BUTTON,
            CREATE_NEW_READING_LIST_BUTTON,
            NAME_OF_READING_LIST_INPUT_FIELD,
            CREATE_NEW_READING_LIST_MODAL_OK_BUTTON,
            READING_LIST_FOLDER,
            TITLE;

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public WebElement waitForTitleElement(String title) {
        return waitForElementPresent(TITLE.formatted(title), "Cant find article title %s on page".formatted(title), 10);
    }

    public void assertTitleIsPresent(String title) {
        assertElementIsPresent(TITLE.formatted(title), "Cannot find title " + title);
    }

    public ArticlePageObject swipeToFooter() {
        if (Platform.getInstance().isAndroid()) {
            swipeToFindElement(FOOTER_ELEMENT, "Cant find the end of the article", Direction.UP);
        } else {
            swipeTillElementAppear(FOOTER_ELEMENT, "Cant find the end of the article", 40, Direction.UP);
        }
        return this;
    }

    public ArticlePageObject swipeToTitle(String title) {
        if (Platform.getInstance().isAndroid()) {
            swipeToFindElement(TITLE.formatted(title), "Cant find the title of the article", Direction.DOWN);
        } else {
            swipeTillElementAppear(TITLE.formatted(title), "Cant find the title of the article", 40, Direction.DOWN);
        }
        return this;
    }

    public ArticlePageObject addArticleToMyNewList(String folderName) {
        if (Platform.getInstance().isIos()) {
            waitForElementAndClick(SAVE_ARTICLE_BUTTON, "Cannot find save article button", 5);
        } else {
            WebElement saveButton = waitForElementAndClick(
                    SAVE_ARTICLE_BUTTON,
                    "Cant find save page button",
                    5
            );

            saveButton.click();

            waitForElementAndClick(
                    ADD_TO_ANOTHER_LIST_BUTTON,
                    "Cant find 'Add to another reading list' button",
                    5
            );

            waitForElementAndClick(
                    CREATE_NEW_READING_LIST_BUTTON,
                    "Cant find 'Create' button",
                    5
            );

            waitForElementAndSendKeys(
                    NAME_OF_READING_LIST_INPUT_FIELD,
                    folderName,
                    "Cant find 'Name of list' input field",
                    5
            );

            waitForElementAndClick(
                    CREATE_NEW_READING_LIST_MODAL_OK_BUTTON,
                    "Cant find 'Ok' button",
                    5
            );
        }
        return this;
    }

    public ArticlePageObject addArticleToMyExistList(String folderName) {
        WebElement saveButton = waitForElementAndClick(
                SAVE_ARTICLE_BUTTON,
                "Cant find save page button",
                5
        );

        saveButton.click();

        waitForElementAndClick(
                ADD_TO_ANOTHER_LIST_BUTTON,
                "Cant find 'Add to another reading list' button",
                5
        );

        waitForElementAndClick(
                READING_LIST_FOLDER.formatted(folderName),
                "Cant find reading list folder button by name %s".formatted(folderName),
                5
        );
        return this;
    }

    public void toMainMenu(String title, int returnBackTimes) {
        final NavigationUi navigationUi = NavigationUiPageObjectFactory.get(driver);

        swipeToTitle(title);
        if (Platform.getInstance().isIos()) {
            final SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
            for (int i = 0; i < returnBackTimes - 1; i++) {
                navigationUi.navigateUp();
            }
            searchPageObject.clickSearchCancelButton();
        } else {
            for (int i = 0; i < returnBackTimes; i++) {
                navigationUi.navigateUp();
            }
        }
    }
}