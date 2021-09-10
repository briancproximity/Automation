package testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.TestBase;

public class ItemOrder extends TestBase 
{	
	@Test()
	public void ecotrakLoginTest() throws InterruptedException
	{			
		Thread.sleep(3000);
		log.debug("Index page loaded.");
		Assert.assertTrue(isElementPresent(By.xpath(OR.getProperty("item001_XPATH"))),"Item001 is present.");
		mouseHover("item001_Hover_XPATH");
		Thread.sleep(300);
		click("item001_XPATH");
		Thread.sleep(3000);
		Assert.assertTrue(isElementPresent(By.xpath(OR.getProperty("item001_AddtoCart_XPATH"))),"Item001 Add to Cart is present.");
		click("item001_AddtoCart_XPATH");
		Thread.sleep(5000);
		
		log.debug("ItemOrder test executed successfully.");
			
	}	
}
