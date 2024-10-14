import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class NikeShoeAutomation {
    private WebDriver driver;
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeClass
    public void setup() {

        driver = new ChromeDriver();
        driver.get("https://www.nike.com/in/");

        // Set up Extent Reports
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent_report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }
    @Test

    public  void testprice() {

        WebDriver driver = new ChromeDriver();

        try {

            driver.get("https://www.nike.com/in/");

            // Click on Men
            WebElement menLink = driver.findElement(By.linkText("Men"));
            menLink.click();

            // Click on Shoes
            WebElement shoesLink = driver.findElement(By.linkText("Shoes"));

            shoesLink.click();

            // Click on Sort By
            Thread.sleep(2000);
            WebElement sortBy = driver.findElement(By.xpath("//*[@id=\"dropdown-btn\"]/span[1]/span[1]"));
            sortBy.click();

            // Click on High to Low
            WebElement highToLow = driver.findElement(By.xpath("//button[contains(text(), 'High-Low')]"));
            highToLow.click();
            //File screen2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            //FileUtils.copyFile(screen2, new File("./Screenshotsep30/" + timestamp + "_Google2" + ".png"));
            // Wait for the page to load sorted results
            Thread.sleep(3000);

            // the products count in displayed page
            List<WebElement> products = driver.findElements(By.cssSelector("div.product-card__body"));
            System.out.println("Total products displayed: " + products.size());

            // the prices in displayed page
            List<WebElement> prices = driver.findElements(By.cssSelector("div.product-price"));
            for (WebElement price : prices) {
                System.out.println(price.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

        @AfterClass
        public void tearDown() {
            // Close the driver
            driver.quit();

            // Flush the reports
            extent.flush();
        }
    }

