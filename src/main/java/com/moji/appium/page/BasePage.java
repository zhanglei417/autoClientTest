package com.moji.appium.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;

import com.moji.appium.base.AndroidDriverBase;
import com.moji.appium.util.GetByLocator;
import com.moji.appium.util.Log;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

public class BasePage {
	public String curActivity;
	public String pageSource;
	public AndroidDriverBase driver;
	private Log logger = Log.getLogger(BasePage.class);
	public BasePage(AndroidDriverBase driver){
		this.driver = driver;
		this.curActivity = getCurActivity();
		this.pageSource = getPageSource();
	}
	/**
	 * list定位,需要传入父节点的element，以及list在配置文件中的key
	 * @param element
	 * @param key
	 * */
	public List<MobileElement> getListElement(AndroidElement element,String key){
		if(element != null){
			List<MobileElement> listElement = element.findElements(GetByLocator.getLocator(key));
			return listElement;
		}else{
			logger.error("定位list失败，element："+element+"为空");
			return null;
		}
	}
	
	/**
	 * list定位,需要传入父节点的定位key，以及list在配置文件中的key
	 * @param String key
	 * @param String key
	 * */
	public List<MobileElement> getListElement(String parentNode,String childNode){
		AndroidElement element = driver.findElement(GetByLocator.getLocator(parentNode));
		List<MobileElement> listElement = getListElement(element,childNode);
		return listElement;		
	}
	/**
	 * List定位，直接通过node节点进行list定位，传入节点在配置文件中的定位信息即可
	 * @param String Node
	 * */
	public List<MobileElement> getListElement(String Node){
		List<MobileElement> listElement = driver.findElements(GetByLocator.getLocator(Node));
		return listElement;
	}
	
	
	/**
	 * 获取当前页面pagesource
	 * */
	private String getPageSource() {
		// TODO Auto-generated method stub
		return driver.getPageSouce();
	}
	/**
	 * 获取当前activity
	 * */
	private String getCurActivity() {
		return driver.currentActivity();
	}
	
	/**
	 * 输入信息
	 * @param element
	 * @param string value
	 * */
	public void sendkeys(AndroidElement element,String value){
		if(element != null){
			element.sendKeys(value);
		}else{
			logger.error("元素："+element+"没有定位到，空的。");
		}
	}
	/**
	 * 定位并输入
	 * @param By by
	 * @param string value
	 * */
	public void sendkeys(By by,String value){
		AndroidElement element = driver.findElement(by);
		element.sendKeys(value);
	}
	
	/**
	 * 点击元素
	 * @param element
	 * */
	public void click(AndroidElement element){
		if(element!=null){
			element.click();
		}else{
			logger.error("元素没有定位到，是null");;
		}
	}
	/**
	 * 定位并点击
	 * @param By by
	 * */
	public void click(By by){
		AndroidElement element=driver.findElement(by);
		click(element);
	}
	
	/**
	 * 清除
	 * @param element
	 * **/
	public void clear(AndroidElement element){
		if(element!=null){
			element.clear();
		}else{
			logger.error("元素没有定位到，是null");
		}
	}
	/**
	 * 逐个清除，对于密码输入框是无效的
	 * */
	public void clearOneByOne(AndroidElement element){
		if(element!=null){
			element.click();
			driver.pressKeyCode(AndroidKeyCode.KEYCODE_MOVE_END);
			String text=element.getText();
			driver.pressBackspace(text.length());
		}else{
			logger.error("元素没有定位到，是null");
		}
	}
	
	/**
	 * 输入内容，直到正确
	 * @param element
	 * @param value
	 * */
	public void sendkeysUntilCorrect(AndroidElement element,String value){
		sendkeys(element, value);
		boolean flag = true;
		while(flag){
			if(value.equals(element.getText())){
				flag = false;
			}else{
				sendkeys(element, value);
			}
		}
	}
	
