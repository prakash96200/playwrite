package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import stepDefinition.Baseclass;



public class EmailConfiguration {
	
	AppConfiguration properties = new AppConfiguration();
	GenericMethods objGM = new GenericMethods();
	public static Map<String, String> dictObj1 = new HashMap<String, String>();
	
	public void sendResultMail(int sPass, int sFail, String startTime, String endTime,String status) throws IOException, InvalidFormatException, InterruptedException
		{
	      String resultLink=properties.getPropValues("projectPath") + "\\Reports\\Report"
					+ Baseclass.folderName + "\\" + Baseclass.fileNameCompare + "\\ESurety Automation Report.html";
//			String resultLink = System.getProperty("user.dir")  + properties.getPropValues("EmailResultPath").replace("./", "\\");
	        String envCompare = System.getProperty("user.dir")  + properties.getPropValues("compareRegressionData").replace("./", "\\");
//	        String formCompare = System.getProperty("user.dir")  + properties.getPropValues("formsComparisonResult").replace("./", "\\");
	        String formCompare =properties.getPropValues("projectPath") + "\\Reports\\Report"
					+ Baseclass.folderName + "\\" + Baseclass.fileNameCompare + "\\FormCompare"+Baseclass.fileNameCompare+".xlsx";
	        String scenarioFileName = System.getProperty("user.dir") +"\\Regression_Suite.xlsx";
			
			try {
		        FileInputStream inputStream = new FileInputStream(new File(scenarioFileName));
//		        Workbook workbook = WorkbookFactory.create(inputStream);
		        Workbook workbook = new XSSFWorkbook(inputStream);
		        Sheet sheet = workbook.getSheet("Email");
		        Cell cell17 = sheet.getRow(0).createCell(17);
		        cell17.setCellValue(resultLink);
		       
		        Cell cell19 = sheet.getRow(0).createCell(19);
		        cell19.setCellValue(sPass);
		        Cell cell20 = sheet.getRow(0).createCell(20);
		        cell20.setCellValue(sFail);
		        Cell cell21 = sheet.getRow(0).createCell(21);
		        cell21.setCellValue(startTime);
		        Cell cell22 = sheet.getRow(0).createCell(22);
		        cell22.setCellValue(endTime);
		        int total=sPass+sFail;
		        System.out.println(total);
		        Cell cell25 = sheet.getRow(0).createCell(25);
		        cell25.setCellValue(total);
		     
		        Cell cell27=sheet.getRow(0).createCell(27);
		        cell27.setCellValue(status);
		        
//		        String datasheetPath=System.getProperty("user.dir")+"\\DataRepository\\NB Submission Data.xlsx";\
//		        String datasheetPath=Baseclass.attachSheet;
				Cell cell28=sheet.getRow(0).createCell(28);
//			    cell28.setCellValue(datasheetPath);	
		        
		        inputStream.close();
		        Thread.sleep(10000);
				FileOutputStream outputStream = new FileOutputStream(scenarioFileName);
		        workbook.write(outputStream);
		        workbook.close();
		        outputStream.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
         //Execute the Email Configuration .VBS File
			String script = System.getProperty("user.dir") +"\\Reports\\Email Configuration.vbs";
	  	    // search for real path:
	  	    String executable = "C:\\Windows\\System32\\wscript.exe"; 
	  	    String cmdArr [] = {executable, script};
	  	    Runtime.getRuntime ().exec (cmdArr);
	        
		}   


	public void sendResultMailAttachment(int sPass, int sFail, String startTime, String endTime,String status) throws IOException, InvalidFormatException, InterruptedException
	{
      String resultLink=properties.getPropValues("projectPath") + "\\Reports\\Report"
				+ Baseclass.folderName + "\\" + Baseclass.fileNameCompare + "\\ESurety Automation Report.html";
//		String resultLink = System.getProperty("user.dir")  + properties.getPropValues("EmailResultPath").replace("./", "\\");
        String envCompare = System.getProperty("user.dir")  + properties.getPropValues("compareRegressionData").replace("./", "\\");
//        String formCompare = System.getProperty("user.dir")  + properties.getPropValues("formsComparisonResult").replace("./", "\\");
        String formCompare =properties.getPropValues("projectPath") + "\\Reports\\Report"
				+ Baseclass.folderName + "\\" + Baseclass.fileNameCompare + "\\FormCompare"+Baseclass.fileNameCompare+".xlsx";
        String scenarioFileName = System.getProperty("user.dir") +"\\Regression_Suite.xlsx";
		
		try {
	        FileInputStream inputStream = new FileInputStream(new File(scenarioFileName));
//	        Workbook workbook = WorkbookFactory.create(inputStream);
	        Workbook workbook = new XSSFWorkbook(inputStream);
	        Sheet sheet = workbook.getSheet("Email");
	        Cell cell17 = sheet.getRow(0).createCell(17);
	        cell17.setCellValue(resultLink);
	       
	        Cell cell19 = sheet.getRow(0).createCell(19);
	        cell19.setCellValue(sPass);
	        Cell cell20 = sheet.getRow(0).createCell(20);
	        cell20.setCellValue(sFail);
	        Cell cell21 = sheet.getRow(0).createCell(21);
	        cell21.setCellValue(startTime);
	        Cell cell22 = sheet.getRow(0).createCell(22);
	        cell22.setCellValue(endTime);
	        int total=sPass+sFail;
	        System.out.println(total);
	        Cell cell25 = sheet.getRow(0).createCell(25);
	        cell25.setCellValue(total);
	     
	        Cell cell27=sheet.getRow(0).createCell(27);
	        cell27.setCellValue(status);
	        
//	        String datasheetPath=System.getProperty("user.dir")+"\\DataRepository\\NB Submission Data.xlsx";\
//	        String datasheetPath=Baseclass.attachSheet;
			Cell cell28=sheet.getRow(0).createCell(28);
//		    cell28.setCellValue(datasheetPath);	
	        
	        inputStream.close();
	        Thread.sleep(10000);
			FileOutputStream outputStream = new FileOutputStream(scenarioFileName);
	        workbook.write(outputStream);
	        workbook.close();
	        outputStream.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
     //Execute the Email Configuration .VBS File
		String script = System.getProperty("user.dir") +"\\Reports\\Email Configuration attachment.vbs";
  	    // search for real path:
  	    String executable = "C:\\Windows\\System32\\wscript.exe"; 
  	    String cmdArr [] = {executable, script};
  	    Runtime.getRuntime ().exec (cmdArr);
        
	}   


}



 






