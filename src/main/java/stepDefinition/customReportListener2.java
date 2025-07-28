package stepDefinition;

//import com.aventstack.chaintest.plugins.ChainTestListener;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.model.ScreenCapture;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.BrowserChannel;

import io.cucumber.core.exception.ExceptionUtils;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.cucumber.messages.types.TestCaseFinished;
import io.cucumber.messages.types.TestStepResult;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import io.cucumber.plugin.event.TestSourceRead;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;
import utility.AppConfiguration;
import utility.ExcelUtility;
import utility.GenericMethods;
import utility.ResultClass;

import io.cucumber.plugin.event.HookTestStep;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.xmlbeans.impl.util.Base64;
import org.testng.annotations.BeforeSuite;



public class customReportListener2 implements EventListener  {
	
	public static BrowserContext context;
	public static BrowserContext context2;
	AppConfiguration properties = new AppConfiguration();
	private ExtentSparkReporter spark;
	
	private static ExtentReports extent;
	public static String testCaseName ;

	Map<String, ExtentTest> feature = new HashMap<String, ExtentTest>();
	public static ExtentTest scenario;
	private ExtentTest step1;
	public static Browser browser;
	public static Browser browser2;
	public Playwright playwright;
	public static ExtentTest step;
	public static Page page;
//	public static Page activePage;
	public static Page page2;
	public static int index=0;
	
	public static Map<String, List<String>> dictObj_or = new HashMap<String, List<String>>();
	public static ArrayList<String> al_NBSubmission=new ArrayList<String>();
	public static List<String> scenarios = new ArrayList<>();
	public static GenericMethods objGM = new GenericMethods();
	public static ArrayList<String> al_login=new ArrayList<String>();
	public customReportListener2() {
	};

	
//	@Before
	public void beforeAll() throws IOException, InterruptedException, InvalidFormatException {
		
		//*** Set OR_File path
		properties.putPropValues("orFilePath", System.getProperty("user.dir") + "\\DataRepository\\OR_CountryWide_Claim.xlsx");
		GenericMethods.readObjectRepository(properties.getPropValues("orFilePath"), "CountryWide_Claim", dictObj_or);
		
		//*** Read testcase 
//		ExcelUtility.readTestCase();
		testCaseName=scenarios.get(index);
		
		//*** Set Datasheet path
		GenericMethods.fileName = System.getProperty("user.dir")+"\\DataRepository\\DataSheet_CountryWide_Claim.xlsx";
		objGM.datareading(al_login, "login");
		
		
		
		
		Thread.sleep(3000);
		playwright = Playwright.create();
//		browser= (Playwright) playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
		ArrayList<String> arguments=new ArrayList<>();
		arguments.add("--start-maximized");
		
		switch(customReportListener2.objGM.dataValuereading("login", Integer.parseInt(customReportListener2.objGM.testCaseValueReading("login").split(",")[0]), customReportListener2.al_login.indexOf("browserType"))){
		case "chrome":
			Thread.sleep(2000);
			browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(arguments).setChannel(BrowserChannel.CHROME));
			break;
		case "firefox":
			browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(arguments));
			break;
		case "":
			browser= (Browser) playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
			break;
		}
		
//		
		
		context = browser.newContext();
//		page = context.newPage();
		 context = ((Browser) browser).newContext();
		 context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
		// Start tracing before creating / navigating a page.
		context.tracing().start(new Tracing.StartOptions()
		  .setScreenshots(true)
		  .setSnapshots(true)
		  .setSources(true));
		context = ((Browser) browser).newContext(new Browser.NewContextOptions().setRecordVideoDir(Paths.get(properties.getPropValues("projectPath") + "\\Reports\\Report\\videos")));
		System.out.println("screen size "+browser.contexts().size());
//		context= browser.newContext(new Browser.NewContextOptions().setViewportSize(1358,642));
//		page=browser.newPage();
		
		 page = context.newPage();
		
