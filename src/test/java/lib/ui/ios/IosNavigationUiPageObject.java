package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUi;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IosNavigationUiPageObject extends NavigationUi {

    public IosNavigationUiPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        MY_LIST_BUTTON = XPATH + "//XCUIElementTypeButton[@name='Сохранено']";
        BACK_ARROW_BUTTON = XPATH + "//XCUIElementTypeButton[@name='Назад']";
    }
}