package com.eg.CW.Testframe2;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class C1 {
    public WebDriver driver;
    public ExtentReports reports;
    public Logger logger = Logger.getLogger(getClass());

    @DataProvider(name = "input")
    public String[] input() throws Exception
    {
        FileInputStream ip = new FileInputStream(new File("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Testframe2\\Excel.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(ip);
        Sheet sh = wb.getSheetAt(0);
        String str[] = new String[1];
        str[0] = sh.getRow(0).getCell(0).getStringCellValue();
        wb.close();
        return str;
    }

    @BeforeTest
    public void beforeTest()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        ExtentSparkReporter spark = new ExtentSparkReporter("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Testframe2\\report.html");
        reports = new ExtentReports();
        reports.attachReporter(spark);
        PropertyConfigurator.configure("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Testframe2\\log4j2.properties");
    }
 
    @BeforeMethod
    public void beforeMethod()
    {
        driver.get("https://www.spencers.in");
    }
    
    @Test(dataProvider = "input")
    public void test1(String product) throws Exception
    {
        driver.findElement(By.xpath("//*[@id='html-body']/div[3]/header/div[2]/div[3]")).click();
        driver.findElement(By.xpath("//*[@id=\"search\"]")).sendKeys(product);
        driver.findElement(By.xpath("//*[@id=\"search_mini_form\"]/div[2]/button")).click();
        Thread.sleep(5000);
        logger.info("Navigation to the results page");
        driver.findElement(By.xpath("//*[@id=\"narrow-by-list\"]/div[1]/div[2]/button")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"narrow-by-list\"]/div[1]/div[2]/form/ul/li[6]/a")).click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"addtocart-149448\"]/button")).click();
        logger.info("Product added to the cart");
        Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"html-body\"]/div[3]/header/div[2]/div[2]/div[1]/a")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"minicart-content-wrapper\"]/div[2]/div[4]/div/a")).click();
        Thread.sleep(3000);
        
        TakesScreenshot scr = (TakesScreenshot) driver;
        FileUtils.copyFile(scr.getScreenshotAs(OutputType.FILE) ,new File("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Testframe2\\img1.png"));
        
        ExtentTest t1 = reports.createTest("Test 1", "Validation of cart item").addScreenCaptureFromPath("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Testframe2\\img1.png"); 
        if(driver.findElement(By.xpath("//*[@id=\"maincontent\"]/div[1]/h1/span")).getText().equals("My Cart (1 Item)"))
        {
            t1.log(Status.PASS, "Successfully added one item to the cart");
            Assert.assertTrue(true);
        }
        else
        {
            t1.log(Status.FAIL, "Didn't add one item to the cart");
            Assert.assertTrue(false);
        }
    }
    
    @Test
    public void test2() throws Exception
    {
        driver.findElement(By.xpath("//*[@id=\"ui-id-2\"]")).click();
        Thread.sleep(2000);
        logger.info("Navigation to groceries page");
        driver.findElement(By.xpath("//*[@id=\"ui-id-20\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"narrow-by-list\"]/div[1]/div[2]/form/ul/li[2]")).click();
        Thread.sleep(2000);
        logger.info("Navigation to edible oil page");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,0)", "");
        
        TakesScreenshot scr = (TakesScreenshot) driver;
        FileUtils.copyFile(scr.getScreenshotAs(OutputType.FILE) ,new File("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Testframe2\\img2.png"));
        
        ExtentTest t2 = reports.createTest("Test 2", "Validation of result page of selected category").addScreenCaptureFromPath("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Testframe2\\img2.png"); 
        if(driver.findElement(By.xpath("//*[@id=\"am-shopby-container\"]/ol/li[1]/div")).getText().equals("Edible Oil"))
        {
            t2.log(Status.PASS, "Successfully navigation to correct category");
            Assert.assertTrue(true);
        }
        else
        {
            t2.log(Status.FAIL, "Didn't navigate to correct category");
            Assert.assertTrue(false);
        }
    }
    
    @Test
    public void test3() throws Exception
    {
        driver.findElement(By.xpath("//*[@id=\"html-body\"]/div[3]/header/div[2]/div[2]/div[3]/div[1]/div[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"html-body\"]/div[3]/header/div[2]/div[2]/div[3]/div[1]/div[2]/div/div[2]/div[5]/div/div/a")).click();
        Thread.sleep(2000);
        logger.info("Navigation to login page");
        driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("testing@gmail.com");
        driver.findElement(By.xpath("//*[@id=\"pass\"]")).sendKeys("Example20@");
        logger.info("Entered wrong details");
        Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"bnt-social-login-authentication\"]")).click();
        Thread.sleep(10000);
        while(driver.findElement(By.xpath("//*[@id=\"social-login-authentication\"]/div/div/div")).getText().equals("Robot verification failed, please try again.") || driver.findElement(By.xpath("//*[@id=\"social-login-authentication\"]/div/div/div")).getText().equals("Could not authenticate. Please try again later"))
        {
            driver.findElement(By.xpath("//*[@id=\"bnt-social-login-authentication\"]")).click();
            Thread.sleep(10000);
        }
        
        TakesScreenshot scr = (TakesScreenshot) driver;
        FileUtils.copyFile(scr.getScreenshotAs(OutputType.FILE) ,new File("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Testframe2\\img3.png"));
        
        ExtentTest t2 = reports.createTest("Test 3", "Validation of invalid login").addScreenCaptureFromPath("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Testframe2\\img3.png"); 
        if(driver.findElement(By.xpath("//*[@id=\"social-login-authentication\"]/div/div/div")).getText().equals("Invalid username or password."))
        {
            t2.log(Status.PASS, "Successfully display of error message");
            Assert.assertTrue(true);
        }
        else
        {
            t2.log(Status.FAIL, "Didn't display error message");
            Assert.assertTrue(false);
        }
        logger.info("Error message");
    }
    
    @AfterMethod
    public void afterMethod() throws Exception
    {
        reports.flush();
        Thread.sleep(10000);
    }

    @AfterTest
    public void afterTest()
    {
        driver.close();
    }
}
