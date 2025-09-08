package lib.ui.mobileWeb;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MwSearchPageObject extends SearchPageObject {


    public MwSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        SEARCH_RESULT_LIST_ELEMENTS = CSS + ".cdx-menu__listbox li[role = option]";
        SEARCH_INPUT_FIELD = XPATH + "//input[@name = 'search']";
        SEARCH_INIT_ELEMENT = ID + "searchIcon";
        SEARCH_REMOVE_X_BUTTON = CSS + ".cdx-text-input .cdx-text-input__clear-icon";
        SEARCH_RESULT_EMPTY_LABEL = CSS + "p.mw-search-nonefound";
        SEARCH_RESULT = XPATH + "//li[@role = 'option']//bdi[text() = '%s']";
        SEARCH_RESULT_BY_TITLE_AND_DESC = XPATH + "//li[@role = 'option']//bdi[text() = '%s']" +
                "//parent::span//following-sibling::span//bdi[text() = '%s']";
    }
}