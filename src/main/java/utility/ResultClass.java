package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.microsoft.playwright.Page;

import stepDefinition.Baseclass;
import tech.grasshopper.pdf.extent.ExtentPDFCucumberReporter;


public class ResultClass extends stepDefinition.Baseclass {

	public static ExtentTest exTest;
	public static ExtentTest exNode;
	public static ExtentTest exTestPdf;
	public static ExtentReports extent;
	public static ExtentReports extentPdf;
	public static ExtentSparkReporter sparkReport;
	public static ExtentPDFCucumberReporter pdf;
	public static String dateName;
	public static String destination;
	AppConfiguration properties = new AppConfiguration();
	
	

	public static ExtentTest createTests(String testName) {

		return extent.createTest(testName);

	}
	
	public static ExtentTest createNodeTests(String testName) {

		return ResultClass.exTest.createNode(testName);
	}

	
	public static ExtentTest createTestsPdf(String testName) {

		return extentPdf.createTest(testName);

	}
	/**	 Prints the custom message in the Extend reports output - log 
	 * @param logger - ExtendTest Logger
	 * @param msg - Message
	 */
	public static void logMessage(Status status, String msg) {
		logHtmlMessage(status, msg);
		logPdfMessage(status, msg);
	}

	public static void logHtmlMessage(Status status, String msg) {
		exTest.log(status, msg);

	}

	public static void logPdfMessage(Status status, String msg) {
		exTestPdf.log(status, msg);

	}

	public static void logMessageScreenshot(Status status, String msg, Page page) throws Exception {
		logMessageHtmlScreenshot(status, msg, page);
//		logMessagePdfScreenshot(status, msg, driver);
	}

	public static void logMessageHtmlScreenshot(Status status, String msg,Page page ) throws Exception {

//		exTest.log(status, msg, MediaEntityBuilder.createScreenCaptureFromPath(ScreenshotUtils.takeScreenshotBase64(page)).build());
		exTest.log(status, msg, MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.takeScreenshotBase64(page)).build());

	}

	public static void logMessagePdfScreenshot(Status status, String msg, Page page) throws Exception {
	exTestPdf.log(status, msg, MediaEntityBuilder.createScreenCaptureFromPath(destination).build());

	}

	
	
