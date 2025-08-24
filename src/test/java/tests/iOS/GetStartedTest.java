package tests.iOS;

import lib.CoreTestCase;
import lib.ui.WelcomePageObject;
import org.junit.jupiter.api.Test;

public class GetStartedTest extends CoreTestCase {

    private WelcomePageObject welcomePageObject = new WelcomePageObject(driver);

    @Test
    void passThroughWelcomeTest() {
        welcomePageObject
                .waitForLearnMoreLink()
                .clickNextButton()
                .waitForNewWayToExploreText()
                .clickNextButton()
                .waitForAddOrEditPreferredLangText()
                .clickNextButton()
                .waitForLearnMoreAboutDataCollectedText()
                .clickGetStartedButton();
    }
}