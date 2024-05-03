package com.tutorials.qa.listeners;



import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.tutorials.qa.utils.ExtentReporter;
import com.tutorials.qa.utils.Utilities;


public class MyListeners implements ITestListener{

	ExtentReports extentReport;
	ExtentTest extentTest;
	
	@Override
	public void onStart(ITestContext context) {
		
		extentReport = ExtentReporter.generateExtentReport();
		
		//System.out.println("Test Case Execution Started");
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		
		extentTest = extentReport.createTest(result.getName());
		extentTest.log(Status.INFO,result.getName()+" started executing");
		
		/*
		 * String testname=result.getName(); System.out.println(testname +
		 * "Started Executing");
		 */
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		
		extentTest.log(Status.PASS,result.getName()+" got successfully executed");
	
		/*
		 * String testname=result.getName(); System.out.println(testname +
		 * "Got Sucessfully Executed");
		 */
		
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
	
	   String testname=result.getName();
       WebDriver driver = null;
		
		try {
			driver = (WebDriver)result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		
		String destinationScreenshotPath = Utilities.captureScreenshot(driver,result.getName());
		extentTest.addScreenCaptureFromPath(destinationScreenshotPath);
		extentTest.log(Status.INFO,result.getThrowable());
		extentTest.log(Status.FAIL,result.getName()+" got failed");
		
		/*
		 * extentTest.addScreenCaptureFromPath(destinationScreenshotPath);
		 * extentTest.log(Status.INFO,result.getThrowable());
		 * extentTest.log(Status.FAIL,result.getName()+" got failed");
		 */
		
		/*
		System.out.println(testname + "Got Failed");
		System.out.println(result.getThrowable());
		System.out.println("Screen Shot Taken");*/
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
		
		extentTest.log(Status.INFO,result.getThrowable());
		extentTest.log(Status.SKIP, result.getName()+" got skipped");
		
		
		/*
		 * String testname=result.getName();; System.out.println(testname +
		 * "Got Skipped"); System.out.println(result.getThrowable());
		 */
	}
	

	@Override
	public void onFinish(ITestContext context) {
		
        extentReport.flush();
		
		String pathOfExtentReport = System.getProperty("user.dir")+"\\test-output\\ExtentReports\\extentReport.html";
		File extentReport = new File(pathOfExtentReport);
		
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
		
	 //System.out.println("Execution Got Completed Sucessfully!.."); 
		
	}







