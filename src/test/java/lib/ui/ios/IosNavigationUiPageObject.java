package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUi;

public class IosNavigationUiPageObject extends NavigationUi {

    public IosNavigationUiPageObject(AppiumDriver driver) {
        super(driver);
    }

    static {
        MY_LIST_BUTTON = XPATH + "//XCUIElementTypeButton[@name='Сохранено']";
        BACK_ARROW_BUTTON = XPATH + "//XCUIElementTypeButton[@name='Назад']";
    }
}