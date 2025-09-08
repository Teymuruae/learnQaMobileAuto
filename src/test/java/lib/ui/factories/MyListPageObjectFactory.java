package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.MyListPageObject;
import lib.ui.android.AndroidMyListPageObject;
import lib.ui.ios.IosMyListPageObject;
import lib.ui.mobileWeb.MwMyListPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MyListPageObjectFactory {

    public static MyListPageObject get(RemoteWebDriver driver) {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidMyListPageObject(driver);
        } else if (Platform.getInstance().isIos()){
            return new IosMyListPageObject(driver);
        } else {
            return new MwMyListPageObject(driver);
        }
    }
}