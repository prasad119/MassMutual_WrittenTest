package com.massmutual.pageobjects;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.massmutual.util.Configuration;
import com.massmutual.util.Global;

public class Label extends Configuration{

	public Label(){ 
		PageFactory.initElements(driver, this);
	}

	@FindBy(how=How.XPATH, using="//*[contains(@id,'lbl_val_')]")
	List<WebElement> list_val_lbl;
	
	@FindBy(how=How.XPATH, using="//*[contains(@id,'txt_val_')]")
	List<WebElement> list_val_txt;
	
	@FindBy(how=How.ID, using="txt_ttl_val")
	WebElement txt_ttl_val;
	
	static double total = 0;
	
	public void countOfValues(){
		List<WebElement> listOfValueLabels = list_val_lbl;
		List<WebElement> listOfAmountLabels = list_val_txt;
		
		if(listOfValueLabels.size()!=listOfAmountLabels.size()) {
			System.out.println("The count of values "+listOfValueLabels.size() +"is not equal to count of lbl "+listOfAmountLabels.size());
			Assert.fail();
		}
		else {
			System.out.println("The count of values "+listOfValueLabels.size() +"is equal to count of lbl "+listOfAmountLabels.size());
		}
		
	}
	
	public void verifyValues() {
		list_val_txt.forEach(option -> {
			if(Double.parseDouble(option.getText())<0) {
				System.out.println(option.getText()+" is less than zero");
				Assert.fail();
			}
			else {
				System.out.println("Values verification is successful");
			}			
		});
	}
	
	public void verifyTotalBalance() {		
		list_val_txt.forEach(option ->{
			double val = Double.parseDouble(option.getText().replace("$", ""));
			total = val+total;
		});
		
		if(total!=Double.parseDouble(txt_ttl_val.getText())) {
			System.out.println("The sum of values "+total+" is not equal to total amount "+txt_ttl_val.getText());
			Assert.fail();
		}
		else {
			System.out.println("Total verification is successful");
		}
	}
	
	public void verifyFarmatOfValues() {
		list_val_txt.forEach(option ->{
			double val = Double.parseDouble(option.getText().replace("$", ""));
			String va = NumberFormat.getCurrencyInstance(new Locale("en", "US"))
	        .format(val);
			if(!option.getText().equals(va)) {
				System.out.println(option+" is not in the currency format");
			}
			else {
				System.out.println("currency format validation successful");
			}
		});
	}



}
