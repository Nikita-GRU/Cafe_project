package by.gruca.cafe.ui;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    @Test
    public void login() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver84.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait driverWait = new WebDriverWait(driver,10);
        driver.get("http://localhost:8099/cafe/login");
        driver.findElement(By.id("login_email")).sendKeys("nikipiki@tut.by");
        driver.findElement(By.id("login_password")).sendKeys("123");
        driver.findElement(By.id("login_submit")).click();
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profile_page")));
        driver.quit();

    }
}
