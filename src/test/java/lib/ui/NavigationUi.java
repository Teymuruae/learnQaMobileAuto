package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class NavigationUi extends MainPageObject {

    private By
            MY_LIST_BUTTON = By.id("org.wikipedia:id/nav_tab_reading_lists");

    public NavigationUi(AppiumDriver driver) {
        super(driver);
    }

    public void navigateUp() {
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
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