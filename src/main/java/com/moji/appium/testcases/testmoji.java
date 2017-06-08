package com.moji.appium.testcases;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.moji.appium.main.AppiumInit;
import com.moji.appium.main.ExecMain;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class testmoji extends CaseBaseTest {

@Parameters({"udid","port"})
@BeforeClass(alwaysRun=true)
public void setUp(String udid, String port) throws Exception {
	driver = new CaseBaseTest().driverInit(udid, port);
}


@Test
public void testLogin() throws Exception {
	driver.swipe(15, 15, 15, 15, 200);
}
@Test
public void testLogout() throws Exception {
}
@Test
public void testSettings() throws Exception {
	driver.findElementById("Settings");
}

@AfterClass(alwaysRun=true)
public void aftertest() throws Exception {
    driver.quit();
}


}
