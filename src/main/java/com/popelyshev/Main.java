package com.popelyshev;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main {

  private static WebDriver driver;
  private static ScheduledExecutorService worker;
  private static Actions actionHandler;
  private static WebElement MAP;

  public static void main(String[] args) {
    System.setProperty("webdriver.chrome.driver","c:/Users/apope/AppData/Local/Temp/chromedriver.exe");
    ChromeOptions opt = new ChromeOptions();
    opt.addArguments("--start-maximized");
    driver = new ChromeDriver(opt);
    driver.get("https://yandex.com/maps/213/moscow/?ll=37.993454%2C55.543578&z=14");
    worker = Executors.newSingleThreadScheduledExecutor();
    actionHandler = new Actions(driver);
    if (!init())
      System.exit(5);
    worker.schedule(Main::showSelectPopup, 1000, TimeUnit.MILLISECONDS);
  }

  private static boolean init() {
    WebElement element = (new WebDriverWait(driver, 15))
            .until(ExpectedConditions.elementToBeClickable(By.className("sidebar-toggle-button")));
    element.click();
    ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('lg-cc')[0].classList.remove('lg-cc_visible')");
    MAP = driver.findElement(By.xpath("//div[@class='map-container']"));
    return true;
  }

  private static void showSelectPopup() {

  }

  private static void mapDrag(int xOffset, int yOffset) {
    actionHandler.dragAndDropBy(MAP, xOffset, yOffset).perform();
  }

}
