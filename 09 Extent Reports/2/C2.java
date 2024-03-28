package com.eg.CW.Extentreport;

import java.io.FileInputStream;
import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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

public class C2 {
    public WebDriver driver;
    public ExtentReports report;

    @DataProvider(name = "input")
    public Object[][] input() throws Exception
    {
        FileInputStream ip = new FileInputStream("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Extentreport\\Excel(1).xlsx");
        Workbook wb = new XSSFWorkbook(ip);
        Sheet sh = wb.getSheetAt(0);
        int rowCount = sh.getPhysicalNumberOfRows();
        Object[][] arr = new Object[rowCount-1][4];
        for(int i=1;i<rowCount;i++)
        {
            Row r = sh.getRow(i);
            for(int j=0;j<r.getPhysicalNumberOfCells();j++)
            {
                arr[i-1][j] = r.getCell(j).getStringCellValue();
            }
        }
        wb.close();
        return arr;
    }

    @BeforeTest
    public void beforeTest()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        ExtentSparkReporter spark = new ExtentSparkReporter(new File("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Extentreport\\report2.html"));
        report = new ExtentReports();
        report.attachReporter(spark);
    }

    @BeforeMethod
    public void beforeMethod()
    {
        driver.get("https://www.ixigo.com");
    }
    
    @Test(dataProvider = "input")
    public void test1(String from,String to,String departureDate,String returnDate) throws Exception
    {
        driver.findElement(By.xpath("//div[@role=\"tablist\"]/button[2]")).click();
        
        driver.findElement(By.xpath("//div[@class=\"relative flex gap-0.5 flex-1\"]/div[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[2]/div/div/div[2]/input")).sendKeys(from);
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[3]/div")).click();

        driver.findElement(By.xpath("//div[@class=\"relative flex gap-0.5 flex-1\"]/div[2]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[2]/div[2]/div/div/div[2]/input")).sendKeys(to);
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[2]/div[3]/div")).click();
        
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[1]/div")).click();
        Thread.sleep(2000);
        while(!driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[3]/div/div[1]/div[1]/button[2]/span[1]")).getText().contains("November"))
        {
            driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[3]/div/div[1]/div[1]/button[3]")).click();
        }
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[3]/div/div[1]/div[2]/div[1]/div/div/div[2]/button[11]")).click();
        
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[2]/div/div[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[3]/div/div[1]/div[2]/div[1]/div/div/div[2]/button[13]")).click();
        
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[3]/div[2]/div/div[1]/div[1]/div[2]/div/button[2]")).click();
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[3]/div[2]/div/div[1]/div[2]/div[2]/div/button[3]")).click();
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[3]/div[2]/div/div[1]/div[5]/div/div[3]")).click();
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[3]/div[2]/div/div[2]/button")).click();

        ExtentTest test = report.createTest("Test1","Validation of return date");
        TakesScreenshot screen = (TakesScreenshot) driver;
        File src = screen.getScreenshotAs(OutputType.FILE);
        File dest = new File("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Extentreport\\img2(1).png");
        FileUtils.copyFile(src, dest);
        test.addScreenCaptureFromPath("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Extentreport\\img2(1).png");
        if(driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[2]/div/div[1]/div/div/p[2]")).getText().contains("08 Nov"))
        {
            test.log(Status.PASS,"Correct return date");
        }
        else
        {
            test.log(Status.FAIL,"Wrong return date");
            Assert.assertTrue(false);
        }
        Thread.sleep(5000);
    }
    
    @Test
    public void test2() throws Exception
    {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,5000)", "");
        Thread.sleep(3000);
        driver.findElement(By.linkText("About Us")).click();
        Thread.sleep(10000);
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        ExtentTest test = report.createTest("Test2","Validation of navigation to about us page");
        TakesScreenshot screen = (TakesScreenshot) driver;
        File src = screen.getScreenshotAs(OutputType.FILE);
        File dest = new File("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Extentreport\\img2(2).png");
        FileUtils.copyFile(src, dest);
        test.addScreenCaptureFromPath("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Extentreport\\img2(2).png");
        System.out.print(driver.getCurrentUrl());
        if(driver.getCurrentUrl().contains("about"))
        {
            test.log(Status.PASS,"Successful navigation to about us page");
        }
        else
        {
            Assert.assertTrue(false);
            test.log(Status.FAIL,"Failure to navigate to about us page");
        }
        Thread.sleep(5000);
    }
    
    @AfterTest
    public void afterTest()
    {
        report.flush();
        driver.quit();
    }
}