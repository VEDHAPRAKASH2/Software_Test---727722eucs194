package com.eg.CW.Log4j.C2;

import java.io.*;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class C2 {
    public WebDriver driver;
    final Logger logger = Logger.getLogger(getClass());

    @DataProvider(name = "input")
    public String[] input() throws Exception
    {
        FileInputStream ip = new FileInputStream(new File("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Log4j\\C2\\Login.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(ip);
        String arr[] = new String[1];
        arr[0] = wb.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
        wb.close();
        return arr;
    }

    @BeforeTest
    public void beforeTest()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        PropertyConfigurator.configure("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Log4j\\C2\\log4j2.properties");
    }
    
    @Test(dataProvider = "input")
    public void test1(String dest) throws Exception
    {
        driver.get("https://www.opentable.com/");

        if(driver.getCurrentUrl().equals("https://www.opentable.com/"))
        logger.info("Successful navigation to the open table site");
        else
        logger.warn("Didn't navigate to the open table site");
        
        Thread.sleep(3000);
        
        driver.findElement(By.xpath("//*[@id=\"home-page-autocomplete-input\"]")).sendKeys(dest);
        Thread.sleep(3000);

        driver.findElement(By.xpath("//*[@id=\"mainContent\"]/header/div/span/div/div/div[2]/div[2]/button")).click();
        Thread.sleep(5000);
        
        if(!driver.getCurrentUrl().equals("https://www.opentable.com/"))
        logger.info("Successful navigation to the result page");
        else
        logger.warn("Didn't navigate to the results page");
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)", "");
        
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div/section/div[6]/div/label[4]/span[2]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div/div/div[2]/div/div[2]/div[1]/div[1]/a")).click();
        Thread.sleep(2000);
        
        ArrayList<String> arr = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(arr.get(1));
        js.executeScript("window.scrollBy(0,500)", "");
        
        if(!driver.getCurrentUrl().equals("https://www.opentable.com/"))
        logger.info("Successful navigation to the result page");
        else
        logger.warn("Didn't navigate to the results page");
        
        TakesScreenshot scr = (TakesScreenshot) driver;
        FileUtils.copyFile(scr.getScreenshotAs(OutputType.FILE), new File("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Log4j\\C2\\img.png"));
        
        Select sel = new Select(driver.findElement(By.xpath("//*[@id=\"restProfileSideBarDtpPartySizePicker\"]")));
        Thread.sleep(2000);
        sel.selectByValue("4");
        driver.findElement(By.xpath("//*[@id=\"restProfileSideBarDtpDayPicker\"]/div/div")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"restProfileSideBarDtpDayPicker-wrapper\"]/div/div/div/div/div[2]/button[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"restProfileSideBarDtpDayPicker-wrapper\"]/div/div/div/table/tbody/tr[3]/td[2]/button")).click();
        Thread.sleep(2000);
        sel = new Select(driver.findElement(By.xpath("//*[@id=\"restProfileSideBartimePickerDtpPicker\"]")));
        Thread.sleep(2000);
        sel.selectByVisibleText("6:30 PM");
        logger.info("Successful input of the given credentials");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"baseApp\"]/div/header/div[2]/div[2]/div[1]/button")).click();
        Thread.sleep(5000);
        logger.info("Successfully opened the sign in page");
    }
    
    @AfterTest
    public void afterTest()
    {
        driver.quit();
    }
}
