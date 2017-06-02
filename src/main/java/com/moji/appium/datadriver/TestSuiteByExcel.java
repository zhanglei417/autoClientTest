package com.moji.appium.datadriver;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.moji.appium.testcases.CaseBaseTest;
import com.moji.appium.util.Log;

public class TestSuiteByExcel {
    public Method method[];
	public String keyword;
	public String locatorExpression;
	public String value;
	public KeyWordsAction keyWordsaction;
	public int testStep;
	public int testLastStep;
	public String testCaseID;
	public String testCaseRunFlag;
	public boolean testResult;
	private Log logger=Log.getLogger(this.getClass());

	@Test
	public void testTestSuite() throws Exception {
		
		logger.info("当前运行的是"+this.getClass()+"类的----》》》testTestSuite方法");
		// 声明一个关键动作类的实例
		
		// 使用 java 的反射机制获取 KeyWordsaction 类的所有方法对象
		method = keyWordsaction.getClass().getMethods();
		logger.info("@@@@@@@@@"+method.length);
		// 定义 excel 关键文件的路径
		String excelFilePath = Constants.Path_ExcelFile;
		// 设定读取 excel 
		ExcelUtil.setExcelFile(excelFilePath);
		// 读取“测试用例集合” sheet 中的测试用例总数，就是用例那个sheet那个总行数，需要减去第一行
		int testCasesCount = ExcelUtil.getRowCount(Constants.Sheet_TestSuite);
		// 使用 for 循环，执行所有标记为“y”的测试用例
		for (int testCaseNo = 1; testCaseNo <= testCasesCount; testCaseNo++) {
			/***
			 *  读取“测试用例集合” sheet 中每行的测试用例序号，读取用例 中的 用例编号
			 *  Sheet_TestSuite 用例集合名字
			 *  testCaseNo sheet 第几行
			 *  Col_TestCaseID 0列，用例编号
			 */
			System.out.println("2222222");
			testCaseID = ExcelUtil.getCellData(Constants.Sheet_TestSuite,testCaseNo, Constants.Col_TestCaseID);
			System.out.println(testCaseID);
			/**
			 * *读取“测试用例集合” sheet 中每行的是否运行列中的值
			 * Constants.Col_RunFlag 判断是否执行
			 * testCaseRunFlag  是否执行  y\n
			 */
			testCaseRunFlag = ExcelUtil.getCellData(Constants.Sheet_TestSuite,testCaseNo, Constants.Col_RunFlag);
			// 如果是否运行列中的值为“y”,则执行测试用例中的所有步骤
			if (testCaseRunFlag.equalsIgnoreCase("y")) {

				// 设定测试用例的当前结果为true，即表明测试执行成功
				testResult = true;
				// 在“知乎”sheet 中，获取当前要执行测试用例的第一个步骤所在行行号
				testStep = ExcelUtil.getFirstRowContainsTestCaseID(Constants.Sheet_TestSteps, testCaseID,Constants.Col_TestCaseID);
				
				//在“知乎”sheet 中，获取当前要执行测试用例的最后一个步骤所在行行号
				testLastStep = ExcelUtil.getTestCaseLastStepRow(Constants.Sheet_TestSteps, testCaseID, testStep);
				System.out.println(testStep);
				System.out.println(testLastStep);
				// 遍历测试用例中的所有测试步骤
				for (; testStep < testLastStep; testStep++) {
			// 从“用例” sheet 中读取关键字和操作值，并调用 execute_Actions 方法执行
					keyword = ExcelUtil.getCellData(Constants.Sheet_TestSteps,testStep, Constants.Col_KeyWordAction);
					// 在日志文件中打印关键字信息
					System.out.println("从excel 文件读取到的关键字是：" + keyword);
					
						//获取定位元素，如果定位元素
					locatorExpression=ExcelUtil.getCellData(Constants.Sheet_TestSteps,testStep,Constants.Col_LocatorExpression);
					System.out.println("读取到的定位元素是："+locatorExpression);
					/**
					 * 判断操作值中是否有元素，如果没元素，只执行操作
					 * */
					if(!keyword.equals("click")){
						value = ExcelUtil.getCellData(Constants.Sheet_TestSteps,testStep, Constants.Col_ActionValue);
						System.out.println("从 excel 文件中读取的操作值是：" + value);
					}
					
					// 在日志文件中打印操作值信息
					
					execute_Actions();
					if (testResult == false) {
						/*
						 * 如果测试用例的任何一个测试步骤执行失败，则测试用例集合 sheet 
                             * 中的当前执行测试用例的执行结果设定为“测试执行失败”
						 */
						ExcelUtil.setCellData("测试用例集合", testCaseNo,
						Constants.Col_TestSuiteTestResult, "测试执行失败");
						// 调用测试方法过程中，若出现异常，则将测试设定为失败状态，停止测试用例执行
						Assert.fail("执行出现异常，测试用例执行失败！");

						/*
						 * 当前测试用例出现执行失败的步骤，则整个测试用例设定为失败状态，
						 *  break语句跳出当前的 for 循环，继续执行测试集合中的下一个测试
                             * 用例
						 */
						break;
					}

				}

				if (testResult == true) {
					/*
					 * 如果测试用例的所有步骤执行成功，则会在测试用例集合 sheet 中的当						 * 前执行测试用例
					 * 的执行结果设定为“测试执行成功”
					 */
					ExcelUtil.setCellData(Constants.Sheet_TestSuite, testCaseNo,
					Constants.Col_TestSuiteTestResult, "测试执行成功");
				}

			}
		}
	}

	private void execute_Actions() {
		//KeyWordsAction keyWordsaction = new KeyWordsAction();
		System.out.print("当前运行的是TestSuiteByExcel类的----》》》execute_Actions方法");
		try {

			for (int i = 0; i < method.length; i++) {
				/*
				 * 使用反射的方式，找到关键字对应的测试方法，并使用 value （操作值） 作为测          
                  	 * 试方法的函数值进行调用
			  	 */

				if (method[i].getName().equals(keyword)) {
					method[i].invoke(keyWordsaction, locatorExpression,value);
					if (testResult == true) {
						/* 当前测试步骤执行成功，在 “知乎”sheet 中，会将当前执行的测
                             *  试步骤结果设定为“测试步骤执行成功”
					      */
						ExcelUtil.setCellData(Constants.Sheet_TestSteps,testStep, Constants.Col_TestStepTestResult,"测试步骤执行成功");
						break;
					} else {
						/* 当前测试步骤执行失败，在 “知乎”sheet 中，会将当前执行的测
                           *  试步骤结果设定为“测试步骤执行失败”
						 */
						ExcelUtil.setCellData(Constants.Sheet_TestSteps,
								testStep, Constants.Col_TestStepTestResult,"测试步骤执行失败");

						break;
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@BeforeClass
	@Parameters({ "udid", "port" })
	public void BeforeClass(String udid,String port) throws Exception {
		logger.error("当前运行的是"+this.getClass()+"类的----》》》BeforeClass方法");
		System.out.println("连接testsuitebyexcel的"+udid+"端口"+port);
		//String udid = "DU2TAN1492003864";
		//String  port = "4490";
		CaseBaseTest cb=new CaseBaseTest();
		keyWordsaction = new KeyWordsAction();
		keyWordsaction.driver=cb.driverInit(udid, port);
		keyWordsaction.driver.implicitlyWait(20);
		System.out.println("~~~~~~~~~~~~~~~");
		
	}
	@AfterClass
	public void afterClass(){
		logger.info("当前运行的是"+this.getClass()+"类的----》》》"+this.getClass().getName()+"方法");
		keyWordsaction.quit();
	}
}
