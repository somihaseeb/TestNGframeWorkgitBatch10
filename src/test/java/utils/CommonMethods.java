package utils;

import com.google.gson.internal.bind.util.ISO8601Utils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CommonMethods {

    public static WebDriver driver;

    //pre condition
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(){
        ConfigReader.readProperties(Constants.CONFIGURATION_FILEPATH);
        switch(ConfigReader.getPropertyValue("browser")){
            case "chrome":
              //  System.setProperty("webdriver.chrome.driver", "src/driver/chromedriver");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.chromedriver().setup();
                driver = new FirefoxDriver();
                break;
            case "safari":
                WebDriverManager.safaridriver().setup();
                driver = new SafariDriver();
                break;
            default:
                throw new RuntimeException("Invalid browser name");

        }

        driver.get(ConfigReader.getPropertyValue("url"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT, TimeUnit.SECONDS);
    }

    //we need two things, locator and string data
    public static void sendText(WebElement element, String textToSend){
        element.clear();
        element.sendKeys(textToSend);
    }

    public static WebDriverWait getWait(){
        WebDriverWait wait = new WebDriverWait(driver, Constants.EXPLICIT_WAIT);
        return wait;
    }

    public static void waitForClickability(WebElement element){
        getWait().until(ExpectedConditions.elementToBeClickable(element));

    }

    public static void click(WebElement element){
       //we should check/wait the element becomes clickable, or available to be clicked
       waitForClickability(element);
        element.click();
    }

    public static JavascriptExecutor getJSWExecutor(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js;
    }

    public static  void jsClick(WebElement element){
        getJSWExecutor().executeScript("arguments[0].click();", element);
    }

public static void takeScreenshot(String fileName){
    TakesScreenshot ts = (TakesScreenshot) driver;
    File sourceFile = ts.getScreenshotAs(OutputType.FILE);

    try{
        FileUtils.copyFile(sourceFile, new File(
                Constants.SCREENSHOT_FILEPATH + fileName + " " + getTimeStamp("yyyy-MM-dd-HH-mm-ss") + ".png"));
    }catch (IOException e){
        e.printStackTrace();
    }
}

    public static String getTimeStamp(String pattern){
        Date date = new Date();
        //pattern YYYY-MM-DD-HH-MM-SS-MS
        //to format the date according to our choice we have a function
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }


    //post condition
    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.quit();
    }
}