//		activePage=page;
		 index++;
	}
	
	

	@Override
	public void setEventPublisher(EventPublisher publisher) {
		// TODO Auto-generated method stub
try {
	properties.putPropValues("projectPath", System.getProperty("user.dir"));
} catch (IOException e2) {
	// TODO Auto-generated catch block
	e2.printStackTrace();
}
		
//		ExcelUtility.readTestCase();
		
		
		Baseclass.folderName = new SimpleDateFormat("MM-dd-yyyy").format(new GregorianCalendar().getTime());
		Baseclass.fileNameCompare = new SimpleDateFormat("MM-dd-yyyy_HHmmssaa").format(new GregorianCalendar().getTime());
		
		String path=System.getProperty("user.dir")+ "\\Reports\\Report"
				+ Baseclass.folderName + "\\" + Baseclass.fileNameCompare;
		createFolderIfNotExists(path);
		
		try {
			properties.putPropValuesChainTest("chaintest.generator.simple.output-file", path +"\\Index.html");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		/*
		 * :: is method reference , so this::collecTag means collectTags method in
		 * 'this' instance. Here we says runStarted method accepts or listens to
		 * TestRunStarted event type
		 */
		publisher.registerHandlerFor(TestRunStarted.class, this::runStarted);
		publisher.registerHandlerFor(TestRunFinished.class, event -> {
			try {
				runFinished(event);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		publisher.registerHandlerFor(TestSourceRead.class, this::featureRead);
		publisher.registerHandlerFor(TestCaseStarted.class, event -> {
			try {
				ScenarioStarted(event);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		publisher.registerHandlerFor(TestStepStarted.class, this::stepStarted);
		publisher.registerHandlerFor(TestStepFinished.class, event -> {
			try {
				stepFinished(event);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		publisher.registerHandlerFor(TestCaseFinished.class, event -> {
			try {
				scenarioFinished(event);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		
//		publisher.registerHandlerFor(TestStepResult.class,  this.step);


	};


	/*
	 * Here we set argument type as TestRunStarted if you set anything else then the
	 * corresponding register shows error as it doesn't have a listner method that
	 * accepts the type specified in TestRunStarted.class
	 */


	// Here we create the reporter
	private void runStarted(TestRunStarted event) {
		try {
//			spark = new ExtentSparkReporter(properties.getPropValues("projectPath") + "\\Reports\\Report"
//					+ Baseclass.folderName + "\\" + Baseclass.fileNameCompare + "\\Automation Report.html");
		
			spark = new ExtentSparkReporter(properties.getPropValues("projectPath") + "\\Reports\\Automation Report.html");
		
			
			
		extent = new ExtentReports();
		spark.config().setTheme(Theme.DARK);
		// Create extent report instance with spark reporter
//		extent.attachReporter(spark);
		spark.config().setJs("document.getElementsByClassName('col-md-4')[3].style.setProperty('width','100%');");
		spark.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss a");
		spark.viewConfigurer().viewOrder().as(new ViewName[] {ViewName.DASHBOARD,ViewName.TEST});
		
		String syst = InetAddress.getLocalHost().getHostName();
		extent.setSystemInfo("OPERATING SYSTEM", System.getProperty("os.name"));
		extent.setSystemInfo("JAVA VERSION ", System.getProperty("java.version"));
		extent.setSystemInfo("USER NAME ", System.getProperty("user.name"));
		extent.setSystemInfo("ENVIRONMENT ", properties.getPropValues("env"));
		extent.setSystemInfo("SYSTEM NAME ", syst);
		File conf=new File(System.getProperty("user.dir") + "\\spark-config.xml");
		spark.loadXMLConfig(conf);
		
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};


	// TestRunFinished event is triggered when all feature file executions are
	// completed
	private void runFinished(TestRunFinished event) throws IOException {
		extent.flush();
//		Baseclass.teardown();
	};


	// This event is triggered when feature file is read
	// here we create the feature node
	private void featureRead(TestSourceRead event) {
		String featureSource = event.getUri().toString();
		String featureName = featureSource.split(".*/")[1];


		if (feature.get(featureSource) == null) {

//			ExtentTest test=extent.createTest(Baseclass.scenarioNames);
//			ExtentTest test=extent.createTest("Testcaset");
//			feature.putIfAbsent(featureSource,test );
			
		}
	};


	// This event is triggered when Test Case is started
	// here we create the scenario node
	private void ScenarioStarted(TestCaseStarted event) throws IOException {
		
		String featureName = event.getTestCase().getUri().toString();
//		feature.get(featureName).log(Status.INFO, "This is PUMB UMB HXC scenario for LA state");
		
		System.out.println("feature name : "+feature.get(featureName));
		ExtentTest test=feature.get(featureName).createNode(event.getTestCase().getName());
		scenario = test;
		step=scenario;
//		test.log(Status.INFO, "abcd");
		
	
	};


	// step started event
	// here we creates the test node
	private void stepStarted(TestStepStarted event) {
		String stepName = " ";
		String keyword = "Triggered the hook :";
		// We checks whether the event is from a hook or step
		if (event.getTestStep() instanceof PickleStepTestStep) {
			// TestStepStarted event implements PickleStepTestStep interface
			// WHich have additional methods to interact with the event object
			// So we have to cast TestCase object to get those methods
			PickleStepTestStep steps = (PickleStepTestStep) event.getTestStep();
			stepName = steps.getStep().getText();
			keyword = steps.getStep().getKeyword();
		} else {
			// Same with HoojTestStep
			HookTestStep hoo = (HookTestStep) event.getTestStep();
			stepName = hoo.getHookType().name();
		}
//		if(!keyword.contains("Triggered the hook") && (!stepName.equals("BEFORE") || !stepName.equals("AFTER_STEP"))) {
//		step = scenario.createNode(Given.class, keyword + " " + stepName);
//		}
	};


	// This is triggered when TestStep is finished

	private void stepFinished(TestStepFinished event) throws Exception {
		
		String stepName = " ";
		String keyword = "Triggered the hook :";
		page.waitForTimeout(500);
//		String path = properties.getPropValues("projectPath") + "\\Reports\\Report"
//				+ Baseclass.folderName + "\\" + Baseclass.fileNameCompare+"\\Screenshots\\Screenshot";
//		String timestamp=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
//		String screenshot=path+timestamp+".png";
//		String screenshot=path+timestamp;
//		byte[] screenshotBytes = page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshot)));
//		byte[] screenshotBytes = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
//		String screenshotBase64 =java.util.Base64.getEncoder().encodeToString(page.screenshot(new Page.ScreenshotOptions().setFullPage(true)));
//		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshot.png")));
//		String screenshotBase64 = page.screenshot(new Page.ScreenshotOptions().setBase64(true));

		
//		Path screenshotPath = Paths.get(screenshot);
//		Files.createFile(screenshotPath); 
//        page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath));
//        byte[] screenshotBytes = Files.readAllBytes(screenshotPath);
//        String screenshotBase64 =java.util.Base64.getEncoder().encodeToString(page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath)));
		
		// We checks whether the event is from a hook or step
		if (event.getTestStep() instanceof PickleStepTestStep) {
			// TestStepStarted event implements PickleStepTestStep interface
			// WHich have additional methods to interact with the event object
			// So we have to cast TestCase object to get those methods
			PickleStepTestStep steps = (PickleStepTestStep) event.getTestStep();
			stepName = steps.getStep().getText();
			keyword = steps.getStep().getKeyword();


		} else {
			// Same with HoojTestStep
			HookTestStep hoo = (HookTestStep) event.getTestStep();
			stepName = hoo.getHookType().name();
		}

		if(!keyword.contains("Triggered the hook") && (!stepName.equals("BEFORE") || !stepName.equals("AFTER_STEP")) ) {
//		step = scenario.createNode(Given.class, keyword + " " + stepName);
		System.out.println("STEP : "+stepName);
		System.out.println("KEYWORD : "+keyword);
//		++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		// Capture a screenshot and convert it to Base64
		
		if (event.getResult().getStatus().toString() == "PASSED") {
			if(step!=null) {
//			step.log(Status.PASS, "<b>"+keyword+"</b> "+stepName);
//			step.log(Status.PASS,stepName);
//				step.log(Status.PASS, stepName, MediaEntityBuilder.createScreenCaptureFromPath(ResultClass.getScreenhot(Baseclass.driver)).build());
//			step.log(Status.PASS, Markup)
//			step.log(Status.PASS, stepName,
//                    MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build());
			step.log(Status.PASS, stepName);
//			ChainTestListener.log("Passed");
			}

		} else if (event.getResult().getStatus().toString() == "SKIPPED")

			
		{
			
			if(step!=null)
//			step.log(Status.SKIP, "<b>"+keyword+"</b> "+stepName);
			step.log(Status.SKIP,stepName);
//			step.log(Status.SKIP, stepName,
//                    MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build());
		} else {
			if(step!=null) {
			Exception e = null;
//			step.log(Status.FAIL, "<b>"+keyword+"</b> "+stepName, MediaEntityBuilder.createScreenCaptureFromBase64String(Baseclass.takescreeshot()).build());
//			step.log(Status.FAIL, stepName, MediaEntityBuilder.createScreenCaptureFromBase64String(Baseclass.takescreeshot()).build());
			step.log(Status.FAIL, stepName);
//			step.log(Status.FAIL, stepName,
//                    MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build());
			}
		}
		Thread.sleep(5000);
	}
		
	}


	private  void scenarioFinished(TestCaseFinished event) throws IOException {
		step.log(Status.INFO,"Scenario completed");
		// Make sure to close, so that videos are saved.
		context.close();
//		extent.flush();
	}
	


//	@AfterStep
    public void takeScreenshotAfterStep(Scenario scenario) {
//        if (((Scenario) scenario).isFailed() || true) { // Set `true` to always capture screenshots
            try {
                Path screenshotPath = Files.createTempFile("screenshot", ".png");
                page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath));
                byte[] screenshotBytes = Files.readAllBytes(screenshotPath);
                scenario.attach(screenshotBytes, "image/png", "Step Screenshot");
            } catch (Exception e) {
                e.printStackTrace();
            }
//        }
    }
	
	public static void createFolderIfNotExists(String path) {
        File folder = new File(path);
        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            if (created) {
                System.out.println("Folders created successfully: " + path);
            } else {
                System.out.println("Failed to create folders: " + path);
            }
        } else {
            System.out.println("Folders already exist: " + path);
        }
    }

}