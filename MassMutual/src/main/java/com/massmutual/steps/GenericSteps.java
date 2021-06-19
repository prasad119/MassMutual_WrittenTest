package com.massmutual.steps;

import com.massmutual.util.Configuration;

import cucumber.api.java.en.Given;

public class GenericSteps {
	
	Configuration conf = new Configuration();
	
	@Given("^launch the application$")
	public void launchApplication() throws Exception {
		conf.launch_app();
	}

}
