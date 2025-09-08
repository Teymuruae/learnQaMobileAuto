package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.jupiter.api.Test;

public class GetStartedTest extends CoreTestCase {

    private WelcomePageObject welcomePageObject = new WelcomePageObject(driver);

    @Test
    void passThroughWelcomeTest() {
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isMobileWeb()) {
            return;
        }
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