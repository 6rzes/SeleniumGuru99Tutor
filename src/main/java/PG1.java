import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class PG1 {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver","c:\\Selenium\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.google.pl");
        driver.findElement(By.id("cnsd")).click();
    }
}
