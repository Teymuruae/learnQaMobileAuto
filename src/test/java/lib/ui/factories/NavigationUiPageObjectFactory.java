package lib.ui.factories;

import lib.Platform;
import lib.ui.NavigationUi;
import lib.ui.android.AndroidNavigationUiPageObject;
import lib.ui.ios.IosNavigationUiPageObject;
import lib.ui.mobileWeb.MwNavigationUiPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class NavigationUiPageObjectFactory {

    public static NavigationUi get(RemoteWebDriver driver) {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidNavigationUiPageObject(driver);
        } else if (Platform.getInstance().isIos()) {
            return new IosNavigationUiPageObject(driver);
        } else {
            return new MwNavigationUiPageObject(driver);
        }
    }
}