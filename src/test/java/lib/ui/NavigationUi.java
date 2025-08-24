package lib.ui;

import io.appium.java_client.AppiumDriver;

public class NavigationUi extends MainPageObject {

    private final String
            MY_LIST_BUTTON = ID + "org.wikipedia:id/nav_tab_reading_lists",
            IMAGE_BUTTON = XPATH + "//android.widget.ImageButton[@content-desc='Navigate up']";

    public NavigationUi(AppiumDriver driver) {
        super(driver);
    }

    public void navigateUp() {
        waitForElementAndClick(
                IMAGE_BUTTON,
                "Cant find 'Navigate up' button",
                5
        );
    }

    public void clickMyList() {
        waitForElementAndClick(
                MY_LIST_BUTTON,
                "Cant find navigate button to my list",
                5
        );
    }
}