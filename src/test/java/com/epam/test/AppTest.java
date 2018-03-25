package com.epam.test;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AppTest {
    private static final String PKG_NAME_ID = "car.browser.nimble:id/";
    private AndroidDriver driver;
    private WebElement searchInput;

    @BeforeTest
    public void init() throws MalformedURLException {
        if (driver == null) {
            File appDir = new File("apps");
            File app = new File(appDir, "browser.apk");
            DesiredCapabilities capabilities = new DesiredCapabilities();

            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.0");
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Samsung Galaxy S6");

            capabilities.setCapability("app", app.getAbsolutePath());
            URL url = new URL("http://127.0.0.1:4723/wd/hub");
            driver = new AndroidDriver(url, capabilities);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
    }

    @Test
    public void enterValueInSearchField() {
        By search = By.id(PKG_NAME_ID + "search");
        searchInput = driver.findElement(search);
        searchInput.sendKeys("java" + "\n");
    }

    @Test
    public void enterWebsite() {
        searchInput.sendKeys("https://www.oracle.com" + "\n");
    }

    @Test
    public void openNewTab() {
        Dimension screenSize = driver.manage().window().getSize();
        new TouchAction(driver)
                .press((int) (screenSize.width * 0.6), 130)
                .press((int) (screenSize.width * 0.3), 130)
                .release()
                .perform();
        By newTab = By.className("android.widget.LinearLayout");
        driver.findElement(newTab).click();
    }

    @Test
    public void quitTab() {
        driver.findElement(By.id(PKG_NAME_ID + "arrow_button")).click();
        driver.findElement(By.id(PKG_NAME_ID + "deleteButton")).click();
    }

    @AfterTest
    public void clearData() {
        if (driver != null) {
            driver.quit();
        }
    }
}