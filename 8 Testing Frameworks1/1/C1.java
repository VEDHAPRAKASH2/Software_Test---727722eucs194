package com.eg.CW.Testframe1;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class C1 {
    public WebDriver driver;
    
    @DataProvider(name = "loginInput")
    public String[][] loginInput() throws Exception
    {
        FileInputStream ip = new FileInputStream(new File("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Testframe1\\Excel.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(ip);
        Sheet sh = wb.getSheet("Sheet1");
        String[][] arr = new String[1][2];
        arr[0][0] = sh.getRow(1).getCell(0).getStringCellValue();
        arr[0][1] = sh.getRow(1).getCell(1).getStringCellValue();
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
    
    @BeforeMethod
    public void beforeMethod()
    {
        driver.get("http://www.dbankdemo.com/bank/login");
    }
    
    @Test(dataProvider = "loginInput")
    public void test1(String username,String password)
    {
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("submit")).submit();

        if(!driver.getCurrentUrl().contains("home"))
        {
            Assert.assertTrue(false);
        }
    }
    
    @Test(dataProvider = "loginInput")
    public void test2(String username,String password) throws Exception
    {
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("submit")).submit();

        driver.findElement(By.linkText("Deposit")).click();

        Select account = new Select(driver.findElement(By.id("selectedAccount")));
        account.selectByVisibleText("Individual Checking (Standard Checking)");

        driver.findElement(By.id("amount")).sendKeys("5000");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//button[@class='btn btn-primary btn-sm']")).click();

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,1000)","");

        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='transactionTable']/tbody/tr/td[4]")).getText(),"$5000.00");
    }
    
    @Test(dataProvider = "loginInput")
    public void test3(String username,String password) throws Exception
    {
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("submit")).submit();

        driver.findElement(By.linkText("Withdraw")).click();

        Select account = new Select(driver.findElement(By.id("selectedAccount")));
        account.selectByVisibleText("Individual Checking (Standard Checking)");

        driver.findElement(By.id("amount")).sendKeys("3000");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//button[@class='btn btn-primary btn-sm']")).click();

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,1000)","");

        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='transactionTable']/tbody/tr/td[4]")).getText(),"$-3000.00");
    }
    
    @AfterTest
    public void afterTest()
    {
        driver.close();
    }
}