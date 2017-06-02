package com.moji.appiumstudy;

import java.util.List;

import org.dom4j.DocumentException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.moji.appium.base.AndroidDriverBase;
import com.moji.appium.base.CrazyPath;
import com.moji.appium.page.Home;
import com.moji.appium.util.GetByLocator;
import com.moji.appium.util.ImageUtil;
import com.moji.appium.util.XmlUtil;

import io.appium.java_client.android.AndroidElement;

public class UploadPhoto {
	private AndroidDriverBase driver;
	@BeforeClass
	public void beforeClass() throws Exception{
		List<String> s=XmlUtil.readXML("configs/device.xml");
		String server = "http://127.0.0.1";
		String port = s.get(1);
		
		String capsPath = CrazyPath.capsPath;
		String udid= s.get(0);
		String input = "com.tencent.qqpinyin/.QQPYInputMethodService";
		try {
			driver = new AndroidDriverBase(server,port,capsPath,udid,input);
			System.out.print("这是执行的upload.class类,他的port是"+s.get(1)+"他的udid是"+s.get(0));
			driver.implicitlyWait(15);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void test_upload() throws Exception{
		driver.findElement(GetByLocator.getLocator("username")).sendKeys("哈哈");
		driver.findElement(GetByLocator.getLocator("pwd")).sendKeys("111111");
		driver.findElement(GetByLocator.getLocator("login")).click();
		Home home = new Home(driver);
		
		home.date();
		home.module();
		driver.findElement(GetByLocator.getLocator("tab")).click();
		
		driver.takeScreenForElement(GetByLocator.getLocator("top"), "images/", "001");
		driver.findElement(GetByLocator.getLocator("head")).click();
		driver.findElement(GetByLocator.getLocator("photos")).click();
		List<AndroidElement> photos = driver.findElements(GetByLocator.getLocator("photo"));
		for(int i=1;i<photos.size();i++){
			photos.get(i).click();
			driver.wait(2000);
			driver.findElement(GetByLocator.getLocator("save")).click();
			driver.findElement(GetByLocator.getLocator("back")).click();
			driver.takeScreenForElement(GetByLocator.getLocator("top"), "images/", String.valueOf(i));
			//System.out.print(ImageUtil.compareImg("images/001.png", "images/"+i+".png", 100f));
			
			if(ImageUtil.compareImg("images/001.png", "images/"+i+".png", 95f)){
				System.out.print("哈哈");
				break;
			}else{
				driver.findElement(GetByLocator.getLocator("head")).click();
				driver.findElement(GetByLocator.getLocator("photos")).click();
				List<AndroidElement> photos1 = driver.findElements(GetByLocator.getLocator("photo"));
			}			
		}
	}
	
	@AfterClass
	public void afterClass(){
		driver.quit();
		
	}
	

}
