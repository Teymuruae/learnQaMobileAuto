package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.NavigationUi;
import lib.ui.android.AndroidNavigationUiPageObject;
import lib.ui.ios.IosNavigationUiPageObject;

public class NavigationUiPageObjectFactory {

    public static NavigationUi get(AppiumDriver driver) {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidNavigationUiPageObject(driver);
        } else {
            return new IosNavigationUiPageObject(driver);
        }
    }
}