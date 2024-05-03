package com.tutorials.qa.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.tutorials.qa.base.Base;
import com.tutorials.qa.pages.HomePage;
import com.tutorials.qa.pages.SearchPage;

public class SearchTest extends Base {

	
	
	public SearchTest(){
		
		super();
	}
	
public WebDriver driver;
SearchPage searchpage;
	
    @BeforeMethod
	public void setup() {
    	
    	driver=initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
    	
		
		
	}

	@Test(priority =1)
	public void verifySearchWithValidProduct() {
		
		HomePage homepage= new HomePage(driver);
		homepage.enterProductIntoSearchBoxField(dataProp.getProperty("validProduct"));
		searchpage= homepage.clickOnSearchButton();
		Assert.assertTrue(searchpage.displayStatusOfHPValidProduct(),"Valid Product Hp is not Displayed");
		
	}
	
	@Test(priority=2)
	public void verifySearchWithInvalidProduct() {
		HomePage homepage= new HomePage(driver);
		homepage.enterProductIntoSearchBoxField(dataProp.getProperty("invalidProduct"));
		searchpage=homepage.clickOnSearchButton();
		
		String actualsearchmsg= searchpage.retrieveNoProductMessageText();
		Assert.assertEquals(actualsearchmsg,"abcd There is no product that matches the search criteria."
		  ,"No product Message Not Displayed" );
		 
		
		
	}
	
	
	@Test(priority=3,dependsOnMethods= {"verifySearchWithInvalidProduct"})
	void verifySearchWithoutAnyProduct() {
		
		HomePage homepage= new HomePage(driver);
		searchpage=homepage.clickOnSearchButton();
		String actualsearchmsg= searchpage.retrieveNoProductMessageText();
		Assert.assertEquals(actualsearchmsg,"There is no product that matches the search criteria."
		  ,"No product Message Not Displayed" );
		
	}
		
		
	
	  @AfterMethod public void tearDown() {
	  
	  driver.quit();
	  
	  }
	 
		 
		
		
	
	
}
