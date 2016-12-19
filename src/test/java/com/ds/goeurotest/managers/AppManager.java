package com.ds.goeurotest.managers;

import com.ds.goeurotest.pages.*;
import com.ds.goeurotest.util.PropertyLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AppManager {

    private static final int WAIT_TIMEOUT = 15;

    /* Browsers constants */
    private static final String FIREFOX = "firefox";
    private static final String CHROME = "chrome";

    public GoEuroStartPage goEuroStartPage;
    public SearchResultPage searchResultPage;

	private WebDriver driver;

    public AppManager() {
        initApp();
    }

	private void initApp() {

        String browserName = PropertyLoader.loadProperty("browser.name");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String driverPath = System.getProperty("user.dir") + PropertyLoader.loadProperty("driver.path");
        System.out.println(System.getProperty("os.name"));
        if(System.getProperty("os.name").startsWith("Linux")) {
            driverPath = driverPath + "_linux";
            try {
                Runtime.getRuntime().exec("chmod +x " + driverPath);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if(System.getProperty("os.name").startsWith("Mac")) {
            driverPath = driverPath + "_osx";
        }
        switch(browserName) {
            case FIREFOX:
                System.setProperty("webdriver.gecko.driver", driverPath);
                //FirefoxProfile profile = new FirefoxProfile();
                //profile.setAcceptUntrustedCertificates(true);
                //driver = new FirefoxDriver(profile);
                driver = new FirefoxDriver();
                break;
            case CHROME:
                System.setProperty("webdriver.chrome.driver", driverPath);
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--ignore-certificate-errors");
                driver = new ChromeDriver(options);
                break;
        }
        driver.manage().timeouts().implicitlyWait(WAIT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        initPageObjects();
        loadStartPage();
	}

    private void initPageObjects() {
        goEuroStartPage = initElements(new GoEuroStartPage(driver));
        searchResultPage = initElements(new SearchResultPage(driver));
    }

    private <T extends BasePage> T initElements(T page) {
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, WAIT_TIMEOUT), page);
        return page;
    }

    public WebDriver getDriver() {
		return driver;
	}

	private void loadStartPage() {
        driver.get(PropertyLoader.loadProperty("site.url"));
    }

	public void closeDriver() {
		driver.quit();
	}
}