	/**
	 * 逐个输入数字，模拟的是键盘输入，15610112934
	 * @param String text
	 * */
	public void sendKeysOneByOne(String text){
		char[] chr=text.toCharArray();//{1,5,6,1,x,x,x,x}
		for(int i=0;i<chr.length;i++){
			int c=Integer.valueOf(String.valueOf(chr[i]));
			int number=0;
			switch (c) {
			case 0:
				//driver.pressKeyCode(AndroidKeyCode.KEYCODE_0);
				number=AndroidKeyCode.KEYCODE_0;
				break;
			case 1:
				number=AndroidKeyCode.KEYCODE_1;
				break;
			case 2:
				number=AndroidKeyCode.KEYCODE_2;
				break;
			case 3:
				number=AndroidKeyCode.KEYCODE_3;
				break;
			case 4:
				number=AndroidKeyCode.KEYCODE_4;
				break;
			case 5:
				number=AndroidKeyCode.KEYCODE_5;
				break;
			case 6:
				number=AndroidKeyCode.KEYCODE_6;
				break;
			case 7:
				number=AndroidKeyCode.KEYCODE_7;
				break;
			case 8:
				number=AndroidKeyCode.KEYCODE_8;
				break;
			case 9:
				number=AndroidKeyCode.KEYCODE_9;
				break;
			default:
				System.out.println("数值不对");
				break;
			}
			driver.pressKeyCode(number);//driver.pressKeyCode(16)=9;
		}
	}
	/**
	 * 坐标点击，针对一些能定位到整体元素，但是无法定位具体元素，并且有规律的元素
	 * 获取每个子元素的中心坐标点
	 * */
	public List<Point> getElementByCoordinates(AndroidElement element,int rows,int columns){
		List<Point> elementResolve = new ArrayList<Point>();
		if(element != null){
			int startx = element.getLocation().getX();//获取元素开始的x坐标
			int starty = element.getLocation().getY();//获取元素开始的y坐标
			int offsetx = element.getSize().getWidth();//获取元素的宽度
			int offsety = element.getSize().getHeight();//获取元素的高度
			//通过获取元素的开始位置和元素的大小，然后通过分成行、列然后再寻找中心点。
			for(int i=0;i<rows;i++){
				for(int j=0;j<columns;j++){
					int x = startx+(offsetx/2*columns)*(2*j+1);
					int y = starty+(offsety/(2*rows)*(2*i+1));
					Point p = new Point(x,y);
					elementResolve.add(p);
				}
			}
		}
		return elementResolve;
	}
	/**
	 * 根据整体元素拆分后的规律，对子元素进行点击操作
	 * */
	public void clickElementByCoordinate(AndroidElement element,int rows,int columns,int index){
		if(element != null){
			List<Point> elementResolve = getElementByCoordinates(element,rows,columns);
			if(!elementResolve.isEmpty() && elementResolve !=null){
				driver.clickByCoordinate(elementResolve.get(index).x, elementResolve.get(index).y);
			}else{
				logger.error("通过坐标点击失败，坐标为空");
			}
		}else{
			logger.error("元素为空");
		}
	}
	
	/**
	 * 输入安全键盘的密码, 128606,{1,2,3,4,5,6,7,8,9, ,0,x}
	 * @param List<Point> pwd 传入的是getElementByCoordinates方法的点
	 * */
	public void sendkeysPwd(List<Point> pwd,int[] password){
		if(password.length>0){
			for(int i=0;i<password.length;i++){
				if(password[i]==0){
					driver.clickByCoordinate(pwd.get(10).x, pwd.get(10).y);
				}else{
					driver.clickByCoordinate(pwd.get(password[i]-1).x, pwd.get(password[i]-1).y);
				}
			}
		}
	}
	
	
	/**
	 * 输入安全键盘的密码, 128606
	 * @param element
	 * 
	 * */
	public void sendkeysPwd(AndroidElement element,int rows,int columns,int[] password){
		if(element!=null){
			List<Point> pwd=getElementByCoordinates(element,rows,columns);
			sendkeysPwd(pwd,password);
		}

	}
	
	/**
	 * 九宫格手势解锁,参数indexs是密码数字组成的数组，参数indexs={5,2,3,6,8,7}
	 * */
	public void wakeByGestures(AndroidElement element,int[] indexs){
		if(element!=null){
			List<Point> elementResolve=getElementByCoordinates(element,3,3);
			TouchAction ta=null;
			if(indexs.length>0){
				ta=new TouchAction(driver).press(elementResolve.get(indexs[0]-1).x, elementResolve.get(indexs[0]-1).y).waitAction(500);
				for(int i=1;i<indexs.length;i++){
					ta.moveTo(elementResolve.get(indexs[i]-1).x-elementResolve.get(indexs[i-1]-1).x, elementResolve.get(indexs[i]-1).y-elementResolve.get(indexs[i-1]-1).y).waitAction(500);
				}
				ta.release().perform();
			}else{
				logger.error("密码参数有误");
			}

		}
	}
	
		
}
