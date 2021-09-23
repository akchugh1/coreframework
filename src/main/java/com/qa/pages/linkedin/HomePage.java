package com.qa.pages.linkedin;

import com.qa.appium.AppiumHelper;
import com.qa.wrappers.CommonFunctions;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class HomePage extends CommonFunctions {

    public HomePage() {
        PageFactory.initElements(new AppiumFieldDecorator(AppiumHelper.driverInit()), this);
    }

    @AndroidFindBy(id = "com.linkedin.android:id/search_bar_text")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement btnSearchBar;

    @AndroidFindBy(id = "com.linkedin.android:id/search_bar_edit_text")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement txtboxSearchBar;


    @AndroidFindBy(id = "com.linkedin.android:id/search_typeahead_see_all_button")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement btnSeeAllResults;

    @AndroidFindBys({ @AndroidBy(id = "com.linkedin.android:id/search_typeahead_entity_text") })
    @iOSXCUITFindBys({ @iOSXCUITBy(id = "NA") })
    public List<MobileElement> searchText;

    @AndroidFindBy(id = "com.linkedin.android:id/search_bar_dismiss_search_keyword_button")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement btnDismissSearch;

    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='Back']")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement navigateBack;

    @AndroidFindBy(id = "com.linkedin.android:id/ad_notification_badge_icon")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement btnChat;

    @AndroidFindBy(id = "com.linkedin.android:id/me_launcher")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement profilePhoto;

    public void searchText(String txtSearch) {
        super.click(btnSearchBar);
        super.sendKeys(txtboxSearchBar,txtSearch);
    }

    public void validateSeeAllResultsButton() {
        Assert.assertTrue(super.isElementPresent(btnSeeAllResults, 10),"See All Results button is not displayed");
    }

    public void validateSearchText(String text){
//        searchText.stream().map(element -> element.getText()).forEach(System.out::println);
//        searchText.stream().map(element -> element.getText().toUpperCase())
//                .map(str->str.contains(text.toUpperCase())).forEach(Assert::assertTrue);

//        searchText.stream().map(element -> element.getText().contains(text)).forEach(System.out::println);
//
        searchText.stream().map(element -> element.getText().toUpperCase().contains(text.toUpperCase()))
                .forEach(Assert::assertTrue);
    }

    public void clickBtnDismissSearch(){
        super.click(btnDismissSearch);
    }

    public void clickProfilePhoto(){
        super.click(profilePhoto);
    }

    public void openChat(){
        super.click(btnChat);
    }

    public void clickBackBtn(){
        super.click(navigateBack);
    }


}
