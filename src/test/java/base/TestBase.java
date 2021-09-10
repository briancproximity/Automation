package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase 
{
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("Log: ");	
	public static WebDriverWait wait;
	public static String browser;

	@BeforeSuite
	public void setUp() 
	{
		if (driver == null) 
		{
			try 
			{
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			} 
			catch (FileNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try 
			{
				config.load(fis);
				log.debug("Config file loaded.");
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try 
			{
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			} 
			catch (FileNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try 
			{
				OR.load(fis);
				log.debug("Object Relations file loaded.");
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(System.getenv("browser")!=null && !System.getenv("browser").isEmpty())
			{				
				browser = System.getenv("browser");
			}
			else
			{				
				browser = config.getProperty("browser");				
			}
			
			config.setProperty("browser", browser);		

			if (config.getProperty("browser").equals("firefox")) 
			{
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();

			} 
			else if (config.getProperty("browser").equals("chrome")) 
			{
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				log.debug("Chrome Launched.");
			} 
			else if (config.getProperty("browser").equals("ie")) 
			{
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}

			driver.get(config.getProperty("testsiteurl"));
			log.debug("Navigated to : " + config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 5);
		}
	}

	public void click(String locator) 
	{
		if (locator.endsWith("_CSS")) 
		{
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		} 
		else if (locator.endsWith("_XPATH")) 
		{
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		} 
		else if (locator.endsWith("_ID")) 
		{
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
	}
	
	public void mouseHover(String locator) 
	{
		Actions action = new Actions(driver);
	
		if (locator.endsWith("_CSS")) 
		{
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		} 
		else if (locator.endsWith("_XPATH")) 
		{
			WebElement item = driver.findElement(By.xpath(OR.getProperty(locator)));
			action.moveToElement(item).perform();
		} 
		else if (locator.endsWith("_ID")) 
		{
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
	}	

	public void type(String locator, String value) 
	{
		if (locator.endsWith("_CSS")) 
		{
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		} 
		else if (locator.endsWith("_XPATH")) 
		{
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		} 
		else if (locator.endsWith("_ID")) 
		{
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}
	}
		
	public boolean isElementPresent(By by) 
	{
		try 
		{
			driver.findElement(by);
			return true;
		} 
		catch (NoSuchElementException e) 
		{
			return false;
		}
	}	

	@AfterSuite
	public void tearDown() 
	{
		if (driver != null) 
		{
			driver.quit();
		}
		log.debug("Test execution completed.");
	}
}
