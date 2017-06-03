package com.moji.appium.listener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.moji.appium.testcases.testmoji;

import io.appium.java_client.AppiumDriver;

	public class mojiScreenShotListener extends TestListenerAdapter{
		
	    private static AppiumDriver<?> driver;

		public static void setDriver(AppiumDriver<?> driver) {
	    	mojiScreenShotListener.driver = driver;
	    }
	    
		@Override
		public void onTestFailure(ITestResult tr){
	        super.onTestFailure(tr);
	        AppiumDriver<?> driver = testmoji.getDriver();
	        File location = new File("FailScreenshot");
	        String time = getCurrentTime();
	        String screenShotName = location.getAbsolutePath()+File.separator+tr.getMethod().getMethodName()+"_" + getCurrentTime() +".png";
	        File screenShot = driver.getScreenshotAs(OutputType.FILE);
	        try{
	            FileUtils.copyFile(screenShot, new File(screenShotName));    
	        }
	        catch(IOException e){
	            e.printStackTrace();
	        }
		}
		
	    private AppiumDriver<?> getDriver() {
			// TODO Auto-generated method stub
	    	return driver;
		}

		public static String getCurrentTime(){
	        Date date=new Date();
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String currentTime=sdf.format(date);
	        return currentTime; 
	    }
}

