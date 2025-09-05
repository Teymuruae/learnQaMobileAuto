package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class AndroidSearchPageObject extends SearchPageObject {

    public AndroidSearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    static {
        SEARCH_RESULT_LIST_ELEMENTS = XPATH + "//*[@resource-id ='org.wikipedia:id/search_results_display']" +
                "//*[@resource-id = 'org.wikipedia:id/page_list_item_title']";
        SEARCH_INPUT_FIELD = XPATH + "//*[@text = 'Search Wikipedia']";
        SEARCH_REMOVE_X_BUTTON = ID + "org.wikipedia:id/search_close_btn";
        SEARCH_RESULT_EMPTY_LABEL = XPATH + "//android.widget.TextView[@text = 'No results']";
        SEARCH_RESULT = XPATH + "//*[@class = 'android.view.ViewGroup']//*[@text = '%s' and not(@resource-id = 'org.wikipedia:id/search_src_text')]";
        SEARCH_RESULT_BY_TITLE_AND_DESC = XPATH + "//*[@text = '%s' and @resource-id = " +
                "'org.wikipedia:id/page_list_item_title']//following-sibling::android.widget.TextView[@text = '%s']";
    }
}
