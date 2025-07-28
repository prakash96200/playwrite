package stepDefinition;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.BrowserChannel;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestStepStarted;
import utility.AppConfiguration;
import utility.DriverClass;
import utility.EmailConfiguration;
import utility.ExcelUtility;
import utility.GenericMethods;
import utility.ResultClass;
import utility.ScreenshotUtils;

public class Baseclass implements ConcurrentEventListener {

    public static GenericMethods objGM = GenericMethods.getInstanceOfSingletonClass();
    public static AppConfiguration properties = new AppConfiguration();
    public static AppConfiguration extentProperties = new AppConfiguration();
    public static DriverClass driverClass = new DriverClass();
    public static ResultClass resultClass = new ResultClass();
    public static EmailConfiguration sendResultMail = new EmailConfiguration();
    public static Map<String, List<String>> dictObj_or = new HashMap<>();

    public static String reportFolder;
    public static ExtentReports reports = new ExtentReports();
    public static String scenarioNames;
    public static String testCaseName = "";
    public static String caseName = "";

    public static List<String> scenarios = new ArrayList<>();

    public static String startTime;
    public static String endTime;

    public static int sPass = 0;
    public static int sFail = 0;
    public static int count = 0;

    public static boolean sCount = true;
    public static boolean hflag = true;
    public static boolean dCount = true;
    public static int index = 0;
    public static String folderName = null;
    public static String fileNameCompare = null;

    public static ArrayList<String> al_login = new ArrayList<>();
    public static ArrayList<String> al_createuser = new ArrayList<>();

    public static Browser browser;
    public Playwright playwright;
    public static BrowserContext context;
    public static Page page;
    public static Page page2;

    @BeforeAll
    public static void reportSetup() throws IOException, EncryptedDocumentException, InvalidFormatException, InterruptedException {
        properties.putPropValues("projectPath", System.getProperty("user.dir"));
        startTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss aa").format(new GregorianCalendar().getTime());
        folderName = new SimpleDateFormat("MM-dd-yyyy").format(new GregorianCalendar().getTime());
        fileNameCompare = new SimpleDateFormat("MM-dd-yyyy_HHmmssaa").format(new GregorianCalendar().getTime());

        properties.putPropValues("reportPath", "\\Reports\\Report");
        properties.putPropValues("orFilePath", System.getProperty("user.dir") + "\\DataRepository\\OR_Abra.xlsx");
        resultClass.createReport();

        properties.putPropValues("envCompare", "\\Reports\\Template for Env Data Comparison.xlsm");
        String fileCompare = properties.getPropValues("projectPath") + properties.getPropValues("subFolderName") + "\\DataCompare_" + fileNameCompare + ".xlsm";

        String extPropPath = properties.getPropValues("projectPath") + "\\Reports\\Report" + folderName + "\\" + fileNameCompare;
        extentProperties.putExtentPropValues("basefolder.name", extPropPath);
        String pdfPath = properties.getPropValues("projectPath") + "\\Reports\\Report" + folderName + "\\" + fileNameCompare + "\\ReportSpark.pdf";
        extentProperties.putExtentPropValues("extent.reporter.pdf.out", pdfPath);

        properties.putPropValues("compareRegressionData", fileCompare);
        ExcelUtility.readTestCase();
        objGM = new GenericMethods();
    }

    @Before
    public void before_start(Scenario scenario) throws Throwable {
        scenarioNames = scenario.getName();
        testCaseName = scenarios.get(index);
        properties.putPropValues("regressionScenario", scenario.getName());
        
        String scenarioName = testCaseName;
        properties.putPropValues("testDataScenario", scenarioName);
        properties.putPropValues("policyNumber", "NA");
        ResultClass.exTest = ResultClass.createTests(testCaseName);
        properties.putPropValues("orFilePath", System.getProperty("user.dir") + "\\DataRepository\\OR_Abra.xlsx");
        GenericMethods.readObjectRepository(properties.getPropValues("orFilePath"), "Abra", dictObj_or);

        GenericMethods.fileName = System.getProperty("user.dir") + "\\DataRepository\\DataSheet_Abra.xlsx";

        objGM.datareading(al_login, "login");
        objGM.datareading(al_createuser, "createuser");

        String testCaseValue = objGM.testCaseValueReading("login");
        if (testCaseValue == null || testCaseValue.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ testCaseValueReading('login') returned empty. Check your Excel test data.");
        }
        int rowIndex = Integer.parseInt(testCaseValue.split(",")[0]);

        playwright = Playwright.create();
        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("--start-maximized");

        String browserType = objGM.dataValuereading("login", rowIndex, al_login.indexOf("browserType"));

        switch (browserType.toLowerCase()) {
            case "chrome":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(arguments).setChannel(BrowserChannel.CHROME));
                break;
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(arguments));
                break;
            case "edge":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(arguments).setChannel(BrowserChannel.MSEDGE));
                break;
            default:
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
        }

        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        context.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true).setSources(true));
        context = browser.newContext(new Browser.NewContextOptions().setRecordVideoDir(Paths.get(properties.getPropValues("projectPath") + "\\Reports\\Report\\videos")));

        page = context.newPage();

        properties.putPropValues("regressionScenario", scenario.getName());
        properties.putPropValues("testDataScenario", scenarioName);

        index++;
    }

    @AfterAll
    public static void close() throws IOException, InvalidFormatException {
        properties.putPropValues("regressionScenario", "");
        endTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss aa").format(new GregorianCalendar().getTime());
        ResultClass.extent.flush();
    }

    public static String getTestCaseName() {
        return caseName;
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, stepHandler);
    }

    public EventHandler<TestStepStarted> stepHandler = new EventHandler<TestStepStarted>() {
        @Override
        public void receive(TestStepStarted event) {
            handleTestStepStarted(event);
        }
    };

    private String stepName;

    private void handleTestStepStarted(TestStepStarted event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep testStep = (PickleStepTestStep) event.getTestStep();
            stepName = testStep.getStep().getText();
        }
    }
}