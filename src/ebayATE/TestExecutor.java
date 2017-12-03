package ebayATE;

import ebayATE.TestController.SelectedBrowser;
import ebayATE.TestLogger.LogLevel;

public class TestExecutor {
	public static void main(String args[]) {
		TestController tc = new TestController();
		
		// Set desired browser to be tested 
		tc.setBrowserType(SelectedBrowser.Firefox);
		//tc.setBrowserType(SelectedBrowser.Chrome);
		
		// Run all tests
		tc.runAllTestsForAllTestData();
		
		// Log final results
		TestLogger.log(LogLevel.INFO, "TestExecutor:", "---Final test results---"
				+ System.lineSeparator() + "Passed: " + tc.getPassedTests()
				+ System.lineSeparator() + "Failed: " + tc.getFailedTests());
	}
}
