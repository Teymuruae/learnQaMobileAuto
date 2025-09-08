package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

public class LoginPageObject extends MainPageObject {
    private static final String
            USERNAME_FIELD = ID + "wpName1",
            PASSWORD_FIELD = ID + "wpPassword1",
            LOGIN_BUTTON = ID + "wpLoginAttempt";

    public LoginPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void setLoginData(String userName, String password) {
        waitForElementAndSendKeys(USERNAME_FIELD, userName, "Cant find username field", 5);
        waitForElementAndSendKeys(PASSWORD_FIELD, password, "Cant find password field", 5);
    }

    public void clickLoginButton() {
        waitForElementAndClick(LOGIN_BUTTON, "Cant find login button", 5);
    }
}