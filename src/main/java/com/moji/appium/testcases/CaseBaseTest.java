package com.moji.appium.testcases;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import com.moji.appium.base.AndroidDriverBase;
import com.moji.appium.base.CrazyPath;
import com.moji.appium.util.ProUtil;

public class CaseBaseTest {
	
	protected static AppiumDriver driver;

	public AndroidDriverBase driverInit(String udid, String port)
			throws Exception {
		String input = "com.moji.mjweather/.LauncherActivity";
//		通过proutil类获取global中的配置
		ProUtil p = new ProUtil(CrazyPath.globalPath);
//		获取p中的server对应字符串
		String server=p.getPro("server");
		String capsPath=CrazyPath.capsPath;
//		AndroidSpecific as=new AndroidSpecific();
//		String input=as.getDefaultInput(udid);
		System.out.println("连接"+udid+"端口"+port);
		System.out.println("开始创建server连接");
		
		return new AndroidDriverBase(server, port, capsPath, udid, input);
		
	}
	
	public static AppiumDriver getDriver(){
		return driver;
	}    

	public static void main(String[] args) throws Exception {
		new CaseBaseTest().driverInit("MSM8926", "4723");

	}
}
