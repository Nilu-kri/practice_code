import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class lowest_price {
    private WebDriver driver;
    private WebDriverWait wait;
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeClass
    public void setup() {

        driver = new ChromeDriver();
        driver.get("https://www.google.com/shopping");
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().window().maximize();



        // Set up Extent Reports
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent_report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }
    @Test

    public  void testprice() {
        test = extent.createTest("testprice");



        try {
            // Set implicit wait
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            // Navigate to Google Shopping
            driver.get("https://www.google.com/shopping");
            System.out.println("Navigated to Google Shopping.");

            // Perform a search (e.g., search for "laptop")
            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys("laptop");
            searchBox.submit();
            Thread.sleep(3000);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


            // Get product price elements
            List<WebElement> priceElements = driver.findElements(By.xpath("//span[@class='a8Pemb OFFNJ']"));
            Thread.sleep(6000);

            List<Double> prices = new ArrayList<>();

            List<Double> filteredPrices = priceElements.stream()
                    .map(WebElement::getText)
                    .map(text -> text.replace("₹", "").replace(",","").trim())
                    .filter(text -> !text.isEmpty())
                    .map(Double::parseDouble)
                    .collect(Collectors.toList());


            Collections.sort(filteredPrices);

            // Get the 2 lowest and 2 highest prices
            if (filteredPrices.size() >= 2) {
                System.out.println("Two Lowest Prices: ");
                System.out.println("₹" + filteredPrices.get(0));
                System.out.println("₹" + filteredPrices.get(1));
            } else {
                System.out.println("Not enough products found for lowest price validation.");
            }

            if (filteredPrices.size() >= 2) {
                System.out.println("Two Highest Prices: ");
                System.out.println("₹" + filteredPrices.get(filteredPrices.size() - 2));
                System.out.println("₹" + filteredPrices.get(filteredPrices.size() - 1));
            } else {
                System.out.println("Not enough products found for highest price validation.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    @AfterClass
    public void tearDown() {
        // Close the driver
        // driver.quit();

        // Flush the reports
        extent.flush();
    }
}
