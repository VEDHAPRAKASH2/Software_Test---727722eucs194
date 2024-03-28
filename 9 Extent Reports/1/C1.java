package com.eg.CW.Extentreport;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
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

    @DataProvider(name = "input")
    public Object[][] input() throws Exception 
    {
        FileInputStream ip = new FileInputStream(new File("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Extentreport\\Excel.xlsx"));
        Workbook wb = new XSSFWorkbook(ip);
        Sheet sh = wb.getSheetAt(0);
        int rows = sh.getPhysicalNumberOfRows();
        Object arr[][] = new Object[rows-1][3];
        for(int i=1;i<rows;i++)
        {
            Row r = sh.getRow(i);
            for(int j=0;j<r.getPhysicalNumberOfCells();j++)
            {
                arr[i-1][j] = (int)r.getCell(j).getNumericCellValue();
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
    }

    @Test(dataProvider = "input")
    public void test(Integer amount,Integer rate,Integer tenure) throws Exception
    {
        ExtentSparkReporter spark = new ExtentSparkReporter("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Extentreport\\report1.html");
        ExtentReports reports = new ExtentReports();
        reports.attachReporter(spark);

        driver.get("https://www.groww.in");

        JavascriptExecutor scroll = (JavascriptExecutor) driver;
        scroll.executeScript("window.scrollBy(0,4000)", "");
        Thread.sleep(3000);
        
        driver.findElement(By.linkText("Calculators")).click();
        Thread.sleep(3000);

        ExtentTest test1 = reports.createTest("Test1","Testing the navigation to calculator page");
        if(driver.findElement(By.className("displayBase")).getText().equals("Calculators"))
        {
            TakesScreenshot img = (TakesScreenshot) driver;
            File src = img.getScreenshotAs(OutputType.FILE);
            File dest = new File("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Extentreport\\img1.png");
            FileUtils.copyFile(src, dest);
            test1.log(Status.PASS, "Successful navigation to calculator page").addScreenCaptureFromPath("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Extentreport\\img1.png");
        }
        else
        {
            test1.log(Status.FAIL, "Failure to navigate to calculator page");
            Assert.assertTrue(false);
        }
        
        scroll.executeScript("window.scrollBy(0,1000)", "");
        Thread.sleep(2000);
        
        driver.findElement(By.xpath("//*[@id='root']/div[2]/div[2]/a[15]")).click();
        Thread.sleep(3000);

        driver.findElement(By.id("LOAN_AMOUNT")).clear();
        Thread.sleep(1000);
        driver.findElement(By.id("LOAN_AMOUNT")).sendKeys(amount.toString());
        Thread.sleep(1000);
        driver.findElement(By.id("RATE_OF_INTEREST")).clear();
        Thread.sleep(1000);
        driver.findElement(By.id("RATE_OF_INTEREST")).sendKeys(rate.toString());
        Thread.sleep(1000);
        driver.findElement(By.id("LOAN_TENURE")).clear();
        Thread.sleep(1000);
        driver.findElement(By.id("LOAN_TENURE")).sendKeys(tenure.toString());
        
        scroll.executeScript("window.scrollBy(0,500)", "");
        ExtentTest test2 = reports.createTest("Test2","Testing the presence of Your Amortization Details (Yearly/Monthly)");
        if(driver.findElement(By.xpath("//p[@class='bodyBaseHeavy']")).getText().equals("Your Amortization Details (Yearly/Monthly)"))
        {
            test2.log(Status.PASS,"The given text is present");
        }
        else
        {
            test2.log(Status.FAIL,"The given text is present");
            Assert.assertTrue(false);
        }

        Thread.sleep(3000);

        reports.flush();
    }

    @AfterTest
    public void afterTest()
    {
        driver.close();
    }
}
