package com.moji.appium.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.moji.appium.server.Port;
import com.moji.appium.server.Servers;


public class XmlUtil {
	
	/**
	 * 创建device.xml文件
	 * @param deviceList
	 * @param appiumPortList
	 * @throws IOException 
	 * */
	public static void createDeviceXml(List<String> deviceList,List<Integer> appiumPortList) throws IOException{
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("Device");
		document.setRootElement(root);
		root.addAttribute("name", "appiumstartList");
		if(deviceList.size()>0){
			for(int j=0;j<deviceList.size();j++){
				Element deviceId = root.addElement("deviceId");
				deviceId.addAttribute("id", String.valueOf(j));
				Element deviceName = deviceId.addElement("deviceName");
				Element appiumPort = deviceId.addElement("appiumPort");
				deviceName.setText(deviceList.get(j));
				appiumPort.setText(String.valueOf(appiumPortList.get(j)));
			}
		}
		OutputFormat format = new OutputFormat("    ",true);
		XMLWriter xmlwriter = new XMLWriter(new FileOutputStream("configs/device.xml"),format);
		xmlwriter.write(document);
	}
	
	/**
	 * 读取device.xml配置文件  127.0.0.1:62025，4490，127.0.0.1:62001,4491
	 * @param filePath   
	 * @return
	 * @throws DocumentException
	 */
	public static List<String> readXML(String filePath) throws DocumentException {
		
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new File(filePath));
		// 获取根元素
		Element root = document.getRootElement();
		//System.out.println(root.getName());

