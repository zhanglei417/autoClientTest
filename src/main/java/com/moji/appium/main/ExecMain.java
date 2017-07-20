package com.moji.appium.main;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;


public class ExecMain {

	private static final String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";
	private static String path = System.getProperty("user.dir");
	public static void main(String args[]) throws Exception{
		System.setProperty(ESCAPE_PROPERTY, "false");
		AppiumInit.init();
		System.out.println("~~~~~~~~~~~~~");
		List<String> suites = new ArrayList<String>();
		suites.add("testng.xml");
//		suites.add(System.getProperty("user.dir")+"/testng.xml");
		TestNG tng = new TestNG();
		tng.setTestSuites(suites);
		tng.run();
	}
}


