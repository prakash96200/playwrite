package testRunner;



import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

public class Abralogin {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
		BrowserContext context = browser.newContext();
		
		Page page = context.newPage();
		page.navigate("https://dev-abra-apim.azure-api.net/abra-ui/v1/login");
		page.locator("//input[@id='username']").fill("test_admin_super");
	
		page.locator("//input[@id='password']").fill("AbraSA@12");
		page.locator("button.primary-button.login-button").click();
		page.waitForTimeout(5000);
		
		System.out.println("Login successful!");						
      
		//To select user management
		page.waitForTimeout(5000);
		page.locator("img[src='assets/images/hamburger.png']").click();
		page.waitForTimeout(5000);
		page.locator("//li[@ng-reflect-router-link='/app/su/users']").nth(1).click();
		
		page.locator("//button[text()='Broker Licenses']").click();
		page.waitForTimeout(1000);
		
		page.locator("//button[text()=' Create New ']").click();
		page.waitForTimeout(1000);
		String headingText = page.locator("//h1[text()='Add Broker License']").textContent();
		System.out.println("Heading Text: " + headingText);
		
		page.getByPlaceholder("Enter name here...").fill("Test1");
		page.locator("//button[text()='Add']").click();
		
		// Wait for the success message to appear
		Locator successMessage = page.locator("//h2[text()='Broker License added successfully']");
		successMessage.waitFor(new Locator.WaitForOptions().setTimeout(5000));

		// Capture and print the message
		String messageText = successMessage.textContent();
		System.out.println("✅ Success Message: " + messageText);

		successMessage.screenshot(new Locator.ScreenshotOptions().setPath(Paths.get("success-toast.png")));

		page.waitForTimeout(5000);
		page.getByPlaceholder("Search by name...").fill("Test1");
		page.waitForTimeout(5000);
		page.getByPlaceholder("Search by name...").clear();
		page.waitForTimeout(500);
		page.locator("//span[contains(@class, 'close-button')]").click();
	
		//page.locator("//button[contains(@class, 'delete-button')]").click();
		//page.locator("//button[text() = 'Confirm']").click();
		//page.waitForTimeout(500);
		
		//String warningText = page.locator("//h2[contains(text(), 'Broker License cannot be deleted')]").textContent();
		
		//System.out.println("Alert Message: " + warningText);
		
		//page.locator("//button[text() = 'Okay']").click();
		
  
		browser.close();
		

		
	}

}
