import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class reports {
    private WebDriver driver;
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeClass
    public void setup() {

        driver = new ChromeDriver();
        driver.get("https://www.google.com");

        // Set up Extent Reports
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent_report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @Test
    public void testFilterSortAndFindThirdHighest() {
        test = extent.createTest("Filter, Sort, and Find Third Highest Product");
        try {
            driver.get("https://www.google.com");
            driver.manage().window().maximize();


            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys("laptop");
            searchBox.submit();
            driver.findElement(By.linkText("Shopping")).click();
            List<WebElement> productPrices = driver.findElements(By.cssSelector("span.a-price span.a-offscreen"));
            List<WebElement> productNames = driver.findElements(By.cssSelector("h2"));


            List<Double> prices = new ArrayList<>();
            for (WebElement priceElement : productPrices) {
                String priceText = priceElement.getText().replace("$", "").replace(",", "").trim();
                prices.add(Double.parseDouble(priceText));
            }



            List<Double> sortedPrices = new ArrayList<>(prices);
            Collections.sort(sortedPrices, Collections.reverseOrder());

            // Get the second highest price
            double secondHighestPrice = sortedPrices.size() > 1 ? sortedPrices.get(1) : -1;
            System.out.println("Second Highest Price: $" + secondHighestPrice);

            // Select the second highest item
            for (int i = 0; i < productPrices.size(); i++) {
                if (prices.get(i).equals(secondHighestPrice)) {
                    productNames.get(i).click();
                    break;
                }
            }

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String formattedTimestamp = now.format(formatter);
            Path destinationPath = Paths.get("C:\\Users\\NKUMA438\\eclipse-workspace\\Demo\\screenshot\\screenshot_"+formattedTimestamp+".png");

            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                Files.copy(screenshotFile.toPath(), destinationPath);
                System.out.println("Screenshot saved to: " + destinationPath);
            }catch (IOException e){
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {}}

    @AfterClass
    public void tearDown() {
        // Close the driver
        driver.quit();

        // Flush the reports
        extent.flush();
    }
}
