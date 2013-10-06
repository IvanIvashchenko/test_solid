package it.sevenbits;

import it.sevenbits.pages.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.junit.Assert.assertNotNull;

public class OrdersTest extends TestBase {

    private boolean acceptNextAlert = true;

    @BeforeMethod
    public void setUp() {
        driver.get(baseUrl + "/orders/find");
    }

    @Test
    public void testFindOrder() {

        WebElement element = this.findWebElement(By.name("code"));
        assertNotNull(element);
        element.sendKeys("invalid code");
        element = this.findWebElement(By.xpath("/html/body/div[1]/div/div[2]/form/button"));
        assertNotNull(element);
        element.click();
//        WebDriverWait wait = new WebDriverWait(driver, 5);
//        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
//        this.closeAlertAndGetItsText();

    }

    private WebElement findWebElement(By by) {

        WebElement element = null;
        try {
            element = driver.findElement(by);
        } catch (NoSuchElementException e) {
            //Do nothing
        }
        return element;
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
