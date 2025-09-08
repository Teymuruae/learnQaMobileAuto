package lib.ui.mobileWeb;

import lib.ui.NavigationUi;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MwNavigationUiPageObject extends NavigationUi {

    public MwNavigationUiPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        MY_LIST_BUTTON = CSS + "a.mw-list-item.menu__item--watchlist";
        BACK_ARROW_BUTTON = XPATH + "//XCUIElementTypeButton[@name='Назад']";
        OPEN_SIDE_MENU_BUTTON = CSS + ".navigation-drawer";
        LOGIN_BUTTON = CSS + "#p-personal";
    }
}