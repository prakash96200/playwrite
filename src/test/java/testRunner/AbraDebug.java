package testRunner;
import java.nio.file.Paths;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

public class AbraDebug {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            
            System.out.println("Navigating to login page...");
            // Navigate to the login page
            page.navigate("https://dev-abra-apim.azure-api.net/abra-ui/v1/login");
            
            // Wait for page to load and take screenshot
            page.waitForLoadState(LoadState.DOMCONTENTLOADED);
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("login_page.png")));
            System.out.println("Login page loaded. Screenshot saved as login_page.png");
            
            // Check if username field exists
            if (page.locator("#username").count() > 0) {
                System.out.println("Username field found. Filling username...");
                page.locator("#username").fill("test_admin_super");
            } else {
                System.out.println("Username field not found!");
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("no_username_field.png")));
            }
            
            // Check if password field exists
            if (page.locator("#password").count() > 0) {
                System.out.println("Password field found. Filling password...");
                page.locator("#password").fill("AbraSA@12");
            } else {
                System.out.println("Password field not found!");
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("no_password_field.png")));
            }
            
            // Check if login button exists
            if (page.locator("button.primary-button.login-button").count() > 0) {
                System.out.println("Login button found. Clicking...");
                page.locator("button.primary-button.login-button").click();
            } else {
                System.out.println("Login button not found! Trying alternative selectors...");
                if (page.locator("button[type='submit']").count() > 0) {
                    System.out.println("Found submit button. Clicking...");
                    page.locator("button[type='submit']").click();
                } else if (page.locator("input[type='submit']").count() > 0) {
                    System.out.println("Found submit input. Clicking...");
                    page.locator("input[type='submit']").click();
                } else {
                    System.out.println("No login button found!");
                    page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("no_login_button.png")));
                }
            }
            
            System.out.println("Waiting for page after login attempt...");
            
            // Wait with a shorter timeout and more specific conditions
            try {
                page.waitForLoadState(LoadState.NETWORKIDLE, new Page.WaitForLoadStateOptions().setTimeout(10000));
                System.out.println("Page load completed.");
            } catch (Exception e) {
                System.out.println("Page load timeout, but continuing...");
            }
            
            // Take screenshot after login attempt
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("after_login.png")));
            System.out.println("Screenshot after login saved as after_login.png");
            
            // Check current URL
            String currentUrl = page.url();
            System.out.println("Current URL: " + currentUrl);
            
            // Check for various success indicators
            boolean loginSuccess = false;
            
            if (page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Dashboard")).count() > 0) {
                System.out.println("Dashboard heading found - Login successful!");
                loginSuccess = true;
            } else if (currentUrl.contains("dashboard")) {
                System.out.println("URL contains 'dashboard' - Login successful!");
                loginSuccess = true;
            } else if (!currentUrl.contains("login")) {
                System.out.println("Redirected away from login page - Login likely successful!");
                loginSuccess = true;
            } else {
                System.out.println("Still on login page - Login may have failed");
                
                // Check for error messages
                if (page.locator(".error, .alert, .warning, [class*='error'], [class*='alert']").count() > 0) {
                    String errorText = page.locator(".error, .alert, .warning, [class*='error'], [class*='alert']").first().textContent();
                    System.out.println("Error message found: " + errorText);
                }
            }
            
            if (loginSuccess) {
                System.out.println("✅ Login test PASSED!");
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("success.png")));
            } else {
                System.out.println("❌ Login test FAILED!");
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("failure.png")));
            }
            
            // Close browser
            browser.close();
        } catch (Exception e) {
            System.err.println("Error during login automation: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