/*
	public static String getScreenhot(WebDriver driver) throws Exception {
		dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		destination = reportFolder + dateName + ".png";
		File finalDestination = new File(destination);
		String screenPath = "./Screenshots/" + dateName + ".png";
		FileUtils.copyFile(source, finalDestination);
		return screenPath;
	}
*/
	

	/**
	 * HTML report path
	 * 
	 * @param driver - WebDriver
	 * @throws IOException
	 * @throws InterruptedException
	 */

	public void createReport() throws IOException {

		extent = new ExtentReports();
		extentPdf = new ExtentReports();
		// SubFolder Creation with Respect to Batch Execution
		properties.putPropValues("subFolderName",
				properties.getPropValues("reportPath") + folderName + "\\" + fileNameCompare);

		new File(properties.getPropValues("projectPath") + "\\Reports\\Report" + folderName).mkdir();
		new File(properties.getPropValues("projectPath") + "\\Reports\\Report" + folderName + "\\" + fileNameCompare)
				.mkdir();
		reportFolder = properties.getPropValues("projectPath") + "\\Reports\\Report" + folderName + "\\"
				+ fileNameCompare + "\\Screenshots\\";

		new File(properties.getPropValues("projectPath") + "\\Reports\\Report" + folderName + "\\" + fileNameCompare
				+ "\\Screenshots").mkdir();
		
		//Commenting the old spark report
		ResultClass.sparkReport = new ExtentSparkReporter(properties.getPropValues("projectPath") + "\\Reports\\Report"
				+ folderName + "\\" + fileNameCompare + "\\Automation Report.html");
	
		final File conf=new File(System.getProperty("user.dir") + "\\spark-config.xml");
		
		  String logoBase64 = ReportUtils.encodeImageToBase64(System.getProperty("user.dir")+"\\logo.jpeg");
		  String reportTitle = "<img src='data:image/jpeg;base64," + logoBase64 + "' width='25' style='max-height: 25px;'";
          sparkReport.config().setReportName(reportTitle);
//		sparkReport.loadXMLConfig(conf);

//		ResultClass.sparkReport.config().setCss("css-string");
//		//ResultClass.sparkReport.config().enableOfflineMode(true);
//		ResultClass.sparkReport.config().setEncoding("utf-8");
//		ResultClass.sparkReport.config().setJs("js-string");
//		ResultClass.sparkReport.config().setProtocol(Protocol.HTTPS);
//		ResultClass.sparkReport.config().setTheme(Theme.DARK);
		
//		ResultClass.sparkReport.config().setDocumentTitle("Esurety");
//		ResultClass.sparkReport.config().setReportName("Automation Test Result");
//		ResultClass.sparkReport.config().setTimeStampFormat("MM-dd-yyyy HH:mm:ss aa");
		ResultClass.extent.attachReporter(ResultClass.sparkReport);
		
				
//		ResultClass.pdf = new ExtentPDFCucumberReporter( properties.getPropValues("projectPath") + "\\Reports\\Report"
//			+ folderName + "\\" + fileNameCompare + "\\ESurety Automation Report.pdf","Screenshots");
//		ResultClass.extentPdf.attachReporter(ResultClass.pdf);
		
		String syst = InetAddress.getLocalHost().getHostName();
		extent.setSystemInfo("OPERATING SYSTEM", System.getProperty("os.name"));
		extent.setSystemInfo("JAVA VERSION ", System.getProperty("java.version"));
		extent.setSystemInfo("USER NAME ", System.getProperty("user.name"));
		extent.setSystemInfo("ENVIRONMENT ", properties.getPropValues("env"));
		extent.setSystemInfo("SYSTEM NAME ", syst);

		

	}

	

	

	/**
	 * Prints the custom message in the Extend reports output - Info
	 * 
	 * @param logger - ExtendTest Logger
	 * @param msg    - Message
	 */
	
	public void logInfo(ExtentTest logger, String msg) {
		// TODO Auto-generated method stub
//		logger.getStatus();
		logHtmlMessage(Status.INFO, msg);
		
//		logPdfMessage(Status.INFO, msg);

	}

	public void logInfo(ExtentTest logger, String msg, Page page) throws Exception {
		// TODO Auto-generated method stub
		logMessageHtmlScreenshot(Status.INFO, msg, page);
//		logMessagePdfScreenshot(Status.INFO, msg, driver);
	}

	public void logWarning(ExtentTest logger, String msg, Page page) throws Exception {
		// TODO Auto-generated method stub
		logMessageHtmlScreenshot(Status.WARNING, msg, page);
//		logMessagePdfScreenshot(Status.WARNING, msg, driver);
	}
	
	public void logBoldInfo(Status status, String msg) {
		exTest.log(status,  "<b><u>" + msg + "</u></b>" );
	}
	
	public void logBoldInfo(ExtentTest logger, String msg, Page page) throws Exception {
		logMessageHtmlScreenshot(Status.INFO, "<b><u>" + msg + "</u></b>", page);
//		logMessagePdfScreenshot(Status.INFO, "<b><u>" + msg + "</u></b>", driver);
	}

	public void logBoldFail(ExtentTest logger, String msg,Page page) throws Exception {
		//logger.getStatus();
		logMessageHtmlScreenshot(Status.FAIL,"<b><u>" + msg + "</u></b>", page);
//		logMessagePdfScreenshot(Status.FAIL, "<b><u>" + msg +"</u></b>" , driver);
		logger.log(Status.FAIL, msg);
		
	}

	public void logFail(ExtentTest logger, String msg,Page driver) throws Exception {
		//logger.getStatus();
		logMessageHtmlScreenshot(Status.FAIL, msg, driver);
//		logMessagePdfScreenshot(Status.FAIL, msg, driver);
//		logger.log(Status.FAIL, msg);
	}


	public void logPass(ExtentTest logger, String msg) {
		logHtmlMessage(Status.PASS, msg);
//		logPdfMessage(Status.PASS, msg);
	}


	public void Fail(Object object) {
		// TODO Auto-generated method stub
		logHtmlMessage(Status.FAIL, null);
//		logPdfMessage(Status.FAIL, null);

	}


	public void logFail(ExtentTest logger, String msg) {
		logHtmlMessage(Status.FAIL, msg);
//		logPdfMessage(Status.FAIL, msg);
//		logger.log(Status.FAIL, msg);	
		
		
	}
	
	
	
	public void logfail(ExtentTest logger, String msg) {
		logHtmlMessage(Status.FAIL, msg);
//		logPdfMessage(Status.FAIL, msg);
//		logger.log(Status.FAIL, msg);
	}


	public void logPass(ExtentTest logger, String msg,Page page) throws Exception {
		logMessageHtmlScreenshot(Status.PASS, "<b><u>" + msg + "</u></b>", page);
//		logMessagePdfScreenshot(Status.PASS, "<b><u>" + msg + "</u></b>", driver);
//		logger.log(Status.PASS, msg);
		
	}


	public void logBoldInfo(ExtentTest logger, String msg) {
		// TODO Auto-generated method stub
		logHtmlMessage(Status.INFO,"<b><u>" + msg + "</u></b>");
		logPdfMessage(Status.INFO, "<b><u>" + msg +"</u></b>");
		logger.log(Status.INFO, msg);
	}

}