package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;

public class ChangeAppConditionTest extends CoreTestCase {

    private MainPageObject mainPageObject = new MainPageObject(driver);
    private SearchPageObject searchPageObject = new SearchPageObject(driver);
    private ArticlePageObject articlePageObject = new ArticlePageObject(driver);

    @Test
    void changeScreenOrientationOnSearchResultTest() {
        final String articleTitle = "Java (programming language)";
       try {
           mainPageObject.skip();
           searchPageObject
                   .initSearchInput()
                   .typeSearchLine("Java")
                   .clickArticle("Object-oriented programming language")
                   .waitForTitleElement(articleTitle);

           String titleBeforeRotation = mainPageObject.waitForElementAndGetAttributeValue(
                   By.xpath("//android.view.View[@content-desc='Java (programming language)']"),
                   "name",
                   "Cant find topic title"
           );

           rotateScreenLandscape();

           String titleAfterRotation = mainPageObject.waitForElementAndGetAttributeValue(
                   By.xpath("//android.view.View[@content-desc='Java (programming language)']"),
                   "name",
                   "Cant find topic title"
           );

           Assertions.assertEquals(titleBeforeRotation, titleAfterRotation,
                   "Titles after rotation are different. Before: %s, After: %s".formatted(titleBeforeRotation, titleAfterRotation));

           rotateScreenPortrait();

           String titleAfterSecondRotation = mainPageObject.waitForElementAndGetAttributeValue(
                   By.xpath("//android.view.View[@content-desc='Java (programming language)']"),
                   "name",
                   "Cant find topic title"
           );

           Assertions.assertEquals(titleAfterRotation, titleAfterSecondRotation,
                   "Titles after rotation are different. Before: %s, After: %s".formatted(titleBeforeRotation, titleAfterRotation));
       }finally {
           final ScreenOrientation orientation = driver.getOrientation();
           if (orientation.value().equals(ScreenOrientation.LANDSCAPE.value())){
               rotateScreenPortrait();
           }
       }
    }

    @Test
    void checkArticleAfterCollapseAndExpandAppTest() {
        final String articleTitle = "Java (programming language)";
        mainPageObject.skip();
        searchPageObject
                .initSearchInput()
                .typeSearchLine("Java")
                .clickArticle("Object-oriented programming language")
                .waitForTitleElement(articleTitle);

        backgroundApp(2);

        articlePageObject.waitForTitleElement(articleTitle);
    }
}