package com.moji.appium.server;

import java.util.ArrayList;
import java.util.List;

import com.moji.appium.util.DosCmd;
import com.moji.appium.util.Log;
import com.moji.appium.util.XmlUtil;

public class Servers {
	private Log logger=Log.getLogger(Servers.class);
	private DosCmd dos;
	private List<String> deviceList;
	private Port port;
	private List<Integer> appiumPortList;
	private List<Integer> bootstrapPortList;
	private String path=System.getProperty("user.dir");
	public Servers(Port port,DosCmd dos){
		this.port=port;
		this.dos=dos;
	}
	
	/**
	 * 获取设备
	 * @throws Exception 
	 * */
	public List<String> getDevices() throws Exception{
		logger.debug("当前运行的是"+this.getClass()+"类的----》》》"+this.getClass().getName()+"方法");
		List<String> devList = dos.execCmdConsonle("adb devices");
		List<String> deviceRes = new ArrayList<String>();
		if(devList.size()>2){
			for(int i=1;i < devList.size()-1;i++){
				String deviceInfo[] = devList.get(i).split("\t");
				if(deviceInfo[1].trim().equals("device")){
					deviceRes.add(deviceInfo[0].trim());
				}
			}
		}else{
			logger.error("获取device设备失败,没有设备可以获取");
		}
		return deviceRes;
		
	}
	
	/**
	 *根据设备数获取端口
	 * @throws Exception 
	 * 
	 * */
	public List<Integer> getPortList(int start) throws Exception{
		System.out.print("当前运行的是"+this.getClass()+"类的----》》》"+this.getClass().getName()+"方法");
		List<String> deviceList = getDevices();
		List<Integer> portList = port.GeneratPortList(start, deviceList.size());
		return portList;
	}
	
	
	
	/**
	 * 生成服务端启动命令字符串存入List
	 * 并且将devicesList 和appiumPortList写入到xml文件中
	 * @throws Exception 
	 * 
	 * */
	public List<String> GeneratServerCommand() throws Exception{
		System.out.print("当前运行的是"+this.getClass()+"类的----》》》"+this.getClass().getName()+"方法");
		appiumPortList = getPortList(4490);
		bootstrapPortList = getPortList(2233);
		deviceList = getDevices();
		List<String> commandList = new ArrayList<String>();
//		获取device list size遍历后生成server启动命令，需要获取portlist和bootstrap portlist，bootstrap和port传不同的值可以进行并发测试
		for(int i =0;i<deviceList.size();i++){
			String command = "appium -p"+appiumPortList.get(i)+" -bp "+bootstrapPortList.get(i)
			+" -U "+ deviceList.get(i)+">"+path+"/logs/"+deviceList.get(i).split(":")[0]+i+".log";
			commandList.add(command);
//			String command = "/usr/local/bin/appium -p "+appiumPortList.get(i)+" -bp "+bootstrapPortList.get(i)
//			+" -U "+ deviceList.get(i)+">"+path+"/logs/"+deviceList.get(i).split(":")[0]+i+".log";
//			commandList.add(command);
			System.out.println(command);
		}
//		创建device xml用于创建testng xml时读取
		XmlUtil.createDeviceXml(deviceList, appiumPortList);
		//配置testng.xml
		return commandList;
	}
	/**
	 * 启动多个服务
	 * @throws Exception 
	 * 
	 * */
	public boolean startServers() throws Exception{
		System.out.print("当前运行的是"+this.getClass()+"类的----》》》"+this.getClass().getName()+"方法");
		List<String> startCommand = GeneratServerCommand();
		System.out.println("===" + startCommand + "===");
		boolean flag = false;
		if(startCommand.size()>0){
			for(String s:startCommand){
				System.out.println("===" + s + "===");
				dos.execCmd(s);
			}
			flag=true;
		}else{
			flag=false;
		}
		return flag;
	}
	

/*	public static void main(String args[]){
		Servers server = new Servers(new Port(new DosCmd()),new DosCmd());
		try {
			server.startServers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

public static void main(String args[]) throws InterruptedException{
	String s1 = "/usr/local/bin/appium -p 4490 -bp 2235 -U MSM8926";
	DosCmd dos = new DosCmd();
	dos.execCmdConsonle(s1);		
	}
}
