package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager 
{	
	private static ExtentReports extent;	

    public static ExtentReports createInstance(String fileName) 
    {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);       
        
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(fileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(fileName);
        
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Automation Test", "Brian Carrillo");
        extent.setSystemInfo("Organization", "Automation Practice");
        extent.setSystemInfo("Build", "v.001");        
        
        return extent;
    }
}
