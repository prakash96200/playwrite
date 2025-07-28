package utility;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportUtils_old {

    private static ExtentReports extentReports;
    private static ExtentTest extentTest;
    private static String reportPath;

    // Initialize ExtentReports
    public static void initializeReport(String testName) {
        if (extentReports == null) {
            // Generate timestamped report file
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            reportPath = "test-output/PlaywrightTestReport_" + timestamp + ".html";

            // Set up ExtentReports
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setDocumentTitle("Playwright Automation Report");
            sparkReporter.config().setReportName("Test Execution Report");
            sparkReporter.config().setTheme(Theme.DARK);

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);

            // ✅ Initialize unique screenshot folder for this test run
//            ScreenshotUtils.initializeScreenshotFolder();
        }
        // Create a new test instance
        extentTest = extentReports.createTest(testName);
    }

    // Log test step as info
    public static void logInfo(String message) {
        if (extentTest != null) {
            extentTest.info(message);
        }
    }

    // Log a passed test step
    public static void logPass(String message) {
        if (extentTest != null) {
            extentTest.pass(message);
        }
    }

    // Log a failed test step
    public static void logFail(String message) {
        if (extentTest != null) {
            extentTest.fail(message);
        }
    }

    // Add screenshot to report
    public static void addScreenshot(String screenshotPath) {
        if (extentTest != null) {
            extentTest.info("Screenshot:").addScreenCaptureFromPath(screenshotPath);
        }
    }

    // Finalize the report
    public static void finalizeReport() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}
