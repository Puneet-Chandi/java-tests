package tests;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

//import reusableComponents.PropertiesOperations;

public class ExtentReportListener implements ITestListener{
	
	static ExtentSparkReporter sparkReporter; // UI of the report public ExtentReports extent; //populate common info of the report
	static ExtentReports extent;
	static ExtentTest test; // creating test case entries in the report and update status of the test metho
	
	//================== ITestListener Methods ================================================
			public void onStart(ITestContext context) {	
				
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy HH-mm-ss");
				Date date = new Date();
				String actualDate = format.format(date);		
				sparkReporter=new ExtentSparkReporter (System.getProperty ("user.dir") + "/reports/TestReport_"+actualDate+".html");
				sparkReporter.config().setDocumentTitle("Automation Test Report"); 
				sparkReporter.config().setReportName("Functional Testing"); 
				sparkReporter.config().setTheme(Theme.DARK); 
				// Title of report sparkReporter.config() setReportName("Functional Testing"); // Name of the report sparkReporter.config() -set Theme (Theme.STANDARD);
				
				extent = new ExtentReports();
				extent.attachReporter(sparkReporter);			
	/*			extent.setSystemInfo("Host Name", "localhost");
				try {
					extent.setSystemInfo("Environment", PropertiesOperations.getPropertyValueByKey("url"));
					extent.setSystemInfo("Browser name",PropertiesOperations.getPropertyValueByKey("browser"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				extent.setSystemInfo("Tester Name", System.getProperty("user.name"));
				extent.setSystemInfo("OS", System.getProperty("os.name"));
		*/		
				
			}
			
			public void onTestSuccess(ITestResult result) {
				test = extent.createTest(result.getName());
				test.log(Status.PASS, "Test case Passed is: "+result.getName());
			}
			public void onTestFailure(ITestResult result) {
				test = extent.createTest(result.getName());
				test.log(Status.FAIL, "Test case Failed is: "+result.getName());
				test.log(Status.FAIL, "Test case Failed cause is: "+result.getThrowable());
			}
			public void onTestSkipped(ITestResult result) {
				test = extent.createTest(result.getName());
				test.log(Status.SKIP, "Test case skipped is: "+result.getName());				
			}
			public void onFinish(ITestContext context) {
				extent.flush();
			}
			
}
