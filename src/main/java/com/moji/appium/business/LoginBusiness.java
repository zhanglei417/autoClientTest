package com.moji.appium.business;

import com.moji.appium.operation.LoginOperation;

public class LoginBusiness {
	LoginOperation loginopear = new LoginOperation();
	public void login(){
		loginopear.sendUserName("mushishi01");
		loginopear.sendPassWord("111111");
	}
}
