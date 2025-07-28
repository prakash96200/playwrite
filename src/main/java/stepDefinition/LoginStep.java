package stepDefinition;

import java.util.List;
import io.cucumber.java.en.When;

import org.testng.annotations.Test;

import com.microsoft.playwright.Page;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pages.Superuser;
import pages.LoginPage;
import utility.ResultClass;

public class LoginStep {
	
	
    
    @Given("Login to the Abra login")
    public void login_to_the_Abra_login() throws Throwable {
    	
    	 LoginPage.launchUrl();
    	 LoginPage.enterCredentials();
    	 
    }
    
    @When("user creates superuser account with valid credentials")
    public void user_creates_superuser_account_with_valid_credentials() throws Throwable {
    	  
    	  Superuser.createSuperuser();
    }
    }
    


