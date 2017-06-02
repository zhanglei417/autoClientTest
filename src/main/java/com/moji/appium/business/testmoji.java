package com.moji.appium.business;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.moji.appium.main.AppiumInit;
import com.moji.appium.testcases.CaseBaseTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class testmoji {

private static AndroidDriver driver;

@BeforeSuite(alwaysRun=true)
public void setUp() throws Exception {
}

@BeforeTest(alwaysRun=true)
public void before() throws Exception {
	AppiumInit.init();
	driver = new CaseBaseTest().driverInit("MSM8926", "4723");
}

@Test
public void testLogin() throws Exception {
	driver.swipe(15, 15, 15, 15, 200);
	driver.findElementById("test");
}
@Test
public void testLogout() throws Exception {
}
@Test
public void testAqi() throws Exception {
}
@Test
public void testSettings() throws Exception {
	driver.findElementById("Settings");
}
@Test
public void testSplash() throws Exception {
}
@Test
public void testSearch() throws Exception {
}
@Test
public void testLivePhoto() throws Exception {
}
@Test
public void testNews() throws Exception {
}


@AfterTest(alwaysRun=true)
public void aftertest() throws Exception {
    driver.quit();
}

@AfterSuite(alwaysRun=true)
public void tearDown() throws Exception {
}

public static AppiumDriver getDriver(){
    return driver;
}    

}
