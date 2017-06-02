package com.moji.appium.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;


public class ProUtil {
	private Log logger=Log.getLogger(ProUtil.class);
	private String filePath;
	private Properties prop;
	private static String path=System.getProperty("user.dir");
	/**
	 * 构造方法
	 * 读取配置文件
	 * */
	public ProUtil(String filePath){
		this.filePath = filePath;
		this.prop = readProperties();
		
	}
	/**
	 * 读取配置文件，并处理中文乱码
	 * @return properties
	 * */
	private Properties readProperties() {
		Properties properties = new Properties();
		try {
			InputStream inputStream = new FileInputStream(filePath);
			BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
			properties.load(bf);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
	
	/**
	 * 获取某个key的文本内容
	 * @param key
	 * @return value
	 * */
	public String getPro(String key){
		if(prop.containsKey(key)){
			return prop.getProperty(key);
		}else{
			logger.error("获取的key不存在:"+key);
			return "";
		}
	}
	
	/**
	 * 写内容
	 * @param key
	 * @param value
	 * */
	public void setProp(String key,String value){
        if (prop == null) {
            prop = new Properties();
        }
        try {
            OutputStream outputStream = new FileOutputStream(filePath);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
            prop.setProperty(key, value);
            prop.store(bw,key+" value");
            bw.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }		
	}
	public static void main(String[] args) {
		String filepath = path+"/configs/test.properties";
		System.out.print(filepath);
		ProUtil p=new ProUtil(filepath);
		System.out.println(p.getPro("password"));
		p.setProp("username", "mushishi");
		System.out.println(p.getPro("username"));
		//System.out.println(p.getPro("username1"));
		p=new ProUtil("configs/message.properties");
		System.out.println(p.getPro("A").equals("0"));
		while(p.getPro("A").equals("0")){
			System.out.println("xxx");
		}
	}
	
	

}
