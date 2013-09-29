package it.sevenbits.pages;

import com.thoughtworks.selenium.SeleneseTestNgHelper;
import org.testng.annotations.Test;

//public class HomePageTest extends TestBase {
//public class HomePageTest extends SeleneseTestBase {
//
//	HomePage homepage;
//
//	@Parameters({ "path" })
//	@BeforeClass
//	public void testInit(String path) {
//
//		// Load the page in the browser
//		driver.get(baseUrl + path);
//		homepage = PageFactory.initElements(driver, HomePage.class);
//	}
//
//	@Test
//	public void testTost() throws InterruptedException {
//
//        selenium.open("/designs/new");
//        selenium.click("link=Галерея");
//        selenium.waitForPageToLoad("30000");
//        selenium.click("link=Мои заказы");
//        selenium.waitForPageToLoad("30000");
//        selenium.click("link=Футболка дня");
//        selenium.waitForPageToLoad("30000");
//        selenium.click("link=О нас");
//        selenium.waitForPageToLoad("30000");
//        selenium.click("link=Мои заказы");
//        selenium.waitForPageToLoad("30000");
//        selenium.type("name=code", "ahaha");
//        selenium.click("//button[@type='submit']");
//        selenium.waitForPageToLoad("30000");
//		Assert.assertTrue(homepage.getH1() != null);
//	}
//
//	@Test
//	public void test2() throws InterruptedException {
//		Assert.assertTrue(true);
//	}
//}

public class HomePageTest extends SeleneseTestNgHelper {
    @Test public void testName() throws Exception {



        selenium.open("/designs/new");
        selenium.click("link=Галерея");
        selenium.waitForPageToLoad("30000");
        selenium.click("link=Мои заказы");
        selenium.waitForPageToLoad("30000");
        selenium.click("link=Футболка дня");
        selenium.waitForPageToLoad("30000");
        selenium.click("link=О нас");
        selenium.waitForPageToLoad("30000");
        selenium.click("link=Мои заказы");
        selenium.waitForPageToLoad("30000");
        selenium.type("name=code", "ahaha");
        selenium.click("//button[@type='submit']");
        selenium.waitForPageToLoad("30000");
    }
}
