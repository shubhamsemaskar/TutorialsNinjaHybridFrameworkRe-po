package com.tutorials.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.tutorialninja.qa.base.Base;
import com.tutorialninja.qa.utils.Utilties;
import com.tutorialsninja.qa.pages.AccountSuccessPage;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.RegisterPage;

public class RegisterTest extends Base {
	
	RegisterPage registerPage;
	AccountSuccessPage accountSuccessPage;
	
	public RegisterTest() {
		super();
	}

	public WebDriver driver;

	@BeforeMethod
	public void setup() {
		driver = intializeBrowserAndOpenApplicationURL((prop.getProperty("browserName")));
		HomePage homePage=new HomePage(driver);
		registerPage = homePage.navigateToRegisterPage();

	}

	@AfterMethod
	public void tearDown() 
	{
		driver.quit();
	}

	@Test(priority = 1)
	public void verifyRegisteringAnAccountWithMandatoryFields() {

	accountSuccessPage=registerPage.registerWithMandatoryFields(dataProp.getProperty("firstname"), dataProp.getProperty("lastname"), Utilties.generateEmailWithTimeStamp(), dataProp.getProperty("telephoneNumber"), prop.getProperty("validPassword"));
		
		Assert.assertEquals(accountSuccessPage.retrieveAccountSuccessPageHeading(),dataProp.getProperty("accountSuccessfullyCreatedHeading"));
			

	}

	@Test(priority = 2)
	public void verifyRegisteringAnAccountByProvidingAllFields() {
		accountSuccessPage=registerPage.registerWithAllFields(dataProp.getProperty("firstname"), dataProp.getProperty("lastname"), Utilties.generateEmailWithTimeStamp(), dataProp.getProperty("telephoneNumber"), prop.getProperty("validPassword"));
		
		Assert.assertEquals(accountSuccessPage.retrieveAccountSuccessPageHeading(),dataProp.getProperty("accountSuccessfullyCreatedHeading"));


	}

	@Test(priority = 3)
	public void verifyRegisteringAccountWithExistingEmailAddress() {
		
		registerPage.registerWithAllFields(dataProp.getProperty("firstName"),dataProp.getProperty("lastName"),prop.getProperty("validEmail"),dataProp.getProperty("telephoneNumber"),prop.getProperty("validPassword"));
		Assert.assertTrue(registerPage.retrieveDuplicateEmailAddressWarning().contains(dataProp.getProperty("duplicateEmailWarning")),"Warning message regaring duplicate email address is not displayed");

	}

	@Test(priority = 4)
	public void verifyRegisteringAccountWithoutFillingAnyDetails() {
		
		
		registerPage.clickOnContinueButton();
		Assert.assertTrue(registerPage.displayStatusOfWarningMessages(dataProp.getProperty("privacyPolicyWarning"),dataProp.getProperty("firstNameWarning"),dataProp.getProperty("lastNameWarning"),dataProp.getProperty("emailWarning"),dataProp.getProperty("telephoneWarning"),dataProp.getProperty("passwordWarning")));
				
		
	}
}
