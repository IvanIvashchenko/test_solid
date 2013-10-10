package it.sevenbits;

import it.sevenbits.pages.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

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
        element = this.findWebElement(By.className("alert"));
//        WebDriverWait wait = new WebDriverWait(driver, 5);
//        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
//        this.closeAlertAndGetItsText();

    }

    @Test
    public void testValidRegistration() {

        WebElement element = this.findWebElement(By.linkText("Регистрация"));
        assertNotNull(element);
        element.click();
        element = this.findWebElement(By.id("user_nickname"));
        assertNotNull(element);
        element.sendKeys("test_user");
        element = this.findWebElement(By.id("user_email"));
        assertNotNull(element);
        element.sendKeys("test_mail@test.com");
        element = this.findWebElement(By.id("user_password"));
        assertNotNull(element);
        element.sendKeys("test_password");
        element = this.findWebElement(By.xpath("//div[@class='block']/fieldset/div[@class='submit']/input"));
        assertNotNull(element);
        element.click();
        assertEquals(driver.getCurrentUrl(), baseUrl);
    }

    @Test
    public void testInvalidRegistration() {

        WebElement element = this.findWebElement(By.linkText("Регистрация"));
        assertNotNull(element);
        element.click();
        assertNull(this.findWebElement(By.className("help-inline")));

        element = this.findWebElement(By.id("user_nickname"));
        assertNotNull(element);
        element.sendKeys("");
        element = this.findWebElement(By.id("user_email"));
        assertNotNull(element);
        element.sendKeys("");
        element = this.findWebElement(By.id("user_password"));
        assertNotNull(element);
        element.sendKeys("");
        element = this.findWebElement(By.xpath("//div[@class='block']/fieldset/div[@class='submit']/input"));
        assertNotNull(element);
        element.click();
        assertEquals(driver.getCurrentUrl(), baseUrl + "users");
        List<WebElement> elements= driver.findElements(By.className("help-inline"));
        assertThat(elements).isNotNull().hasSize(3);
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
