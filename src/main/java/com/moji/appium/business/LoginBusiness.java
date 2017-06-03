package com.moji.appium.business;

import com.moji.appium.operation.LoginOperation;
// business就是业务操作的类，封装一些业务方法。
public class LoginBusiness {
	LoginOperation loginopear = new LoginOperation();
	public void login(){
		loginopear.sendUserName("mushishi01");
		loginopear.sendPassWord("111111");
	}
}
