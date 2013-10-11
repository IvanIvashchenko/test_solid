package it.sevenbits;

import it.sevenbits.pages.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrdersTest extends TestBase {

    @BeforeMethod
    public void setUp() {
        driver.get(baseUrl);
    }

    @BeforeClass
    public void setUpUsers() throws SQLException {

        Statement stmt = connection.createStatement();
        stmt.execute("DELETE FROM orders WHERE recipient = 'test_recipient'");
    }

    @Test
    public void testCreateOrder() {

        WebElement element = this.findWebElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/div[2]/div/div[1]/a[1]"));
        assertNotNull(element);
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='js-tee-shirt-icon tee-man']")));
        element = this.findWebElement(By.xpath("//input[@value='Купить']"));
        assertNotNull(element);
        element.click();
        element = this.findWebElement(By.id("order_recipient"));
        assertNotNull(element);
        element.sendKeys("test_recipient");
        element = this.findWebElement(By.id("order_address_attributes_postcode"));
        assertNotNull(element);
        element.sendKeys("644644");
        element = this.findWebElement(By.id("order_address_attributes_country_code"));
        assertNotNull(element);
        element = this.findWebElement(By.id("order_address_attributes_region"));
        assertNotNull(element);
        Select select = new Select(element);
        select.deselectAll();
        select.selectByVisibleText("Омская область");
        element = this.findWebElement(By.id("order_address_attributes_other"));
        assertNotNull(element);
        element.sendKeys("test_address");
        element = this.findWebElement(By.xpath("//*[@id=\"new_order\"]/div[7]/input"));
        assertNotNull(element);
        element.click();
        assertEquals(driver.getCurrentUrl(), "http://test.robokassa.ru/ReturnResults.aspx?Culture=ru");
    }

    @Test
    public void testCreateUnvalidOrder() {

        WebElement element = this.findWebElement(By.xpath("//input[@value='Купить']"));
        assertNotNull(element);
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

    }

//    @Test
//    public void testFindOrder() {
//
//        WebElement element = this.findWebElement(By.name("code"));
//        assertNotNull(element);
//        element.sendKeys("invalid code");
//        element = this.findWebElement(By.xpath("/html/body/div[1]/div/div[2]/form/button"));
//        assertNotNull(element);
//        element.click();
//        element = this.findWebElement(By.className("alert"));
//        WebDriverWait wait = new WebDriverWait(driver, 5);
//        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
//        this.closeAlertAndGetItsText();
//
//    }
}
