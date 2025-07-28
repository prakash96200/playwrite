package testRunner;

import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.SelectOption;

public class Group {

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
				page.locator("//button[text()=' + Add Group ']").click();
				page.locator("//img[@alt='Camera Icon']").setInputFiles(Paths.get("C:\\Users\\ithangavel\\Downloads\\logo.jpg"));
				page.locator("//*[@id='group-name']").fill("Test Group_1807");
				page.locator("//*[@id='license']").fill("Propel Insurance");                                                                                                                                                         
				page.locator("//*[text()=' Propel Insurance ']").click();
				page.locator("//*[@id='tax-id']").fill("123456789");
				page.locator("//*[@id='sic']").fill("1234");
				page.locator("//*[@id='client-id']").fill("AFDGHUYU345566787");
				page.locator("select#gtpa").selectOption(new SelectOption().setLabel("Flores"));

				//page.locator("//select[@id='gtpa']").click();
				//page.locator("//select[@id='gtpa']/option[text()='Flores']").click();
							
				
				
				//browser.close();
	}

}
