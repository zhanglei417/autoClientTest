package com.moji.appium.page;

import com.moji.appium.base.AndroidDriverBase;
import com.moji.appium.util.GetByLocator;

import io.appium.java_client.android.AndroidElement;

public class LoginPage extends BasePage{
	
	public LoginPage(AndroidDriverBase driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	public AndroidElement username;
	public AndroidElement password;
	public AndroidElement loginbutton;
	public AndroidElement register;
	public AndroidElement findpassword;
	public AndroidElement qqlogin;
	public AndroidElement wxlogin;
	public AndroidElement sinalogin;
	public AndroidElement scanlogin;
	public AndroidElement breakbutton;
	public AndroidElement getUsername() {
		return super.driver.findElement(GetByLocator.getLocator("username"));
	}
	public AndroidElement getPassword() {
		return super.driver.findElement(GetByLocator.getLocator("pwd"));
	}

	public AndroidElement getLoginbutton() {
		return super.driver.findElement(GetByLocator.getLocator("login"));
	}

	public AndroidElement getRegister() {
		return super.driver.findElement(GetByLocator.getLocator("register"));
	}

	public AndroidElement getFindpassword() {
		return super.driver.findElement(GetByLocator.getLocator("findpassword"));
	}

	public AndroidElement getQqlogin() {
		return super.driver.findElement(GetByLocator.getLocator("qq"));
	}

	public AndroidElement getWxlogin() {
		return super.driver.findElement(GetByLocator.getLocator("wx"));
	}

	public AndroidElement getSinalogin() {
		return super.driver.findElement(GetByLocator.getLocator("sina"));
	}

	public AndroidElement getScanlogin() {
		return super.driver.findElement(GetByLocator.getLocator("scan"));
	}
	public AndroidElement getBreakbutton(){
		return super.driver.findElement(GetByLocator.getLocator("break"));
	}
	/**
	 * 登陆
	 * @param username
	 * @param password
	 * */
	public Home login(String username,String password){
		click(getBreakbutton());
		sendkeys(getUsername(),username);
		sendkeys(getPassword(),password);
		click(getLoginbutton());
		return new Home(super.driver);
	}


	

}
