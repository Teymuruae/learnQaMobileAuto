package lib.ui.ios;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IosSearchPageObject extends SearchPageObject {


    public IosSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        SEARCH_INIT_ELEMENT = XPATH + "//XCUIElementTypeSearchField[@name='Поиск по Википедии']";
        SEARCH_RESULT_LIST_ELEMENTS = XPATH + "//XCUIElementTypeOther[2]//XCUIElementTypeCell";
        SEARCH_INPUT_FIELD = SEARCH_INIT_ELEMENT;
        SEARCH_REMOVE_X_BUTTON = ID + "Очистить текст";
        SEARCH_RESULT_EMPTY_LABEL = XPATH + "//XCUIElementTypeStaticText[@name='Ничего не найдено']";
        SEARCH_RESULT = XPATH + "//XCUIElementTypeStaticText[@name='%s']";
        SEARCH_RESULT_BY_TITLE_AND_DESC = XPATH + "//XCUIElementTypeStaticText[@name='%s']" +
                "//following-sibling::XCUIElementTypeStaticText[@name='%s']";
        SEARCH_CANCEL_BUTTON = XPATH + "//XCUIElementTypeStaticText[@name='Отменить']";
    }
}