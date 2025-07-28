package pages;

import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import stepDefinition.Baseclass;
import utility.DriverClass;

import utility.ResultClass;

public class Superuser extends Baseclass{

	//static Locator btnFNOL = Baseclass.page
	//		.locator("//a[@id='ctl00_ctl00_MasterPageContent_ucHeaderMenu_ctl01']");	
	//static String frame = "//frame[@id='mainContentIFrame']";
	//static String frame2="//frame[@name='wndPolicyDetails']";

	static FrameLocator iframe;

	public static void createSuperuser() throws Exception {
		Thread.sleep(5000);

		//Baseclass.page2 = Baseclass.context.waitForPage(() -> {
			//Baseclass.browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
			//Baseclass.page.frameLocator(frame).locator(btnFNOL).click();});
		
		Thread.sleep(5000);

		//System.out.println("title " + Baseclass.page2.title());

		//***Switching into frame
		 //iframe = Baseclass.page2.frameLocator(frame);
		
		try {
			System.out.println("Reading from sheet: " + objGM.getCurrentSheet());
			System.out.println("Row value: " + objGM.testCaseValueReading("createuser"));

			
			DriverClass.objIdentification(page, dictObj_or, al_createuser, Integer.parseInt(objGM.testCaseValueReading("createuser").split(",")[0]), "clickButton",ResultClass.exTest);
			//page.locator("img[src='assets/images/hamburger.png']").click();
			Thread.sleep(3000);
			DriverClass.objIdentification(page, dictObj_or, al_createuser, Integer.parseInt(objGM.testCaseValueReading("createuser").split(",")[0]), "clickUsermanagement",ResultClass.exTest);
			//page.locator("//li[@ng-reflect-router-link='/app/su/users']").nth(1).click();
			Thread.sleep(3000);
			
			Thread.sleep(3000);
			DriverClass.objIdentification(page2,dictObj_or, al_createuser, Integer.parseInt(objGM.testCaseValueReading("createuser").split(",")[0]), "clickAdd1",ResultClass.exTest);
			
			//DriverClass.objIdentification(page2, dictObj_or, al_createuser, Integer.parseInt(objGM.testCaseValueReading("createuser").split(",")[0]), "clickAdd1",ResultClass.exTest);
			//page.locator("(//button[normalize-space(text())='Add'])[2]").click();
			Thread.sleep(2000);
			resultClass.logPass(ResultClass.exTest, "Super user added successfully",page);
			DriverClass.objIdentification(page2, dictObj_or, al_createuser, Integer.parseInt(objGM.testCaseValueReading("createuser").split(",")[0]), "enteremailidsearch",ResultClass.exTest);
			//page.locator("//input[@id='email']").fill("Tt@eal.com");
			Thread.sleep(6000);
			//page.locator("//button[normalize-space(text())='Search']").click();
			//Thread.sleep(3000);
			//page.locator("//button[contains(@class, 'edit-button')]").click();
			//page.locator("//label[@for='inactiveStatus']").click();
			//page.locator("//button[normalize-space(text())='Update']").click();
			//resultClass.logPass(ResultClass.exTest, "Superuser status is change to inactive",page);
			
			
			DriverClass.objIdentification(page2, dictObj_or, al_createuser, Integer.parseInt(objGM.testCaseValueReading("createuser").split(",")[0]), "Clicksearch",ResultClass.exTest);
			//Thread.sleep(3000);
			DriverClass.objIdentification(page2, dictObj_or, al_createuser, Integer.parseInt(objGM.testCaseValueReading("createuser").split(",")[0]), "Clickedit",ResultClass.exTest);
			//Thread.sleep(2000);
			DriverClass.objIdentification(page2, dictObj_or, al_createuser, Integer.parseInt(objGM.testCaseValueReading("createuser").split(",")[0]), "Clickinactive",ResultClass.exTest);
			DriverClass.objIdentification(page2, dictObj_or, al_createuser, Integer.parseInt(objGM.testCaseValueReading("createuser").split(",")[0]), "ClickUpdate",ResultClass.exTest);
			
			
			
		} catch (Throwable e) {
			resultClass.logFail(ResultClass.exTest, "Superuser status is change to inactive:"+e.getMessage(),page);
			e.printStackTrace();
		}
		page.locator("//button[contains(text(), 'Add')]").click();
		page.locator("//input[@id='broker' and @type='radio' and @value='broker']").click();	
		page.locator("//input[@placeholder='Enter first name here...']").fill("Test1");
		page.locator("//input[@placeholder='Enter last name here...']").fill("Broker");
		page.locator("//input[@placeholder='Enter email id here...']").fill("Tet@email.com");
		page.locator("//input[@placeholder='Enter username here...']").fill("Br0ker090");
		Thread.sleep(2000);
		page.locator("//input[@placeholder='Select a Broker License name...']").fill("Brokers Benefits Test");
		page.locator("xpath=//li[normalize-space(text())='Brokers Benefits Test']").click();
		
		Thread.sleep(2000);
		page.locator("(//button[normalize-space(text())='Add'])[2]").click();
		resultClass.logPass(ResultClass.exTest, "Broker added successfully",page);
		
		//page.locator("//input[@placeholder='Select a Broker License name...']").fill("Brokers Benefits Test");
		//page.locator("xpath=//li[normalize-space(text())='Brokers Benefits Test']").click();
       
		
	}
}
