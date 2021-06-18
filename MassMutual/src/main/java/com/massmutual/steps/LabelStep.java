package com.massmutual.steps;

import com.massmutual.pageobjects.Label;
import com.massmutual.util.Configuration;
import com.massmutual.util.Hooks;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class LabelStep 
{
	Label objLabel = new Label();
	
	
	@Given("^user login to application with \"([^\"]*)\" and \"([^\"]*)\"$")
	public void login_to_application(String username,String password) throws Throwable {
		objLabel.launch_app();
		objLabel.enter_username(Hooks.data.get(username));
		objLabel.enter_password(Hooks.data.get(password));	  
	}

	@When("^user select \"([^\"]*)\" and do login$")
	public void select_location(String location) throws Throwable {
		objLabel.select_location(Hooks.data.get(location));
		objLabel.click_onlogin();
	}

	@Then("^verify Msg$")
	public void verify_Msg() throws Throwable {
		objLabel.verify_Msg();
	}

	@Then("^Close Window$")
	public void close_window() throws Throwable {
		Configuration.closeWindow();
	}
}