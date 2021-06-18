package com.massmutual.util;

import java.util.Map;

import org.openqa.selenium.WebDriverException;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {
	public static Scenario scenario;
	public static Map<String,String> data = null;
	@Before
	public void f(Scenario scenario){
		Configuration.initDriver();
		Hooks.scenario = scenario;
		data = DataUtil.readTestDataFromExcel(scenario.getName());
	}

	@After
	public void afterTest(Scenario scenario) throws InterruptedException {
		if (scenario.isFailed()) {
			try {
				Configuration.embedScreenShotToReport();
				Configuration.driver.close();
			} catch (WebDriverException e) {
			}
		}
	}
}
