package com.massmutual.util;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;

@CucumberOptions(
		features="Feature",
		glue={"org.cucumber.stepdefs","org.cucumber.util"},
		plugin={"pretty",
				"html:target/cucumber-reports/cucumber-pretty",
				"json:target/cucumber-reports/CucumberTestReport.json",
		"rerun:target/cucumber-reports/re-run.txt"}
		)
public class Runner  
{
	private TestNGCucumberRunner testRunner;

	@Parameters({"tags"})
	@BeforeTest(alwaysRun = true)
	public void setUP(String tags)
	{
		String temp ="",flag="";
		String[] splited = tags.split(" ");
		if(splited[0].equalsIgnoreCase("--and")){
			for(int i=1;i<splited.length;i++){
				temp = temp+"--tags "+splited[i]+" ";
			}
			flag = temp;
		}
		if(splited[0].equalsIgnoreCase("--or")){
			for(int i=1;i<splited.length;i++){
				temp = temp+splited[i]+",";
			}
			flag = "--tags "+temp.substring(0, temp.length()-1);
		}
		System.out.println(flag);
		System.setProperty("cucumber.options", flag);
		testRunner = new TestNGCucumberRunner(Runner.class);
	}
	@Test(dataProvider="features")
	public void runFeatures(CucumberFeatureWrapper cFeature)
	{
		testRunner.runCucumber(cFeature.getCucumberFeature());
	}
	@DataProvider(name="features")
	public Object[][] getFeatures()
	{
		return testRunner.provideFeatures();
	}
	@AfterTest(alwaysRun = true)
	public void tearDown()
	{
		testRunner.finish();
	}


}