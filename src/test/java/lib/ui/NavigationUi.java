package lib.ui;

import io.appium.java_client.AppiumDriver;

public abstract class NavigationUi extends MainPageObject {

    private AppiumDriver driver;
    protected static String
            MY_LIST_BUTTON,
            BACK_ARROW_BUTTON;

    public NavigationUi(AppiumDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void navigateUp() {
        waitForElementAndClick(
                BACK_ARROW_BUTTON,
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