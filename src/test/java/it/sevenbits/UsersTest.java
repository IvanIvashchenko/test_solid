package it.sevenbits;

import it.sevenbits.pages.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class UsersTest extends TestBase {

    @BeforeMethod
    public void setUp() {
        driver.get(baseUrl);
    }

    @Test
    public void testValidRegistration() throws SQLException {

        Statement stmt = connection.createStatement();
        WebElement element = this.findWebElement(By.xpath("//a[text()='Регистрация']"));
        assertThat(element).isNotNull();
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new_user")));

        element = this.findWebElement(By.id("user_nickname"));
        assertThat(element).isNotNull();
        element.sendKeys("test_user");
        element = this.findWebElement(By.id("user_email"));
        assertThat(element).isNotNull();
        element.sendKeys("test_mail@test.com");
        element = this.findWebElement(By.id("user_password"));
        assertThat(element).isNotNull();
        element.sendKeys("test_password");
        element = this.findWebElement(By.xpath("//div[@class='block']/fieldset/div[@class='submit']/input"));
        assertThat(element).isNotNull();
        element.click();
        assertThat(driver.getCurrentUrl()).isEqualToIgnoringCase(baseUrl);
        assertThat(stmt.execute("SELECT * FROM users WHERE email = 'test_mail@test.com'")).isTrue();
    }

    @Test
    public void testInvalidRegistration() {

        WebElement element = this.findWebElement(By.xpath("//a[text()='Регистрация']"));
        assertThat(element).isNotNull();
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new_user")));

        assertThat(this.findWebElement(By.className("help-inline"))).isNull();
        element = this.findWebElement(By.id("user_nickname"));
        assertThat(element).isNotNull();
        element.sendKeys("");
        element = this.findWebElement(By.id("user_email"));
        assertThat(element).isNotNull();
        element.sendKeys("");
        element = this.findWebElement(By.id("user_password"));
        assertThat(element).isNotNull();
        element.sendKeys("");
        element = this.findWebElement(By.xpath("//div[@class='block']/fieldset/div[@class='submit']/input"));
        assertThat(element).isNotNull();
        element.click();
        assertThat(driver.getCurrentUrl()).isEqualToIgnoringCase(baseUrl + "users");
        List<WebElement> elements= driver.findElements(By.className("help-inline"));
        assertThat(elements).isNotNull().hasSize(3);
    }

    @Test
    public void testInvalidSignIn() {

        WebElement element = this.findWebElement(By.xpath("//a[text()='Войти на сайт']"));
        assertThat(element).isNotNull();
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_email")));
        assertThat(this.findWebElement(By.className("help-inline"))).isNull();

        element = this.findWebElement(By.xpath("//fieldset/div[@class='submit']/input"));
        assertThat(element).isNotNull();
        element.click();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("help-inline")));
        assertThat(driver.getCurrentUrl()).isEqualToIgnoringCase(baseUrl + "users/sign_in");
        element = this.findWebElement(By.className("alert"));
        assertThat(element).isNotNull();
    }

    @AfterClass
    public void removeTestUser() throws SQLException {

        Statement stmt = connection.createStatement();
        stmt.execute("DELETE FROM users WHERE email = 'test_mail@test.com'");
    }
}
