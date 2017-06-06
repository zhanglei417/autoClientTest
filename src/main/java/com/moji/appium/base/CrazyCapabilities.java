package com.moji.appium.base;

import java.io.File;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.moji.appium.util.ProUtil;

import io.appium.java_client.remote.AndroidMobileCapabilityType;

public class CrazyCapabilities {
	/**
	 * 初始化capabilities
	 * 将driver需要的数据去配置文件中读取
	 * @param capsPath caps配置文件存取路径
	 * @param udid 设备ID
	 * */
	public Capabilities initCaps(String capsPath, String udid) {
		DesiredCapabilities caps = new DesiredCapabilities();
		ProUtil p = new ProUtil(capsPath);
		File apkPath = new File(p.getPro("apkpath"));
		caps.setCapability(AndroidCapabilityType.DEVICE_NAME, p.getPro(AndroidCapabilityType.DEVICE_NAME));
		caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, p.getPro(AndroidCapabilityType.APP_PACKAGE));
		caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, p.getPro(AndroidCapabilityType.APP_ACTIVITY));
		caps.setCapability(AndroidMobileCapabilityType.NO_SIGN, p.getPro(AndroidCapabilityType.NO_SIGN));
		caps.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD, p.getPro(AndroidMobileCapabilityType.UNICODE_KEYBOARD));
		caps.setCapability(AndroidMobileCapabilityType.RESET_KEYBOARD, p.getPro(AndroidMobileCapabilityType.RESET_KEYBOARD));
		caps.setCapability(AndroidCapabilityType.UDID, udid);
		caps.setCapability(AndroidCapabilityType.APP, apkPath);
		caps.setCapability(AndroidCapabilityType.NO_RESET,true);
		//caps.setCapability(AndroidCapabilityType.UDID, p.getPro(AndroidCapabilityType.UDID));
		caps.setCapability(AndroidCapabilityType.NEW_COMMAND_TIMEOUT, p.getPro(AndroidCapabilityType.NEW_COMMAND_TIMEOUT));
		return caps;
	}
	public static void main(String args[]){
		CrazyCapabilities ca = new CrazyCapabilities();
		ca.initCaps("/Users/phoenixzhang/Documents/test/appiumstudy/configs/caps.properties", "MSM8926");
		
	}

}
