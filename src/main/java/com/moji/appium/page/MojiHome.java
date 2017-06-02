package com.moji.appium.page;

import java.util.List;

import com.moji.appium.base.AndroidDriverBase;
import com.moji.appium.util.GetByLocator;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;

public class MojiHome extends BasePage{

	public List<AndroidElement> tvWeek;
	public List<AndroidElement> datefix;
	public List<AndroidElement> radiogroup;
	

	public MojiHome(AndroidDriverBase driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	public List<MobileElement> getTvWeek() {
		return getListElement("vppic","vppic");
	}


	public List<MobileElement> getDatefix() {
		return getListElement("module");
	}


	public List<MobileElement> getRadiogroup() {
		return getListElement("table");
	}
	
	public void date(){
		List<MobileElement> date = getTvWeek();
		for(MobileElement element :date){
			element.click();
		}
	}
	
	public void module(){
		List<MobileElement> module = getDatefix();
		for(MobileElement element :module){
			element.click();
			driver.pressBack();
		}
	}

}
