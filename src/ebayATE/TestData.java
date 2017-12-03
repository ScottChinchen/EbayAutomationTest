package ebayATE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import ebayATE.TestLogger.LogLevel;

public class TestData {

	private String testDataRaw;
	private int currentCol;
	private int currentRow;
	
	private void addToLog(LogLevel l, String s) {
		TestLogger.log(l, "TestData: " + s);
	}
	
	// Helper function for readTestDataFromCSV
	private String readFromCSV(String strCSVFile) {
		String result = "";
		File fileCSV = new File(strCSVFile);
		if (!(fileCSV.exists())) {
			addToLog(LogLevel.ERROR, "Error reading file");
		}
		else {
			addToLog(LogLevel.INFO, "File exists, starting read");
			try {
				BufferedReader bufRdr = new BufferedReader(new FileReader(fileCSV));
				String line = null;
				while ((line = bufRdr.readLine()) != null) {
					result += line + System.lineSeparator();
				}
				addToLog(LogLevel.INFO, "File reading complete");
				bufRdr.close();
			} catch (Exception e) {
				addToLog(LogLevel.ERROR, "CSVReader: Exception occurred while reading file: " + e.toString());
			}
		}
		return result;
	}

	public void readTestDataFromCSV(String strCSVFile) {
		testDataRaw = readFromCSV(strCSVFile);
	}
	
	public void setCurrentCol(int col) {
		currentCol = col;
	}
	
	public void setCurrentRow(int row) {
		currentRow = row;
	}
	
	public String getTestDataFromCurrentCell() {
		return getCellFromTestData(currentCol, currentRow);
	}
	
	public String getTestDataFromCurrentRow(int col) {
		return getCellFromTestData(col, currentRow);
	}
	
	public String getCellFromTestData(int col, int row) {
		String result = "";
		try {
			String[] testDataRows = testDataRaw.split(System.lineSeparator());
			String[] testDataTargetRow = testDataRows[row].toString().split(",");
			result = testDataTargetRow[col];
		} catch (Exception e) {
			// Most likely exception here is an out of bounds exception
			// Could extend logic to check against max col and max row for a data set to prevent exception from occurring
			TestLogger.log(LogLevel.ERROR, "TestData: Exception while getting cell from raw data: " + e.toString());
		}
		return result;
	}
	
	public int getNumberOfRowsInTestData() {
		if (testDataRaw == null) return 0;
		// Subtract one from count, as there will be an end of line at the end of the file
		return testDataRaw.length() - testDataRaw.replace(System.lineSeparator(), "").length() - 1;
	}
	
	public int getNumberOfColsInTestData() {
		if (testDataRaw == null) return 0;
		String[] testDataRows = testDataRaw.split(System.lineSeparator());
		// Return the number of columns in the first row, as all rows should have the same number of columns
		// Add one to count, as the first record doesn't have a separator before it
		return testDataRows[0].length() - testDataRows[0].replace(",", "").length() + 1;
	}
	
}
