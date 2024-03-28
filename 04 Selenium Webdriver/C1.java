package com.eg.CW.WebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class C1 {
    public static void main(String args[]) throws Exception
    {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.shoppersstop.com/");
        driver.findElement(By.xpath("//*[@id=\"profileImage\"]/a")).click();
        Thread.sleep(5000);
        driver.close();
    }
}
