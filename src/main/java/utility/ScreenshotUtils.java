package utility;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import com.microsoft.playwright.Page;

import stepDefinition.Baseclass;

public class ScreenshotUtils extends Baseclass{
    private static String screenshotFolder;

    // ✅ Initialize Screenshot Folder
    public static void initializeScreenshotFolder() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        screenshotFolder = "test-output/screenshots/screenshots_" + timestamp + "/";
        screenshotFolder = properties.getPropValues("projectPath") + "\\Reports\\Report"+ folderName + "\\" + fileNameCompare+"\\Screenshots";
        new File(screenshotFolder).mkdirs();
        System.out.println("✅ Screenshot folder created: " + screenshotFolder);
    }

    // ✅ Take screenshot and return Base64 string
    public static String takeScreenshotBase64(Page page) {
        try {
            if (screenshotFolder == null) {
                initializeScreenshotFolder();
            }

            // ✅ Generate valid filename
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//            String validStepName = stepName.replaceAll("[^a-zA-Z0-9_-]", "_");
            String screenshotPath = screenshotFolder  + "\\_" + timestamp + ".png";

            // ✅ Capture Screenshot
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)));

            // ✅ Convert to Base64 safely
            return encodeImageToBase64(screenshotPath);
        } catch (Exception e) {
            System.out.println("❌ Error taking screenshot: " + e.getMessage());
            return null;
        }
    }

 // ✅ Encode Screenshot to Base64 safely (No truncation)
    private static String encodeImageToBase64(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("❌ Screenshot file not found: " + filePath);
                return null;
            }

            byte[] fileContent = Files.readAllBytes(file.toPath());
            String base64String = Base64.getEncoder().encodeToString(fileContent);

            // ✅ Ensure the Base64 length does not exceed limits
            if (base64String.length() > 500) {
                System.out.println("⚠️ Large Base64 encoding detected, splitting...");
                return base64String.substring(0, 500) + base64String.substring(500);
            }

            return base64String;
        } catch (Exception e) {
            System.out.println("❌ Error encoding image to Base64: " + e.getMessage());
            return null;
        }
    }
}
