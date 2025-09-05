package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUi;

public class AndroidNavigationUiPageObject extends NavigationUi {

    public AndroidNavigationUiPageObject(AppiumDriver driver) {
        super(driver);
    }

    static {
        MY_LIST_BUTTON = ID + "org.wikipedia:id/nav_tab_reading_lists";
        BACK_ARROW_BUTTON = XPATH + "//android.widget.ImageButton[@content-desc='Navigate up']";
    }
}