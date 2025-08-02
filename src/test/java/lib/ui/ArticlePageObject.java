package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private final By
            FOOTER_ELEMENT = By.xpath("//android.view.View[@content-desc='View article in browser']"),
            SAVE_ARTICLE_BUTTON = By.id("org.wikipedia:id/page_save"),
            ADD_TO_ANOTHER_LIST_BUTTON = By.xpath("//android.widget.TextView[@text = 'Add to another reading list']"),
            CREATE_NEW_READING_LIST_BUTTON = By.id("org.wikipedia:id/create_button"),
            NAME_OF_READING_LIST_INPUT_FIELD = By.id("org.wikipedia:id/text_input"),
            CREATE_NEW_READING_LIST_MODAL_OK_BUTTON = By.xpath("//android.widget.Button[@text = 'OK']");

    private final String
            READING_LIST_FOLDER = "//android.widget.TextView[@text='%s' and @resource-id = 'org.wikipedia:id/item_title' ]",
            TITLE = "//android.view.View[@content-desc='%s']";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement(String title) {
        return waitForElementPresent(By.xpath(TITLE.formatted(title)), "Cant find article title %s on page".formatted(title), 10);
    }

    public ArticlePageObject swipeToFooter() {
        swipeUpToFindElement(FOOTER_ELEMENT, "Cant find the end of the article");
        return this;
    }

    public ArticlePageObject addArticleToMyNewList(String folderName) {
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
                By.xpath(READING_LIST_FOLDER.formatted(folderName)),
                "Cant find reading list folder button by name %s".formatted(folderName),
                5
        );
        return this;
    }
}