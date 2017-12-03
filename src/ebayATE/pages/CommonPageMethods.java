package ebayATE.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CommonPageMethods {
	private String homepageURL = "http://www.ebay.com";
	
	public void openHomepage(WebDriver driver) {
		driver.navigate().to(homepageURL);
	}
	
	public void selectCategory(WebDriver driver, String category) {
		WebElement we = driver.findElement(By.id("gh-shop-a"));
		we.click();
		we.findElement(By.xpath("..//*[contains(text(), '" + category + "')]")).click();
	}
	
	public void navigateToSignInPage(WebDriver driver) {
		driver.findElement(By.id("gh-ug")).findElement(By.xpath("..//*[contains(text(), 'Sign in')]")).click();
	}
	
}
