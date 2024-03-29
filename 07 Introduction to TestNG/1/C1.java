package com.eg.CW.TestNG;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class C1 {
    public static WebDriver driver;

    @BeforeTest
    public void before()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void test() throws Exception
    {
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(11));
        driver.get("https://economictimes.indiatimes.com/et-now/results");
        w.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Mutual Funds")));
        driver.findElement(By.linkText("Mutual Funds")).click();
        
        w.until(ExpectedConditions.presenceOfElementLocated(By.id("amcSelection")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)", "");
        
        Select select = new Select(driver.findElement(By.id("amcSelection")));
        select.selectByVisibleText("Canara Robeco");
        
        Thread.sleep(1000);
        
        select = new Select(driver.findElement(By.id("schemenm")));
        select.selectByVisibleText("Canara Robeco Bluechip Equity Direct-G");
        
        Thread.sleep(1000);
        
        driver.findElement(By.linkText("Get Details")).click();
        
        Thread.sleep(5000);
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        
        w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"installment_type\"]")));
        
        driver.findElement(By.xpath("//*[@id=\"installment_type\"]")).click();
        driver.findElement(By.xpath("//*[@id='installment_type']/li/ul/li[1]/span")).click();
        Thread.sleep(3000);
        
        driver.findElement(By.xpath("//*[@id='installment_amt']")).click();
        driver.findElement(By.xpath("//*[@id='installment_amt']/li/ul/li[3]/span")).click();
        Thread.sleep(3000);

        driver.findElement(By.xpath("//*[@id=\"installment_period\"]")).click();
        driver.findElement(By.xpath("//*[@id='installment_period']/li/ul/li[4]/span")).click();
        Thread.sleep(3000);
        
        driver.findElement(By.xpath("//*[@id=\"mfNav\"]/div/ul/li[2]")).click();
        Thread.sleep(5000);

        TakesScreenshot screen = (TakesScreenshot)driver;
        File src = screen.getScreenshotAs(OutputType.FILE);
        File des = new File("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\TestNG\\img.jpg");
        FileUtils.copyFile(src, des);

        System.out.print(driver.findElement(By.xpath("//*[@id=\"mfReturns\"]/div[2]/div[2]/ul/li[1]/table/tbody/tr[1]/td[1]")).getText()+" "+driver.findElement(By.xpath("//*[@id=\"mfReturns\"]/div[2]/div[2]/ul/li[1]/table/tbody/tr[1]/td[2]")).getText()+" "+driver.findElement(By.xpath("//*[@id=\"mfReturns\"]/div[2]/div[2]/ul/li[1]/table/tbody/tr[1]/td[3]")).getText()+" "+driver.findElement(By.xpath("//*[@id=\"mfReturns\"]/div[2]/div[2]/ul/li[1]/table/tbody/tr[1]/td[4]")).getText()+" "+driver.findElement(By.xpath("//*[@id=\"mfReturns\"]/div[2]/div[2]/ul/li[1]/table/tbody/tr[1]/td[5]")).getText()+" "+driver.findElement(By.xpath("//*[@id=\"mfReturns\"]/div[2]/div[2]/ul/li[1]/table/tbody/tr[1]/td[6]")).getText()+" "+driver.findElement(By.xpath("//*[@id=\"mfReturns\"]/div[2]/div[2]/ul/li[1]/table/tbody/tr[1]/td[7]")).getText());

        Thread.sleep(3000);
    }

    @AfterTest
    public void post()
    {
        driver.quit();
    }
}
