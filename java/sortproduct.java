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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class sortproduct {
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
        test = extent.createTest("testprice");

        WebDriver driver = new ChromeDriver();

        try {
            // Open Puma application
            driver.get("https://www.puma.com");
            System.out.println("Navigated to Puma application");

            // Navigate to Women's Shop
            driver.findElement(By.xpath("//a[contains(text(), 'SHOP NOW')]")).click();
            Thread.sleep(3000); // Wait for products to load

            // Sort by Price: Low to High
            WebElement sortDropdown = driver.findElement(By.xpath("//select[@class=\"absolute w-full inset-0 opacity-0 text-neutral-100 cursor-pointer\"]"));
            sortDropdown.click();
            WebElement sortLowToHigh = driver.findElement(By.xpath("//option[contains(text(), 'Price Low To High')]"));
            sortLowToHigh.click();
            Thread.sleep(3000); // Wait for sorting to complete

            // Get sorted prices
            List<WebElement> priceElements = driver.findElements(By.xpath("//span[contains(@class, 'price')]"));
            List<Double> pricesLowToHigh = new ArrayList<>();
            for (WebElement priceElement : priceElements) {
                String priceText = priceElement.getText().replace("$", "").replace(",", "").trim();
                pricesLowToHigh.add(Double.parseDouble(priceText));
            }

            // Check if prices are sorted low to high
            List<Double> sortedPricesLowToHigh = new ArrayList<>(pricesLowToHigh);
            Collections.sort(sortedPricesLowToHigh);
            if (pricesLowToHigh.equals(sortedPricesLowToHigh)) {
                System.out.println("Prices are sorted correctly from Low to High.");
            } else {
                System.out.println("Prices are NOT sorted correctly from Low to High.");
            }

            // Sort by Price: High to Low
            sortDropdown.click();
            WebElement sortHighToLow = driver.findElement(By.xpath("//option[contains(text(), 'Price High To Low')]"));
            sortHighToLow.click();
            Thread.sleep(3000); // Wait for sorting to complete

            // Get sorted prices again
            priceElements = driver.findElements(By.xpath("//span[contains(@class, 'price')]"));
            List<Double> pricesHighToLow = new ArrayList<>();
            for (WebElement priceElement : priceElements) {
                String priceText = priceElement.getText().replace("$", "").replace(",", "").trim();
                pricesHighToLow.add(Double.parseDouble(priceText));
            }

            // Check if prices are sorted high to low
            List<Double> sortedPricesHighToLow = new ArrayList<>(pricesHighToLow);
            Collections.sort(sortedPricesHighToLow, Collections.reverseOrder());
            if (pricesHighToLow.equals(sortedPricesHighToLow)) {
                System.out.println("Prices are sorted correctly from High to Low.");
            } else {
                System.out.println("Prices are NOT sorted correctly from High to Low.");
            }

            List<WebElement> products = driver.findElements(By.cssSelector("div.relative.w-full.flex.flex-col.gap-2"));
            System.out.println("Total products displayed: " + products.size());

            // the prices in displayed page
            List<WebElement> prices = driver.findElements(By.xpath("//span[contains(@class, 'price')]"));
            for (WebElement price : prices) {
                System.out.println(price.getText());
            }

            WebElement firstProduct = driver.findElement(By.xpath("//h3[contains(text(), \"FAST-R NITRO™ Elite 2 Women's Running Shoes\")]"));
            firstProduct.click();
            Thread.sleep(3000); // Wait for the add to cart action to complete

            driver.findElement(By.xpath("//span[@class=\"absolute inset-0 flex items-center justify-center\"]")).click();
            System.out.println("size clicked");

            WebElement addToCartButton = driver.findElement(By.xpath("(//div[@class=\"opacity-100\"])[6]"));
            addToCartButton.click();
            Thread.sleep(3000);

            driver.findElement(By.xpath("//div[@class=\"grid gap-3 mobile:grid-cols-1 grid-cols-1\"]")).click();
            System.out.println("product is added to the cart");



            // Optional: Verify if the product is added to the cart (You can add further verification here)
            WebElement cartCount = driver.findElement(By.xpath("//div[@class=\"flex items-start flex-grow space-x-4 sm:space-x-6\"]"));
            System.out.println("Products in cart: " + cartCount.getText());


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
