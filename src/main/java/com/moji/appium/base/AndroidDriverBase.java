package com.moji.appium.base;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.moji.appium.util.DosCmd;
import com.moji.appium.util.Log;
import com.moji.appium.util.ProUtil;

import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
public class AndroidDriverBase extends AndroidDriver{
	
	private Log logger = Log.getLogger(AndroidDriverBase.class);
	public String input; //输入法
	public String udid; //设备udid
	/**
	 * 初始化driver，生成各项参数
	 * */
	public AndroidDriverBase(String server, String port,String capsPath,String udid,String input) throws Exception {
		super(new URL(server+":"+port+"/wd/hub"),new CrazyCapabilities().initCaps(capsPath,udid));
		this.input = input;
		this.udid = udid;
		
	}
	public AndroidDriverBase(String server, String port,String capsPath,String udid,String input,int waitFlag) throws Exception {
		super(new URL(server+":"+port+"/wd/hub"),new CrazyCapabilities().initCaps(capsPath,udid));
		// TODO Auto-generated constructor stub
		System.out.println("################################");
		this.input = input;
		this.udid = udid;
	}
	
	
	
	/**
	 * 获取app应用占取屏幕的宽度
	 * @return 返回宽度
	 * */
	public int appScreenWidth(){
		int width = this.manage().window().getSize().getWidth();
		logger.info("获取app占用屏幕的宽度:"+width);
		return width;
	}
	
	/**
	 * 获取app应用占取屏幕高度
	 * @return 返回高度
	 * */
	public int appScrrenHeight(){
		int height = this.manage().window().getSize().getHeight();
		logger.info("获取app占用屏幕的宽度:"+height);
		return height;
	}
	
