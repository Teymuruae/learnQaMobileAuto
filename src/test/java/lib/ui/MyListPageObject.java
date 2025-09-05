package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.factories.ArticlePageObjectFactory;
import org.junit.jupiter.api.Assertions;

public abstract class MyListPageObject extends MainPageObject {

    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_DESCRIPTION,
            ARTICLE_TITLE,
            ARTICLE_TITLES,
            EDIT_BUTTON, //кнопка "Править". Есть только в ios;
            DONT_SAVE_BUTTON;

    private AppiumDriver driver;

    public MyListPageObject(AppiumDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public MyListPageObject openFolderByName(String folderName) {
        waitForElementAndClick(
                FOLDER_BY_NAME_TPL.formatted(folderName),
                "Cant find Java list",
                5
        );
        return this;
    }

    public MyListPageObject waitForArticleToAppearByDescription(String articleDescription) {
        final String savedPage = ARTICLE_DESCRIPTION.formatted(articleDescription);

        waitForElementPresent(savedPage, "Saved page '%s' is not present".formatted(articleDescription), 7);
        return this;
    }

    public MyListPageObject waitForArticleToDisappearByDescription(String articleDescription) {
        final String savedPage = ARTICLE_DESCRIPTION.formatted(articleDescription);

        waitForElementNotPresent(savedPage, "Saved page '%s' is still present".formatted(articleDescription), 7);
        return this;
    }

    public MyListPageObject swipeByArticleDescriptionToDelete(String articleDescription) {
        final String savedPage = ARTICLE_DESCRIPTION.formatted(articleDescription);

        waitForArticleToAppearByDescription(articleDescription);
        swipeLeft2(savedPage, "Cant find '%s' saved page".formatted(articleDescription));
        waitForArticleToDisappearByDescription(articleDescription);
        return this;
    }

    public MyListPageObject waitForArticleToAppearByTitle(String articleTitle) {
        final String savedPage = ARTICLE_TITLE.formatted(articleTitle);

        waitForElementPresent(savedPage, "Saved page '%s' is not present".formatted(articleTitle), 7);
        return this;
    }

    public MyListPageObject waitForArticleToDisappearByTitle(String articleTitle) {
        final String savedPage = ARTICLE_TITLE.formatted(articleTitle);

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
        waitForElementAndClick(ARTICLE_TITLE.formatted(articleTitle),
                "cant find article by title %s".formatted(articleTitle), 5);
        return ArticlePageObjectFactory.get(driver);
    }

    public MyListPageObject removeArticleByTitle(String articleTitle) {
        final String savedPage = ARTICLE_TITLE.formatted(articleTitle);

        waitForArticleToAppearByTitle(articleTitle);
        if (Platform.getInstance().isAndroid()) {
            swipeLeft2(savedPage, "Cant find '%s' saved page".formatted(articleTitle));
        } else {
            waitForElementAndClick(EDIT_BUTTON, "Edit button not found", 5);
            enterArticleByTitle(articleTitle);
            waitForElementAndClick(DONT_SAVE_BUTTON, "Dont save button not fount", 5);
        }

        waitForArticleToDisappearByTitle(articleTitle);
        return this;
    }
}