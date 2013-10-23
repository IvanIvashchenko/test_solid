package it.sevenbits;

import it.sevenbits.pages.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class OrdersTest extends TestBase {

    @BeforeMethod
    public void setUp() {
        driver.get(baseUrl);
    }

    @AfterClass
    public void setUpOrders() throws SQLException {

        Statement stmt = connection.createStatement();
        stmt.execute("DELETE FROM orders WHERE recipient = 'test_recipient'");
    }

    @Test
    public void testCreateInvalidOrder() {

        WebElement element = this.findWebElement(By.xpath("//div[@class='tee-info-select-size']/a[1]/div[text()='44']"));
        assertThat(element).isNotNull();
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='js-tee-shirt-icon tee-man']")));
        element = this.findWebElement(By.xpath("//input[@value='Купить']"));
        assertThat(element).isNotNull();
        element.click();
        element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='ОПЛАТИТЬ']")));
        element.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("help-inline")));
        List<WebElement> elements= driver.findElements(By.className("help-inline"));
        assertThat(elements).isNotNull().hasSize(3);
    }

    @Test
    public void testCreateOrder() {

        WebElement element = this.findWebElement(By.xpath("//div[@class='tee-info-select-size']/a[1]/div[text()='44']"));
        assertThat(element).isNotNull();
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='js-tee-shirt-icon tee-man']")));
        element = this.findWebElement(By.xpath("//input[@value='Купить']"));
        assertThat(element).isNotNull();
        element.click();
        element = this.findWebElement(By.id("order_recipient"));
        assertThat(element).isNotNull();
        element.sendKeys("test_recipient");
        element = this.findWebElement(By.id("order_address_attributes_postcode"));
        assertThat(element).isNotNull();
        element.sendKeys("644644");
        element = this.findWebElement(By.id("order_address_attributes_country_code"));
        assertThat(element).isNotNull();
        element = this.findWebElement(By.id("order_address_attributes_region"));
        assertThat(element).isNotNull();
        Select select = new Select(element);
        select.selectByVisibleText("Омская область");
        element = this.findWebElement(By.id("order_address_attributes_other"));
        assertThat(element).isNotNull();
        element.sendKeys("test_address");
        element = this.findWebElement(By.xpath("//input[@value='ОПЛАТИТЬ']"));
        assertThat(element).isNotNull();
        element.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ILogo")));
        assertThat(driver.getCurrentUrl()).contains("http://test.robokassa.ru/ReturnResults.aspx");
    }

    @Test
    public void testCreateEmptyOrder() {

        WebElement element = this.findWebElement(By.xpath("//input[@value='Купить']"));
        assertThat(element).isNotNull();
        element.click();
        String alertMsg = this.closeAlertAndGetItsText();
        assertThat(alertMsg).isEqualToIgnoringCase("Сначала выберите размер!");
    }

    @Test
    public void testFindOrder() {

        String findOrderUrl = baseUrl + "orders/find";
        driver.get(findOrderUrl);
        WebElement element = this.findWebElement(By.name("code"));
        assertThat(element).isNotNull();
        element.sendKeys("invalid code");
        element = this.findWebElement(By.xpath("/html/body/div[1]/div/div[2]/form/button"));
        assertThat(element).isNotNull();
        element.click();
        element = this.findWebElement(By.className("alert"));
        assertThat(element).isNotNull();
        assertThat(driver.getCurrentUrl()).isEqualToIgnoringCase(findOrderUrl);
    }
}
