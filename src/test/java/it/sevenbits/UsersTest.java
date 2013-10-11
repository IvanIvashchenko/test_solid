package it.sevenbits;

import it.sevenbits.pages.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

public class UsersTest extends TestBase {

    @BeforeMethod
    public void setUp() {
        driver.get(baseUrl);
    }

    @BeforeClass
    public void setUpUsers() throws SQLException {

        Statement stmt = connection.createStatement();
        stmt.execute("DELETE FROM users WHERE email = 'test_mail@test.com'");
    }

    @Test
    public void testValidRegistration() throws SQLException {

        Statement stmt = connection.createStatement();
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
        assertTrue(stmt.execute("SELECT * FROM users WHERE email = 'test_mail@test.com'"));
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
}
