package com.eg.CW.Log4j.C1;

import java.time.Duration;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class C1 {
    public WebDriver driver;
    final Logger logger = Logger.getLogger(getClass());

    @BeforeTest
    public void beforeTest()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        PropertyConfigurator.configure("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Log4j\\C1\\log4j2.properties");
    }
    
    @Test
    public void test1() throws Exception
    {
        driver.get("https://www.moneycontrol.com/");
        
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        w.until(ExpectedConditions.visibilityOfElementLocated(By.id("search_str"))).sendKeys("Reliance Industries");
        
        Thread.sleep(3000);
        
        driver.findElement(By.xpath("//*[@id=\"autosuggestlist\"]/ul/li[1]/a")).click();
        
        Thread.sleep(5000);
        
        JavascriptExecutor JS=(JavascriptExecutor)driver;
        JS.executeScript("window.scrollBy(0,200,'smooth')", "");
        
        Thread.sleep(2000);
        
        String RI=driver.findElement(By.xpath("//*[@id=\"stockName\"]/h1")).getText();
        if(RI.equals("Reliance Industries Ltd."))
        {
            logger.info("Navigation to results page");
            System.out.println(RI+" Found");
        }
        else
        {
            logger.warn("Doesn't navigate to results page");
            System.out.println("Not Found");
        }
        Thread.sleep(2000);
        
        Actions a = new Actions(driver);
        a.moveToElement(driver.findElement(By.xpath("//*[@id=\"common_header\"]/div[1]/div[3]/nav/div/ul/li[10]"))).perform();
        driver.findElement(By.linkText("SIP Return")).click();
        logger.info("Navigation to SIP returns page");
        
        Select sel = new Select(driver.findElement(By.xpath("//*[@id=\"ff_id\"]")));
        sel.selectByVisibleText("Axis Mutual Fund");
        logger.info("Axis Mutual Fund Chosen");
        Thread.sleep(5000);
        sel = new Select(driver.findElement(By.xpath("//*[@id=\"im_id\"]")));
        sel.selectByVisibleText("Axis Arbitrage Fund - Direct Plan (D)");
        logger.info("Axis Arbitrage Fund - Direct Plan (D) Chosen");
        Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"invamt\"]")).sendKeys("100000");
        logger.info("Investment amount is given as 100000");
        driver.findElement(By.xpath("//*[@id=\"stdt\"]")).sendKeys("2021-08-02");
        logger.info("2021-08-02 is selected as start date");
        driver.findElement(By.xpath("//*[@id=\"endt\"]")).sendKeys("2023-08-17");
        logger.info("2023-08-17 is selected as end date");
        driver.findElement(By.xpath("//*[@id=\"mc_mainWrapper\"]/div[2]/div/div[3]/div[2]/div[2]/form/div[8]/input")).click();

        Thread.sleep(5000);
        
        System.out.println("Investment period "+driver.findElement(By.xpath("//tr[@class=\"cal_row\"][1]/td[2]")).getText());
        System.out.println("Total amount invested (Rs) "+driver.findElement(By.xpath("//tr[@class=\"cal_row\"][2]/td[2]")).getText());
        
        Thread.sleep(3000);
    }
    
    @AfterTest
    public void afterTest()
    {
        driver.close();
    }
}
