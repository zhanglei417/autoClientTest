package com.moji.appium.util;

import java.io.FileOutputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.moji.appium.server.Port;
import com.moji.appium.server.Servers;

public class xmlFileAction {
	public static void createTestxmlFile() throws Exception{
		Servers servers = new Servers(new Port(new DosCmd()), new DosCmd());
		List<String> deviceList = servers.getDevices();
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("suite");
		document.setRootElement(root);
		root.addAttribute("name","suite");
		root.addAttribute("parallel", "test");
		root.addAttribute("thread-count", String.valueOf(deviceList.size()));
		OutputFormat format = new OutputFormat("    ",true);
		XMLWriter xmlwriter = new XMLWriter(new FileOutputStream("configs/test.xml"),format);
		xmlwriter.write(document);
	}


public static void main(String args[]) throws Exception{
	xmlFileAction.createTestxmlFile();
	}
}
