package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class NavigationUi extends MainPageObject {

    private RemoteWebDriver driver;
    protected static String
            MY_LIST_BUTTON,
            BACK_ARROW_BUTTON,
            OPEN_SIDE_MENU_BUTTON,
            LOGIN_BUTTON;

    public NavigationUi(RemoteWebDriver driver) {
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
        if (Platform.getInstance().isMobileWeb()) {
            tryClickElementWithFewAttempts(MY_LIST_BUTTON,
                    "Cant find navigate button to my list",
                    5);
        } else {
            waitForElementAndClick(
                    MY_LIST_BUTTON,
                    "Cant find navigate button to my list",
                    5
            );
        }
    }

    public NavigationUi openSideMenu() {
        if (Platform.getInstance().isMobileWeb()) {
            waitForElementAndClick(OPEN_SIDE_MENU_BUTTON, "Cant find open side menu button", 5);
        }
        return this;
    }

    public void clickLoginButton() {
        if (Platform.getInstance().isMobileWeb()) {
            tryClickElementWithFewAttempts(LOGIN_BUTTON, "Cannot find login button in side menu", 5);
        }
    }
}