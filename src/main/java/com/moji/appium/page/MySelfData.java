package com.moji.appium.page;

import com.moji.appium.base.AndroidDriverBase;
import com.moji.appium.util.GetByLocator;

import io.appium.java_client.android.AndroidElement;
import com.moji.appium.base.AndroidDriverBase;

public class MySelfData extends BasePage{

	public MySelfData(AndroidDriverBase driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	public AndroidElement major;
	public AndroidElement school;
	public AndroidElement year;
	
/*	public AndroidElement getMajor() {
		AndroidElement 
		super.driver.findElement(GetByLocator.getLocator("major")).click();
		super.driver.findElement(GetByLocator.getLocator("major_subject")).click();
		flag = super.driver.findElement(GetByLocator.getLocator("majorname")).isSelected();
		if(flag == true){
			driver.pressBack();
		}
		return null;
	}*/
	public AndroidElement getSchool() {
		return super.driver.findElement(GetByLocator.getLocator("school"));
	}
	public AndroidElement getYear() {
		return year;
	}
	

}
