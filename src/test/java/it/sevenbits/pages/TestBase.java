package it.sevenbits.pages;

import it.sevenbits.util.Browser;
import it.sevenbits.util.PropertyLoader;
import it.sevenbits.webdriver.WebDriverFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Base class for all the test classes
 * 
 * @author Sebastiano Armeli-Battana
 */

public class TestBase {

    private static final String SCREENSHOT_FOLDER = "target/screenshots/";
    private static final String SCREENSHOT_FORMAT = ".png";

    protected WebDriver driver;
    protected String gridHubUrl;
    protected String baseUrl;
    protected Browser browser;

    protected Connection connection;

    private boolean acceptNextAlert = true;

    @BeforeClass
    public void init() throws ClassNotFoundException, SQLException {
        baseUrl = PropertyLoader.loadProperty("site.url");
        gridHubUrl = PropertyLoader.loadProperty("grid2.hub");

        browser = new Browser();
        browser.setName(PropertyLoader.loadProperty("browser.name"));
        browser.setVersion(PropertyLoader.loadProperty("browser.version"));
        browser.setPlatform(PropertyLoader.loadProperty("browser.platform"));

        String username = PropertyLoader.loadProperty("user.username");
        String password = PropertyLoader.loadProperty("user.password");
        
        driver = WebDriverFactory.getInstance(gridHubUrl, browser, username,
                password);
//        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        Class.forName(PropertyLoader.loadProperty("db_driver"));
        connection = DriverManager.getConnection(
                PropertyLoader.loadProperty("db_url"),
                PropertyLoader.loadProperty("db_username"),
                PropertyLoader.loadProperty("db_password")
        );
    }

    @AfterClass
    public void closeDataBase() throws SQLException {
        connection.close();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    protected WebElement findWebElement(By by) {

        WebElement element = null;
        try {
            element = driver.findElement(by);
        } catch (NoSuchElementException e) {
            //Do nothing
        }
        return element;
    }


    protected String closeAlertAndGetItsText() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
//            Alert alert = driver.switchTo().alert();
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

//    @AfterMethod
//    public void setScreenshot(ITestResult result) {
//        if (!result.isSuccess()) {
//            try {
//                WebDriver returned = new Augmenter().augment(driver);
//                if (returned != null) {
//                    File f = ((TakesScreenshot) returned)
//                            .getScreenshotAs(OutputType.FILE);
//                    try {
//                        FileUtils.copyFile(f, new File(SCREENSHOT_FOLDER
//                                + result.getName() + SCREENSHOT_FORMAT));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } catch (ScreenshotException se) {
//                se.printStackTrace();
//            }
//        }
//    }
}
