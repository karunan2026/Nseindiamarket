package qasdet;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.annotations.BeforeMethod;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import org.apache.commons.io.FileUtils;
import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterTest;

public class Dynamicnifty {
	static WebDriver driver;
	static int i = 0;
	ExtentReports extent;
	ExtentTest test;	
	
	@BeforeTest
	public void setupReport() {
		//tss.takeScreenshot(driver, "nseindia");
		
		System.out.println("Report creation initiated");
		// ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("niftyStatusReport.html");
		/*
		 * ExtentSparkReporter spark = new
		 * ExtentSparkReporter("niftyStatusReport.html"); extent = new ExtentReports();
		 * extent.attachReporter(spark);
		 */
		String reportPath = System.getProperty("user.dir") + "\\test-output\\niftyStatusReport.html";
		
		System.out.println(reportPath);
        // Initialize SparkReporter with that path
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

        // Optional: customize the report
        sparkReporter.config().setReportName("niftyStatusReport Report");
        sparkReporter.config().setDocumentTitle("niftyStatusReport Results");

        // Create ExtentReports and attach reporter
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Add system info (optional)
        extent.setSystemInfo("Tester", "Karunanidhi Ethiraj");

        // Start a test
        ExtentTest test = extent.createTest("niftyStatusReport Test");
        test.pass("Test passed!");

        // Flush the report
        extent.flush();
		
		
		
		
		
	}

	@BeforeMethod
	public void setup() {
		

		System.setProperty("webdriver.chrome.driver", "D:\\Softwares\\chromedriver-win64\\chromedriver.exe");
				driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		
		
	}

	@Test
	public void fetchNiftyValue() {
		String stockSymbol = "tatamotors";

        //WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        ScreenshotUtil.takeScreenshot(driver, stockSymbol);

        try {
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            driver.get("https://www.nseindia.com");
            ScreenshotUtil.takeScreenshot(driver, stockSymbol);

            ExtentTest test = extent.createTest("Browser launched ");
            test.pass("Test passed!");
            
            Thread.sleep(5000);

            WebElement searchBox = driver.findElement(By.xpath("//*[@id='layout']/header/div[3]/div[1]/div/div/div[2]/div/div/div/input[1]"));
            searchBox.sendKeys(stockSymbol);
            Thread.sleep(2000); 
            searchBox.sendKeys(Keys.ENTER);
            ScreenshotUtil.takeScreenshot(driver, stockSymbol);          
            Thread.sleep(5000);           
            WebElement priceElement = driver.findElement(By.xpath("//*[@id='layout']/header/div[3]/div[1]/div/div/div[2]/div/div/div/input[1]"));
            System.out.println(stockSymbol.toUpperCase() + " current price: " + priceElement.getText());
            
         // ✅ Get 52-week High
            WebElement high52 = driver.findElement(By.xpath("//div[@id='high52']//span[@class='value']"));
            String high = high52.getText();

            // ✅ Get 52-week Low
            WebElement low52 = driver.findElement(By.xpath("//div[@id='low52']//span[@class='value']"));
            String low = low52.getText();

            // 📤 Print results
            System.out.println("\n Stock: " + stockSymbol.toUpperCase());
            System.out.println("52-Week High: " + high);
            System.out.println("52-Week Low : " + low);
            
            
            

		} catch (Exception e) {
			//test.fail("Failed to fetch NIFTY value: " + e.getMessage());
			 ScreenshotUtil.takeScreenshot(driver, stockSymbol);
			  
		}
	}

	 @AfterMethod
	    public void tearDown(ITestResult result) {
	        if (result.getStatus() == ITestResult.FAILURE) {
	            ScreenshotUtil.takeScreenshot(driver, result.getName());
	        }

	        if (driver != null) {
	            driver.quit();
	        }
	    }
	@AfterTest
	public void tearDownReport() {
		//extent.flush();
	}

	
}
