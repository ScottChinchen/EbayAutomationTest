package ebayATE;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import ebayATE.TestLogger.LogLevel;
import ebayATE.pages.CommonPageMethods;
import ebayATE.pages.SignInPage;


public class TestController {
	
	public static enum SelectedBrowser {
		Firefox, Chrome
	}
	private SelectedBrowser sb = SelectedBrowser.Firefox;
	private WebDriver driver;
	private int PassedTests = 0;
	private int FailedTests = 0;
	
	public void resetTestTracking() {
		PassedTests = 0;
		FailedTests = 0;
	}
	
	public int getPassedTests() {
		return PassedTests;
	}
	
	public int getFailedTests() {
		return FailedTests;
	}
	
	public void setBrowserType(SelectedBrowser newsb) {
		sb = newsb;
	}
	
	private void openWebDriver() {
		switch (sb) {
		case Chrome:
			driver = new ChromeDriver();
			break;
		case Firefox:
		default:
			// Disable detailed Geckodriver logging as it makes the log hard to read
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");
			driver = new FirefoxDriver();
			break;
		}
	}
	
	private void closeWebDriver() {
		driver.quit();
	}
	
	public void runAllTestsForAllTestData() {
		TestData td = new TestData();
		td.readTestDataFromCSV("./TestData.csv");
		for (int row = 0; row < td.getNumberOfRowsInTestData(); row++) {
			// Select the row of data for the next set of tests
			td.setCurrentRow(row);
			// Run tests
			runAllTestsForSingleTestDataRow(td);
		}
	}
	
	public void runAllTestsForSingleTestDataRow(TestData td) {
		
		TestLogger.log(LogLevel.INFO, "TestController:", "---Starting tests---");
		
		if (testHomePageOpens()) {
			PassedTests++;
			TestLogger.log("Test Passed: Open homepage smoke test");
		}
		else {
			FailedTests++;
		}
		
		String categoryToSelect = td.getTestDataFromCurrentRow(0);
		if (testSelectACategory(categoryToSelect)) {
			PassedTests++;
			TestLogger.log("Test Passed: Select a category");
		}
		else{
			FailedTests++;
		}
		
		if (testSignInPageOpens()) {
			PassedTests++;
			TestLogger.log("Test Passed: Navigate to sign in page");
		}
		else {
			FailedTests++;
		}
		
		String username = td.getTestDataFromCurrentRow(2);
		String password = td.getTestDataFromCurrentRow(3);
		if(testSignIn(username, password)) {
			PassedTests++;
			TestLogger.log("Test Passed: Sign in");
		}
		else {
			FailedTests++;
		}

	}
	
	private Boolean testHomePageOpens() {
		// Setup default test result:
		Boolean result = true;
		String moduleName = "testHomePageOpens";
		
		// Pre:
		openWebDriver();
		
		// Execute:
		try {
			new CommonPageMethods().openHomepage(driver);
		} catch (Exception e) {
			TestLogger.log(LogLevel.ERROR, moduleName, "Test failed with exception:"
					+ System.lineSeparator() + e.toString());
			// Update test result:
			result = false;
		}
		
		// Update test result:
		if (!driver.getTitle().contains("eBay")) {
			TestLogger.log(LogLevel.ERROR, moduleName, "Page doesn't have correct title");
			result = false;
		}
		
		// Post:
		closeWebDriver();
		return result;
	}
	
	private Boolean testSelectACategory(String categoryToSelect) {
		Boolean result = true;
		String moduleName = "testSelectACategory";
		openWebDriver();
		try {
			CommonPageMethods page = new CommonPageMethods();
			page.openHomepage(driver);
			page.selectCategory(driver, categoryToSelect);
		} catch (Exception e) {
			TestLogger.log(LogLevel.ERROR, moduleName, "Test failed with exception:"
					+ System.lineSeparator() + e.toString());
			result = false;
		}
		if (!driver.getTitle().toLowerCase().contains(categoryToSelect.toLowerCase())) {
			TestLogger.log(LogLevel.ERROR, moduleName, "Page doesn't have correct title");
			result = false;
		}
		closeWebDriver();
		return result;
	}
	
	private Boolean testSignInPageOpens() {
		// Setup default test result:
		Boolean result = true;
		String moduleName = "testSignInPageOpens";
		
		// Pre:
		openWebDriver();
		
		// Execute:
		try {
			CommonPageMethods page = new CommonPageMethods();
			page.openHomepage(driver);
			page.navigateToSignInPage(driver);
			
		} catch (Exception e) {
			TestLogger.log(LogLevel.ERROR, moduleName, "Test failed with exception:"
					+ System.lineSeparator() + e.toString());
			// Update test result:
			result = false;
		}
		
		// Update test result:
		if (!driver.getTitle().contains("Sign in")) {
			TestLogger.log(LogLevel.ERROR, moduleName, "Page doesn't have correct title");
			result = false;
		}
		
		// Post:
		closeWebDriver();
		return result;
	}
	
	//TODO: Add validation that sign in succeeded once a valid login has been setup
	private Boolean testSignIn(String username, String password) {
		// Setup default test result:
		Boolean result = true;
		String moduleName = "testSignInPageOpens";
		
		// Pre:
		openWebDriver();
		
		// Execute:
		try {
			CommonPageMethods page = new CommonPageMethods();
			page.openHomepage(driver);
			page.navigateToSignInPage(driver);
			SignInPage sipage = new SignInPage();
			sipage.enterUsername(driver, username);
			sipage.enterPassword(driver, password);
			sipage.clickSignInButton(driver);
		} catch (Exception e) {
			TestLogger.log(LogLevel.ERROR, moduleName, "Test failed with exception:"
					+ System.lineSeparator() + e.toString());
			// Update test result:
			result = false;
		}
		
		// Update test result:
		//TODO: Add validation that sign in succeeded once a valid login has been setup
		/*if (!driver.getTitle().contains("Sign in")) {
			TestLogger.log(LogLevel.ERROR, moduleName, "Page doesn't have correct title");
			result = false;
		}*/
		if(true) {
			TestLogger.log(LogLevel.ERROR, moduleName, "Test failed as no sign in account has been created yet");
			result = false;
		}
		
		// Post:
		closeWebDriver();
		return result;
	}
	
}
