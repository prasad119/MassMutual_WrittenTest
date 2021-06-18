package com.massmutual.util;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Configuration{

	public static WebDriver driver = null;

	public static void initDriver() {
		if (Global.browser.equalsIgnoreCase("chrome")) {
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", Global.downloadFilepath);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", chromePrefs);
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			System.setProperty("webdriver.chrome.driver", "resources//chromedriver.exe");
			driver = new ChromeDriver(cap);	
		}
		if (Global.browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "resources//geckodriver.exe");
			driver = new FirefoxDriver();		
		}
		if (Global.browser.equalsIgnoreCase("ie")) {
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			System.setProperty("webdriver.ie.driver", "resources//IEDriverServer.exe");
			driver = new InternetExplorerDriver(capabilities);	
		}
		driver.get(Global.url);
		driver.manage().timeouts().implicitlyWait(Global.waitTime, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	public static String captureScreenShot() {
		try {
			String filePath = Global.screenShotLocation;
			FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath));
			return filePath;
		} catch (Exception e) {
			Hooks.scenario.write("Screenshot captured");
		}
		return null;

	}

	public static void clickOnElement(WebElement element) {
		try {
			if (waitForElement(element)) {
				element.click();
				Hooks.scenario.write("Clicked on element button");
			}
		} 
		catch (Exception e) {
			Hooks.scenario.write("Unable to do click action on " + element);
		}
	}

	public static boolean waitForElement(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Global.waitTime);
			WebElement elementInWait = wait.until(ExpectedConditions.visibilityOf(element));
			if (elementInWait.isDisplayed())
				return true;
			else
				return false;
		} catch (Exception e) {
			Hooks.scenario.write("The expected element is not displayed on the screen//n "+e);
			return false;
		}
	}

	public static void enterValue(WebElement element, String data) {

		try { if(waitForElement(element)) {

			element.sendKeys(data);
			Hooks.scenario.write(data + " entered in text box");
		}
		}
		catch(Exception e) {
			Hooks.scenario.write("Unable to enter "+data+" in textbox"); 
		}
	}

	public static void closeWindow() {
		try {
			driver.close();
		}
		catch(Exception e) {
			Hooks.scenario.write("Current window not closed");
		}
	}

	public static void teardown() {
		try {
			driver.quit();
		}
		catch(Exception e) {
			Hooks.scenario.write("Instance not closed properly");
		}
	}
	
	public static void embedScreenShotToReport(){
		try{
			byte[] screenShot = ((TakesScreenshot) Configuration.driver).getScreenshotAs(OutputType.BYTES);
			Hooks.scenario.embed(screenShot, "image/png");
		}
		catch(Exception e){
			
		}
	}


}
