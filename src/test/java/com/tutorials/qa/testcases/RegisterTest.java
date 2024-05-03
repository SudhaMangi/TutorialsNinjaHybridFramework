package com.tutorials.qa.testcases;



import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.tutorials.qa.base.Base;
import com.tutorials.qa.pages.AccountSuccessPage;
import com.tutorials.qa.pages.HomePage;
import com.tutorials.qa.pages.RegisterPage;
import com.tutorials.qa.utils.Utilities;

public class RegisterTest extends Base {
	
	RegisterPage registerpage;
	AccountSuccessPage accountsuccesspage;
	
	public RegisterTest(){
		
		super();
		
	}
	
    public  WebDriver driver;
	
    @BeforeMethod
	public void setup() {
    	
    	driver=initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
    	HomePage homepage=new HomePage(driver);
    	homepage.clickOnMyAccount();
    	registerpage=homepage.SelectRegisterOtion();
    	
		
	}

    @Test(priority=1)
    public void verifyRegisteringAnAccountWithMandatoryFields() {
    	
    	registerpage.enterFirstName(dataProp.getProperty("firstName"));
    	registerpage.enterLastName(dataProp.getProperty("lastName"));
    	registerpage.enterEmailAddress(Utilities.generateEmailWithTimeStamp());
		registerpage.enterTelephoneNumber(dataProp.getProperty("telephoneNumber"));
        registerpage.enterPassword(prop.getProperty("validPassword"));
    	registerpage.enterConfirmPassword(prop.getProperty("validPassword"));
    	registerpage.selectPrivacyPolicy();
    	
    	accountsuccesspage = registerpage.clickOnContinueButton();
    	
    	String actualSuccessHeading = accountsuccesspage.retrieveAccountSuccessPageHeading() ;
	    Assert.assertEquals(actualSuccessHeading,dataProp.getProperty("accountSuccessfullyCreatedHeading"),"Your Account Has Not Been Created");
    	
    }
    
    @Test(priority=2)
    public void verifyRegisteringAccountByProvidingAllFields() {
    	
    	
    	registerpage.enterFirstName(dataProp.getProperty("firstName"));
    	registerpage.enterLastName(dataProp.getProperty("lastName"));
    	registerpage.enterEmailAddress(Utilities.generateEmailWithTimeStamp());
		registerpage.enterTelephoneNumber(dataProp.getProperty("telephoneNumber"));
        registerpage.enterPassword(prop.getProperty("validPassword"));
    	registerpage.enterConfirmPassword(prop.getProperty("validPassword"));
    	registerpage.selectYesNewsletterOption();
    	registerpage.selectPrivacyPolicy();
    	registerpage.clickOnContinueButton();
    	
        accountsuccesspage = new AccountSuccessPage(driver);
    	
    	String actualSuccessHeading = accountsuccesspage.retrieveAccountSuccessPageHeading();
	    Assert.assertEquals(actualSuccessHeading,dataProp.getProperty("accountSuccessfullyCreatedHeading"),"Your Account Has Not Been Created");
    	 	
    	
    	
    	
    }
    @Test(priority=3)
    public void verifyRegisteringAccountWithExistingEmailAddress(){
    	
    	
    	

    	registerpage.enterFirstName(dataProp.getProperty("firstName"));
    	registerpage.enterLastName(dataProp.getProperty("lastName"));
    	registerpage.enterEmailAddress(prop.getProperty("validEmail"));
    	registerpage.enterTelephoneNumber(dataProp.getProperty("telephoneNumber"));
        registerpage.enterPassword(prop.getProperty("validPassword"));
    	registerpage.enterConfirmPassword(prop.getProperty("validPassword"));
    	registerpage.selectYesNewsletterOption();
    	registerpage.selectPrivacyPolicy();
    	registerpage.clickOnContinueButton();
    	
    	String actualWarning = registerpage.retrieveDuplicateEmailAddressWarning();
	    Assert.assertTrue(actualWarning.contains(dataProp.getProperty("duplicateEmailWarning")),"Warning Message Not Displayed");
    	
    	
    }
    
    
    @Test(priority=4)
    public void verifyRegisteringAccountWithoutFillingAnyDetails() {
    	
    	
    	registerpage.clickOnContinueButton();
        String ActualPrivacyWarning  = registerpage.retrieveDuplicateEmailAddressWarning();
    	Assert.assertTrue(ActualPrivacyWarning.
    		   contains(dataProp.getProperty("privacyPolicyWarning")),"Privacy Warning Message Not Displaying");
    	
    	
    }
    
    
	
	
	
	  @AfterMethod public void tearDown() {
	  
	  driver.quit();
	  
	  }
	 
	 

}
