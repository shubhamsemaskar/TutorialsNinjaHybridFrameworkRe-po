package com.tutorials.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.tutorialninja.qa.base.Base;
import com.tutorialninja.qa.utils.Utilties;
import com.tutorialsninja.qa.pages.AccountPage;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.LoginPage;

public class LoginTest extends Base {
	LoginPage loginPage;
	
	public LoginTest() {
		super();
	}

	public WebDriver driver;

	@BeforeMethod
	public void setup() {

		driver = intializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
		HomePage homePage=new HomePage(driver);
		loginPage= homePage.navigateToLoginPage();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(priority=1,dataProvider="validCredentialsSupplier")
	public void verifyLoginWithValidCredentials(String email,String password) {


		AccountPage accountPage = loginPage.login(email, password);
		Assert.assertTrue(accountPage.getDisplayStatusOfEditYourAccountInformationOption(),"Edit Your Account Information option is not displayed");
	}

	@DataProvider(name="validCredentialsSupplier")
	public Object[][] supplyTestData() {
		
		Object[][] data = Utilties.getTestDataFromExcel("Login");
		return data;
	}
	
	
	@Test(priority = 2)
	public void verifyloginwithinvalidcred() {

		loginPage.login(Utilties.generateEmailWithTimeStamp(), dataProp.getProperty("invalidPassword"));
		
		Assert.assertTrue(loginPage.retrieveEmailPasswordNotMatchingWarningMessageText().contains(dataProp.getProperty("emailPasswordNoMatchWarning")));
			

	}

	@Test(priority = 3)
	public void verifyloginwithinvalidEmailandValidPassword() {
		loginPage.login(Utilties.generateEmailWithTimeStamp(),prop.getProperty("validPassword"));
		Assert.assertTrue(loginPage.retrieveEmailPasswordNotMatchingWarningMessageText().contains(dataProp.getProperty("emailPasswordNoMatchWarning")),"Expected Warning message is not displayed");
		

	}

	@Test(priority = 4)
	public void verifyloginwithValidEmailandInValidPassword() {
		
		loginPage.login(prop.getProperty("validEmail"),dataProp.getProperty("invalidPassword"));		
		Assert.assertTrue(loginPage.retrieveEmailPasswordNotMatchingWarningMessageText().contains(dataProp.getProperty("emailPasswordNoMatchWarning")),"Expected Warning message is not displayed");
	}

	@Test(priority = 5)
	public void verifyloginwithoutprovidingcredentials() {

		
		loginPage.clickOnLoginButton();
String acutalWarningMessage =loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
Assert.assertTrue(loginPage.retrieveEmailPasswordNotMatchingWarningMessageText().contains(dataProp.getProperty("emailPasswordNoMatchWarning")));
		

	}
}