package com.eg.CW.Webelement;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class C2 {
    public static void main(String args[]) throws Exception
    {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
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
        System.out.println(RI+" Found");
        else
        System.out.println("Not Found");
        
        Thread.sleep(2000);
        
        driver.findElement(By.xpath("//*[@id=\"common_header\"]/div[1]/div[3]/nav/div/ul/li[10]")).click();
        
        Thread.sleep(10000);
        
        JS.executeScript("window.scrollBy(0,1000,'smooth')", "");
        driver.findElement(By.linkText("SIP")).click();
        
        Thread.sleep(5000);
        driver.close();
    } 
}
