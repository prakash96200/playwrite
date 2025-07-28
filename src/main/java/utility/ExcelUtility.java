package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import stepDefinition.Baseclass;

public class ExcelUtility {

    public static void readTestCase() {
        try {
            // Use config from Baseclass
            String filePath = Baseclass.properties.getPropValues("projectPath") + File.separator + "Regression_Suite.xlsx";
            String sheetName = "RegressionSuite";

            FileInputStream file = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new Exception("❌ Sheet '" + sheetName + "' not found in file: " + filePath);
            }

            int lastRowNum = sheet.getLastRowNum();

            int scenarioCol = 0; // "Scenario Name"
            int qaCol = 2;       // "QA"
            int uatCol = 3;      // "UAT"
            int prodCol = 4;     // "PROD"

            for (int i = 1; i <= lastRowNum; i++) {  // start from row 1 assuming headers in row 0
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String scenarioName = getCellValue(row.getCell(scenarioCol));
                String qa = getCellValue(row.getCell(qaCol));
                String uat = getCellValue(row.getCell(uatCol));
                String prod = getCellValue(row.getCell(prodCol));

                if ("Yes".equalsIgnoreCase(qa)) {
                    Baseclass.scenarios.add(scenarioName);
                }
                if ("Yes".equalsIgnoreCase(uat)) {
                    Baseclass.scenarios.add(scenarioName);
                }
                if ("Yes".equalsIgnoreCase(prod)) {
                    Baseclass.scenarios.add(scenarioName);
                }
            }

            workbook.close();
            file.close();
            System.out.println("✅ Excel file loaded: " + filePath + " | Sheet: " + sheetName);
            

        } catch (FileNotFoundException e) {
            System.out.println("❌ Excel file not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error reading Excel: " + e.getMessage());
        }
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case BLANK:
                return "";
            default:
                return "";
        }
    }

	public static String[] getRowData(String sheetPath, String sheetName, int rowIndex) {
		// TODO Auto-generated method stub
		return null;
	}
}