		// 获取特定名称的子元素
		@SuppressWarnings("unchecked")
		List<Element> deviceList = root.elements("deviceId");
		// 迭代输出
		//{"127.0.0.1:62001","4490","127.0.0.1:62002","4492"}
		List<String> deviceData = new ArrayList<String>();
		for (Element e : deviceList) {
			for (Iterator iter = e.elementIterator(); iter.hasNext();) {
				Element e1 = (Element) iter.next();
				deviceData.add(e1.getText());
			}
		}
		return deviceData;
	}
	/**
	 * 创建testng.xml配置文件
	 * 配置多个设备
	 * 
	 * @param classname
	 * @throws Exception
	 */
	public static void createTestngXml(String classname) throws Exception {
		Servers servers=new Servers(new Port(new DosCmd()), new DosCmd());
		List<String> deviceList=servers.getDevices();
		System.out.println("设备数量"+deviceList.size());
		Document document = DocumentHelper.createDocument();
		document.addDocType("suite", null, "http://testng.org/testng-1.0.dtd");
		Element root = DocumentHelper.createElement("suite");
		document.setRootElement(root);
		root.addAttribute("name", "Suite");
		root.addAttribute("parallel", "tests");
		root.addAttribute("thread-count", String.valueOf(deviceList.size()));
		Element listeners=root.addElement("listeners");
		Element listener1=listeners.addElement("listener");
		listener1.addAttribute("class-name", "com.moji.appium.listener.ExtentTestNGIReporterListener");
		Element listener2=listeners.addElement("listener");
		listener2.addAttribute("class-name", "com.moji.appium.listener.mojiScreenShotListener");
		List<String> s=readXML("configs/device.xml");
		//{192.168.56.101:5555,4490,192.168.56.102:5555,4491,xxx,4492,yyy,4493}
		for(int j=0;j<deviceList.size();j++){
			Element test = root.addElement("test");
			test.addAttribute("name", deviceList.get(j));
			Element paramUuid=test.addElement("parameter");
			paramUuid.addAttribute("name","udid");
			paramUuid.addAttribute("value",s.get(2*j));
			Element paramPort=test.addElement("parameter");
			paramPort.addAttribute("name", "port");
			paramPort.addAttribute("value",s.get(2*j+1));
			Element classes = test.addElement("classes");
			Element classNode=classes.addElement("class");
			classNode.addAttribute("name", classname);
		}
		OutputFormat format = new OutputFormat("    ", true);
		XMLWriter xmlWrite2;
		try {
			xmlWrite2 = new XMLWriter(new FileOutputStream("testng.xml"),format);
			xmlWrite2.write(document);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 多个设备执行同一个测试用例
	 * */
	public static void createTestngXml(List<String> classesList) throws Exception {
		Servers servers=new Servers(new Port(new DosCmd()), new DosCmd());
		List<String> deviceList=servers.getDevices();
		System.out.println("设备数量"+deviceList.size());
		Document document = DocumentHelper.createDocument();
//		增加注释，按testng要求
		document.addDocType("suite", null, "http://testng.org/testng-1.0.dtd");
		Element root = DocumentHelper.createElement("suite");
		document.setRootElement(root);
		root.addAttribute("name", "Suite");
		root.addAttribute("parallel", "tests");
		root.addAttribute("thread-count", String.valueOf(deviceList.size()));
		Element listeners=root.addElement("listeners");
		Element listener1=listeners.addElement("listener");
		listener1.addAttribute("class-name", "com.moji.appium.listener.ExtentTestNGIReporterListener");
		Element listener2=listeners.addElement("listener");
		listener2.addAttribute("class-name", "com.moji.appium.listener.mojiScreenShotListener");
		List<String> s=readXML("configs/device.xml");
		//{192.168.56.101:5555,4490,192.168.56.102:5555,4491,xxx,4492,yyy,4493}
		for(int j=0;j<deviceList.size();j++){
			Element test = root.addElement("test");
			test.addAttribute("name", deviceList.get(j));
			Element paramUuid=test.addElement("parameter");
			paramUuid.addAttribute("name","udid");
			paramUuid.addAttribute("value",s.get(2*j));
			Element paramPort=test.addElement("parameter");
			paramPort.addAttribute("name", "port");
			paramPort.addAttribute("value",s.get(2*j+1));
			Element classes = test.addElement("classes");
			for(String className:classesList){
				Element classNode=classes.addElement("class");
				classNode.addAttribute("name", className);
			}
		}
		OutputFormat format = new OutputFormat("    ", true);
		XMLWriter xmlWrite2;
		try {
			xmlWrite2 = new XMLWriter(new FileOutputStream("testng.xml"),format);
			xmlWrite2.write(document);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 针对不同的手机执行不同的测试类，需要传入多个测试类 list
	 * */
	public static void createTestngSingleXml(List<String> classesList) throws Exception {
		Servers servers=new Servers(new Port(new DosCmd()), new DosCmd());
		List<String> deviceList=servers.getDevices();
		System.out.println("设备数量"+deviceList.size());
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("suite");
		document.setRootElement(root);
		root.addAttribute("name", "Suite");
		root.addAttribute("parallel", "tests");
		root.addAttribute("thread-count", String.valueOf(deviceList.size()));
		Element listeners=root.addElement("listeners");
		Element listener1=listeners.addElement("listener");
		listener1.addAttribute("class-name", "org.uncommons.reportng.HTMLReporter");
		Element listener2=listeners.addElement("listener");
		listener2.addAttribute("class-name", "org.uncommons.reportng.JUnitXMLReporter");
		List<String> s=readXML("configs/device.xml");
		//{192.168.56.101:5555,4490,192.168.56.102:5555,4491,xxx,4492,yyy,4493}
		for(int j=0;j<deviceList.size();j++){
			Element test = root.addElement("test");
			test.addAttribute("name", deviceList.get(j));
			Element paramUuid=test.addElement("parameter");
			paramUuid.addAttribute("name","udid");
			paramUuid.addAttribute("value",s.get(2*j));
			Element paramPort=test.addElement("parameter");
			paramPort.addAttribute("name", "port");
			paramPort.addAttribute("value",s.get(2*j+1));
			Element classes = test.addElement("classes");
			Element classNode=classes.addElement("class");
			classNode.addAttribute("name", classesList.get(j));
		}
		OutputFormat format = new OutputFormat("    ", true);
		XMLWriter xmlWrite2;
		try {
			xmlWrite2 = new XMLWriter(new FileOutputStream("testng.xml"),format);
			xmlWrite2.write(document);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String args[]){
		List<String> classesList1=new ArrayList<String>();
		classesList1.add("com.mushishi.appiumstudy.App");
		//classesList1.add("com.mushishi.appiumstudy.UploadPhoto");
		//classesList1.add("cn.crazy.appium.network.study.Study2");
		System.out.println(classesList1.size());
		//createTestngSingleXml(classesList);
	}
	
}
