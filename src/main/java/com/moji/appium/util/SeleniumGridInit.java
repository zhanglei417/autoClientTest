package com.moji.appium.util;

public class SeleniumGridInit {
	private static Log logger=Log.getLogger(SeleniumGridInit.class);
	public static void startSeleniumGrid() throws Exception{
		String command = "java -jar e://www//appiumstudy//appiumstudy//grid//selenium-server-standalone.jar -role hub";
		DosCmd dc = new DosCmd();
		dc.execCmd(command);
		logger.info("selenium-server启动成功");

	}
	public static void main(String args[]) throws Exception{
		startSeleniumGrid();
	}

}
