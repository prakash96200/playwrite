package utility;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.microsoft.playwright.Page;

import stepDefinition.Baseclass;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReportUtils {

    private static ExtentReports extent;
    private static ExtentTest test;

    // ✅ Initialize Extent Report
    public static void initReport() {
        if (extent == null) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportPath = "target/extent-report_" + timeStamp + ".html";

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("Automation Report");
            spark.config().setReportName("Execution Summary");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Tester", "Automated User");
        }
    }

    // ✅ Start test node
    public static void startTest(String testName) {
        test = extent.createTest(testName);
    }

    // ✅ Log step with or without screenshot
    public static void log(Status status, String message) {
        if (test != null) {
            test.log(status, message);
        } else {
            System.out.println("[ExtentLog] " + status + ": " + message);
        }
    }

    // ✅ Log with Screenshot
    public static void logWithScreenshot(Status status, String message, Page page) throws IOException {
        String base64 = captureScreenshotAsBase64(page);
		test.log(status, message,
		        MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
    }

    // ✅ Capture screenshot in base64 format
    private static String captureScreenshotAsBase64(Page page) {
        byte[] buffer = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
        return Base64.getEncoder().encodeToString(buffer);
    }

    // ✅ Flush report
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    // Optional: convert an image file path to Base64 (used if saving file-based screenshots)
    static String encodeImageToBase64(String imagePath) {
        try {
            byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            System.out.println("Error encoding image to Base64: " + e.getMessage());
            return null;
        }
    }
}
