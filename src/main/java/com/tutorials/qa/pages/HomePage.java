package com.tutorials.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	
	WebDriver driver;
	
      public HomePage(WebDriver driver) 
      {	
		this.driver = driver;
		PageFactory.initElements(driver,this);
	  }
	
	
	    //Objects
	
		@FindBy(xpath="//span[text()='My Account']")
		private WebElement myAccountDropMenu;
		
		@FindBy(linkText="Login")
		private WebElement loginOption;
		
		@FindBy(linkText= "Register")
		private WebElement RegisterOption;
		
		@FindBy(name="search")
		private WebElement searchBoxField;
		
		@FindBy(xpath="//div[@id='search']/descendant::button")
		private WebElement searchButton;
		
		//Actions
		
		public void clickOnMyAccount() {
			
			myAccountDropMenu.click();
		 }
		
	   public LoginPage  selectLoginOption() {
			
			loginOption.click();
			return new LoginPage(driver);
		 }

	   public RegisterPage SelectRegisterOtion() {
		   
		   RegisterOption.click();
		   return new RegisterPage(driver);
		   
	   }
	   
	   
	   public SearchPage clickOnSearchButton() {
			
			searchButton.click();
			return new SearchPage(driver);
			
		}
		
		public SearchPage searchForAProduct(String productText) {
			
			searchBoxField.sendKeys(productText);
			searchButton.click();
			return new SearchPage(driver);
			
		}
		
		public void enterProductIntoSearchBoxField(String productText) {
			
			searchBoxField.sendKeys(productText);
			
		}
	   
	   
	   
	   
	   
}
