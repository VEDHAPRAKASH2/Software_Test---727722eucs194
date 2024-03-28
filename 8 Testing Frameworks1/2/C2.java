package com.eg.CW.Testframe1;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class C2 {
    public WebDriver driver;

    @DataProvider(name = "loginInput")
    public String[][] loginInput() throws Exception
    {
        FileInputStream ip = new FileInputStream(new File("E:\\4th sem\\Testing\\demo1\\src\\test\\java\\com\\eg\\CW\\Testframe1\\Excel.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(ip);
        Sheet sh = wb.getSheet("Sheet1");
        String[][] arr = new String[1][2];
        arr[0][0] = sh.getRow(2).getCell(0).getStringCellValue();
        arr[0][1] = sh.getRow(2).getCell(1).getStringCellValue();
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
        driver.get("https://www.demoblaze.com");
    }

    @Test
    public void test1() throws Exception
    {
        driver.findElement(By.linkText("Laptops")).click();

        Thread.sleep(1000);

        driver.findElement(By.linkText("MacBook air")).click();

        Thread.sleep(1000);

        driver.findElement(By.linkText("Add to cart")).click();

        Thread.sleep(1000);
        
        driver.switchTo().alert().accept();

        driver.findElement(By.linkText("Cart")).click();

        Thread.sleep(3000);

        System.out.println("Title : "+driver.findElement(By.xpath("//tbody[@id=\"tbodyid\"]//tr//td[2]")).getText());
        System.out.println("Price : "+driver.findElement(By.xpath("//tbody[@id=\"tbodyid\"]//tr//td[3]")).getText());
    }
    
    @Test(dataProvider = "loginInput")
    public void test2(String username,String password) throws Exception
    {
        driver.findElement(By.linkText("Log in")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("loginusername")).sendKeys(username);
        driver.findElement(By.id("loginpassword")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id='logInModal']/div/div/div[3]/button[2]")).click();
        Thread.sleep(3000);
        Assert.assertEquals(driver.findElement(By.id("nameofuser")).getText(),"Welcome Testalpha");
    }

    @AfterTest
    public void afterTest()
    {
        driver.close();
    }
}
