package com.moji.appium.page;

import com.moji.appium.base.AndroidDriverBase;
import com.moji.appium.util.GetByLocator;

import io.appium.java_client.android.AndroidElement;

public class Myself extends BasePage {
	public Myself(AndroidDriverBase driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public AndroidElement mydata;

	public AndroidElement getMydata() {
		return super.driver.findElement(GetByLocator.getLocator("head"));
	}
	
}
