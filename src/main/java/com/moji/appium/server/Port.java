package com.moji.appium.server;

import java.util.ArrayList;
import java.util.List;

import com.moji.appium.util.DosCmd;
import com.moji.appium.util.Log;


public class Port {
	private DosCmd execDos;
	private Log logger=Log.getLogger(Port.class);
	public Port(DosCmd execDos){
		this.execDos=execDos;
	}
	/**
	 * 验证端口是否被占用
	 * @author mushishi
	 * @param portNum 
	 * */
	public Boolean isPortUsed(long portNum){
		System.out.print("当前运行的是"+this.getClass()+"类的----》》》"+this.getClass().getName()+"方法");
		String osName = System.getProperty("os.name");
		List<String> portRes = new ArrayList<String>();
		String command = "";
		boolean flag=true;
		if(osName.toLowerCase().contains("mac")){
			command = "netstat -an | grep "+portNum;
		}else if(osName.toLowerCase().contains("win")){
			command = "netstat -ano|findstr " + portNum;
		}
		try {
			portRes = execDos.execCmdConsonle(command);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(portRes.size()>0){
			logger.error("端口被占用了"+portNum);
		}else{
			
			logger.debug("端口没被占用"+portNum);
			flag=false;
		}
		
		return flag;
	}
	/**
	 * 基于当前设备数量生成可用端口
	 * @author mushishi
	 * @param portStart
	 * @param deviceTotal
	 * 
	 * */
	public List<Integer> GeneratPortList(int portStart,int deviceTotal){
		System.out.print("当前运行的是"+this.getClass()+"类的----》》》"+this.getClass().getName()+"方法");
		List<Integer> portList = new ArrayList<Integer>();
		while(portList.size() != deviceTotal){
			if(portStart>0 && portStart<6000+portList.size()){
				if(!isPortUsed(portStart)){
					portList.add(portStart);
				}
				portStart++;
			}
		}
		return portList;
	}
	

}
