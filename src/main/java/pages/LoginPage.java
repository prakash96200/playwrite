package pages;

import java.io.IOException;

import com.aventstack.extentreports.Status;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import stepDefinition.Baseclass;

import utility.DriverClass;
import utility.ReportUtils;
import utility.ResultClass;

public class LoginPage extends Baseclass {
	
	
	static FrameLocator iframe;
	
		public static void launchUrl() {
		String url;
		try {
			url = objGM.dataValuereading("login", Integer.parseInt(objGM.testCaseValueReading("login").split(",")[0]), al_login.indexOf("envdev"));
			Baseclass.page.navigate(url);
		} catch (NumberFormatException | IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public static void enterCredentials() throws Throwable {
		

		try {
		DriverClass.objIdentification(page, dictObj_or, al_login, Integer.parseInt(objGM.testCaseValueReading("login").split(",")[0]), "userName",ResultClass.exTest);
		Thread.sleep(1000);
		//DriverClass.objIdentification(page, dictObj_or, al_login, Integer.parseInt(objGM.testCaseValueReading("login").split(",")[0]), "clickNext",ResultClass.exTest);
		//Thread.sleep(1000);
		DriverClass.objIdentification(page, dictObj_or, al_login, Integer.parseInt(objGM.testCaseValueReading("login").split(",")[0]), "password",ResultClass.exTest);
		Thread.sleep(3000);
		DriverClass.objIdentification(page, dictObj_or, al_login, Integer.parseInt(objGM.testCaseValueReading("login").split(",")[0]), "clickLogin",ResultClass.exTest);
		Thread.sleep(3000);
		resultClass.logPass(ResultClass.exTest, "Logined successfully",page);
		}
		catch(Exception e) {
			resultClass.logFail(ResultClass.exTest, "Logined failed:"+e.getStackTrace(),page);
		}
	}}
	

	

