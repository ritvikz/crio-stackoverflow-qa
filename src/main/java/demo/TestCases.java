package demo;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import java.util.logging.Level;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {

    ChromeDriver driver;

    // Constructor
    public TestCases() {

        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    // Close browser
    public void endTest() {
        System.out.println("End Test: TestCases");
        driver.quit();
    }


    // ------------------------------------------------
    // TestCase01 → Verify StackOverflow URL
    // ------------------------------------------------
    public void testCase01(){

        System.out.println("Start TestCase01");

        driver.get("https://stackoverflow.com");

        String url = driver.getCurrentUrl();

        if(url.contains("stackoverflow"))
            System.out.println("PASS");
        else
            System.out.println("FAIL");

        System.out.println("End TestCase01\n");
    }


    // ------------------------------------------------
    // TestCase02 → Search and verify results
    // ------------------------------------------------
    public void testCase02() throws InterruptedException {

        System.out.println("Start TestCase02");

        driver.get("https://stackoverflow.com");

        driver.findElement(By.cssSelector("input[name='q']"))
                .sendKeys("Python list comprehension" + Keys.ENTER);

        Thread.sleep(3000);

        List<WebElement> results =
                driver.findElements(By.cssSelector(".s-post-summary"));

        if(results.size() > 0)
            System.out.println("PASS");
        else
            System.out.println("FAIL");

        System.out.println("End TestCase02\n");
    }


    // ------------------------------------------------
    // TestCase03 → Tag filtering
    // ------------------------------------------------
    public void testCase03() throws InterruptedException {

        System.out.println("Start TestCase03");

        driver.get("https://stackoverflow.com/tags");

        driver.findElement(By.cssSelector("input.js-tag-filter"))
                .sendKeys("javascript");

        Thread.sleep(2000);

        driver.findElement(By.xpath("//a[@class='post-tag' and text()='javascript']"))
                .click();

        Thread.sleep(2000);

        if(driver.getCurrentUrl().contains("javascript"))
            System.out.println("PASS");
        else
            System.out.println("FAIL");

        System.out.println("End TestCase03\n");
    }


    // ------------------------------------------------
    // TestCase04 → Sort by Score
    // ------------------------------------------------
    public void testCase04() throws InterruptedException {

        System.out.println("Start TestCase04");

        driver.get("https://stackoverflow.com/questions");

        driver.findElement(By.xpath("//a[contains(@href,'tab=Votes')]"))
                .click();

        Thread.sleep(3000);

        List<WebElement> votes =
                driver.findElements(By.cssSelector(".s-post-summary--stats-item-number"));

        if(votes.size() >= 2) {

            int first = Integer.parseInt(votes.get(0).getText());
            int second = Integer.parseInt(votes.get(1).getText());

            if(first >= second)
                System.out.println("PASS");
            else
                System.out.println("FAIL");

        } else {
            System.out.println("FAIL - Not enough data");
        }

        System.out.println("End TestCase04\n");
    }
}