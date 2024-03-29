package com.eg.CW.Locators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class C1 {
    public static void main(String args[]) throws Exception
    {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.demoblaze.com");
        driver.manage().window().maximize();
        driver.findElement(By.linkText("Laptops")).click();

        Thread.sleep(2000);

        driver.findElement(By.linkText("MacBook air")).click();

        Thread.sleep(2000);

        driver.findElement(By.linkText("Add to cart")).click();

        Thread.sleep(2000);
        
        driver.switchTo().alert().accept();

        driver.findElement(By.linkText("Cart")).click();

        Thread.sleep(3000);

        System.out.println("Title : "+driver.findElement(By.xpath("//tbody[@id=\"tbodyid\"]//tr//td[2]")).getText());
        System.out.println("Price : "+driver.findElement(By.xpath("//tbody[@id=\"tbodyid\"]//tr//td[3]")).getText());

        driver.close();
    }
}
