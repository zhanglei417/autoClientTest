package com.moji.appium.main;

import java.util.ArrayList;
import java.util.List;

import com.moji.appium.server.Port;
import com.moji.appium.server.Servers;
import com.moji.appium.util.DosCmd;
import com.moji.appium.util.XmlUtil;

public class AppiumInit {
	public static void init(){
		List<String> classesList=new ArrayList<String>();
		Servers servers=new Servers(new Port(new DosCmd()), new DosCmd());
		DosCmd dc=new DosCmd();
		if(dc.killServer()){
			try {
				//org.openqa.grid.selenium.proxy.DefaultRemoteProxy
				//proxy:
				System.out.println("开始启动服务");
				servers.startServers();
				System.out.println("服务启动完毕");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		classesList.add("com.mushishi.appium.business.LoginBusiness");
			classesList.add("com.mushishi.appium.business.testmoji");
			try {
				System.out.println(classesList.get(0));
				XmlUtil.createTestngXml(classesList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("清除appium服务失败");
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		init();

	}

}
