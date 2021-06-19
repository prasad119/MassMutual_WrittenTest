package com.massmutual.steps;

import com.massmutual.pageobjects.Label;
import com.massmutual.util.Configuration;
import com.massmutual.util.Hooks;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class LabelStep 
{
	Label objLabel = new Label();	

	@Then("^verify the count of values$")
	public void verifyCount() throws Exception {
		objLabel.countOfValues();
	}

	@Then("^verify values are greater than zero$")
	public void verify_values() throws Exception {
		objLabel.verifyValues();
	}
	
	@And("^verify the format of the values$")
	public void verifyFormat() throws Exception {
		objLabel.verifyFarmatOfValues();
	}
	
	@Then("^verify Total Balance$")
	public void verifyTotal() throws Exception {
		objLabel.verifyTotalBalance();
	}

}