package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class IosSearchPageObject extends SearchPageObject {


    public IosSearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    static {
        SEARCH_RESULT_LIST_ELEMENTS = XPATH + "//XCUIElementTypeOther[2]//XCUIElementTypeCell";
        SEARCH_INPUT_FIELD = XPATH + "//XCUIElementTypeSearchField[@name='Поиск по Википедии']";
        SEARCH_REMOVE_X_BUTTON = ID + "Очистить текст";
        SEARCH_RESULT_EMPTY_LABEL = XPATH + "//XCUIElementTypeStaticText[@name='Ничего не найдено']";
        SEARCH_RESULT = XPATH + "//XCUIElementTypeStaticText[@name='%s']";
        SEARCH_RESULT_BY_TITLE_AND_DESC = XPATH + "//XCUIElementTypeStaticText[@name='%s']" +
                "//following-sibling::XCUIElementTypeStaticText[@name='%s']";
        SEARCH_CANCEL_BUTTON = XPATH + "//XCUIElementTypeStaticText[@name='Отменить']";
    }
}