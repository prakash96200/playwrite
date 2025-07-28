package testRunner;
import java.nio.file.Paths;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

public class Abra {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            
            // Navigate to the login page
            page.navigate("https://dev-abra-apim.azure-api.net/abra-ui/v1/login");
            
            // Fill in username
            page.locator("#username").fill("test_admin_super");
            
            // Fill in password (replace "your_password" with actual password)
            page.locator("#password").fill("AbraSA@12");
            
            // Click the login button
            page.locator("button.primary-button.login-button").click();
            
            // Wait for navigation to complete
            page.waitForLoadState(LoadState.NETWORKIDLE);
            
            // Verify login success - adjust this selector based on your application
            if (page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Dashboard")).isVisible() 
                || page.url().contains("dashboard")) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Login may have failed");
                // Take screenshot for debugging
                page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("login_failure.png")));
            }
            
            // Close browser
            browser.close();
        } catch (Exception e) {
            System.err.println("Error during login automation: " + e.getMessage());
            e.printStackTrace();
        }
    }
}