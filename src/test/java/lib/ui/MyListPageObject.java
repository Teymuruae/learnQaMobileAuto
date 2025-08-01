package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;

public class MyListPageObject extends MainPageObject {

    private String
            FOLDER_BY_NAME_TPL = "//*[@text = '%s']",
            ARTICLE_DESCRIPTION = "//*[@resource-id = 'org.wikipedia:id/page_list_item_description'][@text = '%s']",
            ARTICLE_TITLE = "//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][@text = '%s']";

    private By ARTICLE_TITLES = By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title']");

    private AppiumDriver driver;

    public MyListPageObject(AppiumDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public MyListPageObject openFolderByName(String folderName) {
        waitForElementAndClick(
                By.xpath(FOLDER_BY_NAME_TPL.formatted(folderName)),
                "Cant find Java list",
                5
        );
        return this;
    }

    public MyListPageObject waitForArticleToAppearByDescription(String articleDescription) {
        By savedPage = By.xpath(ARTICLE_DESCRIPTION.formatted(articleDescription));

        waitForElementPresent(savedPage, "Saved page '%s' is not present".formatted(articleDescription), 7);
        return this;
    }

    public MyListPageObject waitForArticleToDisappearByDescription(String articleDescription) {
        By savedPage = By.xpath(ARTICLE_DESCRIPTION.formatted(articleDescription));

        waitForElementNotPresent(savedPage, "Saved page '%s' is still present".formatted(articleDescription), 7);
        return this;
    }

    public MyListPageObject swipeByArticleDescriptionToDelete(String articleDescription) {
        By savedPage = By.xpath(ARTICLE_DESCRIPTION.formatted(articleDescription));

        waitForArticleToAppearByDescription(articleDescription);
        swipeLeft2(savedPage, "Cant find '%s' saved page".formatted(articleDescription));
        waitForArticleToDisappearByDescription(articleDescription);
        return this;
    }

    public MyListPageObject waitForArticleToAppearByTitle(String articleTitle) {
        By savedPage = By.xpath(ARTICLE_TITLE.formatted(articleTitle));

        waitForElementPresent(savedPage, "Saved page '%s' is not present".formatted(articleTitle), 7);
        return this;
    }

    public MyListPageObject waitForArticleToDisappearByTitle(String articleTitle) {
        By savedPage = By.xpath(ARTICLE_TITLE.formatted(articleTitle));

        waitForElementNotPresent(savedPage, "Saved page '%s' is still present".formatted(articleTitle), 7);
        return this;
    }

    public MyListPageObject assertAllArticleTitleTexts(String... articleTitle) {
        for (String title : articleTitle) {
            waitForArticleToAppearByTitle(title);
        }

        final int expectedArticleSize = articleTitle.length;
        final int actualArticlesSize = getAmountOfElements(ARTICLE_TITLES);
        Assertions.assertEquals(expectedArticleSize, actualArticlesSize,
                "size mismatch. Expected articles size: %d, actual: %d".formatted(expectedArticleSize, actualArticlesSize));
        return this;
    }

    public ArticlePageObject enterArticleByTitle(String articleTitle) {
        waitForElementAndClick(By.xpath(ARTICLE_TITLE.formatted(articleTitle)),
                "cant find article by title %s".formatted(articleTitle), 5);
        return new ArticlePageObject(driver);
    }

    public MyListPageObject swipeByArticleTitleToDelete(String articleTitle) {
        By savedPage = By.xpath(ARTICLE_TITLE.formatted(articleTitle));

        waitForArticleToAppearByTitle(articleTitle);
        swipeLeft2(savedPage, "Cant find '%s' saved page".formatted(articleTitle));
        waitForArticleToDisappearByTitle(articleTitle);
        return this;
    }
}