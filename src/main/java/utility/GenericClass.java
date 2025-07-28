package utility;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class GenericClass {

	public static void clickFrameElement(FrameLocator iframe,String element) {
		iframe.locator(element).click();
	}
	
	
}
