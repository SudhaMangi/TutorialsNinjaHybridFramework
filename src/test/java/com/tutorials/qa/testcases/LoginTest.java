package com.tutorials.qa.testcases;





import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.tutorials.qa.base.Base;
import com.tutorials.qa.pages.AccountPage;
import com.tutorials.qa.pages.HomePage;
import com.tutorials.qa.pages.LoginPage;
import com.tutorials.qa.utils.Utilities;

public class LoginTest extends Base {
	
	
	
	public LoginTest(){
		super();
	}

	
	public WebDriver driver;
	LoginPage loginpage;
	
    @BeforeMethod
	public void setup(){
    	
    	driver=initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
    	
    	HomePage homepage=new HomePage(driver);
    	homepage.clickOnMyAccount();
    	loginpage=homepage.selectLoginOption();
    	}
	
	
	
	@Test(priority=1,dataProvider ="validCredentialsSupplier")
	public void verifyLoginWithValidCredentials(String email,String Password) {
		
		AccountPage accountpage = loginpage.login(email, Password);
		Assert.assertTrue
		  (accountpage.getDisplayStatusOfEditYourAccountInformationOption(),"Edit your account information option Not Diplayed");
	}
	
	//Data Driven reading data from xl sheet 
	
	@DataProvider(name="validCredentialsSupplier")
	public Object[][] supplyTestData() {
		
		Object[][] data = Utilities.getTestDataFromExcel("Login");
		return data;
	}
	
	
	/*
	 * //Data Driven Hard Coded Which reading data from below
	 * 
	 * @DataProvider(name="validCredentialsSupplier") public Object[][]
	 * supplyTestData1() {
	 * 
	 * Object[][] data = {{"mgsudha09@outlook.com","12345"},
	 * {"mgsudha09@outlook.com","12345"}, {"mgsudha09@outlook.com","12345"} };
	 * return data; }
	 */
	

	@Test(priority=2)
	public void verifyLoginWithInvalidCredentials() {
	 	
	    loginpage.login(Utilities.generateEmailWithTimeStamp(), dataProp.getProperty("invalidPassword"));
		
		String ActualWarningMessage = loginpage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String ExpectedWarningMessage= dataProp.getProperty("emailPasswordNoMatchWarning");
		Assert.assertTrue(ActualWarningMessage.contains(ExpectedWarningMessage),"Expected Warning Message is not Displayed");
		
		
		
	}
	
	@Test(priority=3)
	public void verifyLoginWithInvalidEmailAndValidPassword() {
		
		loginpage.login(Utilities.generateEmailWithTimeStamp(), prop.getProperty("validPassword"));
		
		String ActualWarningMessage = loginpage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String ExpectedWarningMessage= dataProp.getProperty("emailPasswordNoMatchWarning");
		Assert.assertTrue(ActualWarningMessage.contains(ExpectedWarningMessage),"Expected Warning Message is not Displayed");
		
		
		
		
	}
	
	
	@Test(priority=4)
	public void verifyLoginWithValidEmailAndInvalidPassword() {

		loginpage.login(prop.getProperty("validEmail"), dataProp.getProperty("invalidPassword"));
		String ActualWarningMessage = loginpage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String ExpectedWarningMessage= dataProp.getProperty("emailPasswordNoMatchWarning");
		Assert.assertTrue(ActualWarningMessage.contains(ExpectedWarningMessage),"Expected Warning Message is not Displayed");
		
		
		
	}
	
	@Test(priority=5)
	public void verifyLoginWithoutProvidingCredentials() {
		
		
		loginpage.clickOnLoginButton();
		String ActualWarningMessage = loginpage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String ExpectedWarningMessage= dataProp.getProperty("emailPasswordNoMatchWarning");
		Assert.assertTrue(ActualWarningMessage.contains(ExpectedWarningMessage),"Expected Warning Message is not Displayed");
		
		
	}
	
	
	
	
	  @AfterMethod public void tearDown() {
	  
	  driver.quit();
	  
	  }
	 
}
