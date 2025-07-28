package testRunner;



import java.util.Arrays;
import java.util.stream.Collectors;


import org.testng.annotations.DataProvider;

import io.cucumber.java.Scenario;

import io.cucumber.tagexpressions.TagExpressionParser;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.PickleWrapper;


	

	@CucumberOptions(
			features="src/main/resources/Features/",
			glue= {"stepDefinition"},
			tags = "not @Ignore",
			monochrome = false,
			
			plugin = {"json:build/cucumber-reports/cucumber.json",
				       "rerun:build/cucumber-reports/rerun.txt",
				       "pretty", 
				       "html:target/cucumber-reports.html",
//				       "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
//				       "com.avenstack.chaintest.plugins.ChainTestCucumberListener:",
//				       "com.aventstack.chaintest.plugins.ChainTestCucumberListener:",
//				       "summary",
//				       "stepDefinition.customReportListener",
//				       "com.aventstack.chaintest.plugins.ChainTestCucumberListener:"
//				       "com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"
				      },
			publish=true,
			dryRun = false
			
			)
	public class Runner2 extends AbstractTestNGCucumberTests{
//		public void runMethod() {
//			System.out.println("inside runner");
//		}
		
		@DataProvider
	    @Override
	    public Object[][] scenarios() {
	        Object[][] scenarios = super.scenarios();
	        Object[][] scenarioNames = new Object[scenarios.length][1];
	        String tag = null;
	        for (int i = 0; i < scenarios.length; i++) {
	            // Assuming scenarios[i] is an instance of Scenario and has a getName() method.
	        	 PickleWrapper pickleWrapper = (PickleWrapper) scenarios[i][0];
	             scenarioNames[i][0] = pickleWrapper.getPickle().getName(); // Get scenario name
	             
	        }
//	        System.out.println(scenarioNames[0].length);

	        // Optional: Print out the names for debugging
	        for (Object[] name : scenarioNames) {
	        	if(!name.equals(tag)) {
	        		
	        	}
	            System.out.println(name[0]); // Prints the scenario name
	        }
	        System.out.println(scenarios);
	        return scenarios;
	    }
		
}
