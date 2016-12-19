package com.ds.goeurotest.tests;

import com.ds.goeurotest.managers.AppManager;
import com.ds.goeurotest.util.PropertyLoader;
import com.ds.goeurotest.model.Journey;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Tests {

    @Before
    public void startTests() {
        app = new AppManager();
    }

    @Before
    public void loadJourneyData() {
        String userName = PropertyLoader.loadProperty("from.location");
        String password = PropertyLoader.loadProperty("to.location");
        journey = new Journey(userName, password);
    }

    @Test
    public void verifyPricesSorted() {
        app.goEuroStartPage.ensurePageLoaded().submitSearchData(journey.getFromLocation(), journey.getToLocation());
        Assert.assertTrue(app.searchResultPage.ensurePageLoaded().arePricesSorted());
    }

    @After
    public void closeTestSession() {
        app.closeDriver();
    }

    private AppManager app;
    private Journey journey;
}
