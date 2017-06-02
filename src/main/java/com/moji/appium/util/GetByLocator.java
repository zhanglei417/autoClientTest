package com.moji.appium.util;

import org.openqa.selenium.By;

public class GetByLocator {
	/**
	 * 获取element对象
	 * 返回by对象
	 * */
	public static By getLocator(String key){
		ProUtil properties = new ProUtil("configs/element.properties");
		//通过key获取 定位属性，然后进行拆分，获取key、value
		String locator = properties.getPro(key);
		String locatorType = locator.split(">")[0];
		String locatorValue = locator.split(">")[1];
		//根据判断获取到的tyep类型来决定用什么定位方式
		if(locatorType.toLowerCase().equals("id")){
			return By.id(locatorValue);
		}else if(locatorType.toLowerCase().equals("name")){
			return By.name(locatorValue);
		}else if(locatorType.toLowerCase().equals("classname")||locatorType.toLowerCase().equals("class")){
			return By.className(locatorValue);
		}else if(locatorType.toLowerCase().equals("tagname")){
			return By.className(locatorValue);
		}else if(locatorType.toLowerCase().equals("linktext")){
			return By.linkText(locatorValue);
		}else if(locatorType.toLowerCase().equals("partiallinktext")){
			return By.partialLinkText(locatorValue);
		}else if(locatorType.toLowerCase().equals("cssselector")){
			return By.cssSelector(locatorValue);
		}else if(locatorType.toLowerCase().equals("xpath")){
			return By.xpath(locatorValue);
		}else{
			try{
				throw new Exception("定位方式不存在:"+locatorValue);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
		
		
	}

}
