package utility;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.testng.annotations.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.aventstack.extentreports.ExtentTest;

import stepDefinition.Baseclass;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class GenericMethods

{     
	public static String s= System.getProperty("user.dir") ;
	public static String fileName = s+"\\DataRepository\\DataSheet_Abra.xlsx";
	public boolean flag3;
	public static AppConfiguration properties = new AppConfiguration();
	static ResultClass objRC = new ResultClass();
	public static Map<String, List<String>> readObjectRepository( String fileName, String sheetName, Map<String, List<String>> dictObj ) throws IOException, InvalidFormatException
    {
        Workbook workbookObj = WorkbookFactory.create(new File(fileName));
    	Sheet sheet = workbookObj.getSheet(sheetName);
    	for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++)
    	{
        	 Row row = sheet.getRow(rowIndex);
        	  if (row != null) 
        	  {
        		Cell cell = row.getCell(1);
        	    Cell cell1 = row.getCell(3);
        	    Cell objProperty = row.getCell(6);
        	    if (cell != null) 
        	    {
        	      // Found column and there is value in the cell.
        	    	List<String> Value = new ArrayList<String>();
        	    	Value.add(cell.getStringCellValue());
        	    	Value.add(objProperty.getStringCellValue());
        	    	dictObj.put(cell1.getStringCellValue(),Value);
        	    }
        	}
        }
        workbookObj.close();
		return dictObj;
    }
	public String[][] getExcelData(String fileName, String sheetName) throws IOException 
	{
		String[][] arrayExcelData = null;
		Workbook workbook = null;
		try {
			if(fileName.contains(".xlsx")){
				FileInputStream inputStream = new FileInputStream(new File(fileName));
		        workbook = WorkbookFactory.create(inputStream);		
			}
			else{				
				FileInputStream inputStream = new FileInputStream(new File(fileName));
		        workbook = WorkbookFactory.create(new POIFSFileSystem(inputStream));		
			}
			Sheet sheet = workbook.getSheet(sheetName);

			// Total rows counts the top heading row
			int totalNoOfRows = sheet.getLastRowNum();
			Row row = sheet.getRow(0);
			int totalNoOfCols = row.getLastCellNum();

			arrayExcelData = new String[1][totalNoOfCols];

			try {
				for (int i= 1 ; i < totalNoOfRows; i++) {
					row = sheet.getRow(i);
					
					String compareTestDataScenario = row.getCell(1).toString().trim();
					if (Baseclass.getTestCaseName().equals(compareTestDataScenario) ) {
						for (int j=0; j < totalNoOfCols; j++) 
						{
							try{
								arrayExcelData[0][j] = row.getCell(j).toString().trim();
							}
							catch(Exception e){
								arrayExcelData[0][j] = "";
							}
						}
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		workbook.close();
		return arrayExcelData;
	}
	
	public Map<String, String> readEmailID(String fileName1, String sheetName1, Map<String, String> dictObj1) throws IOException, InvalidFormatException
	{
		  Workbook workbookObj1 = WorkbookFactory.create(new File(fileName1));
	      Sheet sheet1 = workbookObj1.getSheet(sheetName1);
	      for (int emailIndex = 0; emailIndex <= sheet1.getLastRowNum(); emailIndex++) {
	        	 Row row = sheet1.getRow(emailIndex);
	        	  if (row != null) {
	        	    Cell cell = row.getCell(0);
	        	    Cell objProperty = row.getCell(1);
	        	    if (cell != null) {
	        	      // Found column and there is value in the cell.
	        	    	dictObj1.put(cell.getStringCellValue(), objProperty.getStringCellValue());
	        	    }
	        	}
	        }
	        workbookObj1.close();
			return dictObj1;
	}
	
	public ArrayList<String> datareading(ArrayList<String> list2 , String sheetName) throws IOException
	{
		//org.apache.poi.ss.usermodel.Workbook tempWB = null;
		Workbook workbookObj = null;
		InputStream inp = new FileInputStream(fileName);
		BufferedInputStream bufferStream = new BufferedInputStream(inp);
		ZipSecureFile.setMinInflateRatio(0);
		try {
			if(fileName.contains(".xlsx")){
				workbookObj = WorkbookFactory.create(bufferStream);
			}
			else{				
				workbookObj = WorkbookFactory.create(new POIFSFileSystem(bufferStream));
				//tempWB = (org.apache.poi.ss.usermodel.Workbook) new HSSFWorkbook(new POIFSFileSystem(inp));					
			}
			//org.apache.poi.ss.usermodel.Sheet sheet = tempWB.getSheet(sheetName);
			Sheet sheet = workbookObj.getSheet(sheetName);
			list2.add(0, sheetName);
			Row row = sheet.getRow(0);
			int totalNoOfCols = row.getLastCellNum();
			for( int i=1; i<=totalNoOfCols-1;i++)
			{
				Cell value =sheet.getRow(0).getCell(i);
				String values;
				if(value==null) {
					values="";
				}
				else {
					 values= value.toString();
				}
				
				list2.add(i, values);
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		inp.close();
		workbookObj.close();
		return list2;
	}
	
	/** This method used to read the excel data and in order to pick the Program ID
	 * @param fileName - Where the file located in project
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 * @throws EncryptedDocumentException 
	*/
	public static String readProgramID() throws EncryptedDocumentException, InvalidFormatException, IOException
    {
		String fileReadProgramID = s + "\\Regression_Suite.xlsx";
		String programID = "";
        Workbook workbookObj = WorkbookFactory.create(new File(fileReadProgramID));
    	Sheet sheet = workbookObj.getSheet("RegressionSuite");
    	for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++)
    	{
        	Row row = sheet.getRow(rowIndex);
        	Cell scenarioName = row.getCell(0);
        	if (Baseclass.getTestCaseName().contains(scenarioName.getStringCellValue())) {
        		programID = row.getCell(1).getStringCellValue();
        		break;
        	}
        }
    	workbookObj.close();
    	return programID;
      }
	
	
	public ArrayList<String> dataReadingEvnCompare(ArrayList<String> list2 , String envFileName, String sheetName) throws IOException
	{
		Workbook workbook = null;
		try {
			if(fileName.contains(".xlsx")){
				FileInputStream inputStream = new FileInputStream(new File(envFileName));
		        workbook = WorkbookFactory.create(inputStream);
			}
			else{				
				FileInputStream inputStream = new FileInputStream(new File(envFileName));
		        workbook = WorkbookFactory.create(inputStream);			
			}
			Sheet sheet = workbook.getSheet(sheetName);
			list2.add(0, sheetName);
			Row row = sheet.getRow(0);
			int totalNoOfCols = row.getLastCellNum();
			for( int i=1; i<=totalNoOfCols-1;i++)
			{
				Cell value =sheet.getRow(0).getCell(i);
				String values= value.toString();
				list2.add(i, values);
			}
		}
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		workbook.close();
		return list2;
	}
	
	
	public String dataValuereading (String sheetName, int tcRow ,int columncount ) throws IOException
	{ 	
		Cell data = null;
		String value = null;
		Workbook workbook = null;
		try {
			FileInputStream inputStream = new FileInputStream(new File(fileName));
			BufferedInputStream bufferStream = new BufferedInputStream(inputStream);
	        workbook = WorkbookFactory.create(bufferStream);
	        Sheet sheet = workbook.getSheet(sheetName);
//			Cell cell =sheet.getRow(tcRow).getCell(columncount);
			
			if(columncount<0 || sheet.getRow(tcRow).getCell(columncount)==null) {
				value="";
			}
			else {
				data=sheet.getRow(tcRow).getCell(columncount);
				value=data.toString();
			}
			bufferStream.close();
			inputStream.close();
			workbook.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		return value;
	}
	
	@Test
	public String testCaseValueReading (String sheetName) throws NumberFormatException
    {      
           int k=0;
           String rowNumber = "";
           String columnvalue="";
           int pointer=0;
           try 
           {
           FileInputStream inputStream = new FileInputStream(new File(fileName));
           Workbook workbook = WorkbookFactory.create(inputStream);
           Sheet sheet = workbook.getSheet(sheetName);
           ArrayList<String> columndata =new ArrayList<>();
           int row =sheet.getLastRowNum();
           for(int i=0 ;i<=row; i++)
           {      
                  Cell value= sheet.getRow(i).getCell(1);
                  if(value == null || value.toString().trim().isEmpty()) {
                	  columndata.add(i , "");
                  } else {
                  columnvalue=value.toString().trim();
                  columndata.add(i , columnvalue);
                  }
           }
           int arraylistcount = columndata.size();
           String tcName = Baseclass.testCaseName;
           while(pointer<arraylistcount)
           { 
	          for(int i=k;i<arraylistcount;i++){          
	        	  if( tcName.equalsIgnoreCase(columndata.get(i)) && i>pointer){
	                  rowNumber = i + "," + columndata.get(i);
	                  break;
	        	  }
	        	  k =i+1;
	          }   
	          pointer = k;
           }
           workbook.close();
       }catch(Exception e) {
              e.printStackTrace();
       }
       return rowNumber;
    }

	
	public String testCaseRowNumberEnvCompare (String fileEnvName, String sheetName, String testCaseName) throws NumberFormatException, IOException
    {      
		  int k=0;
          String rowNumber = "";
          String columnvalue="";
          int pointer=0;
          Workbook workbook = null;
          try 
          {
          FileInputStream inputStream = new FileInputStream(new File(fileName));
          workbook = WorkbookFactory.create(inputStream);
          Sheet sheet = workbook.getSheet(sheetName);
          ArrayList<String> columndata =new ArrayList<>();
          
          int row =sheet.getLastRowNum();
          for(int i=0 ;i<=row; i++)
          {      
                 Cell value= sheet.getRow(i).getCell(1);
                 columnvalue=value.toString();
                 columndata.add(i , columnvalue);
          }
          int arraylistcount = columndata.size();
          
          String tcName = testCaseName;
          while(pointer<arraylistcount)
          { 
	          for(int i=k;i<arraylistcount;i++){          
	        	  if( tcName.contains(columndata.get(i)) && i>pointer){
	                  rowNumber = rowNumber + k + ",";
	                  break;
	        	  }
	        	  k =i+1;
	          }   
     pointer = k;
          }
          workbook.close();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
      return rowNumber;
   }

	public String getestScenarioNames () throws IOException  {
		String regressionScenarioNames1 = "";
		String scenarioFileName = s+"\\Regression_Suite.xlsx";
		org.apache.poi.ss.usermodel.Workbook tempRS = null;
		try {
			tempRS = new XSSFWorkbook(scenarioFileName);
			org.apache.poi.ss.usermodel.Sheet sheet = tempRS.getSheet("Email");
			Row row = sheet.getRow(0);
			regressionScenarioNames1 = String.valueOf(row.getCell(14));
			tempRS.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return regressionScenarioNames1;
	}

	
	public void putCompareFilePathToExcel(String compareFilePath) throws EncryptedDocumentException, InvalidFormatException  {
		
		String scenarioFileName = System.getProperty("user.dir") +"\\Regression_Suite.xlsx";
		try {
	        FileInputStream inputStream = new FileInputStream(new File(scenarioFileName));
	        Workbook workbook = WorkbookFactory.create(inputStream);
	        Sheet sheet = workbook.getSheet("Email");
	        Cell cell = sheet.getRow(0).createCell(15);
	        cell.setCellValue(compareFilePath);
			
	        inputStream.close();
			FileOutputStream outputStream = new FileOutputStream(scenarioFileName);
	        workbook.write(outputStream);
	        workbook.close();
	        outputStream.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean setCellData(int tcRow , int columncount, String data){
		
		try {
			String excelFile = properties.getPropValues("envCompare");
			
			FileInputStream inputStream = new FileInputStream(new File(excelFile));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheet("");
            
            sheet.createRow(tcRow).createCell(columncount).setCellValue(data);
			
			FileOutputStream outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
  	
	
	public void makeCopyOfComparisonTemplate(String templateName, String copyFileName) throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		FileInputStream inputStream = new FileInputStream(new File(templateName));
        Workbook workbook = WorkbookFactory.create(inputStream);
        inputStream.close();
        
		FileOutputStream outputStream = new FileOutputStream(copyFileName);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
	}
	
	
	public String getLatestFilefromDir(String dirPath){
	    File dir = new File(dirPath);
	    File[] files = dir.listFiles();
	    if (files == null || files.length == 0) {
	        return null;
	    }

	    File lastModifiedFile = files[0];
	    for (int i = 1; i < files.length; i++) {
	       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
	           lastModifiedFile = files[i];
	       }
	    }
	    return lastModifiedFile.toString();
	}

	public void convertPdfToWordDocument(String transactionName) throws Exception
	{
		//Get the 2 files from result folder
       	File dir = new File(properties.getPropValues("subFolderName"));
        File[] dir_contents = dir.listFiles();
        int counter = 0;
        String temp1 = "";
        for(int i = 0; i<dir_contents.length;i++) {
        	int indexNumber = dir_contents[i].getName().indexOf(transactionName);
            if(indexNumber >= 0) {
            	counter = counter + 1;
            	temp1 = temp1 + properties.getPropValues("subFolderName") + "\\" + dir_contents[i].getName() + "#";
            } 
        }
        if (temp1.contains(".pdf")) {
	        //Write these names in to Spreadsheet
	        putCompareFilePathToExcel(temp1);
	        Thread.sleep(3000);
	        //Call VBS file to convert PDF to Word Document
	        String script = s+"\\Reports\\PDF To Word Conversion.vbs";
	  	    // search for real path:
	  	    String executable = "C:\\Windows\\System32\\wscript.exe"; 
	  	    String cmdArr [] = {executable, script};
	  	    Runtime.getRuntime ().exec (cmdArr);
	  	    Thread.sleep(5000); 
        }
	}
	
	//Get Status of Document Comparison
	public String getDocumentComparisonStatus () throws IOException  {
		//Kill the Process 
		//Call VBS script for Killing the Process
        String script = s+"\\Reports\\Kill Process.vbs";
        // search for real path:
        String executable = "C:\\Windows\\System32\\wscript.exe"; 
        String cmdArr [] = {executable, script};
        Runtime.getRuntime ().exec (cmdArr);
        
		String regressionScenarioNames1 = "";
		String scenarioFileName = s+"\\Regression_Suite.xlsx";
		org.apache.poi.ss.usermodel.Workbook tempRS;
		try {
			tempRS = new XSSFWorkbook(scenarioFileName);
			org.apache.poi.ss.usermodel.Sheet sheet = tempRS.getSheet("Email");
			Row row = sheet.getRow(0);
			regressionScenarioNames1 = String.valueOf(row.getCell(16));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return regressionScenarioNames1;
	}

		//get data count
		public String gridDataCountValidation(String fileName, String colName, ExtentTest logger) throws IOException
		{  
			
			String transTypeValue= "";
			 try {
				   Thread.sleep(2000);
		           File inputfile = new File(fileName);
		           DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		           DocumentBuilder docbuilder = dbFactory.newDocumentBuilder();
		           org.w3c.dom.Document doc = docbuilder.parse(inputfile);
		           doc.getDocumentElement().normalize();
		           NodeList monthlyPremium = doc.getElementsByTagName("P241Transaction");
		                for (int tmp = 0; tmp < monthlyPremium.getLength(); tmp++) {
			                Node nNode = monthlyPremium.item(tmp);
			                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			                    Element eElement = (Element) nNode;
			                    NodeList nl = eElement.getElementsByTagName(colName);
			                    if (nl.getLength() > 0) {
			                    	String transTypeValues = nl.item(0).getTextContent();
			                    	transTypeValue =transTypeValue+transTypeValues+",";
			                    }
			                    else {
			                    	transTypeValue =transTypeValue+null+",";
			                    }
			                }
		                } 
					}
			 catch (Exception e) {
		            e.getMessage();
		            e.printStackTrace();
		        }
			 return transTypeValue ;
		}
		public static GenericMethods getInstanceOfSingletonClass() {
			return null;
		}
		public String getCurrentSheet() {
			// TODO Auto-generated method stub
			return null;
		}		
}	
