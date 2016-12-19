package com.ds.goeurotest.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

/**
 * Created by dmitriisperanskii on 26/04/16
 */
public abstract class BasePage {

    static final int WAIT_TIMEOUT = 15;

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, WAIT_TIMEOUT);
    }

    protected boolean isElementPresent(By by) {
        try{
            driver.findElement(by);
        } catch(NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public void getPage(String pageName) {
        driver.get(pageName);
    }

    // public abstract <T extends BasePage> T ensurePageLoaded();

    protected void executeJavaScript(String jSCode) {
        ((JavascriptExecutor)driver).executeScript(jSCode);
    }

    void waitForPageToLoad() {
        int retryAttempts = 10, counter = 0;
        long delay = 1000;
        String jsCode = "try {" +
                "if (document.readyState !== 'complete') { return false; }" +
                "if (window.jQuery) { if (window.jQuery.active !== 0) { return false; } }" +
                "if (window.angular) {" +
                "var injector = window.angular.element('body').injector();" +
                "var $rootScope = injector.get('$rootScope');" +
                "var $http = injector.get('$http');" +
                "var $timeout = injector.get('$timeout');" +
                "if ($rootScope.$$phase === '$apply' || $rootScope.$$phase === '$digest' || $http.pendingRequests.length !== 0) { return false; }  }" +
                "return true;" +
                "} catch (ex) { return false; }";
        while(counter < retryAttempts) {
            if(!(Boolean)((JavascriptExecutor)driver).executeScript(jsCode)) {
                System.out.println("jQuery and/or Angular is/are busy ...");
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            counter++;
        }
    }
}
