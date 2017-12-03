package ebayATE.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignInPage {

	public void enterUsername(WebDriver driver, String username) {
		WebElement we = driver.findElement(By.id("userid"));
		we.sendKeys(username);
	}
	
	public void enterPassword(WebDriver driver, String password) {
		WebElement we = driver.findElement(By.id("pass"));
		we.sendKeys(password);
	}
	
	public void clickSignInButton(WebDriver driver) {
		driver.findElement(By.id("sgnBt")).click();
	}
	
}
