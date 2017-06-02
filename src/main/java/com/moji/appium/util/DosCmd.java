package com.moji.appium.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.moji.appium.server.Port;

public class DosCmd {
	private Log logger=Log.getLogger(DosCmd.class);
	String osName=System.getProperty("os.name");
	/**
	 * 获取执行命令结果，并将结果以list形式返回
	 * @throws InterruptedException 
	 * */
	public List<String> execCmdConsonle(String cmdString) throws InterruptedException{
		List<String> dosRes = new ArrayList<String>();
		try {
			Process process=null;
			System.out.println(cmdString);
			if(osName.toLowerCase().contains("mac")){
				String[] command={"/bin/sh","-c",cmdString};
				process = Runtime.getRuntime().exec(command);
//				process = Runtime.getRuntime().exec("/Users/phoenixzhang/Library/Android/sdk/platform-tools/adb devices");
			}else if(osName.toLowerCase().contains("win")){
				process = Runtime.getRuntime().exec("cmd /c "+cmdString);
			}
			InputStream in = process.getInputStream();
			BufferedReader inr = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = inr.readLine()) != null) {
				dosRes.add(line);
			}
			process.waitFor();
			process.destroy();
			logger.debug("获取adb devicesList成功： "+cmdString+"\n");
		} catch (IOException e) {
			logger.error("获取deviceList失败："+cmdString, e);
		}
		return dosRes;
	}
	
	/**
	 * 执行命令，不收集返回结果
	 * execCmd
	 * */
	public boolean execCmd(String cmdString){
		Runtime p = Runtime.getRuntime();
		try{
			if(osName.toLowerCase().contains("mac")){
				String[] command={"/bin/sh","-c",cmdString};
				Process process=p.exec(command);
			}else if(osName.toLowerCase().contains("win")){
				Process process=p.exec("cmd /c "+cmdString);
			}
			long portNum = Long.parseLong((RandomUtil.getInt(cmdString, 0)));
			System.out.print(portNum+"\n");
			if(portNum>0){
				Port port = new Port(new DosCmd());
				while(!port.isPortUsed(portNum)){
					Thread.sleep(1000);
				}
			}
			logger.debug("命令"+cmdString+"执行成功");
			return true;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("命令"+cmdString+" 执行失败", e);
			return false;
		}
	}
	
	/**
	 * 清理环境，启动前关闭所有服务
	 * */
	public boolean killServer(){
		String command = "taskkill -F -PID node.exe";
		if(osName.toLowerCase().contains("mac")){
			command="killall node";
		}else if(osName.toLowerCase().contains("wind")){
			command = "taskkill -F -PID node.exe";
		}else{
			command = "taskkill -F -PID node.exe";
		}
		if(execCmd(command)){
			logger.debug("node服务清理成功");
			return true;
		}else{
			logger.error("node服务清理失败");
			return false;
		}
	}
	
	
	/***
	public static void main(String args[]) throws InterruptedException{
		DosCmd dos = new DosCmd();
		List<String> deviceList = dos.execCmdConsonle("adb devices");
		for(String s:deviceList){
			System.out.println(s);
		}
	}*/

}
