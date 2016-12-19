package com.ds.goeurotest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dmitriisperanskii on 19/12/2016.
 */
public class SearchResultPage extends BasePage {

    private static final String PRICE_REGEX = "\\d{1,3}\\.\\d{2}";

    public SearchResultPage(WebDriver driver) {
        super(driver);
    }

    public SearchResultPage ensurePageLoaded() {
        waitForPageToLoad();
        String resultsTabDivClass = "resultTabs";
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(resultsTabDivClass)));
        } catch (TimeoutException e) {
            System.out.println("Timeout exceeded while waiting for " + this.getClass().getSimpleName() + " to load!");
        }
        return this;
    }

    public boolean arePricesSorted() {
        selectSortingType("Cheapest");
        String priceSpanXpath = "//div[contains(@class, 'Result__priceContainer')]/span";
        List<Float> prices = new ArrayList<>();
        Pattern pattern = Pattern.compile(PRICE_REGEX);
        List<WebElement> priceSpans = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(priceSpanXpath)));
        for(WebElement priceSpan : priceSpans) {
            Matcher matcher = pattern.matcher(priceSpan.getText());
            if(matcher.find()) {
                prices.add(Float.parseFloat(matcher.group(0)));
            }
        }
        return isPricesListSorted(prices);
    }

    private void selectSortingType(String sortingType) {
        System.out.println("Selected sorting type - " + getSelectedSortingType());
        if(!sortingType.equals(getSelectedSortingType())) {
            List<WebElement> tabs = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(sortingBarDivsXpath)));
            for(WebElement tab : tabs) {
                    if(tab.findElement(By.className("span")).getText().equals(sortingType)) {
                        tab.click();
                }
            }
        }
    }

    private String getSelectedSortingType() {
        String selectedSortingTypeClass = "Sorting__mainActive";
        String selectedSortingType = "";
        List<WebElement> tabs = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(sortingBarDivsXpath)));
        for(WebElement tab : tabs) {
            if(tab.getAttribute("class").contains(selectedSortingTypeClass)) {
                selectedSortingType += tab.findElement(By.tagName("span")).getText();
            }
        }
        return selectedSortingType;
    }

    private boolean isPricesListSorted(List<Float> list) {
        boolean sorted = true;
        if(list.size() == 0) { return false; }
        if(list.size() == 1) { return true; }
        for (int i = 1; i < list.size() - 1; i++) {
            if (list.get(i) < list.get(i - 1)) {
                sorted = false;
                break;
            }
        }
        return sorted;
    }

    private String sortingBarDivsXpath = "//div[contains(@class, 'SortingBar__bar')]/div";
}
