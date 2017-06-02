package com.moji.appium.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class FileUtil {
	private static Log logger=Log.getLogger(FileUtil.class);
	//文件路径
	private static String path=System.getProperty("user.dir");
	private static String filenameTemp;
	/**
	 * 创建文件
	 * @param filename //文件名
	 * @param extension //扩展名
	 * @return true、 false是否创建成功
	 * */
	public static boolean createFile(String filename,String extension){
		boolean flag = false;
		filenameTemp = path+"\\configs\\"+filename+"."+extension;
		File file = new File(filenameTemp);
		if(!file.exists()){
			try {
				file.createNewFile();
				flag = true;
				logger.info("创建文件成功:"+filenameTemp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	/**
	 * 向文件夹中写入数据
	 * @param filename 文件名称(文件路径+文件名称)
	 * @param data 写入的数据
	 * @throws IOException 
	 * 
	 * */
	public static void writeFileContent(String filename,String data) throws IOException{
        String filein = data+"\r\n";//新写入的行，换行
        String temp  = "";
        String filepath = path+filename;
        
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos  = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);//文件路径(包括文件名称)
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();
            
            //文件原有内容
            for(int i=0;(temp =br.readLine())!=null;i++){
                buffer.append(temp);
                // 行与行之间的分隔符 相当于“\n”
                buffer = buffer.append(System.getProperty("line.separator"));
            }
            buffer.append(filein);
            
            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }    
	}
	
	
	
	public static void main(String args[]){
		createFile("test","json");
	}
}