	/**
	 * 判断元素是否存在
	 * @param By.id("")
	 * @return 存在则返回true，不存在返回false
	 * */
	public boolean isElementExist(By by){
		try{
			super.findElement(by).isDisplayed();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * 指定时间内判断元素是否存在
	 * @param By 查找对象
	 * @return true\false  返回数据
	 * @param timeout 超时时间
	 * */
	public boolean isElementExist(By by,int timeout){
		try{
			new WebDriverWait(this, timeout).until(ExpectedConditions.presenceOfElementLocated(by));
			return true;
		}catch(Exception e){
			logger.debug("element not found");
			return false;
		}
	}
	/**
	 * 查找元素是否存在，存在则返回该元素对象，不存在返回null
	 * @param By by
	 * @return element
	 * */
	@Override
	public AndroidElement findElement(By by){
		try{
		AndroidElement element = (AndroidElement) super.findElement(by);
		return element;
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 查找对象，在一定时间里面没找到返回null
	 * @param By 
	 * @param timeout
	 * @return element
	 * 
	 * */
	public AndroidElement findElement(final By by,int timeout){
		try {
			AndroidElement element=new WebDriverWait(this, timeout).until(new ExpectedCondition<AndroidElement>() {
				@Override
				public AndroidElement apply(WebDriver driver) {
					// TODO Auto-generated method stub
					return (AndroidElement) driver.findElement(by);
				}
			});
			return element;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	/**
	 * 滑动屏幕，通过各项参数实现各个方向滑动
	 * @param direction 方向参数up\down\right\left
	 * @param duration 滑动时间，毫秒
	 * */
	public void swipe(String direction,int duration){
		try{
			SwipeScreen swipeScreen = new SwipeScreen(this);
			swipeScreen.swipe(direction, duration);
		}catch(Exception e){
			logger.error(e);
		}
	}
	
	
	/**
	 * 在元素上进行滑动
	 * @param element
	 * @param String derction
	 * @param int duration
	 * 
	 * */
	public void swipeOnElement(AndroidElement element,String derction,int duration){
		String derc = derction.toLowerCase();
		switch(derc){
		case "up":
			element.swipe(SwipeElementDirection.UP, 10, 10, duration);
			break;
		case "down":
			element.swipe(SwipeElementDirection.DOWN, 10, 10, duration);
			break;
		case "left":
			element.swipe(SwipeElementDirection.LEFT, 10, 10, duration);
			break;
		case "right":
			element.swipe(SwipeElementDirection.RIGHT, 10, 10, duration);
			break;
		default:
			logger.error("方向、element参数有错误");
			break;
		}
	}
	
	
	/**
	 * 查找element，然后再在element上进行滑动
	 * @param androidElement element
	 * @param String derction
	 * @parame int duration
	 * */
	public void swipeOnElement(By by,String derction,int duration){
		AndroidElement element = this.findElement(by);
		this.swipeOnElement(element, derction, duration);
	}
	
	
	
	
	
	
	/**
	 * 向某方向滑动直到某元素出现
	 * @param By by
	 * @param direction 滑动方向
	 * @param duration 每次滑动时间 毫秒
	 * @param findcount 查找次数
	 * 
	 * */
	public boolean swipeUntilElementAppear(By by,String direction,int duration,int findCount){
		boolean flag = false;
		while(!flag&&findCount>0){
			try{
				super.findElement(by);
				flag=true;
			}catch(Exception e){
				this.swipe(direction, duration);
				findCount--;
			}
		}
		return flag;
	}
	
	/**
	 * 向某方向滑动直到某元素出现,滑到死
	 * @param By by
	 * @param direction 滑动方向
	 * @param duration 每次滑动时间 毫秒
	 * 
	 * */
	public boolean swipeUntilElementAppear(By by,String direction,int duration){
		boolean flag = false;
		int findCount = 1;
		while(!flag){
			try{
				super.findElement(by);
				flag=true;
			}catch(Exception e){
				this.swipe(direction, duration);
				findCount++;
				if(findCount>20){
					flag=true;
					logger.error("元素没有找到，使用了20次查找");
				}	
				flag = false;
			}
		}
		return flag;
	}
	
	/**
	 * 查找，直到找到某个element,返回找到的element
	 * @param by
	 * @param direction  方向
	 * @param duration   时间
	 * @param findCount
	 * 
	 * */
	
	public AndroidElement swipeUntilElement(By by,String direction,int duration,int findCount){
		AndroidElement element =null;
		boolean flag = false;
		while(!flag&&findCount>0){
			try{
				element = (AndroidElement) super.findElement(by);
				flag = true;
			}catch(Exception e){
				this.swipe(direction, duration);
				findCount--;
			}
		}
		return element;
	}
	
	/**
	 * 重置输入法
	 * @param input 输入法
	 * */
	
	public void resetInput(){
		DosCmd dc = new DosCmd();
		try{
			dc.execCmdConsonle("adb -s "+udid+" shell \" ime set "+input+"\"");
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 切换输入法
	 * @param input
	 */
	public void resetInput(String input){
		DosCmd dc=new DosCmd();
		try {
			dc.execCmdConsonle("adb shell \"ime set "+input+"\"");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取输入法list
	 * 
	 * */
	public List<String> getIme(){
		List<String> ime = new ArrayList<String>();
		DosCmd dc=new DosCmd();
		try {
			ime = dc.execCmdConsonle("adb shell ime list -s");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return ime;
	}
	
	
	/**
	 * 与服务器断开连接
	 * */
	public void quit(){
		super.quit();
		this.resetInput();
	}
	/**
	 * 单击某元素
	 * @param element
	 * @param duration
	 * */
	public void tapSingle(AndroidElement element){
		super.tap(1, element, 100);
	}
	
	/**
	 * touchAction 触摸式的点击一下
	 * @param element
	 * 
	 * */
	public void tap(AndroidElement element){
		try{
			TouchAction action = new TouchAction(this);
			action.tap(element).release().perform();
		}catch(Exception e){
			logger.error(e);
		}
	}
	
	/**
	 * 点击某个坐标
	 * @param x
	 * @param y
	 * 
	 * */
	public void clickByCoordinate(int x,int y){
		try{
			TouchAction ta = new TouchAction(this);
			ta.tap(x, y).release().perform();
			logger.info("成功点击坐标x:"+x+"y坐标："+y);
		}catch(Exception e){
			logger.error("点击坐标失败:"+e);;
		}
	}

	
	/**
	 * 元素长按
	 * @param by
	 */
	public void longPress(By by){
		try {
			AndroidElement element=(AndroidElement) super.findElement(by);
//			TouchAction ta=new TouchAction(this);
//			ta.longPress(element).release().perform();
			this.longPress(element);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 元素长按
	 * @param by
	 */
	public void longPress(AndroidElement element){
		try {
			//AndroidElement element=(AndroidElement) super.findElement(by);
			TouchAction ta=new TouchAction(this);
			ta.longPress(element).release().perform();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 在某一个坐标点上长按
	 * @param x
	 * @param y
	 */
	public void longPress(int x,int y){
		try {
			TouchAction ta=new TouchAction(this);
			ta.longPress(x,y).release().perform();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 点击某个元素
	 * @param AndroidElement element
	 * */
	public void clickElement(AndroidElement element){
		try{
			element.click();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取页面源代码
	 * */
	public String getPageSouce(){
		return super.getPageSource();
	}
	
	/**
	 * 隐式等待 秒
	 * @param timeout
	 */
	public void implicitlyWait(int timeout){
		super.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}
	/**
	 * 默认隐士等待时间
	 * 
	 */
	public void implicitlyWaitDefault(){
		ProUtil p=new ProUtil(CrazyPath.globalPath);
		super.manage().timeouts().implicitlyWait(Integer.valueOf(p.getPro("implicitlyWait")), TimeUnit.SECONDS);
	}
	/**
	 * 切回没有隐士等待
	 * */
	public void implicitlyWaitZero(){
		super.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}
	
	/**
	 * 获取手机分辨率
	 * @return
	 */
	public Point getMobileSize(){
		//adb shell wm size
		DosCmd dos=new DosCmd();
		Point p=null;
		try {
			List<String> resList=dos.execCmdConsonle("adb -s "+udid+" shell wm size");
			String[] size=new String[2];
			if(resList.size()>0){
					size=resList.get(0).split(": ")[1].split("x");
			}
			p=new Point(Integer.valueOf(size[0]),Integer.valueOf(size[1]));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return p;
	}
	
	/**
	 * 判断两个字符串结合list是否元素相等
	 * @param strSrc
	 * @param strDes
	 * @return boolean
	 * 
	 * */
	/**
	 * 判断两个字符串集合list是否元素对应相等
	 * @param strSrc
	 * @param strDes
	 * @return
	 */
	public Boolean listStrEquals(List<String> strSrc,List<String> strDes){
		Boolean flag=false;
		if((!strSrc.isEmpty()&&strSrc!=null)&&(!strDes.isEmpty()&&strDes!=null)){
			if(strSrc.size()==strDes.size()){
				for(int i=0;i<strDes.size();i++){
					if(strSrc.get(i).equals(strDes.get(i))){
						flag=true;
						continue;
					}else{
						flag=false;
						//System.out.println(strSrc.get(i)+" "+strDes.get(i));
						break;
					}
				}
			}else{
				System.out.println("两个list大小不相等");
			}
		}else{
			System.out.println("list为空或者为null");
		}
		return flag;
	}
	/**
	 * 从指定androidelement集合中根据索引选取某一个
	 * @param srcList
	 * @param index
	 * @return
	 */
	public AndroidElement selectElementFromList(List<AndroidElement> srcList,int index){
		AndroidElement element=null;
		if(!srcList.isEmpty()&&srcList!=null){
			if(index>=0&&index<srcList.size()){
				element=srcList.get(index);
			}else{
				System.out.println("集合大小为"+srcList.size()+"但是索引参数传值为"+index);
			}
		}else{
			System.out.println("集合为空或者为null");
		}
		return element;
	}
	/**
	 * 从指定androidelement集合中根据索引选取某一个
	 * @param srcList
	 * @param index
	 * @return
	 */
	public AndroidElement selectElementFromList(By by,int index){
		List<AndroidElement> srcList=super.findElements(by);
		AndroidElement element=null;
		if(!srcList.isEmpty()&&srcList!=null){
			if(index>=0&&index<srcList.size()){
				element=srcList.get(index);
			}else{
				System.out.println("集合大小为"+srcList.size()+"但是索引参数传值为"+index);
			}
		}else{
			System.out.println("集合为空或者为null");
		}
		return element;
	}
	/**
	 * 从AndroidElement集合中选出text值与预期相符的第一个元素
	 * @param srcList
	 * @param strFind
	 * @return
	 */
	public AndroidElement selectElementFromList(List<AndroidElement> srcList,String strFind){
		AndroidElement element=null;
		if(!srcList.isEmpty()&&srcList!=null){
			for(AndroidElement ae:srcList){
				if(ae.getAttribute("text").equals(strFind)){
					element=ae;
					break;
				}
			}
		}else{
			System.out.println("集合为空或者为null");
		}
		return element;
	}
	/**
	 * 某方向滑动直到边界，如底部，顶部(在没有边界标识的时候使用)
	 * @param direction
	 * @param strSrc
	 * @param strDes
	 * @return
	 * 实现滑动前后的字符串集合list的对比
	 */
	public void swipeUntilBoundary(String direction,By by){
		boolean flag=false;
		while(!flag){
			List<String> strSrc=new ArrayList<String>();
			List<String> strDes=new ArrayList<String>();
			//滑动前定位元素并将元素的text添加到集合strSrc里
			List<AndroidElement> elementOld=super.findElements(by);
			for(AndroidElement ae:elementOld){
				strSrc.add(ae.getText());
				
			}
			//这里可以增加对每个元素的操作
			this.swipe(direction, 500);
			this.wait(1000);
			//滑动后定位元素并将元素的text添加到集合strSrc里
			List<AndroidElement> elementNew=super.findElements(by);
			for(AndroidElement ae:elementNew){
				strDes.add(ae.getText());
			}
			flag=this.listStrEquals(strSrc,strDes);
		}
	}
	
	/**
	 * 设备返回键操作
	 */
	public void pressBack(){
		this.wait(500);
		super.pressKeyCode(AndroidKeyCode.BACK);
	}
	/**
	 * 多次返回
	 * @param number
	 */
	public void pressBack(int number){
		if(number>0){
			for(int i=0;i<number;i++){
				this.pressBack();
				System.out.println("执行第"+String.valueOf(i+1)+"次返回");
			}
		}else{
			System.out.println("参数有误");
		}
	}
	/**
	 * 设备home键操作
	 */
	public void pressHome(){
		this.wait(500);
		super.pressKeyCode(AndroidKeyCode.HOME);
	}
	/**
	 * 设备回车键操作
	 */
	public void pressEnter(){
		this.wait(500);
		super.pressKeyCode(AndroidKeyCode.ENTER);
	}
	/**
	 * 手机键盘删除操作
	 */
	public void pressBackspace(){
		this.wait(200);
		super.pressKeyCode(AndroidKeyCode.BACKSPACE);
	}
	/**
	 * 多次手机键盘删除操作
	 * @param numbers
	 */
	public void pressBackspace(int numbers){
		if(numbers>0){
			for(int i=0;i<numbers;i++){
				this.pressBackspace();
				System.out.println("执行第"+String.valueOf(i+1)+"次删除");
			}
		}else{
			System.out.println("参数有误");
		}
	}
	/**
	 * 唤醒屏幕//版本变化可以直接调用
	 */
	public void wakeUp() {
		try {
			if(super.isLocked()){
				super.unlockDevice();
				//AndroidSpecific.wakeUp(udid);
			}else{
				System.out.println("未锁屏不用唤醒");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 等待，死等
	 * @param milliSecond
	 */
	public void wait(int milliSecond){
		try {
			Thread.sleep(milliSecond);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 截图全屏
	 * @param String path
	 * @param String fileName
	 * 
	 * */
	public void takeScreen(String path,String fileName) throws Exception{
		File srcFile=super.getScreenshotAs(OutputType.FILE);
		System.out.println(path+fileName);
		FileUtils.copyFile(srcFile,new File(path+"/"+fileName));
	}
	/**
	 * 针对元素进行截图
	 * @param AndroidElement element
	 * @param String path
	 * @param String fileName
	*/
	public void takeScreenForElement(AndroidElement element,String path,String fileName) throws Exception{
		 // 获得element的位置和大小
		 Point location = element.getLocation();
		 Dimension size = element.getSize();
		 byte[] imageByte=super.getScreenshotAs(OutputType.BYTES);
		 // 创建全屏截图
		 BufferedImage originalImage =ImageIO.read(new ByteArrayInputStream(imageByte));
		 // 截取element所在位置的子图。
		 BufferedImage croppedImage = originalImage.getSubimage(
		  location.getX(),
		  location.getY(),
		  size.getWidth(),
		  size.getHeight());
		 try {
			  ImageIO.write(croppedImage, "png", new File(path+fileName+".png"));
				//ImageIO.write(im, formatName, output)
		 }catch (IOException e) {
				// TODO Auto-generated catch block
			  e.printStackTrace();
		 }
	}
	/**
	 * 针对元素进行截图
	 * @param By by
	 * @param String path
	 * @param String fileName
	 * */
	public void takeScreenForElement(By by,String path,String fileName) throws Exception{
		 // 获得element的位置和大小
		 AndroidElement element=(AndroidElement) super.findElement(by);
		 this.takeScreenForElement(element, path, fileName);
	}
	
	
	
}
