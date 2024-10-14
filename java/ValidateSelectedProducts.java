import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ValidateSelectedProducts {
    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();

        try {

            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


            driver.get("https://www.apple.com");
            System.out.println("Navigated to Apple website.");


            WebElement watchSection = driver.findElement(By.linkText("Watch"));
            watchSection.click();
            System.out.println("Navigated to Watch section.");


            WebElement compareWatchButton = driver.findElement(By.linkText("Compare"));
            compareWatchButton.click();
            System.out.println("Navigated to Compare Watches section.");


            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            SoftAssert sa = new SoftAssert();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='selector-0']")));
            driver.findElement(By.xpath("//select[@id='selector-0']")).click();
            List<WebElement> watchNameList = driver.findElements(By.xpath("//select[@id='selector-0']//optgroup//option"));
            List<String> watchName1 = watchNameList.stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='selector-1']")));
            driver.findElement(By.xpath("//select[@id='selector-1']")).click();
            List<WebElement> watchNameList2 = driver.findElements(By.xpath("//select[@id='selector-1']//optgroup//option"));
            List<String> watchName2 = watchNameList2.stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());

            sa.assertEquals(watchName1, watchName2, "Error: Names did not match");



        } catch (Exception e) {
            System.out.println("Test failed due to exception: " + e.getMessage());
        } finally {
            // Clean up
            //driver.quit();
        }
    }
}

