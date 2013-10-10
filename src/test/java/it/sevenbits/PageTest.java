package it.sevenbits;

import it.sevenbits.pages.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PageTest extends TestBase {

    @BeforeMethod
    public void setUp() {
        driver.get(baseUrl);
    }

    @Test
    public void testMainPage() throws Exception {

        assertEquals(driver.getTitle(), "Неповторимый дизайн футболок, ограниченная серия.");
        assertTrue(isElementPresent(By.linkText("Галерея")));
        assertTrue(isElementPresent(By.linkText("Предложить дизайн")));
        assertTrue(isElementPresent(By.linkText("Мои заказы")));
        assertTrue(isElementPresent(By.linkText("О нас")));
        assertTrue(isElementPresent(By.name("subscription[email]")));
    }

    @Test
    public void testRegistrationPage() throws Exception {

        driver.findElement(By.linkText("Регистрация")).click();
        assertTrue(isElementPresent(By.id("user_nickname")));
        assertTrue(isElementPresent(By.id("user_email")));
        assertTrue(isElementPresent(By.id("user_password")));
        assertTrue(isElementPresent(By.xpath("//input[@value='Зарегистрироваться']")));
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
