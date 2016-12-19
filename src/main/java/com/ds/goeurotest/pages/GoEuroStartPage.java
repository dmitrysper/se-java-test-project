package com.ds.goeurotest.pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class GoEuroStartPage extends BasePage {

    public GoEuroStartPage(WebDriver driver) { super(driver);}

    @FindBy(id = "from_filter")
    private WebElement fromInput;

    @FindBy(id = "to_filter")
    private WebElement toInput;

    @FindBy(id = "search-form__submit-btn")
    private WebElement searchButton;

    @FindBy(css = "span.checkbox")
    private WebElement airBnbCheckbox;

    public void submitSearchData(String fromLocation, String toLocation) {
        fromInput.sendKeys(fromLocation);
        toInput.sendKeys(toLocation);
        unselectAirBnb();
        searchButton.click();
    }

    public GoEuroStartPage ensurePageLoaded() {
        waitForPageToLoad();
        try {
            wait.until(ExpectedConditions.visibilityOf(searchButton));
        } catch (TimeoutException e) {
            System.out.println("Timeout exceeded while waiting for " + this.getClass().getSimpleName() + " to load!");
        }
        return this;
    }

    private void unselectAirBnb() {
        if (airBnbCheckbox.getAttribute("class").contains("checked")) {
            airBnbCheckbox.click();
        }
    }
}
