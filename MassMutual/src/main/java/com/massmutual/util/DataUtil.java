package com.massmutual.util;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import cucumber.api.DataTable;


public class DataUtil {
	/*
	 * This function reads the data from feature file and returns in Map format
	 */
	public static Map<String,String> data(DataTable dataTable){
		return dataTable.asMaps(String.class, String.class).get(0);
	}

	/*
	 * This function reads the data from excel file and returns in Map format
	 * Parameters: Scenario name
	 * Flow: Based on the provided scenario name, it reads a row from the excel file and returns the data in map format
	 */
	public static Map<String,String> readTestDataFromExcel(String tcName){
		try
		{
			int counter=0;
			Row row2 = null;
			Map<String,String> mapOfTestCasesFromColumnOne = new HashMap<String,String>();
			List<String> list1 = new ArrayList<String>();
			List<String> list2 = new ArrayList<String>();
			Workbook workbook = WorkbookFactory.create(new File(Global.excelTestDataFile));
			Sheet sheet = workbook.getSheet(Global.sheetName1);
			DataFormatter dataFormatter = new DataFormatter();
			for (Row row: sheet) {
				counter++;
				if(dataFormatter.formatCellValue(row.getCell(0)).equalsIgnoreCase(tcName)){
					row2 = sheet.getRow(counter);
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						list1.add(dataFormatter.formatCellValue(cell));
					}
					Iterator<Cell> cellIterator2 = row2.cellIterator();
					while (cellIterator2.hasNext()) {
						Cell cell = cellIterator2.next();
						list2.add(dataFormatter.formatCellValue(cell));
					}
					break;
				}
			}
			list1.remove(0);
			for(int i =0;i<list1.size();i++){
				mapOfTestCasesFromColumnOne.put(list1.get(i), list2.get(i));
			}
			return mapOfTestCasesFromColumnOne;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	/*
	 *This function is used to read the data from a json file based on provided scenario name and returns the data in map format
	 *Parameters: scenario name 
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> readTestDataFromJson(String tcName){
		try{
			JSONObject employeeList = (JSONObject) new JSONParser().parse(new FileReader(Global.jsonTestDataFile));
			return (Map<String, String>) employeeList.get(tcName);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/*
	 *This function is used to make a choice between json and excel
	 *Parameters: testdata file format 
	 */
	public static Map<String, String> readTestData(String tcName){
		if(Global.testDataChoice.equalsIgnoreCase("json"))
			return readTestDataFromJson(tcName);
		else if(Global.testDataChoice.equalsIgnoreCase("excel"))
			return readTestDataFromExcel(tcName);
		return null;
	}
}

